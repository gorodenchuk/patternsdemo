package pageObject.utility;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;
import pageObject.mmjtraine.pages.HomePage;
import pageObject.mmjtraine.pages.Page;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by gorod on 28.09.2016.
 */
public class EventHandler implements WebDriverEventListener {

    private static final Logger LOG = LogManager.getLogger(EventHandler.class);

    @Override
    public void beforeNavigateTo(String url, WebDriver driver) {
    }

    @Override
    public void afterNavigateTo(String url, WebDriver driver) {
//        TestStepReporter.report("Navigated to the url:", url);
        LOG.info("WebDriver navigated to '" + url + "'");


    }

    @Override
    public void beforeNavigateBack(WebDriver driver) {

    }

    @Override
    public void afterNavigateBack(WebDriver driver) {

    }

    @Override
    public void beforeNavigateForward(WebDriver driver) {

    }

    @Override
    public void afterNavigateForward(WebDriver driver) {

    }

    @Override
    public void beforeNavigateRefresh(WebDriver webDriver) {

    }

    @Override
    public void afterNavigateRefresh(WebDriver webDriver) {

    }

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        LOG.debug("Element should be found by" + by);
        System.out.println("From Listener: " + Page.currentClass);

        try {
            EventHandler.getConstantName(by, Page.currentClass);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        LOG.debug("WebDriver found element" + by);
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        LOG.info("Clicked successfull");
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver) {

    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver) {
//        TestStepReporter.report(
//                "Change was performed on element with locator:",
//                getElementDescriptorXPATH(driver, element)
//                        + "; Element html tag: "
//                        + getElementDescriptorName(driver, element));
    }

    @Override
    public void beforeScript(String script, WebDriver driver) {

    }

    @Override
    public void afterScript(String script, WebDriver driver) {
//        TestStepReporter.reportln("Execution of script performed ", script);
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {
//        TestStepReporter.reportln("WebDriver Exception throwed:", throwable.getMessage());
    }

    /**
     * Gets the element descriptor xpath.
     * <p>
     * //     * @param driver the driver
     *
     * @return the element descriptor xpath
     */
//    public String getElementDescriptorXPATH(WebDriver driver, WebElement element) {
//        return (String) ((JavascriptExecutor) driver)
//                .executeScript(
//                        "gPt=function(c){if(c.id!=='')"
//                                + "{return'id(\"'+c.id+'\")'}"
//                                + "if(c===document.body){return c.tagName}"
//                                + "var a=0;var e=c.parentNode.childNodes;"
//                                + "for(var b=0;b<e.length;b++){"
//                                + "var d=e[b];if(d===c){"
//                                + "return gPt(c.parentNode)+'/'+c.tagName+"
//                                + "'['+(a+1)+']'}if(d.nodeType===1&&d.tagName===c.tagName)"
//                                + "{a++}}};return gPt(arguments[0]).toLowerCase();",
//                        element);
//    }
    public static void getConstantName(By by, Class<?> currentClass) throws IllegalAccessException {
        for (Field field : currentClass.getDeclaredFields()) {
            if (field.getType().toString().equals("WebElement")) {
                WebElement val = (WebElement) field.get(currentClass);

                if (val == by){
                    System.out.println("NAME OF WEBELEMENT: " + field.getName());
                }
            }
        }
    }
}
    /**
     * Gets the element descriptor name.
     *
     * @param driver the driver
     * @param element the element
     * @return the element descriptor name and element text
     */
//    public String getElementDescriptorName(WebDriver driver, WebElement element) {
//        return element.getTagName() + "<p>" + element.getText();
//    }
//}