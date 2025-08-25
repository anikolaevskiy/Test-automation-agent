package com.test.example.mcp;

/**
 * Set of low level actions that can be exposed to the LLM via MCP.
 * <p>
 * Implementations are typically thin wrappers over a browser automation library
 * such as Playwright.
 */
public interface McpTools {

    /**
     * Performs a click at the given coordinates.
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @return message describing the result
     */
    String clickXY(int x, int y);

    /**
     * Takes a screenshot of the current page.
     *
     * @return screenshot encoded as Base64
     */
    String screenshot();

}
