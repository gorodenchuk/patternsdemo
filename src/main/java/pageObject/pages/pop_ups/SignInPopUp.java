package pageObject.pages.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.pages.Page;
import pageObject.pages.mmjtraine.MenuPage;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

public class SignInPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//form[@id='signinform']//input[@name='email']")
	private WebElement emailField;

	@FindBy(how = How.XPATH, using = "//form[@id='signinform']//input[@name='password']")
	private WebElement passwordField;

	@FindBy(how = How.XPATH, using = "//p[@class='text-center text']/span[@id='sign_up_btn']")
	private WebElement signUpButton;

	@FindBy(how = How.XPATH, using = ".//*[@id='signinform']/button")
	private WebElement signInButton;

	@FindBy(how = How.XPATH, using = "//div[@style='display: block; padding-right: 17px;']//span[@id='forgot_btn']")
	private WebElement forgotYourPasswordButton;

	public SignInPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = LogManager.getLogger(SignInPopUp.class);

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(emailField);
	}

	@Step
	public ResetYourPasswordPopUp clickOnForgotYourPasswordButton() {
		WebDriverWait wait = new WebDriverWait(webDriver, 20);
		forgotYourPasswordButton = wait.until(ExpectedConditions.elementToBeClickable(forgotYourPasswordButton));
		forgotYourPasswordButton.click();
		return PageFactory.initElements(webDriver, ResetYourPasswordPopUp.class);
	}

	@Step
	public SignUpPopUp clickOnSignUpButton() throws InterruptedException {
		fluentWaitUntilElementBecomeClicable(signUpButton, "'SignUp' button").click();

		//		click on WebElement by JS
//		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
//		executor.executeScript("arguments[0].click();", waitUntilElementBecomeClicable(signUpButton, "'SignUp' button"));

			return PageFactory.initElements(webDriver, SignUpPopUp.class);
	}


	@Step
	public MenuPage signInUser1ValidDatA(String generNumber1) throws InterruptedException {
		emailField.sendKeys("mmjtrain.test" + generNumber1 + "@gmail.com");
		passwordField.sendKeys("1234");
		fluentWaitUntilElementBecomeClicable(signInButton, "'SignIn' button").click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public void signInUser1WithChangedPasswordInProfile(String generNumber1)  throws InterruptedException {
		emailField.sendKeys("mmjtrain.test" + generNumber1 + "@gmail.com");
		passwordField.sendKeys("12345");
		fluentWaitUntilElementBecomeClicable(signInButton, "'SignIn' button").click();
	}

	@Step
	public void signInUser1WithChangedEmailInProfile(String generNumber1)  throws InterruptedException {
		emailField.sendKeys("test" + generNumber1 + "@gmail.com");
		passwordField.sendKeys("1234");
		fluentWaitUntilElementBecomeClicable(signInButton, "'SignIn' button").click();
	}

	@Step
	public void signInUser1UsingInvalidData(Map<String,String> testData) throws InterruptedException {
		emailField.sendKeys(testData.get("Email"));
		passwordField.sendKeys(testData.get("Password"));
		fluentWaitUntilElementBecomeClicable(signInButton, "'SignIn' button").click();
	}

}
