package pageObject.pages.mmjtraine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.pop_ups.PhoneNumberVerifyPopUp;
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;
import java.util.Map;

/**
 * Created by NGorodenchuk on 7/10/2017.
 */
public class ProfilePage extends Page{

    @FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-border btn-signin btn-log-out']")
    private WebElement logOutButton;

    @FindBy(how = How.XPATH, using = "//input[@name='current_password']")
    private WebElement currentPasswodField;

    @FindBy(how = How.XPATH, using = "//input[@name='new_password']")
    private WebElement newPasswordField;

    @FindBy(how = How.XPATH, using = "//input[@name='confirm_password']")
    private WebElement confirmPasswordField;

    @FindBy(how = How.XPATH, using = "//form[@id='profile_save_password']//button[@class='btn-main btn-standard btn-savechanges']")
    private WebElement changePasswodButton;

    @FindBy(how = How.XPATH, using = "//input[@name='email']")
    private WebElement emailField;

    @FindBy(how = How.XPATH, using = "//form[@id='profile_save_changes']//button[@class='btn-main btn-standard btn-savechanges']")
    private WebElement saveChangesButton;

    @FindBy(how = How.XPATH, using = "//input[@class='standard-input phone-verify']")
    private WebElement phoneField;

    @FindBy(how = How.XPATH, using = "//ul[@class='list-unstyled text-color text']/li[2]")
    private WebElement verificationTab;

    @FindBy(how = How.XPATH, using = "//form[@method='post']/input[@name='recommendation']")
    private WebElement chooseRecommendationButton;

    @FindBy(how = How.XPATH, using = "//form[@method='post']/input[@name='identification']")
    private WebElement chooseIdentificationButton;

    @FindBy(how = How.XPATH, using = "//form[@method='post']/input[@type='submit']")
    private WebElement submitButton;

    @FindBy(how = How.XPATH, using = "//a[@id='menu_link']")
    private WebElement menuButton;

    @FindBy(how = How.XPATH, using = "//input[@data-valid='true']/following::div[1]/span[.='Verify']")
    private WebElement verifyButton;

    @FindBy(how = How.XPATH, using = "//div/a[@id='history_link']")
    private WebElement orderHistoryButton;

    public ProfilePage(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(currentPasswodField);
    }

    private static final Logger LOG = LogManager.getLogger(ProfilePage.class);

    @Step
    public HomePage clickOnLogOutButton() {
        logOutButton.click();
        return PageFactory.initElements(webDriver, HomePage.class);
    }

