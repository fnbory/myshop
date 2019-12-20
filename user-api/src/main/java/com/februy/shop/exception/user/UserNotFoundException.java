package com.februy.shop.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 8:37
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class UserNotFoundException  extends RuntimeException{

    public UserNotFoundException(String msg){
        super(msg);
    }

}
