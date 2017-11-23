package pageObject.mmjtraine.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import pageObject.mmjtraine.businessobject.geolocationstrategy.GeolocationStrategy;
import pageObject.mmjtraine.pop_ups.SignInPopUp;
import ru.yandex.qatools.allure.annotations.Step;

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
	public MenuPage giveGeolocation(GeolocationStrategy geolocationStrategy) throws InterruptedException {
		geolocationStrategy.setLocation();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public SignInPopUp clickOnSignInButton() {
		signInButton.click();
		return PageFactory.initElements(webDriver, SignInPopUp.class);
	}

}
