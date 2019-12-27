package com.februy.shop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author: fnbory
 * @Date: 27/12/2019 下午 1:33
 */
@SpringBootApplication
@EnableConfigurationProperties
@Slf4j
@ImportResource("classpath:dubbo.xml")
public class OrderApplication  implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(OrderApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
        synchronized (OrderApplication.class) {
            while (true) {
                try {
                    OrderApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }

    @Override
    public void run(String... strings) {
    }
}
