package com.februy.shop.exception.pay;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 1:33
 */
@RestResponseStatus(value = HttpStatus.BAD_REQUEST,code = 10)
public class DepositException extends BaseRestException {
    public DepositException(String amount){
        super(amount);
    }
}