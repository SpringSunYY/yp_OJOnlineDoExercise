package com.yupi.sandbox.controller;

import com.yupi.sandbox.JavaNativeCodeSandbox;
import com.yupi.sandbox.model.ExecuteCodeRequest;
import com.yupi.sandbox.model.ExecuteCodeResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Project: yuoj-code-sandbox
 * @Package: com.yupi.sandbox.controller
 * @Author: YY
 * @CreateTime: 2024-09-23  20:36
 * @Description: MainController
 * @Version: 1.0
 */
@RestController("/")
public class MainController {
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Resource
    private JavaNativeCodeSandbox javaNativeCodeSandbox;

    /**
     * @description: 执行代码沙箱
     * @author: YY
     * @method: executeCode
     * @date: 2024/9/23 20:37
     * @param:
     * @param: executeCodeRequest
     * @param: request
     * @param: response
     * @return: com.yupi.sandbox.model.ExecuteCodeResponse
     **/
    @PostMapping("/executeCode")
    ExecuteCodeResponse executeCode(@RequestBody ExecuteCodeRequest executeCodeRequest, HttpServletRequest request,
                                    HttpServletResponse response) {
        // 基本的认证
        String authHeader = request.getHeader(AUTH_REQUEST_HEADER);
        if (!AUTH_REQUEST_SECRET.equals(authHeader)) {
            response.setStatus(403);
            return null;
        }
        if (executeCodeRequest == null) {
            throw new RuntimeException("请求参数为空");
        }
        return javaNativeCodeSandbox.executeCode(executeCodeRequest);
    }
}
