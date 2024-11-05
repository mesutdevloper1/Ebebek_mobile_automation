package com.Ebebek.driver;





import com.Ebebek.utils.ConfigManager;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobilePlatform;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import org.apache.logging.log4j.Logger;

import static com.Ebebek.utils.DataManager.getData;
import static io.appium.java_client.remote.MobileCapabilityType.*;
import static org.openqa.selenium.remote.CapabilityType.*;


public class DriverManager {
    private static final Logger LOGGER = LogManager.getLogger(DriverManager.class);
    private static final InheritableThreadLocal<AppiumDriver> MOBILE_DRIVER_THREAD_LOCAL = new InheritableThreadLocal<>();

    private DriverManager() {
    }

    public static AppiumDriver getDriver() {
        return MOBILE_DRIVER_THREAD_LOCAL.get();
    }

    public static void setDriver(AppiumDriver driver) {
        MOBILE_DRIVER_THREAD_LOCAL.set(driver);
    }

    public static void quitDriver() {
        try {
            if (MOBILE_DRIVER_THREAD_LOCAL.get() != null) {
                MOBILE_DRIVER_THREAD_LOCAL.get().quit();
            }
        } catch (Exception e) {
            LOGGER.warn(e);
        }
    }

    public static String userName = getData("lambda.userName");
    public static String accessKey = getData("lambda.accessKey");

    public static void launchBrowser(String platform, String deviceName, String platformVersion, String platformName) throws MalformedURLException {

        if (platform.equalsIgnoreCase("lambda")) {
            DesiredCapabilities capabilities = new DesiredCapabilities();

            capabilities.setCapability("deviceName", deviceName);
            capabilities.setCapability("platformVersion", platformVersion);
            capabilities.setCapability("platformName", platformName);

            capabilities.setCapability("name", "Test Parallel");

            capabilities.setCapability("app", getData("appUrl.Ebebek_" + ConfigManager.getOperatingSystem() + ""));
            capabilities.setCapability("build", "Ebebek - " + ConfigManager.getOperatingSystem() + " Test Automation");

            capabilities.setCapability("isRealMobile", true);
            capabilities.setCapability("console", true);
            capabilities.setCapability("network", false);
            capabilities.setCapability("visual", true);
            capabilities.setCapability("devicelog", true);
            capabilities.setCapability("video", true);
            if (platformName.equals("ios")) {
                AndroidDriver appiumDriver = new AndroidDriver<MobileElement>(new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"), capabilities);
                setDriver(appiumDriver);
            } else if (platformName.equals("android")) {
                IOSDriver appiumDriver = new IOSDriver<MobileElement>(new URL("https://" + userName + ":" + accessKey + "@mobile-hub.lambdatest.com/wd/hub"), capabilities);
                setDriver(appiumDriver);
            }

        } else if (platform.equalsIgnoreCase("android")) {
            AppiumDriver<MobileElement> appiumDriver = null;
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(APPLICATION_NAME, getData("apk.Ebebek_" + ConfigManager.getOperatingSystem() + ""));
            capabilities.setCapability(AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
            capabilities.setCapability(PLATFORM_NAME, MobilePlatform.ANDROID);
            capabilities.setCapability(DEVICE_NAME, "emulator-5554"); //emulator değilse terminalden şunun gibi bir id alınır ->  e82d9c6b

            appiumDriver = new AppiumDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), capabilities);
            setDriver(appiumDriver);
        } else if (platform.equalsIgnoreCase("ios")) {
            AppiumDriver<MobileElement> appiumDriver = null;
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability(APPLICATION_NAME, getData("apk.Ebebek_" + ConfigManager.getOperatingSystem() + ""));
            capabilities.setCapability(AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            capabilities.setCapability(PLATFORM_NAME, MobilePlatform.IOS);
            capabilities.setCapability(PLATFORM_VERSION, "15.1");
            capabilities.setCapability(DEVICE_NAME, "MesutPhone");

            //capabilities.setCapability("bundleId", "");  //ios için bu 4 satırın bilgileri, webdriveragent projesi xcode ile build alınıp, xcode üzerinden bilgiler alınır
            //capabilities.setCapability(UDID, "");
            //capabilities.setCapability("xcodeOrgId", "");
            //capabilities.setCapability("xcodeSigningId", "");

            appiumDriver = new AppiumDriver<MobileElement>(new URL("http://localhost:4723/wd/hub"), capabilities);
            setDriver(appiumDriver);
        } else {
            throw new IllegalArgumentException(
                    String.format("%s is invalid value. Enter valid os value in config.properties", platform));
        }


    }
}
