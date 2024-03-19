package utils.runner;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;
import utils.LoggerUtils;

import java.util.Map;

public final class BrowserManager {
    public  static Browser createBrowser(Playwright playwright, Map<String, String> env) {
        String browserName = env.get("browser");
        boolean isHeadless = Boolean.parseBoolean(env.get("isHeadless"));
        int slowMo = Integer.parseInt(env.get("slowMo"));

        switch (browserName) {
            case "chromium" -> {
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless)
                        .setSlowMo(slowMo));
            }
            case "firefox" -> {
                return playwright.firefox().launch(new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless)
                        .setSlowMo(slowMo));
            }
            case "webkit" -> {
                return playwright.webkit().launch(new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless)
                        .setSlowMo(slowMo));
            }
            default -> {
                LoggerUtils.logWarning("WARN: " + browserName + "is not match any options. Chromium launched");
                return playwright.chromium().launch(new BrowserType.LaunchOptions()
                        .setHeadless(isHeadless)
                        .setSlowMo(slowMo));
            }
        }
    }
}
