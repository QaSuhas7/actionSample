package org.aia.pages.fonteva.events;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class StatusesModule {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Events events;
	Actions act;
	static Logger log = Logger.getLogger(StatusesModule.class);
	public String salesOrder = "";
	public String aiaNumber = "";

	public StatusesModule(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
		events = PageFactory.initElements(driver, Events.class);
	}

	// EventApi:EventBuilderStatuses
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderStatuses']")
	WebElement eventBuilderStatuses;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[contains(text(),'statuses for your event.')]")
	WebElement statusespageText;

	@FindBy(xpath = "//button[text()='New Status']")
	WebElement newStatuses;

	@FindBy(xpath = "//h2[text()='Create Event Status']")
	WebElement headerInNewStatusPopup;

	@FindBy(css = "div[data-label='Status Name'] input")
	WebElement statusName;

	@FindBy(css = "label[data-label='Registration Open'] input")
	WebElement registrationOpenInNewStatusCheckBox;

	@FindBy(css = "label[data-label='Published To Portal'] input")
	WebElement publishPortalInNewStatusCheckBox;

	@FindBy(css = "button[aria-label='Save & Close' ][data-name='saveStatus']")
	WebElement saveCloseButtonInNewStatus;

	public void editEventStatuses() {
		util.waitUntilElement(driver, eventBuilderStatuses);
		eventBuilderStatuses.click();
		log.info("Event Statuses is clicked successfully");
		util.waitUntilElement(driver, statusespageText);
		Assert.assertTrue(statusespageText.isDisplayed());
		log.info("statusespageText is displayed");
	}

	public void clicknewStatuses() {
		Utility.waitForWebElement(driver, newStatuses, 0);
		newStatuses.click();
		log.info("new Statuses is clicked successfully");
		Assert.assertTrue(headerInNewStatusPopup.isDisplayed());
		log.info("header in new status popup is displayed");
	}

	public void enterstatusName() {
		Utility.waitForWebElement(driver, statusName, 0);
		util.enterText(driver, statusName, "hold");
		log.info("status name enterd");
	}

	public void selectCheckboxForRegistration() {
		util.waitUntilElement(driver, registrationOpenInNewStatusCheckBox);
		Utility.highLightElement(driver, registrationOpenInNewStatusCheckBox);
		if (registrationOpenInNewStatusCheckBox.isSelected()) {
			System.out.println("Active checkobox is enabled");
		} else {
			util.clickUsingJS(driver, registrationOpenInNewStatusCheckBox);
			log.info("Active CheckBox is clicked");
			Assert.assertTrue(registrationOpenInNewStatusCheckBox.isSelected());
		}
	}

	public void selectCheckboxForPublishPortal() {
		util.waitUntilElement(driver, publishPortalInNewStatusCheckBox);
		Utility.highLightElement(driver, publishPortalInNewStatusCheckBox);
		if (publishPortalInNewStatusCheckBox.isSelected()) {
			System.out.println("Active checkobox is enabled");
		} else {
			util.clickUsingJS(driver, publishPortalInNewStatusCheckBox);
			log.info("Active CheckBox is clicked");
			Assert.assertTrue(publishPortalInNewStatusCheckBox.isSelected());
		}
	}

	public void clicksaveCloseButtonInNewStatus() {
		Utility.waitForWebElement(driver, saveCloseButtonInNewStatus, 0);
		saveCloseButtonInNewStatus.click();
		log.info("save button in new status clicked");
	}

}
