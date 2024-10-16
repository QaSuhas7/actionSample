package org.aia.pages.fonteva.events;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.aia.utility.DateUtils;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class EventInfoModule {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Events events;
	Actions act;
	static Logger log = Logger.getLogger(EventInfoModule.class);
	public String salesOrder = "";
	public String aiaNumber = "";
	public static String futureDate="";

	public EventInfoModule(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
		events = PageFactory.initElements(driver, Events.class);
	}

	@FindBy(xpath = "//li[contains(@data-target-selection-name,'EventApi__Event__c.Edit')]//lightning-button//button")
	WebElement EditButton;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] span[class*='-dropdown--trigger event-builder-action-button slds-p-horizontal--small'] span svg use")
	WebElement hoverSaveDropDown;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] ul[data-name='Save_Exit_Event_Builder'] li a")
	WebElement saveExitButton;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a[contains(text(),'/lt-event?')]")
	WebElement eventUrl;

	// EventInfo locators
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-name='name']/input")
	WebElement eventInfoName;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-name='displayName']/input")
	WebElement eventDisplayName;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] div[data-name='startDate'] div input")
	WebElement eventStartDate;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] div[data-name='endDate'] div input")
	WebElement eventEndDate;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] select[name='Start Time']")
	WebElement eventStartTime;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] select[name='End Time']")
	WebElement eventEndTime;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] select[name='Time Zone']")
	WebElement eventTimeZone;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] div[data-name='registrationTimer'] input")
	WebElement eventRegistrationTimer;

	@FindAll(value = { @FindBy(xpath = "(//label[text()='Event Overview']/following::div[@class='fr-element fr-view cke_editable'])[1]/p") })
	List<WebElement> eventOverViewText;

	@FindBy(xpath = "//div[@data-name='description']/textarea")
	WebElement DescriptionInEventInfo;

	@FindBy(xpath = "(//div[contains(@class,'p-horizontal_large slds-m-bottom_medium')])[2]")
	WebElement whenWhereSummaryInEventInfo;

	@FindBy(xpath = "//label[@data-name='enableEventDisplayNameAndDT']/input")
	WebElement eventDisplayNameAndDT;

	@FindBy(xpath = "//label[@data-name='isFeaturedEvent']/input")
	WebElement isFeaturedEvent;

	@FindBy(xpath = "//select[@name='Time Zone']")
	WebElement timeZoneIneventInfo;

	// Invitation locators
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderInvitationOnly']")
	WebElement eventBuilderInvitation;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] a[data-panel='attendeeCreationTab']")
	WebElement attendeeCreationTab;

	// Venues locators
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderVenues']")
	WebElement eventBuilderVenues;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[contains(text(),'Venues')]")
	WebElement VenuesTab;

	// Event AccessPermissions
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderAccessPermissions']")
	WebElement eventBuilderAccessPermissions;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//th[contains(text(),'Access to this Item')]")
	WebElement AcessPermissionTab;

	// EventApi:EventBuilderSponsorPackages
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderSponsorPackages']")
	WebElement eventBuilderSponsorPackages;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a[@data-tab='sponsorPackages']")
	WebElement sponcerPackageTab;

//	@FindBy(xpath = "(//lightning-formatted-text[@title='AIA Contact Number']/following::div/lightning-formatted-text)[1]")
//	WebElement getAIANumber;

