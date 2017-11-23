package pageObject.mmjtraine.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import pageObject.mmjtraine.businessobject.productitembuilder.PortionProductItem;
import pageObject.mmjtraine.businessobject.productitembuilder.QuantityProductItem;
import ru.yandex.qatools.allure.annotations.Step;

public class MenuPage extends Page {

	@FindBy(how = How.XPATH, using = "//div[@class='btn-main btn-border user-location']/span[contains(text(),'Your Location')]")
	private WebElement yourLocationButton;

	@FindBy(how = How.XPATH, using = "//button[@class='btn-main btn-standard btn-cart cart-full nav-link']")
	private static WebElement orangeCartButton;

	private static final Logger LOG = LogManager.getLogger(MenuPage.class);

	public MenuPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}

	public boolean IsLoaded() throws Exception {
		return IsPageLoaded(yourLocationButton);
	}

	@Step
	public MenuPage selectPortionProductItem(PortionProductItem portionProductItem) {
		WebElement portion = webDriver.findElement(By.xpath("//figure[@data-strain='"
				+ portionProductItem.getStraine() + "' and @data-product='"
				+ portionProductItem.getProductType() + "']//h2[.='"
				+ portionProductItem.getname() + "']/..//span[.='"
				+ portionProductItem.getPortion_1_8() + "']"));
		portion.click();
		return this;
	}

	@Step
	public MenuPage addToCartPortionProductItem(PortionProductItem portionProductItem) {
		WebElement addButton = webDriver.findElement(By.xpath("//figure[@data-strain='"
				+ portionProductItem.getStraine() + "' and @data-product='"
				+ portionProductItem.getProductType() + "']//h2[.='"
				+ portionProductItem.getname() + "']/..//span[.='"
				+ portionProductItem.getPortion_1_8() + "']/ancestor::div[@class='product-order']//button[.='Add to Cart']"));
		addButton.click();
		return this;
	}

	@Step
	public String getPriceOfPortionItem (PortionProductItem portionProductItem){
		String price = webDriver.findElement(By.xpath("//figure[@data-strain='"
				+ portionProductItem.getStraine() + "' and @data-product='"
				+ portionProductItem.getProductType() + "']//h2[.='"
				+ portionProductItem.getname() + "']/..//span[.='"
				+ portionProductItem.getPortion_1_8()
				+ "']/ancestor::div[@class='product-packs title']/div[@class='pack-price text-color']//span[@class='price-val']")).
				getText();
		return price;
	}

	@Step
	public MenuPage selectQuantityProductItem(QuantityProductItem quantityProductItem) {
		WebElement plusButton = webDriver.findElement(By.xpath("//figure[@data-strain='"
				+ quantityProductItem.getStraine() + "' and @data-product='"
				+ quantityProductItem.getProductType() + "']//h2[.='"
				+ quantityProductItem.getname() + "']/..//div[@class='pack-count three-packs text-color-2']/div[@class='count-part count-plus']"));

		for (int i = 0; i < quantityProductItem.getQuantity() -1; i++) {
			plusButton.click();
		}

		return this;
	}

	@Step
	public MenuPage addToCartQuantityProductItem(QuantityProductItem quantityProductItem) {
		WebElement addButton = webDriver.findElement(By.xpath("//figure[@data-strain='"
				+ quantityProductItem.getStraine() + "' and @data-product='"
				+ quantityProductItem.getProductType() + "']//h2[.='"
				+ quantityProductItem.getname() + "']/..//div[@class='pack-count three-packs text-color-2']" +
				"/div[@class='count-part count-plus']/ancestor::div[@class='product-details']//button[@class='btn-main btn-standard btn-add']"));
		addButton.click();
		return this;
	}

}