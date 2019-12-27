package com.februy.shop.mq;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.februy.shop.common.domain.entity.message.ConsumerTransactionMessage;
import com.februy.shop.common.domain.entity.order.Order;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.februy.shop.common.utils.ProtoStuffUtil;
import com.februy.shop.config.MQConsumerConfig;
import com.februy.shop.config.MQProducerConfig;
import com.februy.shop.service.message.ConsumerTransactionMessageService;
import com.februy.shop.service.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 3:10
 */
@Component
@Slf4j
public class AccountMessageListener implements MessageListenerConcurrently {

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("consumerTransactionMessageService")
    private ConsumerTransactionMessageService messageService;

    @Autowired
    private MQProducerConfig producerConfig;

    @Autowired
    private MQConsumerConfig consumerConfig;

    @Autowired
    private MQProducer producer;




    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        log.info("接收到消息数量为:{}", msgs.size());
        for(MessageExt msg:msgs){
            ConsumerTransactionMessage message=null;
            Order order=null;
            try {
                String topic=msg.getTopic();
                String keys=msg.getKeys();
                if(keys.equals(consumerConfig.getCheckKeys())){
                    List<Long> ids= ProtoStuffUtil.deserialize(msg.getBody(),List.class);
                    log.info("消费者接收到回查消息:topic: {}, keys:{} ,body: {}", topic, keys,ids);
                    Map<Long, MessageStatus> result=messageService.findConsumerMessageStatuses(ids);
                    Message checkReply=new Message();
                    checkReply.setTopic(producerConfig.getTopic());
                    checkReply.setBody(ProtoStuffUtil.serialize(result));
                    producer.send(checkReply);
                    continue;
                }
                order=ProtoStuffUtil.deserialize(msg.getBody(),Order.class);
                log.info("消费者接收到消息:topic: {}, keys:{} , order: {}", topic, keys, order);

                // 防止重复消费
                if(messageService.isMessageConsumedSuccessfully(order.getId())) continue;
                message=ConsumerTransactionMessage.builder()
                            .id(order.getId())
                            .createTime(LocalDateTime.now())
                            .topic(msg.getTopic())
                            .build();
                orderService.finishOrder(order);
                message.setMessageStatus(MessageStatus.CONSUMED);
                messageService.insertOrUpdate(message);
            } catch (Exception e) {
                e.printStackTrace();
                if(msg.getReconsumeTimes()==consumerConfig.getRetryTimes()){
                    log.info("客户端重试三次,需要人工处理");
                    messageService.update(ConsumerTransactionMessage.builder()
                                                .id(order.getId())
                                                .messageStatus(MessageStatus.OVER_CONSUME_RETRY_TIME)
                                                .build());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                else {
                    log.info("消费失败，进行重试，当前重试次数为: {}", msg.getReconsumeTimes());
                    message.setMessageStatus(MessageStatus.CONSUME_FAILED);
                    messageService.insertOrUpdate(message);
                    return  ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
