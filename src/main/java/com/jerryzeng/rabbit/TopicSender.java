package com.jerryzeng.rabbit;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    //两个消息接受者都可以收到
    public void send_one() {
        Topic topic = Topic.builder().name("one").age(1).build();
        System.out.println("Sender 1: " + topic);
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, RabbitmqConfig.TOPIC_ONE, topic);
    }


    //只有TopicReceiverTwo都可以收到
    public void send_two() {
        Topic topic = Topic.builder().name("two").age(2).build();
        System.out.println("Sender 2: " + topic);
        this.rabbitTemplate.convertAndSend(RabbitmqConfig.TOPIC_EXCHANGE, RabbitmqConfig.TOPIC_TWO, topic);
    }
}
