package com.februy.shop.config;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.TransactionCheckListener;
import com.alibaba.rocketmq.client.producer.TransactionMQProducer;
import com.alibaba.rocketmq.common.message.MessageExt;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 3:22
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

    @Bean
    public MQProducer mqProducer() throws MQClientException {
        TransactionMQProducer producer=new TransactionMQProducer(groupName);
        producer.setNamesrvAddr(namesrvAddr);
        producer.setTransactionCheckListener(new TransactionCheckListener() {
            @Override
            public LocalTransactionState checkLocalTransactionState(MessageExt messageExt) {
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
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
