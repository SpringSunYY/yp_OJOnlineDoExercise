package com.yupi.sandbox.model;

import lombok.Data;

/**
 * @Project: yuoj-code-sandbox
 * @Package: com.yupi.sandbox.model
 * @Author: YY
 * @CreateTime: 2024-09-12  22:28
 * @Description: ExecuteMessage 执行信息
 * @Version: 1.0
 */
@Data
public class ExecuteMessage {

    private Integer exitValue;

    private String message;

    private String errorMessage;

    private Long time;
}
