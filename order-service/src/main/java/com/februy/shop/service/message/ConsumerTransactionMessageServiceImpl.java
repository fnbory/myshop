package com.februy.shop.service.message;

import com.februy.shop.common.domain.entity.message.ConsumerTransactionMessage;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.februy.shop.dao.message.ConsumerTransactionMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 26/12/2019 下午 4:16
 */
public class ConsumerTransactionMessageServiceImpl implements ConsumerTransactionMessageService {

    @Autowired
    private ConsumerTransactionMessageMapper mapper;

    @Transactional(readOnly = true)
    @Override
    public Map<Long, MessageStatus> findConsumerMessageStatuses(List<Long> ids) {
        Map<Long, MessageStatus> result = new HashMap<>();
        for(Long id:ids){
            MessageStatus status=mapper.findStatusById(id);
            result.put(id,status);
        }
        return result;
    }

    @Override
    public boolean isMessageConsumedSuccessfully(Long id) {
        MessageStatus status = mapper.findStatusById(id);
        return status == MessageStatus.CONSUMED;
    }

    @Override
    public void insertOrUpdate(ConsumerTransactionMessage message) {
        ConsumerTransactionMessage recordInDB = mapper.selectByPrimaryKey(message.getId());
        if (recordInDB == null) {
            mapper.insert(message);
        } else {
            recordInDB.setMessageStatus(message.getMessageStatus());
            mapper.updateByPrimaryKeySelective(recordInDB);
        }
    }

    @Override
    public void update(ConsumerTransactionMessage record) {
        mapper.updateByPrimaryKeySelective(record);
    }
}

