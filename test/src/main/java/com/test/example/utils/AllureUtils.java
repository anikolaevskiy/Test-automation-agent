package com.test.example.utils;

import io.qameta.allure.Allure;

import java.io.ByteArrayInputStream;
import java.util.Base64;

public class AllureUtils {

    public static void addStep(String name, String screenshot) {
        byte[] decodedScreenshot = getDecodedScreenshot(screenshot);
        Allure.step(name, () ->
                Allure.addAttachment("Screen", "image/png", new ByteArrayInputStream(decodedScreenshot), ".png"));
    }

    public static void addStep(String name, String screenshot, String message) {
        byte[] decodedScreenshot = getDecodedScreenshot(screenshot);
        Allure.step(name, () -> {
            Allure.addAttachment("Content", message);
            Allure.addAttachment("Screen", "image/png", new ByteArrayInputStream(decodedScreenshot), ".png");
        });
    }

    private static byte[] getDecodedScreenshot(String screenshot) {
        return Base64.getDecoder().decode(screenshot.substring(1, screenshot.length() - 1));
    }
}
