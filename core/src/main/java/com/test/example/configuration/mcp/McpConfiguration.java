package com.test.example.configuration.mcp;

import com.microsoft.playwright.Page;
import com.test.example.mcp.tools.PlaywrightTools;
import com.test.example.mcp.tools.Tools;
import com.test.example.agent.Agent;
import com.test.example.mcp.gateway.McpGateway;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Spring configuration that exposes {@link Tools} implementations to Spring AI.
 * <p>
 * The configuration wires the {@link ToolCallbackProvider} used by the
 * {@link McpGateway}. Custom tool implementations
 * can be registered here as beans. The provided {@code playwright} profile
 * showcases how to supply a Playwright-based set of tools; additional profiles
 * or beans can be added to integrate with other automation backends.
 */
@Configuration
public class McpConfiguration {

    /**
     * Registers MCP tools so they can be discovered by the agent.
     * <p>
     * When introducing a new {@link Tools} implementation simply expose it as a
     * bean and this provider will automatically make all annotated methods
     * available to the {@link Agent}.
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
    public Tools playwrightTools(Page page) {
        return new PlaywrightTools(page);
    }
}
