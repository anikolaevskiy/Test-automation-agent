package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;

/**
 * Tool parameters for signalling the end of a test scenario.
 */
@JsonTypeName("finish")
@JsonClassDescription("Stop the test execution")
@Data
public class Finish {

    /** Indicates whether the scenario succeeded. */
    @JsonPropertyDescription("Is the test execution successful or not")
    private boolean success;

    /** Description of the test execution result. */
    @JsonPropertyDescription("Description of the test execution result")
    private String description;
}
