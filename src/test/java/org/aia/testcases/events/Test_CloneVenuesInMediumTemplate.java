package org.aia.testcases.events;

import org.aia.pages.BaseClass;
import org.aia.pages.api.events.EventAPIValidations;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.events.EventInfoModule;
import org.aia.pages.fonteva.events.EventConfig;
import org.aia.pages.fonteva.events.Events;
import org.aia.pages.fonteva.events.NewCloneEvents;
import org.aia.pages.fonteva.events.VenuesEvent;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.VideoRecorder;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

public class Test_CloneVenuesInMediumTemplate extends BaseClass {

	Events events;
	NewCloneEvents cloneEventpage;
	ConfigDataProvider testData;
	EventInfoModule editCloneEvent;
	EventAPIValidations eventApivalidation;
	VenuesEvent venuesEvent;
	boolean recording;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		testData = new ConfigDataProvider();
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				testData.getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		events = PageFactory.initElements(driver, Events.class);
		cloneEventpage = PageFactory.initElements(driver, NewCloneEvents.class);
		editCloneEvent = PageFactory.initElements(driver, EventInfoModule.class);
		eventApivalidation = PageFactory.initElements(driver, EventAPIValidations.class);
		venuesEvent = PageFactory.initElements(driver, VenuesEvent.class);
		recording = Boolean.parseBoolean(testData.testDataProvider().getProperty("videoRecording"));
		Logging.configure();
	}

	@Test(priority = 1, description = "Create Venue PopUp InputField,", enabled = true)
	public void verifyCreateVenuePopUpInputField(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("verifyCreateVenuePopUpInputField");
		}
		events.eventsTab();
		events.clickCreatedEvent("RecentEvents");
		editCloneEvent.clickEditButton();
		util.waitForJavascript(driver, 30000, 5000);
		venuesEvent.navigateIntoVenueModule();
		venuesEvent.navigateAddIntoVenuePopUpTabAndVerifyIt();
		venuesEvent.verifyCreateVenueinputFields();
	}

	@Test(priority = 2, description = "Close Icon And CloseButton Are Working fine", enabled = true, dependsOnMethods = {
			"verifyCreateVenuePopUpInputField" })
	public void VerifyCloseIconAndCloseButtonAreWorkingRelatedToCreateVenuePopUp(ITestContext context)
			throws InterruptedException, Throwable {
		// fec-121
		venuesEvent.ClickOnCrossButtonAndVerifycreateNewVenuepopUpTabIsClosed();
		venuesEvent.navigateAddIntoVenuePopUpTabAndVerifyIt();
		venuesEvent.ClickOnCloseButtonAndVerifycreateNewVenuepopUpTabIsClosed();
	}

	@Test(priority = 3, description = "create New Venue", enabled = true, dependsOnMethods = {
			"verifyCreateVenuePopUpInputField" })
	public void createNewVenue(ITestContext context) throws InterruptedException, Throwable {
		// fec-119
		venuesEvent.navigateAddIntoVenuePopUpTabAndVerifyIt();
		venuesEvent.createNewVenue(EventConfig.descriptionTextField, EventConfig.address,
				EventConfig.venueImageURLInput);
	}

	@Test(priority = 4, description = "Created Venue Name Is displayed In Listing Screen", enabled = true, dependsOnMethods = {
			"verifyCreateVenuePopUpInputField" })
	public void verifyCreatedVenueNameIsdisplayedInListingScreen(ITestContext context)
			throws InterruptedException, Throwable {
		venuesEvent.VerifyTotalCreatedVenueCounts();
		venuesEvent.newlyCreatedVenueIsDisplayedInVenuesListingScreen();
	}

	@Test(priority = 5, description = "Total Created Venues Count", dependsOnMethods = {
			"verifyCreateVenuePopUpInputField" }, enabled = true)
	public void VerifyTotalCreatedVenueCounts(ITestContext context) throws InterruptedException, Throwable {
		// fec-124
		venuesEvent.VerifyTotalCreatedVenueCounts();
	}

	@Test(priority = 6, description = "Edit Venue Name And Verify It", enabled = true, dependsOnMethods = {
			"verifyCreateVenuePopUpInputField" })
	public void editVenueNameAndVerifyIt(ITestContext context) throws InterruptedException, Throwable {
		// fec-122
		venuesEvent.editCreatedVenueNameAndVerifyIt();
	}

	@Test(priority = 7, description = "Delete Venue And Verify It", enabled = true, dependsOnMethods = {
			"verifyCreateVenuePopUpInputField" })
	public void deleteVenueAndVerifyIt(ITestContext context) throws InterruptedException, Throwable {
		// fec-120
		venuesEvent.deleteVenueNameAndVerifyIt();
	}

	@AfterClass(alwaysRun = true)
	public void teardown() {
		if (recording) {
			VideoRecorder.stopRecording();
		}
		if (driver != null) {
			BrowserSetup.closeBrowser(driver);
		}
	}
}
