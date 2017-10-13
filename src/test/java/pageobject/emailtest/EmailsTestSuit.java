package pageobject.emailtest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.adminpanel.commonWidgets.NavigationPanel;
import pageObject.pages.adminpanel.pages.ClientsPage;
import pageObject.pages.adminpanel.pages.EditClientPage;
import pageObject.pages.adminpanel.pages.OrdersPage;
import pageObject.pages.mmjtraine.*;
import pageObject.pages.pop_ups.*;
import pageObject.utility.DateTime;
import pageObject.utility.RandomNumberGenerator;
import pageObject.utility.emails.letters.*;
import pageobject.testcase.TestBase;

import static pageObject.pages.Page.compareList;

/**
 * Created by NGorodenchuk on 7/17/2017.
 */
public class EmailsTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(EmailsTestSuit.class);

    @Test(groups = { "emails" })
    // TS 1.971-143
    public void checkCorrectTotalPriceOrderInfUserNameOrderNumberAndSubjectInShippedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,100)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.getOrderInformation(generNumber1)
                .clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnApprovedButtonInOrderHintWindow(generNumber1)
                .clickOnNotYetShippedButton(generNumber1)
                .refreshPage();
        userName = ordersPage.getUserName(generNumber1);
        orderId = ordersPage.getOrderId(generNumber1);

        YourMmjTrainOrderHasNowBeenShipped letter = new YourMmjTrainOrderHasNowBeenShipped("Your MMJ Train order " + orderId + " has now been shipped!");
        letter.fetch(letter);
        letter.setOrderedProductName();
        letter.setOrderedProductQuantity();
        letter.setOrderedProductPrice();

        Assert.assertTrue(compareList(ordersPage.productsListOfItemName, letter.productsListOfItemName) &&
                        compareList(ordersPage.productsListOfItemPrice, letter.productsListOfItemPrice) &&
                        compareList(ordersPage.productsListOfItemQuantity, letter.productsListOfItemQuantity),
                "Name, price and quantity of products in order is missed");

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(emptyText) == Integer.parseInt(letter.getTotalPrice()),
                "Total price of order is missed");

        Assert.assertTrue(orderId.equals(letter.getOrderNumber()),
                "Order number in letter is missed");

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.971-144
    public void checkCorrectUserNameSettedInEditClientPageInShippedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,100)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        EditClientPage editClientPage = navigationPanel.clickOnClientsNavMenuItem()
                .clickOnClientStatusButton(emptyText);
        ClientsPage clientsPage = editClientPage.setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        userName = clientsPage.getUserName(emptyText);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnApprovedButtonInOrderHintWindow(generNumber1)
                .clickOnNotYetShippedButton(generNumber1)
                .refreshPage();
        orderId = ordersPage.getOrderId(generNumber1);

        YourMmjTrainOrderHasNowBeenShipped letter = new YourMmjTrainOrderHasNowBeenShipped("Your MMJ Train order " + orderId + " has now been shipped!");
        letter.fetch(letter);
        letter.setOrderedProductName();
        letter.setOrderedProductQuantity();
        letter.setOrderedProductPrice();

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.971-145
    public void checkCorrectRedirectionOfTrackYourPackagebuttonInShippedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,100)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.getOrderInformation(generNumber1)
                .clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnApprovedButtonInOrderHintWindow(generNumber1)
                .clickOnNotYetShippedButton(generNumber1)
                .refreshPage();
        userName = ordersPage.getUserName(generNumber1);
        orderId = ordersPage.getOrderId(generNumber1);

        YourMmjTrainOrderHasNowBeenShipped letter = new YourMmjTrainOrderHasNowBeenShipped("Your MMJ Train order " + orderId + " has now been shipped!");
        letter.fetch(letter);
        navigateTo(letter.getTrackYourPackageLink());

        Assert.assertTrue(webDriver.getTitle().equals("USPS.com® - USPS Tracking®"),
                "USPS tracking page doesn`t loaded or link doesnt work correct");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1813
    public void checkUsernameOrderNumberAndTotalPriceInProceedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.getOrderInformation(generNumber1)
                .clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnApprovedButtonInOrderHintWindow(generNumber1)
                .clickOnNotYetShippedButton(generNumber1)
                .clickOnNotPaidButton(generNumber1)
                .refreshPage();
        userName = ordersPage.getUserName(generNumber1);
        orderId = ordersPage.getOrderId(generNumber1);

        MmjTrainOrderHasBeenProcessed letter = new MmjTrainOrderHasBeenProcessed("MMJ Train order " + orderId +" has been processed");
        letter.fetch(letter);

        Assert.assertTrue(ordersPage.getTotalPriceAferUsingDiscounts(emptyText) == Integer.parseInt(letter.getTotalPrice()),
                "Total price of order is missed");

        Assert.assertTrue(orderId.equals(letter.getOrderNumber()),
                "Order number in letter is missed");

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1814
    public void checkCorrectUserNameSettedInEditClientPageInProceedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        EditClientPage editClientPage = navigationPanel.clickOnClientsNavMenuItem()
                .clickOnClientStatusButton(emptyText);
        ClientsPage clientsPage = editClientPage.setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        userName = clientsPage.getUserName(emptyText);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.getOrderInformation(generNumber1)
                .clickOnPendingButton(generNumber1)
                .setAttributeInHintWindowElement(generNumber1)
                .clickOnApprovedButtonInOrderHintWindow(generNumber1)
                .clickOnNotYetShippedButton(generNumber1)
                .clickOnNotPaidButton(generNumber1)
                .refreshPage();
        orderId = ordersPage.getOrderId(generNumber1);

        MmjTrainOrderHasBeenProcessed letter = new MmjTrainOrderHasBeenProcessed("MMJ Train order " + orderId +" has been processed");
        letter.fetch(letter);

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

