package com.example.aqa;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.example.agent.Agent;
import com.test.example.agent.llm.tools.Finish;
import com.test.example.configuration.MainConfiguration;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Integration test that runs a full scenario through the {@link Agent} and verifies the result.
 */
@Slf4j
@SpringBootTest(classes = MainConfiguration.class)
public class AgentTest {

    @Autowired
    private Agent agent;

    /**
     * Executes a sample scenario and asserts that the agent reports success.
     */
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void test() {

        log.info("Starting test...");

        // Test scenario stub
        var result = agent.executeTestScenario("""
                Step 1 - Green "Code" button is visible and enabled on the repository page
                Expected result - After clicking "Code", the clone popup/dropdown opens showing cloning options (HTTPS, SSH, GitHub CLI) and a copyable repository URL (options like "Open with GitHub Desktop" and "Download ZIP" are visible).
                """);

        var parsedResult = objectMapper.readValue(result.functionArguments(), Finish.class);
        Assertions.assertThat(parsedResult.isSuccess())
                .as(parsedResult.getDescription())
                .isTrue();

        log.info("Test completed successfully. Result: {}", parsedResult.getDescription());
    }
}

