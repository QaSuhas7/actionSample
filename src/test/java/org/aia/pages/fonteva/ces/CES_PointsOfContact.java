package org.aia.pages.fonteva.ces;

import static org.testng.Assert.assertTrue;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.netty.handler.timeout.TimeoutException;

public class CES_PointsOfContact {
	
	boolean clicked = false;
	int maxAttempts = 3;
	int attempts = 0;

	WebDriver driver;
	Actions action;
	JavascriptExecutor executor;

	public CES_PointsOfContact(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	Utility util = new Utility(driver, 10);

	@FindBy(xpath = "//input[@id='username']")
	WebElement userName;

	@FindBy(xpath = "//input[@id='password']")
	WebElement password;

	@FindBy(xpath = "//input[@id='Login']")
	WebElement loginBtn;

	@FindBy(xpath = "//*[@title='Contact']/span")
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
	
	@FindBy(xpath = "//div[@data-aura-class='sfaOutputNameWithHierarchyIcon']//lightning-formatted-text[text()='AutomationOrg']")
	WebElement automatiomOrgHeader;

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

	@FindBy(xpath = "//span[@class='error']")
	WebElement messageError;

	@FindBy(xpath = "//span[text()='AutomationOrg']")
	WebElement accountNameLink;

	@FindBy(xpath = "//slot/span[contains(text(),'Points of')]")
	WebElement pointsOfContactBtn;

	@FindBy(xpath = "//lightning-base-combobox//div/descendant::span[text()='CES Primary']")
	WebElement accountRole;

	@FindBy(xpath = "//slot/lightning-button/button[text() = 'Save']")
	WebElement pointsOfContactSaveBtn;

	@FindBy(xpath = "//slot/lightning-button/button[contains(text(),'Save & New')]")
	WebElement pointsOfContactSaveNewBtn;

	@FindBy(xpath = "//div//h2[text()='We hit a snag.']")
	WebElement newPointsOfContactError;

	@FindBy(xpath = "//slot//lightning-button//button[text()='New']")
	WebElement pointsOfContactNewBtn;

	@FindBy(xpath = "//table[@aria-label=\"Points of contact\"]//tbody//tr[2]//td[6]//button")
	WebElement chevronBtn;

	@FindBy(xpath = "//div[contains(@class,'actionMenu') and contains(@class, 'visible')]//a[@title='Edit']")
	WebElement chevronEditBtn;

	@FindBy(xpath = "//div[contains(@class,'actionMenu') and contains(@class, 'visible')]//a[@title='Delete']")
	WebElement chevronDeleteBtn;

	@FindBy(xpath = "//records-record-layout-item//lightning-combobox//button[@role='combobox']")
	WebElement accountRoleDropDown;

	String accountRoleDropDownValue = "//records-record-layout-item//lightning-combobox//span[@title='%s']";

	@FindBy(xpath = "//input[@placeholder='Search Contacts...']")
	WebElement searchBtn;

	@FindBy(xpath = "//span[text()='Delete']")
	WebElement deletePointsOfContact;

	@FindBy(xpath = "//tr[@data-row-number='1']//th//span//a[contains(text(), 'auto')]")
	WebElement contactLinkPOC;

	@FindBy(xpath = "//input[@placeholder='Search Contacts...']")
	WebElement searchContactPOC;

	@FindBy(xpath = "//lightning-base-combobox-item//span[contains(text(), 'auto')]")
	WebElement contactValuePOC;

	@FindBy(xpath = "//div[contains(@class,'modal-body')]")
	WebElement modalBody;

	@FindBy(xpath = "//span[contains(@class, 'toastMessage')]")
	WebElement successMessage;


	String startLocator = "//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody//a[text()='";
	String endLocator = "']";
	String appName = "Provider Application";

	public void clickPointsOfContact(String fullName, String providerID, String providerStatus)
			throws InterruptedException {
		/*
		 * util.waitUntilElement(driver, userName);
		 * userName.sendKeys("sgopisetty@innominds.com.aia.testing");
		 * password.sendKeys("Harshi@437"); loginBtn.click();
		 */
		util.waitUntilElement(driver, appLauncherIcn);
		Thread.sleep(10000);
		appLauncherIcn.click();
		util.waitUntilElement(driver, appSearchtxtbx);
		util.enterText(driver, appSearchtxtbx, appName);
		util.waitUntilElement(driver, searchedAppPA);
		WebElement provAppElement = Utility.waitForWebElement(driver, "//b[text()='" + appName + "']", 10);
		provAppElement.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, tableProviderApp);
		util.waitUntilElement(driver, selectList);
		selectList.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, allBtn);
		allBtn.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, searchBox);
		searchBox.sendKeys(providerID);
		Thread.sleep(3000);
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(30000);
		try {
			driver.findElement(By.xpath("//table[@aria-label='All']//tbody//a[@title='"+providerID+"']")).click();
		}catch (Exception e) {
			System.out.println("No items display");
		boolean ponumber = false;
		if (noItemHeading.isDisplayed()) {
			int count = 8;
			for (int i = 0; i < count && ponumber == false; i++) {
				Thread.sleep(10000);
				searchBox.clear();
				Thread.sleep(4000);
				searchBox.sendKeys(Keys.ENTER);
				Thread.sleep(4000);
				driver.findElement(
						By.cssSelector("a[class='toggle slds-th__action slds-text-link--reset '] span[title='Provider Application Number']"))
						.click();
				searchBox.sendKeys(providerID);
				Thread.sleep(3000);
				searchBox.sendKeys(Keys.ENTER);
				Thread.sleep(8000);
				try {
					WebElement providerElemnt = driver.findElement(By.xpath("//table[@aria-label='All']//tbody//a[@title='"+providerID+"']"));
					if (providerElemnt.isDisplayed()) {
						ponumber = true;
						if (ponumber == true) {
							util.clickUsingJS(driver, providerElemnt);
							System.out.println("provider number clicked successfully");
						} else {
							System.out.println("ponumber is not clicked");
						}

					} else {
						System.out.println("ponumber is not displayed");
					}
				} catch (Exception s) {
					System.out.println(s);
				}

			}
		}
		}

