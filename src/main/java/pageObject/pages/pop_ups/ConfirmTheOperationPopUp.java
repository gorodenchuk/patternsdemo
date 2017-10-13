package pageObject.pages.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.adminpanel.pages.OrdersPage;
import ru.yandex.qatools.allure.annotations.Step;

public class ConfirmTheOperationPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//div[@id='delete']//button[@class='delete_confirm btn-main btn-yes']")
	private WebElement yesButton;

	@FindBy(how = How.XPATH, using = "//div[@id='delete']//button[@class='delete_not_confirm btn-main btn-no']")
	private WebElement noOrderButton;

	public ConfirmTheOperationPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = LogManager.getLogger(ConfirmTheOperationPopUp.class);

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(yesButton);
	}

	@Step
	public OrdersPage clickOnYesButton(){
		yesButton.click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

}
