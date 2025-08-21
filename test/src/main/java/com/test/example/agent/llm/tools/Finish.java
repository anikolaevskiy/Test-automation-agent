package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Schema used by the LLM to signal the end of a test scenario.
 * <p>
 * When the model emits a call to the {@code finish} tool, the agent interprets
 * it as a termination signal. The record carries the outcome of the scenario so
 * tests can assert on the success flag and description.
 */

@JsonTypeName("finish")
@JsonClassDescription("Stop the test execution")
public record Finish(@JsonPropertyDescription("Is the test execution successful or not")
                     boolean success,
                     @JsonPropertyDescription("Description of the test execution result")
                     String description) {
}
