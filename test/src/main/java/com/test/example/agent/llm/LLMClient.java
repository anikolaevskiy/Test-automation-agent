package com.test.example.agent.llm;

import com.test.example.agent.Action;

public interface LLMClient {


    void setUpScenario(String scenario);

    Action requestNextAction(String screenshot, int width, int height);
}
