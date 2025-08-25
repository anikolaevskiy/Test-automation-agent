package com.test.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.example.agent.QAgent;
import com.test.example.agent.QAgentImpl;
import com.test.example.configuration.common.CommonConfiguration;
import com.test.example.llm.LlmClient;
import com.test.example.mcp.McpGateway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Wires together the agent core components.
 * <p>
 * The configuration pulls in common utilities and exposes a fully configured
 * {@link QAgent} bean ready to use in tests.
 */
@Configuration
@EnableConfigurationProperties(AgentProperties.class)
@Import(CommonConfiguration.class)
public class AgentConfiguration {

    /**
     * Creates the main agent instance.
     *
     * @param mcp          gateway used to invoke tools
     * @param llm          client communicating with the model
     * @param properties   user configurable agent settings
     * @param objectMapper JSON mapper for serialising tool arguments
     * @return configured {@link QAgent}
     */
    @Bean
    public QAgent qagent(McpGateway mcp, LlmClient llm, AgentProperties properties, ObjectMapper objectMapper) {
        return new QAgentImpl(llm, mcp, objectMapper, properties.delayBetweenActions(), properties.maxActions());
    }
}
