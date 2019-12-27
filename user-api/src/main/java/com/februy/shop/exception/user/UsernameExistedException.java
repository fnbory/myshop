package com.februy.shop.exception.user;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 4:25
 */
@RestResponseStatus(value = HttpStatus.CONFLICT,code=1)
@RestField("name")
public class UsernameExistedException extends BaseRestException {
    public UsernameExistedException(String msg){
        super(msg);
    }
}
