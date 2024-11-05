package io.cucumber.skeleton;


import com.Ebebek.driver.DriverManager;
import com.Ebebek.utils.ConfigManager;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.cucumber.java.an.E;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static base.AutomationMethods.terminateApp;
import static com.Ebebek.utils.ConfigManager.getOperatingSystem;
import static com.Ebebek.utils.DataManager.getData;
import static com.Ebebek.utils.Helper.deleteOldTestReportFiles;


@CucumberOptions(
        publish = false,
        features = {"src/test/resources/io.cucumber.features/Ebebek_android.feature"},
        glue = "io.cucumber.skeleton",
        tags = "@ANDROID",
        monochrome = true,
        plugin = {"summary", "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/failedrerun.txt"}
)
public class RunCucumberTest extends AbstractTestNGCucumberTests {
    private static final Logger LOGGER = LogManager.getLogger(RunCucumberTest.class);

    @BeforeMethod
    @Parameters({"deviceName", "platformVersion", "platformName"})
    public void startDriver(ITestResult result, String deviceName, String platformVersion, String platformName) throws MalformedURLException {
        System.setProperty("extent.reporter.html.start", new SimpleDateFormat("dd_MMM_yyy_HH_mm_ss", Locale.ENGLISH).format(new Date()));
        DriverManager.launchBrowser(ConfigManager.getPlatform(), deviceName, platformVersion, platformName);
        try {
            terminateApp(getData("activity.main_" + getOperatingSystem() + ""));
            LOGGER.info("Open apps are closed.");
        }catch (Exception e){
            LOGGER.info("Open app not found.");
        }
    }

    @AfterMethod
    public void closeDriver(ITestResult result) {
        terminateApp(getData("activity.main_" + getOperatingSystem() + ""));
    }

    @BeforeTest(alwaysRun = true)
    public void beforeTestReport() {
        PageFactory.initElements(new AppiumFieldDecorator(DriverManager.getDriver()), this);
    }

    @AfterTest(alwaysRun = true)
    public void afterTestReport() {
    }

    @BeforeSuite(alwaysRun = true)
    public void beforeSuite() {
        try {
            deleteOldTestReportFiles();
        }catch (Exception e){
            LOGGER.info("No found old report files.");
        }
    }


    @AfterSuite(alwaysRun = true)
    public void afterSuite() {
        terminateApp(getData("activity.main_" + getOperatingSystem() + ""));
    }

}
