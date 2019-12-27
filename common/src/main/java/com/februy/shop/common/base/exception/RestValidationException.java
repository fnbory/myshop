package com.februy.shop.common.base.exception;

import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 4:28
 */

@RestResponseStatus(value = HttpStatus.BAD_REQUEST,code=1)
public class RestValidationException extends  BaseRestException {
    public RestValidationException(List<FieldError> errors){
        super(errors);
    }
}