//    server cause problem 403 error on subdomain
    @Test(enabled = true, groups = { "emails" })
    // TS 1.1781
    public void checkUsernameOrderNumberDeliveryAdressDeliveryMethodSiqnatureProductInformationAndTotalPriceInCancelLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        DateTime dt = new DateTime();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        orderMakingTime = dt.getTimeInLA();
        HistoryPage historyPage = profilePage.clickOnOrderHistoryButton();
        historyPage.clickCreatedOrderItem(orderMakingTime);
        CancleOrderPopUp cancleOrderPopUp = historyPage.clickonCancelButtonOnCreatedOrderItem(orderMakingTime);
        ProfilePage profilePage1 = cancleOrderPopUp.clickYesButton();

        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        ordersPage.getOrderInformation(generNumber1);
        userName = ordersPage.getUserName(generNumber1);
        orderId = ordersPage.getOrderId(generNumber1);
        orderTotalPrice = ordersPage.getTotalPriceAferUsingDiscounts(generNumber1);

        YourOrderHasBeenCanceled letter = new YourOrderHasBeenCanceled("Your order " + orderId +" has been canceled");
        letter.fetch(letter);
        letter.setOrderedProductName();
        letter.setOrderedProductPrice();
        letter.setOrderedProductQuantity();

        Assert.assertTrue(compareList(ordersPage.productsListOfItemName, letter.productsListOfItemName) &&
                        compareList(ordersPage.productsListOfItemPrice, letter.productsListOfItemPrice) &&
                        compareList(ordersPage.productsListOfItemQuantity, letter.productsListOfItemQuantity),
                "Name, price and quantity of products in order is missed");

        Assert.assertTrue(orderTotalPrice == Integer.parseInt(letter.getTotalPrice()),
                "Total price of order is missed");

        Assert.assertTrue(orderId.equals(letter.getOrderNumber()),
                "Order number in letter is missed");

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        Assert.assertTrue("No".equals(letter.getSignature()),
                "User signature set 'Yes'");

        Assert.assertTrue(ordersPage.getOrderAddress(emptyText).equals(letter.getDeliveryAddress()),
                "Delivery address is incorrect");

        Assert.assertTrue("Orders are shipped via USPS.0.00$ OVERNIGHT SHIPPING (1 business day)."
                        .equals(letter.getDeliverMethod()),
                "Delivery method is incorrect");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    //    server cause problem 403 error on subdomain
    @Test(groups = { "emails" })
    // TS 1.1778
    public void checkUserNameInsteadFullUsernameDeliveryInstructionAndSignatureEmailAddressInCancelLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        DateTime dateTime = new DateTime();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String fullUserName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;
        String deliveryInstuction = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu erat sed dolor vehicula condimentum et eu justo. " +
                "Sed quam mauris, posuere eu lacinia at, porta sed augue. Duis ut aliquet est. Maecenas mattis magna sit amet libero porttitor, " +
                "non elementum justo pretium. Aliquam eu gravida neque. Sed sagittis nisi est, vitae rutrum ligula ornare quis. Sed eget nisi at urna dignissim " +
                "condimentum. Morbi interdum ligula a nibh molestie, ut luctus quam luctus. Vestibulum ante ipsum primis in faucibus orci luctus";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setDeliveryInstruction(deliveryInstuction)
                .activateCheckBox()
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        orderMakingTime = dateTime.getTimeInLA();
        // Thread.sleep(2000);
        CreateNewTab();
        //        wait
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        //        wait
        // Thread.sleep(2000);
        EditClientPage editClientPage = navigationPanel.clickOnClientsNavMenuItem()
                .clickOnClientStatusButton(emptyText);
        ClientsPage clientsPage = editClientPage.setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        userName = clientsPage.getUserName(emptyText);
        //        wait
        // Thread.sleep(3000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        //        wait
        // Thread.sleep(3000);
        fullUserName = ordersPage.getFullUserName(generNumber1);
        orderId = ordersPage.getOrderId(generNumber1);
        //        wait
        // Thread.sleep(2000);
        SwitchFromSecondTabToFirst();
        //        wait
        // Thread.sleep(2000);
        HistoryPage historyPage = profilePage.clickOnOrderHistoryButton();
        historyPage.clickCreatedOrderItem(orderMakingTime);
        CancleOrderPopUp cancleOrderPopUp = historyPage.clickonCancelButtonOnCreatedOrderItem(orderMakingTime);
        ProfilePage profilePage1 = cancleOrderPopUp.clickYesButton();
        // Thread.sleep(2000);

        YourOrderHasBeenCanceled letter = new YourOrderHasBeenCanceled("Your order " + orderId +" has been canceled");
        letter.fetch(letter);

        LOG.info(letter.getFullName());
        LOG.info(letter.getDeliverInstruction());

        Assert.assertTrue("Yes".equals(letter.getSignature()),
                "User signature set 'No'");

        Assert.assertTrue(deliveryInstuction.equals(letter.getDeliverInstruction()),
                "Delivery instruction in letter is missed or incorrect");

        Assert.assertTrue(fullUserName.equals(letter.getFullName()),
                "Full name is missed or incorrect");

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1664
    public void checkUsernameInRecommendationDeclinedLetterWhenUserChangeFromApproved() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();

        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        // Thread.sleep(2000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        // Thread.sleep(3000);
        userName = ordersPage.getUserName(generNumber1);
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        editClientPage.clickOnMedicalRecommendationStatus();

        TheStatusOfYourRecommendationIsDeclined letter = new TheStatusOfYourRecommendationIsDeclined("The status of your Recommendation is DECLINED!");
        letter.fetch(letter);
        letter.getUserName();

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1665
    public void checkRecievingRecommendationDeclinedLetterAfterChangeingFromPenndingToDelcined() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();

        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        // Thread.sleep(2000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        // Thread.sleep(3000);
        userName = ordersPage.getUserName(generNumber1);
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        editClientPage.clickOnMedicalRecommendationStatus();

        TheStatusOfYourRecommendationIsDeclined letter = new TheStatusOfYourRecommendationIsDeclined("The status of your Recommendation is DECLINED!");
        letter.fetch(letter);
        letter.getUserName();

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1668
    public void checkDisplayingFullUserNameInRecommendationDeclinedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,100)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        //        wait
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        //        wait
        // Thread.sleep(2000);
        EditClientPage editClientPage = navigationPanel.clickOnClientsNavMenuItem()
                .clickOnClientStatusButton(emptyText);
        ClientsPage clientsPage = editClientPage.setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        userName = clientsPage.getUserName(emptyText);
        //        wait
        // Thread.sleep(3000);
        editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        clientsPage = editClientPage.clickOnMedicalRecommendationStatus()
                .clickSaveBuuton();

        TheStatusOfYourRecommendationIsDeclined letter = new TheStatusOfYourRecommendationIsDeclined("The status of your Recommendation is DECLINED!");
        letter.fetch(letter);

        LOG.info(letter.getUserName());

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed or incorrect");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1672
    public void checkRedirectionAfterUsingVisitMyAccountLinkFromRecommendationDeclinedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,100)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        //        wait
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        //        wait
        // Thread.sleep(2000);
        EditClientPage editClientPage = navigationPanel.clickOnClientsNavMenuItem()
                .clickOnClientStatusButton(emptyText);
        ClientsPage clientsPage = editClientPage.setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        userName = clientsPage.getUserName(emptyText);
        //        wait
        // Thread.sleep(3000);
        editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        clientsPage = editClientPage.clickOnMedicalRecommendationStatus()
                .clickSaveBuuton();

        TheStatusOfYourRecommendationIsDeclined letter = new TheStatusOfYourRecommendationIsDeclined("The status of your Recommendation is DECLINED!");
        letter.fetch(letter);
        navigateTo(letter.getVisitMyAccountLink());

        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-geo']")).isDisplayed(),
                "Home Page doesn`t loaded or link doesnt work correct");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1622
    public void checkFullUsernameInIdentificationDeclinedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();

        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        // Thread.sleep(2000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        // Thread.sleep(3000);
        userName = ordersPage.getUserName(generNumber1);
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        editClientPage.clickOnPersonalRecommendationStatus();

        TheStatusOfYourIdIsDeclined letter = new TheStatusOfYourIdIsDeclined("The status of your ID is DECLINED!");
        letter.fetch(letter);
        letter.getUserName();

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1626
    public void checkUsernameInIdentificationDeclinedLetterWhenUserChangeFromApproved() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();

        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        //        wait
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        //        wait
        // Thread.sleep(2000);
