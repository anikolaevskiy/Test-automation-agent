package com.test.example.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.example.llm.LlmClient;
import com.test.example.mcp.McpGateway;
import com.test.example.configuration.common.CommonConfiguration;
import com.test.example.agent.QAgent;
import com.test.example.agent.QAgentImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(AgentProperties.class)
@Import(CommonConfiguration.class)
public class AgentConfiguration {

    @Bean
    public QAgent qagent(McpGateway mcp, LlmClient llm, AgentProperties properties, ObjectMapper objectMapper) {
        return new QAgentImpl(llm, mcp, objectMapper, properties.delayBetweenActions(), properties.maxActions());
    }
}
