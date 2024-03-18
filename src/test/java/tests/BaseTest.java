package tests;

import com.microsoft.playwright.*;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import utils.LoggerUtils;
import utils.runner.BrowserManager;

import static utils.TestData.BASE_URL;
import static utils.TestData.HOME_END_POINT;

public abstract class BaseTest {
    private final Playwright playwright = Playwright.create();
    private final Browser browser = BrowserManager.createBrowser(playwright,"chromium", false, 1000);
    private BrowserContext context;
    private Page page;

    @BeforeSuite
    void isPlaywrightCreateAndBrowserLaunch() {
        if(playwright != null) {
            LoggerUtils.logInfo("Playwright created");
        } else {
            LoggerUtils.logFatal("FATAL: Playwright is not created");
            System.exit(1);
        }

        if(browser.isConnected()) {
            LoggerUtils.logInfo("Browser " + browser.browserType().name() + " is connected");
        } else {
            LoggerUtils.logFatal("FATAL: browser is not created");
            System.exit(1);
        }
    }

    @BeforeMethod
    void createContextAndPage() {
        context = browser.newContext();
        LoggerUtils.logInfo("Context created");

        page = context.newPage();
        LoggerUtils.logInfo("Page created");
        LoggerUtils.logInfo("Test is started");

        getPage().navigate(BASE_URL + HOME_END_POINT);

        if(isOnHomePage()) {
            LoggerUtils.logInfo("Base url is opened and content is not empty.");
        }else {
            LoggerUtils.logError("ERROR: Base url is NOT opened OR content is EMPTY.");
        }
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

    private boolean isOnHomePage() {
        getPage().waitForLoadState();

        return getPage().url().equals(BASE_URL) && !page.content().isEmpty();
    }

    protected boolean getIsOnHomePage() {

        return isOnHomePage();
    }

    Page getPage() {
        return page;
    }
}
