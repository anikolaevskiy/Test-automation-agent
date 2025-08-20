package com.test.example.configuration.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.test.example.agent.llm.tools.ClickXY;
import com.test.example.agent.llm.tools.Finish;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(OpenAIProperties.class)
public class OpenAIConfiguration {

    @Bean
    public OpenAIClient openAIClient(OpenAIProperties properties) {
        return OpenAIOkHttpClient.builder()
                .apiKey(properties.key())
                .build();
    }

    @Bean
    public ChatCompletionCreateParams.Builder params(OpenAIProperties properties) {
        return ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_5)
                .maxCompletionTokens(properties.maxCompletionTokens())
                .addSystemMessage(properties.instructions())
                .addTool(ClickXY.class)
                .addTool(Finish.class);
    }
}
