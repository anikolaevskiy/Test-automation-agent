package com.test.example.configuration.mcp;

import com.test.example.mcp.tools.Tools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration that exposes {@link Tools} implementations to Spring AI.
 * <p>
 * The configuration wires the {@link ToolCallbackProvider} used by the
 * {@link com.test.example.mcp.gateway.McpGateway}. Custom tool implementations
 * can be registered here as beans. When introducing a new {@link Tools}
 * implementation simply expose it as a bean and this provider will
 * automatically make all annotated methods available to the
 * {@link com.test.example.agent.Agent}.
 */
@Configuration
public class McpConfiguration {

    /**
     * Registers MCP tools so they can be discovered by the agent.
     * <p>
     * When introducing a new {@link Tools} implementation simply expose it as a
     * bean and this provider will automatically make all annotated methods
     * available to the {@link com.test.example.agent.Agent}.
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
