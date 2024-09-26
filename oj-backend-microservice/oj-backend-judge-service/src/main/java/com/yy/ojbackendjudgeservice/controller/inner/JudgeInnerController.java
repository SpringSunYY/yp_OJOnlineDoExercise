package com.yy.ojbackendjudgeservice.controller.inner;

import com.yy.ojbackendjudgeservice.judge.JudgeService;
import com.yy.ojbackendmodel.entity.QuestionSubmit;
import com.yy.ojbackendserviceclient.service.JudgeFeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Project: oj-backend-microservice
 * @Package: com.yy.ojbackendjudgeservice.controller.inner
 * @Author: YY
 * @CreateTime: 2024-09-26  22:06
 * @Description: JudgeInnerController
 * 服务仅内部调用，不是给前端的
 * @Version: 1.0
 */
@RestController
@RequestMapping("/inner")
public class JudgeInnerController implements JudgeFeignClient {

    @Resource
    private JudgeService judgeService;

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    @Override
    @PostMapping("/do")
    public QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId){
        return judgeService.doJudge(questionSubmitId);
    }
}
