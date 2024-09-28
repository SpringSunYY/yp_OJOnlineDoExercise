package com.yy.ojbackendjudgeservice.rabbitmq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;


/**
 * @Project: oj-backend-microservice
 * @Package: com.yy.ojbackendjudgeservice.rabbitmq
 * @Author: YY
 * @CreateTime: 2024-09-28  16:14
 * @Description: InitRabbitMq
 * @Version: 1.0
 */
@Slf4j
public class InitRabbitMq {
    public static void doInit() {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("192.168.10.100");
//            factory.setPort(15672);
            Connection connection = factory.newConnection();
            Channel channel = connection.createChannel();
            String EXCHANGE_NAME = "code_exchange";
            channel.exchangeDeclare(EXCHANGE_NAME, "direct");

            // 创建队列，随机分配一个队列名称
            String queueName = "code_queue";
            channel.queueDeclare(queueName, true, false, false, null);
            channel.queueBind(queueName, EXCHANGE_NAME, "my_routingKey");
            log.info("消息队列启动成功");
        } catch (Exception e) {
            log.error("消息队列启动失败",e);
        }
    }

    public static void main(String[] args) {
        doInit();
    }
}
