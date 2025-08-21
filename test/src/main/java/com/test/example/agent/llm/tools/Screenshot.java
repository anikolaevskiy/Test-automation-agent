package com.test.example.agent.llm.tools;

import com.fasterxml.jackson.annotation.JsonClassDescription;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * Tool parameters for taking a screenshot of the current page.
 */
@JsonTypeName("screenshot")
@JsonClassDescription("Take a screenshot of the page")
public class Screenshot {
}

