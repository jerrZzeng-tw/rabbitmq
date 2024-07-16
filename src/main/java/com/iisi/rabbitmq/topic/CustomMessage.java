package com.iisi.rabbitmq.topic;

import lombok.Builder;
import lombok.Data;

import java.util.Random;

@Builder
@Data
public class CustomMessage {
    static Random random = new Random();

    String text;
    Integer num;
    Boolean secret;

    public static CustomMessage generateCustomMessage() {
        int num = random.nextInt(1000000);
        return CustomMessage.builder().text("Hello").num(num).secret(num % 2 == 0).build();
    }
}