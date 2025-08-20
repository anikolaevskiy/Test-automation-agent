package com.test.example.mcp.tools;

import org.springframework.ai.tool.annotation.Tool;

public interface Tools {

    @Tool(name = "click_xy")
    Result clickXY(int x, int y);

    @Tool(name = "screenshot")
    Result screenshot();

    record Result(String message, String screenshot, int width, int height) { }
}
