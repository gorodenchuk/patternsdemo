package pageobject.desktoptest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.mmjtraine.CheckOutPage;
import pageObject.pages.mmjtraine.MenuPage;
import pageObject.pages.pop_ups.*;
import pageObject.utility.DB.DbTables.Users;
import pageObject.utility.RandomNumberGenerator;
import pageobject.testcase.TestBase;

/**
 * Created by NGorodenchuk on 7/17/2017.
 */
public class MakingOrderTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(MakingOrderTestSuit.class);

    @Test(groups = { "makingOrder" })
    // TS 1.971-1
    public void checkInCheckOutPageCalculatingDiscountForOneAddedProductItem() throws Exception {
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

        Assert.assertTrue(checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }



    @Test(groups = { "makingOrder" })
    // TS 1.971-2
    public void checkInCheckOutPageCalculatingDiscountWhenUserUsePromoCodeForOneAddedProductItem() throws Exception {
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
        checkOutPage.useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }



    @Test(groups = { "makingOrder" })
    // TS 1.971-3
    public void checkInCheckOutPageCalculatingDiscountWhenUserUsePromoCodeThatAreGreaterThanProductPrice() throws Exception {
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
        checkOutPage.useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }



    @Test(groups = { "makingOrder" })
    // TS 1.971-4
    public void checkOnCheckOutPageCalculatingDiscountWhenUserUseCreditMooneyThatAreEqualProductPrice() throws Exception {
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
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-5
    public void checkOnCheckOutPageCalculatingDiscountWhenUserUseLessCreditMoneyThanProductPrice() throws Exception {
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
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-6
    public void checkOnCheckOutPageCalculatingDiscountWhenUserUseMoreCreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney();

       Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-7
    public void checkOnCheckOutPageCalculatingDiscountWhenUserUse5CreditMoney5PromocodeThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1).useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-8
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse5PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

       Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-9
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse25PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-10
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse10PromocodeAnd10CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-11
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse20PromocodeAnd20CreditMoney() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-12
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse5PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-13
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse25PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-14
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse5CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-15
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse5CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-16
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse10CreditMoneyAnd10Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-17
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse20CreditMoneyAnd20Promocode() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-18
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse25CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-19
    public void checkOnCheckOutPageCalculatingProductPriceWhenUserUse25CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-20
    public void checkOnConfirmYourOrderPopUpCalculatingDiscountForOneAddedProductItem() throws Exception {
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


        Assert.assertTrue(confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-21
    public void checkOnConfirmYourOrderPopUpCalculatingDiscountWhenUserUsePromoCodeForOneAddedProductItem() throws Exception {
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

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-22
    public void checkOnConfirmYpurOrderPopUpCalculatingDiscountWhenUserUsePromoCodeThatAreGreaterThanProductPrice() throws Exception {
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

         Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-23
    public void checkOnConfirmYourOrderPageCalculatingDiscountWhenUserUseCreditMooneyThatAreEqualProductPrice() throws Exception {
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

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-24
    public void checkOnConfirmYourOrderPageCalculatingDiscountWhenUserUseLessCreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney(15, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-25
    public void checkOnConfirmYourOrderPageCalculatingDiscountWhenUserUseMoreCreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-26
    public void checkOnConfirmYourOrderPageCalculatingDiscountWhenUserUse5CreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-27
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse5PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-28
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse25PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-29
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse10PromocodeAnd10CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-30
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse20PromocodeAnd20CreditMoney() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-31
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse5PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-32
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse25PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-33
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse5CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-34
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse5CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-35
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse10CreditMoneyAnd10Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-36
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse20CreditMoneyAnd20Promocode() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-37
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse25CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-38
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenUserUse25CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-58-1
    public void checkOnCheckOutPageCalculatingDiscountForOneAddedProductItemBySubuser() throws Exception {
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
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-58-2
    public void checkOnCheckOutPageCalculatingDiscountWhenSubuserUsePromoCodeForOneAddedProductItem() throws Exception {
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
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-58-3
    public void CheckOnCheckOutPageCalculatingDiscountWhenSubuserUsePromoCodeThatAreGreaterThanProductPrice() throws Exception {
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
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-58
    public void checkOnCheckOutPageCalculatingDiscountWhenSubuserUseCreditMooneyThatAreEqualProductPrice() throws Exception {
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
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-59
    public void checkOnCheckOutPageCalculatingDiscountWhenSubuserUseLessCreditMoneyThanProductPrice() throws Exception {
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
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-60
    public void checkOnCheckOutPageCalculatingDiscountWhenSubuserUseMoreCreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-61
    public void checkOnCheckOutPageCalculatingDiscountWhenSubuserUse5CreditMoney5PromocodeThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1).useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-62
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse5PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

         Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-63
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse25PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-64
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse10PromocodeAnd10CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

       Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-65
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse20PromocodeAnd20CreditMoney() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

       Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-66
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse5PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

       Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-67
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse25PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-68
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse5CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-69
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse5CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-70
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse10CreditMoneyAnd10Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-71
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse20CreditMoneyAnd20Promocode() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-72
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse25CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-73
    public void checkOnCheckOutPageCalculatingProductPriceWhenSubuserUse25CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1);

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-74
    public void checkOnConfirmPopUpCalculatingDiscountWhenSubuserCreditMooneyThatAreEqualProductPrice() throws Exception {
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

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-75
    public void checkOnConfirmPopUpCalculatingDiscountWhenSubuserUseLessCreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney(15, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-76
    public void checkOnConfirmPopUpPageCalculatingDiscountWhenSubuserUseMoreCreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-77
    public void checkOnConfirmPopUpPageCalculatingDiscountWhenSubuserUse5CreditMoneyThanProductPrice() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(confirmYourOrderPopUp.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && confirmYourOrderPopUp.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && confirmYourOrderPopUp.getYouSavePrice() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-78
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse5PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-79
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse25PromocodeAnd5CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-80
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse10PromocodeAnd10CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-81
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse20PromocodeAnd20CreditMoney() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-82
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse5PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-83
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse25PromocodeAnd25CreditMoney() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreatedPromoCode(generNumber1)
                .useCreditMoney()
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-84
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse5CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-85
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse5CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (5, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-86
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse10CreditMoneyAnd10Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (10, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 10);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-87
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse20CreditMoneyAnd20Promocode() throws Exception {
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
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 20);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-88
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse25CreditMoneyAnd5Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 5);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

    @Test(groups = { "makingOrder" })
    // TS 1.971-89
    public void checkOnConfirmYourOrderPopUpCalculatingProductPriceWhenSubuserUse25CreditMoneyAnd25Promocode() throws Exception {
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
        creditMoneyValue = menuPage.createInDbCreditMoney (25, generNumber1);
        CheckOutPage checkOutPage = menuPage.clickOnOrangeCheckOutButton();
        promoCodeValue = checkOutPage.createPromoCodeInDb(generNumber1, 25);
        checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails();
        totalSumOfProductPricesInOrderBlock = checkOutPage.getTotalPriceBeforeUsingDiscounts();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.useCreditMoney()
                .useCreatedPromoCode(generNumber1)
                .clickOnPlaceOrderButton();

        Assert.assertTrue(checkOutPage.getOldPriceDisplayedOnCheckOutPage() == totalSumOfProductPricesInOrderBlock
                && checkOutPage.getTotalPriceAferUsingDiscounts() == checkOutPage.calculateTotalPriceWithUsedDiscounts(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue)
                && checkOutPage.getYouSavePriceValueDisplayedIncheckOutPage() == checkOutPage.calculateYouSavePrice(totalSumOfProductPricesInOrderBlock, promoCodeValue, creditMoneyValue));

        menuPage.deleteInDbProductPromocodeUserAndOrder(generNumber1);
    }

}




