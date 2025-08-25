package com.test.example.configuration.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "retry")
@ConfigurationPropertiesScan
public record RetryProperties(int maxAttempts, int delay) {
}
