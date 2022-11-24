package com.backbase.communication.config;

import com.backbase.buildingblocks.backend.internalrequest.DefaultInternalRequestContext;
import com.backbase.buildingblocks.backend.internalrequest.InternalRequestContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {

    @Bean
    @ConditionalOnMissingBean(InternalRequestContext.class)
    public InternalRequestContext internalRequestContext() {
        return new DefaultInternalRequestContext();
    }
}
