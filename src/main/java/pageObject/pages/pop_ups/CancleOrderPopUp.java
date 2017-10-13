package pageObject.pages.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.mmjtraine.CheckOutPage;
import pageObject.pages.mmjtraine.ProfilePage;
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancleOrderPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//div[@style='display: block; padding-right: 17px;']//button[@class='btn-main btn-border btn-yes']")
	private WebElement yesButton;

	public CancleOrderPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = LogManager.getLogger(CancleOrderPopUp.class);

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(yesButton);
	}

	@Step
	public ProfilePage clickYesButton(){
		yesButton.click();
		return PageFactory.initElements(webDriver, ProfilePage.class);
	}

}
