package org.aia.pages.memberPortal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyInformation {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	ConfigDataProvider testData;

	public MyInformation(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		testData = new ConfigDataProvider();
	}

	@FindBy(xpath = "//a[@title='Contact Information']")
	WebElement contactInformation;

	@FindBy(xpath = "//a[@title='Membership']")
	WebElement memberShip;

	@FindBy(xpath = "//a[@title='Communication Preferences']")
	WebElement communicationProcess;

	@FindBy(xpath = "//a[@title='Professional Information']")
	WebElement professionalInfo;

	@FindBy(xpath = "//a[@title='Demographics']")
	WebElement demographics;

	@FindBy(xpath = "//a[@title='Interest Areas']")
	WebElement interestAreas;

	@FindBy(xpath = "//a[@title='Transactions History Receipts']")
	WebElement transactionsHistory;

	@FindBy(xpath = "//a[@title='My Event Registrations']")
	WebElement myEventRegistration;

	public void verifyMyinformationTabSections() throws Throwable {
		Thread.sleep(7000);
		util.waitUntilElement(driver, contactInformation);
		Assert.assertTrue(memberShip.isDisplayed());
		Assert.assertTrue(communicationProcess.isDisplayed());
		Assert.assertTrue(professionalInfo.isDisplayed());
		Assert.assertTrue(demographics.isDisplayed());
		Assert.assertTrue(interestAreas.isDisplayed());
		util.scrollingElementUsingJS(driver, transactionsHistory);
		Assert.assertTrue(transactionsHistory.isDisplayed());
		Assert.assertTrue(myEventRegistration.isDisplayed());
	}

}
