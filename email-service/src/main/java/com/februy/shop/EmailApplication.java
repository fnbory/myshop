package com.februy.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author: fnbory
 * @Date: 22/12/2019 下午 8:06
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ImportResource("classpath:dubbo.xml")
@EnableConfigurationProperties
public class EmailApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(EmailApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
        synchronized (EmailApplication.class) {
            while (true) {
                try {
                    EmailApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
