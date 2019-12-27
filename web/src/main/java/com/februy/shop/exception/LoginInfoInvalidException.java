package com.februy.shop.exception;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import com.februy.shop.common.domain.dto.user.LoginDTO;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 2:40
 */
@RestResponseStatus(value = HttpStatus.UNAUTHORIZED, code = 1)
@RestField("loginInfo")
public class LoginInfoInvalidException extends BaseRestException {
    public LoginInfoInvalidException(LoginDTO loginDTO) {
        super(loginDTO);
    }
}