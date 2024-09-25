package com.yy.ojbackendjudgeservice.judge.codesandbox.impl;


import com.yy.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeResponse;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge.codesandbox.impl
 * @Author: YY
 * @CreateTime: 2024-09-10  23:01
 * @Description: ThirdPartyCodeSandbox
 * 第三方代码沙箱
 * @Version: 1.0
 */
public class ThirdPartyCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("第三方");
        return null;
    }
}
