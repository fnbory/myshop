package com.februy.shop.exception.email;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 2:25
 */
@RestResponseStatus(value = HttpStatus.NOT_FOUND,code=1)
@RestField("email")
public class EmailTemplateNotFoundException extends BaseRestException {
    public  EmailTemplateNotFoundException(String email){
        super(email);
    }
}
