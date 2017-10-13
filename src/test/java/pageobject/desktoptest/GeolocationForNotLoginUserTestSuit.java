package pageobject.desktoptest;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObject.pages.mmjtraine.*;
import pageObject.pages.pop_ups.RegisterationStep1_1of3PopUp;
import pageObject.pages.pop_ups.SignInPopUp;
import pageObject.pages.pop_ups.SignUpPopUp;
import pageObject.utility.CsvDataProvider;
import pageObject.utility.RandomNumberGenerator;
import pageObject.utility.location.GetLocationExample;
import pageObject.utility.location.mode.ServerLocation;
import pageobject.testcase.TestBase;

import java.util.ArrayList;
import java.util.Map;

public class GeolocationForNotLoginUserTestSuit extends TestBase{
	private static final Logger LOG = LogManager.getLogger(GeolocationForNotLoginUserTestSuit.class);

	@Test(groups = { "geolocation" })
	// TS 1.17
	public void redirectToMenuPageAfterClickOnAutolocationButtonForNotLogginedUser() throws Exception {
		MenuPage menuPage = homePage.clickOnGeolocationButton();

//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[.='" + location +"']"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.35
	public void notLogginedUserGoesToMenuPageWithoutGivingLocation() throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();

		Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'MMJ Train needs your location')]"))
				.isDisplayed());
	}

	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "geolocation" })
	// TS 1.36
	public void notLogginedUserSetHisGeolocationManualyFromHomePage(Map<String,String> addressItemsList) throws Exception {
		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult(addressItemsList);

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[contains(text(),'" + addressItemsList.get("Places") + "')]"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.37
	public void notLogginedUserGiveHisAutoLocationAndRedirectsToMenuPage() throws Exception {
		MenuPage menuPage = homePage.clickOnGeolocationButton();

//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[.='" + location +"']"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.44
	public void notLogginedUserGivesGeolocationManuallyFromMessage2Page() throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();
		Messages3Page messages3Page = messages1Page.clickOnIWillEnterMyLocationManuallyButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//h3[contains(text(),'Please enter your location')]"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.45
	public void notLogginedUserGivesGeolocationAutomaticalyFromMessage1Page() throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();
		MenuPage menuPage = messages1Page.clickOnOkIUnderstandButton();

//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[.='" + location +"']"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.52
	public void notLogginedUserTryToDetectHisLocationAutomaticalyFromMessage3Page() throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();
		Messages3Page messages3Page = messages1Page.clickOnIWillEnterMyLocationManuallyButton();
		MenuPage menuPage = messages3Page.clickOnTryDetectAgainButton();

//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[.='" + location +"']"))
			.isDisplayed());
	}

	@Test(dataProvider = "CsvDataProvider", dataProviderClass = CsvDataProvider.class, groups = { "geolocation" })
	// TS 1.62
	public void notLogginedUserSetHisGeolocationManualyFromMessage3Page(Map<String,String> addressItemsList) throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();
		Messages3Page messages3Page = messages1Page.clickOnIWillEnterMyLocationManuallyButton();
		MenuPage menuPage = messages3Page.setPlaceNameInAddressFielsAndSelectResult(addressItemsList);

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[contains(text(),'" + addressItemsList.get("Places") + "')]"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.79
	public void notLogginedUserGoesToMessage5PageAfterClickOnYourLocationButtonAtMenuPage() throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();
		MenuPage menuPage = messages1Page.clickOnOkIUnderstandButton();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//a[contains(text(),'Continue with my current location')]"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.80
	public void notLogginedUserMakeClickAtContinueWithMyCurrentLocationButtonAtMessage5Page() throws Exception {
		MenuPage menuPage = homePage.clickOnGeolocationButton();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.clickOnContinueWithMyCurrentLocationButton();

//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");

		Assert.assertTrue(webDriver.findElement(By.xpath("//p[.='" + location +"']"))
			.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.81
	public void notLogginedUserMakeClickAtContinueWithCurrentLocationButtonAtMessage5Page() throws Exception {
		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.clickOnContinueWithMyCurrentLocationButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location']/p[.='Lviv']"))
				.isDisplayed());
	}

//	@Test(groups = { "geolocation" })
//	// TS 1.83
//	public void checkDisplayingTheSameYourLocationNameOnMessage5PageAndMenuPage() throws Exception {
//		MenuPage menuPage = homePage.clickOnGeolocationButton();
//		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//
//		WebElement elem1 = webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location current-location standard-input text-left']/p/text()"));
//		elem1.getText();
//
//		String locMessage5;
//
//		menuPage = messages5Page.clickOnYourLocationButton();
//
//		WebElement elem2 = (webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location']/p/text()")));
//		String locMenu = elem2.getText();
//
//		if (locMessage5 == locMenu)
//				Assert.assertTrue(true);
//	}

	@Test(groups = { "geolocation" })
	// TS 1.84
	public void checkIfNotLogginedUserCanChangeHisCurrentLocationToTheSame() throws Exception {
		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		messages5Page.setPlaceInAddressField("Lviv");

		try {
			webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='Lviv']"));
			LOG.info("Element Present");
			Assert.assertTrue(false);
		} catch (NoSuchElementException e) {
			LOG.info("Element absent");
			Assert.assertTrue(true);
		}

	}

	@Test(groups = { "geolocation" })
	// TS 1.85
	public void notLogginedUserMakeClickOnYourLocationButtonOnMessage5() throws Exception {
		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.clickOnYourLocationButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location']/p[.='Lviv']"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
//	 TS 1.86-1
	public void checkThatInRecentBlockDoesntDisplaysMoreThan5LocationItems() throws Exception {
		MenuPage menuPage = homePage.clickOnGeolocationButton();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("San Francisco");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Berlin");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();

		//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");
		String loc = location.toString();

		String [] recentCities = {loc, "Liverpool", "Anniston", "Atmore", "Athens", "San Francisco"};

		try {
			for (int i = 0; i < 6; i++)
			webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='" + recentCities[i] + "']")).isDisplayed();
			LOG.info("Elements Present");
			Assert.assertTrue(false);
		} catch (NoSuchElementException e) {
			LOG.info("Elements absent");
			Assert.assertTrue(true);
		}

	}

	@Test(groups = { "geolocation" })
