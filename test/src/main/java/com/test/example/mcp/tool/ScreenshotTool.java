package com.test.example.mcp.tool;

import com.microsoft.playwright.Page;
import com.test.example.agent.llm.tools.Screenshot;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Captures a screenshot of the current page.
 */
@Component
public class ScreenshotTool implements McpTool {

    @Override
    public String name() {
        return "screenshot";
    }

    @Override
    public Class<?> paramsType() {
        return Screenshot.class;
    }

    @Override
    public Tools.Result execute(Page page, Object params) {
        var screenshot = page.screenshot();
        var viewport = page.viewportSize();
        return new Tools.Result(
                "Page screenshot",
                Base64.getEncoder().encodeToString(screenshot),
                viewport.width,
                viewport.height
        );
    }
}

