package com.test.example.llm;

public interface LlmClient {

    LlmClient addUserMessage(String message);

    LlmClient addUserMessage(String message, String base64Image);

    FunctionToCall send();

    record FunctionToCall(String name, String args) { }
}
