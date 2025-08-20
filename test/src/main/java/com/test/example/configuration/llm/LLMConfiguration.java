package com.test.example.configuration.llm;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import com.test.example.agent.llm.LLMClient;
import com.test.example.configuration.openai.OpenAIConfiguration;

/**
 * Configures the {@link LLMClient} implementation.
 */
@Configuration
@Import(OpenAIConfiguration.class)
public class LLMConfiguration {
}
