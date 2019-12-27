package com.februy.shop.common.utils;

import java.util.UUID;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 1:27
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
