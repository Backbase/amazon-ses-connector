package com.backbase.productled;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@Slf4j
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
        }catch (InterruptedException e) {
            log.warn("Interrupted!", e);
            Thread.currentThread().interrupt();
        } catch (Exception e) {
            log.error("Exception:" , e);
            exitCode = 1;
        }
        System.exit(exitCode);
    }

}