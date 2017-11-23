package pageObject.mmjtraine.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.mmjtraine.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

public class ResetYourPasswordPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//div[@style='']//input[@class='standard-input']")
	private WebElement yourEmailField;

	@FindBy(how = How.XPATH, using = "//div[@style='']//button[@class='btn-main btn-standard']")
	private WebElement resetPasswordButton;

	@FindBy(how = How.XPATH, using = "//div[@style='']//span[@class='back_btn']")
	private WebElement backToLoginButton;


	public ResetYourPasswordPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = LogManager.getLogger(ResetYourPasswordPopUp.class);

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(yourEmailField);
	}

	@Step
	public ResetYourPasswordPopUp resetPassword(String email){
		yourEmailField.sendKeys(email);
		resetPasswordButton.click();
		return PageFactory.initElements(webDriver, ResetYourPasswordPopUp.class);
	}

	@Step
	public SignInPopUp clickOnBackToLoginButton(){
		backToLoginButton.click();
		return PageFactory.initElements(webDriver, SignInPopUp.class);
	}
}
