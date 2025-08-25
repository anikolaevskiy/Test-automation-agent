package com.test.example.mcp;

/**
 * Entry point for invoking MCP tools.
 * <p>
 * Implementations translate tool calls into concrete technology actions, e.g.
 * executing Playwright commands. A dedicated method for each commonly used tool
 * allows for simpler agent code while still supporting generic calls.
 */
public interface McpGateway {

    /**
     * Invokes a named tool with JSON arguments.
     *
     * @param toolName name of the tool to execute
     * @param jsonArgs serialized arguments
     * @return tool result as returned by the implementation
     */
    String call(String toolName, String jsonArgs);

    /**
     * Convenience method for the {@code click_xy} tool.
     *
     * @param args JSON arguments describing the click
     * @return result of the tool execution
     */
    String click(String args);

    /**
     * Convenience method for the {@code screenshot} tool.
     *
     * @return Base64 encoded screenshot
     */
    String screenshot();
}
