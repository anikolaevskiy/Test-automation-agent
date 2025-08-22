package com.test.example.configuration.comman;

import com.test.example.agent.Agent;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration properties that control the behaviour of the {@link Agent}.
 *
 * @param maxIterations maximum number of tool invocations before the agent stops
 */
@PropertySource("classpath:agent.properties")
@ConfigurationProperties(prefix = "agent")
@ConfigurationPropertiesScan
public record AgentProperties(int maxIterations) {
}
