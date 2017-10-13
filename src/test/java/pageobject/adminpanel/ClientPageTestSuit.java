package pageobject.adminpanel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.adminpanel.commonWidgets.NavigationPanel;
import pageObject.pages.adminpanel.pages.ClientsPage;
import pageObject.pages.adminpanel.pages.EditClientPage;
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
public class ClientPageTestSuit extends TestBase {

    private static final Logger LOG = LogManager.getLogger(ClientPageTestSuit.class);

    @Test(groups = { "clients" })
    // TS 1.1524
    public void checkIfUserCanChangePhoneNumberOnEditClientPageAndItWillBeSaved() throws Exception {
        String phonefromEditClientsPage;

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        clientsPage = editClientPage.setPhoneNumber(generNumber2)
                .setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        phonefromEditClientsPage = editClientPage.getPhoneNumber();

        String actualPhoneNumber = "(806) 945-" + generNumber2;

        Assert.assertTrue(actualPhoneNumber.equals(phonefromEditClientsPage));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1525
    public void CheckIfUserCanChangePhoneNumberOnEditClientPageAndItWillBeChangedForAtClientPage() throws Exception {
        String phonefromClientsPage;

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        clientsPage = editClientPage.setPhoneNumber(generNumber2)
                .setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        phonefromClientsPage = clientsPage.getPhoneNumber(generNumber1);

        String actualPhoneNumber = "(806) 945-" + generNumber2;

        Assert.assertTrue(actualPhoneNumber.equals(phonefromClientsPage));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1527
    public void CheckIfUserCanChangePhoneNumberOnEditClientPageAndItWillBeChangedOnProfilePage () throws Exception {
        String phonefromProfilePage;

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        RandomNumberGenerator.generateRandomInteger();
        String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
        clientsPage = editClientPage.setPhoneNumber(generNumber2)
                .setFAndLName("TEST", "TEST")
                .clickSaveBuuton();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        phonefromProfilePage = profilePage.getPhoneNumber();

        String actualPhoneNumber = "(806) 945-" + generNumber2;

        LOG.info(phonefromProfilePage);
        LOG.info(actualPhoneNumber);

        Assert.assertTrue(actualPhoneNumber.equals(phonefromProfilePage));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1529
    public void CheckIfUserCanChangePhoneNumberStatusFromVerifiedOnNotVerifiedAndItWillSaved () throws Exception {
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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.clickOnVerifiedPhoneNumberStatus()
                .refreshPage();

        Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='input text status-inline']/span[@class='status-red change-status']")).isDisplayed(), "Status changed to Not Verified");

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1536
    public void CheckIfUseUpdateMedicalVerificationItWillBeDisplayedInEditClientPage () throws Exception {
        String fAndLName = "TEST";
        String uploadedIdentification;
        String updatedIdentification;

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        uploadedIdentification = Users.selectIdentification(generNumber1);
        editClientPage.updateIdentification();
        updatedIdentification = Users.selectIdentification(generNumber1);

        LOG.info(uploadedIdentification);
        LOG.info(updatedIdentification);

        Assert.assertTrue(!uploadedIdentification.equals(updatedIdentification));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1537
    public void CheckIfUseUpdatePersonalVerificationItWillBeDisplayedInEditClientPage () throws Exception {
        String fAndLName = "TEST";
        String uploadedRecommendation;
        String updatedRecommendation;

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        uploadedRecommendation = Users.selectRecommendation(generNumber1);
        editClientPage.updateRecommendation();
        updatedRecommendation = Users.selectRecommendation(generNumber1);

        LOG.info(uploadedRecommendation);
        LOG.info(updatedRecommendation);

        Assert.assertTrue(!uploadedRecommendation.equals(updatedRecommendation));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1542
    public void CheckIfAdminUpdatePersonalVerificationWithStatusVerifiedOnEditClientPageAndInProfilePageStatusDoesntChangeAbovePersonalBlock() throws Exception {
        String recommendationStatus = "Pending";

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

        Users.updateVerify_PersonalToApprove(generNumber1);
        registerationStep3_1of3PopUp.IsLoaded();
        MenuPage menuPage = registerationStep3_1of3PopUp.uploadJpegfileOnStep3_1of3().navigateToPhotoRecPage();

        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.updateRecommendation();
        SwitchFromSecondTabToFirst();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getPersonaIdentificationStatus());
        Assert.assertTrue(profilePage.getPersonaIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1543
    public void CheckIfAdminUpdateMedicalVerificationWithStatusVerifiedOnEditClientPageAndInProfilePageStatusDoesntChangeAbovePersonalBlock   () throws Exception {
        String recommendationStatus = "Pending";

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

        Users.updateVerify_MedicalToApprove(generNumber1);
        menuPage.IsLoaded();
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.updateRecommendation();
        SwitchFromSecondTabToFirst();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getPersonaIdentificationStatus());
        Assert.assertTrue(profilePage.getMedicalIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.15423-1
    public void CheckIfAdminUpdatePersonalVerificationWithStatusDeclinedOnEditClientPageAndInProfilePageStatusDoesntChangeAbovePersonalBlock  () throws Exception {
        String recommendationStatus = "Pending";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        Users.updateVerify_PersonalToDeclined(generNumber1);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.updateRecommendation();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getPersonaIdentificationStatus());
        Assert.assertTrue(profilePage.getPersonaIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1543-2
    public void CheckIfAdminUpdateMedicalVerificationWithStatusDeclinedOnEditClientPageAndInProfilePageStatusDoesntChangeAbovePersonalBlock   () throws Exception {
        String recommendationStatus = "Pending";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        Users.updateVerify_MedicalToDeclined(generNumber1);
        Users.updateRec_ExpiredToNotValidDate(generNumber1);
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.updateRecommendation();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getPersonaIdentificationStatus());
        Assert.assertTrue(profilePage.getMedicalIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1544
    public void CheckIfAdminSetStatusApprovedInsteadPenddingForPersonalVerificationIfItWillChangedInProfilePageAbovePersonalRec () throws Exception {
        String recommendationStatus = "Verified";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.clickOnPersonalRecommendationStatus()
                .clickOnApprovedPersonalRecommendationStatus();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getPersonaIdentificationStatus());
        Assert.assertTrue(profilePage.getPersonaIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1544-1
    public void CheckIfAdminSetStatusDeclinedInsteadApprovedForPersonalVerificationIfItWillChangedInProfilePageAbovePersonalRec () throws Exception {
        String recommendationStatus = "Declined";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.clickOnPersonalRecommendationStatus()
                .clickOnApprovedPersonalRecommendationStatus()
                .clickOnPersonalRecommendationStatus();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getPersonaIdentificationStatus());
        Assert.assertTrue(profilePage.getPersonaIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1544-2
    public void CheckIfAdminSetStatusApprovedInsteadDeclinedForPersonalVerificationIfItWillChangedInProfilePageAbovePersonalRec () throws Exception {
        String recommendationStatus = "Verified";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.clickOnPersonalRecommendationStatus()
                .clickOnDeclinedPersonalRecommendationStatus()
                .clickOnPersonalRecommendationStatus();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getPersonaIdentificationStatus());
        Assert.assertTrue(profilePage.getPersonaIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1544-3
    public void CheckIfAdminSetStatusApprovedInsteadPenddingForMedicallVerificationIfItWillChangedInProfilePageAbovePersonalRec () throws Exception {
        String recommendationStatus = "Verified";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.clickOnMedicalRecommendationStatus()
                .clickOnApprovedMedicalRecommendationStatus();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getMedicalIdentificationStatus());
        Assert.assertTrue(profilePage.getMedicalIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1544-4
    public void CheckIfAdminSetStatusDeclinedInsteadApprovedForMedicalVerificationIfItWillChangedInProfilePageAbovePersonalRec () throws Exception {
        String recommendationStatus = "Declined";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.clickOnMedicalRecommendationStatus()
                .clickOnApprovedMedicalRecommendationStatus()
                .clickOnMedicalRecommendationStatus();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getMedicalIdentificationStatus());
        Assert.assertTrue(profilePage.getMedicalIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }

    @Test(groups = { "clients" })
    // TS 1.1544-5
    public void CheckIfAdminSetStatusApprovedInsteadDeclinedForMedicalVerificationIfItWillChangedInProfilePageAbovePersonalRec () throws Exception {
        String recommendationStatus = "Verified";

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
        CheckOutPage checkOutPage = menuPage.addToCartQuantityTypeOfProduct().clickOnOrangeCheckOutButton();
        ConfirmYourOrderPopUp confirmYourOrderPopUp = checkOutPage.setDeliveryAddress("111 S Grand Ave", "94103")
                .setPaymentDetails()
                .clickOnPlaceOrderButton();
        confirmYourOrderPopUp.IsLoaded();
        ProfilePage profilePage = confirmYourOrderPopUp.clickConfirmOrderButton();
        CreateNewTab();
        NavigationPanel navigationPanel = loginPage.loginSuperAdmin();
        ClientsPage clientsPage = navigationPanel.clickOnClientsNavMenuItem();
        EditClientPage editClientPage = clientsPage.clickOnClientStatusButton(generNumber1);
        editClientPage.clickOnMedicalRecommendationStatus()
                .clickOnDeclinedMedicalRecommendationStatus()
                .clickOnMedicalRecommendationStatus();
        SwitchFromSecondTabToFirst();
        profilePage.refreshPage();
        profilePage.clickOnVerificationTab();

        LOG.info(profilePage.getMedicalIdentificationStatus());
        Assert.assertTrue(profilePage.getMedicalIdentificationStatus().equals(recommendationStatus));

        Orders.deleteOrder(generNumber1);
        Users.deleteUser(generNumber1);
    }
}




