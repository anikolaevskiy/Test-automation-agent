package com.test.agent.configuration.mcp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.test.agent.gateway.PlaywrightMcpGateway;
import com.test.agent.tools.PlaywrightMcpTools;
import com.test.example.agent.mcp.McpGateway;
import com.test.example.agent.mcp.McpTools;
import com.test.example.configuration.common.CommonConfiguration;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(CommonConfiguration.class)
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
    public McpGateway mcpGateway(ToolCallbackProvider tools, ObjectMapper objectMapper) {
        return new PlaywrightMcpGateway(tools, objectMapper);
    }
}
