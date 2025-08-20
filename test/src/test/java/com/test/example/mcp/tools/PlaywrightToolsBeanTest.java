package com.test.example.mcp.tools;

import com.microsoft.playwright.Page;
import com.test.example.configuration.mcp.McpConfiguration;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = McpConfiguration.class)
@ActiveProfiles("playwright")
class PlaywrightToolsBeanTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private Page page;

    @Test
    void registersOnlySinglePlaywrightToolsBean() {
        var beans = context.getBeansOfType(PlaywrightTools.class);
        assertThat(beans).hasSize(1);
    }
}