//		WebElement provAppIDElement = Utility.waitForWebElement(driver,
//				"//table[@aria-label='All']/tbody//a[@title='" + providerID + "']", 10);
//		provAppIDElement.click();
		Thread.sleep(10000);
		util.waitUntilElement(driver, accountNameLink);
		accountNameLink.click();
		util.waitForJavascript(driver, 30000, 5000);
		Thread.sleep(40000);
		Utility.waitForWebElement(driver, showallBtn, 20);
		showallBtn.click();
		Utility.waitForWebElement(driver, automatiomOrgHeader, 20);
		util.mosueOverUsingAction(driver, automatiomOrgHeader);
		util.waitUntilElement(driver, pointsOfContactBtn);
		pointsOfContactBtn.click();

	}

	public void validateErrorPointsOfContact() throws InterruptedException {
		util.waitUntilElement(driver, pointsOfContactNewBtn);
		pointsOfContactNewBtn.click();
		util.waitUntilElement(driver, accountRoleDropDown);
		accountRoleDropDown.click();
		util.getCustomizedWebElement(driver, accountRoleDropDownValue, "CES Secondary").click();
		util.waitUntilElement(driver, searchContactPOC);
		searchContactPOC.click();
		util.waitUntilElement(driver, contactValuePOC);
		contactValuePOC.click();
		util.waitUntilElement(driver, pointsOfContactSaveBtn);
		pointsOfContactSaveBtn.click();
		// edit the role to "CES Primary"
		accountroleChangePOC("CES Primary");
		util.waitUntilElement(driver, newPointsOfContactError);
		assertTrue(newPointsOfContactError.isDisplayed());

	}

	public void deletePointsOfContact() throws InterruptedException {


		while (!clicked && attempts < maxAttempts) {
			try {
				System.out.println("INFO: Edit flow Started... ");
				Thread.sleep(40000);
				util.waitUntilElement(driver, chevronBtn);
				chevronBtn.click();
				util.waitUntilElement(driver, chevronDeleteBtn);
				executor.executeScript("arguments[0].click();", chevronDeleteBtn);
				if (ExpectedConditions.visibilityOf(modalBody) != null) {
					System.out.println("INFO: Edit btn clicked ");
					clicked = true;
				} else {
					System.out.println("INFO: Failed to click edit btn");
					attempts++;
				}
			} catch (Exception e) {
				System.out.println("Wait: Edit button isn't visible " + e.getMessage());
				attempts++;
			}
		}

		if (clicked) {
			Thread.sleep(20000);
			deletePointsOfContact.click();

		}
	}

	public void accountroleChangePOC(String accountRole) throws InterruptedException {

		while (!clicked && attempts < maxAttempts) {
			try {
				System.out.println("INFO: Edit flow Started... ");
				Thread.sleep(40000);
				util.waitUntilElement(driver, chevronBtn);
				chevronBtn.click();
				util.waitUntilElement(driver, chevronEditBtn);
				executor.executeScript("arguments[0].click();", chevronEditBtn);
				if (ExpectedConditions.visibilityOf(modalBody) != null) {
					System.out.println("INFO: Edit btn clicked ");
					clicked = true;
				} else {
					System.out.println("INFO: Failed to click edit btn");
					attempts++;
				}
			} catch (Exception e) {
				System.out.println("Wait: Edit button isn't visible " + e.getMessage());
				attempts++;
			}
		}

		if (clicked) {
			Thread.sleep(40000);
			util.waitUntilElement(driver, accountRoleDropDown);
			accountRoleDropDown.click();
			Thread.sleep(2000);
			util.getCustomizedWebElement(driver, accountRoleDropDownValue, accountRole).click();
			util.waitUntilElement(driver, pointsOfContactSaveBtn);
			pointsOfContactSaveBtn.click();
		}
	}

	public void newPointsOfContact(String accountRole) throws InterruptedException {
		try {
		util.waitUntilElement(driver, pointsOfContactNewBtn);
		pointsOfContactNewBtn.click();
		util.waitUntilElement(driver, accountRoleDropDown);
		accountRoleDropDown.click();
		util.getCustomizedWebElement(driver, accountRoleDropDownValue, accountRole).click();
		util.waitUntilElement(driver, searchContactPOC);
		searchContactPOC.click();
		util.waitUntilElement(driver, contactValuePOC);
		contactValuePOC.click();
		util.waitUntilElement(driver, pointsOfContactSaveBtn);
		pointsOfContactSaveBtn.click();
		util.waitUntilElement(driver, contactLinkPOC);
		contactLinkPOC.click();
	} catch (ElementClickInterceptedException e) {
        // Log the error message
        Logger logger = Logger.getLogger(getClass().getName());
        logger.severe("ElementClickInterceptedException occurred: " + e.getMessage());

    }

	}
}
