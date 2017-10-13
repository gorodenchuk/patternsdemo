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
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;

/**
 * Created by NGorodenchuk on 7/13/2017.
 */
public class RegisterationStep3_1of3PopUp extends Page {

    private static final Logger LOG = LogManager.getLogger(RegisterationStep3_1of3PopUp.class);


    @FindBy(how = How.XPATH, using = "//img[@class='close-register nav-link']")
    private WebElement closseButton;

    @FindBy(how = How.XPATH, using = "//form[@method='post']/input[@name='recommendation']")
    private WebElement chooseButton;

    @FindBy(how = How.XPATH, using = "//form[@method='post']/input[@type='submit']")
    private WebElement submitButton;

    public RegisterationStep3_1of3PopUp(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(closseButton);
    }


    public MenuPage clickOnCloseButton() {
        //		click on WebElement by JS
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", closseButton);

        return PageFactory.initElements(webDriver, MenuPage.class);
    }

    @Step
    public RegisterationStep3_1of3PopUp uploadJpegfileOnStep3_1of3TEST() throws InterruptedException {

//        This JS add upload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/recommendation/set'enctype='multipart/form-data'> <input name='recommendation' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        chooseButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", submitButton);

        return PageFactory.initElements(webDriver, RegisterationStep3_1of3PopUp.class);
    }

    @Step
    public RegisterationStep3_1of3PopUp uploadJpegfileOnStep3_1of3() throws InterruptedException {

//        This JS add upload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/recommendation/set'enctype='multipart/form-data'> <input name='recommendation' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);
        fluentWaitUntilElementBecomeClicable(chooseButton, "'Choose' button");

        chooseButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", fluentWaitUntilElementBecomeClicable(submitButton, "'Submit' button"));

        return PageFactory.initElements(webDriver, RegisterationStep3_1of3PopUp.class);
    }

    @Step
    public MenuPage navigateToPhotoRecPageTEST() throws InterruptedException {
        webDriver.navigate().to(WEB_URL + "photorec");
        return PageFactory.initElements(webDriver, MenuPage.class);
    }

    @Step
    public MenuPage navigateToPhotoRecPage() throws InterruptedException {
        webDriver.navigate().to(WEB_URL + "photorec");

        return PageFactory.initElements(webDriver, MenuPage.class);
    }

    @Step
    public RegisterationStep3_1of3PopUp approveRecommendationInDb(String generNumber1) throws SQLException, InterruptedException {
        Users.updateVerify_MedicalToApprove(generNumber1);
        return PageFactory.initElements(webDriver, RegisterationStep3_1of3PopUp.class);
    }

    @Step
    public RegisterationStep3_1of3PopUp setNotValidDateInRec_ExpiredInDb (String generNumber1) throws SQLException, InterruptedException {
        Users.updateRec_ExpiredToNotValidDate(generNumber1);
        return PageFactory.initElements(webDriver, RegisterationStep3_1of3PopUp.class);
    }

    @Step
    public RegisterationStep3_1of3PopUp updateRec_ExpiredToValidDate(String generNumber1) throws SQLException, InterruptedException {
        Users.updateRec_ExpiredToValidDate(generNumber1);
        return PageFactory.initElements(webDriver, RegisterationStep3_1of3PopUp.class);
    }
}
