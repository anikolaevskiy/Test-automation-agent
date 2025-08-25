package com.test.agent.tools;

import com.microsoft.playwright.Page;
import com.test.example.mcp.McpTools;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;

import java.util.Base64;

@RequiredArgsConstructor
public class PlaywrightMcpTools implements McpTools {

    private final Page page;

    /**
     * Clicks at the provided coordinates and returns the updated screenshot.
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @return result of the click including a screenshot
     */
    @Override
    @Tool(name = "click_xy")
    public String clickXY(int x, int y) {
        page.mouse().click(x, y);
        return "Clicked at (" + x + ", " + y + ")";
    }

    /**
     * Captures a screenshot of the current page.
     *
     * @return screenshot encoded as Base64 along with its dimensions
     */
    @Override
    @Tool(name = "screenshot")
    public String screenshot() {
        return Base64.getEncoder().encodeToString(page.screenshot());
    }
}
