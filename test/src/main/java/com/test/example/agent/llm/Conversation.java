package com.test.example.agent.llm;

import com.openai.models.ChatModel;
import com.openai.models.chat.completions.*;
import com.test.example.agent.llm.tools.ClickXY;
import com.test.example.agent.llm.tools.Finish;
import com.test.example.configuration.openai.OpenAIProperties;

import java.util.List;

class Conversation {

    private final ChatCompletionCreateParams.Builder builder;

    Conversation(OpenAIProperties properties, String scenario) {
        this.builder = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_5)
                .maxCompletionTokens(properties.maxCompletionTokens())
                .addSystemMessage(properties.instructions())
                .addTool(ClickXY.class)
                .addTool(Finish.class)
                .addUserMessage(scenario);
    }

    ChatCompletionCreateParams build(String screenshot, int width, int height) {
        builder.addUserMessageOfArrayOfContentParts(
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
        return builder.build();
    }

    void addAssistantMessage(String message) {
        builder.addAssistantMessage(message);
    }
}