//	 TS 1.88
	public void checkFiveRecentItemsAddedOneFromMessage3And4FromMessage5() throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();
		Messages3Page messages3Page = messages1Page.clickOnIWillEnterMyLocationManuallyButton();
		MenuPage menuPage = messages3Page.setPlaceNameInAddressFielsAndSelectResult("Lviv");
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();

		String [] recentCities = {"Lviv", "Liverpool", "Anniston", "Atmore"};

		for (int i=0; i<5; i++){
			Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='" + recentCities[i] + "']"))
					.isDisplayed());
		}
	}

	@Test(groups = { "geolocation" })
//	 TS 1.89
	public void fiveLocationItemsInRecentBlockAtMessage5PageAddedManualyFromMessage5pageAnd1FromHomePage() throws Exception {
		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("San Francisco");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();

		String[] recentCities = {"Lviv", "Liverpool", "Anniston", "Atmore", "Athens"};

		for (int i = 0; i < 5; i++) {
			Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='" + recentCities[i] + "']"))
					.isDisplayed());
		}
	}

	@Test(groups = { "geolocation" })
//	 TS 1.90
	public void fiveLocationItemsInRecentBlockAtMessage5PageAddedManualyFromMessage5page() throws Exception {
		MenuPage menuPage = homePage.clickOnGeolocationButton();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
			menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
			menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
			menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
			menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("San Francisco");
			menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();

		//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");
		String loc = location.toString();

			String [] recentCities = {loc, "Liverpool", "Anniston", "Atmore", "Athens"};

			for (int i=0; i<5; i++){
			Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='" + recentCities[i] + "']"))
					.isDisplayed());
		}
	}

	@Test(groups = { "geolocation" })
