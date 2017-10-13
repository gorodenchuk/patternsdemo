package pageObject.pages.adminpanel.pages;

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
import pageObject.pages.pop_ups.ConfirmTheOperationPopUp;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;

public class OrdersPage extends Page {

	@FindBy(how = How.XPATH, using = "//ul[@id='gr-6']")
	private WebElement deleteFilterButton;

	public  List<String> productsListOfItemName = new ArrayList<String>();
	public  List<String> productsListOfItemPrice = new ArrayList<String>();
	public  List<String> productsListOfItemQuantity = new ArrayList<String>();

	private static final Logger LOG = LogManager.getLogger(OrdersPage.class);


	public OrdersPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(deleteFilterButton);
	}

	@Step
	public int getTotalPriceAferUsingDiscounts(String generNumber1) throws InterruptedException {
		Thread.sleep(2000);
		String tp = webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test"
				+ generNumber1 + "@gmail.com']/ancestor::div[@class='order-data']//li[@class='o-price text-center']" +
				"/span/span[1]")).getText();
		String tpn = tp.replace("$", "").replaceAll("\\s","");
		Thread.sleep(2000);
		int totalPriceAfterUsingDiscounts = Integer.parseInt(tpn);
		return totalPriceAfterUsingDiscounts;
	}

	@Step
	public OrdersPage getOrderInformation(String generNumber1) {

		List<WebElement> productsListOfNameForItems = webDriver.findElements(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test"+ generNumber1 +"@gmail.com']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']//table[@class='order-table']//p[@class='item-name']"));
		List<WebElement> listOfPricesForItems = webDriver.findElements(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test"+ generNumber1 +"@gmail.com']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']//table[@class='order-table']//td[@class='item-price']/span"));
		List<WebElement> productsListOfQuantityForItems = webDriver.findElements(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test"+ generNumber1 +"@gmail.com']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']//table[@class='order-table']//p[@class='quantity text-color']/span"));

		for (int i = 0; i < productsListOfNameForItems.size(); i++){

			productsListOfItemName.add(productsListOfNameForItems.get(i).getText());
			productsListOfItemPrice.add(listOfPricesForItems.get(i).getText());
			productsListOfItemQuantity.add(productsListOfQuantityForItems.get(i).getText());
		}

		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	public int getSumOfProductItems(String generNumber1) {
		List<WebElement> listOfPricesForItems = webDriver.findElements(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test"+ generNumber1 +"@gmail.com']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']//table[@class='order-table']//td[@class='item-price']/span"));
		int price = 0;
		for( int i = 1; i < listOfPricesForItems.size(); i++) {
			price += Integer.parseInt(listOfPricesForItems.get(i).getText().replace("$", ""));
		}
		return price;
	}

	public String getOrderAddress(String generNumber1) {
		String address = webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']//p[@class='o-address-text']/span")).getText();
		return address;
	}

	@Step
	public ConfirmTheOperationPopUp clickOnDeleteButtonAboveCreatedOrder(String generNumber1){
		webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']/ancestor::div[@class='order-data']" +
				"/preceding::div[@class='control-panel-left']" +
				"//button[@class='btn-main btn-delete btn-with-icon btn-delete-one']")).click();
		return PageFactory.initElements(webDriver, ConfirmTheOperationPopUp.class);
	}

	@Step
	public OrdersPage clickOnDeleteFilterButton(){
		deleteFilterButton.click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public OrdersPage clickOnPendingButton(String generNumber1) {
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']//a[@class='status btn-pending main_status']")).click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	public OrdersPage setAttributeInHintWindowElement(String generNumber1) throws InterruptedException {
		JavascriptExecutor executor = (JavascriptExecutor)webDriver;

		String attName = "style";
		String attValue = "display: block";
		WebElement element = webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']//div[@class='fast-dropdown']"));

		executor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);",
				element, attName, attValue);
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public OrdersPage clickOnApprovedButtonInOrderHintWindow(String generNumber1) throws InterruptedException {
		fluentWaitUntilElementBecomeClicable(webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']//div[@class='fast-dropdown']" +
				"/a[@data-status='approved']")), "'Approve' button in hint window").click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public OrdersPage clickOnDisabledButtonInOrderHintWindow(String generNumber1) throws InterruptedException {
		webDriver.findElement(By.xpath("//li[@class='o-user data-color']" +
				"//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
				"/ancestor::div[@class='order-data']//li[@class='o-statuses-list']//div[@class='fast-dropdown']" +
				"/a[@data-status='disabled']")).click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public OrdersPage clickOnApprovedButtonInOrderItem (String generNumber1) throws InterruptedException {
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']/a[@class='status all_status main_status']/span[@class='status-green']")).click();
		Thread.sleep(500);
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public OrdersPage clickOnDisabledButtonInOrderItem(String generNumber1) throws InterruptedException {
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']/a[@class='status all_status main_status']")).click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public OrdersPage refreshPage () {
		webDriver.navigate().refresh();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	public OrdersPage clickOnNotPaidButton(String generNumber1) {
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']/a[@class='status paid_status']/span[@class='status-red']")).click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	public OrdersPage clickOnPaidButton(String generNumber1) {
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']/a[@class='status paid_status']/span[@class='status-green']")).click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	public OrdersPage clickOnNotYetShippedButton(String generNumber1) throws InterruptedException {
		fluentWaitUntilElementBecomeClicable(webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']/a[@class='status shipped_status']/span[@class='status-red']")), "`Not Shipped` button").click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	public OrdersPage clickOnShippingdButton(String generNumber1) {
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"//li[@class='o-statuses-list']/a[@class='status shipped_status']/span[@class='status-green']")).click();
		return PageFactory.initElements(webDriver, OrdersPage.class);
	}

	@Step
	public EditOrderPage clickOnEditButtonAboveCreatedOrder(String generNumber1){
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']" +
				"/preceding::div[@class='control-panel-left']//button[@class='btn-main btn-edit btn-with-icon']")).click();
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	@Step
	public EditOrderPage clickOnEditButtonAboveCreatedOrderWithEditedEmail(String generNumber1){
		webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']/preceding::div[@class='control-panel-left']//button[@class='btn-main btn-edit btn-with-icon']")).click();
		return PageFactory.initElements(webDriver, EditOrderPage.class);
	}

	public String searchedOrderByEmail (String generNumber1){
		String orderByUserEmail = webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']")).getText();
		return orderByUserEmail;
	}

	@Step
	public int calculateTotalPriceAfterDeletingProductInEditOrderPage(int totalSumOfProductPricesInOrderBlock, int sumOfPricesOfDeletedProductsInOrderPage, int promoCodeValue, int creditMoneyValue) {
		int absoluteValue = Math.abs(promoCodeValue + creditMoneyValue - sumOfPricesOfDeletedProductsInOrderPage);
		int priceAfterDeletingProductInEditOrderPage = totalSumOfProductPricesInOrderBlock - sumOfPricesOfDeletedProductsInOrderPage - absoluteValue;
		if (priceAfterDeletingProductInEditOrderPage < 0) {
			return 0;
		} else {
			return priceAfterDeletingProductInEditOrderPage;
		}
	}

	@Step
	public String getUserName(String generNumber1) {
		String name = webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']/li[@class='o-user data-color']//span")).getText();

		// This part of code delete all text after first space and shows only first user name or email
		int firstSpace = (name.indexOf(" ") >= 0) ? name.indexOf(" ") : name.length()-1;
		return name.substring(0,firstSpace);
	}

	@Step
	public String getFullUserName(String generNumber1) {
		String name = webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::ul[@class='list-unstyled data-list text-color-1 data-row-2']/li[@class='o-user data-color']//span")).getText();
		return name;
	}

	@Step
	public String getOrderId(String generNumber1) {
		String orderId = webDriver.findElement(By.xpath("//p[.='testproduct" + generNumber1 + "']/ancestor::div[@class='order-data']//li[@class='o-code']")).getText();
		return  orderId;
	}

}
