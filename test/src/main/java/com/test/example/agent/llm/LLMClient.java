package com.test.example.agent.llm;

import com.test.example.agent.Action;

/**
 * Abstraction of a language model capable of deciding the next action based on a screenshot.
 */
public interface LLMClient {

    /**
     * Provides the initial test scenario to the LLM.
     *
     * @param scenario description of the test steps
     */
    void setUpScenario(String scenario);

    /**
     * Requests the next action to perform based on the screenshot of the page.
     *
     * @param screenshot Base64 encoded image of the current page
     * @param width      width of the screenshot
     * @param height     height of the screenshot
     * @return the action the LLM wants to execute next
     */
    Action requestNextAction(String screenshot, int width, int height);
}
