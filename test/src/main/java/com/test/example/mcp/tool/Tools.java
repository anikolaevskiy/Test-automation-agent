package com.test.example.mcp.tool;

/**
 * Utility holder for tool results.
 */
public final class Tools {

    private Tools() {
    }

    /**
     * Result returned by MCP tools.
     *
     * @param message    human-readable description of the action
     * @param screenshot Base64 encoded screenshot
     * @param width      screenshot width
     * @param height     screenshot height
     */
    public record Result(String message, String screenshot, int width, int height) { }
}

