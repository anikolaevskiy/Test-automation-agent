package com.test.example.agent.mcp;

import org.springframework.ai.tool.annotation.Tool;

public interface McpTools {

    @Tool(name = "click_xy")
    McpResponse clickXY(int x, int y);


    @Tool(name = "screenshot")
    McpResponse screenshot();

}
