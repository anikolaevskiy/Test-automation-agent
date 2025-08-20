package com.test.example.mcp.tools;

import com.microsoft.playwright.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class PlaywrightTools implements Tools {

    private final Page page;

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
