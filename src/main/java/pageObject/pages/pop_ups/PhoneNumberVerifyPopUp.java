package pageObject.pages.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

public class PhoneNumberVerifyPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//form[@id='verify_form']/button[@class='btn-main btn-standard btn-message btn-sendcode']")
	private WebElement SendVerificationCodeButton;

	private static final Logger LOG = LogManager.getLogger(RegisterationStep2_1of3PopUp.class);

	public PhoneNumberVerifyPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(SendVerificationCodeButton);
	}


	@Step
	public EnterVerificationCodePopUp clickOnSendVerificationCodeButton() {
		SendVerificationCodeButton.click();
		return PageFactory.initElements(webDriver, EnterVerificationCodePopUp.class);
	}


}
