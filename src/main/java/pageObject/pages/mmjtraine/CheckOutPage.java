package pageObject.pages.mmjtraine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.pop_ups.ConfirmYourOrderPopUp;
import pageObject.utility.DB.DataBaseExecute;
import pageObject.utility.DB.DbTables.Promocodes;
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CheckOutPage extends Page {

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-standard btn-message btn-order']")
	private WebElement placeOrderButton;

	@FindBy(how = How.XPATH, using = "//img[@class='close-register nav-link']")
	private WebElement closseButton;

	@FindBy(how = How.XPATH, using = "//input[@id='address']")
	private WebElement addressField;

	@FindBy(how = How.XPATH, using = "//input[@id='zip']")
	private WebElement zipCodeField;

	@FindBy(how = How.XPATH, using = "//textarea[@id='delivery_instruction']")
	private WebElement deliveryInstructionField;

	@FindBy(how = How.XPATH, using = "//form//input[@id='first_name']")
	private WebElement firstNameField;

	@FindBy(how = How.XPATH, using = "//label[@class='checkbox-label sign-required']/span[@class='custom-checkbox']")
	private WebElement signatureCheckBox;

	@FindBy(how = How.XPATH, using = "//form//input[@id='last_name']")
	private WebElement lastNameField;

	@FindBy(how = How.XPATH, using = "//input[@id='card_number']")
	private WebElement cardNumberField;

	@FindBy(how = How.XPATH, using = "//input[@id='month']")
	private WebElement monthField;

	@FindBy(how = How.XPATH, using = "//input[@id='year']")
	private WebElement yearField;

	@FindBy(how = How.XPATH, using = "//input[@id='cvv']")
	private WebElement cvvField;

	@FindBy(how = How.XPATH, using = "//div[@class='inp-group promo-row']/input[@class='standard-input promocode-input']")
	private WebElement promoCodeField;

	@FindBy(how = How.XPATH, using = "//div[@class='inp-group promo-row']/button[@class='btn-main btn-standard btn-message promocode-btn']")
	private WebElement recalculateButton;

	@FindBy(how = How.XPATH, using = "//div[@class='credit-row']//span[@class='custom-checkbox']")
	private WebElement payWithCreditsCheckBox;

	public CheckOutPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = LogManager.getLogger(CheckOutPage.class);

	public List<String> productsListOfIdFromCheckOutPage = new ArrayList<String>();

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(placeOrderButton);
	}

	@Step
	public MenuPage clickOnCloseButton() {
		//		click on WebElement by JS
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		executor.executeScript("arguments[0].click();", closseButton);

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public void createListOfProductIdAndPrice() {
		List<WebElement> productsListOfItems = webDriver.findElements(By.xpath("//table[@class='order-table']//tr"));

		for (int i = 0; i < productsListOfItems.size(); i++) {
			productsListOfIdFromCheckOutPage.add(productsListOfItems.get(i).getAttribute("id"));
		}

	}

	@Step
	public CheckOutPage activateCheckBox() {
		signatureCheckBox.click();
		return PageFactory.initElements(webDriver, CheckOutPage.class);
	}

	@Step
	public MenuPage clickOnDeleteAddedProductItem() {
		MenuPage menuPage = new MenuPage(webDriver);
		WebElement deleteAddedProductButton = webDriver.findElement(By.xpath("//div[@class='order-content text']//tr[@id='" + menuPage.productId + "']//td[@class='item-delete']"));
		deleteAddedProductButton.click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public ConfirmYourOrderPopUp clickOnPlaceOrderButton() {
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;
		executor.executeScript("arguments[0].click();", placeOrderButton);
		return PageFactory.initElements(webDriver, ConfirmYourOrderPopUp.class);
	}

	@Step
	public CheckOutPage setDeliveryAddress(String address, String zipCode) {
		addressField.clear();
		addressField.sendKeys(address);
		zipCodeField.sendKeys(zipCode);
		return PageFactory.initElements(webDriver, CheckOutPage.class);
	}

	@Step
	public CheckOutPage setDeliveryInstruction(String deliveryInstuction) {
		deliveryInstructionField.sendKeys(deliveryInstuction);
		return PageFactory.initElements(webDriver, CheckOutPage.class);
    }

	@Step
	public CheckOutPage setPaymentDetails() {
		try {
			firstNameField.sendKeys("TEST");
			lastNameField.sendKeys("TEST");
		}
		catch (NoSuchElementException e) {
			LOG.info("First and Last name already setted!");
		}
		finally {
			webDriver.switchTo().frame(webDriver.findElement(By.id("spreedly-number-frame-1001")));
			cardNumberField.sendKeys("4111 1111 1111 1111");
			webDriver.switchTo().defaultContent();
			monthField.sendKeys("12");
			yearField.sendKeys("2018");
			webDriver.switchTo().frame(webDriver.findElement(By.id("spreedly-cvv-frame-1001")));
			cvvField.sendKeys("123");
			webDriver.switchTo().defaultContent();
			return PageFactory.initElements(webDriver, CheckOutPage.class);
		}

	}

	@Step
	public CheckOutPage deleteAddedOredersExceptOne() {
		MenuPage menuPage = new MenuPage(webDriver);
		for (int i = 0; i < menuPage.productsListOfId.size()-1; i++) {
//			 click on delete button
			webDriver.findElement(By.xpath("//div[@class='order-content text']//tr[@id='" + menuPage.productsListOfId.get(i) + "']//td[@class='item-delete']")).click();
		}
		return PageFactory.initElements(webDriver, CheckOutPage.class);
	}

	@Step
	public CheckOutPage useCreatedPromoCode(String generNumber1) {
		promoCodeField.sendKeys("testpromo" + generNumber1);
		recalculateButton.click();
		return PageFactory.initElements(webDriver, CheckOutPage.class);

	}

	@Step
	public int createPromoCodeInDb(String generNumber1, int price) throws SQLException {
		int promoCodeValue;
		Promocodes.insertPromocodeNotUsed(generNumber1, price);
		promoCodeValue = price;
		return promoCodeValue;
	}

	@Step
	public int getTotalPriceBeforeUsingDiscounts() {
		int totalSumOfProductPricesInOrderBlock = 0;
		List<WebElement> listOfPriceOnCheckOutPage = webDriver.findElements(By.xpath("//div[@class='order-content text']//td[@class='item-price']/span"));
		for (int i = 0; i < listOfPriceOnCheckOutPage.size(); i++) {
			String onlyNumb = listOfPriceOnCheckOutPage.get(i).getText().replace("$", "");
			int price = Integer.parseInt(onlyNumb);
			totalSumOfProductPricesInOrderBlock += price;
		}
		return totalSumOfProductPricesInOrderBlock;
	}

	@Step
	public int getTotalPriceAferUsingDiscounts() {
		String tp = webDriver.findElement(By.xpath("//div[@class='order-content text']/p[@class='total-price text-color-2 text-center']/span")).getText();
		String tpn = tp.replace("$", "");
		int totalPriceAfterUsingDiscounts = Integer.parseInt(tpn);
		return totalPriceAfterUsingDiscounts;
	}

	@Step
	 public int getOldPriceDisplayedOnCheckOutPage(){
			String op = webDriver.findElement(By.xpath("//div[@class='order-content text']/p[@class='old-price text-color text-center']/span")).getText();
			String opn = op.replace("$", "");
			int oldPrice = Integer.parseInt(opn);
			return oldPrice;
	}

	@Step
	public int getYouSavePriceValueDisplayedIncheckOutPage() {
		int youSavePromoCode = 0;
		int youSaveCreditMoney = 0;

		String yspc = webDriver.findElement(By.xpath("//div[@class='order-content text']//p[1]//span[@class='discount-size']/i")).getText();
		youSavePromoCode = Integer.parseInt(yspc);

		try {
			String yscm = webDriver.findElement(By.xpath("//div[@class='order-content text']//p[2]//span[@class='discount-size']/i")).getText();
			youSaveCreditMoney = Integer.parseInt(yscm);
		} catch (NoSuchElementException e){
			LOG.info("Element not found");
		}

	 	return youSavePromoCode + youSaveCreditMoney;
	}

	public int calculateTotalPriceWithUsedDiscounts(int totalSumOfProductPricesInOrderBlock, int promoCodeValue, int creditMoneyValue) {
		int result = totalSumOfProductPricesInOrderBlock - promoCodeValue - creditMoneyValue;

		if (result < 0) {
			return 0;
		} else {
			return result;
		}
	}

	public int calculateYouSavePrice(int totalSumOfProductPricesInOrderBlock, int promoCodeValue, int creditMoneyValue) {

		if (promoCodeValue + creditMoneyValue <= totalSumOfProductPricesInOrderBlock) {
			return promoCodeValue + creditMoneyValue;
		} else {
			return totalSumOfProductPricesInOrderBlock;
		}
	}

	public CheckOutPage useCreditMoney() {
		payWithCreditsCheckBox.click();
		return PageFactory.initElements(webDriver, CheckOutPage.class);
	}

}