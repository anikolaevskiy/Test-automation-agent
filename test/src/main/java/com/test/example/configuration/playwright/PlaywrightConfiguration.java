package com.test.example.configuration.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration for creating Playwright browser instances.
 */
@Configuration
@EnableConfigurationProperties(PlaywrightProperties.class)
public class PlaywrightConfiguration {

    /**
     * Configures default Playwright launch options.
     *
     * @return launch options with headless mode disabled
     */
    @Bean
    public BrowserType.LaunchOptions playwrightOptions() {
        return new BrowserType.LaunchOptions().setHeadless(false);
    }

    /**
     * Creates a Playwright {@link Page} and navigates to the configured host.
     *
     * @param properties Playwright configuration properties
     * @param options    browser launch options
     * @return initialized page instance
     */
    @Bean(destroyMethod = "close")
    public Page playwrightPage(PlaywrightProperties properties, BrowserType.LaunchOptions options) {
        var playwright = Playwright.create();
        var chromium = playwright.chromium();
        var browser = chromium.launch(options);

        var context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(1280, 800));

        var page = context.newPage();
        page.navigate(properties.appHost());
        return page;
    }
}
