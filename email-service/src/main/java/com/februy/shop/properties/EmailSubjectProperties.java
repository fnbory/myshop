package com.februy.shop.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 2:13
 */
@ConfigurationProperties
@PropertySource("classpath:email-subject.properties")
@Component
@Getter
@Setter
@Slf4j
public class EmailSubjectProperties {
    private Map<String,String> subjects;
    public String getProperty(String key) {
        return subjects.get(key);
    }
}
