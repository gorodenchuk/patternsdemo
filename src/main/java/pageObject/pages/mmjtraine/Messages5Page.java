package pageObject.pages.mmjtraine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

public class Messages5Page extends Page {

	@FindBy(how = How.XPATH, using = "//a[@class='message-link text']")
	private WebElement ContinueWithMyCurrentLocationButton;

	@FindBy(how = How.XPATH, using = "//span[@class='text text-color']")
	private WebElement yourLocationButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='address-geo']")
	private WebElement addressField;

	public Messages5Page(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(addressField);
	}

	@Step
	public MenuPage clickOnContinueWithMyCurrentLocationButton() {
		ContinueWithMyCurrentLocationButton.click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage clickOnYourLocationButton() {
		yourLocationButton.click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage setPlaceInAddressField(String place) throws InterruptedException {
		addressField.sendKeys(place);
		addressField.findElement(By.xpath("//span[@class='pac-item-query']//span[contains(text(),'" + place + "')]")).click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}






}
