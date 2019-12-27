package com.februy.shop.exception;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 2:24
 */
@RestResponseStatus(value = HttpStatus.UNAUTHORIZED,code=2)
@RestField("captcha")
public class CaptchaValidationException extends BaseRestException {
    public CaptchaValidationException(String value){
        super(value);
    }
}
