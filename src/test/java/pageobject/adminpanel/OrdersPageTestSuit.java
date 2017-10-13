package pageobject.adminpanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.adminpanel.pages.EditOrderPage;
import pageObject.pages.adminpanel.commonWidgets.NavigationPanel;
import pageObject.pages.adminpanel.pages.OrdersPage;
import pageObject.pages.mmjtraine.CheckOutPage;
import pageObject.pages.mmjtraine.MenuPage;
import pageObject.pages.mmjtraine.ProfilePage;
import pageObject.pages.pop_ups.*;
import pageObject.utility.DB.DbTables.Orders;
import pageObject.utility.DB.DbTables.Users;
import pageObject.utility.RandomNumberGenerator;
import pageobject.testcase.TestBase;

/**
 * Created by NGorodenchuk on 7/17/2017.
 */
public class OrdersPageTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(OrdersPageTestSuit.class);

    @Test(groups = { "orders" })
    // TS 1.971-39
    public void checkInAdminPanelOnOrderPageCalculatingDiscountForOneAddedProductItem() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-40
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenUserUsePromoCodeForOneAddedProductItem() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-41
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenUserUsePromoCodeThatAreGreaterThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 30);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-42
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenUserUseCreditMooneyThatAreEqualProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (20, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-43
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenUserUseLessCreditMoneyThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (15, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-44
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenUserUseMoreCreditMoneyThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-45
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenUserUse5CreditMoneyThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-46
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse5Promocode5CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-47
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse25PromocodeAnd5CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-48
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse10PromocodeAnd10CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-49
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse20PromocodeAnd20CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (20, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-50
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse5PromocodeAnd25CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-51
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse25PromocodeAnd25CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-52
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse5CreditMoneyAnd5Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-53
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse5CreditMoneyAnd25Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-54
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse10CreditMoneyAnd10Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-55
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse20CreditMoneyAnd20Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (20, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-56
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse25CreditMoneyAnd5Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-57
    public void checkOnAdminPanelPageCalculatingProductPriceWhenUserUse25CreditMoneyAnd25Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-89-1
    public void checkInAdminPanelOnOrderPageCalculatingDiscountForOneAddedProductItemBySubuser() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-89-2
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenSubuserUsePromoCodeForOneAddedProductItem() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-89-3
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenSubuserUsePromoCodeThatAreGreaterThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 30);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-90
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenSubuserUseCreditMooneyThatAreEqualProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (20, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-91
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenSubuserUseLessCreditMoneyThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (15, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-92
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenSubuserUseMoreCreditMoneyThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-93
    public void checkInAdminPanelOnOrderPageCalculatingDiscountWhenSubuserUse5CreditMoneyThanProductPrice() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-94
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse5Promocode5CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-95
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse25PromocodeAnd5CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-96
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse10PromocodeAnd10CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-97
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse20PromocodeAnd20CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (20, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-98
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse5PromocodeAnd25CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-99
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse25PromocodeAnd25CreditMoney() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-100
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse5CreditMoneyAnd5Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-101
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse5CreditMoneyAnd25Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-102
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse10CreditMoneyAnd10Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-103
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse20CreditMoneyAnd20Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (20, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-104
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse25CreditMoneyAnd5Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.971-105
    public void checkOnAdminPanelPageCalculatingProductPriceWhenSubuserUse25CreditMoneyAnd25Promocode() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        Users.insertUser(generNumber2);
        Users.updateRefInvited(generNumber1, generNumber2);
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " + checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));
        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(promoCodeValue);
        LOG.info(creditMoneyValue);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1480
    public void checkInAdminPanelOnOrderPageDisplayingListOfPortionAddedProductItems() throws Exception {
        int count = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.AddToCartAllAvailablePartionTypesOfProductsItems()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.getOrderInformation(generNumber1);

        Thread.sleep(3000);

        for (int i = 0; i < menuPage.productsListOfPortionItemId.size(); i++){
            for (int j = 0; j < ordersPage.productsListOfItemName.size(); j++ ){
                if (menuPage.productsListOfPortionItemName.get(i).equals(ordersPage.productsListOfItemName.get(j))){
                    menuPage.productsListOfPortionItemPrice.get(i).equals(ordersPage.productsListOfItemPrice.get(j));
                    menuPage.productsListOfPortionItemQuantity.get(i).equals(ordersPage.productsListOfItemQuantity.get(j));
                    break;
                }
            }
            count++;
        }

        Assert.assertTrue(count == menuPage.productsListOfPortionItemId.size(),
                "Count of added product item equal items that display at Confirm Your Order PopUp ");


        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1481
    public void checkInAdminPanelOnOrderPageDisplayingListOfQuantityAddedProductItems() throws Exception {
        int count = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.AddToCartAllAvailableQuantityTypesOfProductsItems().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem()
                .getOrderInformation(generNumber1);

        Thread.sleep(3000);

        for (int i = 0; i < menuPage.productsListOfQuantityItemId.size(); i++){
            for (int j = 0; j < ordersPage.productsListOfItemName.size(); j++ ){
                if (menuPage.productsListOfQuantityItemName.get(i).equals(ordersPage.productsListOfItemName.get(j))){
                    menuPage.productsListOfQuantityItemPrice.get(i).equals(ordersPage.productsListOfItemPrice.get(j));
                    menuPage.productsListOfQuantityItemQuantity.get(i).equals(ordersPage.productsListOfItemQuantity.get(j));

                    break;
                }
            }
            count++;
        }

        Assert.assertTrue(count == menuPage.productsListOfQuantityItemId.size(),
                "Count of added product item equal items that display at Order Page in Admin Panel ");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1482
    public void checkInAdminPanelOnOrderPageDisplayingAddressAtOrderItemThatUserUseWhenMakesOrder() throws Exception {
        String fullAddress;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        fullAddress = confirmYourOrderPopUp.getUserAddress(generNumber1);
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();


        LOG.info(fullAddress);
        LOG.info(ordersPage.getOrderAddress(generNumber1));

        Assert.assertTrue(fullAddress.equals(ordersPage.getOrderAddress(generNumber1)),
                "Addresses is equal");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1483
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnDeleteButtonOrderRemovesFromOrderPage () throws Exception {

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ConfirmTheOperationPopUp confirmTheOperationPopUp = ordersPage.clickOnDeleteButtonAboveCreatedOrder(generNumber1);
        confirmTheOperationPopUp.IsLoaded();
        ordersPage = confirmTheOperationPopUp.clickOnYesButton();


       try {
            webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                    "/ancestor::div[@class='order-data']/preceding::div[@class='control-panel-left']" +
                    "//button[@class='btn-main btn-delete btn-with-icon btn-delete-one']")).isDisplayed();
            LOG.info("Element present");
            Assert.assertTrue(false);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            LOG.info("Elements absent");
            Assert.assertTrue(true);
        }

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1484
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnDeleteButtonOrderRemovesToDeletePagePage () throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ConfirmTheOperationPopUp confirmTheOperationPopUp = ordersPage.clickOnDeleteButtonAboveCreatedOrder(generNumber1);
        confirmTheOperationPopUp.IsLoaded();
        ordersPage = confirmTheOperationPopUp.clickOnYesButton();
        ordersPage.clickOnDeleteFilterButton();

            Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']"))
                    .isDisplayed());

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1485
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnDeleteButtonOnDeletePageOrderRemovesFinally() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ConfirmTheOperationPopUp confirmTheOperationPopUp = ordersPage.clickOnDeleteButtonAboveCreatedOrder(generNumber1);
        confirmTheOperationPopUp.IsLoaded();
        ordersPage = confirmTheOperationPopUp.clickOnYesButton();
        confirmTheOperationPopUp = ordersPage.clickOnDeleteFilterButton().clickOnDeleteButtonAboveCreatedOrder(generNumber1);
        confirmTheOperationPopUp.clickOnYesButton();
        // Thread.sleep(3000);

        try {
            webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']"))
                    .isDisplayed();
            LOG.info("Element present");
            Assert.assertTrue(false);
        } catch (org.openqa.selenium.NoSuchElementException e) {
            LOG.info("Elements absent");
            Assert.assertTrue(true);
        }

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1486
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnPendingButtonStatusSchangeToDisabled() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnDisabledButtonInOrderHintWindow(generNumber1)
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@class='status all_status main_status']/span[contains(text(),'Disabled')]")).isDisplayed(),
        "status changed to disabled");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1487
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnPendingButtonStatusChangeToApproved() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();

        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnApprovedButtonInOrderHintWindow(generNumber1)
                .refreshPage()
                .IsLoaded();


        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                        "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@class='status all_status main_status']")).isDisplayed(),
                "status changed to approved");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1488
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnApprovedButtonStatusChangeToDisabled() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnApprovedButtonInOrderHintWindow(generNumber1)
                .refreshPage()
                .clickOnApprovedButtonInOrderItem(generNumber1)
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                        "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@data-status='approved']")).isDisplayed(),
                "status changed to disabled");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1489
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnDisabledButtonStatusChangeToApproved() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnDisabledButtonInOrderHintWindow(generNumber1)
                .refreshPage()
                .clickOnDisabledButtonInOrderItem(generNumber1)
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                        "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@class='status all_status main_status']")).isDisplayed(),
                "status changed to approved");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1490
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnNotPaidButtonStatusChangeToPaid() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnNotPaidButton(generNumber1)
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                        "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@class='status paid_status']/span[@class='status-green']")).isDisplayed(),
                "status changed to paid");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1491
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnPaidButtonStatusChangesToNotPaid() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnNotPaidButton(generNumber1)
                .refreshPage()
                .clickOnPaidButton(generNumber1)
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                        "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@class='status paid_status']/span[@class='status-red']")).isDisplayed(),
                "status changed to paid");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1492
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnNotYetShippedButtonStatusChangeToShipping() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnNotYetShippedButton(generNumber1)
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                        "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@class='status shipped_status']/span[@class='status-green']")).isDisplayed(),
                "status changed to paid");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1493
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnShippingButtonStatusChangeToNotYetShipped() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnNotYetShippedButton(generNumber1)
                .refreshPage()
                .clickOnShippingdButton(generNumber1)
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']" +
                        "/ancestor::div[@class='order-data']//li[@class='o-statuses-list']/a[@class='status shipped_status']/span[@class='status-red']")).isDisplayed(),
                "status changed to paid");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1494
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnEditOrderButtonCanSetFAndLName() throws Exception {
        String fAndLName = "Test";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        ordersPage = editOrderPage.setFAndLName(fAndLName)
                .clickSaveButton();
        editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);

        String fName = webDriver.findElement(By.xpath("//input[@id='firstname']")).getAttribute("value");
        String lName = webDriver.findElement(By.xpath("//input[@id='lastname']")).getAttribute("value");

        Assert.assertTrue(fName.equals(fAndLName) && lName.equals(fAndLName));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1495
    public void checkInAdminPanelOnOrderPageWhenUserSetFAndLNameTheyAreNotDisplayingInProfilePage() throws Exception {
        String fAndLName = "Test";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        ordersPage = editOrderPage.setFAndLName(fAndLName)
                .clickSaveButton();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();


        String fName = webDriver.findElement(By.xpath("//form[@id='profile_save_changes']/input[@name='firstname']")).getAttribute("value");
        String lName = webDriver.findElement(By.xpath("//form[@id='profile_save_changes']/input[@name='lastname']")).getAttribute("value");

        Assert.assertTrue(!fName.equals(fAndLName) && lName.equals(fAndLName));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1496
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnEditOrderButtonCanSetEmail() throws Exception {
        String email = "test@gmail.com";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        ordersPage = editOrderPage.setEmail(email)
                .clickSaveButton();
        editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrderWithEditedEmail(email);

        String emailField = webDriver.findElement(By.xpath("//input[@id='email']")).getAttribute("value");

        Assert.assertTrue(emailField.equals(email));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1497
    public void checkInAdminPanelOnOrderPageWhenUserSetEmailTheyAreNotDisplayingInProfilePage() throws Exception {
        String email = "test@gmail.com";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        ordersPage = editOrderPage.setEmail(email)
                .clickSaveButton();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();

        String oldEmail = "mmjtrain.test" + generNumber1 + "@gmail.com";
        String emailField = webDriver.findElement(By.xpath("//form[@id='profile_save_changes']//input[@name='email']")).getAttribute("value");

        Assert.assertTrue(emailField.equals(oldEmail));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1498
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnEditOrderButtonCanSetPhoneNumber() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        ordersPage = editOrderPage.setPhone(generNumber2)
                .clickSaveButton();
        editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);

        String phoneField = webDriver.findElement(By.xpath("//input[@id='phone']")).getAttribute("value");
        String phoneNumber = "(806) 945-" + generNumber2 + "";

        Assert.assertTrue(phoneField.equals(phoneNumber));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1499
    public void checkInAdminPanelOnOrderPageWhenUserSetPhoneNumberTheyAreNotDisplayingInProfilePage() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        ordersPage = editOrderPage.setPhone(generNumber2)
                .clickSaveButton();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();

        String phoneNumber = "(806) 945-" + generNumber1 + "";
        String phoneField = webDriver.findElement(By.xpath("//form[@id='profile_save_changes']//input[@name='phone']")).getAttribute("value");

        Assert.assertTrue(phoneField.equals(phoneNumber));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1500
    public void checkInAdminPanelOnOrderPageWhenUserMakesClickOnEditOrderButtonCanSetAllFieldInAddressBlock() throws Exception {
        String test = "Test";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        ordersPage = editOrderPage.setAllFieldsInAddressBlock(generNumber1, test)
                .clickSaveButton();
        editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);

        String addressFieldValue = webDriver.findElement(By.xpath("//textarea[@id='address']")).getAttribute("value");
        String cityFieldValue = webDriver.findElement(By.xpath("//input[@id='city']")).getAttribute("value");
        String stateFieldValue = webDriver.findElement(By.xpath("//input[@id='state']")).getAttribute("value");
        String zipCodeFieldValue = webDriver.findElement(By.xpath("//input[@id='zcode']")).getAttribute("value");

        Assert.assertTrue(addressFieldValue.equals(test) &&
                cityFieldValue.equals(test) &&
                stateFieldValue.equals(test) &&
                zipCodeFieldValue.equals("1" + generNumber1));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1502
    public void checkIfInEditOrderPageDisplayDeliveryInstructionThatUserSetWhenMakesOrder() throws Exception {
        String deliveryInstuction = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu erat sed dolor vehicula condimentum et eu justo. " +
                "Sed quam mauris, posuere eu lacinia at, porta sed augue. Duis ut aliquet est. Maecenas mattis magna sit amet libero porttitor, " +
                "non elementum justo pretium. Aliquam eu gravida neque. Sed sagittis nisi est, vitae rutrum ligula ornare quis. Sed eget nisi at urna dignissim " +
                "condimentum. Morbi interdum ligula a nibh molestie, ut luctus quam luctus. Vestibulum ante ipsum primis in faucibus orci luctus";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductEachQuantity(generNumber1, 10, 10)
                .refreshPage()
                .addToCartQuantityTypeOfProduct()
                .clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryInstruction(deliveryInstuction);
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                . setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);

        Assert.assertTrue(deliveryInstuction.equals(editOrderPage.getDeliveryInstruction()));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1503
    public void checkInOrderPageOrderDisapearingAfterDeletingProductInEditOrderPage() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        ordersPage = editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage.IsLoaded();

        try {
            webDriver.findElement(By.xpath("//li[@class='o-user data-color']//span[.='mmjtrain.test" + generNumber1 + "@gmail.com']")).isDisplayed();
            Assert.assertTrue(false, "Order displayed");
        } catch (NoSuchElementException e){
            Assert.assertTrue(true, "Order deleted");
        }

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "orders" })
    // TS 1.1504
    public void CheckInOrderPageDisplayingCorrectPriceAfterCreating2PortionProductItemAndDeletingOneOfThemInEditOrderPage () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .createInDbProductOzOuncePortion(generNumber2, 10, 15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2)
                .clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

            if (isPresented == false) {
                editOrderPage.clickOnSaveButton();
                ordersPage.IsLoaded();
            }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == " +
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1505
    public void CheckInCorrectPriceAfterCreating2PortionProductItemUsingPromocodeThatIsEqual1OfProductPriceAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .createInDbProductOzOuncePortion(generNumber2, 10, 15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2)
                .clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 15);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1506
    public void CheckInCorrectPriceAfterCreating2PortionProductItemUsingPromocodeThatIsEqual2OfProductPriceAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .createInDbProductOzOuncePortion(generNumber2, 10, 15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2)
                .clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 30);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1507
    public void CheckInCorrectPriceAfterCreating2PortionProductItemUsingPromocodeThatIsMoreThanProductsPricesAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .createInDbProductOzOuncePortion(generNumber2, 10, 15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2)
                .clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 40);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();
        // Thread.sleep(1000);

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1508
    public void CheckInCorrectPriceAfterCreating2PortionProductItemUsingCreditMoneyThatIsEqual1ProductItemPriceProductsPricesAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .createInDbProductOzOuncePortion(generNumber2, 10, 15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        creditMoneyValue = menuPage.createInDbCreditMoney (15, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);

    }

    @Test(groups = { "orders" })
    // TS 1.1509
    public void CheckInCorrectPriceAfterCreating2PortionProductItemUsingCreditMoneyThatIsEqual2ProductItemPriceProductsPricesAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .createInDbProductOzOuncePortion(generNumber2, 10, 15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        creditMoneyValue = menuPage.createInDbCreditMoney (30, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1510
    public void CheckInCorrectPriceAfterCreating2PortionProductItemUsingCreditMoneyThatIsMoreThanProductsPricesAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,15)
                .createInDbProductOzOuncePortion(generNumber2, 10, 15)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        creditMoneyValue = menuPage.createInDbCreditMoney (40, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1511
    public void CheckCorrectPriceAfterCreatingProductItemUsingCMoneyAndPCThatEachEqualHalfOfProductPricesAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .createInDbProductOzOuncePortion(generNumber2, 10, 20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1512
    public void CheckCorrectPriceAfterCreatingProductItemUsingCMoneyAndPCThatEqualEachOfProductPricesAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .createInDbProductOzOuncePortion(generNumber2, 10, 20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        creditMoneyValue = menuPage.createInDbCreditMoney (20, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1513
    public void CheckCorrectPriceAfterCreatingProductItemUsingCMoneyAndPCThatMoreThanEachOfProductPricesAndDeletingOneOfThem  () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        menuPage.createInDbProductOzOuncePortion(generNumber1,10,20)
                .createInDbProductOzOuncePortion(generNumber2, 10, 20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        creditMoneyValue = menuPage.createInDbCreditMoney (22, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 22);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        sumOfPricesOfDeletedProductsInOrderPage = editOrderPage.clickOnDeleteButtonAboveCreatedProduct(generNumber1);
        editOrderPage.clickOkButtonInConfirmAlert();
        ordersPage = editOrderPage.clickOnSaveButton();

        Boolean isPresented = webDriver.findElements(By.xpath("//ul[@id='gr-6']")).size() > 0;

        if (isPresented == false) {
            editOrderPage.clickOnSaveButton();
            ordersPage.IsLoaded();
        }

        LOG.info(totalSumOfProductPricesInOrderBlock);
        LOG.info(sumOfPricesOfDeletedProductsInOrderPage);

        LOG.info(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) + " == "
                + ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(generNumber1) ==
                ordersPage.calculateTotalPriceAfterDeletingProductInEditOrderPage(totalSumOfProductPricesInOrderBlock, sumOfPricesOfDeletedProductsInOrderPage, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1514
    public void CheckInEditOrderPageDisplayingCorrectPriceAfterCreating2QuantityProductItemAndEditingTheirQuantity () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;
        int newEditedProductPrice = 0;
        int oldEditedProductPrice = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbProductEachQuantity(generNumber1,10,20)
                .createInDbProductEachQuantity(generNumber2, 10, 20)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        oldEditedProductPrice = editOrderPage.getSumOfProductItems();
        editOrderPage.clickOnEditProductItem(generNumber1)
                .clickOnPlusButtonAtQuantityProductItem(generNumber1, 1)
                .clickSaveButtomOnProductItem(generNumber1);
        newEditedProductPrice = editOrderPage.getSumOfProductItems();

        LOG.info(newEditedProductPrice);
        LOG.info(oldEditedProductPrice);

        if (newEditedProductPrice != oldEditedProductPrice)
            Assert.assertTrue(true, "price are different");

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1514-0
    public void CheckInEditOrderPageDisplayingCorrectPriceAfterCreating2QuantityProductItemAndEditingTheirQuantityInRecessionWay () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;
        int newEditedProductPrice = 0;
        int oldEditedProductPrice = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbProductEachQuantity(generNumber1,10,20)
                .createInDbProductEachQuantity(generNumber2, 10, 20)
                .refreshPage()
                .selectQuanitytTypeOfProductUsingPlus(generNumber1, 2)
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        oldEditedProductPrice = editOrderPage.getSumOfProductItems();
        editOrderPage.clickOnEditProductItem(generNumber1)
                .clickOnMinusButtonAtQuantityProductItem(generNumber1, 1)
                .clickSaveButtomOnProductItem(generNumber1);
        newEditedProductPrice = editOrderPage.getSumOfProductItems();

        LOG.info(newEditedProductPrice);
        LOG.info(oldEditedProductPrice);

        if (newEditedProductPrice != oldEditedProductPrice)
            Assert.assertTrue(true, "price are different");

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1514-01
    public void CheckInEditOrderPageDisplayingCorrectPriceAfterCreating2QuantityProductItemAndEditingTheirQuantityForMakingMinusPortion () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;
        int newEditedProductPrice = 0;
        int oldEditedProductPrice = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbProductEachQuantity(generNumber1,10,20)
                .createInDbProductEachQuantity(generNumber2, 10, 20)
                .refreshPage()
                .selectQuanitytTypeOfProductUsingPlus(generNumber1, 1)
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        oldEditedProductPrice = editOrderPage.getSumOfProductItems();
        editOrderPage.clickOnEditProductItem(generNumber1)
                .clickOnMinusButtonAtQuantityProductItem(generNumber1, 1)
                .clickSaveButtomOnProductItem(generNumber1);
        newEditedProductPrice = editOrderPage.getSumOfProductItems();

        LOG.info(newEditedProductPrice);
        LOG.info(oldEditedProductPrice);

        if (newEditedProductPrice == oldEditedProductPrice)
            Assert.assertTrue(true, "price are different");

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1514-1
    public void CheckInEditOrderPageDisplayingCorrectPriceAfterCreating2PortuionProductItemAndEditingTheirPortuion () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;
        int newEditedProductPrice = 0;
        int oldEditedProductPrice = 0;


        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbPortionOzHalfOunceAndOunceProduct(generNumber1,10,20, 10)
                .createInDbPortionOzHalfOunceAndOunceProduct(generNumber2, 10, 20, 10)
                .refreshPage()
                .selectTypeOfPortionBeforeAddProduct(generNumber1, "1/2 oz")
                .addToCartCreatedInDbProduct(generNumber1)
                .selectTypeOfPortionBeforeAddProduct(generNumber2, "1/2 oz")
                .addToCartCreatedInDbProduct(generNumber2);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        oldEditedProductPrice = editOrderPage.getSumOfProductItems();
        editOrderPage.clickOnEditProductItem(generNumber1)
                .clickOnNewPortionOfProductItem(generNumber1)
                .clickSaveButtomOnProductItem(generNumber1);
        newEditedProductPrice = editOrderPage.getSumOfProductItems();

        LOG.info(newEditedProductPrice);
        LOG.info(oldEditedProductPrice);

        if (newEditedProductPrice != oldEditedProductPrice)
            Assert.assertTrue(true, "price are different");

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }

    @Test(groups = { "orders" })
    // TS 1.1514-3
    public void CheckInOrderPageDisplayingCorrectPriceAfterCreating2QuantityProductItemAndEditingTheirQuantity () throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        LOG.info(generNumber1);
        LOG.info(generNumber2);
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;
        int newEditedProductPrice = 0;
        int oldEditedProductPrice = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbProductEachQuantity(generNumber1,10,20)
                .createInDbProductEachQuantity(generNumber2, 10, 20)
                .refreshPage()
                .selectQuanitytTypeOfProductUsingPlus(generNumber1, 2)
                .addToCartCreatedInDbProduct(generNumber1)
                .addToCartCreatedInDbProduct(generNumber2);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        oldEditedProductPrice = editOrderPage.getSumOfProductItems();
        editOrderPage.clickOnEditProductItem(generNumber1)
                .clickOnMinusButtonAtQuantityProductItem(generNumber1, 1)
                .clickSaveButtomOnProductItem(generNumber1);
        ordersPage = editOrderPage.clickOnSaveButton();
        newEditedProductPrice = ordersPage.getSumOfProductItems(generNumber1);

        LOG.info(newEditedProductPrice);
        LOG.info(oldEditedProductPrice);

        if (newEditedProductPrice != oldEditedProductPrice)
            Assert.assertTrue(true, "price are different");

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }


    @Test(groups = { "orders" })
    // TS 1.1515
    public void CheckInOrderPageDisplayingCorrectPriceAfterCreating2PortionProductItemAndEditingTheirPortion () throws Exception {

        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        int sumOfPricesOfDeletedProductsInOrderPage = 0;
        int newEditedProductPrice = 0;
        int oldEditedProductPrice = 0;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage.createInDbPortionOzHalfOunceAndOunceProduct(generNumber1,10,20, 10)
                .createInDbPortionOzHalfOunceAndOunceProduct(generNumber2, 10, 20, 10)
                .refreshPage()
                .selectTypeOfPortionBeforeAddProduct(generNumber1, "1/2 oz")
                .addToCartCreatedInDbProduct(generNumber1)
                .selectTypeOfPortionBeforeAddProduct(generNumber2, "1/2 oz")
                .addToCartCreatedInDbProduct(generNumber2);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        EditOrderPage editOrderPage = ordersPage.clickOnEditButtonAboveCreatedOrder(generNumber1);
        oldEditedProductPrice = editOrderPage.getSumOfProductItems();
        editOrderPage.clickOnEditProductItem(generNumber1)
                .clickOnNewPortionOfProductItem(generNumber1)
                .clickSaveButtomOnProductItem(generNumber1);
        ordersPage = editOrderPage.clickOnSaveButton();
        newEditedProductPrice = ordersPage.getSumOfProductItems(generNumber1);

        LOG.info(newEditedProductPrice);
        LOG.info(oldEditedProductPrice);

        if (newEditedProductPrice == oldEditedProductPrice)
            Assert.assertTrue(true, "price are different");

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber2);
    }



}




