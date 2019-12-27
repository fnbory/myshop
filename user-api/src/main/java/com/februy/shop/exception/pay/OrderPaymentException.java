package com.februy.shop.exception.pay;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 2:32
 */
@RestField("order")
@RestResponseStatus(value= HttpStatus.BAD_REQUEST,code=40410)
public class OrderPaymentException extends BaseRestException {
    public OrderPaymentException(Long orderId){
        super(orderId);
    }
}