package com.iisi.rabbitmq.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CustomMessageSender {

    public final RabbitTemplate rabbitTemplate;

    public CustomMessageSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedDelay = 5000L)
    public void sendMessage() {
        log.info("Sending message...");
        rabbitTemplate.convertAndSend(RabbitConfig.EXCHANGE_NAME, "com.sms.email.message", CustomMessage.generateCustomMessage());
    }
}