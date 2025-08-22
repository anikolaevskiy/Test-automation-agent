package com.test.agent.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.example.agent.mcp.McpGateway;
import com.test.example.agent.mcp.McpResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.ai.tool.ToolCallbackProvider;

import java.util.Arrays;

@RequiredArgsConstructor
public class PlaywrightMcpGateway implements McpGateway {

    private final ToolCallbackProvider provider;
    private final ObjectMapper objectMapper;

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public McpResponse call(String toolName, String jsonArgs) {
        var tool = Arrays.stream(provider.getToolCallbacks())
                .filter(t -> t.getToolDefinition().name().equals(toolName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No MCP tool: " + toolName));

        var result = tool.call(jsonArgs);

        return objectMapper.readValue(result, McpResponse.class);
    }
}
