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
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;

import static com.jayway.restassured.RestAssured.get;
import static com.jayway.restassured.RestAssured.post;

/**
 * Created by NGorodenchuk on 7/13/2017.
 */
public class RegisterationStep2_1of3PopUp extends Page {

    private static final Logger LOG = LogManager.getLogger(RegisterationStep2_1of3PopUp.class);


    @FindBy(how = How.XPATH, using = "//img[@class='close-register nav-link']")
    private WebElement closseButton;

    @FindBy(how = How.XPATH, using = "//form[@method='post']/input[@name='identification']")
    private WebElement chooseButton;

    @FindBy(how = How.XPATH, using = "//form[@method='post']/input[@type='submit']")
    private WebElement submitButton;


    public RegisterationStep2_1of3PopUp(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(closseButton);
    }

    @Step
    public RegisterationStep2_1of3PopUp uploadJpegfileTEST() throws InterruptedException {

//        This JS add upload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/identification/set'enctype='multipart/form-data'> <input name='identification' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);
        chooseButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");


        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", submitButton);

        return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
    }

    @Step
    public RegisterationStep2_1of3PopUp uploadJpegfile() throws InterruptedException {

//        This JS add upload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/identification/set'enctype='multipart/form-data'> <input name='identification' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);
        fluentWaitUntilElementBecomeClicable(chooseButton, "'Choose' button");
        chooseButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", fluentWaitUntilElementBecomeClicable(submitButton,"'Submit' button"));
        return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
    }

    @Step
    public RegisterationStep3_1of3PopUp navigateToPhotoIdPageTEST() throws InterruptedException {
        webDriver.navigate().to(WEB_URL + "photoid");
        return PageFactory.initElements(webDriver, RegisterationStep3_1of3PopUp.class);
    }

    @Step
    public RegisterationStep3_1of3PopUp navigateToPhotoIdPage() throws InterruptedException {
        webDriver.navigate().to(WEB_URL + "photoid");
        return PageFactory.initElements(webDriver, RegisterationStep3_1of3PopUp.class);
    }

    @Step
    public RegisterationStep2_1of3PopUp approveIdentificationInDbTEST (String generNumber1) throws SQLException, InterruptedException {
        Users.updateVerify_PersonalToApprove(generNumber1);
        return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
    }

    @Step
    public RegisterationStep2_1of3PopUp approveIdentificationInDb(String generNumber1) throws SQLException, InterruptedException {
        Users.updateVerify_PersonalToApprove(generNumber1);
        return PageFactory.initElements(webDriver, RegisterationStep2_1of3PopUp.class);
    }
}







