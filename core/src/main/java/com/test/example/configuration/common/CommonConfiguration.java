package com.test.example.configuration.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides beans shared across the application.
 */
@Configuration
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
}
