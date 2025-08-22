package com.test.agent.llm;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.*;
import com.test.example.agent.Action;
import com.test.example.agent.llm.LlmClient;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * {@link LlmClient} implementation that delegates reasoning to the OpenAI GPT
 * model.
 * <p>
 * The client keeps track of the conversation with the model, sends page
 * screenshots and extracts the tool call the model wants to perform. Different
 * LLM providers can be supported by creating alternative {@link LlmClient}
 * implementations following the same pattern.
 */
@AllArgsConstructor
public class GPTClient implements LlmClient {

    private final OpenAIClient openAIClient;

    private ChatCompletionCreateParams params;

    /**
     * Adds the scenario description to the conversation context.
     *
     * @param scenario textual test scenario
     */
    @Override
    public void setUpScenario(String scenario) {
        params = params.toBuilder().addUserMessage(scenario).build();
    }

    /**
     * Sends the screenshot to the LLM and returns the next action.
     *
     * @param screenshot Base64 encoded screenshot
     * @param width      screenshot width
     * @param height     screenshot height
     * @return action suggested by the LLM
     */
    @Override
    public Action requestNextAction(String screenshot, int width, int height) {
        params = params.toBuilder().addUserMessageOfArrayOfContentParts(
                List.of(
                        ChatCompletionContentPart.ofText(
                                ChatCompletionContentPartText.builder()
                                        .text(String.format("Screenshot of the page, size %d x %d", width, height))
                                        .build()),

                        ChatCompletionContentPart.ofImageUrl(
                                ChatCompletionContentPartImage.builder()
                                        .imageUrl(ChatCompletionContentPartImage.ImageUrl.builder()
                                                .url("data:image/png;base64," + screenshot)
                                                .detail(ChatCompletionContentPartImage.ImageUrl.Detail.HIGH)
                                                .build())
                                        .build())
                )).build();


        var action =  openAIClient.chat()
                .completions()
                .create(params)
                .choices()
                .stream()
                .map(ChatCompletion.Choice::message)
                .map(ChatCompletionMessage::toolCalls)
                .map(Optional::orElseThrow)
                .map(Collection::stream)
                .flatMap(stream -> stream.map(ChatCompletionMessageToolCall::function))
                .map(Optional::orElseThrow)
                .map(ChatCompletionMessageFunctionToolCall::function)
                .peek(function -> params = params.toBuilder().addAssistantMessage(function.name() + " " + function.arguments()).build())
                .map(function -> new Action(function.name(), function.arguments()))
                .findFirst()
                .orElseThrow();

        params = params.toBuilder().addAssistantMessage(action.functionName() + " " + action.functionArguments()).build();

        return action;

    }

}
