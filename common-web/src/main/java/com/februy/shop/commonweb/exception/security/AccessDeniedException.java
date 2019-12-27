package com.februy.shop.commonweb.exception.security;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 9:55
 */
@RestResponseStatus(value = HttpStatus.FORBIDDEN,code=1)
@RestField("role")
public class AccessDeniedException extends BaseRestException {
    public AccessDeniedException(String role){
        super(role);
    }
}