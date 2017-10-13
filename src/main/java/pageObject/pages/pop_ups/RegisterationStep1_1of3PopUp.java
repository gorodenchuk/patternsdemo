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
import pageObject.pages.mmjtraine.MenuPage;
import pageObject.utility.DB.DataBaseExecute;
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;

public class RegisterationStep1_1of3PopUp extends Page {

	@FindBy(how = How.XPATH, using = "//img[@class='close-register nav-link']")
	private WebElement closseButton;

	@FindBy(how = How.XPATH, using = "//form[@id='verify']/input[@class='standard-input']")
	private WebElement phoneField;

	@FindBy(how = How.XPATH, using = "//span[@class='custom-checkbox']")
	private WebElement checkBox;

	@FindBy(how = How.XPATH, using = "//form[@id='verify']/button[@class='btn-main btn-standard btn-message btn-sendcode']")
	private WebElement sendVerificationCode;

	private static final Logger LOG = LogManager.getLogger(RegisterationStep2_1of3PopUp.class);


	public RegisterationStep1_1of3PopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(phoneField);
	}


	@Step
	public MenuPage clickOnCloseButton() {

//		click on WebElement by JS
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		executor.executeScript("arguments[0].click();", closseButton);

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public RegisterationStep1_2of3PopUp setValidDataAndClcikSendVerificationButton() {
		phoneField.sendKeys("8689745451");
//		click on WebElement by JS
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		executor.executeScript("arguments[0].click();", checkBox);
		sendVerificationCode.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_2of3PopUp.class);
	}

	@Step
	public RegisterationStep1_2of3PopUp setNumberWith9DigitsAndClickSendVerificationButton() {
		phoneField.sendKeys("860971970");
//		click on WebElement by JS
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		executor.executeScript("arguments[0].click();", checkBox);
		sendVerificationCode.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_2of3PopUp.class);
	}

	@Step
	public RegisterationStep1_2of3PopUp setValidPhoneButCheckBoxLeftNotActiveAndClcikSendVerificationButton() {
		phoneField.sendKeys("8609719707");
		sendVerificationCode.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_2of3PopUp.class);
	}

	@Step
	public RegisterationStep1_2of3PopUp ClcikSendVerificationButton() {
		sendVerificationCode.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_2of3PopUp.class);
	}

	@Step
	public RegisterationStep1_2of3PopUp ClcikSendVerificationButtonWhithEnterredValidPhoneNumber() {
		phoneField.sendKeys("8609719707");
		sendVerificationCode.click();
		return PageFactory.initElements(webDriver, RegisterationStep1_2of3PopUp.class);
	}


	@Step
	public RegisterationStep2_1of3PopUp verifyUserPhoneNumberInDBTEST(String generNumber1) throws InterruptedException, SQLException {
		Users.updatePhone_VerifyToApprove(generNumber1);
		Users.updatePhone(generNumber1);
		webDriver.navigate().to(WEB_URL + "photoid");

		return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
	}

	@Step
	public RegisterationStep2_1of3PopUp verifyUserPhoneNumberInDB(String generNumber1) throws InterruptedException, SQLException {
		Users.updatePhone_VerifyToApprove(generNumber1);
		Users.updatePhone(generNumber1);
        webDriver.navigate().to(WEB_URL + "photoid");

		return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
	}




}
