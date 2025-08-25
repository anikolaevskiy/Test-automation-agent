package com.example.aqa;


import com.test.example.agent.QAgent;
import com.test.example.configuration.MainConfiguration;
import com.test.example.tms.StubTmsClient;
import com.test.example.utils.AllureUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest(classes = MainConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("QAgent tests")
public class QAgentTest {

    @Autowired
    private QAgent agent;

    @Autowired
    private StubTmsClient tmsClient;

    @Getter
    @Value("${tms.ids}")
    private List<String> tmsIds;

    @DisplayName("QAgent")
    @ParameterizedTest(name = " | {0}")
    @MethodSource("getTmsIds")
    public void qAgentTest(String testCaseId) {

        var scenario = tmsClient.getTestCaseById(testCaseId);
        var state = agent.start(scenario);
        AllureUtils.addStep("Started scenario execution", state.screenshot(), scenario);

        while (state.proceed()) {
            state = agent.nextAction(state.screenshot());
            AllureUtils.addStep(state.message(), state.screenshot());
        }

        Assertions.assertThat(state.successful())
                .as(state.message())
                .isTrue();
    }
}

