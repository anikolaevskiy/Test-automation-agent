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
 * Configures the language model client used by the agent.
 * <p>
 * Different language model providers can be supplied by introducing additional
 * beans conditioned on Spring profiles. The included {@code openai} profile
 * demonstrates how to wire the GPT based implementation.
 */
@Configuration
@Import(OpenAIConfiguration.class)
public class LLMConfiguration {

    /**
     * Creates an OpenAI-based client when the "openai" profile is active.
     * Additional profiles can instantiate other implementations (e.g. local
     * models) without modifying the agent.
     *
     * @param openAiClient OpenAI client
     * @param params       chat completion parameters
     * @return GPT-backed language model client
     */
    @Bean
    @Profile("openai")
    public LLMClient llm(OpenAIClient openAiClient, ChatCompletionCreateParams params) {
        return new GPTClient(openAiClient, params);
    }
}
