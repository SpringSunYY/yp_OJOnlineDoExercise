package com.yy.ojbackendquestionservice.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Project: oj-backend-microservice
 * @Package: com.yy.ojbackendquestionservice.rabbitmq
 * @Author: YY
 * @CreateTime: 2024-09-28  16:43
 * @Description: rabbitmq
 * 消息生产者
 * @Version: 1.0
 */
@Component
public class MyMessageProducer {

    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * @param exchange
     * @param routingKey
     * @param message
     */
    public void sendMessage(String exchange, String routingKey, String message) {
        rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }

}