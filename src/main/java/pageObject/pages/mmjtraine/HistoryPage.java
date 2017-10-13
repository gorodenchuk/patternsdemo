package pageObject.pages.mmjtraine;

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
import pageObject.pages.pop_ups.CancleOrderPopUp;
import pageObject.utility.DateTime;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by NGorodenchuk on 10/5/2017.
 */
public class HistoryPage extends Page {

    @FindBy(how = How.XPATH, using = "//h3[.='Orders History']")
    private WebElement orderHistoryButton;

    public HistoryPage(WebDriver webDriver) {
        super(webDriver);
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(orderHistoryButton);
    }

    private static final Logger LOG = LogManager.getLogger(HistoryPage.class);

    @Step
    public void clickCreatedOrderItem(String orderMakingTime) throws InterruptedException {
        WebElement createdOrder = webDriver.findElement(By.xpath("//div[@class='panel-head']//p[contains(text(), '" + orderMakingTime + "')]"));
        createdOrder.click();
    }

    @Step
    public CancleOrderPopUp clickonCancelButtonOnCreatedOrderItem(String orderMakingTime) {
        WebElement cancelButton = webDriver.findElement(By.xpath("//div[@class='panel-head']//p[contains(text(), '" + orderMakingTime + "')]/ancestor::div[@class='panel-head']/following-sibling::div/div[2]//button[@class='btn-main btn-border btn-cancel']"));

        JavascriptExecutor executor = (JavascriptExecutor)webDriver;
        executor.executeScript("arguments[0].click();", cancelButton);
        return PageFactory.initElements(webDriver, CancleOrderPopUp.class);
    }
}
