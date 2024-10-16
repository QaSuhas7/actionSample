package org.aia.testcases.events;

import java.io.IOException;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.events.EventAPIValidations;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.events.EventRegistration;
import org.aia.pages.events.ViewRecipts;
import org.aia.pages.fonteva.events.EventInfoModule;
import org.aia.pages.fonteva.events.EventConfig;
import org.aia.pages.fonteva.events.Events;
import org.aia.pages.fonteva.events.NewCloneEvents;
import org.aia.pages.fonteva.events.QuickLinksInEvents;
import org.aia.pages.fonteva.events.TicketModule;
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
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;



public class Test_Clone_EventTemplateMedium extends BaseClass {

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
	boolean recording;
	ArrayList<Object> receiptData;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		try {
			testData = new ConfigDataProvider();
			sessionID = new FontevaConnectionSOAP();
			driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
					DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
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
			recording = Boolean.parseBoolean(testData.testDataProvider().getProperty("videoRecording"));
			Logging.configure();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@Test(priority = 1, description = "Create New CloneEvent enter event name, enter date, select event category and event search click clone button", enabled = true)
	public void test_CreateCloneEventMediumTemplate(ITestContext context) throws Throwable {
		Logging.logger.info(
				"================================test_CreateCloneEventMediumTemplate started==========================");
		try {
			String exitEvent = testData.testDataProvider().getProperty("upgrade_CloneEventTemplate");
			if (recording) {
				VideoRecorder.startRecording("test_CreateCloneEvent");
			}
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
			cloneEventpage.getEventId();
			util.waitForJavascript(driver, 30000, 5000);

			context.setAttribute("eventId", cloneEventpage.eventId);
			context.setAttribute("eventName", EventConfig.getEventName);
			context.setAttribute("startDate", cloneEventpage.startDate);
			context.setAttribute("eventCategory", testData.testDataProvider().getProperty("eventCategory"));
			// Create Clone event validation
			eventApivalidation.verifyEvent(context);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@Test(priority = 2, description = "Verify Price modify for an existing Event", enabled = true)
	public void test_EditPriceInCloneEventMediumTEmplate(ITestContext context) throws InterruptedException, Throwable {
		Logging.logger
				.info("================================test_EditPriceInCloneEvent started==========================");
		try {
			if (recording) {
				VideoRecorder.startRecording("test_EditPriceInCloneEvent");
			}
			events.eventsTab();
			util.waitForJavascript(driver, 20000, 5000);
			String eventName = events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			util.waitForJavascript(driver, 30000, 5000);
			String eventTimeZone = testData.testDataProvider().getProperty("eventTimeZone");
			String registrationTime = testData.testDataProvider().getProperty("registrationTimer");
			String startTime = testData.testDataProvider().getProperty("eventStartTimeInMediumTemplate");
			String endTime = testData.testDataProvider().getProperty("eventEndTimeInMediumTemplate");
			eventInfoModule.editEventInfo(eventName, startTime, endTime, registrationTime, eventTimeZone);
			// tickets tab
			ticketModule.eventTicketsTab();
			ticketModule.validateEventTicketSalesStartDate();
			//// fec-194
			// ticketModule.verifyUserAbleToProvidedateIntoTicketSalesStartDate();
			ticketModule.editEventTicket(true);
			ticketModule.validateEditTicketTypeHeader();
			ticketModule.enterPriceInCreateTicketType("10.00");
			ticketModule.saveAndContinueButtonInTicketType();
			util.waitForJavascript(driver, 30000, 5000);
			eventInfoModule.selectActiveStatus();
			eventInfoModule.saveExitButton();
			util.waitForJavascript(driver, 30000, 5000);
			eventInfoModule.clickEventUrl();
			util.waitForJavascript(driver, 30000, 5000);
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(1);
			util.waitForJavascript(driver, 90000, 5000);
			signInpage.signUp();
			ArrayList<String> dataList = signUpPage.signUpData();
			signUpPage.signUpUser();
			mailinator.verifyEmailForAccountSetup(dataList.get(3));
			util.switchToTabs(driver, 1);
			util.navigateToURl(driver, DataProviderFactory.getConfig().getValue("signinUpgradeStg"));
			signInpage.login(dataList.get(5), dataList.get(6));
			util.waitForJavascript(driver, 20000, 2000);
			// String aiaNumber = eventRegistration.getAIAData();
			util.switchToTabs(driver, 0);
			events.globalSearch(dataList.get(5));
			String aiaNumber = events.clickContactsInGlobalSearch();
			events.eventsTab();
			events.eventsSearch(EventConfig.getEventName);
			eventInfoModule.clickEventUrl();
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(3);
			util.waitForJavascript(driver, 30000, 5000);
			eventRegistration.validateFirstNameInRegistartion();
			eventRegistration.validateLastNameInRegistartion();
			eventRegistration.ValidateEmailInRegistartion();
			eventRegistration.singleticketRegistratioButton();
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
			// Note:- Sometimes API body returning as null
			mailinator.sessionConfirmationEmailforEvents(dataList, eventName);

			// Email validations registration confirm message
		    mailinator.registrationConfirmationEmailforEvents(dataList, eventName);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}

	}

	@Test(priority = 3, description = "Verify Future date modify for an existing Event", enabled = true)
	public void test_EditFutureDateCloneEventMediumTEmplate(ITestContext context) throws InterruptedException, Throwable {
		Logging.logger
				.info("================================test_EditFutureDateCloneEventMediumTEmplate started==========================");
		try {
			if (recording) {
				VideoRecorder.startRecording("test_EditFutureDateCloneEventMediumTEmplate");
			}
			events.eventsTab();
			util.waitForJavascript(driver, 20000, 5000);
			String eventName = events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			eventInfoModule.enterStartDateInEventInfo();
			//fec-194
			//enter future date in schedule itms in agenda module
//			agendaModule.clickEventAgenda();
//			util.waitForJavascript(driver, 30000, 5000);
//			agendaModule.agendaMoreOptionButton1InScheduleItem();
//			agendaModule.enterFuturestartDateInscheduleItem();
//			agendaModule.clickbuttonsInScheduleItem();
//			agendaModule.agendaMoreOptionButton2InScheduleItem();
//			agendaModule.enterFuturestartDateInscheduleItem();
//         	agendaModule.clickbuttonsInScheduleItem();
//			util.waitForJavascript(driver, 10000, 5000);
			eventInfoModule.saveExitButton();
			util.waitForJavascript(driver, 30000, 5000);
			eventInfoModule.clickEventUrl();
			util.waitForJavascript(driver, 30000, 5000);
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(1);
			util.waitForJavascript(driver, 90000, 5000);
			signInpage.signUp();
			ArrayList<String> dataList = signUpPage.signUpData();
			signUpPage.signUpUser();
			mailinator.verifyEmailForAccountSetup(dataList.get(3));
			util.switchToTabs(driver, 1);
			util.navigateToURl(driver, DataProviderFactory.getConfig().getValue("signinUpgradeStg"));
			signInpage.login(dataList.get(5), dataList.get(6));
			util.waitForJavascript(driver, 20000, 2000);
			util.switchToTabs(driver, 0);
			eventInfoModule.clickEventUrl();
			util.switchToTabs(driver, 3);
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(3);
			util.waitForJavascript(driver, 30000, 5000);
			eventRegistration.validateFirstNameInRegistartion();
			eventRegistration.validateLastNameInRegistartion();
			eventRegistration.ValidateEmailInRegistartion();
			eventRegistration.singleticketRegistratioButton();
			eventRegistration.agendaModule();
			util.waitForJavascript(driver, 10000, 2000);
			eventRegistration.totalPaymentamountInCheckout();
			eventRegistration.paymentDataIncheckoutModule();
			eventRegistration.biilingaddressInCheckoutModule();
			eventRegistration.paymentProcessButton();
			ArrayList<Object> receiptData = eventRegistration.clickReceiptInChecout();
			util.waitForJavascript(driver, 90000, 5000);
			// Here we validate the receipt using API call
			eventApivalidation.verifyReciptDetails(dataList.get(3), receiptData.get(1), receiptData.get(0));
 
			// Email validations session confirm message
			// Note:- Sometimes API body returning as null
			mailinator.validateFutureDatesessionConfirmationEmailforEvents(dataList, eventName, EventInfoModule.futureDate);
			

		}catch (Exception e) {
			// TODO: handle exception
		}

}

	@Test(priority = 4, description = "Verify 'Attendees' info after registering for the event", enabled = true)
	public void validate_AttendeesMediumTEmplates(ITestContext context) throws InterruptedException, Throwable {
		Logging.logger.info("================================validate_Attendees started==========================");
		try {
			if (recording) {
				VideoRecorder.startRecording("validate_Attendees");
			}
			events.eventsTab();
			util.waitForJavascript(driver, 20000, 5000);
			String eventName = events.clickCreatedEvent("RecentEvents");
			events.validateBeforeRegistrationData();
			eventInfoModule.clickEventUrl();
			util.waitForJavascript(driver, 30000, 5000);
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(1);
			util.waitForJavascript(driver, 30000, 5000);
			signInpage.signUp();
			ArrayList<String> dataList = signUpPage.signUpData();
			signUpPage.signUpUser();
			mailinator.verifyEmailForAccountSetup(dataList.get(3));
			util.switchToTabs(driver, 1);
			util.navigateToURl(driver, DataProviderFactory.getConfig().getValue("signinUpgradeStg"));
			signInpage.login(dataList.get(5), dataList.get(6));
			util.waitForJavascript(driver, 20000, 2000);
			String aiaNumber = eventRegistration.getAIAData();
			util.switchToTabs(driver, 0);
			eventInfoModule.clickEventUrl();
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(3);
			util.waitForJavascript(driver, 30000, 5000);
			eventRegistration.validateFirstNameInRegistartion();
			eventRegistration.validateLastNameInRegistartion();
			eventRegistration.ValidateEmailInRegistartion();
			eventRegistration.singleticketRegistratioButton();
			eventRegistration.agendaModule();
			util.waitForJavascript(driver, 10000, 2000);
			eventRegistration.totalPaymentamountInCheckout();
			// Here we getting receipt data from UI and storing in ArrayList
			eventRegistration.paymentDataIncheckoutModule();
			eventRegistration.biilingaddressInCheckoutModule();
			eventRegistration.paymentProcessButton();
			util.waitForJavascript(driver, 10000, 5000);
			ArrayList<Object> receiptData = eventRegistration.clickReceiptInChecout();
			util.waitForJavascript(driver, 30000, 5000);
			// Here we validate PDF data
			String paymentType = testData.testDataProvider().getProperty("PaymentType");
			String paymentMethodDescr = testData.testDataProvider().getProperty("PaymentMethodDescription");
			viewReceipts.viewReceiptValidationsForEvents(receiptData.get(1), receiptData.get(0), paymentType,
					paymentMethodDescr, aiaNumber);

			util.switchToTabs(driver, 0);
			events.eventsTab();
			events.eventsSearch(eventName);
			String eventID = cloneEventpage.getEventId();
			ArrayList<String> afterRegistrationsalesandTotal = events.validateAfterRegistrationData();
			linksInEvents.clickAttendees();

			// Registered attendees count
			linksInEvents.getAttendeesSize();
			context.setAttribute("eventId", eventID);
			context.setAttribute("eventName", eventName);
			context.setAttribute("attendees", afterRegistrationsalesandTotal.get(0));

			// Here we validate Attendees totals using api call
			eventApivalidation.verifyAttendees(context);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}

	}

	@Test(priority = 5, description = "Verify 'sales and registration' info after registering for the event", enabled = true)
	public void validate_SalesAndRegistrationMediumTEmplate(ITestContext context)
			throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================validate_SalesAndRegistration started==========================");
		try {
			util.waitForJavascript(driver, 10000, 5000);
			if (recording) {
				VideoRecorder.startRecording("validate_SalesAndRegistration");
			}
			events.eventsTab();
			util.waitForJavascript(driver, 90000, 5000);
			String eventName = events.clickCreatedEvent("RecentEvents");
			events.validateBeforeRegistrationData();
			eventInfoModule.clickEventUrl();
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(1);
			util.waitForJavascript(driver, 90000, 5000);
			signInpage.signUp();
			ArrayList<String> dataList = signUpPage.signUpData();
			signUpPage.signUpUser();
			mailinator.verifyEmailForAccountSetup(dataList.get(3));
			util.switchToTabs(driver, 1);
			util.navigateToURl(driver, DataProviderFactory.getConfig().getValue("signinUpgradeStg"));
			signInpage.login(dataList.get(5), dataList.get(6));
			util.waitForJavascript(driver, 30000, 3000);
			String aiaNumber = eventRegistration.getAIAData();
			util.switchToTabs(driver, 0);
			eventInfoModule.clickEventUrl();
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(3);
			util.waitForJavascript(driver, 30000, 5000);
			eventRegistration.validateFirstNameInRegistartion();
			eventRegistration.validateLastNameInRegistartion();
			eventRegistration.ValidateEmailInRegistartion();
			eventRegistration.singleticketRegistratioButton();
			eventRegistration.agendaModule();
			eventRegistration.totalPaymentamountInCheckout();
			// Here we getting receipt data from UI and storing in ArrayList
			eventRegistration.paymentDataIncheckoutModule();
			eventRegistration.biilingaddressInCheckoutModule();
			eventRegistration.paymentProcessButton();

			ArrayList<Object> receiptData = eventRegistration.clickReceiptInChecout();

			util.waitForJavascript(driver, 30000, 5000);

			// Here we validate PDF data
			String paymentType = testData.testDataProvider().getProperty("PaymentType");
			String paymentMethodDescr = testData.testDataProvider().getProperty("PaymentMethodDescription");
			viewReceipts.viewReceiptValidationsForEvents(receiptData.get(1), receiptData.get(0), paymentType,
					paymentMethodDescr, aiaNumber);

			util.switchToTabs(driver, 0);
			events.eventsTab();
			events.eventsSearch(eventName);
			String eventID = cloneEventpage.getEventId();
			ArrayList<String> afterRegistrationsalesandTotal = events.validateAfterRegistrationData();
			context.setAttribute("eventId", eventID);
			context.setAttribute("eventName", eventName);
			context.setAttribute("attendees", afterRegistrationsalesandTotal.get(0));
			context.setAttribute("soldtickets", afterRegistrationsalesandTotal.get(1));
			context.setAttribute("remainEvents", afterRegistrationsalesandTotal.get(2));
			context.setAttribute("remainTickets", afterRegistrationsalesandTotal.get(3));

			// Here we validate sales & Registration totals using api call
			eventApivalidation.verifySalesOrderRegistration(context);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@Test(priority = 6, description = "Verify 'validate_RegistrationTime' info after registering for the event", enabled = true)
	public void validate_RegistrationTime(ITestContext context) throws InterruptedException, Throwable {
		Logging.logger
				.info("================================validate_RegistrationTime started==========================");
		try {
			util.waitForJavascript(driver, 10000, 5000);
			if (recording) {
				VideoRecorder.startRecording("validate_SalesAndRegistration");
			}
			events.eventsTab();
			util.waitForJavascript(driver, 90000, 5000);
			String eventName = events.clickCreatedEvent("RecentEvents");
			String fontevaRegTime = events.registrationTimerDetails();
			eventInfoModule.clickEventUrl();
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(1);
			util.waitForJavascript(driver, 90000, 5000);
			signInpage.signUp();
			ArrayList<String> dataList = signUpPage.signUpData();
			signUpPage.signUpUser();
			mailinator.verifyEmailForAccountSetup(dataList.get(3));
			util.switchToTabs(driver, 1);
			util.navigateToURl(driver, DataProviderFactory.getConfig().getValue("signinUpgradeStg"));
			signInpage.login(dataList.get(5), dataList.get(6));
			util.waitForJavascript(driver, 30000, 3000);
			util.switchToTabs(driver, 0);
			eventInfoModule.clickEventUrl();
			// sometimes Register link is not clicked in AIA application
			eventRegistration.RegisterLink(3);
			util.waitForJavascript(driver, 30000, 5000);
			eventRegistration.validateFirstNameInRegistartion();
			eventRegistration.validateLastNameInRegistartion();
			eventRegistration.ValidateEmailInRegistartion();
			eventRegistration.singleticketRegistratioButton();
			eventRegistration.validateTime(fontevaRegTime);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}

	}

	@Test(priority = 7, description = "Edit cloneEvent date and time,", enabled = true)
	public void test_EditSelectDateInCloneEventInfoMediumTEmplate(ITestContext context)
			throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================test_EditSelectDateInCloneEventInfo started==========================");
		try {
			events.eventsTab();
			events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			util.waitForJavascript(driver, 90000, 5000);
			eventInfoModule.editCloneEventDateAndTime();
			eventInfoModule.verifyTimeDurationOfEditCloneEventDateAndTime();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@Test(priority = 8, description = "Validate register timer in Edit cloneEvent info,", enabled = true)
	public void validate_RegistrationTimerCloneEventInfoMediumTEmplate(ITestContext context)
			throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================validate_RegistrationTimerCloneEventInfo started==========================");
		try {
			events.eventsTab();
			events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			util.waitForJavascript(driver, 90000, 5000);
			eventInfoModule.verifyUserAbleToProvidedataInRegistrationTimerInputBox();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
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
