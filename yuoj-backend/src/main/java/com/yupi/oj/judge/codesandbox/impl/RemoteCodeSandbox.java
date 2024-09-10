package com.yupi.oj.judge.codesandbox.impl;

import com.yupi.oj.judge.codesandbox.CodeSandbox;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge.codesandbox.impl
 * @Author: YY
 * @CreateTime: 2024-09-10  23:00
 * @Description: RemoteCodeSandbox
 * 远程代码沙箱
 * @Version: 1.0
 */
public class RemoteCodeSandbox implements CodeSandbox {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程");
        return null;
    }
}