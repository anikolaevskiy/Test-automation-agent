package com.test.agent.configuration.playwright;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

/**
 * Configuration properties for Playwright integration.
 *
 * @param appHost URL of the application under test
 */
@ConfigurationProperties(prefix = "playwright")
@ConfigurationPropertiesScan
public record PlaywrightProperties(String appHost, boolean headless, int width, int height) {
}