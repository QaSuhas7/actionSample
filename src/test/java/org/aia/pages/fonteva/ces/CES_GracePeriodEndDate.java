package org.aia.pages.fonteva.ces;

import static org.testng.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DateUtils;
import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.AddHasCasting;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;
import io.cucumber.java.it.Data;

public class CES_GracePeriodEndDate {

	WebDriver driver;
	Actions action;

	public CES_GracePeriodEndDate(WebDriver Idriver) {

		this.driver = Idriver;
		action = new Actions(driver);
	}

	ConfigDataProvider data = new ConfigDataProvider();
	Utility util = new Utility(driver, 10);

	@FindBy(xpath = "//input[@id='username']")
	WebElement userName;

	@FindBy(xpath = "//input[@id='password']")
	WebElement password;

	@FindBy(xpath = "//input[@id='Login']")
	WebElement loginBtn;

	@FindBy(xpath = "//*[@title='Contacts']/span")
	WebElement contacts;

	@FindBy(xpath = "//span[text()='App Launcher']/parent::div//parent::button")
	WebElement appLauncherIcn;

	@FindBy(xpath = "//label[text()='Search apps and items...']/parent::div/div/input")
	WebElement appSearchtxtbx;

	@FindBy(xpath = "//b[text()='Provider Application']")
	WebElement searchedAppPA;

	@FindBy(xpath = "//table[@aria-label='Recently Viewed']/tbody/tr/th")
	WebElement tableProviderApp;

	@FindBy(xpath = "//button[@title='Select a List View'] | //button[contains(@title,'Select a List View')]")
	WebElement selectList;

	@FindBy(xpath = "//span[@class=' virtualAutocompleteOptionText'and text()='All']")
	WebElement allBtn;

	@FindBy(xpath = "//table[@aria-label='All']/tbody/tr")
	WebElement tableAllProviders;

	@FindBy(xpath = "//span[text()='Application Status']/parent::div/parent::div//button")
	WebElement editAppStatusIon;

	@FindBy(xpath = "//button[contains(@aria-label, 'Application Status,')]")
	WebElement appStatusBtnDrpdwn;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	////////////// Membership

	@FindBy(xpath = "//a[@title='Contacts']/parent::one-app-nav-bar-item-root")
	WebElement contactsDiv;

