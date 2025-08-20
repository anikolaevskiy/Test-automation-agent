package com.test.example.agent.llm;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.*;
import com.test.example.agent.Action;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GPTClient implements LLMClient {

    private final OpenAIClient openAIClient;

    private final ChatCompletionCreateParams.Builder params;

    @Override
    public void setUpScenario(String scenario) {
        params.addUserMessage(scenario);
    }

    @Override
    public Action requestNextAction(String screenshot, int width, int height) {
        params.addUserMessageOfArrayOfContentParts(
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
                ));

        var function = openAIClient.chat()
                .completions()
                .create(params.build())
                .choices()
                .getFirst()
                .message()
                .toolCalls()
                .orElseThrow()
                .getFirst()
                .function()
                .orElseThrow()
                .function();

        params.addAssistantMessage(function.name() + " " + function.arguments());

        return new Action(function.name(), function.arguments());
    }

}
