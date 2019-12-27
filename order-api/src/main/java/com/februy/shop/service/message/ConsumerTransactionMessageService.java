package com.februy.shop.service.message;

import com.februy.shop.common.domain.entity.message.ConsumerTransactionMessage;
import com.februy.shop.common.enumeration.message.MessageStatus;

import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 4:23
 */
public interface ConsumerTransactionMessageService {
    Map<Long, MessageStatus> findConsumerMessageStatuses(List<Long> ids);

    boolean isMessageConsumedSuccessfully(Long id);

    void insertOrUpdate(ConsumerTransactionMessage message);

    void update(ConsumerTransactionMessage build);
}
