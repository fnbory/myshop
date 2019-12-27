package com.februy.shop.common.domain.dto.message;

import com.februy.shop.common.enumeration.message.MessageStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 2:04
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageQueryConditionDTO implements Serializable {
    private MessageStatus status;
    private Long id;
    private String topic;
    private Integer pageNum;
    private Integer pageSize;
}