//        userName = ordersPage.getUserName(generNumber1);
        EditClientPage editClientPage = navigationPanel.clickOnClientsNavMenuItem()
                .clickOnClientStatusButton(emptyText);
        ClientsPage clientsPage = editClientPage.setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        userName = clientsPage.getUserName(emptyText);
        //        wait
        // Thread.sleep(3000);
        editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        clientsPage = editClientPage.clickOnPersonalRecommendationStatus()
                .clickSaveBuuton();
        TheStatusOfYourIdIsDeclined letter = new TheStatusOfYourIdIsDeclined("The status of your ID is DECLINED!");
        letter.fetch(letter);
        letter.getUserName();

        LOG.info(userName);

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1623
    public void checkRecievingIdentificationDeclinedLetterAfterChangeingFromPenndingToDelcined() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();

        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        // Thread.sleep(2000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        // Thread.sleep(3000);
        userName = ordersPage.getUserName(generNumber1);
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        editClientPage.clickOnPersonalRecommendationStatus();

        TheStatusOfYourIdIsDeclined letter = new TheStatusOfYourIdIsDeclined("The status of your ID is DECLINED!");
        letter.fetch(letter);
        letter.getUserName();

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1630
    public void checkRedirectionAfterUsingVisitMyAccountLinKFromIdDeclinedLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();

        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        // Thread.sleep(2000);
        CreateNewTab();
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        // Thread.sleep(2000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        // Thread.sleep(3000);
        userName = ordersPage.getUserName(generNumber1);
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(emptyText);
        editClientPage.clickOnPersonalRecommendationStatus();

        TheStatusOfYourIdIsDeclined letter = new TheStatusOfYourIdIsDeclined("The status of your ID is DECLINED!");
        letter.fetch(letter);
        navigateTo(letter.getVisitMyAccountLink());

        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-geo']")).isDisplayed(),
                "Home Page doesn`t loaded or link doesnt work correct");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1595
    public void checkUsernameOrderNumberDeliveryAdressDeliveryMethodSiqnatureProductInformationAndTotalPriceInPlacingOrderLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        DateTime dt = new DateTime();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();
        menuPage.IsLoaded();
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
//        Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
//        Thread.sleep(2000);
        CreateNewTab();
