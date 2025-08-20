package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Tool parameters describing a click at certain coordinates.
 */
@JsonTypeName("click_xy")
@JsonClassDescription("Click at specified coordinates")
public class ClickXY {

    /** X center coordinate. */
    @JsonPropertyDescription("X center coordinate")
    public int x;

    /** Y center coordinate. */
    @JsonPropertyDescription("Y center coordinate")
    public int y;

    /**
     * Explanation why this action is needed to be performed.
     */
    @JsonPropertyDescription("Explanation why this action is needed to be performed")
    public String description;
}
