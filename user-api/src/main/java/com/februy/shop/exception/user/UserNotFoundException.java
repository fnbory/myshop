package com.februy.shop.exception.user;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 8:37
 */
@RestResponseStatus(value = HttpStatus.NOT_FOUND,code=8)
public class UserNotFoundException  extends BaseRestException {

    public UserNotFoundException(String msg){
        super(msg);
    }

}
