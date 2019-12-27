package com.februy.shop.config;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.RunnableFuture;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 2:22
 */
@Configuration
@Slf4j
@Getter
public class MQProducerConfig {

    @Value("${spring.rocketmq.producer.group-name}")
    private String groupName;
    @Value("${spring.rocketmq.producer.namesrv-addr}")
    private String namesrvAddr;
    @Value("${spring.rocketmq.producer.topic}")
    private String topic;
    @Value("${spring.rocketmq.producer.confirm-message-faiure-retry-times}")
    private Integer retryTimes;
    public static final Integer CHECK_GAP = 1;
    @Value("${spring.rocketmq.producer.check-keys}")
    private String checkKeys;

    @Bean
    public MQProducer mqProducer() throws MQClientException {
        TransactionMQProducer producer=new TransactionMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setTransactionCheckListener(
                msg ->  LocalTransactionState.COMMIT_MESSAGE
        );

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                producer.shutdown();
            }
        }));
        producer.start();
        log.info("producer started!");
        return producer;
    }
}
