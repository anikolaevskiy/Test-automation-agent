package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Tool parameters for signalling the end of a test scenario.
 */

@JsonTypeName("finish")
@JsonClassDescription("Stop the test execution")
public record Finish(@JsonPropertyDescription("Is the test execution successful or not")
                     boolean success,
                     @JsonPropertyDescription("Description of the test execution result")
                     String description) {
}
