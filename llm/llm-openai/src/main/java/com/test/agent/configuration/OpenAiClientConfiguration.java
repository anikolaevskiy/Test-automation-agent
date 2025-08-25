package com.test.agent.configuration;

import com.openai.client.OpenAIClient;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import com.test.agent.OpenAiClient;
import com.test.agent.configuration.openai.OpenAIConfiguration;
import com.test.example.configuration.common.CommonConfiguration;
import com.test.example.llm.LlmClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.support.RetryTemplate;

/**
 * Spring wiring that exposes the {@link OpenAiClient} as an {@link LlmClient}.
 * <p>
 * The bean is only active under the {@code openai} profile so alternative LLM
 * implementations can be plugged in for other environments.
 */
@Configuration
@Import({OpenAIConfiguration.class, CommonConfiguration.class})
public class OpenAiClientConfiguration {

    /**
     * Creates the OpenAI-backed {@link LlmClient}.
     */
    @Bean
    @Profile("openai")
    public LlmClient llm(OpenAIClient openAiClient, RetryTemplate retry, ChatCompletionCreateParams params) {
        return new OpenAiClient(openAiClient, retry, params);
    }
}
