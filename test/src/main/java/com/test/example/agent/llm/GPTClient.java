package com.test.example.agent.llm;

import com.openai.client.OpenAIClient;
import com.test.example.agent.Action;
import com.test.example.configuration.openai.OpenAIProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * {@link LLMClient} implementation that uses the OpenAI GPT model to decide on actions.
 */
@Component
@Scope("prototype")
@RequiredArgsConstructor
public class GPTClient implements LLMClient {

    private final OpenAIClient openAIClient;

    private final OpenAIProperties properties;

    private Conversation conversation;

    /**
     * Adds the scenario description to the conversation context.
     *
     * @param scenario textual test scenario
     */
    @Override
    public void setUpScenario(String scenario) {
        this.conversation = new Conversation(properties, scenario);
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
        var params = conversation.build(screenshot, width, height);

        var function = openAIClient.chat()
                .completions()
                .create(params)
                .choices()
                .getFirst()
                .message()
                .toolCalls()
                .orElseThrow()
                .getFirst()
                .function()
                .orElseThrow()
                .function();

        var action = new Action(function.name(), function.arguments());
        conversation.addAssistantMessage(action.name() + " " + action.arguments());

        return action;
    }

}
