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
import pageObject.pages.mmjtraine.CheckOutPage;
import pageObject.pages.mmjtraine.MenuPage;

/**
 * Created by NGorodenchuk on 7/13/2017.
 */
public class RegisterationStep3_2of3PopUp extends Page {

    private static final Logger LOG = LogManager.getLogger(RegisterationStep3_2of3PopUp.class);


    @FindBy(how = How.XPATH, using = "//img[@class='close-register nav-link']")
    private WebElement closseButton;

    @FindBy(how = How.XPATH, using = "//button[@style='display:block' and contains(text(),'Finish')]")
    private WebElement finishButton;

    public RegisterationStep3_2of3PopUp(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(finishButton);
    }

    public MenuPage clickOnCloseButton() {
        //		click on WebElement by JS
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", closseButton);

        return PageFactory.initElements(webDriver, MenuPage.class);
    }

}
