package com.test.example.configuration.openai;

import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.chat.completions.ChatCompletionCreateParams;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.example.mcp.tool.McpTool;
import java.util.List;

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

    /**
     * Provides a template for creating chat completion requests.
     *
     * @param properties OpenAI configuration properties
     * @return builder preconfigured with model, instructions and available tools
     */
    @Bean
    public ChatCompletionCreateParams.Builder params(OpenAIProperties properties, List<McpTool> tools) {
        var builder = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_5)
                .maxCompletionTokens(properties.maxCompletionTokens())
                .addSystemMessage(properties.instructions());

        tools.forEach(tool -> builder.addTool(tool.paramsType()));

        return builder;
    }
}