//	 TS 1.91
	public void checkFiveRecentItemsAddedOneByAutolocationFromMessage3And4FromMessage5() throws Exception {
		Messages1Page messages1Page = homePage.openMenuPageWithoutGivingLocation();
		Messages3Page messages3Page = messages1Page.clickOnIWillEnterMyLocationManuallyButton();
		MenuPage menuPage = messages3Page.clickOnTryDetectAgainButton();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Berlin");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();

		//		Use your current ip address for identificate your location
		GetLocationExample obj = new GetLocationExample();
		ServerLocation location = obj.getLocation("217.20.186.161");
		String loc = location.toString();

		String [] recentCities = {loc, "Liverpool", "Anniston", "Atmore", "Athens"};

		for (int i=0; i<5; i++){
			Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='" + recentCities[i] + "']"))
					.isDisplayed());
		}
	}

	@Test(groups = { "geolocation" })
//	 TS 1.95
	public void checkThatInRecentBlockWouldNotBeDisplayedMoreThan1SameItems() throws Exception {
		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Lviv");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Lviv");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Lviv");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();

		try {
			ArrayList <WebElement> myTagsWithId = new ArrayList<WebElement>();
				myTagsWithId.add(webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='Lviv']")));

			for(int i =0; i < myTagsWithId.size(); i++) {
				if (i >= 1) {
					LOG.info("Present more more than 1 element" + i );
					Assert.assertTrue(false);
				}
			}
		} catch (NoSuchElementException e) {
			LOG.info("Elements absent");
			Assert.assertTrue(true);
		}

	}

	@Test(groups = { "geolocation" })
	// TS 1.94
	public void checkIfAtMenuPageDisplaysUserLocationThatHeSatManualyAtMessage5Page() throws Exception {
		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location']/p[.='Liverpool']"))
				.isDisplayed());
	}

	@Test(groups = { "geolocation" })
	// TS 1.96
	public void checkThatForListOfLocationAfterLogOutDoesntDisplays() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Berlin");
		menuPage.IsLoaded();
		menuPage.clickOnProfileButton().clickOnLogOutButton();
		menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		messages5Page = menuPage.clcikOnYourLocationButton();

		try {
			webDriver.findElement(By.xpath("//div[@class='recent-locations']/input"))
					.isDisplayed();
			LOG.info("Wrong location items displayed");
			Assert.assertTrue(false);
		} catch (NoSuchElementException e) {
			LOG.info("Elements absent");
			Assert.assertTrue(true);
		}

	}

	@Test(groups = { "geolocation" })
	// TS 1.96
	public void checkThatLocationsforLogginedUserWithListOfLocationsInRecentBlockAfterLogOutAndLoggingDisplayed() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Berlin");
		menuPage.IsLoaded();
		menuPage.clickOnProfileButton().clickOnLogOutButton();
		menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.clickOnContinueWithMyCurrentLocationButton();
		signInPopUp = menuPage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		menuPage = signInPopUp.signInUser1ValidDatA(generNumber1);
		messages5Page = menuPage.clcikOnYourLocationButton();

		String [] recentCities = {"Liverpool", "Anniston", "Atmore", "Athens", "Berlin"};

		try {
			for (int i = 0; i < 5; i++)
				webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='" + recentCities[i] + "']")).isDisplayed();
			LOG.info("Elements Present");
			Assert.assertTrue(true);
		} catch (NoSuchElementException e) {
			LOG.info("Elements absent");
			Assert.assertTrue(false);
		}

	}

	@Test(groups = { "geolocation" })
	// TS 1.96-1
	public void checkThatLocationInYourLocationElementforLogginedUserWithListOfLocationsInRecentBlockAfterLogOutAndLoggingDisplayed() throws Exception {
		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
		signUpPopUp.IsLoaded();
		RandomNumberGenerator.generateRandomInteger();
		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.signUpUser1ValidDatA(generNumber1);
		MenuPage menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
		menuPage.IsLoaded();
		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Anniston");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Atmore");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Athens");
		menuPage.IsLoaded();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.setPlaceInAddressField("Berlin");
		menuPage.IsLoaded();
		menuPage.clickOnProfileButton()
				.clickOnLogOutButton();
		menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
		messages5Page = menuPage.clcikOnYourLocationButton();
		menuPage = messages5Page.clickOnContinueWithMyCurrentLocationButton();
		signInPopUp = menuPage.clickOnSignInButton();
		signInPopUp.IsLoaded();
		menuPage = signInPopUp.signInUser1ValidDatA(generNumber1);
		menuPage.IsLoaded();
		// Thread.sleep(1000);
		menuPage.clcikOnYourLocationButton();

		Assert.assertTrue(webDriver.findElement(By.xpath("//div[@class='btn-main btn-border user-location current-location standard-input text-left']/p[.='Lviv']"))
				.isDisplayed());
	}

