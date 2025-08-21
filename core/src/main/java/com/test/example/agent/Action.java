package com.test.example.agent;

/**
 * Describes an action suggested by the LLM, including the tool name and serialized arguments.
 *
 * @param functionName     name of the tool function to execute
 * @param functionArguments JSON arguments for the tool function
 */
public record Action(String functionName, String functionArguments) {
}
