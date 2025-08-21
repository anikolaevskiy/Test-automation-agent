package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Schema describing a click at certain coordinates.
 * <p>
 * Instances of this record are produced by the LLM when it decides to invoke
 * the {@code click_xy} tool. The annotations allow Spring AI to expose the
 * schema to the model so it understands the expected arguments.
 */
@JsonTypeName("click_xy")
@JsonClassDescription("Click at specified coordinates")
public record ClickXY(@JsonPropertyDescription("X center coordinate") int x,
                      @JsonPropertyDescription("Y center coordinate") int y,
                      @JsonPropertyDescription("Explanation why this action is needed to be performed") String description) {
}
