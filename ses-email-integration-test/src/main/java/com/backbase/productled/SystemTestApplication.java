package com.backbase.productled;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SystemTestApplication {

    private static int exitCode = 0;

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> exitCode;
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SystemTestApplication.class);
        try {
            context.getBean(SendEmailTest.class).sendEmailTest();
            exitCode = 0;
        } catch (Exception e) {
            e.printStackTrace();
            exitCode = 1;
        }
        System.exit(exitCode);
    }

}