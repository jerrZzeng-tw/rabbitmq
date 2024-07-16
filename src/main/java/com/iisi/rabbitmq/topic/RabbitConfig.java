package com.iisi.rabbitmq.topic;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RabbitConfig {


    public static final String EXCHANGE_NAME = "topic-order-exchange";
    public static final String SMS_QUEUE = "sms-topic-queue";
    public static final String EMAIL_QUEUE = "email-topic-queue";
    public static final String WECHAT_QUEUE = "wechat-topic-queue";

    /**
     * 1.
     * 聲明交換機
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        /**
         * topicExchange的參數說明:
         * 1. 交換機名稱
         * 2. 是否持久化 true：持久化，交換機一直保留 false：不持久化，用完就刪除
         * 3. 是否自動刪除 false：不自動刪除 true：自動刪除
         */
        return new TopicExchange(EXCHANGE_NAME, true, false);
    }

    //    /**
    //     * 2.
    //     * 聲明佇列
    //     *
    //     * @return
    //     */
    //    @Bean
    //    public Queue smsQueue() {
    //        /**
    //         * Queue建構函式參數說明
    //         * 1. 佇列名
    //         * 2. 是否持久化 true：持久化 false：不持久化
    //         */
    //        return new Queue(SMS_QUEUE, true);
    //    }
    //
    //    @Bean
    //    public Queue emailQueue() {
    //        return new Queue(EMAIL_QUEUE, true);
    //    }
    //
    //    @Bean
    //    public Queue wechatQueue() {
    //        return new Queue(WECHAT_QUEUE, true);
    //    }
    //
    //    /**
    //     * 3.
    //     * 佇列與交換機繫結
    //     */
    //    @Bean
    //    public Binding smsBinding() {
    //        return BindingBuilder.bind(smsQueue()).to(topicExchange()).with("*.sms.#");
    //    }
    //
    //    @Bean
    //    public Binding emailBinding() {
    //        return BindingBuilder.bind(emailQueue()).to(topicExchange()).with("#.email.*");
    //    }
    //
    //    @Bean
    //    public Binding wechatBinding() {
    //        return BindingBuilder.bind(wechatQueue()).to(topicExchange()).with("*.wechat.#");
    //    }


    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory,
                                         final Jackson2JsonMessageConverter producerJackson2MessageConverter) {
        final var rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter);
        //設定開啟消息推送結果回呼
        rabbitTemplate.setMandatory(true);
        //設定ConfirmCallback回呼
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                //                log.info("==============ConfirmCallback start ===============");
                //                log.info("回呼資料：{}", correlationData);
                //                log.info("確認結果：{}", ack);
                //                log.info("返回原因：{}", cause);
                //                log.info("==============ConfirmCallback end =================");
            }
        });
        //設定ReturnCallback回呼
        rabbitTemplate.setReturnsCallback(new RabbitTemplate.ReturnsCallback() {
            @Override
            public void returnedMessage(ReturnedMessage returnedMessage) {
                //                log.info("==============ReturnCallback start ===============");
                //                log.info("傳送消息：{}", returnedMessage.getMessage());
                //                log.info("回應碼：{}", returnedMessage.getReplyCode());
                //                log.info("回應訊息：{}", returnedMessage.getReplyText());
                //                log.info("交換機：{}", returnedMessage.getExchange());
                //                log.info("路由鍵：{}", returnedMessage.getRoutingKey());
                //                log.info("==============ReturnCallback end =================");
            }
        });
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}