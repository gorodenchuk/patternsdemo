package pageObject.pages.pop_ups;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.mmjtraine.CheckOutPage;
import pageObject.pages.mmjtraine.MenuPage;
import pageObject.pages.mmjtraine.ProfilePage;
import pageObject.utility.DB.DbTables.Users;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConfirmYourOrderPopUp extends Page {

	@FindBy(how = How.XPATH, using = "//body[@class='modal-open']//div[@id='order_confirm']//button[@class='btn-main btn-border btn-upload btn-edit']")
	private WebElement editButton;

	@FindBy(how = How.XPATH, using = "//body[@class='modal-open']//div[@id='order_confirm']//button[@class='btn-main btn-standard btn-savechanges']")
	private WebElement confirmOrderButton;

	public  List<String> productsListOfItemId = new ArrayList<String>();
	public  List<String> productsListOfItemName = new ArrayList<String>();
	public  List<String> productsListOfItemPrice = new ArrayList<String>();
	public  List<String> productsListOfItemQuantity = new ArrayList<String>();

	public ConfirmYourOrderPopUp(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	private static final Logger LOG = LogManager.getLogger(ConfirmYourOrderPopUp.class);

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(editButton);
	}

	@Step
	public RegisterationStep1_1of3PopUp refreshSignUpPopUp(){
		webDriver.navigate().refresh();
		return PageFactory.initElements(webDriver, RegisterationStep1_1of3PopUp.class);
	}


	@Step
	public CheckOutPage clickEditButton() {
		editButton.click();
		return PageFactory.initElements(webDriver, CheckOutPage.class);
	}

	@Step
	public ProfilePage clickConfirmOrderButton() throws InterruptedException {
		confirmOrderButton.click();
		return PageFactory.initElements(webDriver, ProfilePage.class);
	}

	@Step
	public ConfirmYourOrderPopUp getOrderInformation() {

		List<WebElement> productsListOfIdForItems = webDriver.findElements(By.xpath("//table[@class='preview-order-table text']//tr"));
		List<WebElement> productsListOfNameForItems = webDriver.findElements(By.xpath("//table[@class='preview-order-table text']//tr/td[@class='item-name text-color-2']"));
		List<WebElement> listOfPricesForItems = webDriver.findElements(By.xpath("//table[@class='preview-order-table text']//tr/td[@class='item-price text-color-2']"));
		List<WebElement> productsListOfQuantityForItems = webDriver.findElements(By.xpath("//table[@class='preview-order-table text']//tr/td[@class='item-count text-color']"));

		for (int i = 0; i < productsListOfIdForItems.size(); i++){
			productsListOfItemId.add(productsListOfIdForItems.get(i).getAttribute("id"));
			productsListOfItemName.add(productsListOfNameForItems.get(i).getText());
			productsListOfItemPrice.add(listOfPricesForItems.get(i).getText());
			productsListOfItemQuantity.add(productsListOfQuantityForItems.get(i).getText());
		}

		return PageFactory.initElements(webDriver, ConfirmYourOrderPopUp.class);
	}


	@Step
	public int getTotalPriceAferUsingDiscounts() {
		String tp = webDriver.findElement(By.xpath("//div[@id='order_confirm']//p[@class='total-price text-color-2 text-center']/span")).getText();
		String tpn = tp.replace("$", "");
		int totalPriceAfterUsingDiscounts = Integer.parseInt(tpn);
		return totalPriceAfterUsingDiscounts;
	}

	@Step
	public int getOldPriceDisplayedOnCheckOutPage(){
		String op = webDriver.findElement(By.xpath("//div[@id='order_confirm']//p[@class='old-price text-color text-center']/span")).getText();
		String opn = op.replace("$", "");
		int oldPrice = Integer.parseInt(opn);
		return oldPrice;
	}

	@Step
	public int getYouSavePrice() {
		String ysp = webDriver.findElement(By.xpath("//div[@id='order_confirm']//span[@class='discount-size']/i")).getText();
		int youSavePrice = Integer.parseInt(ysp);
		return youSavePrice;
	}

	@Step
	public String getUserAddress(String generNumber1) throws SQLException, InterruptedException {
		String fullAddress;
		String name = fluentWaitUntilElementBecomeClicable(webDriver.findElement(By.xpath("//div[@class='modal-content']//p[@class='text-color confirm-info address-text']/span[1]")), "'Name' parameter").getText();
		String address = fluentWaitUntilElementBecomeClicable(webDriver.findElement(By.xpath("//div[@class='modal-content']//p[@class='text-color confirm-info address-text']/span[2]")), "'Address' parameter").getText();
		String zcode = fluentWaitUntilElementBecomeClicable(webDriver.findElement(By.xpath("//div[@class='modal-content']//p[@class='text-color confirm-info address-text']/span[3]")), "'Zcode' parameter").getText();
		String city = fluentWaitUntilElementBecomeClicable(webDriver.findElement(By.xpath("//div[@class='modal-content']//p[@class='text-color confirm-info address-text']/span[4]")), "'City' parameter").getText();
		String state = fluentWaitUntilElementBecomeClicable(webDriver.findElement(By.xpath("//div[@class='modal-content']//p[@class='text-color confirm-info address-text']/span[5]")), "'State' parameter").getText();
		String phone = Users.selectPhone(generNumber1);
		return fullAddress = address + "\n" + city + ",  " + state + " " + zcode + "\n" + phone;
	}


}
