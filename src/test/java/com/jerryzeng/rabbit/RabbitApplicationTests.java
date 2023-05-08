package com.jerryzeng.rabbit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RabbitApplicationTests {
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private TopicReceiver topicReceiver;

    @Test
    void test() throws InterruptedException {
        topicSender.send_one();
        topicSender.send_two();
    }


}
