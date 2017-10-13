package pageObject.pages.adminpanel.commonWidgets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import pageObject.pages.Page;
import pageObject.pages.adminpanel.pages.ClientsPage;
import pageObject.pages.adminpanel.pages.OrdersPage;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Created by NGorodenchuk on 10/10/2017.
 */

public class NavigationPanel extends Page {

    @FindBy(how = How.XPATH, using = "//ul[@class='nav navbar-nav side-nav']//a[@href='/admin/orders']")
    private WebElement ordersNavMenuItem;

    @FindBy(how = How.XPATH, using = "//ul[@class='nav navbar-nav side-nav']//a[@href='/admin/clients']")
    private WebElement clientsNavMenuItem;

    private static final Logger LOG = LogManager.getLogger(NavigationPanel.class);


    public NavigationPanel(WebDriver webDriver) {
        super(webDriver);
        // TODO Auto-generated constructor stub
    }

    public boolean IsLoaded() throws Exception {
        return IsPageLoaded(ordersNavMenuItem);
    }

    @Step
    public OrdersPage clickOnordersNavMenuItem() {
        ordersNavMenuItem.click();
        return PageFactory.initElements(webDriver, OrdersPage.class);
    }

    @Step
    public ClientsPage clickOnClientsNavMenuItem() {
        clientsNavMenuItem.click();
        return PageFactory.initElements(webDriver, ClientsPage.class);
    }
}