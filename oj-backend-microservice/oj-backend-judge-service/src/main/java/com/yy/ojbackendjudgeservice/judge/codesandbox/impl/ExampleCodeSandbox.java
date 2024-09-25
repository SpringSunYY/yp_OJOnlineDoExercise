package com.yy.ojbackendjudgeservice.judge.codesandbox.impl;



import com.yy.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeResponse;
import com.yy.ojbackendmodel.codesandbox.JudgeInfo;
import com.yy.ojbackendmodel.enums.JudgeInfoMessageEnum;
import com.yy.ojbackendmodel.enums.QuestionSubmitStatusEnum;

import java.util.List;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge.codesandbox
 * @Author: YY
 * @CreateTime: 2024-09-10  22:59
 * @Description: ExampleCodeSandbox
 * 示例代码沙箱
 * @Version: 1.0
 */
public class ExampleCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        List<String> inputList = executeCodeRequest.getInputList();
        System.out.println("事例");
        ExecuteCodeResponse executeCodeResponse = new ExecuteCodeResponse();
        executeCodeResponse.setOutputList(inputList);
        executeCodeResponse.setMessage("执行成功！！！");
        executeCodeResponse.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        JudgeInfo judgeInfo = new JudgeInfo();
        judgeInfo.setMessage(JudgeInfoMessageEnum.ACCEPTED.getText());
        judgeInfo.setMemory(100L);
        judgeInfo.setTime(100L);
        executeCodeResponse.setJudgeInfo(judgeInfo);

        return executeCodeResponse;
    }
}