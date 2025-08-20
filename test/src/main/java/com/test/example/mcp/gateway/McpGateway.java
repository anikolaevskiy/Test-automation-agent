package com.test.example.mcp.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.example.mcp.tools.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class McpGateway {

    private final ToolCallbackProvider provider;

    public Tools.Result call(String toolName, String jsonArgs) throws JsonProcessingException {
        var tool = Arrays.stream(provider.getToolCallbacks())
                .filter(t -> t.getToolDefinition().name().equals(toolName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No MCP tool: " + toolName));

        var result = tool.call(jsonArgs);

        return new ObjectMapper().readValue(result, Tools.Result.class);
    }
}
