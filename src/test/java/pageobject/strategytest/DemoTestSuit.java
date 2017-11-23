package pageobject.strategytest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.mmjtraine.businessobject.geolocationstrategy.AutoLocation;
import pageObject.mmjtraine.businessobject.geolocationstrategy.ManualLocation;
import pageObject.mmjtraine.pages.MenuPage;
import pageobject.testcase.TestBase;

public class DemoTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(DemoTestSuit.class);

    @Test(enabled = true)
    // TS 1.1
    public void checkRedirectionToMenuPageAfterAddingLocationManually() throws Exception {
        MenuPage menuPage = homePage.giveGeolocation(new ManualLocation(webDriver,"San Francisco"));
        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location']/span[.='Your Location']"))
                .isDisplayed(), "page isn't redirected on Menu, or something goes wrong");
    }

    @Test(enabled = true)
    // TS 1.2
    public void checkRedirectionToMenuPageAfterAddingLocationAutomatically() throws Exception {
        MenuPage menuPage = homePage.giveGeolocation(new AutoLocation(webDriver));
        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location']/span[.='Your Location']"))
                .isDisplayed(), "page isn't redirected on Menu, or something goes wrong");
    }

}
