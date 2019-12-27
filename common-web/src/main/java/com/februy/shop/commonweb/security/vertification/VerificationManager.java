package com.februy.shop.commonweb.security.vertification;

/**
 * @Author: fnbory
 * @Date: 21/12/2019 下午 8:29
 */
public interface VerificationManager {

    void createVerificationCode(String key, String value, long expireTime);

    boolean checkVerificationCode(String key, String value);

    void deleteVerificationCode(String key);
}
