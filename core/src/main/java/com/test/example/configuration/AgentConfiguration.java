package com.test.example.configuration;

import com.test.example.agent.Agent;
import com.test.example.agent.llm.LlmClient;
import com.test.example.agent.mcp.McpGateway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Central configuration that wires together the pieces of the agent.
 * <p>
 * It imports the configurations for the tools and the LLM and exposes the
 * {@link Agent} bean used in tests. Replacing any of the imported
 * configurations allows the application to switch to different tooling or LLM
 * providers without touching the rest of the code.
 */
@Configuration
@EnableConfigurationProperties(AgentProperties.class)
public class AgentConfiguration {

    /**
     * Creates the {@link Agent} using the configured gateway and LLM client.
     *
     * @param mcp        gateway to access tools
     * @param llm        language model client responsible for reasoning
     * @param properties agent settings
     * @return initialized agent instance
     */
    @Bean
    public Agent agent(McpGateway mcp, LlmClient llm, AgentProperties properties) {
        return new Agent(mcp, llm, properties.maxIterations());
    }
}
