package com.yupi.oj.judge.codesandbox.impl;

import java.util.List;

import com.yupi.oj.judge.codesandbox.CodeSandbox;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;
import com.yupi.oj.judge.codesandbox.model.JudgeInfo;
import com.yupi.oj.model.enums.JudgeInfoMessageEnum;
import com.yupi.oj.model.enums.QuestionSubmitStatusEnum;

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