package com.test.example.configuration;

import com.test.example.configuration.playwright.PlaywrightConfiguration;
import com.test.example.configuration.common.CommonConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Aggregates all configuration classes required to run the agent in tests.
 * <p>
 * This class acts as the entry point for the Spring Boot test context. It
 * imports the wiring for the agent, tools and common utilities. Custom test
 * setups can import additional configurations or replace existing ones to adapt
 * the environment.
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
