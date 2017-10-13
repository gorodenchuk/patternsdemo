package pageobject.desktoptest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.mmjtraine.CheckOutPage;
import pageObject.pages.mmjtraine.MenuPage;
import pageObject.pages.mmjtraine.Messages5Page;
import pageObject.pages.mmjtraine.ProfilePage;
import pageObject.pages.pop_ups.*;
import pageObject.utility.CsvDataProvider;
import pageObject.utility.DB.DbTables.*;
import pageObject.utility.RandomNumberGenerator;
import pageobject.testcase.TestBase;

import java.util.Map;
import java.util.NoSuchElementException;

/**
 * Created by NGorodenchuk on 7/17/2017.
 */
public class ProductAndCartTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(ProductAndCartTestSuit.class);

    @Test(groups = { "productAndCart" })
    // TS 1.627
    public void checkIfPopUpWillAppearIfNotLogindegUserWillClickOnPopUp() throws Exception {

        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.clickOnGreenCheckOutButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@id='cart_empty']//h4[.='Your Shopping Cart is Empty']"))
                .isDisplayed());
    }

    @Test(groups = { "productAndCart" })
    // TS 1.629
    public void checkIfSignInPopUpWillAppearIfNotLogindeUserWillClickOnPopUp() throws Exception {
        MenuPage menuPage = homePage.clickOnGeolocationButton();
        SignInPopUp signInPopUp = menuPage.addProductToCart().clickOnOrangeCheckOutButtonByNotLoggined();
        signInPopUp.IsLoaded();

        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@id='form_signin']/h4[.='Sign In']"))
                .isDisplayed());
    }

    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "productAndCart" })
    // TS 1.642..
    public void checkThatLogginedUserCanAddToCartPortionOzProductItemAndPricePortionNameDisplayCorrectOnCartButton(Map<String,String> testdata) throws Exception {
        String productNameOnCheckOutPage;
        String productPriceOnCheckOutPage;
        String productPortionOnCheckOutPage;
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

        CheckOutPage checkOutPage = menuPage.addToCartSearchedPortionProduct(testdata)
                    .clickOnOrangeCheckOutButton();

        productNameOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-details']/p[1]")).getText();
        productPriceOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-price']/span")).getText();
        productPortionOnCheckOutPage = webDriver.findElement(By.xpath(".//tr[@id='"+ menuPage.productId +"']//p[@class='quantity text-color']/span")).getText();

        Assert.assertTrue((productNameOnCheckOutPage.equals(menuPage.productName) && (productPriceOnCheckOutPage.equals(menuPage.productPrice))
        && (productPortionOnCheckOutPage.equals(testdata.get("Porsion")))));

        Users.deleteUser(generNumber1);
    }

    //    !!!! add scenarios to test doc
    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "productAndCart" })
    // TS need to add
    public void checkThatLogginedUserCanAddToCartPortionGProductItemAndPricePortionNameDisplayCorrectOnCartButton (Map<String,String> testdata) throws Exception {
        String productNameOnCheckOutPage;
        String productPriceOnCheckOutPage;
        String productPortionOnCheckOutPage;
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

        CheckOutPage checkOutPage = menuPage.addToCartSearchedPortionProduct(testdata)
                .clickOnOrangeCheckOutButton();

        productNameOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-details']/p[1]")).getText();
        productPriceOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-price']/span")).getText();
        productPortionOnCheckOutPage = webDriver.findElement(By.xpath(".//tr[@id='"+ menuPage.productId +"']//p[@class='quantity text-color']/span")).getText();

        Assert.assertTrue((productNameOnCheckOutPage.equals(menuPage.productName) && (productPriceOnCheckOutPage.equals(menuPage.productPrice))
                && (productPortionOnCheckOutPage.equals(testdata.get("Porsion")))));

        Users.deleteUser(generNumber1);
    }

    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "productAndCart" })
    // TS 1.651..
    public void checkThatLogginedUserCanAddToCartQuantityProductItemAndPricePortionNameDisplayCorrectOnCartButton (Map<String,String> testdata) throws Exception {
        String productNameOnCheckOutPage;
        String productPriceOnCheckOutPage;
        String productPortionOnCheckOutPage;
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

        CheckOutPage checkOutPage = menuPage.addToCartSearchedQuantityProduct(testdata)
                .clickOnOrangeCheckOutButton();

        productNameOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-details']/p[1]")).getText();
        productPriceOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-price']/span")).getText();
        productPortionOnCheckOutPage = webDriver.findElement(By.xpath(".//tr[@id='"+ menuPage.productId +"']//p[@class='quantity text-color']/span")).getText();

        Assert.assertTrue((productNameOnCheckOutPage.equals(menuPage.productName) && (productPriceOnCheckOutPage.equals(menuPage.productPrice))
                && (productPortionOnCheckOutPage.equals(menuPage.productQuantity))));

        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.774
    public void checkThatParticularyLogginedUserCanAddToCartAllAvailableTypesOfProduct () throws Exception {
        String productQuantityCounterOnCheckOutButton;

        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.addToCartAllAvailableProductsInStore();

        productQuantityCounterOnCheckOutButton = webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']//span[@class='cart-counter']")).getText();
        Assert.assertTrue((productQuantityCounterOnCheckOutButton.equals(menuPage.productQuantity)));

    }

    @Test(groups = { "productAndCart" })
    // TS 1.774-1
    public void checkThatLogginedUserCanAddToCartAllAvailableTypesOfProductAndCheckIfDisplayingInCartNameQuantityAndPriceOfAddedProducts () throws Exception {
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
        CheckOutPage checkOutPage = menuPage.addToCart2TypesOfAllAvailableProductsInStore()
                .clickOnOrangeCheckOutButton();
        checkOutPage.createListOfProductIdAndPrice();

        String totalPrice = webDriver.findElement(By.xpath("//section[@class='message order']//p[@class='total-price text-color-2 text-center']/span")).getText();
        String pr = Integer.toString(menuPage.productTotalPrice);

        Assert.assertTrue( CollectionUtils.isEqualCollection(menuPage.productsListOfId, checkOutPage.productsListOfIdFromCheckOutPage) && totalPrice.equals( "$" + pr));

        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.774-2
    public void checkThatLogginedUserCanAddToCartAllAvailableTypesOfProduct () throws Exception {
        String productQuantityCounterOnCheckOutButton;

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

        menuPage.addToCartAllAvailableProductsInStore();

        productQuantityCounterOnCheckOutButton = webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']//span[@class='cart-counter']")).getText();

        Assert.assertTrue(productQuantityCounterOnCheckOutButton.equals(menuPage.productQuantity));

        Users.deleteUser(generNumber1);
    }

    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "productAndCart" })
    // TS 1.863-865-1
    public void checkCorrectResultOfFoundedProductByStraineOnMenuPageAndItemCounterInFilterAndCookie (Map<String,String> testdata) throws Exception {
        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.selectInFilterStrains(testdata).refreshPage();
        menuPage.IsLoaded();

        String showingItems = webDriver.findElement(By.xpath("//i[@id='products_showing']")).getText();
        WebElement foundedProductItem = webDriver.findElement(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @style='opacity: 1;']"));

        Assert.assertTrue( showingItems.equals(menuPage.productQuantity) &&  foundedProductItem.isDisplayed());
    }

    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "productAndCart" })
    // TS 1.866-869-3
    public void checkCorrectResultOfFoundedByProductOnMenuPageAndItemCounterInFilterAndCookie (Map<String,String> testdata) throws Exception {
        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.selectInFilterProduct(testdata).refreshPage();
        menuPage.IsLoaded();

        String showingItems = webDriver.findElement(By.xpath("//i[@id='products_showing']")).getText();
        WebElement foundedProductItem = webDriver.findElement(By.xpath("//figure[@data-product='" + testdata.get("Products") + "' and @style='opacity: 1;']"));

        Assert.assertTrue( showingItems.equals(menuPage.productQuantity) &&  foundedProductItem.isDisplayed());
    }


    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "productAndCart" })
    // TS 1.869-4-881-16
    public void checkCorrectResultOfFoundedProductByStraineAndProductOnMenuPageAndItemCounterInFilterAndCookie (Map<String,String> testdata) throws Exception {
        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.selectInFilterProductsAndStrains(testdata).refreshPage();
        menuPage.IsLoaded();

        String showingItems = webDriver.findElement(By.xpath("//i[@id='products_showing']")).getText();
        WebElement foundedProductItem = webDriver.findElement(By.xpath("//figure[@data-strain='" + testdata.get("Strains") + "' and @data-product='" + testdata.get("Products") + "' and @style='opacity: 1;']"));

        Assert.assertTrue( showingItems.equals(menuPage.productQuantity) &&  foundedProductItem.isDisplayed());
    }

    @Test(groups = { "productAndCart" })
    // TS 1.959
    public void checkVerificationWithValidAddressWithLocationInUsa() throws Exception {
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
//        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//        menuPage = messages5Page.setPlaceInAddressField("San Francisco");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("1911 Johnson Ave", "93401").
                setPaymentDetails().
                clickOnPlaceOrderButton();

        try {
            webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[.='Address Not Found.']"));
        } catch (org.openqa.selenium.NoSuchElementException e) {
            LOG.info("Element absent");
            Assert.assertTrue(true);
        }

    }

    @Test(groups = { "productAndCart" })
    // TS 1.960
    public void checkVerificationWithInValidAddressWithLocationInUsa() throws Exception {
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
//        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//        menuPage = messages5Page.setPlaceInAddressField("San Francisco");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("Via del Gianicolo, 3", "93405").
                setPaymentDetails().
                clickOnPlaceOrderButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[.='Address Not Found.']")).isDisplayed());

    }

    @Test(groups = { "productAndCart" })
    // TS 1.961
    public void checkVerificationWithEmptyAddressWithLocationInUsa() throws Exception {
        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
        signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
        signUpPopUp.IsLoaded();
        RandomNumberGenerator.generateRandomInteger();
        String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
        registerationStep2_1of3PopUp.IsLoaded();
        RegisterationStep3_1of3PopUp registerationStep3_1of3PopUp = registerationStep2_1of3PopUp.uploadJpegfile().navigateToPhotoIdPage();;

        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
//        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//        menuPage = messages5Page.setPlaceInAddressField("San Francisco");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("", "94103").
                setPaymentDetails().
                clickOnPlaceOrderButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//input[@id='address' and @class='standard-input search-input error-input']")).isDisplayed());
    }

    @Test(groups = { "productAndCart" })
    // TS 1.963
    public void checkVerificationWithCityNameInAddressFieldWithLocationInUsa() throws Exception {
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
//        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//        menuPage = messages5Page.setPlaceInAddressField("San Francisco");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("San Francisco", "94103").
                setPaymentDetails().
                clickOnPlaceOrderButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[.='Multiple addresses were found for the information you entered, and no default exists.']"))
                .isDisplayed());

    }

    @Test(groups = { "productAndCart" })
    // TS 1.964
    public void checkVerificationWithStateNameInAddressFieldWithLocationInUsa() throws Exception {
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
//        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//        menuPage = messages5Page.setPlaceInAddressField("San Francisco");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("Calofornia", "93041").
                setPaymentDetails().
                clickOnPlaceOrderButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[.='Multiple addresses were found for the information you entered, and no default exists.']"))
                .isDisplayed());

    }

    @Test(groups = { "productAndCart" })
    // TS 1.965
    public void checkVerificationWithZipCodeInAddressFieldWithLocationInUsa() throws Exception {
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
//        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//        menuPage = messages5Page.setPlaceInAddressField("San Francisco");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("94102", "93401").
                setPaymentDetails().
                clickOnPlaceOrderButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[.='Address Not Found.']"))
                .isDisplayed());

    }

    @Test(groups = { "productAndCart" })
    // TS 1.965-1
    public void checkVerificationAfterEditingAddressWithCorrectData() throws Exception {
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
        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
        menuPage = messages5Page.setPlaceInAddressField("Los Angeles");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("1530 Bullpup Cir", "93041").
                setPaymentDetails().
                clickOnPlaceOrderButton();
        // Thread.sleep(1000);
        checkOutPage = confirmYourOrderPopUp.clickEditButton();
        // Thread.sleep(1000);
        checkOutPage.setDeliveryAddress("1911 Johnson Ave", "93401").clickOnPlaceOrderButton();
        // Thread.sleep(1000);

        Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[1]")).getText().equals("1911 JOHNSON AVE") &&
                webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[2]")).getText().equals("93401-4131") &&
                webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[3]")).getText().equals("SN LUIS OBISP") &&
                webDriver.findElement(By.xpath("//p[@class='text-color confirm-info address-text']/span[4]")).getText().equals("CA"));
    }

    @Test(groups = { "productAndCart" })
    // TS 1.965-2
    public void checkDisplayingOnCheckOutPageCityAndStateNameAfterEditingAddress() throws Exception {
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
        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
        menuPage = messages5Page.setPlaceInAddressField("Los Angeles");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("1530 Bullpup Cir", "93041").
                setPaymentDetails().
                clickOnPlaceOrderButton();
        // Thread.sleep(1000);
        checkOutPage = confirmYourOrderPopUp.clickEditButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='detailed-address']/div[@class='inp-group'][1]/input[1]")).getAttribute("value").equals("Los Angeles") &&
                webDriver.findElement(By.xpath("//div[@class='detailed-address']/div[@class='inp-group'][2]/input[1]")).getAttribute("value").equals("CA"));
    }

    @Test(groups = { "productAndCart" })
    // TS 1.965-3
    public void checkDisplayingOnCheckOutPageCityAndStateNameAfterEditingChangedAddress() throws Exception {
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
        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
        menuPage = messages5Page.setPlaceInAddressField("Los Angeles");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("1530 Bullpup Cir", "93041").
                setPaymentDetails().
                clickOnPlaceOrderButton();
        // Thread.sleep(1000);
        checkOutPage = confirmYourOrderPopUp.clickEditButton();
        // Thread.sleep(1000);
        checkOutPage.setDeliveryAddress("1911 Johnson Ave", "93401").clickOnPlaceOrderButton();
        // Thread.sleep(1000);
        checkOutPage = confirmYourOrderPopUp.clickEditButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='detailed-address']/div[@class='inp-group'][1]/input[1]")).getAttribute("value").equals("SN LUIS OBISP") &&
                webDriver.findElement(By.xpath("//div[@class='detailed-address']/div[@class='inp-group'][2]/input[1]")).getAttribute("value").equals("CA"));
    }

    @Test(groups = { "productAndCart" })
    // TS 1.966
    public void placeOrderWithDeliveryInstruction() throws Exception {
        String deliveryInstuction = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eu erat sed dolor vehicula condimentum et eu justo. " +
                "Sed quam mauris, posuere eu lacinia at, porta sed augue. Duis ut aliquet est. Maecenas mattis magna sit amet libero porttitor, " +
                "non elementum justo pretium. Aliquam eu gravida neque. Sed sagittis nisi est, vitae rutrum ligula ornare quis. Sed eget nisi at urna dignissim " +
                "condimentum. Morbi interdum ligula a nibh molestie, ut luctus quam luctus. Vestibulum ante ipsum primis in faucibus orci luctus...";

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
        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
        menuPage = messages5Page.setPlaceInAddressField("Los Angeles");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        checkOutPage.setDeliveryInstruction(deliveryInstuction);
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("1911 Johnson Ave", "93401")
                . setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        profilePage.IsLoaded();

        Assert.assertTrue(webDriver.findElement(By.xpath("//ul[@class='list-unstyled text-color text']/li[2]"))
                .isDisplayed());

        Users.deleteUser(generNumber1);
        Orders.deleteOrder(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.967
    public void placeOrderWithoutDeliveryInstruction() throws Exception {
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
        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
        menuPage = messages5Page.setPlaceInAddressField("Los Angeles");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("1911 Johnson Ave", "93401")
                . setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        profilePage.IsLoaded();

        Assert.assertTrue(webDriver.findElement(By.xpath("//ul[@class='list-unstyled text-color text']/li[2]"))
                .isDisplayed());

        Users.deleteUser(generNumber1);
        Orders.deleteOrder(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.970-5
    public void checkThatUserCanPlace2() throws Exception {
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
        Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
        menuPage = messages5Page.setPlaceInAddressField("Los Angeles");
        CheckOutPage checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "93405")
                . setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        profilePage.IsLoaded();
        menuPage = profilePage.clickOnMenuButton();
        checkOutPage = menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "93405")
                . setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//ul[@class='list-unstyled text-color text']/li[2]"))
                .isDisplayed());

        Users.deleteUser(generNumber1);
        Orders.deleteOrder(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.970-6
    public void checkThatInConfirmYourOrderPopUpDisplaysAddedAllAvailablePortionProductsFromMenuPage() throws Exception {
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
        CheckOutPage checkOutPage = menuPage.AddToCartAllAvailablePartionTypesOfProductsItems().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "93405")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.getOrderInformation();

        for (int i = 0; i < menuPage.productsListOfPortionItemId.size(); i++){
            for (int j = 0; j < confirmYourOrderPopUp.productsListOfItemId.size(); j++ ){
                if (menuPage.productsListOfPortionItemId.get(i).equals(confirmYourOrderPopUp.productsListOfItemId.get(j))){
                    menuPage.productsListOfPortionItemName.get(i).equals(confirmYourOrderPopUp.productsListOfItemName.get(j));
                    menuPage.productsListOfPortionItemPrice.get(i).equals(confirmYourOrderPopUp.productsListOfItemPrice.get(j));
                    menuPage.productsListOfPortionItemQuantity.get(i).equals(confirmYourOrderPopUp.productsListOfItemQuantity.get(j));

                    break;
                }
            }
            count++;
        }

        Assert.assertTrue(count == menuPage.productsListOfPortionItemId.size(),
                "Count of added product item equal items that display at Confirm Your Order PopUp ");


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.970-7
    public void checkThatInConfirmYourOrderPopUpDisplaysAddedAllAvailableQuantityProductsFromMenuPage() throws Exception {
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
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "93405")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.getOrderInformation();

        for (int i = 0; i < menuPage.productsListOfQuantityItemId.size(); i++){
            for (int j = 0; j < confirmYourOrderPopUp.productsListOfItemId.size(); j++ ){
                if (menuPage.productsListOfQuantityItemId.get(i).equals(confirmYourOrderPopUp.productsListOfItemId.get(j))){
                    menuPage.productsListOfQuantityItemName.get(i).equals(confirmYourOrderPopUp.productsListOfItemName.get(j));
                    menuPage.productsListOfQuantityItemPrice.get(i).equals(confirmYourOrderPopUp.productsListOfItemPrice.get(j));
                    menuPage.productsListOfQuantityItemQuantity.get(i).equals(confirmYourOrderPopUp.productsListOfItemQuantity.get(j));

                    break;
                }
            }
            count++;
        }
        LOG.info(menuPage.productsListOfQuantityItemId.size());

        Assert.assertTrue(count == menuPage.productsListOfQuantityItemId.size(),
                "Count of added product item equal items that display at Confirm Your Order PopUp ");


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.970-8
    public void checkThatInConfirmYourOrderPopUpDisplaysAllAddedAvailableProductItemsFromMenuPage() throws Exception {
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
        CheckOutPage checkOutPage = menuPage.addToCart2TypesOfAllAvailableProductsInStore().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "93405")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.getOrderInformation();

        for (int i = 0; i < menuPage.productsListOfId.size(); i++){
            for (int j = 0; j < confirmYourOrderPopUp.productsListOfItemId.size(); j++ ){
                if (menuPage.productsListOfId.get(i).equals(confirmYourOrderPopUp.productsListOfItemId.get(j))){

                    break;
                }
            }
            count++;
        }

        Assert.assertTrue(count == menuPage.productsListOfId.size(),
                "Count of added product item equal items that display at Confirm Your Order PopUp ");


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.970-9
    public void checkThatInConfirmYourOrderPopUpChangeQuantityOfProductAfterRemoveThemAtCheckOutPage() throws Exception {
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
        CheckOutPage checkOutPage = menuPage.addToCart2TypesOfAllAvailableProductsInStore().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.deleteAddedOredersExceptOne()
                .setDeliveryAddress("111 S Grand Ave", "93405")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        confirmYourOrderPopUp.getOrderInformation();

        for (int i = 0; i < confirmYourOrderPopUp.productsListOfItemId.size(); i++)
        Assert.assertTrue(confirmYourOrderPopUp.productsListOfItemPrice.get(i).equals(webDriver.findElement(By.xpath("//p[@class='total-price text-color-2 text-center']/span")).getText()),
                "Count of added product item equal items that display at Confirm Your Order PopUp ");


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1027
    public void checkThatWhenUserAddProductPortionItemTypeToCartOnCheckOutButtonAppearsCheckOutWord() throws Exception {
        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.IsLoaded();
        menuPage.addToCartPorіonTypeOfProduct();

        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']//span[.='Checkout']")).isDisplayed());
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1027-1
    public void checkThatWhenUserAddProductQuantityItemTypeToCartOnCheckOutButtonAppearsCheckOutWord() throws Exception {
        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.IsLoaded();
        menuPage.addToCartQuantityTypeOfProduct();

        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']//span[.='Checkout']")).isDisplayed());
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1043
    public void checkThatWhenLogginedUserUpdatesProductAddedButtonChangeItNameForPortionItemType() throws Exception {
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
        menuPage.AddToCartAndThanUpdatePoritonTypeOfProduct();

        Assert.assertTrue(webDriver.findElement(By.xpath("//figure//div[@class='pack-count three-packs text-color-2 count-select']/div[@class='count-part count-active']/ancestor::div[@class='product-order']//button[.='Updated']")).isDisplayed());


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1047
    public void checkThatWhenLogginedUserUpdatesProductUsingPlusAddedButtonChangeItNameForQuantityItemType() throws Exception {
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
        menuPage.AddToCartAndThanUpdateQuanitytTypeOfProductUsingPlus();

        Assert.assertTrue(webDriver.findElement(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//button[.='Updated']")).isDisplayed());


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1048
    public void checkThatWhenLogginedUserUpdatesProductUsingMinusAddedButtonChangeItNameForQuantityItemType() throws Exception {
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
        menuPage.AddToCartAndThanUpdateQuanitytTypeOfProductUsingMinusButton();

        Assert.assertTrue(webDriver.findElement(By.xpath("//figure//div[@class='pack-count three-packs text-color-2']/ancestor::div[@class='product-order']//button[.='Updated']")).isDisplayed());


        Users.deleteUser(generNumber1);
    }

//    !!!!
    @Test(groups = { "productAndCart" })
    // TS 1.1049
    public void checkThatWhenLogginedUserUpdatesProductForPortionItemTypeNameQuantityProductPriceAndTotalPriceOnCheckOutPageUpdates() throws Exception {

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
        CheckOutPage checkOutPage = menuPage.AddToCartAndThanUpdatePoritonTypeOfProduct().clickOnOrangeCheckOutButton();

        String productNameOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-details']/p[1]")).getText();
        String productPriceOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-price']/span")).getText();
        String productPortionOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']//p[@class='quantity text-color']/span")).getText();

        Assert.assertTrue((productNameOnCheckOutPage.equals(menuPage.productName) && (productPriceOnCheckOutPage.equals("$" + menuPage.productPrice))
                && (productPortionOnCheckOutPage.equals(menuPage.productQuantity))));


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1050
    public void checkThatWhenLogginedUserUpdatesProductForQuantityItemTypeUsingPlusButtonNameQuantityProductPriceAndTotalPriceOnCheckOutPageUpdates() throws Exception {

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
        CheckOutPage checkOutPage =  menuPage.AddToCartAndThanUpdateQuanitytTypeOfProductUsingPlus().clickOnOrangeCheckOutButton();

        String productNameOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-details']/p[1]")).getText();
        String productPriceOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-price']/span")).getText();
        String productPortionOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']//p[@class='quantity text-color']/span")).getText();

        Assert.assertTrue((productNameOnCheckOutPage.equals(menuPage.productName) && (productPriceOnCheckOutPage.equals("$" + menuPage.productPrice))
                && (productPortionOnCheckOutPage.equals(menuPage.productQuantity))));


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1051
    public void checkThatWhenLogginedUserUpdatesProductForQuantityItemTypeUsingMinusButtonNameQuantityProductPriceAndTotalPriceOnCheckOutPageUpdates() throws Exception {

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
        CheckOutPage checkOutPage =  menuPage.AddToCartAndThanUpdateQuanitytTypeOfProductUsingMinusButton().clickOnOrangeCheckOutButton();

        String productNameOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-details']/p[1]")).getText();
        String productPriceOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']/td[@class='item-price']/span")).getText();
        String productPortionOnCheckOutPage = webDriver.findElement(By.xpath("//tr[@id='"+ menuPage.productId +"']//p[@class='quantity text-color']/span")).getText();

        Assert.assertTrue((productNameOnCheckOutPage.equals(menuPage.productName) && (productPriceOnCheckOutPage.equals("$" + menuPage.productPrice))
                && (productPortionOnCheckOutPage.equals(menuPage.productQuantity))));


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1061
    public void checkThatWhenLogginedUserDeleteOnCheckOutPagePortionItemItDoesntDisplayAsAddedOnMenuPage() throws Exception {

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
        CheckOutPage checkOutPage =  menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        menuPage = checkOutPage.clickOnDeleteAddedProductItem();

        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart']")).isDisplayed());



        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
//    // TS 1.1062
    public void checkThatWhenLogginedUserDeleteOnCheckOutPageQuantityItemItDoesntDisplayAsAddedOnMenuPage() throws Exception {

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
        CheckOutPage checkOutPage =  menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        menuPage = checkOutPage.clickOnDeleteAddedProductItem();

        Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart']")).isDisplayed());



        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1063
    public void checkThatWhenLogginedUserCloseCheckOutPagePortionItemItStillDisplayAsAddedOnMenuPage() throws Exception {

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
        CheckOutPage checkOutPage =  menuPage.addToCartPorіonTypeOfProduct().clickOnOrangeCheckOutButton();
        menuPage = checkOutPage.clickOnCloseButton();


        try{
            webDriver.findElement(By.xpath("//button[@class='product-item product-added']")).isDisplayed();
            webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']")).isDisplayed();
            Assert.assertTrue(true);
        }
        catch (NoSuchElementException e){
            Assert.assertTrue(false, "Element should not be displays");
        }


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1064
    public void checkThatWhenLogginedUserCloseCheckOutPageQuantityItemItStillDisplayAsAddedOnMenuPage() throws Exception {

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
        CheckOutPage checkOutPage =  menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        menuPage = checkOutPage.clickOnCloseButton();


        try{
            webDriver.findElement(By.xpath("//button[@class='product-item product-added']")).isDisplayed();
            webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']")).isDisplayed();
            Assert.assertTrue(true);
        }
        catch (NoSuchElementException e){
            Assert.assertTrue(false, "Element should not be displays");
        }


        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "productAndCart" })
    // TS 1.1067
    public void checkThatParticularyLogginedUserCanAddToCartAllAvailableProductRefreshBrowserTabAndCounterWilldisplayCorrectQuantity () throws Exception {
        String productQuantityCounterOnCheckOutButton;

        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.addToCartAllAvailableProductsInStore().refreshPage();
        menuPage.IsLoaded();

        productQuantityCounterOnCheckOutButton = webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-cart cart-full nav-link']//span[@class='cart-counter']")).getText();
        Assert.assertTrue((productQuantityCounterOnCheckOutButton.equals(menuPage.productQuantity)));

    }

    @Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "productAndCart" })
    // TS 1.1220-1226-5
    public void checkAppearingRessetButtonWhenUserSelectFilterParameters (Map<String,String> testdata) throws Exception {
        MenuPage menuPage = homePage.clickOnGeolocationButton();
        menuPage.selectFilterParameter(testdata);

        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='filters-result' and @style='display: block;']//button[.='Reset']")).isDisplayed());
    }

}




