package com.test.agent.tools;

import com.microsoft.playwright.Page;
import com.test.example.agent.mcp.McpResponse;
import com.test.example.agent.mcp.McpTools;
import lombok.RequiredArgsConstructor;

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
    public McpResponse clickXY(int x, int y) {
        page.mouse().click(x, y);
        var screenshot = screenshot();
        return new McpResponse(
                "Clicked at (" + x + ", " + y + ")",
                screenshot.screenshot(),
                screenshot.width(),
                screenshot.height()
        );
    }

    /**
     * Captures a screenshot of the current page.
     *
     * @return screenshot encoded as Base64 along with its dimensions
     */
    @Override
    public McpResponse screenshot() {
        return new McpResponse(
                "Page screenshot",
                Base64.getEncoder().encodeToString(page.screenshot()),
                page.viewportSize().width,
                page.viewportSize().height
        );
    }
}
