package pageObject.pages.mmjtraine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import pageObject.pages.Page;
import pageObject.pages.pop_ups.RegisterationStep2_2of3PopUp;
import pageObject.pages.pop_ups.SignInPopUp;
import pageObject.pages.pop_ups.SignUpPopUp;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

public class HomePage extends Page {

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-geo']")
	private WebElement GeolocationButton;

	@FindBy(how = How.XPATH, using = "//input[@class='standard-input search-input address-geo']")
	private WebElement addressField;

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-border btn-signin']")
	private WebElement signInButton;

	private static final Logger LOG = LogManager.getLogger(HomePage.class);


	public HomePage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(addressField);
	}

	@Step
	public MenuPage clickOnGeolocationButton() {
		GeolocationButton.click();
			return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage setPlaceNameInAddressFielsAndSelectResult() {
		addressField.sendKeys("Lviv");
		webDriver.findElement(By.xpath("//span[@class='pac-item-query']//span[contains(text(),'" + "Lviv" + "')]")).click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage setPlaceNameInAddressFielsAndSelectResult(Map<String,String> testData) {
        addressField.sendKeys(testData.get("Places"));
        webDriver.findElement(By.xpath("//span[@class='pac-item-query']//span[contains(text(),'" + testData.get("Places") + "')]")).click();
        return PageFactory.initElements(webDriver, MenuPage.class);
    }

	@Step
	public Messages1Page openMenuPageWithoutGivingLocation() {
		webDriver.get(WEB_URL + "menu");
		return PageFactory.initElements(webDriver, Messages1Page.class);
	}

	@Step
	public MenuPage openMenuPage() {
		webDriver.get(WEB_URL + "menu");
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public SignInPopUp clickOnSignInButton() {
		signInButton.click();
		return PageFactory.initElements(webDriver, SignInPopUp.class);
	}


}
