package com.yupi.sandbox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge.mode
 * @Author: YY
 * @CreateTime: 2024-09-10  22:52
 * @Description: ExecuteCodeRequest
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeRequest {
    private List<String> inputList;
    private String code;
    private String language;
}
