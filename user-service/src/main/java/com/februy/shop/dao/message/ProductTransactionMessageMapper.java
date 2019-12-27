package com.februy.shop.dao.message;

import com.februy.shop.common.domain.dto.message.MessageQueryConditionDTO;
import com.februy.shop.common.domain.entity.message.ProducerTransactionMessage;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 2:15
 */
@Mapper
public interface ProductTransactionMessageMapper {

    int insert(ProducerTransactionMessage record);

    Page<ProducerTransactionMessage> findByCondition(MessageQueryConditionDTO queryDTO, Integer pageNum, Integer pageSize);

    List<ProducerTransactionMessage> selectBatchByPrimaryKeys(List<Long> ids);

    void updateByPrimaryKeySelective(ProducerTransactionMessage message);
}
