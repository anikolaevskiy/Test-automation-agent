package com.test.example.configuration.comman;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Configuration properties that control the behaviour of the {@link com.test.example.agent.Agent}.
 *
 * @param maxIterations maximum number of tool invocations before the agent stops
 */
@PropertySource("classpath:agent.properties")
@ConfigurationProperties(prefix = "agent")
@ConfigurationPropertiesScan
public record AgentProperties(int maxIterations) {
}
