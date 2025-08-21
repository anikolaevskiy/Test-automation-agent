package com.test.example.configuration.mcp;

import com.test.example.mcp.tool.McpTool;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Spring configuration for registering MCP tools and providers.
 */
@Configuration
public class McpConfiguration {

    /**
     * Registers MCP tool beans so they can be discovered by the agent.
     *
     * @param mcpTools list of available tools
     * @return provider that exposes the tools
     */
    @Bean
    public ToolCallbackProvider toolsProvider(List<McpTool> mcpTools) {
        return MethodToolCallbackProvider.builder()
                .toolBeans(mcpTools)
                .build();
    }
}
