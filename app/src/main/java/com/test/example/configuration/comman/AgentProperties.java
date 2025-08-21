package com.test.example.configuration.comman;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration properties that control the behaviour of the {@link com.test.example.agent.Agent}.
 *
 * @param maxIterations maximum number of tool invocations before the agent stops
 */
@PropertySource("classpath:agent.properties")
@ConfigurationProperties(prefix = "agent")
public record AgentProperties(int maxIterations) {
}
