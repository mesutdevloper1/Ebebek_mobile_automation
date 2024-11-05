package io.cucumber.skeleton;

import base.AutomationMethods;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.Ebebek.utils.ConfigManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;

import static base.ProjectMethods.*;
import static com.Ebebek.utils.ConfigManager.getOperatingSystem;
import static com.Ebebek.utils.DataManager.getData;
import static com.Ebebek.utils.Helper.sleepInSeconds;


public class StepDefinitionsGeneral extends AutomationMethods {

    private static final Logger LOGGER = LogManager.getLogger(StepDefinitionsGeneral.class);


    @Given("click {string}")
    public void clickElement(String element) throws Exception {
        click(element);
    }

    @And("enter {string} in {string}")
    public void sendKeysInput(String text,String element) throws Exception {
        sendKeys(element,text);
    }

    @Given("open app")
    public void openApp() {
        openApp(getData("activity.main_" + getOperatingSystem() + ""));
    }


    @And("skip onboarding screens")
    public void skipOnboarding() throws Exception {
        click("SPLASH_SCREEN_BASLA_BUTTON");
        click("SPLASH_SCREEN_ATLA_BUTTON");

    }


    @And("i see {string} text")
    public void iSeeTextInPage(String text) throws Exception {
        Assert.assertTrue(findTextOnPage(text));
    }

    @And("i see {string} element")
    public void iSeeElementInPage(String text) throws Exception {
        Assert.assertTrue(elementVisibiltyWithSize(text));
    }

    @And("i cant see {string} element")
    public void iCantSeeElementInPage(String text) throws Exception {
        Assert.assertFalse(elementVisibiltyWithSize(text));
    }

    @And("i cant see {string} text")
    public void iCantSeeTextInPage(String text) throws Exception {
        Assert.assertFalse(findTextOnPage(text));
    }

    @And("swipe right")
    public void swipeRight() throws Exception {
        swipeScreen("right");
    }

    @And("swipe left")
    public void swipeLeft() throws Exception {
        swipeScreen("left");
    }


    @And("app goes to background")
    public void appGoesToBackground() throws Exception {
        appBackgroundStay();
    }

    @And("app goes to foreground")
    public void appGoesToForeground() throws Exception {
        appForeground(getData("activity.main_" + getOperatingSystem() + ""));
    }

    @And("wait {string} second")
    public void waitSecond(String second) throws Exception {
        sleepInSeconds(Integer.parseInt(second));
    }

    @And("kill app")
    public void killApp() throws Exception {
        terminateApp(getData("activity.main_" + getOperatingSystem() + ""));
    }

    @And("i see the keyboard is on")
    public void keyboardOn() throws Exception {
        Assert.assertTrue(isKeyboardShowStatus());
    }

    @And("i see the keyboard is off")
    public void keyboardOff() throws Exception {
        Assert.assertFalse(isKeyboardShowStatus());
    }

    @And("i see {string} button is clickable")
    public void buttonIsClickable(String element) throws Exception {
     Assert.assertTrue(isButtonClickableStatus(element));
    }

    @And("i see {string} button is not clickable")
    public void buttonIsNotClickable(String element) throws Exception {
        Assert.assertFalse(isButtonClickableStatus(element));
    }

    @And("i see {string} input is selected")
    public void inputIsSelected(String element) throws Exception {
        Assert.assertTrue(isInputSelectedStatus(element));
    }

    @And("enter {string} with keyboard")
    public void inputTextWithKeyboard(String text) throws Exception {
        sendKeysWithKeyboard(text);
    }

}
