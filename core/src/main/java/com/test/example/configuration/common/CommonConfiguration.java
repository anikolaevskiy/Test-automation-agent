package com.test.example.configuration.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.support.RetryTemplate;

/**
 * Provides beans shared across the application.
 */
@Configuration
@EnableConfigurationProperties(RetryProperties.class)
public class CommonConfiguration {

    /**
     * Creates a default Jackson {@link ObjectMapper}.
     *
     * @return new mapper instance
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public RetryTemplate retry(RetryProperties properties) {
        return RetryTemplate.builder()
                .maxAttempts(properties.maxAttempts())
                .fixedBackoff(properties.delay())
                .build();
    }
}
