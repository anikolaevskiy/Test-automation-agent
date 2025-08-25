package com.test.agent.gateway;

import com.test.example.mcp.McpGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.ToolCallbackProvider;

import java.util.Arrays;

@RequiredArgsConstructor
public class PlaywrightMcpGateway implements McpGateway {

    private final ToolCallbackProvider provider;

    @Override
    public String call(String toolName, String jsonArgs) {
        var tool = Arrays.stream(provider.getToolCallbacks())
                .filter(t -> t.getToolDefinition().name().equals(toolName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No MCP tool: " + toolName));

        return tool.call(jsonArgs);
    }

    @Override
    public String click(String args) {
        return call("click_xy", args);
    }

    @Override
    public String screenshot() {
        return call("screenshot", "{}");
    }
}