package pageObject.mmjtraine.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.mmjtraine.businessobject.useresfacade.UserHelper;
import pageObject.mmjtraine.pages.Page;
import pageObject.mmjtraine.pages.MenuPage;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

public class SignInPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//form[@id='signinform']//input[@name='email']")
	private WebElement emailField;

	@FindBy(how = How.XPATH, using = "//form[@id='signinform']//input[@name='password']")
	private WebElement passwordField;

	@FindBy(how = How.XPATH, using = ".//*[@id='signinform']/button")
	private WebElement signInButton;

	public SignInPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = LogManager.getLogger(SignInPopUp.class);

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(emailField);
	}


	public <T extends Page> T signInUserWithInValidData(String userEmail, String userPassword, T page) throws InterruptedException {
		emailField.sendKeys(userEmail);
		passwordField.sendKeys(userPassword);
		fluentWaitUntilElementBecomeClicable(signInButton, "'SignIn' button").click();
		return page;

	}

}
