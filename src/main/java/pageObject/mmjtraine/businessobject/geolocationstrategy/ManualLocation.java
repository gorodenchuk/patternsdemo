package pageObject.mmjtraine.businessobject.geolocationstrategy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObject.mmjtraine.pages.HomePage;
import pageObject.mmjtraine.pages.Page;

public class ManualLocation extends Page implements GeolocationStrategy {

    @FindBy(how = How.XPATH, using = "//input[@id='address-geo']")
    public WebElement addressField;

    private String location;
    private static final Logger LOG = LogManager.getLogger(HomePage.class);

    public  ManualLocation(WebDriver webDriver, String location){
        super(webDriver);
        this.location = location;
    }

    @Override
    public void setLocation(){
        WebElement addressField = webDriver.findElement(By.xpath("//input[@id='address-geo']"));
        addressField.clear();
        addressField.sendKeys(location);
        webDriver.findElement(By.xpath("//span[@class='pac-item-query']//span[contains(text(),'" + location + "')]")).click();
    }


}
