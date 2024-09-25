package com.yy.ojbackendjudgeservice.judge.codesandbox;


import com.yy.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;


    /**
     * @description: 代理需要传入参数
     * @author: YY
     * @method: CodeSandboxProxy
     * @date: 2024/9/11 16:39
     * @param:
     * @param: codeSandbox
     * @return:
     **/
    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息：" + executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        log.info("代码沙箱响应信息：" + executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
