package pageobject.facadetest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.mmjtraine.businessobject.geolocationstrategy.ManualLocation;
import pageObject.mmjtraine.businessobject.productitembuilder.PortionProductItem;
import pageObject.mmjtraine.businessobject.productitembuilder.QuantityProductItem;
import pageObject.mmjtraine.businessobject.useresfacade.UserHelper;
import pageObject.mmjtraine.pages.MenuPage;
import pageObject.mmjtraine.pop_ups.SignInPopUp;
import pageobject.testcase.TestBase;

public class DemoTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(DemoTestSuit.class);
   UserHelper userHelper = new UserHelper();

    @Test(enabled = true)
    // TS 3.1
    public void checkLogInAdminUser() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.signInUserWithInValidData(userHelper.readAdminUserEmail(), userHelper.readAdminUserPassword(), new SignInPopUp(webDriver));
        Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='signinform']/p[.='Unknown user.']")).isDisplayed(), "User with INVALID data was able to log in");
    }

    @Test(enabled = true)
    // TS 3.1
    public void checkLogInManagerUser() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.signInUserWithInValidData(userHelper.readManagerUserEmail(), userHelper.readManagerUserPassword(), new SignInPopUp(webDriver));
        Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='signinform']/p[.='Unknown user.']")).isDisplayed(), "User with INVALID data was able to log in");
    }

    @Test(enabled = true)
    // TS 3.1
    public void checkLogInShipperUser() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.signInUserWithInValidData(userHelper.readShipperUserEmail(), userHelper.readShipperUserPassword(), new SignInPopUp(webDriver));
        Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='signinform']/p[.='Unknown user.']")).isDisplayed(), "User with INVALID data was able to log in");
    }

}
