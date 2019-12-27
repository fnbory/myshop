package com.februy.shop.common.config;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 4:52
 */
@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.messages")
public class InternationalizationConfig {

    private String basename;

    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageSource(){
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(basename);
        return  messageSource;
    }

}
