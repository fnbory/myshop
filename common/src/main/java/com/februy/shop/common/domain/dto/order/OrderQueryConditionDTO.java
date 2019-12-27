package com.februy.shop.common.domain.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.februy.shop.common.enumeration.order.OrderStatus;
import com.februy.shop.common.properties.DateTimeProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 9:18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderQueryConditionDTO implements Serializable {

    private Long userId;
    private Long categoryId;
    private List<Long> productIds;
    @JsonFormat(pattern = DateTimeProperties.LOCAL_DATE_TIME_PATTERN)
    private LocalDateTime begin;
    @JsonFormat(pattern = DateTimeProperties.LOCAL_DATE_TIME_PATTERN)
    private LocalDateTime end;
    private OrderStatus status;
    private Integer pageNum;
    private Integer pageSize;
}
