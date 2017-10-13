package pageObject.pages.mmjtraine;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.pop_ups.SignInPopUp;
import pageObject.utility.DB.DbTables.*;
import ru.yandex.qatools.allure.annotations.Step;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MenuPage extends Page {

	@FindBy(how = How.XPATH, using = "//div[@class='btn-main btn-border user-location']/span[contains(text(),'Your Location')]")
	private WebElement yourLocationButton;

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-border btn-signin nav-link']")
	private WebElement profileButton;

	@FindBy(how = How.XPATH, using = "//figure[1]//button[@class='btn-main btn-standard btn-add']")
	private WebElement addToCart1StItemButton;

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-standard btn-cart']")
	private WebElement greenCartButton;

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-standard btn-cart cart-full nav-link']")
	private WebElement orangeCartButton;

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-border btn-signin']")
	private WebElement signInButton;

	@FindBy(how = How.XPATH, using = "//figure//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part count-active']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']")
	private WebElement porsionItemAddButton;

	@FindBy(how = How.XPATH, using = "//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']")
	private WebElement quantityItemAddButton;

	public  String productPrice;
	public  String productName;
	public  String productId;
	public  String productQuantity;
	public  Integer productTotalPrice;


	public  List<String> productsListOfId = new ArrayList<String>();

	public  List<String> productsListOfPortionItemId = new ArrayList<String>();
	public  List<String> productsListOfPortionItemName = new ArrayList<String>();
	public  List<String> productsListOfPortionItemPrice= new ArrayList<String>();
	public  List<String> productsListOfPortionItemQuantity = new ArrayList<String>();

	public  List<String> productsListOfQuantityItemId = new ArrayList<String>();
	public  List<String> productsListOfQuantityItemName = new ArrayList<String>();
	public  List<String> productsListOfQuantityItemPrice = new ArrayList<String>();
	public  List<String> productsListOfQuantityItemQuantity = new ArrayList<String>();

	private static final Logger LOG = LogManager.getLogger(MenuPage.class);

	public MenuPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}


	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(yourLocationButton);
	}

	@Step
	public Messages5Page clcikOnYourLocationButton() {
		yourLocationButton.click();
		return PageFactory.initElements(webDriver, Messages5Page.class);
	}

	@Step
	public HomePage openHomePage() {
		webDriver.get("https://mmjtrain.com");
		return PageFactory.initElements(webDriver, HomePage.class);
	}

	@Step
	public ProfilePage clickOnProfileButton() throws InterruptedException {
		profileButton.click();
		return PageFactory.initElements(webDriver, ProfilePage.class);
	}

	@Step
	public MenuPage addProductToCart() {
		addToCart1StItemButton.click();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public CheckOutPage clickOnOrangeCheckOutButton() {
		orangeCartButton.click();
		return PageFactory.initElements(webDriver, CheckOutPage.class);
	}

	@Step
	public SignInPopUp clickOnOrangeCheckOutButtonByNotLoggined() throws Exception {
		IsPageLoaded(orangeCartButton);
		orangeCartButton.click();
		return PageFactory.initElements(webDriver, SignInPopUp.class);
	}

	@Step
	public CheckOutPage clickOnGreenCheckOutButton() {
		greenCartButton.click();
		return PageFactory.initElements(webDriver, CheckOutPage.class);
	}

	@Step
	public SignInPopUp clickOnSignInButton() {
		signInButton.click();
		return PageFactory.initElements(webDriver, SignInPopUp.class);
	}

	@Step
	public MenuPage addToCartSearchedPortionProduct(Map<String,String> testdata)  throws InterruptedException {

		List<WebElement> productListOfPortionButton = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//span[.='" + testdata.get("Porsion") + "']"));
		List<WebElement> productListOfAddButtons = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//span[.='" + testdata.get("Porsion") + "']/ancestor::div[@class='product-order']//button[.='Add to Cart']"));
		List<WebElement> productsListOfPrice = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//span[.='" + testdata.get("Porsion") + "']/ancestor::div[@class='product-packs title']/div[@class='pack-price text-color']//span[@class='price-val']"));
		List<WebElement> productsListOfName = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//span[.='" + testdata.get("Porsion") + "']/ancestor::div[@class='product-details']/h2"));
		List<WebElement> productsListOfId = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//span[.='" + testdata.get("Porsion") + "']/ancestor::figure"));

		productsListOfId.add(webDriver.findElement(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//span[.='" + testdata.get("Porsion") + "']/ancestor::figure")));

		productListOfPortionButton.get(0).click();
		productPrice = "$" + productsListOfPrice.get(0).getText();
		productName = productsListOfName.get(0).getText();
		productId = productsListOfId.get(0).getAttribute("id");
		productListOfAddButtons.get(0).click();

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage addToCartSearchedQuantityProduct(Map<String,String> testdata)  throws InterruptedException {
		int countOfClicks = Integer.parseInt(testdata.get("Quantity"));

		List<WebElement> productListOfPlusButton = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//div[@class='pack-count three-packs text-color-2']/div[@class='count-part count-plus']"));
		List<WebElement> productsListOfId = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//div[@class='pack-count three-packs text-color-2']/div[@class='count-part count-plus']/ancestor::figure"));
		List<WebElement> productsListOfAddButton = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//div[@class='pack-count three-packs text-color-2']/div[@class='count-part count-plus']/ancestor::div[@class='product-details']//button[@class='btn-main btn-standard btn-add']"));
		List<WebElement> productsListOfPrice = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//div[@class='pack-count three-packs text-color-2']/div[@class='count-part count-plus']/ancestor::div[@class='product-details']//span[@class='price-val']"));
		List<WebElement> productsListOfName = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "']//div[@class='pack-count three-packs text-color-2']/div[@class='count-part count-plus']/ancestor::div[@class='product-details']/h2[@class='title product-name']"));

		for (int i = 0; i < countOfClicks; i++) {
            productListOfPlusButton.get(0).click();
		}
		productPrice = "$" + productsListOfPrice.get(0).getText();
		productName = productsListOfName.get(0).getText();
		productId = productsListOfId.get(0).getAttribute("id");
		productQuantity = Integer.toString(countOfClicks + 1) ;
		productsListOfAddButton.get(0).click();

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage addToCartAllAvailableProductsInStore()  throws InterruptedException {

		List<WebElement> productsListOfAddButton = webDriver.findElements(By.xpath("//button[@class='btn-main btn-standard btn-add']"));

		for (int i = 0; i < productsListOfAddButton.size(); i++) {
			productsListOfAddButton.get(i).click();
			productQuantity = Integer.toString(i+1) ;
		}
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage addToCart2TypesOfAllAvailableProductsInStore()  throws InterruptedException {
		List<WebElement> productsListOfItems = webDriver.findElements(By.xpath("//figure"));
		List<WebElement> productListOfAddButtons = webDriver.findElements(By.xpath("//button[@class='btn-main btn-standard btn-add']"));
		List<WebElement> productsListOfPrice = webDriver.findElements(By.xpath("//figure//span[@class='price-val']"));
		List<Integer> productsListOfAllPricesInt = new ArrayList<Integer>();

		int productSumPrice = 0;
		for (int i = 0; i < productsListOfItems.size(); i++) {
			productsListOfId.add(productsListOfItems.get(i).getAttribute("id"));
			productsListOfAllPricesInt.add(Integer.parseInt(productsListOfPrice.get(i).getText()));
			productSumPrice += productsListOfAllPricesInt.get(i);
			productTotalPrice = productSumPrice;
			productListOfAddButtons.get(i).click();
		}

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage AddToCartAndThanUpdatePoritonTypeOfProduct()  throws InterruptedException {
//		Take first portion type item and click to "Add to Cart" button
		List<WebElement> listOfAddToCartButtonsOfPortionItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part count-active']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']"));
//		Select another portion of product item
		List<WebElement> selectSecondPortion = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part']/span"));
//		List of Prices
//		List<WebElement> listOfPrices = webDriver.findElements(By.xpath("//div[@class='pack-count three-packs text-color-2 count-select']/following::div[@class='pack-price text-color']//span[@class='price-val']"));
		List<WebElement> listOfPrices = webDriver.findElements(By.xpath("//div[@class='pack-count three-packs text-color-2 count-select']/following::div[1]//span[@class='price-val']"));
//		List of Item Id
		List<WebElement> productsListOfId = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/ancestor::figure"));
//		List of Item Name
		List<WebElement> productsListOfName = webDriver.findElements(By.xpath("//div[@class='product-details']//div[@class='pack-count three-packs text-color-2 count-select']/ancestor::div[@class='product-details']/h2[@class='title product-name']"));

		for (int i = 0; i < 1; i++) {
			listOfAddToCartButtonsOfPortionItems.get(i).click();
			productId = productsListOfId.get(i).getAttribute("id");
			productName = productsListOfName.get(i).getText();
			Thread.sleep(2000);
			for (int j = 0; j < 1; j++){
				selectSecondPortion.get(j).click();
				productPrice = listOfPrices.get(j).getText();
				productQuantity = webDriver.findElements(By.xpath("//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part count-active']/span")).get(j).getText();
			}
		}
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage AddToCartAndThanUpdateQuanitytTypeOfProductUsingPlus()  throws InterruptedException {
//		Take list first "Add to Cart" button for Quantity item
		List<WebElement> listOfAddToCartButtonsOfQuantityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']"));
//		Take list of "plus" button for Quantity item
		List<WebElement> selectPlusButton = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//div[@class='count-part count-plus']/span"));
//		List of Prices
		List<WebElement> listOfPrices = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure//div[@class='pack-price text-color']//span[@class='price-val']"));
//		List of Item Id
		List<WebElement> productsListOfId = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure"));
//		List of Item Name
		List<WebElement> productsListOfName = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure//div[@class='product-details']/h2[@class='title product-name']"));
//		List of Item Quantity
		List<WebElement> productsListQuantity = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//div[@class='count-part count-value']/span"));

		for (int i = 0; i < 1; i++) {
			listOfAddToCartButtonsOfQuantityItems.get(i).click();
			productId = productsListOfId.get(i).getAttribute("id");
			productName = productsListOfName.get(i).getText();
			Thread.sleep(2000);
			for (int j = 0; j < 1; j++){
				selectPlusButton.get(j).click();
				productPrice = listOfPrices.get(j).getText();
				productQuantity = productsListQuantity.get(j).getText();

			}
		}
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage AddToCartAndThanUpdateQuanitytTypeOfProductUsingMinusButton()  throws InterruptedException {
//		Take list first "Add to Cart" button for Quantity item
		List<WebElement> listOfAddToCartButtonsOfQuantityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']"));
//		Take list of "plus" button for Quantity item
		List<WebElement> selectPlusButton = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//div[@class='count-part count-plus']/span"));
//		Take list of "minus" button for Quantity item
		List<WebElement> selectMinusButton = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//div[@class='count-part count-minus']/span"));
//		List of Prices
		List<WebElement> listOfPrices = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure//div[@class='pack-price text-color']//span[@class='price-val']"));
//		List of Item Id
		List<WebElement> productsListOfId = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure"));
//		List of Item Name
		List<WebElement> productsListOfName = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure//div[@class='product-details']/h2[@class='title product-name']"));
//		List of Item Quantity
		List<WebElement> productsListQuantity = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//div[@class='count-part count-value']/span"));

		for (int i = 0; i < 1; i++) {
			listOfAddToCartButtonsOfQuantityItems.get(i).click();
			productId = productsListOfId.get(i).getAttribute("id");
			productName = productsListOfName.get(i).getText();
			Thread.sleep(2000);
			for (int j = 0; j < 1; j++){
				selectPlusButton.get(j).click();
				selectMinusButton.get(j).click();
				productPrice = listOfPrices.get(j).getText();
				productQuantity = productsListQuantity.get(j).getText();

			}
		}
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage addToCartPorіonTypeOfProduct()  throws InterruptedException {
//		Take first portion type item and click to "Add to Cart" button
		List<WebElement> listOfAddToCartButtonsOfPortionItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part count-active']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']"));
//		List of Item Id
		List<WebElement> productsListOfId = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/ancestor::figure"));

		for (int i = 0; i < 1; i++) {
			productId = productsListOfId.get(i).getAttribute("id");
			listOfAddToCartButtonsOfPortionItems.get(i).click();
			Thread.sleep(2000);
		}
		return PageFactory.initElements(webDriver, MenuPage.class);
	}


	@Step
	public MenuPage addToCartQuantityTypeOfProduct()  throws InterruptedException {
//		Take first quantity type item and click to "Add to Cart" button
		List<WebElement> listOfAddToCartButtonsOfQauntityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']"));
//		List of Item Id
		List<WebElement> productsListOfId = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure"));

		for (int i = 0; i < 1; i++) {
			productId = productsListOfId.get(i).getAttribute("id");
			listOfAddToCartButtonsOfQauntityItems.get(i).click();
			Thread.sleep(2000);
		}
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage refreshPage()  throws InterruptedException {
		webDriver.navigate().refresh();
		return PageFactory.initElements(webDriver, MenuPage.class);
	}


	public void selectFilterParameter(Map<String, String> testdata) {
		webDriver.findElement(By.xpath("//div[@class='filter-item' and @data-filter='" + testdata.get("Parameters") + "']")).click();
	}

	@Step
	public MenuPage selectInFilterStrains(Map<String, String> testdata) {
		//		Straine fileter parameter
		webDriver.findElement(By.xpath("//div[@data-filtersgroup='strains']/div[@data-filter='" + testdata.get("Strains") + "']")).click();

		List<WebElement> listOfFoundedProduct = webDriver.findElements(By.xpath("//figure[@data-strain='sativa' or @data-product='accessory' and @style='opacity: 1;']"));
		productQuantity = Integer.toString(listOfFoundedProduct.size());

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage selectInFilterProduct(Map<String, String> testdata) {
		//		Product fileter parameter
		webDriver.findElement(By.xpath("//div[@data-filtersgroup='products']/div[@data-filter='" + testdata.get("Products") + "']")).click();

		List<WebElement> listOfFoundedProduct = webDriver.findElements(By.xpath("//figure[@data-product='" + testdata.get("Products") + "' and @style='opacity: 1;']"));
		productQuantity = Integer.toString(listOfFoundedProduct.size());

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage selectInFilterProductsAndStrains(Map<String, String> testdata) {
		//		Straine fileter parameter
		webDriver.findElement(By.xpath("//div[@data-filtersgroup='strains']/div[@data-filter='" + testdata.get("Strains") + "']")).click();
		//		Product fileter parameter
		webDriver.findElement(By.xpath("//div[@data-filtersgroup='products']/div[@data-filter='" + testdata.get("Products") + "']")).click();

		List<WebElement> listOfFoundedProduct = webDriver.findElements(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "' and @style='opacity: 1;']"));
		productQuantity = Integer.toString(listOfFoundedProduct.size());

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage AddToCartAllAvailablePartionTypesOfProductsItems()  throws InterruptedException {
//		List of Item ID for all portion product item types
		List<WebElement> productsListOfIdForPortionItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/ancestor::figure"));
//		List of Item Name for all portion product item types
		List<WebElement> productsListOfNameForPortionItems = webDriver.findElements(By.xpath("//div[@class='product-details']//div[@class='pack-count three-packs text-color-2 count-select']/ancestor::div[@class='product-details']/h2[@class='title product-name']"));
//		List of Prices for all portion product item types
		List<WebElement> listOfPricesForPortionItems = webDriver.findElements(By.xpath("//div[@class='pack-count three-packs text-color-2 count-select']/following::div[1]//span[@class='price-val']"));
//		List of Item Quantity for all portion product item types
		List<WebElement> productsListOfQuantityForPortionItems = webDriver.findElements(By.xpath("//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part count-active']/span"));
//		"Add to Cart" button for all portion product item types
		List<WebElement> listOfAddToCartButtonsForPortionItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part count-active']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']"));

		for (int i = 0; i < listOfAddToCartButtonsForPortionItems.size(); i++){
			productsListOfPortionItemId.add(productsListOfIdForPortionItems.get(i).getAttribute("id"));
			productsListOfPortionItemName.add(productsListOfNameForPortionItems.get(i).getText());
			productsListOfPortionItemPrice.add("$" + listOfPricesForPortionItems.get(i).getText());
			productsListOfPortionItemQuantity.add(productsListOfQuantityForPortionItems.get(i).getText());
			listOfAddToCartButtonsForPortionItems.get(i).click();
		}

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage AddToCartAllAvailableQuantityTypesOfProductsItems()  throws InterruptedException {
//		List of Item ID  for all quantity product item types
		List<WebElement> productsListOfIdForQuantityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure"));
//		List of Item Name for all quantity product item types
		List<WebElement> productsListOfNameForQuantityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure//div[@class='product-details']/h2[@class='title product-name']"));
//		List of Prices for all quantity product item types
		List<WebElement> listOfPricesForQuantityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::figure//div[@class='pack-price text-color']//span[@class='price-val']"));
//		List of Item Quantity for all quantity product item types
		List<WebElement> productsListOfQuantityForQuantityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//div[@class='count-part count-value']/span"));
//		"Add to Cart" button for all quantity product item types
		List<WebElement> listOfAddToCartButtonsForQuantityItems = webDriver.findElements(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//button[@class='btn-main btn-standard btn-add']"));



		for (int i = 0; i < listOfAddToCartButtonsForQuantityItems.size(); i++){
			productsListOfQuantityItemId.add(listOfAddToCartButtonsForQuantityItems.get(i).getText());
			productsListOfQuantityItemName.add(productsListOfNameForQuantityItems.get(i).getText());
			productsListOfQuantityItemPrice.add("$" + listOfPricesForQuantityItems.get(i).getText());
			productsListOfQuantityItemQuantity.add(productsListOfQuantityForQuantityItems.get(i).getText());
			listOfAddToCartButtonsForQuantityItems.get(i).click();
		}

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage createInDbProductOzOuncePortion(String generNumber1, int inStock, int price) throws SQLException {
		Menu_Products.insertProductOz(generNumber1, inStock);
		Menu_Product_Prices.insertMenuProductPriceForOunceOZ(generNumber1, price);
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage createInDbPortionOzHalfOunceAndOunceProduct(String generNumber1, int inStock, int ouncePrice, int halfOuncePrice) throws SQLException {
		Menu_Products.insertProductOz(generNumber1, inStock);
		Menu_Product_Prices.insertMenuProductPriceForHalfOunceAndOunceOZ(generNumber1, ouncePrice, halfOuncePrice);
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage createInDbProductEachQuantity(String generNumber1, int inStock, int price) throws SQLException {
		Menu_Products.insertProductEach(generNumber1, inStock);
		Menu_Product_Prices.insertMenuProductPriceForEach(generNumber1, price);
		return PageFactory.initElements(webDriver, MenuPage.class);
	}


	@Step
	public MenuPage createInDbProductGHalfPortion(String generNumber1, int inStock, int price) throws SQLException {
		Menu_Products.insertProductG(generNumber1, inStock);
		Menu_Product_Prices.insertMenuProductPriceForHalfG(generNumber1, price);
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	public void deleteInDbProductPromocodeUserAndOrder(String generNumber1) throws SQLException {
		Menu_Product_Prices.deleteMenuProductById(generNumber1);
		Menu_Products.deleteProductById(generNumber1);
		Promocodes.deletePromocode(generNumber1);
		Users.deleteUser(generNumber1);
		Orders.deleteOrder(generNumber1);
	}

	public void deleteInDbProductPromocode(String generNumber1) throws SQLException {
		Menu_Product_Prices.deleteMenuProductById(generNumber1);
		Menu_Products.deleteProductById(generNumber1);
		Promocodes.deletePromocode(generNumber1);
		Users.deleteUser(generNumber1);
		Orders.deleteOrder(generNumber1);
	}

	public void userAndOrder(String generNumber1) throws SQLException {
		Menu_Product_Prices.deleteMenuProductById(generNumber1);
		Menu_Products.deleteProductById(generNumber1);
		Promocodes.deletePromocode(generNumber1);
		Users.deleteUser(generNumber1);
		Orders.deleteOrder(generNumber1);
	}

	@Step
	public MenuPage selectTypeOfPortionBeforeAddProduct (String generNumber1, String portion) {

		WebElement choosePortion = webDriver.findElement(By.xpath("//figure[@id='007bc586a1bf71a9cde462ce5462" + generNumber1 + "']//div[@class='pack-count three-packs text-color-2 count-select']/div[.='" + portion + "']"));
		choosePortion.click();

		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage selectQuanitytTypeOfProductUsingPlus(String generNumber1, int quantityOfClicks)  throws InterruptedException {

		WebElement plusButton = webDriver.findElement(By.xpath("//figure[@id='007bc586a1bf71a9cde462ce5462" + generNumber1 + "']//div[@class='count-part count-plus']"));

		for (int i = 0; i < quantityOfClicks; i++) {
			plusButton.click();
		}
		return PageFactory.initElements(webDriver, MenuPage.class);
	}

	@Step
	public MenuPage addToCartCreatedInDbProduct(String generNumber1) {
		String priceOfSelectedPortion;

		priceOfSelectedPortion = webDriver.findElement(By.xpath("//figure[@id='007bc586a1bf71a9cde462ce5462" + generNumber1 + "']//span[@class='price-val']")).getText();
//		click on addToCartButton
		webDriver.findElement(By.xpath("//figure[@id='007bc586a1bf71a9cde462ce5462" + generNumber1 + "']//button[@class='btn-main btn-standard btn-add']")).click();
//		get price of created product
		productTotalPrice = Integer.parseInt(priceOfSelectedPortion);
		return PageFactory.initElements(webDriver, MenuPage.class);
	}



	@Step
	public int createInDbCreditMoney(int discount, String generNumber1) throws SQLException {
		int creditMoneyValue;
		Users.updateDiscount(discount, generNumber1);
		creditMoneyValue = discount;
		return creditMoneyValue;
	}

}