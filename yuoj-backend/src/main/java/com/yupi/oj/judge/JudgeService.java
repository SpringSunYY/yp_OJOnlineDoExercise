package com.yupi.oj.judge;

import com.yupi.oj.model.entity.QuestionSubmit;

public interface JudgeService {
    /**
     * @description: 判题服务
     * @author: YY
     * @method: doJudge
     * @date: 2024/9/11 16:54
     * @param:
     * @param: questionSubmitId
     * @return: com.yupi.oj.model.vo.QuestionSubmitVO
     **/
    QuestionSubmit doJudge(long questionSubmitId);
}
