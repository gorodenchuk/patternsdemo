package pageObject.pages.adminpanel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.adminpanel.commonWidgets.NavigationPanel;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by NGorodenchuk on 8/15/2017.
 */
public class LoginPage extends Page{

    @FindBy(how = How.XPATH, using = "//input[@id='ManagerUsername']")
    private WebElement userNameField;

    @FindBy(how = How.XPATH, using = "//input[@id='ManagerUserpass']")
    private WebElement passwordField;

    @FindBy(how = How.XPATH, using = "//input[@class='btn-main']")
    private WebElement submitButton;

    private static final Logger LOG = LogManager.getLogger(LoginPage.class);

    public LoginPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(userNameField);
    }

    @Step
    public NavigationPanel loginSuperAdmin() {
        userNameField.sendKeys("test@test.com");
        passwordField.sendKeys("1234");
        submitButton.click();
        return PageFactory.initElements(webDriver, NavigationPanel.class);
    }

}
