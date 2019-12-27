package com.februy.shop.commonweb.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 1:36
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthenticationProperties {

    private String secretKey;
    private Integer tokenExpireTime;
    private Integer captchaExpireTime;
    private Integer activationCodeExpireTime;
    private Integer forgetNameCodeExpireTime;

    public static final String AUTH_HEADER = "Authentication";
    public static final String USER_ID = "id";
    public static final String LOGIN_URL = "/tokens";
    public static final String EXCEPTION_ATTR_NAME = "BaseRestException";
}
