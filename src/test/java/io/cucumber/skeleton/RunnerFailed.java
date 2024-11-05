package io.cucumber.skeleton;


import com.Ebebek.driver.DriverManager;
import com.Ebebek.utils.ConfigManager;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.net.MalformedURLException;

import static base.AutomationMethods.terminateApp;
import static com.Ebebek.utils.ConfigManager.getOperatingSystem;
import static com.Ebebek.utils.DataManager.getData;
import static com.Ebebek.utils.Helper.deleteOldTestReportFiles;


@CucumberOptions(
        features = "@target/failedrerun.txt",
        glue = "io.cucumber.skeleton",
        monochrome = true,
        plugin = {"summary", "pretty",
                "html:target/cucumber-reports.html",
                "json:target/cucumber.json",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "rerun:target/failedrerun.txt"}
)

public class RunnerFailed extends AbstractTestNGCucumberTests {
    private static final Logger LOGGER = LogManager.getLogger(RunnerFailed.class);

    @BeforeMethod
    @Parameters({ "deviceName", "platformVersion", "platformName" })
    public void startDriver(ITestResult result,String deviceName,String platformVersion,String platformName) throws MalformedURLException {
        DriverManager.launchBrowser(ConfigManager.getPlatform(),deviceName,platformVersion,platformName);
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
