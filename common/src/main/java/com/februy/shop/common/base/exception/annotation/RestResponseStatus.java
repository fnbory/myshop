package com.februy.shop.common.base.exception.annotation;

import org.springframework.http.HttpStatus;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 4:54
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface RestResponseStatus {
    HttpStatus value();

    int code();

    String msgKey() default "";

    String url() default "";
}
