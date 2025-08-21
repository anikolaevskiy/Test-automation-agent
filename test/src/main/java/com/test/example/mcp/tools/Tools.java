package com.test.example.mcp.tools;

import org.springframework.ai.tool.annotation.Tool;

/**
 * Contract for all Model Context Protocol tools available to the agent.
 * <p>
 * Each method annotated with {@link Tool} represents an action that the LLM can
 * trigger. The methods return a {@link Result} record that is fed back into the
 * LLM to decide on subsequent actions.
 * <p>
 * <strong>Adding a new tool</strong>
 * <ol>
 *   <li>Add a new method to this interface and annotate it with {@link Tool}.</li>
 *   <li>Provide a record describing the tool parameters (see
 *   {@code com.test.example.agent.llm.tools} for examples) and expose it to the
 *   LLM in {@link com.test.example.configuration.openai.OpenAIConfiguration} via
 *   {@code ChatCompletionCreateParams.Builder#addTool}.</li>
 *   <li>Implement the method in a {@link Tools} implementation such as
 *   {@link com.test.example.mcp.tools.PlaywrightTools}.</li>
 *   <li>Ensure your implementation is registered as a bean so that
 *   {@link com.test.example.mcp.gateway.McpGateway} can discover it (see
 *   {@link com.test.example.configuration.mcp.McpConfiguration}).</li>
 * </ol>
 * Following these steps makes the new action available to the agent without
 * further changes in the orchestration logic.
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
