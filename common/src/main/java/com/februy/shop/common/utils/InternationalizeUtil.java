package com.februy.shop.common.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Locale;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 6:26
 */
@Component
public final class InternationalizeUtil {

    @Autowired
    private MessageSource ms;

    private InternationalizeUtil(){}

    private static InternationalizeUtil util;

    @PostConstruct
    public void init() {
        util = this;
        util.ms = this.ms;
    }

    public static String getMessage(String message, Locale locale){
        return util.ms.getMessage(message, null, locale);
    }

}
