package com.test.example.configuration;

import com.test.example.configuration.llm.LLMConfiguration;
import com.test.example.configuration.mcp.McpConfiguration;
import com.test.example.configuration.playwright.PlaywrightConfiguration;
import com.test.example.configuration.CommonConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Aggregates all configuration classes required to run the agent in tests.
 */
@Configuration
@ComponentScan(basePackages = "com.test.example")
@Import({
        PlaywrightConfiguration.class,
        McpConfiguration.class,
        LLMConfiguration.class,
        CommonConfiguration.class
})
public class MainConfiguration {
}
