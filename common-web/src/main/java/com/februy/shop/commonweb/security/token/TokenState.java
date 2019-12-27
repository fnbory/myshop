package com.februy.shop.commonweb.security.token;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 3:39
 */
public enum TokenState {
    VALID,
    EXPIRED,
    INVALID,
    NOT_FOUND;
    private static final Map<String, TokenState> stringToEnum = new HashMap<>();

    static {
        for (TokenState type : values()) {
            stringToEnum.put(type.toString(), type);
        }
    }

    public static TokenState fromString(String type) {
        if (!stringToEnum.containsKey(type)) {
            return null;
        }
        return stringToEnum.get(type);
    }
}
