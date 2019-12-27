package com.februy.shop.exception;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 3:16
 */
@RestResponseStatus(value = HttpStatus.UNAUTHORIZED,code =3)
@RestField("activationCode")
public class ActivationCodeValidationException extends BaseRestException {
    public ActivationCodeValidationException(String activationCode){
        super(activationCode);
    }
}
