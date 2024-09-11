package com.yupi.oj.judge;

import java.util.List;
import java.util.stream.Collectors;

import cn.hutool.json.JSONUtil;
import com.yupi.oj.common.ErrorCode;
import com.yupi.oj.exception.BusinessException;
import com.yupi.oj.judge.codesandbox.CodeSandbox;
import com.yupi.oj.judge.codesandbox.CodeSandboxFactory;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.oj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.oj.judge.strategy.JudgeContext;
import com.yupi.oj.judge.strategy.JudgeStrategy;
import com.yupi.oj.model.dto.question.JudgeCase;
import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;
import com.yupi.oj.model.entity.Question;
import com.yupi.oj.model.entity.QuestionSubmit;
import com.yupi.oj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.oj.service.QuestionService;
import com.yupi.oj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
