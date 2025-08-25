package com.test.agent.configuration.playwright;

import com.microsoft.playwright.*;
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
     * Builds launch options using the configured headless flag.
     */
    @Bean
    public BrowserType.LaunchOptions playwrightOptions(PlaywrightProperties properties) {
        return new BrowserType.LaunchOptions().setHeadless(properties.headless());
    }

    /**
     * Creates a Playwright instance.
     *
     * @return managed Playwright instance
     */
    @Bean(destroyMethod = "close")
    public Playwright playwright() {
        return Playwright.create();
    }

    /**
     * Launches a Chromium {@link Browser} using the provided options.
     *
     * @param playwright Playwright instance
     * @param options    launch options
     * @return launched browser
     */
    @Bean(destroyMethod = "close")
    public Browser browser(Playwright playwright, BrowserType.LaunchOptions options) {
        return playwright.chromium().launch(options);
    }

    /**
     * Creates a new {@link BrowserContext} with a predefined viewport to ensure
     * stable coordinates across runs.
     */
    @Bean(destroyMethod = "close")
    public BrowserContext browserContext(Browser browser, PlaywrightProperties properties) {
        return browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(properties.width(), properties.height()));
    }

    /**
     * Creates a Playwright {@link Page} and navigates to the configured host.
     */
    @Bean(destroyMethod = "close")
    public Page playwrightPage(PlaywrightProperties properties, BrowserContext context) {
        var page = context.newPage();
        page.navigate(properties.appHost());
        return page;
    }
}
