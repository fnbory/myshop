package com.februy.shop.service.message;

import com.februy.shop.common.domain.dto.message.MessageQueryConditionDTO;
import com.februy.shop.common.domain.entity.message.ProducerTransactionMessage;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 1:55
 */
public interface ProducerTransactionMessageService {

    PageInfo<ProducerTransactionMessage> findByQueryDTO(MessageQueryConditionDTO queryDTO);

    List<ProducerTransactionMessage> findByIds(List<Long> ids);

    void reSend(List<ProducerTransactionMessage> messages);

    void updateStatusAndReSend(Map<Long, MessageStatus> checkResult);

    void save(ProducerTransactionMessage msg);

    void update(ProducerTransactionMessage message);
}
