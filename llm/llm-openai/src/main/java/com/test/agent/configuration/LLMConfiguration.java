package com.test.agent.configuration;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.test.agent.OpenAiClient;
import com.test.agent.configuration.openai.OpenAIConfiguration;
import com.test.example.llm.LlmClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(OpenAIConfiguration.class)
public class LLMConfiguration {

    @Bean
    @Profile("openai")
    public LlmClient llm(OpenAIClient openAiClient, ChatCompletionCreateParams params) {
        return new OpenAiClient(openAiClient, params);
    }
}
