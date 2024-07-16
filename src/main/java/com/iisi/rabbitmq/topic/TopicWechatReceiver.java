package com.iisi.rabbitmq.topic;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j

@Service
//監聽佇列
@RabbitListener(bindings = @QueueBinding(value = @Queue(value = RabbitConfig.WECHAT_QUEUE, durable = "true"), exchange = @Exchange(name = RabbitConfig.EXCHANGE_NAME, durable = "true", type = ExchangeTypes.TOPIC), key = "*.wechat.#"))
public class TopicWechatReceiver {
    @RabbitHandler
    public void wechatMessage(CustomMessage msg, Channel channel, Message message) throws IOException {
        try {
            log.info("wechat topic --接收到消息：{}", msg);

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                log.error("消息已重複處理失敗,拒絕再次接收...");
                //basicReject: 拒絕消息，與basicNack區別在於不能進行批次操作，其他用法很相似 false表示消息不再重新進入佇列
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒絕消息
            } else {
                log.error("消息即將再次返回佇列處理...");
                // basicNack:表示失敗確認，一般在消費消息業務異常時用到此方法，可以將消息重新投遞入佇列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }

    }
}