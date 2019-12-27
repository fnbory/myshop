package com.februy.shop.exception.pay;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 2:50
 */
@RestResponseStatus(value= HttpStatus.FORBIDDEN,code=2)
@RestField("userId")
public class PaymentPasswordInCorrectException extends BaseRestException {
    public PaymentPasswordInCorrectException(Long userId){
        super(userId);
    }
}