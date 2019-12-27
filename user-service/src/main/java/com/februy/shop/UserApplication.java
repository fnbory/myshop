package com.februy.shop;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * @Author: fnbory
 * @Date: 20/12/2019 下午 9:12
 */

@SpringBootApplication
@ImportResource({"classpath:dubbo.xml"})
@EnableConfigurationProperties
@ComponentScan({"com.februy"})
public class UserApplication implements CommandLineRunner {

    public static void main(String[] args)  {
        SpringApplication app = new SpringApplication(UserApplication.class);
        app.setWebEnvironment(false);
        app.run(args);
        synchronized (UserApplication.class) {
            while (true) {
                try {
                    UserApplication.class.wait();
                } catch (Throwable e) {
                }
            }
        }
    }

    @Override
    public void run(String... args) {
    }
}
