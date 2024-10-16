package org.aia.pages.fonteva.events;

import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import groovy.transform.Final;

public class AgendaModule {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Events events;
	Actions act;
	static Logger log = Logger.getLogger(AgendaModule.class);
	public String salesOrder = "";
	public String aiaNumber = "";

	public AgendaModule(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
		events = PageFactory.initElements(driver, Events.class);
	}

	// EventApi:EventBuilderAgenda
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderAgenda']")
	WebElement eventBuilderAgenda;

	@FindBy(css = "table[class='slds-table slds-table_header-fixed slds-table_bordered slds-table_edit'] tr:nth-child(5)  th lightning-base-formatted-text")
	WebElement scheduleItemDisplayName;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a[@data-tab='agenda']")
	WebElement agendaTab;

	@FindBy(xpath = "//button[text()='New Schedule Item']")
	WebElement newScheduleItem;

	@FindBy(xpath = "//h2[text()='Add Schedule Item']")
	WebElement headerPopupInSceduleItem;

	@FindBy(css = "label[data-label='Active when the event is active'] input")
	WebElement activeCheckboxInsceduleItem;

	@FindBy(css = "div[data-label='Schedule item name'] input")
	WebElement sceduleItemName;

	@FindBy(css = "div[data-label='Capacity'] input")
	WebElement capacityInscheduleItem;

	@FindBy(css = "div[data-label='Display Name'] input")
	WebElement displayNameInscheduleItem;

	@FindBy(css = "div[data-label='Price'] input")
	WebElement priceInscheduleItem;

	@FindBy(css = "div[data-label='Start Date'] input")
	WebElement startDateInscheduleItem;

	@FindBy(xpath = "//div[@data-label='Start Time']//select[@name='Start Time']")
	WebElement startTimeInscheduleItem;

	@FindBy(xpath = "//div[@class='slds-form-element display-element is-required']//div[@data-name='startMin']//select")
	WebElement startMinInScheduleItem;

	@FindBy(xpath = "//div[@class='slds-form-element display-element is-required']//div[@data-name='startAMPM']//select")
	WebElement startAmPmSceduleItem;

	@FindBy(css = "div[data-name='durationHour']")
	WebElement durationHourInScheduleItem;

	@FindBy(css = "div[data-name='durationHour'] input")
	WebElement durationHourInput;

	@FindBy(css = "div[data-name='durationMin'] select")
	WebElement durationMinInScheduleItem;

	@FindBy(xpath = "//label[@data-label='Allow Conflicts']/input")
	WebElement allowConflictsInScheduleItem;
	
	@FindAll(value = {@FindBy(xpath = "//div[@data-name='sessionEndDatetime']/following::div[@class='selectize-dropdown-content']//div[@data-value]")}) List<WebElement> speakerOptionsInNewSchedule;

	@FindBy(xpath = "//h2[text()='Add Schedule Item']/following::div[@data-name='speakers']//div[contains(@class,'selectize-input items not-full')]")
	WebElement speakerInScheduleItem;

	@FindBy(xpath = "//h2[text()='Add Schedule Item']/following::div[@data-name='speakers']//div[contains(@class,'selectize-input items not-full has-options')]/input")
	WebElement speakerInputInScheduleItem;

	@FindBy(xpath = "//div[@data-name='eventStartAndEndDate']/following::div[@data-name='description']//div[@class='fr-element fr-view cke_editable']//p")
	WebElement descriptionInscheduleItem;

	@FindBy(xpath = "//button[@data-name='saveUpdateScheduleItem']")
	WebElement buttonsInScheduleItem;

	public void clickEventAgenda() {
		Utility.waitForWebElement(driver, eventBuilderAgenda, 0);
		util.clickUsingJS(driver, eventBuilderAgenda);
		log.info("Event Agenda is clicked successfully");
		util.waitUntilElement(driver, agendaTab);
		Assert.assertTrue(agendaTab.isDisplayed());
		log.info("agendaTab is displayed");
	}

	public void clickNewScheduleItem() {
		Utility.waitForWebElement(driver, newScheduleItem, 10);
		newScheduleItem.click();
		log.info("New Schedule itm is clicked successfully");
		Utility.waitForWebElement(driver, headerPopupInSceduleItem, 10);
		Assert.assertTrue(headerPopupInSceduleItem.isDisplayed());
	}

