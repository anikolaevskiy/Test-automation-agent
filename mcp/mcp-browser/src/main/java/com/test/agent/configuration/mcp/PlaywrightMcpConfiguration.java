package com.test.agent.configuration.mcp;
import com.microsoft.playwright.Page;
import com.test.agent.gateway.PlaywrightMcpGateway;
import com.test.agent.tools.PlaywrightMcpTools;
import com.test.example.mcp.McpGateway;
import com.test.example.mcp.McpTools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class PlaywrightMcpConfiguration {

    @Bean
    public ToolCallbackProvider toolsProvider(McpTools mcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(mcpTools)
                .build();
    }

    @Bean
    @Profile("playwright")
    public PlaywrightMcpTools playwrightTools(Page page) {
        return new PlaywrightMcpTools(page);
    }

    @Bean
    @Profile("playwright")
    public McpGateway mcpGateway(ToolCallbackProvider tools) {
        return new PlaywrightMcpGateway(tools);
    }
}

