package com.backbase.productled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import javax.mail.MessagingException;
import java.io.IOException;

@SpringBootApplication
public class SystemTestApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SystemTestApplication.class);
        try {
            context.getBean(SendEmailTest.class).sendEmailTest();
        } catch (Exception e) {
            e.printStackTrace();
        }
        context.close();
    }
}