	@FindBy(xpath = "//div[@class='uiVirtualDataTable indicator']")
	WebElement tableDiv;

	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]//ancestor::a")
	WebElement memberShip;

	@FindBy(xpath = "//a/span[@title='Name']")
	WebElement tableheaderName;

	@FindBy(xpath = "//h2//span[@title='Terms']")
	WebElement Terms;

	// @FindBy(xpath="//table[@aria-label='Terms']/tbody/tr/th//span/a |
	// //table[@aria-label='Terms']/tbody/tr/th//span//ancestor::a") WebElement
	// termId;
	@FindBy(xpath = "//table[@aria-label='Terms']/tbody/tr/th//span//ancestor::a")
	WebElement termId;

	@FindBy(xpath = "//table[@aria-label='Memberships']/tbody/tr/th//div")
	WebElement tableSubscriptionId;

	@FindBy(xpath = "//input[@name='OrderApi__Term_End_Date__c']")
	WebElement inputTermEndDate;

	@FindBy(xpath = "//input[@name='OrderApi__Grace_Period_End_Date__c']")
	WebElement inputTermGraceDate;

	@FindBy(xpath = "//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody/tr/th")
	WebElement Name;

	@FindBy(xpath = "//button[@title='Edit Term End Date']/span")
	WebElement editBtn;

	@FindBy(xpath = "//a[contains(text(),'Show')]")
	WebElement showallBtn;

	@FindBy(xpath = "//h1/span[text()='Contacts']/parent::h1/parent::div/parent::div//button")
	WebElement contactallBtn;

	@FindBy(xpath = "//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//span[text()='All Contacts'][1]")
	WebElement contactallLink;

	@FindBy(xpath = "//div[text()='Contact']")
	WebElement contactTitle;

	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	WebElement searchBox;

	@FindBy(xpath = "//span[text()='No items to display.']")
	WebElement noItemHeading;

	@FindBy(xpath = "//p[text()='Account Name']//parent::div//div//a")
	WebElement accountName;

	@FindBy(xpath = "(//*[@role = 'grid']//tbody//tr//td)[2]//a")
	WebElement selectPId;

	@FindBy(xpath = "//span[text()='Provider Application Number']/parent::div/parent::div//div//span//slot/*[@slot='outputField']")
	WebElement pAppNumber;

	@FindBy(xpath = "//span[text()='Owner']/parent::div/parent::div//slot/span")
	WebElement ownerName;

	@FindBy(xpath = "//span[text()='Original Application Type']/parent::div/parent::div//div//span//slot/*[@slot='outputField']")
	WebElement originalAppType;

	@FindBy(xpath = "//span[text()='Application Status']/parent::div/parent::div//div//span//slot/*[@slot='outputField']")
	WebElement appStatus;

	@FindBy(xpath = "//span[text()='Attestation Date']/parent::div/parent::div//div//span//slot/*[@slot='outputField']")
	WebElement attestationDate;

	@FindBy(xpath = "//span[text()= 'Account']/parent::div/parent::div//a//span")
	WebElement accounttxt;

	@FindBy(xpath = "//span[text()='Organization Name']/parent::div/parent::div//div//span//slot/*[@slot='outputField']")
	WebElement organizationName;

	@FindBy(xpath = "//span[text()= 'Telephone']/parent::div/parent::div//a")
	WebElement telephonetxt;

	@FindBy(xpath = "//span[text()='CES Provider Number']/parent::div/parent::div//div//span//slot/*[@slot='outputField']")
	WebElement cesProviderNum;

	@FindBy(xpath = "//div//span[text()='CES Provider Status']/following::div/span/slot/lightning-formatted-text[text()='Not Active']")
	WebElement cesProviderStatus;

	@FindBy(xpath = "//span[text()='CES Membership Type']/parent::div/parent::div//div//span//slot/*[@slot='outputField']")
	WebElement cesMembershipType;

	@FindBy(xpath = "//*[contains(text(),'Open Memberships')]")
	WebElement membershipslnk;

	@FindBy(xpath = "(//*[@role = 'table']//tbody//tr//td)[4]//div//div")
	WebElement availableMemType;

	@FindBy(xpath = "//div//a[@title='AutomationOrg']")
	WebElement accountPage;

	@FindBy(xpath = "//*[contains(text(),'Open Sales Orders')]")
	WebElement salesOrderlnk;

	@FindBy(xpath = "//*[@data-label='Sales Order #']//a//span")
	WebElement salesOrderNumber;

	@FindBy(xpath = "//*[contains(text(),'Open Receipts')]")
	WebElement receiptslnk;

	@FindBy(xpath = "//*[@data-label='Receipt Number']//a//span")
	WebElement receiptNumber;

	@FindBy(xpath = "//*[contains(text(),'Open Transaction Lines')]")
	WebElement transcationsLines;

	@FindBy(xpath = "//*[contains(text(),'Open Points of contact')]")
	WebElement pointsofContactlnk;

	@FindBy(xpath = "//*[@aria-label='Points of contact']//th//div//span//a")
	WebElement pointsofContactName;

	@FindBy(xpath = "//button[@title='Edit Application Status']")
	WebElement editApplicationStatus;

