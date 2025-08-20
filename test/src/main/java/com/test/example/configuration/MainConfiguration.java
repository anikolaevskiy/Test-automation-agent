package com.test.example.configuration;

import com.test.example.configuration.llm.LLMConfiguration;
import com.test.example.configuration.mcp.McpConfiguration;
import com.test.example.configuration.playwright.PlaywrightConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.test.example")
@Import({
        PlaywrightConfiguration.class,
        McpConfiguration.class,
        LLMConfiguration.class
})
public class MainConfiguration {
}
