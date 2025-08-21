package com.test.example.mcp.tool;

import com.microsoft.playwright.Page;
import com.test.example.agent.llm.tools.ClickXY;
import org.springframework.stereotype.Component;

import java.util.Base64;

/**
 * Clicks on the page at provided coordinates and returns a screenshot.
 */
@Component
public class ClickXYTool implements McpTool {

    @Override
    public String name() {
        return "click_xy";
    }

    @Override
    public Class<?> paramsType() {
        return ClickXY.class;
    }

    @Override
    public Tools.Result execute(Page page, Object params) {
        var click = (ClickXY) params;
        page.mouse().click(click.x, click.y);
        var screenshot = page.screenshot();
        var viewport = page.viewportSize();
        return new Tools.Result(
                "Clicked at (" + click.x + ", " + click.y + ")",
                Base64.getEncoder().encodeToString(screenshot),
                viewport.width,
                viewport.height
        );
    }
}

