package com.februy.shop.exception.product;

import com.februy.shop.common.base.exception.BaseRestException;
import com.februy.shop.common.base.exception.annotation.RestField;
import com.februy.shop.common.base.exception.annotation.RestResponseStatus;
import org.springframework.http.HttpStatus;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 9:08
 */
@RestResponseStatus(value = HttpStatus.NOT_FOUND,code=10)
@RestField("product")
public class ProductNotFoundException extends BaseRestException {
    public ProductNotFoundException(String key){
        super(key);
    }
}