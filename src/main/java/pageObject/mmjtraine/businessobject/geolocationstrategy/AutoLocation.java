package pageObject.mmjtraine.businessobject.geolocationstrategy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObject.mmjtraine.pages.Page;

public class AutoLocation extends Page implements GeolocationStrategy {

    public AutoLocation(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void setLocation() {
        WebElement geolocationButton = webDriver.findElement(By.xpath("//button[@class='btn-main btn-geo']"));
        geolocationButton.click();
    }


}
