package com.test.example.configuration.playwright;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration properties for Playwright integration.
 *
 * @param appHost URL of the application under test
 */
@PropertySource("classpath:playwright.properties")
@ConfigurationProperties(prefix = "playwright")
@ConfigurationPropertiesScan
public record PlaywrightProperties(String appHost) {
}
