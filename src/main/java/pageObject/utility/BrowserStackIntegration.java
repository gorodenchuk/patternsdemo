package pageObject.utility;

import com.browserstack.local.Local;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.FileReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by NGorodenchuk on 7/24/2017.
 */
public class BrowserStackIntegration {

    private Local l;
    public WebDriver driver;
    public void setUp() throws Exception {
        String username = "alex3545";
        String accessKey = "N9itYTzAx9Q2XqDWbUox";
        String server = "hub-cloud.browserstack.com";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("os", "OS X");
        capabilities.setCapability("os_version", "Sierra");
        capabilities.setCapability("browser", "Safari");
        capabilities.setCapability("browser_version", "10.1");
        capabilities.setCapability("resolution", "1920x1080");
        capabilities.setCapability("browserstack.debug", "true");
        capabilities.setCapability("build", "First build");

        driver = new RemoteWebDriver(new URL("http://"+username+":"+accessKey+"@"+server+"/wd/hub"), capabilities);
    }
}
