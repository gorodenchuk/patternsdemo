package pageObject.pages.adminpanel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

public class EditClientPage extends Page {

	@FindBy(how = How.XPATH, using = "//input[@id='phone']")
	private WebElement phoneField;

	@FindBy(how = How.XPATH, using = "//input[@id='firstname']")
	private WebElement fNameField;

	@FindBy(how = How.XPATH, using = "//input[@id='lastname']")
	private WebElement lNameField;

	@FindBy(how = How.XPATH, using = "//button [@id='verify_save_changes']")
	private WebElement saveButton;

	@FindBy(how = How.XPATH, using = "//form[@method='post']/input[@name='identification']")
	private WebElement chooseIdentificationButton;

	@FindBy(how = How.XPATH, using = "//form[@method='post']/input[@name='recommendation']")
	private WebElement chooseRecommendationButton;

	@FindBy(how = How.XPATH, using = "//form[@method='post']/input[@type='submit']")
	private WebElement submitButton;
	
	private static final Logger LOG = LogManager.getLogger(EditClientPage.class);


	public EditClientPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(phoneField);
	}

    public EditClientPage setPhoneNumber(String generNumber2) throws InterruptedException {
		fNameField.clear();
		fNameField.sendKeys("806945"+ generNumber2 +"");
		fNameField.sendKeys(Keys.CONTROL + "a");
		fNameField.sendKeys(Keys.CONTROL + "c");
		phoneField.clear();
		phoneField.sendKeys(Keys.CONTROL + "v");
		fNameField.clear();
		return PageFactory.initElements(webDriver, EditClientPage.class);
    }

	public EditClientPage setFAndLName(String fName, String lName) throws InterruptedException {
		fluentWaitUntilElementBecomeClicable(fNameField, "'First Name' field").clear();
		fNameField.sendKeys(fName);
		lNameField.clear();
		lNameField.sendKeys(lName);
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}


	public ClientsPage clickSaveBuuton() {
		saveButton.click();
		return PageFactory.initElements(webDriver, ClientsPage.class);

	}

	public String getPhoneNumber() {
		String phone = phoneField.getAttribute("value");
		return phone;
	}

//	public EditClientPage clickOnNotVerifiedPhoneNumberStatus() {
//		WebElement phoneStatus = webDriver.findElement(By.xpath("//div[@class='input text status-inline']/span[@class='status-red change-status']"));
//		phoneStatus.click();
//		return PageFactory.initElements(webDriver, EditClientPage.class);
//	}

	public EditClientPage clickOnVerifiedPhoneNumberStatus() {
		WebElement phoneStatus = webDriver.findElement(By.xpath("//div[@class='input text status-inline']/span[@class='status-green change-status']"));
		phoneStatus.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage refreshPage () {
		webDriver.navigate().refresh();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage updateIdentification() throws InterruptedException {
		//        This JS add upload form to htmml
		String elem = "document.body.innerHTML += \"<form method=post " +
				"action='"+ WEB_URL + "api/identification/set'enctype='multipart/form-data'> <input name='identification' type='file'><input type=submit></form>;\"";
		((JavascriptExecutor) webDriver).executeScript(elem);
		chooseIdentificationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage2.png");
//		Thread.sleep(2000);
		fluentWaitUntilElementBecomeClicable(submitButton, "'Submit' button").click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage updateRecommendation() throws InterruptedException {
//        This JS add upload form to htmml
		String elem = "document.body.innerHTML += \"<form method=post " +
				"action='"+ WEB_URL + "api/recommendation/set'enctype='multipart/form-data'> <input name='recommendation' type='file'><input type=submit></form>;\"";
		((JavascriptExecutor) webDriver).executeScript(elem);

		chooseRecommendationButton.sendKeys("C:\\Users\\NGorodenchuk.ARTFULBITS\\workplace\\com.traine\\src\\test\\java\\resources\\uploads\\testimage2.png");
		fluentWaitUntilElementBecomeClicable(submitButton, "'Submit' button").click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage clickOnPersonalRecommendationStatus() {
		WebElement personalStatus = webDriver.findElement(By.xpath("//span[@ident='verify_personal']"));
		personalStatus.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage clickOnApprovedPersonalRecommendationStatus() {
		WebElement personalStatus = webDriver.findElement(By.xpath("//span[@ident='verify_personal']/following-sibling::span/a[@data-status='approved']"));
		personalStatus.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage clickOnDeclinedPersonalRecommendationStatus() {
		WebElement personalStatus = webDriver.findElement(By.xpath("//span[@ident='verify_personal']/following-sibling::span/a[@data-status='declined']"));
		personalStatus.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage clickOnMedicalRecommendationStatus() {
		WebElement personalStatus = webDriver.findElement(By.xpath("//span[@ident='verify_medical']"));
		personalStatus.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage clickOnApprovedMedicalRecommendationStatus() {
		WebElement personalStatus = webDriver.findElement(By.xpath("//span[@ident='verify_medical']/following-sibling::span/a[@data-status='approved']"));
		personalStatus.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	@Step
	public EditClientPage clickOnDeclinedMedicalRecommendationStatus() {
		WebElement personalStatus = webDriver.findElement(By.xpath("//span[@ident='verify_medical']/following-sibling::span/a[@data-status='declined']"));
		personalStatus.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}
}
