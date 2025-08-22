package com.test.example.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.example.agent.llm.LlmClient;
import com.test.example.agent.mcp.McpGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Agent {

    private final McpGateway mcp;

    private final LlmClient llm;

    private final int maxIterations;

    /**
     * Executes the provided test scenario until the LLM signals completion.
     *
     * @param testScenario textual description of the test steps
     * @return final {@link Action} returned by the LLM (typically a finish action)
     */
    public Action executeTestScenario(String testScenario) {

        log.info("Executing test scenario:\n{}", testScenario);
        llm.setUpScenario(testScenario);

        var initScreen = mcp.call("screenshot", "{}");

        var action = llm.requestNextAction(initScreen.screenshot(), initScreen.width(), initScreen.height());
        log.info("Initial action: {} {}", action.functionName(), action.functionArguments());

        var iteration = 0;
        while (!action.functionName().equals("finish") && iteration < maxIterations) {

            var actionResult = mcp.call(action.functionName(), action.functionArguments());
            log.info("Action executed with result: {}", actionResult.message());

            log.info("Defining next action based on the result of the previous action...");
            action = llm.requestNextAction(actionResult.screenshot(), actionResult.width(), actionResult.height());
            log.info("Next action: {} {}", action.functionName(), action.functionArguments());

            iteration++;
        }

        return action;
    }

}
