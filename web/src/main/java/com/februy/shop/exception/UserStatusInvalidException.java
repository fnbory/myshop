package com.februy.shop.exception;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 1:23
 */
@RestResponseStatus(value = HttpStatus.UNAUTHORIZED,code=3)
@RestField("userStatus")
public class UserStatusInvalidException extends BaseRestException {
    public UserStatusInvalidException(String userStatus){
        super(userStatus);
    }
}
