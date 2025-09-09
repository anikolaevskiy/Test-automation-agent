package com.test.agent;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.*;
import com.test.example.llm.LlmClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.support.RetryTemplate;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * {@link LlmClient} implementation backed by the official OpenAI SDK.
 * <p>
 * The client accumulates user messages and submits them as a chat completion
 * request. The first tool call returned by the model is exposed to the agent.
 */
@Slf4j
@AllArgsConstructor
public class OpenAiClient implements LlmClient {

    private final OpenAIClient openAIClient;

    private final RetryTemplate retry;

    private ChatCompletionCreateParams params;

    @Override
    public LlmClient addUserMessage(String message) {
        params = params.toBuilder().addUserMessage(message).build();
        return this;
    }

    @Override
    public LlmClient addUserMessage(String message, String base64Image) {
        params = params.toBuilder().addUserMessageOfArrayOfContentParts(
                List.of(
                        ChatCompletionContentPart.ofText(
                                ChatCompletionContentPartText.builder()
                                        .text(String.format(message + "\nReturn only function to call. Do not include and other content into response."))
                                        .build()),

                        ChatCompletionContentPart.ofImageUrl(
                                ChatCompletionContentPartImage.builder()
                                        .imageUrl(ChatCompletionContentPartImage.ImageUrl.builder()
                                                .url("data:image/png;base64," + base64Image)
                                                .detail(ChatCompletionContentPartImage.ImageUrl.Detail.HIGH)
                                                .build())
                                        .build())
                )).build();

        return this;
    }

    /**
     * Sends the accumulated conversation to the OpenAI service and returns the
     * tool call suggested by the model.
     */
    @Override
    public FunctionToCall send() {
        return retry.execute(context -> openAIClient.chat()
                .completions()
                .create(params)
                .choices()
                .stream()
                .map(ChatCompletion.Choice::message)
                .peek(message -> message.content().ifPresent(content -> log.info("LLM response content: {}", content)))
                .map(ChatCompletionMessage::toolCalls)
                .map(o -> o.orElseThrow(() -> new IllegalStateException("No function call in response from LLM")))
                .map(Collection::stream)
                .flatMap(stream -> stream.map(ChatCompletionMessageToolCall::function))
                .map(o -> o.orElseThrow(() -> new IllegalStateException("No function call in response from LLM")))
                .map(ChatCompletionMessageFunctionToolCall::function)
                // include the tool call in the conversation for transparency
                .peek(function -> params = params.toBuilder().addAssistantMessage(function.name() + " " + function.arguments()).build())
                .map(function -> new FunctionToCall(function.name(), function.arguments()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No function call in response from LLM")));
    }
}
