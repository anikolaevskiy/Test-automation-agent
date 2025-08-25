package com.test.agent.configuration.openai;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Configuration properties for the OpenAI integration.
 *
 * @param key                API key used to access OpenAI
 * @param maxCompletionTokens maximum number of completion tokens
 * @param instructionsPath   path to system instructions for the model
 */
@PropertySource("classpath:openai.properties")
@ConfigurationProperties(prefix = "openai")
@ConfigurationPropertiesScan
public record OpenAIProperties(
        String key,
        int maxCompletionTokens,
        Resource instructionsPath
) {

    /**
     * Loads system instructions from the configured resource.
     *
     * @return instruction text
     */
    @SneakyThrows(IOException.class)
    public String instructions() {
        return new String(instructionsPath.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

    }
}
