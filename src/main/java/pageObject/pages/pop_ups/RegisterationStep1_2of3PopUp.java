package pageObject.pages.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.utility.DB.DataBaseExecute;
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;

public class RegisterationStep1_2of3PopUp extends Page {

	@FindBy(how = How.XPATH, using = "//img[@class='close-register nav-link']")
	private WebElement closseButton;

	@FindBy(how = How.XPATH, using = "//form[@id='confirm']/input[@class='standard-input text-center']")
	private WebElement verificationField;

	@FindBy(how = How.XPATH, using = "//form[@id='confirm']/button[@class='btn-main btn-standard btn-message btn-confirmcode']")
	private WebElement confirmButton;

	@FindBy(how = How.XPATH, using = "//form[@id='confirm']//span[@class='resend-btn text-color-2']")
	private WebElement resendCodeButton;

	private static final Logger LOG = LogManager.getLogger(RegisterationStep1_2of3PopUp.class);


	public RegisterationStep1_2of3PopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(verificationField);
	}

	@Step
	public RegisterationStep2_1of3PopUp verifyRecievedCode(String generNumber1) throws InterruptedException, SQLException {

		Thread.sleep(3000);

//		JS select phone_code from DB and set it like value in to html
		String js1 = "document.getElementsByTagName('input')[2].setAttribute('value','" + Users.selectPhone_Code(generNumber1) + "')";
		String js2 = "document.getElementsByTagName('button')[1].removeAttribute('disabled')";
		((JavascriptExecutor) webDriver).executeScript(js1);
		Thread.sleep(3000);
		((JavascriptExecutor) webDriver).executeScript(js2);
		Thread.sleep(3000);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", confirmButton);
		Thread.sleep(3000);

		return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
	}
//
////	Need to rewrite some wait
	@Step
	public RegisterationStep2_1of3PopUp clcikOnResendCodeButton(String generNumber1) throws InterruptedException, SQLException {
		resendCodeButton.click();

		Thread.sleep(3000);

		// this part is wrong i can`t click

//		JS select phone_code from DB and set it like value in to html
		String js1 = "document.getElementsByTagName('input')[0].setAttribute('value','" + Users.selectPhone_Code(generNumber1) + "')";
		String js2 = "document.getElementsByTagName('button')[1].removeAttribute('disabled')";
		((JavascriptExecutor) webDriver).executeScript(js1);
		Thread.sleep(3000);
		((JavascriptExecutor) webDriver).executeScript(js2);
		Thread.sleep(3000);
		((JavascriptExecutor) webDriver).executeScript("arguments[0].click();", confirmButton);
		Thread.sleep(3000);


		verificationField.sendKeys(Users.selectPhone_Code(generNumber1));
		fluentWaitUntilElementBecomeClicable(confirmButton, "'Confirm' button").click();
		return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
	}









}
