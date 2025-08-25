package com.test.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "agent")
@ConfigurationPropertiesScan
public record AgentProperties(int maxActions, int delayBetweenActions) {
}
