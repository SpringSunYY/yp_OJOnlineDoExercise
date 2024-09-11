package com.yupi.oj.judge.strategy;

import com.yupi.oj.model.dto.questionsubmit.JudgeInfo;

/**
 * @Project: yuoj-backend
 * @Package: com.yupi.oj.judge.strategy
 * @Author: YY
 * @CreateTime: 2024-09-11  17:23
 * @Description: JudgeStrategy 判题策略
 * @Version: 1.0
 */
public interface JudgeStrategy {
    /**
     * @description: 执行判题
     * @author: YY
     * @method: doJudge
     * @date: 2024/9/11 17:25
     * @param:
     * @param: judgeContext
     * @return: com.yupi.oj.model.dto.questionsubmit.JudgeInfo
     **/
    JudgeInfo doJudge(JudgeContext judgeContext);
}
