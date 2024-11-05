package com.Ebebek.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

public class ConfigManager {

   private static final String TEST_CONFIG_FILE = Paths.get("src/test/resources/app.properties").toString();
    private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);
    private static Map<String, String> configMap = new HashMap<>();

    private ConfigManager() {
    }

    public static synchronized String getConfigProperty(String key) {
        if (configMap.size() == 0) {
            Properties properties = new Properties();
            try {
                properties.load(new FileInputStream(TEST_CONFIG_FILE));
                configMap = new HashMap<>(properties.entrySet()
                        .stream()
                        .collect(Collectors.toMap(e -> e.getKey().toString(),
                                e -> e.getValue().toString())));
                LOGGER.debug("Loaded config properties : " + TEST_CONFIG_FILE);
            } catch (Exception e) {
                LOGGER.error(e);
                throw new RuntimeException(e);
            }
        }
        return configMap.get(key);
    }

    public static String getPlatform() {
        return System.getProperty("platform", getStringValue("platform", "android"));
    }


    public static String getOperatingSystem() {
        return System.getProperty("operatingSystem", getStringValue("operatingSystem", "lambda"));
    }

    public static Integer getMaxRetryCount() {
        return Integer.parseInt(System.getProperty("max.retry.count", getStringValue("max.retry.count", String.valueOf(1))));
    }

    public static String getTestUrlConfig() {
        return System.getProperty("testEnv", getStringValue("testEnv", "test"));
    }

    public static String getStringValue(String key, String defaultValue) {
        return getRunTimeValue(key, defaultValue);
    }

    public static String getRunTimeValue(String key, String defaultValue) {
        Assert.assertTrue(StringUtils.isNotBlank(key), "Key can not be null");
        if (StringUtils.isNotBlank(System.getenv(key))) {
            return System.getenv(key);
        } else if (StringUtils.isNotBlank(System.getProperty(key))) {
            return System.getProperty(key);
        } else if (StringUtils.isNotBlank(ConfigManager.getConfigProperty(key))) {
            return ConfigManager.getConfigProperty(key);
        } else {
            return defaultValue;
        }
    }
}
