package com.test.example.utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.util.Base64;

/**
 * Utility methods for attaching screenshots and messages to Allure reports.
 */
public class AllureUtils {

    /**
     * Adds a step with screenshot only.
     *
     * @param name       step name
     * @param screenshot Base64 encoded screenshot
     */
    public static void addStep(String name, String screenshot) {
        byte[] decodedScreenshot = getDecodedScreenshot(screenshot);
        Allure.step(name, () ->
                Allure.addAttachment("Screen", "image/jpeg", new ByteArrayInputStream(decodedScreenshot), ".jpeg"));
    }

    /**
     * Adds a step with screenshot and textual message.
     */
    public static void addStep(String name, String screenshot, String message) {
        byte[] decodedScreenshot = getDecodedScreenshot(screenshot);
        Allure.step(name, () -> {
            Allure.addAttachment("Content", message);
            Allure.addAttachment("Screen", "image/png", new ByteArrayInputStream(decodedScreenshot), ".png");
        });
    }

    /**
     * Decodes screenshot data stripping surrounding quotes added by the model.
     */
    private static byte[] getDecodedScreenshot(String screenshot) {
        return Base64.getDecoder().decode(screenshot.substring(1, screenshot.length() - 1));
    }
}
