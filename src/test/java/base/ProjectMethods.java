package base;


import com.Ebebek.driver.DriverManager;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.cucumber.skeleton.StepDefinitionsGeneral;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;


import static base.AutomationMethods.*;
import static com.Ebebek.utils.ConfigManager.getOperatingSystem;
import static com.Ebebek.utils.ElementManager.returnElement;

public class ProjectMethods {

    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsGeneral.class);

    public static boolean findTextOnPage(String text) throws Exception {
        if (getOperatingSystem().equals("android")) {
            return DriverManager.getDriver().findElements(MobileBy.xpath("//android.view.View[@content-desc='" + text + "']")).size() > 0;

        } else if (getOperatingSystem().equals("ios")) {
            return false;  // yazılacak
        } else {
            LOGGER.error("Unidentified system.");
            return false;
        }
    }


    public static boolean isKeyboardShowStatus() throws Exception {
        AndroidDriver androidDriver = (AndroidDriver) driver;
        return androidDriver.isKeyboardShown();
    }

    public static boolean isButtonClickableStatus(String element) throws Exception {
        By by = returnElement(element);
        return driver.findElement(by).isEnabled();
    }

    public static boolean isInputSelectedStatus(String element) throws Exception {
        By by = returnElement(element);
        return driver.findElement(by).isSelected();
    }

}
