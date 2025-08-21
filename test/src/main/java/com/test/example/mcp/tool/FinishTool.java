package com.test.example.mcp.tool;

import com.microsoft.playwright.Page;
import com.test.example.agent.llm.tools.Finish;
import org.springframework.stereotype.Component;

/**
 * Signals the end of the test scenario.
 */
@Component
public class FinishTool implements McpTool {

    @Override
    public String name() {
        return "finish";
    }

    @Override
    public Class<?> paramsType() {
        return Finish.class;
    }

    @Override
    public Tools.Result execute(Page page, Object params) {
        var finish = (Finish) params;
        return new Tools.Result(
                finish.getDescription(),
                "",
                0,
                0
        );
    }
}

