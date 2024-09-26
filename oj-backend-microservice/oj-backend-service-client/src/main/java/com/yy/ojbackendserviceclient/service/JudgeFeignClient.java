package com.yy.ojbackendserviceclient.service;


import com.yy.ojbackendmodel.entity.QuestionSubmit;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "oj-backend-judge-service", path = "/api/judge/inner")
public interface JudgeFeignClient {
    /**
     * @description: 判题服务
     * @author: YY
     * @method: doJudge
     * @date: 2024/9/11 16:54
     * @param:
     * @param: questionSubmitId
     * @return: com.yupi.oj.model.vo.QuestionSubmitVO
     **/
    @PostMapping("/do")
    QuestionSubmit doJudge(@RequestParam("questionSubmitId") long questionSubmitId);
}
