package com.yy.ojbackendjudgeservice.judge.codesandbox.impl;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.util.StringUtils;
import com.yy.ojbackendcommon.common.ErrorCode;
import com.yy.ojbackendcommon.exception.BusinessException;
import com.yy.ojbackendjudgeservice.judge.codesandbox.CodeSandbox;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeRequest;
import com.yy.ojbackendmodel.codesandbox.ExecuteCodeResponse;

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
    // 定义鉴权请求头和密钥
    private static final String AUTH_REQUEST_HEADER = "auth";

    //实际需要加密
    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}