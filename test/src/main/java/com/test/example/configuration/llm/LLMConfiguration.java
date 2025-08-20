package com.test.example.configuration.llm;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.test.example.agent.llm.GPTClient;
import com.test.example.agent.llm.LLMClient;
import com.test.example.configuration.openai.OpenAIConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(OpenAIConfiguration.class)
public class LLMConfiguration {

    @Bean
    @Profile("openai")
    public LLMClient llm(OpenAIClient openAiClient, ChatCompletionCreateParams.Builder params) {
        return new GPTClient(openAiClient, params);
    }
}