//        Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
//        Thread.sleep(2000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        // Thread.sleep(3000);
        ordersPage.getOrderInformation(generNumber1);
        ordersPage.clickOnNotPaidButton(generNumber1);

        userName = ordersPage.getUserName(generNumber1);
        orderId = ordersPage.getOrderId(generNumber1);
        orderTotalPrice = ordersPage.getTotalPriceAferUsingDiscounts(emptyText);

        ThankYouForPlacingYourOrderWithMmjTrain letter = new ThankYouForPlacingYourOrderWithMmjTrain("Thank you for placing your order " + orderId + " with MMJ Train");
        letter.fetch(letter);
        letter.setOrderedProductName();
        letter.setOrderedProductPrice();
        letter.setOrderedProductQuantity();

        Assert.assertTrue(compareList(ordersPage.productsListOfItemName, letter.productsListOfItemName) &&
                        compareList(ordersPage.productsListOfItemPrice, letter.productsListOfItemPrice) &&
                        compareList(ordersPage.productsListOfItemQuantity, letter.productsListOfItemQuantity),
                "Name, price and quantity of products in order is missed");

        Assert.assertTrue(orderTotalPrice == Integer.parseInt(letter.getTotalPrice()),
                "Total price of order is missed");

        Assert.assertTrue(orderId.equals(letter.getOrderNumber()),
                "Order number in letter is missed");

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        Assert.assertTrue("No".equals(letter.getSignature()),
                "User signature set 'Yes'");

        Assert.assertTrue(ordersPage.getOrderAddress(emptyText).equals(letter.getDeliveryAddress()),
                "Delivery address is incorrect");

        Assert.assertTrue("Orders are shipped via USPS.0.00$ OVERNIGHT SHIPPING (1 business day)."
                        .equals(letter.getDeliverMethod()),
                "Delivery method is incorrect");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1600
    public void checkUserNameInsteadFullUsernameDeliveryInstructionInPlacingOrderLetter() throws Exception {
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        DateTime dateTime = new DateTime();

        int totalSumOfProductPricesInOrderBlock = 0;
        int promoCodeValue = 0;
        int creditMoneyValue = 0;
        String emptyText = "";
        String userName;
        String fullUserName;
        String orderId;
        String orderMakingTime;
        int orderTotalPrice;
        String deliveryInstuction = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu erat sed dolor vehicula condimentum et eu justo. " +
                "Sed quam mauris, posuere eu lacinia at, porta sed augue. Duis ut aliquet est. Maecenas mattis magna sit amet libero porttitor, " +
                "non elementum justo pretium. Aliquam eu gravida neque. Sed sagittis nisi est, vitae rutrum ligula ornare quis. Sed eget nisi at urna dignissim " +
                "condimentum. Morbi interdum ligula a nibh molestie, ut luctus quam luctus. Vestibulum ante ipsum primis in faucibus orci luctus";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().approveRecommendationInDb(emptyText)
                .updateRec_ExpiredToValidDate(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();

        CreateNewTab();
        //        wait
        // Thread.sleep(2000);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        //        wait
        // Thread.sleep(2000);
        EditClientPage editClientPage = navigationPanel.clickOnClientsNavMenuItem()
                .clickOnClientStatusButton(emptyText);
        ClientsPage clientsPage = editClientPage.setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        userName = clientsPage.getUserName(emptyText);

        SwitchFromSecondTabToFirst();
        //        wait
        // Thread.sleep(2000);
        menuPage = menuPage.createInDbProductOzOuncePortion(generNumber1,10,50)
                .refreshPage()
                .addToCartCreatedInDbProduct(generNumber1);
        creditMoneyValue = menuPage.createInDbCreditMoney (25, emptyText);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setDeliveryInstruction(deliveryInstuction)
                .activateCheckBox()
                .setPaymentDetails();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        // Thread.sleep(500);
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        orderMakingTime = dateTime.getTimeInLA();
        // Thread.sleep(2000);
       SwitchFromFirstPageToSecond();
        //        wait
        // Thread.sleep(3000);
        OrdersPage ordersPage = navigationPanel.clickOnordersNavMenuItem();
        //        wait
        // Thread.sleep(3000);
        fullUserName = ordersPage.getFullUserName(generNumber1);
        orderId = ordersPage.getOrderId(generNumber1);
        ordersPage.clickOnNotPaidButton(generNumber1);

        ThankYouForPlacingYourOrderWithMmjTrain letter = new ThankYouForPlacingYourOrderWithMmjTrain("Thank you for placing your order " + orderId + " with MMJ Train");
        letter.fetch(letter);

        LOG.info(letter.getFullName());
        LOG.info(letter.getDeliverInstruction());

        Assert.assertTrue("Yes".equals(letter.getSignature()),
                "User signature set 'No'");

        Assert.assertTrue(deliveryInstuction.equals(letter.getDeliverInstruction()),
                "Delivery instruction in letter is missed or incorrect");

        Assert.assertTrue(fullUserName.equals(letter.getFullName()),
                "Full name is missed or incorrect");

        Assert.assertTrue(userName.equals(letter.getUserName()),
                "User name in letter is missed");

        menuPage.deleteInDbProductPromocode(generNumber1);
        menuPage.userAndOrder(emptyText);
    }


    @Test(groups = { "emails" })
    // TS 1.1575
    public void checkDisplayingRessetPAsswordButtonRessetPasswordLinkInRessetPasswordLetter() throws Exception {
        String emptyText = "";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .approveRecommendationInDb(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
        homePage = profilePage.clickOnLogOutButton();
        signInPopUp = homePage.clickOnSignInButton();
        ResetYourPasswordPopUp resetYourPasswordPopUp = signInPopUp.clickOnForgotYourPasswordButton();
        resetYourPasswordPopUp.resetPassword("mmjtrain.test@gmail.com");

        MmmjTrainPasswordReset letter = new MmmjTrainPasswordReset("MMJ Train Password Reset");
        letter.fetch(letter);

        Assert.assertTrue(!letter.getResetPasswordButtonLink().equals(null),
                "Reset Password Button Link doesn't exist");

        Assert.assertTrue(!letter.getResetPasswordLink().equals(null),
                "Reset Password Link doesn't exist");

        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1575
    public void checkLinkRedirectionInResetPasswordButtonToCreateNewPasswordPageInRessetPasswordLetter() throws Exception {
        String emptyText = "";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .approveRecommendationInDb(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
        homePage = profilePage.clickOnLogOutButton();
        signInPopUp = homePage.clickOnSignInButton();
        ResetYourPasswordPopUp resetYourPasswordPopUp = signInPopUp.clickOnForgotYourPasswordButton();
        resetYourPasswordPopUp.resetPassword("mmjtrain.test@gmail.com");

        MmmjTrainPasswordReset letter = new MmmjTrainPasswordReset("MMJ Train Password Reset");
        letter.fetch(letter);
        navigateTo(letter.getResetPasswordButtonLink());

        Assert.assertTrue(webDriver.findElement(By.xpath("//input[@id='ForgotpasswordNewPassword']")).isDisplayed(),
                "Create New Password Page doesn`t loaded or link doesnt work correct");

        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1582
    public void checkLinkRedirectionToCreateNewPasswordPageInRessetPasswordLetter() throws Exception {
        String emptyText = "";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(emptyText);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile()
                .approveIdentificationInDb(emptyText)
                .navigateToPhotoIdPage();
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3()
                .approveRecommendationInDb(emptyText)
                .navigateToPhotoRecPage();
        menuPage.IsLoaded();
        ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
        homePage = profilePage.clickOnLogOutButton();
        signInPopUp = homePage.clickOnSignInButton();
        ResetYourPasswordPopUp resetYourPasswordPopUp = signInPopUp.clickOnForgotYourPasswordButton();
        resetYourPasswordPopUp.resetPassword("mmjtrain.test@gmail.com");

        MmmjTrainPasswordReset letter = new MmmjTrainPasswordReset("MMJ Train Password Reset");
        letter.fetch(letter);
        navigateTo(letter.getResetPasswordLink());

        Assert.assertTrue(webDriver.findElement(By.xpath("//input[@id='ForgotpasswordNewPassword']")).isDisplayed(),
                "Create New Password Page doesn`t loaded or link doesnt work correct");

        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1550
    public void checkDisplayingUserNameAndStartShoppingButtonInWelcomeLetter() throws Exception {
        String emptyText = "";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);

        WelcomeToMmjTrain letter = new WelcomeToMmjTrain("Welcome to MMJ Train");
        letter.fetch(letter);

        Assert.assertTrue(letter.getUserName().equals("mmjtrain.test@gmail.com"),
                "User name doesn't exist");

        Assert.assertTrue(!letter.getStartShoppingButtonLink().equals(null),
                "Start Shopping Button Link doesn't exist");

        MenuPage menuPage = new MenuPage(webDriver);
        menuPage.userAndOrder(emptyText);
    }

    @Test(groups = { "emails" })
    // TS 1.1557
    public void checkLinkRedirectionOnStartShoppingButtonWelcomeLetter() throws Exception {
        String emptyText = "";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(emptyText);

        WelcomeToMmjTrain letter = new WelcomeToMmjTrain("Welcome to MMJ Train");
        letter.fetch(letter);
        navigateTo(letter.getStartShoppingButtonLink());

        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-geo']")).isDisplayed(),
                "Home Page doesn`t loaded or link doesnt work correct");

        MenuPage menuPage = new MenuPage(webDriver);
        menuPage.userAndOrder(emptyText);
    }
}


