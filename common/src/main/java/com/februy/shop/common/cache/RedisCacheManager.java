package com.februy.shop.common.cache;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 8:34
 */
public interface RedisCacheManager {


    boolean putWithExpireTime(String key, String value, long expireTime);

    <T> T get(final String key,Class<T> targetClass);

    void delete(String key);
}
