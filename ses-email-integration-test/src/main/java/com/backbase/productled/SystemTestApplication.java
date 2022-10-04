package com.backbase.productled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SystemTestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SystemTestApplication.class);
        try {
            context.getBean(SendEmailTest.class).sendEmailTest();
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}