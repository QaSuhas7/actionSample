package org.aia.pages.fonteva.membership;

import static org.testng.Assert.assertTrue;

import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class Memberships {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	ContactCreateUser createOrder;
	Actions action;
	JavascriptExecutor executor;

	/**
	 * @param Idriver
	 */
	public Memberships(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
		createOrder = new ContactCreateUser(driver);

	}

	String userContact = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[@field-label='Contact']//a//slot//slot/span[contains(text(),'%s')]";

	String listQuickLink = "//a/slot/span[contains(text(),'%s')]";

//	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]")
	@FindBy(xpath = "//slot[contains(text(),'Memberships')]/ancestor::a")
	WebElement membership;

	@FindBy(xpath = "//table[@aria-label='Memberships']//tbody//tr//th//a")
	WebElement membershipSubId;

	@FindBy(xpath = "//h2//span[@title='Terms']/parent::a")
	WebElement terms;
	@FindBy(xpath = "//table[@aria-label='Terms']/tbody/tr/th//a")
	WebElement termId;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	@FindBy(xpath = "//table[@aria-label='Memberships']/tbody/tr/th")
	WebElement tableSubscriptionId;

	@FindBy(xpath = "//input[@name='OrderApi__Term_End_Date__c']")
	WebElement inputTermEndDate;

	@FindBy(xpath = "//input[@name='OrderApi__Grace_Period_End_Date__c']")
	WebElement inputTermGraceDate;

//	@FindBy(xpath = "//button[@title='Edit Term End Date']/span")
	@FindBy(xpath = "//button[@title='Edit Term End Date']")
	WebElement editBtn;

	String contactName = "(//span[text()='%s']//ancestor::a)[2]";

	@FindBy(xpath = "//a[contains(text(),'Show All')]")
	WebElement showAll;

	@FindBy(xpath = "//button[text()='Join']")
	WebElement reJoinBtn;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBy(xpath = "(//span[text()='Edit Status']/ancestor::button)[2]")
	WebElement statusEditBtn;

	@FindBy(xpath = "//button[@data-value='Active']")
	WebElement statusDrpBtn;

	String selectStatus = "//span[text()='%s']";

	@FindBy(xpath = "//button[@title='Edit Membership Expire Date']")

	WebElement expireMembershipEditBtn;

	@FindBy(xpath = "//input[@name='AIA_Membership_Expire_Date__c']")
	WebElement editexpireMembership;

	String contactInRec = "//span[text()='%s']//ancestor::a";

	@FindBy(xpath = "//records-record-layout-item[@field-label='Contact']//a")
	WebElement contactNameInReceipt;

	@FindBy(xpath = "//span/input[@name='OrderApi__Enable_Auto_Renew__c']")
	WebElement autoRenewCheckBoxInmemberShip;

	@FindBy(xpath = "//button[@title='Edit Enable Auto Renew']/following::records-record-layout-item[@field-label='Payment Method' ]//a")
	WebElement payemntMethodInAutoRenewSection;

	@FindBy(xpath = "//div/span[text()='Subscription Plan']/following::dd//a//span/slot/span/slot[contains(text(),'Dues - Payment in Full (Auto Renw)')]")
	WebElement subscriptionPlanIndetails;

	/**
	 * @param userFullname
	 * @throws InterruptedException
	 * 
	 */
	public void terminateUser(String userFullname) throws InterruptedException {
//		util.waitUntilElement(driver, membership);
//		action.moveToElement(membership).build().perform();
//		executor.executeScript("arguments[0].click();", membership);
//		util.waitUntilElement(driver, membershipSubId);
//		executor.executeScript("arguments[0].click();", membershipSubId);
//		// membershipSubId.click();
		util.waitUntilElement(driver, terms);
		action.moveToElement(terms).build().perform();
		executor.executeScript("arguments[0].click();", terms);
		// terms.click();
		util.waitUntilElement(driver, termId);
		executor.executeScript("arguments[0].click();", termId);
		// termId.click();
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		action.scrollToElement(editBtn).build().perform();
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		inputTermEndDate.sendKeys(data.testDataProvider().getProperty("tremendDate"));
		util.waitUntilElement(driver, inputTermGraceDate);
		inputTermGraceDate.clear();
		inputTermGraceDate.sendKeys(data.testDataProvider().getProperty("termGraceDate"));
		saveBtn.click();
		Thread.sleep(7000);
//		executor.executeScript("window.scrollBy(0,-550)", "");
//		Thread.sleep(6000);
//		executor.executeScript("arguments[0].click();",
//				util.getCustomizedWebElement(driver, contactName, userFullname));
	}

	/**
	 * @param fullName
	 * @param membershipStatus
	 * @throws InterruptedException
	 */
	public void setMembershipStatus(String fullName, String membershipStatus) throws InterruptedException {
		util.waitUntilElement(driver, membership);
		action.moveToElement(membership).build().perform();
		executor.executeScript("arguments[0].click();", membership);
		util.waitUntilElement(driver, membershipSubId);
		executor.executeScript("arguments[0].click();", membershipSubId);
		util.waitUntilElement(driver, statusEditBtn);
		executor.executeScript("arguments[0].scrollIntoView(true);", statusEditBtn);
		executor.executeScript("arguments[0].click();", statusEditBtn);
		Thread.sleep(1000);
		executor.executeScript("arguments[0].click();", statusDrpBtn);
		WebElement selectStatus = driver.findElement(By.xpath("//span[text()='" + membershipStatus + "']"));
		Utility.waitForWebElement(driver, selectStatus, 20);
		selectStatus.click();
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, selectStatus, membershipStatus));
//		util.getCustomizedWebElement(driver, selectStatus, membershipStatus).click();
		util.waitUntilElement(driver, saveBtn);
		saveBtn.click();
		Thread.sleep(12000);
	}

	/**
	 * @throws InterruptedException
	 */
	public void expireMembership() throws InterruptedException {
		executor.executeScript("window.scrollBy(0,500)", "");
		util.waitUntilElement(driver, expireMembershipEditBtn);
		executor.executeScript("arguments[0].click();", expireMembershipEditBtn);
		// expireMembershipEditBtn.click();
		util.enterText(driver, editexpireMembership, data.testDataProvider().getProperty("expireMembership"));
		util.waitUntilElement(driver, saveBtn);
		saveBtn.click();
		Thread.sleep(12000);
	}

	/**
	 * Here we select contact from receipt
	 */
	public void selectContactInRec(String fullName) {
		try {
			Thread.sleep(5000);
			executor.executeScript("window.scrollBy(0,250)", "");
			Thread.sleep(5000);
			util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactInRec, fullName));
			executor.executeScript("arguments[0].click();",
					util.getCustomizedWebElement(driver, contactInRec, fullName));
			util.waitUntilElement(driver, showAll);
			showAll.click();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void navigateToMemberShip() throws InterruptedException {
		util.waitUntilElement(driver, contactNameInReceipt);
		util.clickUsingJS(driver, contactNameInReceipt);
		Thread.sleep(6000);
		util.waitUntilElement(driver, showAll);
		showAll.click();
//		util.mosueOverUsingAction(driver, reJoinBtn);
		util.waitUntilElement(driver, membership);
		util.clickUsingJS(driver, membership);
//		membership.click();
		util.waitUntilElement(driver, membershipSubId);
		util.clickUsingJS(driver, membershipSubId);
	}

	public void selectMembership() {
		util.waitUntilElement(driver, membership);
		util.clickUsingJS(driver, membership);
//		membership.click();
		util.waitUntilElement(driver, membershipSubId);
		util.clickUsingJS(driver, membershipSubId);
	}

	public void autoRenewSectionValidation() throws InterruptedException {
		Thread.sleep(10000);
		System.out.println(driver.manage().window().getSize());
		((JavascriptExecutor) driver).executeScript("scroll(0,1200)");
		util.waitUntilElement(driver, autoRenewCheckBoxInmemberShip);
		String status = autoRenewCheckBoxInmemberShip.getAttribute("checked");
		if (status.equals("true")) {
			log.info("Auto Renew Checkbox is enabled");
		}
		util.waitUntilElement(driver, payemntMethodInAutoRenewSection);
		String value = payemntMethodInAutoRenewSection.getAttribute("text");
		if (value != null) {
			log.info("payment method value is auto populated");
			System.out.println("payment method value is auto populated");
		}
		util.scrollingElementUsingJS(driver, subscriptionPlanIndetails);
		((JavascriptExecutor) driver).executeScript("scroll(0,200)");
		String subscriptionPlanText = subscriptionPlanIndetails.getText();

		if (subscriptionPlanText.equalsIgnoreCase("Dues - Payment in Full (Auto Renw)")) {
			log.info("Subscription plan " + subscriptionPlanText + " is displayed");
		}

	}

	public void navigateTOContactPage(String userFullName) throws InterruptedException {

		WebElement userContact = driver.findElement(By.xpath(
				"//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[@field-label='Contact']//a//slot//slot/span[contains(text(),'"
						+ userFullName + "')]"));
		util.clickUsingJS(driver, userContact);
		Thread.sleep(6000);
		util.waitUntilElement(driver, showAll);
		showAll.click();
	}

	public void navigatememberShipAccountLinks(String linkName,String table) throws InterruptedException {
		WebElement listQuickLink = driver.findElement(By.xpath("//a[contains(@href,'"+linkName+"__r')]"));
		util.clickUsingJS(driver, listQuickLink);
		List<WebElement> linkOflinsts = driver
				.findElements(By.xpath("//table[@aria-label='"+table+"']//tbody//th//a"));
		switch (linkName) {
		case "Membership Transfers":
			if (linkOflinsts.size() != 0) {
				System.out.println("membership transfer data is displayed");
			}
			break;
		case "Sales Orders":
			if (linkOflinsts.size() == 2) {
				System.out.println("New sales order is created");
			}
			break;

		}
	}

	// a/slot/span[contains(text(),'Membership Transfers')]

}
