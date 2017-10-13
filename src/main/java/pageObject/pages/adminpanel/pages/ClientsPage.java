package pageObject.pages.adminpanel.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import ru.yandex.qatools.allure.annotations.Step;

public class ClientsPage extends Page {


	private static final Logger LOG = LogManager.getLogger(ClientsPage.class);


	public ClientsPage(WebDriver webDriver) {
		super(webDriver);
		// TODO Auto-generated constructor stub
	}
	
	@Step
	public EditClientPage clickOnClientStatusButton(String generNumber1) {
		WebElement clientStatusButton = webDriver.findElement(By.xpath("//span[.='mmjtrain.test" + generNumber1 + "" +
				"@gmail.com']/ancestor::ul[@class='list-unstyled data-list text-color-1']//li[@class='c-verification']/a"));
		clientStatusButton.click();
		return PageFactory.initElements(webDriver, EditClientPage.class);
	}

	public String getPhoneNumber(String generNumber1) {
		String phone = webDriver.findElement(By.xpath("//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']/ancestor::li[@class='c-contact']/span[2]")).getText();
		return phone;
	}

	@Step
	public String getUserName(String generNumber1) {
		String name = webDriver.findElement(By.xpath("//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']/ancestor::li[@class='c-contact']")).getText();

		// This part of code delete all text after first space and shows only first user name or email
		int firstSpace = (name.indexOf(" ") >= 0) ? name.indexOf(" ") : name.length()-1;
		return name.substring(0,firstSpace);
//		return name;
	}
}
