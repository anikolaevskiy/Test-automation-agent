package com.test.example.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.test.example.agent.llm.LLMClient;
import com.test.example.mcp.gateway.McpGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Core orchestrator of the test automation agent.
 * <p>
 * The agent receives a textual scenario, asks the {@link LLMClient} to suggest the
 * next action and delegates its execution to the configured MCP tools through
 * the {@link McpGateway}. The cycle repeats until the LLM returns a
 * {@code finish} action or the configured amount of iterations is reached.
 * <p>
 * The design deliberately separates the decision making (LLM) from the
 * environment interaction (tools) so both sides can be replaced or extended
 * independently. Supplying a different {@link LLMClient} implementation or a
 * new set of tools is enough to adapt the agent to another domain.
 */
@Slf4j
@RequiredArgsConstructor
public class Agent {

    private final McpGateway mcp;

    private final LLMClient llm;

    private final int maxIterations;

    /**
     * Executes the provided test scenario until the LLM signals completion.
     *
     * @param testScenario textual description of the test steps
     * @return final {@link Action} returned by the LLM (typically a finish action)
     * @throws JsonProcessingException if tool results cannot be deserialized
     */
    public Action executeTestScenario(String testScenario) throws JsonProcessingException {

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
