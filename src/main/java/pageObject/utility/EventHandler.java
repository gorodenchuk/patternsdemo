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
//        System.out.println("From Listener: " + Page.currentClass);
//
//        try {
//            EventHandler.getConstantName(by, Page.currentClass);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }


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

    public static void getConstantName(By by, Class<?> currentClass) throws IllegalAccessException, ClassNotFoundException {
        for (Field fieldName : currentClass.getDeclaredFields()) {
            Class<?> c = Class.forName(currentClass.toString());
//            if (fieldName.getType().getSimpleName().equals("WebElement")) {
                Object fieldValue = fieldName.get(c);
                System.out.println("+++++++++++++++++++++++++++++++++++" + fieldValue);

//                if (val == by){
//                    System.out.println("NAME OF WEBELEMENT: " + field.getName());
//                }
//            }


            System.out.println("Field name: " + fieldName);
            System.out.println("Field getTypeName: " + fieldName.getType().getTypeName());
            System.out.println("Field getSimpleName: " + fieldName.getType().getSimpleName());
            System.out.println("Field getSimpleName: " + fieldName.get(currentClass));

            System.out.println("Field getName: " + fieldName.getName());

        }
    }
}