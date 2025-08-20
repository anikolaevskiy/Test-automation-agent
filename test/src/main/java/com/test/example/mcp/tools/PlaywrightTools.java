package com.test.example.mcp.tools;

import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;

import java.util.Base64;

/**
 * {@link Tools} implementation backed by Playwright.
 * Allows the LLM to interact with a real browser instance.
 */
@RequiredArgsConstructor
public class PlaywrightTools implements Tools {

    private final Page page;

    /**
     * Clicks at the provided coordinates and returns the updated screenshot.
     *
     * @param x horizontal coordinate
     * @param y vertical coordinate
     * @return result of the click including a screenshot
     */
    @Override
    public Result clickXY(int x, int y) {
        page.mouse().click(x, y);
        var screenshot = screenshot();
        return new Result(
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
    public Result screenshot() {
        return new Result(
                "Page screenshot",
                Base64.getEncoder().encodeToString(page.screenshot()),
                page.viewportSize().width,
                page.viewportSize().height
        );
    }

}
