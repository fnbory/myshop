package com.februy.shop.commonweb.security.vertification.impl;


import com.februy.shop.common.cache.RedisCacheManager;
import com.februy.shop.commonweb.security.vertification.VerificationManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 8:31
 */
@Component
@Slf4j
public class VerificationManagerImpl implements VerificationManager {


    @Autowired
    private RedisCacheManager redisCacheManager;
    @Override
    public void createVerificationCode(String key, String value, long expireTime) {
        log.info("creating...");
        log.info("expireTime:{}",expireTime);
        redisCacheManager.putWithExpireTime(key,value,expireTime);
    }

    @Override
    public boolean checkVerificationCode(String key, String value) {
        String realvalue=redisCacheManager.get(key,String.class);
        if(realvalue==null||!value.equals(realvalue)) return false;
        return true;
    }

    @Override
    public void deleteVerificationCode(String key) {
        redisCacheManager.delete(key);
    }

}
