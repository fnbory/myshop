package com.februy.shop.exception.pay;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 25/12/2019 下午 2:51
 */
@RestResponseStatus(value = HttpStatus.NOT_ACCEPTABLE,code = 1)
@RestField("balance")
public class BalanceNotEnoughException extends BaseRestException {
    public BalanceNotEnoughException(String balance){
        super(balance);
    }
}