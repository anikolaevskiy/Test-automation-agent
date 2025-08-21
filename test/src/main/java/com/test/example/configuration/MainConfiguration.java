package com.test.example.configuration;

import com.test.example.agent.Agent;
import com.test.example.agent.llm.LLMClient;
import com.test.example.configuration.playwright.PlaywrightConfiguration;
import com.test.example.configuration.comman.CommonConfiguration;
import com.test.example.mcp.gateway.McpGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Aggregates all configuration classes required to run the agent in tests.
 */
@Configuration
@ComponentScan(basePackages = "com.test.example")
@Import({
        AgentConfiguration.class,
        PlaywrightConfiguration.class,
        CommonConfiguration.class
})
public class MainConfiguration {
}
