package com.jerryzeng.rabbit;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TopicReceiver {
    @RabbitListener(queues = {RabbitmqConfig.TOPIC_ONE})
    public void receiverOne(Topic topic) {
        System.out.println("Receiver 1:" + topic);
    }

    @RabbitListener(queues = {RabbitmqConfig.TOPIC_TWO})
    public void receiverTwo(Topic topic) {
        System.out.println("Receiver 2:" + topic);
    }
}