	public void validateActiveCheckBoxInScheduleItem() throws Throwable {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, activeCheckboxInsceduleItem, 0);
		if (activeCheckboxInsceduleItem.isSelected()) {
			System.out.println("Active checkobox is enabled");
		} else {
			util.clickUsingJS(driver, activeCheckboxInsceduleItem);
			log.info("Active CheckBox is clicked");
		}
	}

	public void entersceduleItemName() {
		EventConfig.scheduleNameInputFieldInAgenda = util.randomStringGenerator(5);
		Utility.waitForWebElement(driver, sceduleItemName, 0);
		util.enterText(driver, sceduleItemName, EventConfig.scheduleNameInputFieldInAgenda);
		log.info("scedule item name enterd");
	}

	public void entercapacityInscheduleItem() {
		Utility.waitForWebElement(driver, capacityInscheduleItem, 0);
		util.enterText(driver, capacityInscheduleItem, "2");
		log.info("capacity in schedule item enterd");

	}

	public void enterdisplayNameInscheduleItem() {
		EventConfig.scheduleDisplayNameInputFieldInAgenda = util.randomStringGenerator(5);
		Utility.waitForWebElement(driver, displayNameInscheduleItem, 0);
		util.enterText(driver, displayNameInscheduleItem, EventConfig.scheduleDisplayNameInputFieldInAgenda);
		log.info("display name in schedule item enterd");
	}

	public void enterpriceInscheduleItem() {
		Utility.waitForWebElement(driver, priceInscheduleItem, 0);
		util.enterText(driver, priceInscheduleItem, "460");
		log.info("price in schedule item enterd");
	}

	public void enterstartDateInscheduleItem() {
		String startDate = new SimpleDateFormat("MM/dd/yyyy").format(new Date());
		Utility.waitForWebElement(driver, startDateInscheduleItem, 0);
		util.enterText(driver, startDateInscheduleItem, startDate);
		log.info("start date in schedule item enterd");
	}

	public void selectstartTimeInscheduleItem() {
		Utility.waitForWebElement(driver, startTimeInscheduleItem, 0);
		util.selectDropDownByText(startTimeInscheduleItem, "08");
		log.info("start time in schedule item selected");
	}

	public void selectstartMinInScheduleItem() {
		Utility.waitForWebElement(driver, startMinInScheduleItem, 0);
		util.selectDropDownByText(startMinInScheduleItem, "18");
		log.info("start min in schedule item selected");
	}

	public void selectstartAmPmSceduleItem() {
		Utility.waitForWebElement(driver, startAmPmSceduleItem, 0);
		util.selectDropDownByText(startAmPmSceduleItem, "AM");
		log.info("start AMPM in schedule item selected");
	}

	public void enterDurationHourInScheduleItem() {
		Utility.waitForWebElement(driver, durationHourInScheduleItem, 0);
		durationHourInScheduleItem.click();
		util.enterText(driver, durationHourInput, "05");
		durationHourInput.sendKeys(Keys.TAB);
		log.info("duration in schedule item selected");
	}

	public void selectDurationMinInScheduleItem() {
		Utility.waitForWebElement(driver, durationMinInScheduleItem, 0);
		util.selectDropDownByText(durationMinInScheduleItem, "06");
		log.info("duration in schedule item selected");
	}

	public void selectallowConflictsInScheduleItem() {
		Assert.assertFalse(allowConflictsInScheduleItem.isSelected(), "checkbox is not selected");
	}

	public ArrayList<String> enterspeakerInScheduleItem() {
		log.info(speakerOptionsInNewSchedule.size());
		speakerInScheduleItem.click();
		ArrayList<String> speakerList = new ArrayList<String>();
		for (int i = 0; i < speakerOptionsInNewSchedule.size(); i++) {
			WebElement option = speakerOptionsInNewSchedule.get(i);
			System.out.println(option.getAttribute("textContent"));
			speakerList.add(option.getAttribute("textContent"));
			if(option.getAttribute("textContent").equals(EventConfig.speakerNameInputField)) {
				option.click();
			}
		}
		return speakerList;
	}

	public void enterdescriptionInscheduleItem() throws InterruptedException {
		Thread.sleep(2000);
		util.scrollingElementUsingJS(driver, descriptionInscheduleItem);
		Utility.waitForWebElement(driver, descriptionInscheduleItem, 0);
		descriptionInscheduleItem.click();
		util.enterText(driver, descriptionInscheduleItem, "Description enterd");
		log.info("description in schedule item enterd");
	}

	public void clickbuttonsInScheduleItem() {
		Utility.waitForWebElement(driver, buttonsInScheduleItem, 0);
		buttonsInScheduleItem.click();
		log.info("save button in schedule item clicked");
	}

}
