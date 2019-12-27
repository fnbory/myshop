package com.februy.shop.common.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 2:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageIdDTO implements Serializable {
    private List<Long> ids;
}
