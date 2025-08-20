package com.test.example.configuration.playwright;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(PlaywrightProperties.class)
public class PlaywrightConfiguration {

    @Bean
    public BrowserType.LaunchOptions playwrightOptions() {
        return new BrowserType.LaunchOptions().setHeadless(false);
    }

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
