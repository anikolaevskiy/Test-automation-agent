package com.test.example.configuration.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for the OpenAI client and default chat parameters.
 */
@Configuration
@EnableConfigurationProperties(OpenAIProperties.class)
public class OpenAIConfiguration {

    /**
     * Creates an {@link OpenAIClient} using the configured API key.
     *
     * @param properties OpenAI configuration properties
     * @return OpenAI client instance
     */
    @Bean
    public OpenAIClient openAIClient(OpenAIProperties properties) {
        return OpenAIOkHttpClient.builder()
                .apiKey(properties.key())
                .build();
    }
}
