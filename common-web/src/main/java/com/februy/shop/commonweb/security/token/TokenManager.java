package com.februy.shop.commonweb.security.token;

import com.februy.shop.commonweb.security.domin.TokenCheckResult;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 3:00
 */
public interface TokenManager {

    String createToken(String username);

    TokenCheckResult checkToken(String token);

    void deleteToken(String username);
}
