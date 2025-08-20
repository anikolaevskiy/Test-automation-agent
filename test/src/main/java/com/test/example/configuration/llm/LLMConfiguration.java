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

/**
 * Configures the {@link LLMClient} implementation.
 */
@Configuration
@Import(OpenAIConfiguration.class)
public class LLMConfiguration {

    /**
     * Creates an OpenAI-based {@link LLMClient} when the "openai" profile is active.
     *
     * @param openAiClient OpenAI client
     * @param params       chat completion parameters
     * @return GPT-backed implementation of {@link LLMClient}
     */
    @Bean
    @Profile("openai")
    public LLMClient llm(OpenAIClient openAiClient, ChatCompletionCreateParams.Builder params) {
        return new GPTClient(openAiClient, params);
    }
}
