package pageObject.pages.mmjtraine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.pop_ups.CancleOrderPopUp;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by NGorodenchuk on 10/5/2017.
 */
public class CreateNewPasswordPage extends Page {

    @FindBy(how = How.XPATH, using = "//input[@id='ForgotpasswordNewPassword']")
    private WebElement newPasswordField;

    public CreateNewPasswordPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(newPasswordField);
    }

    private static final Logger LOG = LogManager.getLogger(CreateNewPasswordPage.class);


}
