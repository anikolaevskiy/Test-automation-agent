package com.test.example.configuration.openai;

import lombok.SneakyThrows;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;

import java.io.IOException;

@PropertySource("classpath:openai.properties")
@ConfigurationProperties(prefix = "openai")
@ConfigurationPropertiesScan
public record OpenAIProperties(
        String key,
        int maxCompletionTokens,
        Resource instructionsPath
) {

    @SneakyThrows(IOException.class)
    public String instructions() {
        return new String(instructionsPath.getInputStream().readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);

    }
}
