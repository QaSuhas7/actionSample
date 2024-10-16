package org.aia.testcases.events;

import org.aia.pages.BaseClass;
import org.aia.pages.api.events.EventAPIValidations;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.events.EventInfoModule;
import org.aia.pages.fonteva.events.EventConfig;
import org.aia.pages.fonteva.events.Events;
import org.aia.pages.fonteva.events.NewCloneEvents;
import org.aia.pages.fonteva.events.TicketModule;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.aia.utility.VideoRecorder;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

public class Test_CloneTicketsInMediumTemplate extends BaseClass {
	Events events;
	NewCloneEvents cloneEventpage;
	ConfigDataProvider testData;
	EventInfoModule eventInfoModule;
	TicketModule ticketModule;
	EventAPIValidations eventApivalidation;
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
		ticketModule = PageFactory.initElements(driver, TicketModule.class);
		eventApivalidation = PageFactory.initElements(driver, EventAPIValidations.class);
		recording = Boolean.parseBoolean(testData.testDataProvider().getProperty("videoRecording"));
		Logging.configure();
	}

	@Test(priority = 1, description = "FEC-102:- verify field in TicketModule tickets info,", enabled = true)
	public void test_verifyFieldInTicketModuleMediumTEmplate(ITestContext context)
			throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================test_verifyFieldInTicketModuleMediumTEmplate started==========================");
		try {
			if (recording) {
				VideoRecorder.startRecording("test_verifyFieldInTicketModule");
			}
			events.eventsTab();
			events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			util.waitForJavascript(driver, 20000, 5000);
			ticketModule.verifyAllFIeldsTicketModule();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}

	}

	@Test(priority = 2, description = "FEC-118:-test_VerifyManageInventoryInTickets", enabled = true)
	public void test_VerifyManageInventoryInTickets(ITestContext context) throws InterruptedException, Throwable {
		try {
			if (recording) {
				VideoRecorder.startRecording("test_VerifyManageInventoryInTickets");
			}
			events.eventsTab();
			String eventName = events.clickCreatedEvent("RecentEvents");
			String eventId = cloneEventpage.getEventId();
			eventInfoModule.clickEditButton();
			util.waitForJavascript(driver, 30000, 5000);
			ticketModule.eventTicketsTab();
			ticketModule.clickManageInventory();
			ticketModule.enterAiaMemberDetails();
			String uiEventCapacity = ticketModule.VerifyEventTicketCapacity();
			context.setAttribute("eventId", eventId);
			context.setAttribute("eventName", eventName);
			context.setAttribute("uiEventCapacityAfterModify", uiEventCapacity);

			// Edit Event capacity API validation
			eventApivalidation.verifyEventTicketCapacity(context);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@Test(priority = 3, description = "FEC-104:-verify field in TicketModule info, tickets sales start date,", enabled = true)
	public void test_VeriftTickets_sales_startDateMediumTEmplate(ITestContext context)
			throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================test_VeriftTickets_sales_startDateMediumTEmplates started==========================");
		
		try {
			if (recording) {
				VideoRecorder.startRecording("validate_CreateNewTicketTypeMediumTEmplate");
			}
			events.eventsTab();
			String eventName = events.clickCreatedEvent("RecentEvents");
			String eventId = cloneEventpage.getEventId();
			eventInfoModule.clickEditButton();
			util.waitForJavascript(driver, 90000, 5000);
			ticketModule.verifyUserAbleToProvidedateIntoTicketSalesStartDate();
//			context.setAttribute("eventId", eventId);
//			context.setAttribute("eventName", eventName);
//			context.setAttribute("ticketSalesStartDate", EventConfig.ticketSalesStartDate);
			// api validation
			////fec-194 automation bug 
			//eventApivalidation.verifyEventModifyTicketSalesStartDate(context);
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@Test(priority = 4, description = "FEC-114:-Verify CreateNewTicket And ValidateAllfields,", enabled = true)
	public void test_VerifycCreateNewTicketAndValidateAllfieldsMediumTEmplate(ITestContext context)
			throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================test_VerifycCreateNewTicketAndValidateAllfieldsMediumTEmplate started==========================");
		
		try {
			if (recording) {
				VideoRecorder.startRecording("validate_CreateNewTicketTypeMediumTEmplate");
			}
			events.eventsTab();
			events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			util.waitForJavascript(driver, 30000, 5000);
			ticketModule.eventTicketsTab();
			ticketModule.verifyAllFIeldsInCreateTicketpivotTabPopUp();
			ticketModule.publishedCheckBox();
			ticketModule.activeCheckBox();
			ticketModule.enterTicketName();
			ticketModule.enterPriceInCreateTicketType("25.00");
			ticketModule.restrictQuantityCheckBox();
			ticketModule.enterDescriptionInCreateTicketType();
			ticketModule.buttonsInCreateTicketType("SaveContinue");

		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@Test(priority = 5, description = "FEC-115:-validate_TicketDisplayOrderValues", enabled = true)
	public void validate_TicketDisplayOrderValues(ITestContext context) throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================validate_TicketDisplayOrderValues started==========================");
		
		try {
			if (recording) {
				VideoRecorder.startRecording("validate_TicketDisplayOrderValues");
			}
			events.eventsTab();
			System.out.println("eventsticket tab displayed");
			events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			ticketModule.eventTicketsTab();
			util.waitForJavascript(driver, 30000, 5000);
			System.out.println("tickets tab is displayed");
			ticketModule.VerifyTicketOrderValues();
			ticketModule.validateSortingOrder();
			System.out.println("testcase executed SUccessfully");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}

	}
	
	@Test(priority = 6, description = "FEC-103:-Verify creation of New Ticket Type", enabled = true)
	public void validate_CreateNewTicketTypeMediumTEmplate(ITestContext context)
			throws InterruptedException, Throwable {
		Logging.logger.info(
				"================================validate_CreateNewTicketTypeMediumTEmplate started==========================");
		
		try {
			if (recording) {
				VideoRecorder.startRecording("validate_CreateNewTicketTypeMediumTEmplate");
			}
			events.eventsTab();
			events.clickCreatedEvent("RecentEvents");
			eventInfoModule.clickEditButton();
			ticketModule.eventTicketsTab();
			ticketModule.clickNewTicketType();
			ticketModule.publishedCheckBox();
			ticketModule.activeCheckBox();
			ticketModule.enterTicketName();
			ticketModule.enterPriceInCreateTicketType("20.00");
			ticketModule.restrictQuantityCheckBox();
			ticketModule.enterDescriptionInCreateTicketType();
			ticketModule.buttonsInCreateTicketType("SaveContinue");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@AfterMethod(alwaysRun = true)
	public void teardown(ITestResult result) {
		if (recording) {
			VideoRecorder.stopRecording();
		}
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("LOG : FAIL Test failed to executed");
			Utility.takeScreenShotAfterFail(driver, result);
		}
		if (driver != null) {
			BrowserSetup.closeBrowser(driver);
		}
	}

}
