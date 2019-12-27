package com.februy.shop.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Author: fnbory
 * @Date: 24/12/2019 下午 1:28
 */
public class JsonUtil {

    private static ObjectMapper objectMapper=new ObjectMapper();

    public static String json(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
