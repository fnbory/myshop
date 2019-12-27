package com.februy.shop.common.cache.impl;

import com.februy.shop.common.cache.RedisCacheManager;
import com.februy.shop.common.properties.CharsetProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;


/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 8:36
 */
@Component
public class RedisCacheManagerImpl implements RedisCacheManager {



    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public boolean putWithExpireTime(String key, String value, long expireTime) {
        final byte[] keyBytes=key.getBytes(CharsetProperties.charset);
        RedisSerializer valueSerializer=redisTemplate.getValueSerializer();
        final byte[] valueBytes=value.getBytes(CharsetProperties.charset);
        boolean result=redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.setEx(keyBytes,expireTime,valueBytes);
                return true;
            }
        });
        return result;
    }

    @Override
    public <T> T get(final String key,Class<T> targetClass) {
        byte[] result=redisTemplate.execute(new RedisCallback<byte[]>() {
            @Override
            public byte[] doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.get(key.getBytes(CharsetProperties.charset));
            }
        });
        if(result==null) return null;
        RedisSerializer valusSerializer=redisTemplate.getValueSerializer();
        return (T)valusSerializer.deserialize(result);
    }

    @Override
    public void delete(String key) {
        redisTemplate.delete(key);
    }
}
