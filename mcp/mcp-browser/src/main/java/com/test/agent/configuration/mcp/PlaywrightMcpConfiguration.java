package com.test.agent.configuration.mcp;

import com.microsoft.playwright.Page;
import com.test.agent.configuration.playwright.PlaywrightConfiguration;
import com.test.agent.gateway.PlaywrightMcpGateway;
import com.test.agent.tools.PlaywrightMcpTools;
import com.test.example.mcp.McpGateway;
import com.test.example.mcp.McpTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Spring configuration that exposes Playwright based MCP tools and gateway.
 */
@Configuration
@Import(PlaywrightConfiguration.class)
public class PlaywrightMcpConfiguration {

    /**
     * Registers methods of {@link McpTools} as Spring AI tool callbacks.
     */
    @Bean
    public ToolCallbackProvider toolsProvider(McpTools mcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(mcpTools)
                .build();
    }

    /**
     * Creates the default Playwright tools implementation.
     */
    @Bean
    @Profile("playwright")
    public PlaywrightMcpTools playwrightTools(Page page, Page.ScreenshotOptions screenshotOptions) {
        return new PlaywrightMcpTools(page, screenshotOptions);
    }

    /**
     * Exposes the {@link McpGateway} that delegates to Spring AI tool callbacks.
     */
    @Bean
    @Profile("playwright")
    public McpGateway mcpGateway(ToolCallbackProvider tools) {
        return new PlaywrightMcpGateway(tools);
    }
}
