package com.yy.ojbackendjudgeservice.judge;

import cn.hutool.json.JSONUtil;

import com.yy.ojbackendcommon.common.ErrorCode;
import com.yy.ojbackendcommon.exception.BusinessException;
import com.yy.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yy.ojbackendjudgeservice.judge.codesandbox.CodeSandboxFactory;
import com.yy.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.yy.ojbackendmodel.codesandbox.JudgeInfo;
import com.yy.ojbackendmodel.dto.question.JudgeCase;
import com.yy.ojbackendmodel.entity.Question;
import com.yy.ojbackendmodel.entity.QuestionSubmit;
import com.yy.ojbackendmodel.enums.QuestionSubmitStatusEnum;
import com.yy.ojbackendserviceclient.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge
 * @Author: YY
 * @CreateTime: 2024-09-11  16:54
 * @Description: JudgeServiceImpl
 * @Version: 1.0
 */
@Service
public class JudgeServiceImpl implements JudgeService {
    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;

    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        //拿到题目提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在！！！");
        }
        Long questionId = questionSubmit.getQuestionId();
        Question question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目信息不存在！！！");
        }
        //更改判题状态 防止用户多次提交重复执行 判题中
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中！！！");
        }
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新错误！！！");
        }

        //调用沙箱
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);

        //用户输入
        String code = questionSubmit.getCode();
        String language = questionSubmit.getLanguage();
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCase::getInput).collect(Collectors.toList());

        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        //返回结果
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();

        // 根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "判题状态更新错误！！！");
        }
        return questionSubmitService.getById(questionId);
    }
}
