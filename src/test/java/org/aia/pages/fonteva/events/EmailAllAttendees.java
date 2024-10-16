package org.aia.pages.fonteva.events;

import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class EmailAllAttendees {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Events events;
	Actions act;
	EventInfoModule eventInfoModule;
	NewCloneEvents newCloneEvents;
	QuickLinksInEvents linksInEvents ;
	static Logger log = Logger.getLogger(EmailAllAttendees.class);
	public String getEventName="";
	public int attendeesQun;

	public EmailAllAttendees(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
		events = PageFactory.initElements(driver, Events.class);
		newCloneEvents = PageFactory.initElements(driver, NewCloneEvents.class);
		linksInEvents = PageFactory.initElements(driver, QuickLinksInEvents.class);
		eventInfoModule = PageFactory.initElements(driver, EventInfoModule.class);
	}

	@FindBy(xpath = "//a[contains(@class,'label-action dndItem')]/span[text()='Events']")
	WebElement eventsModule;

	@FindBy(xpath = "//tbody/tr[1]/td[3]/span[1]")
	WebElement eventButton;

	@FindBy(xpath = "//tbody/tr[1]/th[1]/span[1]/a")
	WebElement createdEvent;

	@FindBy(xpath = "//span[@class='slds-grid slds-grid--align-spread forceInlineEditCell']//a[@data-refid='recordId']")
	WebElement eventName;

	@FindBy(xpath = "//span[@title='Status']")
	WebElement statusButton;

	@FindBy(xpath = "//li[contains(@data-target-selection-name,'EventApi__Event__c.Email_All_Attendees')]//lightning-button//button")
	WebElement emailAddressButton;

	@FindBy(xpath = "//h2[text()='Confirm Mass Email']")
	WebElement confirmEmail;

	@FindBy(xpath = "//div[@class=\"slds-text-align_center\"]/p[1]")
	WebElement emailText;

	@FindBy(xpath = "//p[text()='Do you want to proceed?']")
	WebElement emailTextProceed;

	@FindBy(xpath = "//button[@class='slds-button slds-button_brand']")
	WebElement yesButton;

	@FindBy(xpath = "//button[@class='slds-button slds-button_neutral' and text()='No']")
	WebElement noButton;
	
	@FindBy(xpath = "//div[@class='toastTitle slds-text-heading--small']") WebElement toastesuccesMsg;

	@FindBy(xpath = "//div[@class='toastContent slds-notify__content']//span[@class='toastMessage forceActionsText']")
	WebElement toastmessage;

	public void clickActiveEvent() throws Throwable {
		Thread.sleep(5000);
		String expectedStatus = "Active"; // Assuming this is the status to compare with
		util.waitUntilElement(driver, eventButton);
		// Assuming eventButton is a WebElement and s is the text of the element, and
		// comparing the text content
		String actualStatus = eventButton.getText();
		if (actualStatus.equalsIgnoreCase(expectedStatus)) {
			Utility.highLightElement(driver, eventButton);
			// util.clickUsingJS(driver, eventButton);
			System.out.println("It's Active");
			util.waitUntilElement(driver, eventsModule);
			util.clickUsingJS(driver, eventsModule);
			util.waitUntilElement(driver, createdEvent);
			getEventName = createdEvent.getText();
			util.clickUsingJS(driver, createdEvent);
			EventConfig.eventID = newCloneEvents.getEventId();
			}
		   else {
			Utility.highLightElement(driver, eventButton);
			System.out.println("It's not Active");
			util.waitUntilElement(driver, statusButton);
			util.clickUsingJS(driver, statusButton);
			Thread.sleep(5000);
			util.clickUsingJS(driver, statusButton);
			Thread.sleep(5000);
			String status = eventButton.getText();
			if (status.equalsIgnoreCase(expectedStatus)) {
				Utility.highLightElement(driver, eventButton);
				util.clickUsingJS(driver, eventButton);
				System.out.println("It's Active now");
				util.waitUntilElement(driver, eventsModule);
				util.clickUsingJS(driver, eventsModule);
				util.waitUntilElement(driver, createdEvent);
				util.clickUsingJS(driver, createdEvent);
				getEventName = createdEvent.getText();
				Thread.sleep(5000);
				EventConfig.eventID = newCloneEvents.getEventId();
				
				System.out.println("its active now");
			} else {
				Utility.highLightElement(driver, eventButton);
				System.out.println("It's still not Active");
				Assert.fail();
			}

		}
	}
	
	public void clickEmailAddressButton() {
		util.waitUntilElement(driver, emailAddressButton);
		util.clickUsingJS(driver, emailAddressButton);
		System.out.println("VERIFIED: email address is clicked");
	}
	
	public void validateEmailAddressPopUp() throws InterruptedException {
		util.waitUntilElement(driver, confirmEmail);
		Utility.highLightElement(driver, confirmEmail);
		Assert.assertTrue(confirmEmail.isDisplayed(), "Confirm Mass Email");
		System.out.println("Confirm Mass Email text is displayed");
		util.waitUntilElement(driver, emailText);
		Utility.highLightElement(driver, emailText);
		Assert.assertTrue(emailText.isDisplayed(),
				"You are about to send mass emails to 3 TestQAMedium11072023130156 recipients");
		System.out.println(
				"You are about to send mass emails to 3 TestQAMedium11072023130156 recipients text is displayed");
		util.waitUntilElement(driver, emailTextProceed);
		Utility.highLightElement(driver, emailTextProceed);
		Assert.assertTrue(emailTextProceed.isDisplayed(), "Do you want to proceed?'");
		if (yesButton.isDisplayed()) {
			System.out.println("yes is displayed");
			util.clickUsingJS(driver, yesButton);
			Thread.sleep(5000);
			System.out.println("VERIFIED: click Yes button");
			String toasteSuccessMessage = toastesuccesMsg.getText();
			log.info(toasteSuccessMessage);
			String Toast = toastmessage.getText();
			Assert.assertTrue(toastmessage.isEnabled(), "Toast message is not displayed");
			System.out.println(Toast);
		} else {
			Assert.fail();
		}
	}
	
	

	public void clickEmailAdressNo() throws InterruptedException {
		Thread.sleep(5000);
		String expectedStatus = "Active"; // Assuming this is the status to compare with
		util.waitUntilElement(driver, eventButton);
		// Assuming eventButton is a WebElement and s is the text of the element, and
		// comparing the text content
		String actualStatus = eventButton.getText();

		if (actualStatus.equalsIgnoreCase(expectedStatus)) {
			Utility.highLightElement(driver, eventButton);
			// util.clickUsingJS(driver, eventButton);
			System.out.println("It's Active");
			util.waitUntilElement(driver, eventsModule);
			util.clickUsingJS(driver, eventsModule);
			util.waitUntilElement(driver, createdEvent);
			util.clickUsingJS(driver, createdEvent);
			Thread.sleep(5000);
			util.waitUntilElement(driver, emailAddressButton);
			util.clickUsingJS(driver, emailAddressButton);
			Thread.sleep(5000);
			System.out.println("VERIFIED: email address is clicked");
			util.waitUntilElement(driver, confirmEmail);
			Utility.highLightElement(driver, confirmEmail);
			Assert.assertTrue(confirmEmail.isDisplayed(), "Confirm Mass Email");
			System.out.println("Confirm Mass Email text is displayed");
			util.waitUntilElement(driver, emailText);
			Utility.highLightElement(driver, emailText);
			Assert.assertTrue(emailText.isDisplayed(), "You are about to send mass emails to 3  recipients");
			System.out.println("You are about to send mass emails to 3  recipients text is displayed");
			util.waitUntilElement(driver, emailTextProceed);
			Utility.highLightElement(driver, emailTextProceed);
			Assert.assertTrue(emailTextProceed.isDisplayed(), "Do you want to proceed?'");
			util.waitUntilElement(driver, noButton);
			util.clickUsingJS(driver, noButton);
			Thread.sleep(5000);
			System.out.println("VERIFIED: click No button");
		} else {
			Utility.highLightElement(driver, eventButton);
			System.out.println("It's not Active");
			util.waitUntilElement(driver, statusButton);
			util.clickUsingJS(driver, statusButton);
			Thread.sleep(5000);
			util.clickUsingJS(driver, statusButton);
			Thread.sleep(5000);
			String status = eventButton.getText();

			if (status.equalsIgnoreCase(expectedStatus)) {
				Utility.highLightElement(driver, eventButton);
				util.clickUsingJS(driver, eventButton);
				System.out.println("It's Active now");
				util.waitUntilElement(driver, eventsModule);
				util.clickUsingJS(driver, eventsModule);
				util.waitUntilElement(driver, createdEvent);
				util.clickUsingJS(driver, createdEvent);
				Thread.sleep(5000);
				
				util.waitUntilElement(driver, emailAddressButton);
				util.clickUsingJS(driver, emailAddressButton);
				System.out.println("VERIFIED: email address is clicked");
				Thread.sleep(5000);
				util.waitUntilElement(driver, confirmEmail);
				Utility.highLightElement(driver, confirmEmail);
				Assert.assertTrue(confirmEmail.isDisplayed(), "Confirm Mass Email");
				System.out.println("Confirm Mass Email text is displayed");
				util.waitUntilElement(driver, emailText);
				Utility.highLightElement(driver, emailText);
				Assert.assertTrue(emailText.isDisplayed(), "You are about to send mass emails to 3  recipients");
				System.out.println("You are about to send mass emails to 3  recipients text is displayed");
				util.waitUntilElement(driver, emailTextProceed);
				Utility.highLightElement(driver, emailTextProceed);
				Assert.assertTrue(emailTextProceed.isDisplayed(), "Do you want to proceed?'");
				util.waitUntilElement(driver, noButton);
				Utility.highLightElement(driver, noButton);
				if (yesButton.isDisplayed()) {
					System.out.println("No is displayed");
					util.clickUsingJS(driver, noButton);
					Thread.sleep(5000);
					System.out.println("VERIFIED: click yes button");
				} else {
					Assert.fail();
				}
				System.out.println("its active now");
			} else {
				Utility.highLightElement(driver, eventButton);
				System.out.println("It's still not Active");
				Assert.fail();
			}
		}
	}

}
