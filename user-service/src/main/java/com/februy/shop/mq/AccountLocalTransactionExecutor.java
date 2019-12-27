package com.februy.shop.mq;

import com.alibaba.rocketmq.client.producer.LocalTransactionExecuter;
import com.alibaba.rocketmq.client.producer.LocalTransactionState;
import com.alibaba.rocketmq.common.message.Message;
import com.februy.shop.common.domain.entity.message.ProducerTransactionMessage;
import com.februy.shop.common.domain.entity.order.Order;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.februy.shop.common.enumeration.order.OrderStatus;
import com.februy.shop.common.utils.ProtoStuffUtil;
import com.februy.shop.exception.order.OrderStateIllegalException;
import com.februy.shop.service.message.ProducerTransactionMessageService;
import com.februy.shop.service.pay.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 1:47
 */
@Component
@Slf4j
public class AccountLocalTransactionExecutor implements LocalTransactionExecuter {

    @Autowired
    private PayService payService;

    @Autowired
    private ProducerTransactionMessageService messageService;

    @Override
    public LocalTransactionState executeLocalTransactionBranch(Message message, Object o) {
        try {
            String paymentPassword=(String) o;
            Order order= ProtoStuffUtil.deserialize(message.getBody(),Order.class);
            if(order.getOrderStatus()!= OrderStatus.UNPAID){
                log.info("{} 订单状态不为unpaid", order.getId());
                throw new OrderStateIllegalException(order.getOrderStatus().toString());
            }
            payService.decreaseAccount(order.getUser().getId(),order.getTotalPrice(),paymentPassword);
            ProducerTransactionMessage msg=ProducerTransactionMessage.builder()
                                            .id(order.getId())
                                            .body(message.getBody())
                                            .createTime(LocalDateTime.now())
                                            .updateTime(LocalDateTime.now())
                                            .messageStatus(MessageStatus.UNCONSUMED)
                                            .topic(message.getTopic())
                                            .sendTimes(0)
                                            .build();
            messageService.save(msg);
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("本地事务执行失败，直接回滚!");
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }
}
