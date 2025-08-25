package com.test.example.llm;

/**
 * Abstraction over a conversational Large Language Model.
 * <p>
 * Implementations collect messages and submit them to the model, returning the
 * tool call proposed by the LLM.
 */
public interface LlmClient {

    /**
     * Adds a plain text user message to the conversation.
     *
     * @param message content to send
     * @return client for fluent usage
     */
    LlmClient addUserMessage(String message);

    /**
     * Adds a user message that contains both text and a base64 encoded image.
     *
     * @param message     textual part of the message
     * @param base64Image image to provide as additional context
     * @return client for fluent usage
     */
    LlmClient addUserMessage(String message, String base64Image);

    /**
     * Sends the accumulated conversation to the model and returns the next tool call.
     *
     * @return function name and arguments suggested by the LLM
     */
    FunctionToCall send();

    /**
     * Descriptor of a tool call suggested by the model.
     *
     * @param name name of the tool to execute
     * @param args JSON encoded arguments for the tool
     */
    record FunctionToCall(String name, String args) { }
}
