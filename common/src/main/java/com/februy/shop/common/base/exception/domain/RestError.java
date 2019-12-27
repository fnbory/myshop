package com.februy.shop.common.base.exception.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 5:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestError implements Serializable {

    private HttpStatus status;

    private int code;

    private List<RestFieldError> errors;

    private String moreinfoUrl;


}
