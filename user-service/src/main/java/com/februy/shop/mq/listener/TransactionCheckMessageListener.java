package com.februy.shop.mq.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.februy.shop.common.utils.ProtoStuffUtil;
import com.februy.shop.service.message.ProducerTransactionMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 2:58
 */
@Component
@Slf4j
public class TransactionCheckMessageListener implements MessageListenerConcurrently {

    @Autowired
    private ProducerTransactionMessageService producerTransactionMessageService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        log.info("接收到消息数量为:{}", msgs.size());
        for(MessageExt message:msgs){
            try {
                Map<Long, MessageStatus> checkResult=null;
                String topic=message.getTopic();
                String keys=message.getKeys();
                checkResult= ProtoStuffUtil.deserialize(message.getBody(),Map.class);
                log.info("消费者接收到消息:topic: {}, keys:{} , order: {}", topic, keys, checkResult);
                producerTransactionMessageService.updateStatusAndReSend(checkResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

    }
}
