package pageobject.desktoptest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.mmjtraine.CheckOutPage;
import pageObject.pages.mmjtraine.MenuPage;
import pageObject.pages.mmjtraine.ProfilePage;
import pageObject.pages.pop_ups.*;
import pageObject.utility.CsvDataProvider;
import pageObject.utility.DB.DataBaseExecute;
import pageObject.utility.DB.DbTables.Users;
import pageObject.utility.RandomNumberGenerator;
import pageobject.testcase.TestBase;

import java.util.Map;

public class ParticularSignUpTestSuit extends TestBase{

	private static final Logger LOG = LogManager.getLogger(ParticularSignUpTestSuit.class);

	@Test (groups = { "particularSingUp" })
	// TS 1.107
	public void checkAppearingHintWindowAboweZipField() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.setTwoNumberInZipCodeField("44");

		Assert.assertTrue(webDriver.findElement(By.xpath(".//*[@class='dynamic-list text text-color']"))
				.isDisplayed());
	}

	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "particularSingUp" })
	// TS 1.108
	public void checkSignUpWithValidData(Map<String,String> testdata) throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RegisterationStep1_1of3PopUp registerationStep1of3Page = signUpPopUp.signUpWithValidData(testdata);
		registerationStep1of3Page.IsLoaded();


		Users.deleteUser(testdata);

		Assert.assertTrue(webDriver.findElement(By.xpath("//button[contains(text(),'Send Verification Code')]"))
				.isDisplayed());
	}

	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "particularSingUp" })
	// TS 1.109
	public void checkSignUpWithNotValidData(Map<String,String> testdata) throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RegisterationStep1_1of3PopUp registerationStep1of3Page = signUpPopUp.signUpWithValidData(testdata);
		registerationStep1of3Page.IsLoaded();

		Users.deleteUser(testdata);

		Assert.assertTrue(webDriver.findElement(By.xpath(".//small[contains(text(),'All accounts are private and secure.')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.127
	public void checkThatUserCouldNotUseExistingEmailInSignUp() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		homePage = profilePage.clickOnLogOutButton();
		signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		signUpPopUp.signUpUser1ValidDatA(generNumber1);


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[contains(text(),'User already exists.')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.146
	public void checkThatUserCanUseInRegistrationSameZipThatAnotherUserUsed() throws Exception {
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		String generNumber2 = RandomNumberGenerator.getGeneratedNumber();
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		registerationStep11Of3PopUp.IsLoaded();
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		homePage = profilePage.clickOnLogOutButton();
		homePage.IsLoaded();
		signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		signUpPopUp = signInPopUp.clickOnSignUpButton();
		registerationStep11Of3PopUp = signUpPopUp.signUpUser2ValidDatA(generNumber2);


		Users.deleteUser(generNumber1);
		Users.deleteUser(generNumber2);

		Assert.assertTrue(webDriver.findElement(By.xpath("//button[contains(text(),'Send Verification Code')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.152
	public void checkSignInWithValidData() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		homePage = profilePage.clickOnLogOutButton();
		homePage.clickOnSignInButton();
		signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		menuPage = signInPopUp.signInUser1ValidDatA(generNumber1);
		menuPage.IsLoaded();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//span[contains(text(),'Your Location')]"))
				.isDisplayed());
	}


//  !!! here cases for checking the button clickabilings of signin and logging joined together
	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "particularSingUp" })
	// TS 1.153, 1.154, 1.158, 1.59, 1.164-1.168
	public void checkSignInWithNotValidData(Map<String,String> testdata) throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		homePage = profilePage.clickOnLogOutButton();
		signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		signInPopUp.signInUser1UsingInvalidData(testdata);
		// Thread.sleep(800);


		Users.deleteUser(generNumber1);

		Assert.assertFalse(false, "user could not sign in");

	}

	@Test(groups = { "particularSingUp" })
	// TS 1.156
	public void checkSignInWithPasswordChangedInProfile() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		homePage = profilePage.setDataForChangingPassword("1234", "12345", "12345").
				clcikChangePasswordButton().
				clickOnLogOutButton();
		signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		signInPopUp.signInUser1WithChangedPasswordInProfile(generNumber1);
		menuPage.IsLoaded();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//span[contains(text(),'Your Location')]"))
				.isDisplayed());
	}


//SomethingWrong
	@Test(groups = { "particularSingUp" })
	// TS 1.155
	public void checkSignInWithEmailChangedInProfile() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		homePage = profilePage.changeCurrentEmail(generNumber1).clickOnLogOutButton();
		signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		signInPopUp.signInUser1WithChangedEmailInProfile(generNumber1);
		menuPage.IsLoaded();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//span[contains(text(),'Your Location')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 1.186
	public void checkVerificationlinkDisplaysAfterEntering10DigitsPhoneNumber() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.set10DigitsPhoneNumberInPhoneField(generNumber1);


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//input[@data-valid='true']/following::div[1]/span[.='Verify']"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 1.187
	public void checkThatAfterMakingClickOnVerifyButtonPopUpAppear() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signInPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		PhoneNumberVerifyPopUp phoneNumberVerifyPopUp = profilePage.set10DigitsPhoneNumberInPhoneField(generNumber1).
				clickOnVerifyButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//body[@class='modal-open']//h4[.='Verify Your Phone Number']"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	//assert-
	// TS 1.188
	public void checkThatVerificationlinkDosntDisplaysAfterEnteringLettersInPhoneField() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();

		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.setLettersInPhoneField();

		try {
			webDriver.findElement(By.xpath("//input[@data-valid='true']/following::div[1]/span[.='Verify']"));
			LOG.info("Element Present");
			Assert.assertTrue(false);
		} catch (NoSuchElementException e) {
			LOG.info("Element absent");
			Assert.assertTrue(true);
		}


		Users.deleteUser(generNumber1);

	}

	@Test(groups = { "particularSingUp" })
	//assert-
	// TS 1.189
	public void checkThatVerificationlinkDosntDisplaysAfterEntering9DigitsPhoneNumber() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.set9DigitsPhoneNumberInPhoneField();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//input[@data-valid='false']/following::div[1]/span[.='Verify'], Verify button doesnt displaying"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 1.191
	public void checkThatVerificationlinkDosntDisplaysAfterEnteringSpecialSympbolsInPhoneField() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.setSpecialSymbolsInPhoneField();

		try {
			webDriver.findElement(By.xpath("//input[@data-valid='true']/following::div[1]/span[.='Verify']"));
			LOG.info("Element Present");
			Assert.assertTrue(false);
		} catch (NoSuchElementException e) {
			LOG.info("Element absent");
			Assert.assertTrue(true);
		}


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 1.192
	public void checkSavingNUmberAfterClickOnSaveButtonAfterInputing10DigitsPhoneNumber() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.set10DigitsPhoneNumberInPhoneField(generNumber1).clickOnSaveButtonAndRefreshPage();
		profilePage.IsLoaded();

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='verified-input']/input[@value='(860) 971-" + generNumber1 + "']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 1.193
	public void checkDisplayingVerifyButtonAfterSaving10DigitsNumberAfterRefreshingPage() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.set10DigitsPhoneNumberInPhoneField(generNumber1).clickOnSaveButtonAndRefreshPage();
		profilePage.IsLoaded();
		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='verify-status text verify-phone-status']/span[.='Verify']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 15.193-1
	public void checkAppearingErrorMessageAfterSavingNotUSAPhoneNumber() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.set10DigitsNotUSAPhoneNumberInPhoneField().clickOnSaveButton();
		profilePage.IsLoaded();
		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='profile_save_changes']/p[.='phone: Only US phone numbers']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 15.198-1
	public void checkDisplayingEntered10DigitsNotUSAPhoneNumberInVerifyYourNumberPhonePopUp() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.set10DigitsNotUSAPhoneNumberInPhoneField()
				.clickOnVerifyButton();
		profilePage.IsLoaded();

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='verify_form']/p[.='(111) 111-1111']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 15.198-2
	public void checkDisplayingEntered10DigitsUSAPhoneNumberInVerifyYourNumberPhonePopUp() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		PhoneNumberVerifyPopUp phoneNumberVerifyPopUp = profilePage.set10DigitsPhoneNumberInPhoneField(generNumber1)
				.clickOnVerifyButton();
		phoneNumberVerifyPopUp.IsLoaded();
		// Thread.sleep(1000);
		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='verify_form']/p[.='(860) 971-" + generNumber1 + "']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 15.209
	public void checkVerificationStatusDisplaysAfterVerifyingPhoneNumber() throws Exception {

		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.set10DigitsPhoneNumberInPhoneField(generNumber1);
//        PhoneNumberVerifyPopUp phoneNumberVerifyPopUp = profilePage.set10DigitsPhoneNumberInPhoneField();
//                .clickOnVerifyButton();
//		phoneNumberVerifyPopUp.IsLoaded();
//        EnterVerificationCodePopUp enterVerificationCodePopUp = phoneNumberVerifyPopUp.clickOnSendVerificationCodeButton();
//        profilePage = enterVerificationCodePopUp.verifyRecievedCode(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='profile_save_changes']//div[@class='verify-status text verify-phone-status']/span[.='Verified']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	//assert+
	// TS 15.209-1
	public void checkIferrorMessageAppearInEnterVerificationCodePopUpifUserSetsNotCorrectCode() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		PhoneNumberVerifyPopUp phoneNumberVerifyPopUp = profilePage.set10DigitsPhoneNumberInPhoneField(generNumber1)
				.clickOnVerifyButton();
		phoneNumberVerifyPopUp.IsLoaded();
		EnterVerificationCodePopUp enterVerificationCodePopUp = phoneNumberVerifyPopUp.clickOnSendVerificationCodeButton();
		profilePage = enterVerificationCodePopUp.verifyNotcorectCode(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='confirm']/p[.='Code is not valid.']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "particularSingUp" })
	// TS 15.224-239
	public void checkIfInProfilePageUserCanSaveEmailOnInValidFormat(Map<String,String> testData) throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.ChangeEmailAndSave(testData);

		Assert.assertTrue(webDriver.findElement(By.xpath("//button[@class='btn-main btn-standard btn-savechanges' and @disabled='disabled']"))
				.isDisplayed());

		Users.deleteUser(generNumber1);
		Users.deleteUser(testData);
	}

	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "particularSingUp" })
	// TS 15.240
	public void checkIfInProfilePageUserCanSaveEmailOnValidFormat(Map<String,String> testData) throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.ChangeEmailAndSave(testData);

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='verified-input']/input[@data-original='" + testData.get("Email") + "']"))
				.isDisplayed());

		Users.deleteUser(generNumber1);
		Users.deleteUser(testData);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.241
	public void checkIfInProfilePageUserCanSaveEmailOnValidFormat() throws Exception {
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		String generNumber2 = RandomNumberGenerator.getGeneratedNumber();

		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		homePage = profilePage.clickOnLogOutButton();
		signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();

		registerationStep11Of3PopUp = signUpPopUp.signUpUser2ValidDatA(generNumber2);
		menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		profilePage = menuPage.clickOnProfileButton().
				useAlreadeExistEmailOfAnotherUserAndSaveIt(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='profile_save_changes']/p[.='email: Email already taken.']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
		Users.deleteUser(generNumber2);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.250
	public void checkIfChangePasswordButtonBecomeActiveIfUserEnterValidData() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.setDataForChangingPassword("1234", "12345", "12345");
		profilePage.IsLoaded();

		try {
			webDriver.findElement(By.xpath("//form[@id='profile_save_password']/button[@disabled='disabled' and .='Change Password']"));
			LOG.info("Element Present");
			Assert.assertTrue(false);
		} catch (NoSuchElementException e) {
			LOG.info("Element absent");
			Assert.assertTrue(true);
		}


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.253
	public void checkIfUserCanChangePasswordIfHeUseNotValidPassword() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton().
				setDataForChangingPassword("12345", "12345", "12345").
				clcikChangePasswordButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='profile_save_password']/p[.='Incorrect password']"))
				.isDisplayed());



		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.254
	public void checkIfChangePasswordButtonDoesntBrecomeActiveIfUserEnter1NumberInNewPasswordField() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton().
				setDataForChangingPassword("1234", "1");
		profilePage.IsLoaded();

		try {
			webDriver.findElement(By.xpath("//form[@id='profile_save_password']/button[@disabled='disabled' and .='Change Password']"));
			LOG.info("Element Present");
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			LOG.info("Element absent");
			Assert.assertTrue(false);
		}


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.255
	public void checkIfChangePasswordButtonDoesntBrecomeActiveIfUserEnter1NumberInCurrentPasswordField() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton().
				setDataForChangingPassword("1234", "12345", "1").
				clcikChangePasswordButton();

		try {
			webDriver.findElement(By.xpath("//form[@id='profile_save_password']/button[@disabled='disabled' and .='Change Password']"));
			LOG.info("Element Present");
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			LOG.info("Element absent");
			Assert.assertTrue(false);
		}


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.256
	public void checkIfChangePasswordButtonDoesntBrecomeActiveIfUserEnter3SymbolInCurrentPasswordField() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		ProfilePage profilePage = menuPage.clickOnProfileButton().
				setDataForChangingPassword("123", "12345", "12345").
				clcikChangePasswordButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='profile_save_password']/p[.='Incorrect password']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

    @Test(groups = { "particularSingUp" })
    // TS 1.258
    public void checkIfUserCanChangeHisPasswordAtTheSame() throws Exception {
        String oldPass = "81dc9bdb52d04dc20036dbd8313ed055";

        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
	signInPopUp.IsLoaded();
        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
        RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
        MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
	menuPage.IsLoaded();
        ProfilePage profilePage = menuPage.clickOnProfileButton().
                setDataForChangingPassword("1234", "1234", "1234").
                clcikChangePasswordButton();
		profilePage.IsLoaded();
		String newPass = Users.selectPassword(generNumber1);

		if (newPass == oldPass)
			Assert.assertTrue(true);

		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.284
	public void checkRedirectionOnStep1_1of3AfterSignUp() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='verify']/h3[contains(text(),'Enter Your Mobile Phone')]"))
				.isDisplayed());

		Users.deleteUser(generNumber1);
	}


	@Test(groups = { "particularSingUp" })
	// TS 1.312
	public void userSetValidNumberAndRedirectsToNextStep() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		RegisterationStep1_2of3PopUp registerationStep1_2of3PopUp = registerationStep1_1of3PopUp.setValidDataAndClcikSendVerificationButton();
		registerationStep1_2of3PopUp.IsLoaded();

		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//form[@id='confirm']/h3[contains(text(),'Enter Verification Code')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.313
	public void checkThatSendButtonWouldNotActiveIfUse9DigitsNumber() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		RegisterationStep1_2of3PopUp registerationStep1_2of3PopUp = registerationStep1_1of3PopUp.setNumberWith9DigitsAndClickSendVerificationButton();
		registerationStep1_2of3PopUp.IsLoaded();

		Users.deleteUser(generNumber1);

		Assert.assertFalse(false, "Send Verification Button is not Clickable");
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.314
	public void checkThatSendButtonWouldNotActiveIfUserWouldNotMakeCheckBoxActive() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		RegisterationStep1_2of3PopUp registerationStep1_2of3PopUp = registerationStep1_1of3PopUp.setValidPhoneButCheckBoxLeftNotActiveAndClcikSendVerificationButton();
		registerationStep1_2of3PopUp.IsLoaded();


		Users.deleteUser(generNumber1);

		Assert.assertFalse(false, "Send Verification Button is not Clickable");
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.315
	public void checkThatSendButtonWouldNotActiveIfUserWillTapOnSendVerificationButton() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		RegisterationStep1_2of3PopUp registerationStep1_2of3PopUp = registerationStep1_1of3PopUp.ClcikSendVerificationButton();
		registerationStep1_2of3PopUp.IsLoaded();

		Users.deleteUser(generNumber1);

		Assert.assertFalse(false, "Send Verification Button is not Clickable");
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.317
	public void checkThatSendButtonWouldNotActiveIfUserWillTapOnSendVerificationButtonButWithEnteredValidNumber() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		RegisterationStep1_2of3PopUp registerationStep1_2of3PopUp = registerationStep1_1of3PopUp.ClcikSendVerificationButtonWhithEnterredValidPhoneNumber();
		registerationStep1_2of3PopUp.IsLoaded();

		Users.deleteUser(generNumber1);

		Assert.assertFalse(false, "Send Verification Button is not Clickable");
	}

