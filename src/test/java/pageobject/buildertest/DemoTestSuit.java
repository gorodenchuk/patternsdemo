package pageobject.buildertest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.mmjtraine.businessobject.geolocationstrategy.ManualLocation;
import pageObject.mmjtraine.businessobject.productitembuilder.PortionProductItem;
import pageObject.mmjtraine.businessobject.productitembuilder.QuantityProductItem;
import pageObject.mmjtraine.pages.MenuPage;
import pageobject.testcase.TestBase;

public class DemoTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(DemoTestSuit.class);

    @Test(enabled = true)
    // TS 2.1
    public void checkRedirectionToMenuPageAfterAddingLocationManually() throws Exception {

        MenuPage menuPage = homePage.giveGeolocation(new ManualLocation(webDriver,"San Francisco"));
        menuPage.IsLoaded();
        PortionProductItem portionProductItem = new PortionProductItem.
                PortionProductItemBuilder("flowers", "Girl Scout Cookies", "hybrid").
                setEnabledPortion_1_8("1/8 oz").
                setEnabledPortion_1_6("1/6 oz").
                build();
        menuPage.selectPortionProductItem(portionProductItem).addToCartPortionProductItem(portionProductItem);

        QuantityProductItem quantityProductItem = new QuantityProductItem.
                QuantityProductItemBuilder("prerolls", "testproduct1342", "hybrid")
                .setQuantity(3)
                .build();
        menuPage.selectQuantityProductItem(quantityProductItem).addToCartQuantityProductItem(quantityProductItem);


        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']"))
                .isDisplayed(), "Checkout button should be orange");
    }

}
