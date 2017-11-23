package pageObject.mmjtraine.businessobject.geolocationstrategy;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObject.mmjtraine.pages.Page;

public class AutoLocation extends Page implements GeolocationStrategy {

    @FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-geo']")
    private WebElement geolocationButton;

    public AutoLocation(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public void setLocation() {
        WebElement geolocationButton = webDriver.findElement(By.xpath("//button[@class='btn-main btn-geo']"));
        geolocationButton.click();
    }


}
