package com.backbase.productled.config;

import com.backbase.buildingblocks.multitenancy.Tenant;
import com.backbase.buildingblocks.multitenancy.TenantProvider;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Configuration for communication service.
 */
@Configuration
@EnableScheduling
public class CommunicationConfiguration {

    private static final String MT_ENABLED = "backbase.multi-tenancy.enabled";

    /**
     * @return instance of the mapper builder to be used for marshalling
     */
    @Bean
    public Jackson2ObjectMapperBuilder jacksonBuilder() {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        builder.modules(new JavaTimeModule());
        // The Include.NON_NULL setting is needed for a bug in the OpenAPI generator: https://github.com/OpenAPITools/openapi-generator/issues/3796
        // It also means that we cannot use the new ParameterNamesModule(JsonCreator.Mode.PROPERTIES) setting in our mapper
        builder.serializationInclusion(Include.NON_NULL);
        return builder;
    }

    /**
     * Creates a default TenantProvider in case missing.
     * @return TenantProvider
     */
    @Bean
    @ConditionalOnProperty(name = MT_ENABLED, havingValue = "false", matchIfMissing = true)
    public TenantProvider tenantProvider() {
        return new TenantProvider() {
            @Override
            public Optional<Tenant> findTenantById(String s) {
                return Optional.empty();
            }

            @Override
            public Collection<Tenant> getTenants() {
                return Collections.emptyList();
            }
        };
    }
}
