package com.test.example.configuration.mcp;

import com.microsoft.playwright.Page;
import com.test.example.mcp.tools.PlaywrightTools;
import com.test.example.mcp.tools.Tools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Spring configuration for registering MCP tools and providers.
 */
@Configuration
public class McpConfiguration {

    /**
     * Registers MCP tools so they can be discovered by the agent.
     *
     * @param tools implementation of the tools interface
     * @return provider that exposes the tools
     */
    @Bean
    public ToolCallbackProvider toolsProvider(Tools tools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(tools)
                .build();
    }

    /**
     * Creates a Playwright-backed tools implementation when the "playwright" profile is active.
     *
     * @param page Playwright page used by the tools
     * @return tools instance backed by Playwright
     */
    @Bean
    @Profile("playwright")
    public PlaywrightTools playwrightTools(Page page) {
        return new PlaywrightTools(page);
    }
}
