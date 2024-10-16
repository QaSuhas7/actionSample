package org.aia.testcases.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.events.EventAPIValidations;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.events.EmailAllAttendees;
import org.aia.pages.fonteva.events.EventConfig;
import org.aia.pages.fonteva.events.Events;
import org.aia.pages.fonteva.events.QuickLinksInEvents;
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


public class TestEmailAllAttendees extends BaseClass {
	Events events;
	EmailAllAttendees email;
	ConfigDataProvider testData;
	EventAPIValidations eventApivalidation;
	QuickLinksInEvents linksInEvents;
	MailinatorAPI mailinator;
	boolean recording;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		testData = new ConfigDataProvider();
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				testData.getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		eventApivalidation = new EventAPIValidations(driver);
		events = PageFactory.initElements(driver, Events.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		linksInEvents = PageFactory.initElements(driver, QuickLinksInEvents.class);
		email = PageFactory.initElements(driver, EmailAllAttendees.class);
		recording = Boolean.parseBoolean(testData.testDataProvider().getProperty("videoRecording"));
		Logging.configure();
	}

	@Test(priority = 1, description = "validateEmailAttandees", enabled = true)
	public void validateEmailAttandees(ITestContext context) throws InterruptedException, Throwable {
		try {
			events.eventsTab();
			email.clickActiveEvent();
			email.clickEmailAddressButton();
			email.validateEmailAddressPopUp();
			linksInEvents.clickAttendees();
			// Registered attendees count
			ArrayList<String> emailList = linksInEvents.getEmailInAttendees();
			linksInEvents.getFullNameInAttendees();
			context.setAttribute("eventId", EventConfig.eventID);
			context.setAttribute("eventName", email.getEventName);
			//email verification
			mailinator.registrationConfirmationEmailforEmailAttendees(emailList, email.getEventName);
			System.out.println("testcase passed");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		} catch (Throwable e) {
			throw new AssertionError(e.getMessage());
		}
	}

	@Test(priority = 2, description = "validateEmailAttandeesNo", enabled = true)
	public void validateEmailAttandeesNo(ITestContext context) throws InterruptedException, Throwable {
		try {
			events.eventsTab();
			System.out.println("eventsticket tab displayed");
			email.clickEmailAdressNo();
			System.out.println("testcase passed");
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
