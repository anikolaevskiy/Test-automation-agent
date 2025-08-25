package com.test.example.agent;

/**
 * Facade orchestrating the conversation between the LLM and the environment.
 * <p>
 * Implementations maintain the dialog state and decide whether the scenario
 * should continue based on the LLM response or predefined safety limits.
 */
public interface QAgent {

    /**
     * Starts a new test scenario by providing the initial instructions to the model.
     *
     * @param scenario textual description of the desired behaviour
     * @return initial state containing the first screenshot
     */
    State start(String scenario);

    /**
     * Requests the next action from the model using the latest screenshot.
     *
     * @param screenshot base64 encoded screenshot of the current page
     * @return updated state describing the chosen action
     */
    State nextAction(String screenshot);

    /**
     * Immutable snapshot of the agent state after each step.
     *
     * @param proceed     whether the agent should continue the loop
     * @param successful  final result of the scenario
     * @param screenshot  latest screenshot returned by the tool
     * @param message     human readable description of the last action
     */
    record State(boolean proceed, boolean successful, String screenshot, String message) { }
}