//	@FindBy(xpath = "//lightning-formatted-text[text()='Account Name']/following-sibling::div/a")
//	WebElement accountName;

	@FindBy(xpath = "(//table[@data-aura-class='uiVirtualDataTable']//tbody/tr/th/following::td/span/span[@title!=''])[1]")
	WebElement getAIANumber;

	@FindBy(xpath = "(//table[@data-aura-class='uiVirtualDataTable']//tbody//tr/th/span/a)[1]")
	WebElement accountName;

	@FindBy(xpath = "//a//slot/span[contains(text(),'Sales Orders')]")
	WebElement salesOrderLink;

	@FindBy(xpath = "//th[@data-label='Sales Order #']//a/slot/slot/span")
	WebElement salesOrderText;

	@FindBy(css = "button[class*='_neutral search-button slds-truncate']")
	WebElement globSearch;

	@FindBy(xpath = "//label[text()='Search...']/following-sibling::div/input")
	WebElement globSearchInput;

	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	WebElement searchEvents;

	@FindBy(xpath = "//span[@class='slds-grid slds-grid--align-spread forceInlineEditCell']//a[@data-refid='recordId']")
	WebElement eventName;

	// status in edit
	@FindBy(xpath = "//div[text()='Status:']//span[text()='Planned']")
	WebElement StatusInEditEvent;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//span[text()='Planned']")
	WebElement statusDropdown;

	@FindBy(xpath = "(//div[@class='windowViewMode-normal oneContent active lafPageHost']//a[text()='Active'])")
	WebElement activeOption;
	// Date selection on Calendar in Event info

	@FindBy(xpath = "//label[contains(text(),'Start Date')]//parent::span//parent::div//following-sibling::div//input")
	WebElement startDateCalender;

	@FindBy(xpath = "//label[contains(text(),'End Date')]//parent::span//parent::div//following-sibling::div//input")
	WebElement endDateCalendar;

	@FindBy(xpath = "//a[@title='Go to previous month']")
	WebElement perviousMonthsButton;

	@FindBy(xpath = "//a[@title='Go to next month']")
	WebElement nextMonthsButton;

	@FindBy(xpath = "//label[contains(text(),'Start Time')]//parent::span//following-sibling::div//div//select")
	WebElement startTimeHoursDropDown;

	@FindBy(xpath = "(//div[@class='slds-select_container'])[2]//select")
	WebElement startTimeMinutesDropDown;

	@FindBy(xpath = "(//div[@class='slds-select_container'])[3]//select")
	WebElement startTimePeriodDropDown;

	@FindBy(xpath = "//label[contains(text(),'End Time')]//parent::span//following-sibling::div//div//select")
	WebElement endTimeHoursDropDown;

	@FindBy(xpath = "(//div[@class='slds-select_container'])[5]//select")
	WebElement endTimeMinutesDropDown;

	@FindBy(xpath = "(//div[@class='slds-select_container'])[6]//select")
	WebElement endTimePeriodDropDown;

	String DurationOfStartEndDates = "(//span[text() ='Duration']//following-sibling::span)[2]//span";

	@FindBy(xpath = "(//span[text() ='Duration']//following-sibling::span)[2]//span")
	WebElement DurationOfStartEndDate;

	@FindBy(xpath = "//button[contains(text(),'Save')]")
	WebElement eventSaveButton;

	@FindBy(xpath = "//span[contains(text(),'24-Hour Time')]//preceding-sibling::span")
	WebElement a24HoursTimeCheckBoxXpath;

	@FindBy(xpath = "//label[contains(text(),'Registration Timer (in minutes)')]//parent::span//following-sibling::div//input")
	WebElement RegistrationTimerInputBox;

	public void getAIAData() throws Throwable {
		util.waitUntilElement(driver, getAIANumber);
		Utility.highLightElement(driver, getAIANumber);
		aiaNumber = getAIANumber.getText();
		log.info(getAIANumber.getText());
		Utility.highLightElement(driver, accountName);
		accountName.click();

		// navigate to main events page
		events.eventsTab();
		Thread.sleep(4000);
	}

	public void clickEditButton() {
		util.waitUntilElement(driver, EditButton);
		util.clickUsingJS(driver, EditButton);
		System.out.println("VERIFIED: Edit Event page is displayed");
	}

	/**
	 * @param ExpectedEventName Here We can get the values Event name, Event Start
	 *                          date, Event end date, Event start time, Event end
	 *                          time,Event TimeZone, Registration timer.
	 */
	public void editEventInfo(String ExpectedEventName, String startTime, String endTime, String regTime,
			String timeZone) {
		util.waitUntilElement(driver, eventInfoName);
		log.info(eventInfoName.getAttribute("value"));
		System.out.println(eventInfoName.getAttribute("value"));
		Assert.assertEquals(eventInfoName.getAttribute("value"), ExpectedEventName);

		log.info(eventDisplayName.getAttribute("value"));
		System.out.println(eventDisplayName.getAttribute("value"));
		Assert.assertEquals(eventDisplayName.getAttribute("value"), ExpectedEventName);

		log.info(eventStartDate.getAttribute("value"));
		String startDateStr = eventStartDate.getAttribute("value");
		System.out.println(eventStartDate.getAttribute("value"));
		Assert.assertEquals(eventStartDate.getAttribute("value"),
				new SimpleDateFormat("MM/dd/yyyy").format(new Date()));

		log.info(eventEndDate.getAttribute("value"));
		String endDateStr = eventEndDate.getAttribute("value");
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date startDate = null;
		Date endDate = null;
		try {
			startDate = dateFormat.parse(startDateStr);
			endDate = dateFormat.parse(endDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (endDate.compareTo(startDate) >= 0) {
			System.out.println("End date is equal to or after the start date.");
		} else {
			System.out.println("End date is before the start date.");
		}

		log.info(eventStartTime.getAttribute("value"));
		System.out.println(eventStartTime.getAttribute("value"));
		Assert.assertEquals(eventStartTime.getAttribute("value"), startTime);

		log.info(eventEndTime.getAttribute("value"));
		System.out.println(eventEndTime.getAttribute("value"));
		Assert.assertEquals(eventEndTime.getAttribute("value"), endTime);

		log.info(eventTimeZone.getAttribute("value"));
		System.out.println(eventTimeZone.getAttribute("value"));
		Assert.assertEquals(eventTimeZone.getAttribute("value"), timeZone);

		log.info(eventRegistrationTimer.getAttribute("value"));
		System.out.println(eventRegistrationTimer.getAttribute("value"));
		Assert.assertEquals(eventRegistrationTimer.getAttribute("value"), regTime);
	}

	public void editEventInvitation() {
		util.waitUntilElement(driver, eventBuilderInvitation);
		eventBuilderInvitation.click();
		log.info("Event Invitation is clicked successfully");
		util.waitUntilElement(driver, attendeeCreationTab);
		Assert.assertTrue(attendeeCreationTab.isDisplayed());
		log.info("attendeeCreationTab is displayed");
	}

	public void editEventVenues() {
		util.waitUntilElement(driver, eventBuilderVenues);
		eventBuilderVenues.click();
		log.info("Event Venues is clicked successfully");
		util.waitUntilElement(driver, VenuesTab);
		Assert.assertTrue(VenuesTab.isDisplayed());
		log.info("VenuesTab is displayed");
	}

	public void editEventAccessPermissions() {
		util.waitUntilElement(driver, eventBuilderAccessPermissions);
		eventBuilderAccessPermissions.click();
		log.info("Event AccessPErmission is clicked successfully");
		util.waitUntilElement(driver, AcessPermissionTab);
		Assert.assertTrue(AcessPermissionTab.isDisplayed());
		log.info("AcessPermissionTab is displayed");
	}

	public void editEventSponsorPackages() {
		util.waitUntilElement(driver, eventBuilderSponsorPackages);
		eventBuilderSponsorPackages.click();
		log.info("Event Sponsor is clicked successfully");
		util.waitUntilElement(driver, sponcerPackageTab);
		Assert.assertTrue(sponcerPackageTab.isDisplayed());
		log.info("sponcerPackageTab is displayed");
	}

	public void saveExitButton() throws Throwable {
		Utility.highLightElement(driver, hoverSaveDropDown);
		act.moveToElement(hoverSaveDropDown);
		hoverSaveDropDown.click();
		log.info("saveClose dropdown is clicked");
		util.waitUntilElement(driver, saveExitButton);
		saveExitButton.click();
		log.info("saveExit button is clicked sucessfully");
	}

	public void clickEventUrl() throws Throwable {
		Thread.sleep(5000);
		util.waitUntilElement(driver, eventUrl);
		util.scrollingElementUsingJS(driver, eventUrl);
		log.info("Scrolled event url");
		util.clickUsingJS(driver, eventUrl);
		log.info("event url is clicked sucessfully");
	}

	public void editCloneEventDateAndTime() throws InterruptedException {
		// Select Start Date and Time
		if (EventConfig.select24HoursTime == true) {
			util.waitUntilElement(driver, a24HoursTimeCheckBoxXpath);
			Thread.sleep(5000);
			util.scrollingElementUsingJS(driver, a24HoursTimeCheckBoxXpath);
			a24HoursTimeCheckBoxXpath.click();
		}

		util.waitUntilElement(driver, startDateCalender);
		util.scrollingElementUsingJS(driver, startDateCalender);
		startDateCalender.clear();
		startDateCalender.sendKeys(EventConfig.startDateWithTime.split(" ")[0]);

		util.selectDropDownByText(startTimeHoursDropDown, EventConfig.startDateWithTime.split(" ")[1].split(":")[0]);
		util.selectDropDownByText(startTimeMinutesDropDown, EventConfig.startDateWithTime.split(" ")[1].split(":")[1]);

		if (EventConfig.select24HoursTime == false) {
			util.selectDropDownByText(startTimePeriodDropDown, EventConfig.startTimePeriodType);
		}

		// Select End Date and Time
		util.scrollingElementUsingJS(driver, endDateCalendar);
		util.waitUntilElement(driver, endDateCalendar);
		endDateCalendar.clear();
		endDateCalendar.sendKeys(EventConfig.endDateWithTime.split(" ")[0]);

		util.selectDropDownByText(endTimeHoursDropDown, EventConfig.endDateWithTime.split(" ")[1].split(":")[0]);
		util.selectDropDownByText(endTimeMinutesDropDown, EventConfig.endDateWithTime.split(" ")[1].split(":")[1]);

		if (EventConfig.select24HoursTime == false) {
			util.selectDropDownByText(endTimePeriodDropDown, EventConfig.endTimePeriodType);
		}
	}

	public void verifyTimeDurationOfEditCloneEventDateAndTime() throws ParseException {
		boolean value = true;
		util.scrollingElementUsingJS(driver, DurationOfStartEndDate);
		List<WebElement> totalElementTexts = driver.findElements(By.xpath(DurationOfStartEndDates));
		String exactTotalElementText = null;
		for (WebElement totalElm : totalElementTexts) {
			if (!totalElm.getText().isBlank()) {
				exactTotalElementText = exactTotalElementText + totalElm.getText();
			}
		}
		System.out.println("UI date validation Text" + exactTotalElementText);

		ArrayList<String> values = DateUtils.findDifferenceBetweenTwoDates(EventConfig.startDateWithTime,
				EventConfig.endDateWithTime);
		for (String dateTime : values) {
			System.out.println("Expected date validation Text" + dateTime);
		}
		for (int i = 0; i < values.size(); i++) {
			if (!values.get(i).startsWith("0")) {
				if (!exactTotalElementText.contains(values.get(i))) {
					value = false;
					break;
				};
			}
		}
		Assert.assertTrue(value, "Assert failure:- giving date duration is wrong");
	}

	public void verifyUserAbleToProvidedataInRegistrationTimerInputBox() throws InterruptedException {
		util.waitUntilElement(driver, RegistrationTimerInputBox);
		util.scrollingElementUsingJS(driver, RegistrationTimerInputBox);
		RegistrationTimerInputBox.clear();
		RegistrationTimerInputBox.sendKeys(EventConfig.RegistrationTimer);
		String PickDate = RegistrationTimerInputBox.getAttribute("value");
		System.out.println("PickDate :" + PickDate);
		System.out.println("EventConfig.ticketalesStartDate : " + EventConfig.RegistrationTimer);
		Assert.assertEquals(RegistrationTimerInputBox.getAttribute("value"), EventConfig.RegistrationTimer);
	}

	public void selectActiveStatus() {
		Utility.waitForWebElement(driver, StatusInEditEvent, 10);
		if (StatusInEditEvent.isDisplayed()) {
			Utility.waitForWebElement(driver, statusDropdown, 20);
			util.mosueOverUsingAction(driver, statusDropdown);
			util.clickUsingJS(driver,statusDropdown);
			Utility.waitForWebElement(driver, activeOption, 20);
			util.clickUsingJS(driver, activeOption);
		}
	}
	
	public void enterEventOverview() {
		log.info(eventOverViewText.size());
		for (int i = 0; i < eventOverViewText.size(); i++) {
		log.info(eventOverViewText.get(i).getText());
			eventOverViewText.get(i).clear();
			eventOverViewText.get(i).sendKeys(Keys.BACK_SPACE);
		}
		for (int j = 0; j < eventOverViewText.size() ; j++) {
			eventOverViewText.get(j).sendKeys(EventConfig.eventOverView);
		}	
	}
	
	public void enterDescriptionInEventInfo() {
			Utility.waitForWebElement(driver, DescriptionInEventInfo, 0);
			util.enterText(driver, DescriptionInEventInfo, "entered description");
			log.info("Description has been entered");
		}
		
		public void enterwhenWhereSummaryInEventInfo() {
			String todayDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
			System.out.println(todayDate.replace("/", ""));
			String date = todayDate.replace("/", "");   
			Utility.waitForWebElement(driver, whenWhereSummaryInEventInfo, 0);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("document.getElementsByClassName('bold slds-m-bottom_small')[0].textContent="+date);
			log.info("When and where summary entered");
		}
		
		public void selecteventDisplayNameAndDT() {
			Assert.assertTrue(eventDisplayNameAndDT.isDisplayed());
			log.info("CheckBox is selected event display name and dt");
		}
		public void selectisFeaturedEvent() {
			Assert.assertTrue(isFeaturedEvent.isDisplayed());
			log.info("Checkbox is selected for feature event");
		}
		
		public void selecttimeZoneIneventInfo(String timeZone) {
			Utility.waitForWebElement(driver, timeZoneIneventInfo, 0);
			util.selectDropDownByText(timeZoneIneventInfo, timeZone);
			log.info("time zone in event info selected");
		}
		
		public void enterStartDateInEventInfo() {
		    futureDate = DateUtils.setFutureDAte();
			Utility.waitForWebElement(driver, eventStartDate, 0);
			util.enterText(driver, eventStartDate, futureDate);
		}
}
