package com.test.example.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Settings that control the agent behaviour.
 *
 * @param maxActions          maximum number of tool invocations before the agent stops
 * @param delayBetweenActions pause in milliseconds after each tool call to allow the UI to settle
 */
@ConfigurationProperties(prefix = "agent")
@ConfigurationPropertiesScan
public record AgentProperties(int maxActions, int delayBetweenActions) {
}
