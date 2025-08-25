package com.example.aqa;


import com.test.example.configuration.MainConfiguration;
import com.test.example.agent.QAgent;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@Slf4j
@SpringBootTest(classes = MainConfiguration.class)
public class QAgentTest {

    @Autowired
    private QAgent agent;

    @SneakyThrows
    @Test
    public void test() {

        var scenario = """
                Step 1 - Green "Code" button is visible and enabled on the repository page
                Expected result - After clicking "Code", the clone popup/dropdown opens showing cloning options (HTTPS, SSH, GitHub CLI) and a copyable repository URL (options like "Open with GitHub Desktop" and "Download ZIP" are visible).
                """;

        var state = agent.start(scenario);

        while (state.proceed()) {
            state = agent.nextAction(state.screenshot());
        }

        Assertions.assertThat(state.successful())
                .as(state.message())
                .isTrue();
    }
}

