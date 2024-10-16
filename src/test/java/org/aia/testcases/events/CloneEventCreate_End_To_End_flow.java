package org.aia.testcases.events;

import java.io.IOException;
import java.util.ArrayList;
import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.events.EventAPIValidations;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.events.EventRegistration;
import org.aia.pages.events.ViewRecipts;
import org.aia.pages.fonteva.events.AgendaModule;
import org.aia.pages.fonteva.events.EventConfig;
import org.aia.pages.fonteva.events.EventInfoModule;
import org.aia.pages.fonteva.events.Events;
import org.aia.pages.fonteva.events.NewCloneEvents;
import org.aia.pages.fonteva.events.PagesModule;
import org.aia.pages.fonteva.events.QuickLinksInEvents;
import org.aia.pages.fonteva.events.SpeakersModule;
import org.aia.pages.fonteva.events.StatusesModule;
import org.aia.pages.fonteva.events.TicketModule;
import org.aia.pages.fonteva.events.VenuesEvent;
import org.aia.pages.membership.CheckYourEmailPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpPage;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.aia.utility.VideoRecorder;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


public class CloneEventCreate_End_To_End_flow extends BaseClass {
	Events events;
	NewCloneEvents cloneEventpage;
	ConfigDataProvider testData;
	EventInfoModule eventInfoModule;
	SignUpPage signUpPage;
	MailinatorAPI mailinator;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	EventRegistration eventRegistration;
	ViewRecipts viewReceipts;
	EventAPIValidations eventApivalidation;
	QuickLinksInEvents linksInEvents;
	TicketModule ticketModule;
	VenuesEvent venuesModule;
	SpeakersModule speakersModule;
	AgendaModule agendaModule;
	StatusesModule statusModule;
	PagesModule pagesModule;
	boolean recording;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		testData = new ConfigDataProvider();
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				testData.getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		events = PageFactory.initElements(driver, Events.class);
		cloneEventpage = PageFactory.initElements(driver, NewCloneEvents.class);
		eventInfoModule = PageFactory.initElements(driver, EventInfoModule.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		linksInEvents = PageFactory.initElements(driver, QuickLinksInEvents.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		eventRegistration = PageFactory.initElements(driver, EventRegistration.class);
		viewReceipts = PageFactory.initElements(driver, ViewRecipts.class);
		eventApivalidation = PageFactory.initElements(driver, EventAPIValidations.class);
		ticketModule = PageFactory.initElements(driver, TicketModule.class);
		venuesModule = PageFactory.initElements(driver, VenuesEvent.class);
		speakersModule = PageFactory.initElements(driver, SpeakersModule.class);
		agendaModule = PageFactory.initElements(driver, AgendaModule.class);
		statusModule = PageFactory.initElements(driver, StatusesModule.class);
		pagesModule = PageFactory.initElements(driver, PagesModule.class);
		recording = Boolean.parseBoolean(testData.testDataProvider().getProperty("videoRecording"));
		Logging.configure();
	}

	@Test(priority = 1, description = "Create a Valid Clone event (End to End Process)", enabled = false, groups = {
	"Smoke" })
	public void CreateCloneEvent_End_To_End_Flow(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("CreateCloneEvent_End_To_End_Flow");
		}
		String exitEvent = testData.testDataProvider().getProperty("upgrade_CloneEventTemplate");
		events.eventsTab();
		events.eventsTab();
		util.waitForJavascript(driver, 30000, 5000);
		events.newButtonInEvents();
		events.validateHeaderCloneEvent();
		cloneEventpage.validateCloneOnExistingRadioButton();
		cloneEventpage.enterEventName("Medium");
		cloneEventpage.enterStartDate();
		cloneEventpage.selectEventCategory(testData.testDataProvider().getProperty("eventCategory"));
		cloneEventpage.CloneEventSearchTemplate(exitEvent);
		cloneEventpage.eventCloneButton();
		cloneEventpage.verifyCloneEventSegmentCheckBoxs();
		cloneEventpage.eventFinishCloneButton();
		util.waitForJavascript(driver, 20000, 5000);
		cloneEventpage.validateEventHeader();
		String eventId = cloneEventpage.getEventId();
		util.waitForJavascript(driver, 90000, 5000);
		context.setAttribute("eventId", eventId);
		context.setAttribute("eventName", EventConfig.getEventName);
		context.setAttribute("startDate", cloneEventpage.startDate);
		context.setAttribute("eventCategory", testData.testDataProvider().getProperty("eventCategory"));
		// Create Clone event validation
		eventApivalidation.verifyEvent(context);
		events.eventsTab();
		util.waitForJavascript(driver, 20000, 5000);
		String eventName = events.clickCreatedEvent("RecentEvents");
		eventInfoModule.clickEditButton();
		String eventTimeZone = testData.testDataProvider().getProperty("eventTimeZone");
		String registrationTime = testData.testDataProvider().getProperty("registrationTimer");
		String startTime = testData.testDataProvider().getProperty("eventStartTimeInMediumTemplate");
		String endTime = testData.testDataProvider().getProperty("eventEndTimeInMediumTemplate");
		util.waitForJavascript(driver, 40000, 5000);
		// Event info
		eventInfoModule.editEventInfo(eventName, startTime, endTime, registrationTime, eventTimeZone);
		eventInfoModule.enterEventOverview();
		eventInfoModule.enterDescriptionInEventInfo();
		eventInfoModule.enterwhenWhereSummaryInEventInfo();
		eventInfoModule.selecteventDisplayNameAndDT();
		eventInfoModule.selectisFeaturedEvent();
		eventInfoModule.selecttimeZoneIneventInfo(eventTimeZone);
		// tickets tab
		ticketModule.eventTicketsTab();
		util.waitForJavascript(driver, 8000, 2000);
		ticketModule.validateEventTicketSalesStartDate();
		//fec-194
		//ticketModule.verifyUserAbleToProvidedateIntoTicketSalesStartDate();
		util.waitForJavascript(driver, 5000, 2000);
		ticketModule.editEventTicket(true);
		ticketModule.validateEditTicketTypeHeader();
		ticketModule.enterPriceInCreateTicketType("5.00");
		ticketModule.saveAndContinueButtonInTicketType();
		ticketModule.clickNewTicketType();
		ticketModule.publishedCheckBox();
		ticketModule.activeCheckBox();
		ticketModule.enterTicketName();
		ticketModule.enterPriceInCreateTicketType("50.00");
		ticketModule.restrictQuantityCheckBox();
		ticketModule.enterDescriptionInCreateTicketType();
		ticketModule.buttonsInCreateTicketType("SaveContinue");
		//venues tab
		util.waitForJavascript(driver, 8000, 2000);
		venuesModule.navigateIntoVenueModule();
		venuesModule.clickAddVenue();
		venuesModule.createNewVenue(EventConfig.descriptionTextField, EventConfig.address,
				EventConfig.venueImageURLInput);
		// speakers tab
		util.waitForJavascript(driver, 8000, 2000);
		speakersModule.eventSpeakersTab();
		speakersModule.clickNewSpeaker();
		speakersModule.enterSpeakerName();
		speakersModule.contactRecordsInNewSpeakerPopup();
		speakersModule.featuredRadioButton();
		speakersModule.enterTitleInSpeaker();
		speakersModule.enterCompanyInSpeaker();
		speakersModule.selectStatusInSpeakers();
		speakersModule.speakerPhotoUrlBrowser();
		speakersModule.cropImageButtonsInSpeaker();
		speakersModule.verifySpeakerBio();
		speakersModule.verifyFaceBookURL();
		speakersModule.verifyLinkedURL();
		speakersModule.verifyTwitterURL();
		speakersModule.speakerButtonsInnewSpeakerPopup();
		// agenda tab
		util.waitForJavascript(driver, 10000, 2000);
		agendaModule.clickEventAgenda();
		agendaModule.clickNewScheduleItem();
		agendaModule.validateActiveCheckBoxInScheduleItem();
		agendaModule.entersceduleItemName();
		agendaModule.entercapacityInscheduleItem();
		agendaModule.enterdisplayNameInscheduleItem();
		agendaModule.enterpriceInscheduleItem();
		agendaModule.enterstartDateInscheduleItem();
		agendaModule.selectstartTimeInscheduleItem();
		agendaModule.selectstartMinInScheduleItem();
		agendaModule.selectstartAmPmSceduleItem();
		agendaModule.enterDurationHourInScheduleItem();
		agendaModule.selectDurationMinInScheduleItem();
		agendaModule.selectallowConflictsInScheduleItem();
		agendaModule.enterspeakerInScheduleItem();
		agendaModule.enterdescriptionInscheduleItem();
		agendaModule.clickbuttonsInScheduleItem();
		util.waitForJavascript(driver, 30000, 5000);
		// status tab
		statusModule.editEventStatuses();
		util.waitForJavascript(driver, 8000, 2000);
		statusModule.clicknewStatuses();
		statusModule.enterstatusName();
		statusModule.selectCheckboxForRegistration();
		statusModule.selectCheckboxForPublishPortal();
		statusModule.clicksaveCloseButtonInNewStatus();
//		// pages tab
		util.waitForJavascript(driver, 10000, 2000);
		pagesModule.eventPagesTab();
		pagesModule.multiOptionsInPages("Delete");
		pagesModule.clickNewPage();
		pagesModule.downButtonInRegColumnInPage();
		pagesModule.enterNavigationLabelName();
		pagesModule.enterBrowserLabelName();
		pagesModule.SelectCheckboxForPublished();
		pagesModule.clickOnActions();
		pagesModule.clickManageComopnents();
		util.waitForJavascript(driver, 10000, 2000);
		pagesModule.selectEventSSO();
		pagesModule.validate_AddedEventOne();
		pagesModule.selectEventRegistration();
		pagesModule.validate_AddedEventTwo();
		pagesModule.selectEventFooter();
		pagesModule.validate_AddedEventThird();
		pagesModule.clickSaveCloseButtonInPageModule();
		// active
		//The Event Builder header with options 'Refresh', 'Preview In', 'Status','Edit in salesforce' and Edit' is not getting displayed.
		//Bug Id:- FEC-194
		eventInfoModule.selectActiveStatus();
		eventInfoModule.saveExitButton();
		util.waitForJavascript(driver, 30000, 5000);
		eventInfoModule.clickEventUrl();
		util.waitForJavascript(driver, 30000, 5000);
		// AIA application
		util.switchToTabs(driver, 1);
		eventRegistration.speakersButtonInAIA();
		eventRegistration.detailsNavButton();
		eventRegistration.validateEventOverView();
		eventRegistration.agendaNavigationButtonInAIA();
		eventRegistration.validateScheduleInAgenda();
		eventRegistration.speakersButtonInAIA();
		eventRegistration.validateSpeakerInAIA();
		// sometimes Register link is not clicked in AIA application
		eventRegistration.RegisterLink(1);
		util.waitForJavascript(driver, 30000, 5000);
		signInpage.signUp();
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		util.switchToTabs(driver, 1);
		util.navigateToURl(driver, DataProviderFactory.getConfig().getValue("fonteva_sign_in"));
		signInpage.login(dataList.get(5), dataList.get(6));
		util.waitForJavascript(driver, 30000, 5000);
		String aiaNumber = eventRegistration.getAIAData();
		util.switchToTabs(driver, 0);
		eventInfoModule.clickEventUrl();
		// sometimes Register link is not clicked in AIA application
		eventRegistration.RegisterLink(3);
		util.waitForJavascript(driver, 30000, 5000);
		eventRegistration.selectTicketQuantity();
		eventRegistration.clickRegisterButton();
		eventRegistration.validateRegisterReq();
		eventRegistration.clickRegistrationButton();
		eventRegistration.clickRegistrationButton();
		eventRegistration.getScheduleDetailsInAgenda();
		eventRegistration.agendaModule();
		util.waitForJavascript(driver, 10000, 2000);
		eventRegistration.totalPaymentamountInCheckout();
		// Here we getting receipt data from UI and storing in ArrayList
		eventRegistration.paymentDataIncheckoutModule();
		eventRegistration.biilingaddressInCheckoutModule();
		eventRegistration.paymentProcessButton();
		ArrayList<Object> receiptData = eventRegistration.clickReceiptInChecout();
		util.waitForJavascript(driver, 90000, 5000);
		// Here we validate PDF data
		String paymentType = testData.testDataProvider().getProperty("PaymentType");
		String paymentMethodDescr = testData.testDataProvider().getProperty("PaymentMethodDescription");
		viewReceipts.viewReceiptValidationsForEvents(receiptData.get(1), receiptData.get(0), paymentType,
				paymentMethodDescr, aiaNumber);
		// Here we validate the receipt using API call
		eventApivalidation.verifyReciptDetails(dataList.get(3), receiptData.get(1), receiptData.get(0));
		// Here we validate the Sales order using API call
		eventApivalidation.verifySalesOrder(dataList.get(3),
				DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData.get(0),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Email validations session confirm message
		mailinator.sessionConfirmationEmailforEvents(dataList, eventName);
		// Email validations registration confirm message
		// Note:- Sometimes API body returning as null
		mailinator.registrationConfirmationEmailforEvents(dataList, eventName);
	}

	@AfterMethod(alwaysRun = true)
	public void teardown(ITestResult result) throws IOException {
		if (recording) {
			VideoRecorder.stopRecording();
		}
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("LOG : FAIL Test failed to executed");
			Utility.takeScreenShotAfterFail(driver, result);
		}
		BrowserSetup.closeBrowser(driver);
	}
}