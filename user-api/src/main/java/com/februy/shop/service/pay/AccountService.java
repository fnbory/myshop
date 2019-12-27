package com.februy.shop.service.pay;

import com.februy.shop.common.domain.entity.message.ProducerTransactionMessage;
import com.februy.shop.common.domain.entity.order.Order;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 1:46
 */
public interface AccountService {

    void commit(Order order, String paymentPassword);

    void rollback(ProducerTransactionMessage message);
}
