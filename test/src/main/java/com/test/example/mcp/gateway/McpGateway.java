package com.test.example.mcp.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.example.mcp.tools.Tools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * Gateway for invoking MCP tools and converting their JSON responses to {@link Tools.Result}.
 */
@Component
@RequiredArgsConstructor
public class McpGateway {

    private final ToolCallbackProvider provider;

    /**
     * Calls an MCP tool by its name with the provided JSON arguments.
     *
     * @param toolName the name of the tool to execute
     * @param jsonArgs JSON representation of the tool arguments
     * @return the parsed {@link Tools.Result} returned by the tool
     * @throws JsonProcessingException if the returned JSON cannot be deserialized
     */
    public Tools.Result call(String toolName, String jsonArgs) throws JsonProcessingException {
        var tool = Arrays.stream(provider.getToolCallbacks())
                .filter(t -> t.getToolDefinition().name().equals(toolName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No MCP tool: " + toolName));

        var result = tool.call(jsonArgs);

        return new ObjectMapper().readValue(result, Tools.Result.class);
    }
}
