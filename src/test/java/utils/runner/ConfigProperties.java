package utils.runner;

import utils.LoggerUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public final class ConfigProperties {
    private static Properties properties  = initProperties();

    public static final Map<String, String> ENVIRONMENT_CHROMIUM = setEnvironment("browserName1", "isHeadless1", "slowMo1");
    public static final Map<String, String> ENVIRONMENT_FIREFOX = setEnvironment("browserName2", "isHeadless2", "slowMo2");
    public static final Map<String, String> ENVIRONMENT_WEBKIT = setEnvironment("browserName3", "isHeadless3", "slowMo3");

    public static Properties initProperties() {
        properties = new Properties();
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
            properties.load(file);

        } catch (IOException e) {
            LoggerUtils.logError("ERROR: Properties file NOT found OR file IS EMPTY OR file IS CORRUPT.");
            throw new RuntimeException(e);
        }
        return properties;
    }

    private static Map<String, String> setEnvironment(String browser, String isHeadless, String slowMo) {
        Map<String, String> env = new HashMap<>();

        if(properties != null && !properties.isEmpty()
        && properties.containsKey(browser) && !properties.getProperty(browser).trim().isEmpty()
        && properties.containsKey(isHeadless) && !properties.getProperty(isHeadless).trim().isEmpty()
        && properties.containsKey(slowMo) && !properties.getProperty(slowMo).trim().isEmpty()) {
            env.put("browser", properties.getProperty(browser).trim());
            env.put("isHeadless", properties.getProperty(isHeadless).trim());
            env.put("slowMo", properties.getProperty(slowMo).trim());
        } else {
            LoggerUtils.logWarning("WARN: Set DEFAULT environment.");

            //default env
            env.put("browser", "chromium");
            env.put("isHeadless", "true");
            env.put("slowMo", "0");
        }

        return env;
    }
}
