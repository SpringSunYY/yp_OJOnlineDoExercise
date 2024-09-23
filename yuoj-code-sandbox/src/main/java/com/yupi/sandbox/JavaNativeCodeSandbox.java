package com.yupi.sandbox;

import com.yupi.sandbox.model.ExecuteCodeRequest;
import com.yupi.sandbox.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/**
 * @Project: yuoj-code-sandbox
 * @Package: com.yupi.sandbox
 * @Author: YY
 * @CreateTime: 2024-09-22  16:45
 * @Description: JavaNativeCodeSandbox java原生实现，直接复用模版方法
 * @Version: 1.0
 */
@Component
public class JavaNativeCodeSandbox extends JavaCodeSandboxTemplate {
    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }
}

