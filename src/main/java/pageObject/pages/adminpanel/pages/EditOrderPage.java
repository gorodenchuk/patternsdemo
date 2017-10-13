package pageObject.pages.adminpanel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

public class EditOrderPage extends Page {

	@FindBy(how = How.XPATH, using = "//input[@id='firstname']")
	private WebElement firstNameField;

	@FindBy(how = How.XPATH, using = "//input[@id='lastname']")
	private WebElement lastNameField;

	@FindBy(how = How.XPATH, using = "//div[@class='col-xs-12 page-controls']//button[@class='btn-main btn-save']")
	private WebElement saveButton;

	@FindBy(how = How.XPATH, using = "//input[@id='email']")
	private WebElement emailField;

	@FindBy(how = How.XPATH, using = "//input[@id='phone']")
	private WebElement phoneField;

	@FindBy(how = How.XPATH, using = "//textarea [@id='address']")
	private WebElement addressField;

	@FindBy(how = How.XPATH, using = "//input[@id='city']")
	private WebElement cityField;

	@FindBy(how = How.XPATH, using = "//input[@id='state']")
	private WebElement stateField;

	@FindBy(how = How.XPATH, using = "//input[@id='zcode']")
	private WebElement zipCodeField;

	public  List<String> productsListOfItemName = new ArrayList<String>();
	public  List<String> productsListOfItemPrice = new ArrayList<String>();
	public  List<String> productsListOfItemQuantity = new ArrayList<String>();

	private static final Logger LOG = LogManager.getLogger(EditOrderPage.class);


	public EditOrderPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(firstNameField);
	}

	@Step
	public EditOrderPage setFAndLName(String fAndLName) {
		firstNameField.clear();
		firstNameField.sendKeys(fAndLName);
		lastNameField.clear();
		lastNameField.sendKeys(fAndLName);
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	@Step
	public OrdersPage clickSaveButton(){
		saveButton.click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
    public EditOrderPage setEmail(String email) {
		emailField.clear();
		emailField.sendKeys(email);
		return PageFactory.initElements(webDriver, EditOrderPage.class);
    }

	@Step
	public EditOrderPage setPhone(String generNumber2) {
		phoneField.clear();
		phoneField.sendKeys("(806) 945-" + generNumber2 + "");
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	@Step
	public EditOrderPage setAllFieldsInAddressBlock(String generNumber1, String test) {
		addressField.clear();
		addressField.sendKeys(test);
		cityField.clear();
		cityField.sendKeys(test);
		stateField.clear();
		stateField.sendKeys(test);
		zipCodeField.clear();
		zipCodeField.sendKeys("1" + generNumber1);
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	public String getDeliveryInstruction() {
		String deliveryInstruction;
		deliveryInstruction = webDriver.findElement(By.xpath("//form[@id='OrderAdminEditForm']//p[@class='text text-color-1 delivery-instructions']")).getText();
		return deliveryInstruction;
	}

	@Step
	public int clickOnDeleteButtonAboveCreatedProduct(String generNumber1) throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		String prices = webDriver.findElement(By.xpath("//tr[@class='product_item']//p[contains(text(),'testproduct" + generNumber1 + "')]/ancestor::tr/td[@class='item-price']/span")).getText();
		int sumOfPricesOfDeletedProductsInOrderPage = Integer.parseInt(prices);

		WebElement deleteButton = webDriver.findElement(By.xpath("//tr[@class='product_item']//p[contains(text(),'testproduct" + generNumber1 + "')]/ancestor::tr/td[@class='item-delete']"));
		executor.executeScript("arguments[0].click();", deleteButton);

		return sumOfPricesOfDeletedProductsInOrderPage;
	}

	@Step
	public OrdersPage clickOkButtonInConfirmAlert() {
		webDriver.switchTo().alert().accept();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public OrdersPage clickOnSaveButton() {
		//		click on WebElement by JS
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		executor.executeScript("arguments[0].click();", saveButton);
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public EditOrderPage clickOnEditProductItem(String generNumber1) {
		WebElement editButton = webDriver.findElement(By.xpath("//tr[@data-productid='" + generNumber1 + "']//i[@class='fa fa-pencil-square-o btn-changecount']"));
		editButton.click();
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	@Step
	public EditOrderPage clickOnNewPortionOfProductItem(String generNumber1) {
		WebElement price = webDriver.findElement(By.xpath("//tr[@data-productid='" + generNumber1 + "']//ul[@class='pack-count count-select']/li[2]"));
		price.click();
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	@Step
	public EditOrderPage clickOnPlusButtonAtQuantityProductItem(String generNumber1, int quantityOfClicks) {
		WebElement plusButton = webDriver.findElement(By.xpath("//tr[@data-productid='" + generNumber1 + "']//li[@class='count-part count-plus']"));

		for (int i = 0; i < quantityOfClicks; i++) {
			plusButton.click();
		}

		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	@Step
	public EditOrderPage clickOnMinusButtonAtQuantityProductItem(String generNumber1, int quantityOfClicks) {
		WebElement plusButton = webDriver.findElement(By.xpath("//tr[@data-productid='" + generNumber1 + "']//li[@class='count-part count-minus']"));
		for (int i = 0; i < quantityOfClicks; i++) {
			plusButton.click();
		}
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	@Step
	public EditOrderPage clickSaveButtomOnProductItem(String generNumber1) {
		WebElement saveButton = webDriver.findElement(By.xpath("//tr[@data-productid='" + generNumber1 + "']//div[@class='change-count']//button[@class='btn-main btn-save']"));
		saveButton.click();
		return PageFactory.initElements(webDriver, EditOrderPage.class);

	}

	public int getSumOfProductItems() {
		List<WebElement> listOfPricesForItems = webDriver.findElements(By.xpath("//td[@class='item-price']/span"));
		int price = 0;
		for( int i = 1; i < listOfPricesForItems.size(); i++) {
			price += Integer.parseInt(listOfPricesForItems.get(i).getText());
		}
		return price;
	}

	@Step
	public OrdersPage getOrderInformation(String generNumber1) {

		List<WebElement> productsListOfNameForItems = webDriver.findElements(By.xpath("//p[@class='item-name']"));
		List<WebElement> listOfPricesForItems = webDriver.findElements(By.xpath("//td[@class='item-price']/span"));
		List<WebElement> productsListOfQuantityForItems = webDriver.findElements(By.xpath("//p[@class='quantity text-color-1']/span"));

		for (int i = 0; i < productsListOfNameForItems.size(); i++) {

			productsListOfItemName.add(productsListOfNameForItems.get(i).getText());
			productsListOfItemPrice.add(listOfPricesForItems.get(i).getText());
			productsListOfItemQuantity.add(productsListOfQuantityForItems.get(i).getText());
		}

		return PageFactory.initElements(webDriver, OrdersPage.class);
	}
}

