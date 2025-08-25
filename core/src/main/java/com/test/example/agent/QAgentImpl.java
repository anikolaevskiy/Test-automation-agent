package com.test.example.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.example.llm.LlmClient;
import com.test.example.llm.tools.ClickXY;
import com.test.example.llm.tools.Finish;
import com.test.example.mcp.McpGateway;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.Base64;

@Slf4j
@RequiredArgsConstructor
public class QAgentImpl implements QAgent {

    private final LlmClient llm;

    private final McpGateway mcp;

    private final ObjectMapper objectMapper;

    private final int delay;

    private final int maxActionsNumber;

    private int actionsCounter = 0;

    @Override
    public State start(String scenario) {
        log.info("Starting new scenario: \n{}", scenario);
        llm.addUserMessage(scenario);
        return new State(true, true, mcp.screenshot(), "Started scenario");

    }

    @Override
    @SneakyThrows(JsonProcessingException.class)
    public State nextAction(String screenshot) {

        if (actionsCounter >= maxActionsNumber) {
            return finishByLimit(screenshot);
        }

        actionsCounter++;

        var function = llm.addUserMessage("Screenshot of current state", screenshot).send();

        if (function.name().equals("finish")) {
            return finish(screenshot, function.args());

        } else if (function.name().equals("click_xy")) {
            return click(function.args());
        }

        return new State(false, false, screenshot, "Unknown action: " + function.name());
    }


    @SneakyThrows(InterruptedException.class)
    private State click(String args) throws JsonProcessingException {
        var click = objectMapper.readValue(args, ClickXY.class);
        log.info(click.description());
        var result = mcp.click(args);
        log.info(result);

        // Adding delay to mimic human-like interaction and allow UI to stabilize
        Thread.sleep(delay);

        return new State(true, true, mcp.screenshot(), click.description());
    }

    private State finish(String screenshot, String args) throws JsonProcessingException {
        var finish = objectMapper.readValue(args, Finish.class);
        log.info(finish.description());
        return new State(false, finish.success(), screenshot, finish.description());
    }

    private State finishByLimit(String screenshot) throws JsonProcessingException {
        log.info("Max actions number reached: {}", maxActionsNumber);
        var finish = new Finish(false, "Max actions number reached");
        return finish(screenshot, objectMapper.writeValueAsString(finish));
    }

    private static BufferedImage getScreenshotAsImage(String screenshot) throws Exception {
        return ImageIO.read(new ByteArrayInputStream(Base64.getDecoder().decode(screenshot)));
    }
}
