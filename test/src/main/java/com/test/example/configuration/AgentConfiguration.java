package com.test.example.configuration;

import com.test.example.agent.Agent;
import com.test.example.agent.llm.LLMClient;
import com.test.example.configuration.comman.AgentProperties;
import com.test.example.configuration.llm.LLMConfiguration;
import com.test.example.configuration.mcp.McpConfiguration;
import com.test.example.mcp.gateway.McpGateway;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableConfigurationProperties(AgentProperties.class)
@Import({McpConfiguration.class,
        LLMConfiguration.class})
public class AgentConfiguration {

    @Bean
    public Agent agent(McpGateway mcp, LLMClient llm, AgentProperties properties) {
        return new Agent(mcp, llm, properties.maxIterations());
    }
}
