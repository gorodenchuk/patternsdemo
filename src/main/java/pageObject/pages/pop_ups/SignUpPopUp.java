package pageObject.pages.pop_ups;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObject.pages.Page;
import pageObject.utility.RandomNumberGenerator;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Map;

public class SignUpPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//form[@id='signup']//input[@name='email']")
	private WebElement emailField;

	@FindBy(how = How.XPATH, using = "//form[@id='signup']//input[@name='password']")
	private WebElement passwordField;

	@FindBy(how = How.XPATH, using = "//form[@id='signup']//*[@id='zip']")
	private WebElement zipCodeField;

	@FindBy(how = How.XPATH, using = ".//*[@id='sign_in_btn']")
	private WebElement signInButton;

	@FindBy(how = How.XPATH, using = "//form[@id='signup']/button[@class='btn-main btn-standard signup_btn']")
	private WebElement signUpButton;

	public SignUpPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(zipCodeField);
	}

	@Step
	public void setTwoNumberInZipCodeField(String twoDigits) {
		zipCodeField.sendKeys(twoDigits);
	}

	@Step
	public RegisterationStep1_1of3PopUp signUpUser1ValidDatATEST(String generNumber1) throws InterruptedException {
		emailField.sendKeys("mmjtrain.test" + generNumber1 + "@gmail.com");
		passwordField.sendKeys("1234");
		zipCodeField.sendKeys("94103");
		signUpButton.sendKeys(Keys.RETURN);
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}

	@Step
	public RegisterationStep1_1of3PopUp signUpUser1ValidDatA(String generNumber1) throws InterruptedException {

		emailField.sendKeys("mmjtrain.test" + generNumber1 + "@gmail.com");
		fluentWaitUntilElementBecomeClicable(passwordField, "'Password' field").sendKeys("1234");
		zipCodeField.sendKeys("94103");
		signUpButton.sendKeys(Keys.RETURN);
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}

	@Step
	public RegisterationStep1_1of3PopUp signUpUserWithRealEmailAddress(String generNumber1) throws InterruptedException {

		emailField.sendKeys("mmjtrain.test" + generNumber1 + "@gmail.com");
		fluentWaitUntilElementBecomeClicable(passwordField, "'Password' button").sendKeys("1234");
		zipCodeField.sendKeys("94103");
		signUpButton.sendKeys(Keys.RETURN);
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}

	@Step
	public RegisterationStep1_1of3PopUp signUpUser2ValidDatA(String generNumber2) throws InterruptedException {

		emailField.sendKeys("mmjtrain.test" + generNumber2 + "@gmail.com");
		fluentWaitUntilElementBecomeClicable(passwordField, "'Password' button").sendKeys("1234");
		zipCodeField.sendKeys("44001");
		signUpButton.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}

	@Step
	public RegisterationStep1_1of3PopUp signUpWithNotValidData(Map<String,String> testdata) {
		emailField.sendKeys(testdata.get("Email"));
		passwordField.sendKeys(testdata.get("Password"));
		zipCodeField.sendKeys(testdata.get("ZipCode"));
		signUpButton.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}

	@Step
	public RegisterationStep1_1of3PopUp signUpWithValidData(Map<String,String> testdata) {
		emailField.sendKeys(testdata.get("Email"));
		passwordField.sendKeys(testdata.get("Password"));
		zipCodeField.sendKeys(testdata.get("ZipCode"));
		signUpButton.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}

	@Step
	public RegisterationStep1_1of3PopUp refreshSignUpPopUp(){
		webDriver.navigate().refresh();
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}


}
