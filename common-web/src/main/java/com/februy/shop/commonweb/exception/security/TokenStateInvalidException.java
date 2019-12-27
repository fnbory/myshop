package com.februy.shop.commonweb.exception.security;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 3:38
 */
@RestResponseStatus(value= HttpStatus.UNAUTHORIZED,code=4)
@RestField("tokenStatus")
public class TokenStateInvalidException extends BaseRestException {
    public TokenStateInvalidException(String tokenStatus){
        super(tokenStatus);
    }
}