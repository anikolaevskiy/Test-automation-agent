package com.test.example.agent.llm;

import com.test.example.agent.Action;
import com.test.example.agent.Agent;

/**
 * Abstraction of a language model capable of deciding the next action based on
 * a screenshot of the current browser state.
 * <p>
 * Implementations encapsulate the communication details with a particular model
 * provider. This allows the {@link Agent} to remain
 * provider-agnostic and makes it straightforward to plug in alternative LLMs
 * (local or remote) by implementing this interface.
 */
public interface LlmClient {

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
