package com.test.example.agent.mcp;

public interface McpGateway {

    McpResponse call(String toolName, String jsonArgs);
}
