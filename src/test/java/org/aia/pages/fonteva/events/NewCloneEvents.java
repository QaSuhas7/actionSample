package org.aia.pages.fonteva.events;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

import org.testng.Assert;

public class NewCloneEvents {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	ConfigDataProvider testData;
	public String eName = "";
	public String newEvent = "";
	public String startDate = "";
	public String eventId = "";
	static Logger log = Logger.getLogger(NewCloneEvents.class);

	public NewCloneEvents(WebDriver Idriver) {
		this.driver = Idriver;
		executor = (JavascriptExecutor) driver;
		testData = new ConfigDataProvider();
	}

	@FindBy(xpath = "//a[contains(@class,'label-action dndItem')]/span[text()='Events']")
	WebElement eventsLink;

	@FindBy(xpath = "//a[@class='forceActionLink' and @title='New']")
	WebElement eventNewButton;

	@FindBy(xpath = "//div[@data-name='cloneEventTitle']")
	WebElement cloneEventHeader;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//input[@name='eventCreation']/following::span[text()='Clone an existing event']/preceding-sibling::input")
	WebElement existingCloneEvent;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-name='eventObjCloneName']/Input")
	WebElement eventName;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-name='startDate']//div/input")
	WebElement eventStartDate;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-name='cloneEvent']//select[@name='Event Category']")
	WebElement selectCategory;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] div[class*=selectize-input] input")
	WebElement eventSearch;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-name='cloneOptions']//div/label[contains(text(),'Select which segments you would like to clone')]")
	WebElement eventOptionTitle;

	@FindAll(value = { @FindBy(css = "div[class=selectize-dropdown-content] div") })
	List<WebElement> eventOptions;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] button[data-name='cloneContinueModal']")
	WebElement eventCloneButton;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] label[data-name='ticketCheck'] input")
	WebElement ticketCheckbox;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] label[data-name='scheduleCheck'] input")
	WebElement scheduleCheckbox;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] label[data-name='venueCheck'] input")
	WebElement venueCheckbox;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] label[data-name='vendorCheck'] input")
	WebElement vendorCheckbox;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] label[data-name='speakerCheck'] input")
	WebElement speakerCheckbox;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] label[data-name='sponsorCheck'] input")
	WebElement sponsorCheckbox;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] label[data-name='eventStatusPageCheck'] input")
	WebElement eventStatusPageCheckbox;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] button[data-name='finishCloneModal']")
	WebElement eventFinishCloneButon;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//slot[contains(@class,\"slds-page-header__title slds-m-right--small slds\")]//lightning-formatted-text")
	WebElement eventNameHeader;

	@FindBy(xpath = "//a[contains(text(),'/lt-event?')]")
	WebElement eventUrl;

	public String enterEventName(String template) {
		EventConfig.getEventName = "TestQA" + template + new SimpleDateFormat("MMddyyyyHHmmss").format(new Date());
		util.enterText(driver, eventName, EventConfig.getEventName);
		log.info("Entered Event name as" + EventConfig.getEventName);
		return EventConfig.getEventName;
	}

	/**
	 * @param eventCategory
	 * @throws InterruptedException
	 * @throws Throwable
	 */
	public void enterStartDate() throws InterruptedException, Throwable {

		startDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		util.enterText(driver, eventStartDate, startDate);
		log.info("Event Date is Entered");
	}

	public void selectEventCategory(String eventCategory) {
		util.waitUntilElement(driver, selectCategory);
		util.selectDropDownByText(selectCategory, eventCategory);
		log.info("Event category slected" + eventCategory);
	}

	public void validateCloneOnExistingRadioButton() throws InterruptedException {
		util.waitUntilElement(driver, existingCloneEvent);
		Thread.sleep(5000);
		boolean cloneEventRadioButton = existingCloneEvent.isSelected();
		Assert.assertTrue(cloneEventRadioButton);
		log.info("Existing clone event radio button is selected");
	}

	public void CloneEventSearchTemplate(String exitEvent) throws Throwable {
		util.waitUntilElement(driver, eventSearch);
		eventSearch.click();
		util.enterText(driver, eventSearch, exitEvent);
		Thread.sleep(3000);
		log.info("event list size" + eventOptions.size());
		for (int i = 0; i < eventOptions.size(); i++) {
			String event = eventOptions.get(i).getText();
			if (event.equals(exitEvent)) {
				eventOptions.get(i).click();
				System.out.println("matched");
				break;
			} else {
				System.out.println("not matched");
			}
		}
	}

	public void eventCloneButton() throws InterruptedException {
		Thread.sleep(2000);
		eventCloneButton.click();
	}

	public void verifyCloneEventSegmentCheckBoxs() throws InterruptedException {

		util.waitUntilElement(driver, eventOptionTitle);
		log.info(eventOptionTitle.getText());
		util.waitUntilElement(driver, ticketCheckbox);
		Assert.assertTrue(ticketCheckbox.isDisplayed());
		System.out.println("VERIFIED: Ticket checkbox is selected");
		log.info("VERIFIED: Ticket checkbox is selected");
		Assert.assertTrue(scheduleCheckbox.isDisplayed());
		System.out.println("VERIFIED: scheduleCheckbox is selected");
		log.info("VERIFIED: scheduleCheckbox is selected");
		Assert.assertTrue(venueCheckbox.isDisplayed());
		System.out.println("VERIFIED: venueCheckbox is selected");
		log.info("VERIFIED: venueCheckbox is selected");
		Assert.assertTrue(speakerCheckbox.isDisplayed());
		System.out.println("VERIFIED: speakerCheckbox is selected");
		log.info("VERIFIED: speakerCheckbox is selected");
		Assert.assertTrue(eventStatusPageCheckbox.isDisplayed());
		System.out.println("VERIFIED: eventStatusPageCheckbox is selected");
		log.info("VERIFIED: eventStatusPageCheckbox is selected");
	}

	public void eventFinishCloneButton() {
		eventFinishCloneButon.click();
		log.info("Clone button is clicked sucessfully");
	}

	public void validateEventHeader() {
		util.waitForJavascript(driver, 90000, 5000);
		util.waitUntilElement(driver, eventNameHeader);
		newEvent = eventNameHeader.getText();
		log.info(newEvent);
		System.out.println(eventNameHeader);
		Assert.assertTrue(eventNameHeader.isDisplayed());
		log.info("eventName header is displayed");
	}

	public String getEventId() {
		Utility.waitForWebElement(driver, eventUrl, 30);
		String url = eventUrl.getAttribute("href");
		try {
			// Parse the URL to extract the value of the "id" parameter
			URI uri = new URI(url);

			Map<String, String> params = getQueryParams(uri);

			// Get the ID parameter
			eventId = params.get("id");

			// Print the ID
			System.out.println("EVENTID: " + eventId);
			return eventId;
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return eventId;
	}

	/**
	 * @param uri
	 * @return
	 */
	private static Map<String, String> getQueryParams(URI uri) {
		Map<String, String> params = new java.util.HashMap<>();
		String query = uri.getQuery();

		if (query != null && !query.isEmpty()) {
			// Split the query string into individual parameters
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				if (keyValue.length == 2) {
					// Add the parameter to the map
					String key = keyValue[0];
					String value = keyValue[1];
					params.put(key, value);
				}
			}
		}

		return params;
	}

}