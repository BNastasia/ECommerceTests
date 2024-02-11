package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.LoggerUtils;

abstract class BaseTest {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
            .setHeadless(false).setSlowMo(1000));
    private BrowserContext context;
    private Page page;

    @BeforeSuite
    void isPlaywrightCreateAndBrowserLaunch() {
        if(playwright != null) {
            LoggerUtils.logInfo("Playwright created");
        } else {
            LoggerUtils.logFatal("Fatal: Playwright is not created");
            System.exit(1);
        }

        if(browser.isConnected()) {
            LoggerUtils.logInfo("Browser " + browser.browserType().name() + " is connected");
        } else {
            LoggerUtils.logFatal("Fatal: browser is not created");
            System.exit(1);
        }
    }

    @BeforeMethod
    void createContextAndPage() {
        context = browser.newContext();
        LoggerUtils.logInfo("Context created");

        page = context.newPage();
        LoggerUtils.logInfo("Page created");
    }

    @AfterMethod
    void closePageAndContext() {
        if(page != null) {
            page.close();
            LoggerUtils.logInfo("Page closed");
        }

        if(context != null) {
            context.close();
            LoggerUtils.logInfo("Context closed");
        }
    }

    @AfterSuite
    void closeBrowserAndPlaywright() {
        if(browser != null) {
            browser.close();
            LoggerUtils.logInfo("Browser closed");
        }
        if(playwright != null) {
            playwright.close();
            LoggerUtils.logInfo("Playwright closed");
        }
    }

    Page getPage() {
        return page;
    }
}
