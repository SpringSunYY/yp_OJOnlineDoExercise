package com.yy.ojbackendquestionservice.controller.inner;

import com.yy.ojbackendmodel.entity.Question;
import com.yy.ojbackendmodel.entity.QuestionSubmit;
import com.yy.ojbackendquestionservice.service.QuestionService;
import com.yy.ojbackendquestionservice.service.QuestionSubmitService;
import com.yy.ojbackendserviceclient.service.QuestionFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @Project: oj-backend-microservice
 * @Package: com.yy.ojbackendquestionservice.controller.innner
 * @Author: YY
 * @CreateTime: 2024-09-26  22:02
 * @Description: QuestionInnerController
 * 服务仅内部调用，不是给前端的
 * @Version: 1.0
 */
@RestController
@RequestMapping("/inner")
public class QuestionInnerController implements QuestionFeignClient {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @GetMapping("/get/id")
    @Override
    public Question getQuestionById(@RequestParam("questionId") long questionId) {
        return questionService.getById(questionId);
    }

    @GetMapping("/question_submit/get/id")
    @Override
    public QuestionSubmit getQuestionSubmitById(@RequestParam("questionId") long questionSubmitId) {
        return questionSubmitService.getById(questionSubmitId);
    }

    @PostMapping("/question_submit/update")
    @Override
    public boolean updateQuestionSubmitById(@RequestBody QuestionSubmit questionSubmit) {
        return questionSubmitService.updateById(questionSubmit);
    }

}
