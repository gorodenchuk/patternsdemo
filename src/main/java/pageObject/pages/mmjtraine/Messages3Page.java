package pageObject.pages.mmjtraine;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

public class Messages3Page extends Page {

	@FindBy(how = How.XPATH, using = "//a[@class='message-link text btn-autolocation']")
	private WebElement tryDetectAgainButton;

	@FindBy(how = How.XPATH, using = "//input[@id='address-geo']")
	private WebElement addressField;

	public Messages3Page(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(addressField);
	}

	public MenuPage clickOnTryDetectAgainButton() {
		tryDetectAgainButton.click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage setPlaceNameInAddressFielsAndSelectResult(Map<String,String> testData) {
		addressField.sendKeys(testData.get("Places"));
		webDriver.findElement(By.xpath("//span[@class='pac-item-query']//span[contains(text(),'" + testData.get("Places") + "')]")).click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage setPlaceNameInAddressFielsAndSelectResult(String places) {
		addressField.sendKeys(places);
		webDriver.findElement(By.xpath("//span[@class='pac-item-query']//span[contains(text(),'" + places + "')]")).click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

}
