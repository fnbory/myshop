package com.februy.shop.dao.message;

import com.februy.shop.common.domain.entity.message.ConsumerTransactionMessage;
import com.februy.shop.common.enumeration.message.MessageStatus;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 4:45
 */
@Mapper
public interface ConsumerTransactionMessageMapper {

    MessageStatus findStatusById(Long id);

    void insert(ConsumerTransactionMessage message);

    void updateByPrimaryKeySelective(ConsumerTransactionMessage recordInDB);

    ConsumerTransactionMessage selectByPrimaryKey(Long id);
}
