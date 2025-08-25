package com.test.example.mcp;

public interface McpGateway {

    String call(String toolName, String jsonArgs);

    String click(String args);

    String screenshot();
}
