package com.test.example.mcp.tools;

import org.springframework.ai.tool.annotation.Tool;

/**
 * Defines MCP tools that can be invoked by the agent to interact with the browser.
 */
public interface Tools {

    /**
     * Clicks on the page at the specified coordinates.
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @return a {@link Result} containing a screenshot after the click
     */
    @Tool(name = "click_xy")
    Result clickXY(int x, int y);

    /**
     * Takes a screenshot of the current page.
     *
     * @return a {@link Result} containing the screenshot
     */
    @Tool(name = "screenshot")
    Result screenshot();

    /**
     * Result returned by MCP tools.
     *
     * @param message    human-readable description of the action
     * @param screenshot Base64 encoded screenshot
     * @param width      screenshot width
     * @param height     screenshot height
     */
    record Result(String message, String screenshot, int width, int height) { }
}
