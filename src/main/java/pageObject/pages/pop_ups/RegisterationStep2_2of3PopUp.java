package pageObject.pages.pop_ups;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by NGorodenchuk on 7/13/2017.
 */
public class RegisterationStep2_2of3PopUp extends Page {

    @FindBy(how = How.XPATH, using = "//img[@class='close-register nav-link']")
    private WebElement closseButton;

    @FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-standard btn-message btn-continue']")
    private WebElement continueButton;

    public RegisterationStep2_2of3PopUp(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(continueButton);
    }

}