package com.februy.shop.exception.order;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 9:53
 */
@RestResponseStatus(value = HttpStatus.NOT_FOUND,code = 12)
@RestField("order")
public class OrderNotFoundException extends BaseRestException {
    public OrderNotFoundException(String order){
        super(order);
    }
}
