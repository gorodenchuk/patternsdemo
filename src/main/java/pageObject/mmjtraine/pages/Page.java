package pageObject.mmjtraine.pages;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * Abstract class representation of a Page in the UI with additional features. Page object pattern
 */
public abstract class Page <T extends Page>{

//    final static public String WEB_URL = "http://localhost/";
    final static public String WEB_URL = "https://devtests.mmjtrain.com/";
    protected WebDriver webDriver;
    private static final Logger LOG = LogManager.getLogger(Page.class);
    public static Class<?> currentClass;
	/*
	 * Constructor injecting the WebDriver interface
	 *
	 * @param webDriver
	 */

    public Page(WebDriver webDriver) {
        this.webDriver = webDriver;
        currentClass  = getClass();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public String getTitle() {
        return webDriver.getTitle();
    }

    public boolean isElementPresent(WebElement element) {
        try {
            element.isEnabled();
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForTextPresent(WebElement webelement, String text) throws InterruptedException {
        int waitRetryDelayMs = 1;
        int timeOut = 10;
        boolean first = true;

        for (int milliSecond = 0; ; milliSecond += waitRetryDelayMs) {
            if (milliSecond > timeOut * 1000) {
                System.out.println("Timeout: Text '" + text + "' is not found ");
                break;
            }

            if (webelement.getText().contains(text)) {
                if (!first) System.out.println("Text is found: '" + text + "'");
                break;
            }

            if (first) System.out.println("Waiting for text is present: '" + text + "'");

            first = false;
            Thread.sleep(waitRetryDelayMs);
        }
    }


    public WebElement fluentWaitUntilElementBecomeClicable(WebElement webelement, String text) throws InterruptedException {
        int waitRetryDelayMs = 500;
        int timeOut = 10000;

        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(timeOut, TimeUnit.MILLISECONDS)
                .pollingEvery(waitRetryDelayMs, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(webelement));
            LOG.info("Element " + text + " found");
            return webelement;

        } catch (Exception  e){
            LOG.info("Element " + text + " NOT found");
            e.printStackTrace();
            return null;
        }
    }

    public WebElement waitUntilElementBecomeClicable(WebElement webelement, String text) throws InterruptedException {
        int timeOutInSeconds = 10;

        WebDriverWait wait = new WebDriverWait(webDriver, timeOutInSeconds);

        try {
            wait.until(ExpectedConditions.elementToBeClickable(webelement));
            LOG.info("Element " + text + " found");
            return webelement;

        } catch (Exception  e){
            LOG.info("Element " + text + " NOT found");
            e.printStackTrace();
            return null;
        }
    }

    public boolean IsPageLoaded(WebElement element) throws Exception {
        int waitRetryDelayMs = 500;
        int timeOut = 10000;

        Wait<WebDriver> wait = new FluentWait<WebDriver>(webDriver)
                .withTimeout(timeOut, TimeUnit.MILLISECONDS)
                .pollingEvery(waitRetryDelayMs, TimeUnit.MILLISECONDS)
                .ignoring(NoSuchElementException.class);
        try {
        wait.until(ExpectedConditions.visibilityOf(element));
            LOG.info("Page is found");
            return true;

        } catch (Exception  e){
            LOG.info("Page is NOT found");
            e.printStackTrace();
            return false;
        }
    }

    public static boolean compareList(List ls1, List ls2){
        return ls1.toString().contentEquals(ls2.toString())?true:false;
    }


}