//	inProgress. Dosnt work log in / sign up
//		@Test(groups = { "geolocation" })
//	// TS 1.97
//	public void checkThatNotLogginedUserCanAdd5LocationsThanMakeSignUpAndWilDisplaysStil5() throws Exception {
//		MenuPage menuPage = homePage.setPlaceNameInAddressFielsAndSelectResult();
//		Messages5Page messages5Page = menuPage.clcikOnYourLocationButton();
//		menuPage = messages5Page.setPlaceInAddressField("Liverpool");
//		menuPage.IsLoaded();
//		messages5Page = menuPage.clcikOnYourLocationButton();
//		menuPage = messages5Page.setPlaceInAddressField("Anniston");
//		menuPage.IsLoaded();
//		messages5Page = menuPage.clcikOnYourLocationButton();
//		menuPage = messages5Page.setPlaceInAddressField("Atmore");
//		menuPage.IsLoaded();
//		messages5Page = menuPage.clcikOnYourLocationButton();
//		menuPage = messages5Page.setPlaceInAddressField("Athens");
//		menuPage.IsLoaded();
//		messages5Page = menuPage.clcikOnYourLocationButton();
//		menuPage = messages5Page.setPlaceInAddressField("Berlin");
//		menuPage.IsLoaded();
//		HomePage homePage = menuPage.openHomePage();
//		SignInPopUp signInPopUp = homePage.clickOnSignInButton();
//		signInPopUp.IsLoaded();
//		SignUpPopUp signUpPopUp = signInPopUp.clickOnSignUpButton();
//		signUpPopUp.IsLoaded();
//		RandomNumberGenerator.generateRandomInteger();
//		String generNumber1 = RandomNumberGenerator.getGeneratedNumber();
//		signUpPopUp.signUpUser1ValidDatA(generNumber1);
//		RegisterationStep1_1of3PopUp registerationStep11Of3PopUp = signUpPopUp.refreshSignUpPopUp();
//		signInPopUp = homePage.clickOnSignInButton();
//		signInPopUp.IsLoaded();
//		signInPopUp.signInUser1ValidDatA(generNumber1);
//		registerationStep11Of3PopUp.IsLoaded();
//		menuPage = registerationStep11Of3PopUp.clickOnCloseButton();
//		menuPage.IsLoaded();
//		messages5Page = menuPage.clcikOnYourLocationButton();
//
//		String [] recentCities = {"Liverpool", "Anniston", "Atmore", "Athens", "Berlin"};
//
//		try {
//			for (int i = 0; i < 6; i++)
//				webDriver.findElement(By.xpath("//div[@class='recent-locations']/input[@value='" + recentCities[i] + "']")).isDisplayed();
//			LOG.info("Elements Present");
//			Assert.assertTrue(true);
//		} catch (NoSuchElementException e) {
//			LOG.info("Elements absent");
//			Assert.assertTrue(false);
//		}
//
//
//		Users.deleteUser(generNumber1);
//	}

}