    @Step
    public ProfilePage setDataForChangingPassword(String current, String newpass, String confirm) throws InterruptedException {
        currentPasswodField.sendKeys(current);
        newPasswordField.sendKeys(newpass);
        confirmPasswordField.sendKeys(confirm);
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage setDataForChangingPassword(String current, String newpass) throws InterruptedException {
        currentPasswodField.sendKeys(current);
        newPasswordField.sendKeys(newpass);
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage clcikChangePasswordButton() throws InterruptedException {
        changePasswodButton.click();
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }


    @Step
    public ProfilePage ChangeEmailAndSave(Map<String,String> testData) throws InterruptedException {
        emailField.clear();
        emailField.sendKeys(testData.get("Email"));
        saveChangesButton.click();
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage useAlreadeExistEmailOfAnotherUserAndSaveIt(String generNumber1) throws InterruptedException {
        emailField.clear();
        emailField.sendKeys("mmjtrain.test" + generNumber1 + "@gmail.com");
        saveChangesButton.click();
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage changeCurrentEmail(String generNumber1) throws InterruptedException {
        emailField.clear();
        emailField.sendKeys("test" + generNumber1 + "@gmail.com");
        saveChangesButton.click();
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage verifyPhoneNumberInDB(String generNumber1) throws InterruptedException, SQLException {

        Users.updatePhone_VerifyToApprove(generNumber1);
        Thread.sleep(5000);
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage clickOnVerificationTab() throws InterruptedException {

        //		JS make active verification tab and not active Personal Information tab
        String elem1 = "document.getElementById('personal_information').setAttribute('class','tab-pane fade')";
        String elem2 = "document.getElementById('verification').setAttribute('class','tab-pane fade active in')";

        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript(elem1);
        executor.executeScript(elem2);

        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage veifyIdentificationInDB(String generNumber1) throws InterruptedException, SQLException {
        //        This JS add uppload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/identification/set'enctype='multipart/form-data'> <input name='identification' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        chooseIdentificationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        submitButton.click();
        Thread.sleep(3000);
        Users.updateVerify_PersonalToApprove(generNumber1);
        webDriver.navigate().to(WEB_URL + "profile");

        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage verifyRecommendationInDB(String generNumber1) throws InterruptedException, SQLException {

        //        This JS add uppload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/recommendation/set'enctype='multipart/form-data'> <input name='recommendation' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        chooseRecommendationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        submitButton.click();
        Thread.sleep(3000);
        Users.updateVerify_MedicalToApprove(generNumber1);
        webDriver.navigate().to(WEB_URL + "profile");

        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public MenuPage clickOnMenuButton() throws InterruptedException {
        menuButton.click();
        return PageFactory.initElements(webDriver, MenuPage.class);
    }

    @Step
    public ProfilePage uploadIdentificationAndPutStatusPendingInDB(String generNumber1) throws InterruptedException, SQLException {
        //        This JS add uppload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/identification/set'enctype='multipart/form-data'> <input name='identification' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        chooseIdentificationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        submitButton.click();
        Thread.sleep(3000);
        Users.updateVerify_PersonalToPending(generNumber1);
        webDriver.navigate().to(WEB_URL + "profile");
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage uploadRecommendationAndPutStatusPendingInDB(String generNumber1) throws InterruptedException, SQLException {
        //        This JS add uppload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/recommendation/set'enctype='multipart/form-data'> <input name='recommendation' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        chooseRecommendationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        submitButton.click();
        Thread.sleep(3000);
        Users.updateVerify_MedicalToPending(generNumber1);
        webDriver.navigate().to(WEB_URL + "profile");

        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage uploadRecommendationAndPutStatusDeclinedInDB(String generNumber1) throws InterruptedException, SQLException {

        //        This JS add uppload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/recommendation/set'enctype='multipart/form-data'> <input name='recommendation' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        chooseRecommendationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        submitButton.click();
        Thread.sleep(3000);
        Users.updateVerify_MedicalToDeclined(generNumber1);
        webDriver.navigate().to(WEB_URL + "profile");
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage uploadIdentificationAndPutStatusDeclinedInDB(String generNumber1) throws InterruptedException, SQLException {

        //        This JS add uppload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/identification/set'enctype='multipart/form-data'> <input name='identification' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        chooseIdentificationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage1.jpg");
        submitButton.click();
        Thread.sleep(3000);
        Users.updateVerify_PersonalToDeclined(generNumber1);
        webDriver.navigate().to(WEB_URL + "profile");
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage set10DigitsPhoneNumberInPhoneField(String generNumber1) throws InterruptedException {
        String elem = "document.getElementsByTagName('input')[5].setAttribute('type','text')";
        ((JavascriptExecutor) webDriver).executeScript(elem);
        currentPasswodField.sendKeys("860971"+ generNumber1 +"");

        currentPasswodField.sendKeys(Keys.CONTROL + "a");
        currentPasswodField.sendKeys(Keys.CONTROL + "c");
        phoneField.clear();
        phoneField.sendKeys(Keys.CONTROL + "v");
        Thread.sleep(2000);

        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage set10DigitsNotUSAPhoneNumberInPhoneField() throws InterruptedException {
        phoneField.sendKeys("1111111111");
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public void set9DigitsPhoneNumberInPhoneField() throws InterruptedException {
        phoneField.sendKeys("860971970");
    }

    @Step
    public void setLettersInPhoneField() throws InterruptedException {
        phoneField.sendKeys("qwertyuiop");
    }

    @Step
    public PhoneNumberVerifyPopUp clickOnVerifyButton() throws InterruptedException {
        phoneField.sendKeys("8609719707");
        verifyButton.click();
        return PageFactory.initElements(webDriver, PhoneNumberVerifyPopUp.class);
    }

    @Step
    public void setSpecialSymbolsInPhoneField() throws InterruptedException {
        phoneField.sendKeys("~!@#$%^&*()_-=+[]{}|':;?/.><№");
    }

    @Step
    public void clickOnSaveButtonAndRefreshPage() throws InterruptedException {
        saveChangesButton.click();
        webDriver.navigate().refresh();
//        Thread.sleep(5000);
    }

    @Step
    public void clickOnSaveButton() throws InterruptedException {
        saveChangesButton.click();
    }

    @Step
    public ProfilePage createFormOnProfilePAge() throws InterruptedException {

        //        This JS add uppload form to htmml
        String elem = "document.body.innerHTML += \"<form method=post " +
                "action='"+ WEB_URL + "api/identification/set'enctype='multipart/form-data'> <input name='identification' type='file'><input type=submit></form>;\"";
        ((JavascriptExecutor) webDriver).executeScript(elem);

        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    @Step
    public ProfilePage refreshPage () {
        webDriver.navigate().refresh();
        return PageFactory.initElements(webDriver, ProfilePage.class);
    }

    public String getPhoneNumber() {
        String phone = webDriver.findElement(By.xpath("//div[@class='verified-input']/input[@name='phone']")).getAttribute("value");
        return phone;
    }

    public String getPersonaIdentificationStatus() {
        String status = webDriver.findElement(By.xpath("//p[@class='text text-color-2 subtitle verification-status-personal']/span")).getText();
        return status;
    }

    public String getMedicalIdentificationStatus() {
        String status = webDriver.findElement(By.xpath("//p[@class='text text-color-2 subtitle verification-status-medical']/span")).getText();
        return status;
    }

    @Step
    public HistoryPage clickOnOrderHistoryButton () {
        orderHistoryButton.click();
        return PageFactory.initElements(webDriver, HistoryPage.class);
    }
}
