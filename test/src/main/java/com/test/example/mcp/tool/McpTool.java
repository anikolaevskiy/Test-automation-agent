package com.test.example.mcp.tool;

import com.microsoft.playwright.Page;

/**
 * Defines a tool available through the Model Context Protocol.
 */
public interface McpTool {

    /**
     * @return the unique tool name exposed to the LLM
     */
    String name();

    /**
     * @return class representing the tool parameters
     */
    Class<?> paramsType();

    /**
     * Executes the tool against the given page using provided parameters.
     *
     * @param page   the Playwright page to operate on
     * @param params deserialized tool parameters
     * @return result information including optional screenshot
     */
    Tools.Result execute(Page page, Object params);
}

