package pageObject.pages.mmjtraine;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

public class Messages1Page extends Page {

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-standard btn-message btn-autolocation']")
	private WebElement okIUnderstandButton;

	@FindBy(how = How.XPATH, using = "//a[@class='message-link text btn-manually']")
	private WebElement iWillEnterMyLocationManuallyButton;

	public Messages1Page(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(iWillEnterMyLocationManuallyButton);
	}

	@Step
	public MenuPage clickOnOkIUnderstandButton() {
		okIUnderstandButton.click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public Messages3Page clickOnIWillEnterMyLocationManuallyButton() {
		iWillEnterMyLocationManuallyButton.click();
		return PageFactory.initElements(webDriver, Messages3Page.class);
	}

}
