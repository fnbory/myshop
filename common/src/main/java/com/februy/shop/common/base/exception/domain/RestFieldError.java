package com.februy.shop.common.base.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;

import java.io.ObjectInputStream;
import java.io.Serializable;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 5:04
 */
@Data
@AllArgsConstructor
public class RestFieldError implements Serializable {

    private String field;

    private Object rejectedValue;

    private String message;

    public RestFieldError(FieldError error){
        super();
        this.field=error.getField();
        this.rejectedValue=error.getRejectedValue();
        this.message=error.getDefaultMessage();
    }



}
