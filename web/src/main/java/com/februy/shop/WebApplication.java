package com.februy.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 9:12
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ImportResource({"classpath:dubbo.xml"})
@EnableConfigurationProperties
public class WebApplication {


    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class,args);

    }
}
