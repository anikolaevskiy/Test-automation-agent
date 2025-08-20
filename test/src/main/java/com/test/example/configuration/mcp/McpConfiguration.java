package com.test.example.configuration.mcp;

import com.microsoft.playwright.Page;
import com.test.example.mcp.tools.PlaywrightTools;

import com.test.example.mcp.tools.Tools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Spring configuration for registering MCP tools and providers.
 */
@Configuration
@Import(PlaywrightTools.class)
public class McpConfiguration {


    /**
     * Creates {@link PlaywrightTools} when the "playwright" profile is active.
     *
     * @param page Playwright page instance
     * @return configured tools implementation
     */
    @Bean
    @Profile("playwright")
    public PlaywrightTools playwrightTools(Page page) {
        return new PlaywrightTools(page);
    }

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
}
