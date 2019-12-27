package com.februy.shop.service.message;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.MQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.februy.shop.common.domain.dto.message.MessageQueryConditionDTO;
import com.februy.shop.common.domain.entity.message.ProducerTransactionMessage;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.februy.shop.config.MQProducerConfig;
import com.februy.shop.dao.message.ProductTransactionMessageMapper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.resources.Messages;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 2:21
 */
@Slf4j
@Service("producerTransactionMessageService")
public class ProducerTransactionMessageServiceImpl implements ProducerTransactionMessageService {

    @Autowired
    private ProductTransactionMessageMapper mapper;

    @Autowired
    private MQProducer producer;

    @Autowired
    MQProducerConfig config;


    @Transactional(readOnly = true)
    @Override
    public PageInfo<ProducerTransactionMessage> findByQueryDTO(MessageQueryConditionDTO queryDTO) {
        return mapper.findByCondition(queryDTO, queryDTO.getPageNum(), queryDTO.getPageSize()).toPageInfo();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ProducerTransactionMessage> findByIds(List<Long> ids) {
        return mapper.selectBatchByPrimaryKeys(ids);
    }

    @Transactional
    @Override
    public void reSend(List<ProducerTransactionMessage> messages) {
        for(ProducerTransactionMessage message:messages){
            if(message.getSendTimes()==config.getRetryTimes()){
                message.setUpdateTime(LocalDateTime.now());
                message.setMessageStatus(MessageStatus.OVER_CONFIRM_RETRY_TIME);
                mapper.updateByPrimaryKeySelective(message);
                continue;
            }
            Message msg=new Message();
            msg.setTopic(config.getTopic());
            msg.setBody(message.getBody());
            try {
                SendResult result=producer.send(msg);
                message.setSendTimes(message.getSendTimes()+1);
                message.setUpdateTime(LocalDateTime.now());
                log.info("发送重试消息完毕,Message:{},result:{}", message, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    @Override
    public void updateStatusAndReSend(Map<Long, MessageStatus> checkResult) {
        List<Long> confirmMessageFailedIds=new ArrayList<>();
        for(Map.Entry<Long, MessageStatus> entry:checkResult.entrySet()){
            if(entry.getValue()==null) confirmMessageFailedIds.add(entry.getKey());
            else mapper.updateByPrimaryKeySelective(ProducerTransactionMessage.builder().id(entry.getKey()).messageStatus(entry.getValue()).updateTime(LocalDateTime.now()).build());
            this.reSend(mapper.selectBatchByPrimaryKeys(confirmMessageFailedIds));
        }
    }

    @Override
    public void save(ProducerTransactionMessage msg) {
        mapper.insert(msg);
    }

    @Override
    public void update(ProducerTransactionMessage message) {
        mapper.updateByPrimaryKeySelective(message);
    }
}
