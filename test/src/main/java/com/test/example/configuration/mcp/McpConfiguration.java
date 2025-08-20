package com.test.example.configuration.mcp;

import com.microsoft.playwright.Page;
import com.test.example.mcp.tools.PlaywrightTools;

import com.test.example.mcp.tools.Tools;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Configuration
@Import(PlaywrightTools.class)
public class McpConfiguration {


    @Bean
    @Profile("playwright")
    public PlaywrightTools playwrightTools(Page page) {
        return new PlaywrightTools(page);
    }

    @Bean
    public ToolCallbackProvider toolsProvider(Tools tools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(tools)
                .build();
    }
}
