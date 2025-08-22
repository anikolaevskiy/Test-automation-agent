package com.test.agent.configuration;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.test.agent.configuration.openai.OpenAIConfiguration;
import com.test.agent.llm.GPTClient;
import com.test.example.agent.llm.LlmClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

/**
 * Configures the {@link LlmClient} implementation used by the agent.
 * <p>
 * Different language model providers can be supplied by introducing additional
 * beans conditioned on Spring profiles. The included {@code openai} profile
 * demonstrates how to wire the GPT based implementation.
 */
@Configuration
@Import(OpenAIConfiguration.class)
public class LLMConfiguration {

    /**
     * Creates an OpenAI-based {@link LlmClient} when the "openai" profile is active.
     * Additional profiles can instantiate other implementations (e.g. local
     * models) without modifying the agent.
     *
     * @param openAiClient OpenAI client
     * @param params       chat completion parameters
     * @return GPT-backed implementation of {@link LlmClient}
     */
    @Bean
    @Profile("openai")
    public LlmClient llm(OpenAIClient openAiClient, ChatCompletionCreateParams params) {
        return new GPTClient(openAiClient, params);
    }
}
