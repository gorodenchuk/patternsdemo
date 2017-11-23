package pageObject.webDriver;


import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
//import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.annotations.Parameters;
import pageObject.utility.EventHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

/**
 * A factory that returns a singleton of WebDriver object.
 */
public class WebDriverFactory {

	private static final String CHROME = "chrome";
	private static final String FIREFOX = "firefox";
	private static final String INTERNETEXPLORER = "ie";
	private static final String SAFARI = "safari";
	private static final String BS_CHROME = "chrome_56";
	private static WebDriver webDriver;
	private static EventFiringWebDriver eventDriver;
	private static DesiredCapabilities dc;
	public static final String USERNAME = "alex3545";
	public static final String AUTOMATE_KEY = "N9itYTzAx9Q2XqDWbUox";
	public static final String URL = "https://" + USERNAME + ":" + AUTOMATE_KEY + "@hub-cloud.browserstack.com/wd/hub";
//	private static  String driver_Name = "MMJTrainQA";
//	private static  String driver_path = "C:\\DBUsersTable\\NGorodenchuk.ARTFULBITS\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\igxrvgxv.MMJTrainQA";

	private WebDriverFactory() {

	}

	/**
	 * Gets the single instance of WebDriverFactory.
	 *
	 * @param browser the browser set in properties
	 * @return single instance of WebDriverFactory
	 * @throws Exception the exception of invalid browser property
	 */
	public static WebDriver getInstance(String browser) throws Exception {
		if (eventDriver == null) {
			if (webDriver == null) {
			if (CHROME.equals(browser)) {

				setChromeDriver();

				ChromeOptions options = new ChromeOptions();
				// set some options
				options.addArguments("Test-type");

//				Accept auto geolocationtest in pop-up for chrome
//				options.setExperimentalOption("prefs", new JSONObject().
//						put("profile.default_content_settings.geolocationtest", 1));

				dc = DesiredCapabilities.internetExplorer();
				dc.setCapability(ChromeOptions.CAPABILITY, options);

				webDriver = new ChromeDriver(dc);

			} else if (FIREFOX.equals(browser)) {

				ProfilesIni profile = new ProfilesIni();
				FirefoxProfile myprofile = profile.getProfile("MMJTrainQA");

				webDriver = new FirefoxDriver(myprofile);

			} else if (INTERNETEXPLORER.equals(browser)) {

				setIeDriver();

				dc = DesiredCapabilities.internetExplorer();
				dc.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, false);

				webDriver = new InternetExplorerDriver(dc);

			} else if (SAFARI.equals(browser)) {

				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability("browser", "Safari");
				dc.setCapability("browser_version", "10.1");
				dc.setCapability("os", "OS X");
				dc.setCapability("os_version", "Sierra");
				dc.setCapability("resolution", "1920x1080");
				dc.setCapability("browserstack.safari.enablePopups", "true");
				dc.setCapability("project", "com.mmjtraine");

				webDriver = new RemoteWebDriver(new URL(URL), dc);

			} else if (BS_CHROME.equals(browser)) {

				DesiredCapabilities dc = new DesiredCapabilities();
				dc.setCapability("browser", "Chrome");
				dc.setCapability("browser_version", "56.0");
				dc.setCapability("os", "Windows");
				dc.setCapability("os_version", "10");
				dc.setCapability("resolution", "1920x1080");
				dc.setCapability("browserstack.safari.enablePopups", "true");
				dc.setCapability("project", "com.mmjtraine");

				webDriver = new RemoteWebDriver(new URL(URL), dc);

			} else
				throw new Exception(
						"Invalid browser property set in configuration file");

			}

			eventDriver = new EventFiringWebDriver(webDriver);
			eventDriver.register(new EventHandler());
		}
		return eventDriver;
	}

	public static WebDriver getSetDriver() {
		if (eventDriver == null) {
			throw new RuntimeException("Driver is not set");
		}
		return eventDriver;
	}



	/**
	 * Kill driver instance.
	 * @throws Exception
	 */
	public static void killDriverInstance() throws Exception {
		if (webDriver != null) {
			webDriver.quit();
			webDriver = null;
			eventDriver.quit();
			eventDriver = null;
		}

	}

	/**
	 * Sets the chrome driver path for specific OS.
	 *
	 * @throws Exception the exception
	 */
	public static void setChromeDriver() throws Exception {
		String osName = System.getProperty("os.name").toLowerCase();
		StringBuffer chromeBinaryPath = new StringBuffer(
				"src/main/java/resources/");

		if (osName.startsWith("win")) {
			chromeBinaryPath.append("chrome-win/chromedriver.exe");
		} else if (osName.startsWith("lin")) {
			chromeBinaryPath.append("chrome-lin/chromedriver");
		} else if (osName.startsWith("mac")) {
			chromeBinaryPath.append("chrome-mac/chromedriver");
		} else
			throw new Exception("Your OS is invalid for webdriver tests");

		System.setProperty("webdriver.chrome.driver",
				chromeBinaryPath.toString());
	}

	public static void setIeDriver() throws Exception {
		String osName = System.getProperty("os.name").toLowerCase();
		StringBuffer ieBinaryPath = new StringBuffer(
				"src/main/java/resources/");

		if (osName.startsWith("win")) {
			ieBinaryPath.append("ie-win/IEDriverServer.exe");
		} else
			throw new Exception("Your OS is invalid for webdriver tests");

		System.setProperty("webdriver.ie.driver",
				ieBinaryPath.toString());
	}


}