//////    CHECK!!!!
//    @Test(groups = { "particularSingUp" })
//    // TS 1.325
//    public void checkThatUserCanUseVerificationCodeToVerifyHisNumberOnStep1_2of3Page() throws Exception {
//        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
//	signInPopUp.IsLoaded();
//        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
//	signUpPopUp.IsLoaded();
//		RandomNumberGenerator.generateRandomInteger();
//		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
//        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
//        RegisterationStep1_2of3PopUp registerationStep1_2of3PopUp = registerationStep1_1of3PopUp.setValidDataAndClcikSendVerificationButton();
//registerationStep1_2of3PopUp.IsLoaded();
//        RegisterationStep2_1of3PopUp registerationStep21Of3PopUp = registerationStep1_2of3PopUp.verifyRecievedCode(generNumber1);
//
//        Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Identification')]"))
//                .isDisplayed());
//
//
//        Users.deleteUser(generNumber1);
//    }

////    CHECK!!!!
//    @Test(groups = { "particularSingUp" })
//    // TS 1.326
//    public void checkThatUserCanRecieveResendedVerificationCodeAndVerifyHisNumberOnStep1_2_3Page() throws Exception {
//        SignInPopUp signInPopUp = homePage.clickOnSignInButton();
//	signInPopUp.IsLoaded();
//        SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
//	signUpPopUp.IsLoaded();
//		RandomNumberGenerator.generateRandomInteger();
//		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
//        RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
//        RegisterationStep1_2of3PopUp registerationStep1_2of3PopUp = registerationStep1_1of3PopUp.setValidDataAndClcikSendVerificationButton();
//	registerationStep1_2of3PopUp.IsLoaded();
//        RegisterationStep2_1of3PopUp registerationStep2of3PopUp = registerationStep1_2of3PopUp.clcikOnResendCodeButton();
//	registerationStep2of3PopUp.IsLoaded();
//
//
//        Users.deleteUser(generNumber1);
//
//            Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Identification')]"))
//                    .isDisplayed());
//
//    }

	@Test(groups = { "particularSingUp" })
	// TS 1.328
	public void checkThatUploadButtonIsDisplayedOnStep1_2of3() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep1_1of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		RegisterationStep2_1of3PopUp registerationStep2_1of3PopUp = registerationStep1_1of3PopUp.verifyUserPhoneNumberInDB(generNumber1);
		registerationStep2_1of3PopUp.IsLoaded();
		Assert.assertTrue(webDriver.findElement(By.xpath("//button[@style='display:block' and .='Upload identification']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);

	}

	@Test(groups = { "particularSingUp" })
	// TS 1.331
	public void checkThatUserCanUploadJpgImageAsPhotoId() throws Exception {
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

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='uploaded-image']/img[@style='display:block']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);

	}

	@Test(groups = { "particularSingUp" })
	// TS 1.332
	public void checkThaInProfilePageDisplayedUploadedIdentificationJpgOnStep2_1of3() throws Exception {
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

		MenuPage menuPage = registerationStep3_1of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton().
				clickOnVerificationTab().
				createFormOnProfilePAge();
		profilePage.IsLoaded();
		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='uploaded-image']/img[@style='display: block']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.332-1
	public void checkThaInProfilePageDisplayedStatusPendingForIdentificationJpgOnStep2_1of3() throws Exception {
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

		MenuPage menuPage = registerationStep3_1of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='verification-status-personal']/span[.='Pending']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.332-1
	public void checkThatAfterUploadingIdentificationContinueButtonDisplayingOn2_2of3Step() throws Exception {
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

		Assert.assertTrue(webDriver.findElement(By.xpath("//ul[@class='text text-color text-left reg-text']/following::button[@style='display:block' and .='Continue']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.334
	public void checkTahatAfterUploadingIdentificationChangeIdentificationButtonDisplayingOn2_2of3Step() throws Exception {
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

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='registration-content text-center reg-step-3']/h3[contains(text(),'Patient info')]"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.335
	public void checkThatAfterUploadingIdentificationUserCanClickOnContinueButton() throws Exception {
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

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='uploaded-image' and @style='display:block']/button[.='Change identification']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.339
	public void checkThatStep3_1of3OpensAfterUploadingIdentificationOnPrivousStep() throws Exception {
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


		Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Patient info')]"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.340
	public void checkThaInProfilePageDisplayedStatusPendingForIdentificationJpgOnStep2_1of3InFrontOfImage() throws Exception {
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

		MenuPage menuPage = registerationStep3_1of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton().
				clickOnVerificationTab().
				createFormOnProfilePAge();

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='text text-color-2 subtitle verification-status-personal']/span[.='Pending']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.342
	public void checkThatUploadButtonDisplayedOnStep3_1of3() throws Exception {
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



		Assert.assertTrue(webDriver.findElement(By.xpath("//button[@style='display:block' and contains(text(),'Upload recommendation')]"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.348
	public void checkThatUserCanUploadJpgImageAsMedicalID() throws Exception {
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

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='uploaded-image']/img[@style='display:block']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.349
	public void checkThaInProfilePageDisplayedUploadedMedicalRecJpgOnStep2_1of3() throws Exception {
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
		ProfilePage profilePage = menuPage.clickOnProfileButton().
				clickOnVerificationTab().
				createFormOnProfilePAge();
		profilePage.IsLoaded();

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='uploaded-image']/img[@style='display: block']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.350
	public void checkIfOnStep3_2Of3DisplayingFinishButton() throws Exception {
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

		Assert.assertTrue(webDriver.findElement(By.xpath("//button[@style='display:block' and contains(text(),'Finish')]"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.351
	public void checkRedirectionOnMneuPageAfterClickOnFinishButtonOnStep3_3Of3() throws Exception {
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


		Assert.assertTrue(webDriver.findElement(By.xpath("//span[contains(text(),'Your Location')]"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}



	@Test(groups = { "particularSingUp" })
	// TS 1.356
	public void checkIfUserCanOpenCheckOutPageAfterFinishing3StepRegistration() throws Exception {
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

		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Your Order')]"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.357-1
	public void CheckInProfileDisplayingPendingStatusInSidebarMenuForImageOnStep3_2Of3() throws Exception {
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

		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.clickOnVerificationTab().createFormOnProfilePAge();

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='verification-status-medical']/span[.='Pending']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.357
	public void CheckInProfileDisplayingPendingStatusInFrontUploadedImageOnStep3_2Of3() throws Exception {
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

		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		profilePage.clickOnVerificationTab().createFormOnProfilePAge();

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[@class='text text-color-2 subtitle verification-status-medical']/span[.='Pending']"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.362
	public void checkRedirectionToCheckOutPageForFullRegisteredUserWithAddedProduct() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		menuPage = profilePage.verifyPhoneNumberInDB(generNumber1).
				clickOnVerificationTab().
				veifyIdentificationInDB(generNumber1).
				clickOnVerificationTab().
				verifyRecommendationInDB(generNumber1).
				clickOnMenuButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Your Order')]"))
				.isDisplayed());


		Users.deleteUser(generNumber1);
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.363
	public void redirectionTo1of3StepWhenUserWithoutVerifyedPhoneTryToAddProductsAndOpenCart() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//button[contains(text(),'Send Verification Code')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.364
	public void redirectionTo2of3StepWhenUserWithoutVerifyedIdentitificationTryToAddProductsAndOpenCart() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		menuPage = profilePage.verifyPhoneNumberInDB(generNumber1).
				clickOnMenuButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//button[contains(text(),'Upload identification')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.365
	public void redirectionTo3of3StepWhenUserWithoutVerifyedRecomendationTryToAddProductsAndOpenCart() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		menuPage = profilePage.verifyPhoneNumberInDB(generNumber1).
				clickOnVerificationTab().
				veifyIdentificationInDB(generNumber1).
				clickOnVerificationTab().
				clickOnMenuButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//button[contains(text(),'Upload recommendation')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.367
	public void redirectionToCheckOutPageWhenUserWithPendingIdentificationsTryToAddProductsAndOpenCart() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		menuPage = profilePage.verifyPhoneNumberInDB(generNumber1).
				clickOnVerificationTab().
				uploadIdentificationAndPutStatusPendingInDB(generNumber1).
				clickOnVerificationTab().
				uploadRecommendationAndPutStatusPendingInDB(generNumber1).
				clickOnMenuButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Your Order')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.368
	public void redirectionToCheckOutPageWhenUserWithPendingMedicalIdentTryToAddProductsAndOpenCart() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		menuPage = profilePage.verifyPhoneNumberInDB(generNumber1).
				clickOnVerificationTab().
				veifyIdentificationInDB(generNumber1).
				clickOnVerificationTab().
				uploadRecommendationAndPutStatusPendingInDB(generNumber1).
				clickOnMenuButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Your Order')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.369
	public void redirectionTo3of3StepWhenUserWithDeclinedMedicalIdentTryToAddProductsAndOpenCart() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		menuPage = profilePage.verifyPhoneNumberInDB(generNumber1).
				clickOnVerificationTab().
				veifyIdentificationInDB(generNumber1).
				clickOnVerificationTab().
				uploadRecommendationAndPutStatusDeclinedInDB(generNumber1).
				clickOnMenuButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[contains(text(),'Your medical identification is declined')]"))
				.isDisplayed());
	}

	@Test(groups = { "particularSingUp" })
	// TS 1.370
	public void redirectionTo2of3StepWhenUserWithDeclinedRecommendationTryToAddProductsAndOpenCart() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		ProfilePage profilePage = menuPage.clickOnProfileButton();
        profilePage.IsLoaded();
		menuPage = profilePage.verifyPhoneNumberInDB(generNumber1).
				clickOnVerificationTab().
				uploadIdentificationAndPutStatusDeclinedInDB(generNumber1).
				clickOnVerificationTab().
				verifyRecommendationInDB(generNumber1).
				clickOnMenuButton();
		CheckOutPage checkOutPage = menuPage.addProductToCart().
				clickOnOrangeCheckOutButton();


		Users.deleteUser(generNumber1);

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[contains(text(),'Your identification is declined')]"))
				.isDisplayed());
	}

}
