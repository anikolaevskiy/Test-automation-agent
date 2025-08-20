package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;

@JsonTypeName("click_xy")
@JsonClassDescription("Click at specified coordinates")
public class ClickXY {

    @JsonPropertyDescription("X center coordinate")
    public int x;

    @JsonPropertyDescription("Y center coordinate")
    public int y;

    @JsonPropertyDescription("Explanation why this action is needed to be performed")
    public String description;
}
