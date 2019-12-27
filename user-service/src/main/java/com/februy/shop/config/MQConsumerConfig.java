package com.februy.shop.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.februy.shop.mq.listener.TransactionCheckMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 2:56
 */
@Configuration
@Slf4j
public class MQConsumerConfig {
    private DefaultMQPushConsumer consumer;

    @Value("${spring.rocketmq.consumer.group-name}")
    private String groupName;
    @Value("${spring.rocketmq.consumer.namesrv-addr}")
    private String namesrvAddr;
    @Value("${spring.rocketmq.consumer.topic}")
    private String topic;
    @Autowired
    private TransactionCheckMessageListener transactionCheckMessageListener;
    @Value("${spring.rocketmq.consumer.consume-failure-retry-times}")
    private Integer retryTimes;

    @PostConstruct
    public void init() throws MQClientException {
        this.consumer = new DefaultMQPushConsumer(groupName);
        this.consumer.setNamesrvAddr(namesrvAddr);
        // 启动后从队列头部开始消费
        this.consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        this.consumer.subscribe(topic, "*");
        this.consumer.registerMessageListener(transactionCheckMessageListener);
        this.consumer.start();
        log.info("consumer started!");
    }
}