//	@FindBy(xpath = "//span[text()= 'Account']/parent::div/parent::div//a//span")
	@FindBy(xpath = "//a//span[text()='AutomationOrg']")
	WebElement selectAccount;

	@FindBy(xpath = "//div[contains(text(),'Your entry does not match the allowed format M/d/yyyy.')]")
	WebElement dateFormatError;

	String startLocator = "//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody//a[text()='";
	String endLocator = "']";
	String appName = "Provider Application";

	/**
	 * Here ,we verify GracePeriodEndDate after changing termEndDate
	 * 
	 * @throws InterruptedException
	 */

	public void validateGracePeriodEndDate() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, memberShip);
		action.moveToElement(memberShip).click().perform();
		System.out.println("Memberships clicked");
		driver.navigate().refresh();
		util.waitUntilElement(driver, tableSubscriptionId);
		Thread.sleep(1000);
		tableSubscriptionId.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, Terms);
		Terms.click();
		util.waitUntilElement(driver, termId);
		js.executeScript("arguments[0].click();", termId);
		// termId.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		action.scrollToElement(editBtn);
		js.executeScript("window.scrollBy(0,200)", editBtn);
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		String inputTermEndDateValue = inputTermEndDate.getText();
		System.out.println("Input TermEndDate Value is: " + inputTermEndDateValue);
		util.waitUntilElement(driver, inputTermGraceDate);
		String inputTermGraceDateValue = inputTermGraceDate.getText();
		System.out.println("Input TermGraceDate Value is:" + inputTermGraceDateValue);
		assertTrue(inputTermGraceDateValue.equalsIgnoreCase(inputTermEndDateValue));

	}

	/**
	 * Here, we verify the dateFormatError
	 * 
	 * @throws InterruptedException
	 */
	public void validateDateFormatError() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, memberShip);
		action.moveToElement(memberShip).click().perform();
		System.out.println("Memberships clicked");
		driver.navigate().refresh();
		util.waitUntilElement(driver, tableSubscriptionId);
		Thread.sleep(1000);
		tableSubscriptionId.click();
		util.waitUntilElement(driver, Terms);
		Terms.click();
		util.waitUntilElement(driver, termId);
		js.executeScript("arguments[0].click();", termId);
		// termId.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		action.scrollToElement(editBtn);
		js.executeScript("window.scrollBy(0,200)", editBtn);
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		inputTermEndDate.sendKeys(data.testDataProvider().getProperty("incorrectDateFormat"));
		saveBtn.click();
		Thread.sleep(1000);
		action.sendKeys(Keys.F5);
		Thread.sleep(2000);
		util.waitUntilElement(driver, dateFormatError);
		String dateFormatErrorValue = dateFormatError.getText();
		assertTrue(dateFormatErrorValue.equalsIgnoreCase(data.testDataProvider().getProperty("dateFormatErrorMsg")));

	}

	/**
	 * Here, termEndDate and GracePeriodEndDates will be modified. termEndDate is
	 * >32 days GracePeriodEndDate will be today's date
	 * 
	 * @throws InterruptedException
	 */

	public void changeTermandGracePeriodDates() throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, memberShip);
		action.moveToElement(memberShip).click().perform();
		System.out.println("Memberships clicked");
		driver.navigate().refresh();
		util.waitUntilElement(driver, tableSubscriptionId);
		Thread.sleep(1000);
		tableSubscriptionId.click();
		util.waitUntilElement(driver, Terms);
		Terms.click();
		util.waitUntilElement(driver, termId);
		js.executeScript("arguments[0].click();", termId);
		// termId.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		action.scrollToElement(editBtn);
		js.executeScript("window.scrollBy(0,200)", editBtn);
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		DateUtils dateUtils = new DateUtils();
		String previousDate = dateUtils.getDate(-32, "MM/dd/yyyy");
		System.out.println("date before -32 is: " + previousDate);
		inputTermEndDate.sendKeys(previousDate);
		util.waitUntilElement(driver, inputTermGraceDate);
		inputTermGraceDate.clear();
		String todaysDate = dateUtils.getDate(0, "MM/dd/yyyy");
		System.out.println("today's Date is: " + todaysDate);
		inputTermGraceDate.sendKeys(todaysDate);
		saveBtn.click();
		Thread.sleep(1000);
		action.sendKeys(Keys.F5);
		Thread.sleep(3000);
	}

	/**
	 * Here, on the account page, we verify the CESProviderStatus.
	 *
	 **/
	public void validateCESProviderStatus() throws InterruptedException {
		driver.navigate().refresh();
		driver.switchTo().alert().accept();
		Thread.sleep(3000);
		Utility.waitForWebElement(driver, selectAccount, 20);
		selectAccount.click();
		Thread.sleep(10000);
		System.out.println("Account selected");
		util.scrollingElementUsingJS(driver, cesProviderStatus);
		util.waitUntilElement(driver, cesProviderStatus);
		String cesProviderStatusValue = cesProviderStatus.getText();
		System.out.println("CES Provider Status Value is:" + cesProviderStatusValue);
		assertTrue(cesProviderStatusValue.equalsIgnoreCase(data.testDataProvider().getProperty("cesProviderStatus")));
	}
}
