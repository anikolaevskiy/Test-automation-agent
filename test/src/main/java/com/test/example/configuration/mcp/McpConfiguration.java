package com.test.example.configuration.mcp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.Page;
import com.test.example.mcp.tool.McpTool;
import com.test.example.mcp.tool.Tools;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.ToolDefinition;
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
    public ToolCallbackProvider toolsProvider(List<McpTool> mcpTools,
                                              Page page,
                                              ObjectMapper objectMapper) {
        ToolCallback[] callbacks = mcpTools.stream()
                .map(tool -> new ToolCallback() {
                    private final ToolDefinition definition = ToolDefinition.builder()
                            .name(tool.name())
                            .inputType(tool.paramsType())
                            .outputType(Tools.Result.class)
                            .build();

                    @Override
                    public ToolDefinition getToolDefinition() {
                        return definition;
                    }

                    @Override
                    public String call(String arguments) {
                        try {
                            Object params = objectMapper.readValue(arguments, tool.paramsType());
                            var result = tool.execute(page, params);
                            return objectMapper.writeValueAsString(result);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    }
                })
                .toArray(ToolCallback[]::new);

        return () -> callbacks;
    }
}
