package com.yupi.oj.judge.codesandbox;

import com.yupi.oj.judge.codesandbox.model.ExecuteCodeRequest;
import com.yupi.oj.judge.codesandbox.model.ExecuteCodeResponse;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge.codesandbox
 * @Author: YY
 * @CreateTime: 2024-09-10  22:49
 * @Description: CodeSandbox
 *              沙箱代码定义
 * @Version: 1.0
 */
public interface CodeSandbox {
    /**
     * @description: 执行代码
     * @author: YY
     * @method: executeCode
     * @date: 2024/9/10 22:58
     * @param:
     * @param: executeCodeRequest
     * @return: com.yupi.oj.judge.model.ExecuteCodeResponse
     **/
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}
