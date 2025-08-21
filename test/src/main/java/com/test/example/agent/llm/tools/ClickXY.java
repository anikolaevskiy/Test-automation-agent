package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Tool parameters describing a click at certain coordinates.
 */
@JsonTypeName("click_xy")
@JsonClassDescription("Click at specified coordinates")
public record ClickXY(@JsonPropertyDescription("X center coordinate") int x,
                      @JsonPropertyDescription("Y center coordinate") int y,
                      @JsonPropertyDescription("Explanation why this action is needed to be performed") String description) {
}
