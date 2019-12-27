package com.februy.shop.controller.message;

import com.februy.shop.common.domain.dto.message.MessageIdDTO;
import com.februy.shop.common.domain.dto.message.MessageQueryConditionDTO;
import com.februy.shop.common.domain.entity.message.ProducerTransactionMessage;
import com.februy.shop.common.enumeration.message.MessageStatus;
import com.februy.shop.common.properties.PageProperties;
import com.februy.shop.service.message.ProducerTransactionMessageService;
import com.februy.shop.service.pay.AccountService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 1:53
 */
@RestController("/message_console")
public class MessageConsoleController {

    @Autowired
    AccountService accountService;

    @Autowired
    ProducerTransactionMessageService messageService;

    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public PageInfo<ProducerTransactionMessage> findByQueryDTO(@RequestBody MessageQueryConditionDTO queryDTO){
        if (queryDTO.getPageNum() == null || queryDTO.getPageNum() <= 0) {
            queryDTO.setPageNum(Integer.valueOf(PageProperties.DEFAULT_PAGE_NUM));
        }
        if (queryDTO.getPageSize() == null || queryDTO.getPageSize() <= 0) {
            queryDTO.setPageSize(Integer.valueOf(PageProperties.DEFAULT_PAGE_SIZE));
        }
        return messageService.findByQueryDTO(queryDTO);
    }

    @RequestMapping(value = "/reSend", method = RequestMethod.POST)
    public void reSend(@RequestBody MessageIdDTO dto) {
        List<ProducerTransactionMessage> messages = messageService.findByIds(dto.getIds());
        messages.forEach(message ->{
            message.setMessageStatus(MessageStatus.UNCONSUMED);
            message.setSendTimes(0);
        });
        messageService.reSend(messages);
    }

    @RequestMapping(value = "/rollback", method = RequestMethod.POST)
    public void rollback(@RequestBody MessageIdDTO dto) {
        for (ProducerTransactionMessage message : messageService.findByIds(dto.getIds())) {
            accountService.rollback(message);
        }
    }

}
