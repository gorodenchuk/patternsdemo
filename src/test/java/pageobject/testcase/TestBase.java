package pageobject.testcase;


import com.browserstack.local.Local;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Parameters;
import pageObject.mmjtraine.pages.HomePage;
import pageObject.mmjtraine.pages.Page;
import pageObject.utility.TestExecutionListener;
import pageObject.webDriver.WebDriverFactory;
import pageObject.webDriver.WebWindow;

import java.util.concurrent.TimeUnit;




/*
 * Base class for all the Test classes
 */
@Listeners({ TestExecutionListener.class })
public class TestBase {

	protected WebDriver webDriver;
	protected Local l;
	static EventFiringWebDriver eventDriver;
	protected HomePage homePage;
	public String handleHost;
	private static final Logger LOG = LogManager.getLogger(TestBase.class);

	@BeforeMethod(alwaysRun = true)
	@Parameters({"browserName"})
	public void setup(String browserName) throws Exception {
		webDriver = WebDriverFactory.getInstance(browserName);
//		eventDriver = new EventFiringWebDriver(webDriver);
//		eventDriver.register(new EventHandler());
		webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		// webDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		webDriver.manage().window().maximize();
		navigateTo(Page.WEB_URL);
//		webDriver.manage().deleteAllCookies();
		homePage = PageFactory.initElements(webDriver, HomePage.class);
		handleHost = webDriver.getWindowHandle();
	}

	public void setUp(final ITestContext context) {
		context.setAttribute("driverKey", webDriver);
	}

	public void onTestFailure(final ITestResult result) {
		WebDriver driver = (WebDriver) result.getTestContext().getAttribute("driverKey");
	}

	protected  void navigateTo(String URL){
		webDriver.get(URL);
	}

	protected void CreateNewTab() {
		try {
			WebWindow tab2 = new WebWindow(webDriver, "https://88.99.251.182/admin312423423423534534534534534534534534534543");
			LOG.info("New tab is created");
		} catch (Exception e) {
			System.err.println(e.getMessage());
			System.err.println("Couldn't load second page");
			LOG.info("Couldn't load second page");
		}
	}

	protected void SwitchFromSecondTabToFirst() {
		try {
			webDriver.switchTo().window(handleHost);
			webDriver.switchTo().activeElement();
			LOG.info("Switch from second tab to first");
		} catch (Exception e) {
			System.err.println("Couldn't get back to first page");
			LOG.info("Couldn't get back to first page");
		}
	}
	protected void SwitchFromFirstPageToSecond() {
		try {
			for (String handle : webDriver.getWindowHandles()) {
				if (handle != handleHost) {
					webDriver.switchTo().window(handle);
					webDriver.switchTo().activeElement();
					LOG.info("Switch from first tab to second");
				} // смотрим все вкладки (а их две всего); если і-тая вкладка не равна первой handleHost (инициализированой в пункте (а), тогда переключаемся на нее).
			}
		} catch (Exception e) {
			System.err.println("Couldn't get to second page");
			LOG.info("Couldn't get to second page");
		}
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() throws Exception {
		if (webDriver != null) {
			WebDriverFactory.killDriverInstance();
		}
		if(l != null)
			l.stop();

//		if (webDriver != null) {
//			webDriver.quit();
//		}
	}

}
