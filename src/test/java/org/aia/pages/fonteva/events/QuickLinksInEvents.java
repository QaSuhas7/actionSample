package org.aia.pages.fonteva.events;

import java.util.ArrayList;
import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class QuickLinksInEvents {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	ConfigDataProvider testData;
	public String eName = "";
	public String newEvent = "";
	public String startDate = "";
	public String eventId = "";
	static Logger log = Logger.getLogger(NewCloneEvents.class);

	public QuickLinksInEvents(WebDriver Idriver) {
		this.driver = Idriver;
		executor = (JavascriptExecutor) driver;
		testData = new ConfigDataProvider();
	}

	// Attendees
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a//slot/span[contains(text(),'Attendees')]")
	WebElement attendeesLink;

	@FindAll(value = {
			@FindBy(xpath = "//lightning-primitive-cell-factory[@data-label='Attendee ID']/following::lst-formatted-text[text()='Registered']") })
	List<WebElement> attendeesInfo;
	
	//td[@data-label="Full Name"]
	@FindAll(value = {@FindBy(xpath  = "//td[@data-label=\"Full Name\"]")}) List<WebElement> fullNameInAttendes;
	@FindAll(value = {@FindBy(xpath =  "//a[@class='emailuiFormattedEmail']")}) List<WebElement> emailInAttendes; 

	public void clickAttendees() {
		util.waitUntilElement(driver, attendeesLink);
		String attendeescount = attendeesLink.getText();
		String attendees = attendeescount.replaceAll("&nbsp;", "");
		System.out.println(attendees);
		log.info("Attendees count is" + attendees);
		util.waitUntilElement(driver, attendeesLink);
		Utility.highLightElement(driver, attendeesLink);
		attendeesLink.click();
	}

	/**
	 * @return 
	 * @throws Throwable
	 */
	public void getAttendeesSize() throws Throwable {
		Thread.sleep(5000);
		System.out.println(attendeesInfo.size());
		int attendeesCount = attendeesInfo.size();
		System.out.println(attendeesCount);
		log.info("Attendees count" + attendeesCount);
	}
	
public ArrayList<String> getEmailInAttendees() throws InterruptedException {
	Thread.sleep(5000);
	ArrayList<String> eventEmails = new ArrayList<String>();
	log.info(emailInAttendes.size());
	System.out.println("mk");
	for (int i = 0; i < emailInAttendes.size(); i++) {
		eventEmails.add( emailInAttendes.get(i).getAttribute("textContent").replace("@architects-team.m8r.co", ""));
	}
	return eventEmails;
}

public List<String> getFullNameInAttendees() throws InterruptedException {
	Thread.sleep(5000);
	List<String> fullNameListInAttendees = new ArrayList<String>();
	log.info(fullNameInAttendes.size());
	for (int i = 0; i < fullNameInAttendes.size(); i++) {
		fullNameListInAttendees.add( fullNameInAttendes.get(i).getText());
	}

	return fullNameListInAttendees;
}
}
