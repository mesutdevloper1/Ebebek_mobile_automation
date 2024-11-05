package base;

import com.Ebebek.driver.DriverManager;
import io.appium.java_client.*;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

import static com.Ebebek.driver.DriverManager.getDriver;
import static com.Ebebek.driver.DriverManager.quitDriver;
import static com.Ebebek.utils.ElementManager.returnElement;
import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.ElementOption.element;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;


public class AutomationMethods {

    static AppiumDriver<MobileElement> driver = (AppiumDriver<MobileElement>) getDriver();
    private DriverManager mobileDriver;


    public static void click(String element) throws Exception {
        By by = returnElement(element);
        DriverManager.getDriver().findElement(by).click();
    }

    public static void touch(String element) throws Exception {
        By by = returnElement(element);
        new TouchAction(driver)
                .tap(element((WebElement) by))
                .waitAction(waitOptions(ofMillis(250))).perform();
    }

    public static void tap(String element) throws Exception {
        By by = returnElement(element);
        new TouchAction(driver)
                .tap(element((WebElement) by)).perform();
    }

    public static void touchLocation(int xPoint, int yPoint) {
        TouchAction touchAction = new TouchAction(driver);
        touchAction.tap(point(xPoint, yPoint)).perform();
        //touchAction.tap(xPoint, yPoint).perform();
    }

    public static void sendKeysWithClick(String element, String text) throws Exception {
        By by = returnElement(element);
        click(element);
        DriverManager.getDriver().findElement(by).sendKeys(text);
    }

    public static void sendKeys(String element, String text) throws Exception {
        By by = returnElement(element);
        driver.findElement(by).sendKeys(text);
    }


    public static boolean elementVisibiltyWithSize(String element) throws Exception {
        By by = returnElement(element);
        return DriverManager.getDriver().findElements(by).size() > 0;
    }

    public static void back() {
        getDriver().navigate().back();
    }


    public static void appBackground() {
        driver.runAppInBackground(Duration.ofSeconds(3));
    }

    public static void appBackgroundStay() {
        driver.runAppInBackground(Duration.ofSeconds(-1));
    }

    public static void appForeground(String app) {
        driver.activateApp(app);
    }

    public static void openApp(String string) {
        driver.activateApp(string);
    }

    public static void terminateApp(String string) {
        driver.terminateApp(string);
    }

    public static void uninstallApp(String string) {
        driver.removeApp(string);
    }

    public static void hideKeyboard() {
        driver.hideKeyboard();
    }

    public static void waitElement(MobileElement mobileElement) {
        WebDriverWait wait = new WebDriverWait(driver, 15);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(String.valueOf(mobileElement))));
    }


    public static boolean checkIfWebElementExists(By by) {
        try {
            if (getDriver().findElement(by).isDisplayed() && getDriver().findElement(by).isEnabled()) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }


    public void checkTextContains(String key, String text) {
        int i = 0;
        boolean check = false;
        String elementText = null;
        do {
            MobileDriver element = findElementByKeySafe(key);
            if (element == null) {
                //swipeScreen(Direction.UP);
                i++;
            } else {
                //  elementText = element.getText();
                //element.checkTextContains(text);
                check = true;
                i = 3;
            }

        } while (i < 3);
        if (check == false) ;
        //Assert.fail("\nExpected:"+text+"\nBut was:"+elementText);
    }

    private MobileDriver findElementByKeySafe(String key) {

        return null;
    }


    public void swipeScreen(String direction) {
        Dimension size = driver.manage().window().getSize();
        int startX;
        int startY;
        int endX;
        int endY;
        TouchAction touchAction = new TouchAction(driver);
        switch (direction) {
            case "right":
                startX = (int) (size.getWidth() * 0.9);
                startY = (int) (size.getHeight() * 0.5);
                endX = (int) (size.getWidth() * 0.5);

                touchAction.longPress(PointOption.point(startX, startY))
                        .moveTo(PointOption.point(endX, startY))
                        .release()
                        .perform();
                break;
            case "left":
                startX = (int) (size.getWidth() * 0.1);
                startY = (int) (size.getHeight() * 0.5);
                endX = (int) (size.getWidth() * 0.5);

                touchAction.longPress(PointOption.point(startX, startY))
                        .moveTo(PointOption.point(endX, startY))
                        .release()
                        .perform();
                break;
            case "up":
                startY = (int) (size.getWidth() * 0.1);
                startX = (int) (size.getWidth() * 0.5);
                endY = (int) (size.getHeight() * 0.5);
                touchAction.longPress(PointOption.point(startX, startY))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
                break;
            case "down":
                startY = (int) (size.getWidth() * 0.9);
                startX = (int) (size.getWidth() * 0.5);
                endY = (int) (size.getHeight() * 0.5);
                touchAction.longPress(PointOption.point(startX, startY))
                        .moveTo(PointOption.point(startX, endY))
                        .release()
                        .perform();
                break;
        }
    }

    public static void sendKeysWithKeyboard(String text) throws Exception {
        driver.getKeyboard().sendKeys(text);
    }

}


