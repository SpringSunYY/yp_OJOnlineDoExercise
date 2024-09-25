package com.yy.ojbackendjudgeservice.judge.strategy;


import com.yy.ojbackendmodel.codesandbox.JudgeInfo;
import com.yy.ojbackendmodel.dto.question.JudgeCase;
import com.yy.ojbackendmodel.entity.Question;
import com.yy.ojbackendmodel.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCase> judgeCaseList;

    private Question question;

    private QuestionSubmit questionSubmit;
}
