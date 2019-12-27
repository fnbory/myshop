package com.februy.shop.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: fnbory
 * @Date: 23/12/2019 下午 4:05
 */
@Component
@ConfigurationProperties
@PropertySource("classpath:cos.properties")
@Getter
@Setter
@Slf4j
public class CosProperties {
    private String appId;
    private String secretId;
    private String secretKey;
}
