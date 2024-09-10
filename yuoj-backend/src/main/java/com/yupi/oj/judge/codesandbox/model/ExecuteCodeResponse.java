package com.yupi.oj.judge.codesandbox.model;

import com.yupi.oj.model.dto.question.JudgeConfig;
import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge.mode
 * @Author: YY
 * @CreateTime: 2024-09-10  22:53
 * @Description: ExecuteCodeResponse
 * @Version: 1.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExecuteCodeResponse {
    private List<String> outputList;
    /**
     * 执行信息
     */
    private String message;

    /**
     * 执行状态
     */
    private Integer status;

    /**
     * 盘提醒下
     */
    private JudgeInfo judgeInfo;
}
