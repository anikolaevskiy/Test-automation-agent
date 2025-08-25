package com.test.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:agent.properties")
@ConfigurationProperties(prefix = "agent")
@ConfigurationPropertiesScan
public record AgentProperties(int maxActions, int delayBetweenActions) {
}
