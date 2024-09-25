package com.yy.ojbackendjudgeservice.judge;

import com.yy.ojbackendjudgeservice.judge.strategy.DefaultJudgeStrategy;
import com.yy.ojbackendjudgeservice.judge.strategy.JavaLanguageJudgeStrategy;
import com.yy.ojbackendjudgeservice.judge.strategy.JudgeContext;
import com.yy.ojbackendjudgeservice.judge.strategy.JudgeStrategy;
import com.yy.ojbackendmodel.codesandbox.JudgeInfo;
import com.yy.ojbackendmodel.entity.QuestionSubmit;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmit questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}
