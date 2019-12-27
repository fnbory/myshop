package com.februy.shop.service.pay;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.TransactionSendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.februy.shop.common.domain.entity.message.ProducerTransactionMessage;
import com.februy.shop.common.domain.entity.order.Order;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.februy.shop.common.utils.ProtoStuffUtil;
import com.februy.shop.config.MQProducerConfig;
import com.februy.shop.exception.pay.OrderPaymentException;
import com.februy.shop.mq.AccountLocalTransactionExecutor;
import com.februy.shop.service.message.ProducerTransactionMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 1:35
 */
@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private MQProducerConfig  config;

    @Autowired
    private MQProducer producer;

    @Autowired
    private ProducerTransactionMessageService messageService;

    @Autowired
    private PayService payService;

    @Autowired
    private AccountLocalTransactionExecutor executor;

    @Override
    public void commit(Order order, String paymentPassword) {
        Message message=new Message();
        message.setTopic(config.getTopic());
        message.setBody(ProtoStuffUtil.serialize(order));
        TransactionSendResult result=null;
        try {
            result=this.producer.sendMessageInTransaction(message,executor,paymentPassword);
            log.info("事务消息发送结果：{}", result);
            log.info("TransactionState:{} ", result.getLocalTransactionState());
        } catch (Exception e) {
            log.info("AccountService抛出异常...");
            e.printStackTrace();
        }
        if(result.getLocalTransactionState()== LocalTransactionState.ROLLBACK_MESSAGE){
            throw new OrderPaymentException(order.getId());
        }


    }

    @Transactional
    @Override
    public void rollback(ProducerTransactionMessage message) {
        Order order=ProtoStuffUtil.deserialize(message.getBody(),Order.class);
        message.setMessageStatus(MessageStatus.ROLLBACK);
        message.setUpdateTime(LocalDateTime.now());
        messageService.update(message);
        payService.increaseAccount(order.getUser().getId(),order.getTotalPrice());
    }
}
