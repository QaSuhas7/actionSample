package org.aia.pages.fonteva.membership;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.apache.commons.io.serialization.ValidatingObjectInputStream;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.github.dockerjava.api.command.PullImageCmd;

import freemarker.template.utility.Execute;
import groovyjarjarantlr4.v4.runtime.tree.xpath.XPath;
import javassist.expr.NewArray;

/**
 * @author IM-RT-LP-1483(Suhas)
 *
 */
public class ContactCreateUser {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;

	/**
	 * @param Idriver
	 */
	public ContactCreateUser(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	@FindBy(xpath = "//input[@id='username']")
	WebElement userName;

	@FindBy(xpath = "//input[@id='password']")
	WebElement password;

	@FindBy(xpath = "//input[@id='Login']")
	WebElement loginBtn;

	@FindBy(xpath   = "//a[@title='Contacts']/span//parent::a")
	WebElement contacts;

	@FindBy(xpath = "//a[@title='Contacts']/parent::one-app-nav-bar-item-root")
	WebElement contactsDiv;

	@FindBy(xpath = "//div[text()='New']/parent::a")
	WebElement newBtn;

	@FindBy(xpath = "//input[@name='firstName']")
	WebElement firstName;

	@FindBy(xpath = "//input[@name='lastName']")
	WebElement lastName;

	@FindBy(xpath = "//input[@name='OrderApi__Personal_Email__c']")
	WebElement emailAddress;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	@FindBy(xpath = "//button[text()='Join']")
	WebElement joinBtn;

	String memType = "//span[@title='%s']";

	@FindBy(xpath = "//button[@name='AIA_Membership_Type__c']")
	WebElement selectMemTypeBtn;

	@FindBy(xpath = "//input[contains(@name,'Zip_Code')]")
	WebElement enterZipCode;

	@FindBy(xpath = "//button[contains(@name,'Career_Type')]")
	WebElement careerTypeDrp;

	String careerType = "//span[text()='%s']";

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtn;

	@FindBy(xpath = "//input[contains(@name,'License_Number')]")
	WebElement enterLicenseNumber;

	@FindBy(xpath = "//button[contains(@name,'License_State')]")
	WebElement licenseStateDrp;

	String state = "//span[text()='%s']";

	String country = "//span[text()='%s']";

	@FindBy(xpath = "//input[contains(@name,'License_Date')]")
	WebElement licenseStartDate;

	@FindBy(xpath = "//button[text()='Today']")
	WebElement selectTodayDate;

	@FindBy(xpath = "//input[contains(@name,'License_Expire_Date__c')]")
	WebElement licenseExpireDate;

	@FindBy(xpath = "//button[contains(@aria-label,'Subscription Plans')]")
	WebElement selectDuesDrp;

	@FindBy(xpath = "//span[contains(@title,'Payment in Full')]")
	WebElement selectDeusOpt;

	@FindBy(xpath = "//span[contains(@title,'Dues Installment Plan - 6 Installments')]")
	WebElement selectDueDip;

	@FindBy(xpath = "//span[contains(@title,'Dues Installment Plan ')]")
	WebElement selectPayInInsatllmentElement;

	@FindBy(xpath = "//button[contains(text(),'Create sales order')]")
	WebElement createSalesOrder;

	@FindBy(xpath = "//button[text()='Ready For Payment']")
	WebElement readyForPaymentBtn;

	@FindBy(xpath = "//button[text()='Apply Payment']")
	WebElement applyPayment;

	@FindBy(xpath = "//select[@aria-label='Payment Type']")
	WebElement paymentType;

	@FindBy(xpath = "//span[text()='Apply Payment']/parent::button")
	WebElement applyLastPayment;

	@FindBy(xpath = "//input[@name='full_name']")
	WebElement cardHolderName;

	@FindBy(xpath = "//input[@id='card_number']")
	WebElement cardNum;

	@FindBy(xpath = "//select[@aria-label='Exp month']")
	WebElement expMonth;

	@FindBy(xpath = "//select[@aria-label='Exp year']")
	WebElement expYear;

	@FindBy(xpath = "(//iframe[@title='accessibility title'])[3]")
	WebElement drpIframe;
	
	@FindBy(xpath = "//select[@aria-label='Payment Type']")
	WebElement option;

	@FindBy(xpath = "//iframe[@title='Payment Form']")
	WebElement cardNumIframe1;

	@FindBy(xpath = "//iframe[@title='Card number']")
	WebElement cardNumIframe2;

	@FindBy(xpath = "//span[text()='Process Payment']/parent::button")
	WebElement processPaymentBtn;

	// @FindBy(xpath = "//lightning-formatted-text[@slot='primaryField']")
	// WebElement receiptNo;

//	@FindBy(xpath = "//img[@title='Receipt']/following::div//slot[@name='primaryField']")
	@FindBy(xpath = "//img[@title='Receipt']/following::div//slot[@name='primaryField']//lightning-formatted-text")
	WebElement receiptNo;
	

	@FindBy(xpath = "(//a[contains(@href,'OrderApi__Sales_Order__c')])[2]/slot/slot/span")
	WebElement salesOrder;

	@FindBy(xpath = "(//p[text()='Total']/parent::div/p)[2]/slot/lightning-formatted-text")
	WebElement totalAmmount;

	@FindBy(xpath = "//h1/span[text()='Contacts']/parent::h1/parent::div/parent::div//button")
	WebElement contactallBtn;

	@FindBy(xpath = "//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//span[text()='All Contacts'][1]")
	//@FindBy(xpath = "//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//a/span[text()='All']")
	WebElement contactallLink;
	
	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	WebElement localSearch;
	
	@FindBy(xpath = "//span[@class='countSortedByFilteredBy']")
	WebElement itemCount;

	String contactName = "//a[text()='%s']";

	String contactInTable = "//table[@aria-label='All Contacts']//td[3]//span/a[@title='%s']";

	@FindBy(xpath = "//input[@aria-label='Search All Contacts list view.']")
	WebElement contactSearchInputBox;
	
	@FindBy(xpath = "//span[text()='No items to display.']")
	WebElement noItemHeading;
	
//	String contactName = "//a[@title='%s']";
	
	@FindBy(xpath = "//span[@title='AIA Number']/parent::a")
	WebElement assendingToggleBtn;

	@FindBy(xpath = "//a[contains(text(),'Show All (2')]")
	WebElement showAll;

	@FindBy(xpath = "//a/span[@title='Name']")
	WebElement tableheaderName;

	@FindBy(xpath = "//a[contains(@href,'Member_Value')]")
	WebElement mvoTab;

	@FindBy(xpath = "//button[text()='New']")
	WebElement mvoNewBtn;

	String contact = "//span[text()='%s']//ancestor::a";
	@FindBy(xpath = "//button[contains(@aria-label,'Join License Country')]")
	WebElement licenseCountryDrp;
	

	@FindBy(xpath = "//span[text()='Name']/parent::div/parent::dt/following-sibling::dd//div/span/slot/lightning-formatted-name")
	WebElement nameonContactpage;

	@FindBy(xpath = "//button[@title='Edit Name']")
	WebElement nameEditBtn;
	
	@FindBy(xpath = "//button[@aria-label='Prefix']")
	WebElement prefixBtn;

	@FindBy(xpath = "//span[@title='Dr.']")
	WebElement prefixTypeDr;

	@FindBy(xpath = "//button[@name='salutation']")
	WebElement prefixTypeMr;
	
	@FindBy(xpath = "//div[@aria-label='Prefix']//lightning-base-combobox-item//span[text()='Mr.']")
    WebElement selectPrefix;    
	
	@FindBy(xpath = "//input[@name='middleName']")
	WebElement middleName;

	@FindBy(xpath = "//input[@name='AIA_Other_Credentials__c']")
	WebElement otherCreds;

	@FindBy(xpath = "//span[text()='Certificate Name']/parent::div/parent::dt/following-sibling::dd//div/span/slot//lightning-formatted-text")
	WebElement certificatenameonContactpage;

	@FindBy(xpath = "//span[text()='AIA Designation']/parent::div/parent::dt/following-sibling::dd//div/span//lightning-formatted-text")
	WebElement aiaDesignationonContactpage;

	@FindBy(xpath = "//span[text()='Other Credentials']/parent::div/parent::dt/following-sibling::dd//div/span/slot//lightning-formatted-text")
	WebElement otherCredsonContactpage;

	@FindBy(xpath = "(//ul[@role='presentation']//li[4]//button)")
	WebElement chevronButton;
	
	//@FindBy(xpath = "//button[contains(text(),'Renew')]/following::span[contains(text(),'Show more actions')]")
		@FindBy(xpath = "(//li[@data-target-selection-name='sfdc:QuickAction.Contact.Email_Change_Request']/parent::ul[@class='slds-button-group-list'])//li[4]//button")
		WebElement showMoreActionsBtn;


	@FindBy(xpath = "//span[text()='Upgrade']/parent::a")
	WebElement upgradeBtn;

	@FindBy(xpath = "//div[@class='quick-actions-panel']")
	WebElement upgradeDevPopUp;

	@FindBy(xpath = "//button[@name='AIA_Membership_Type__c']")
	WebElement membershipTypeDrp;

	String membershipTypeSelect = "//lightning-base-combobox-item/span/span[text()='%s']";

	@FindBy(xpath = "//button[text()='Upgrade']")
	WebElement upgradeMemBtn;

	@FindBy(xpath = "//input[@name='AIA_Join_Supervisor__c']")
	WebElement supervisorName;
	
	// New flow payment flow 
	@FindBy(xpath = "//*[@class='iframe-parent slds-template_iframe slds-card']//iframe")
	WebElement firstFrame;
	
	@FindBy(xpath = "//iframe[@data-name='paymentIframe']")
    WebElement secondFrame;
	
	@FindBy(xpath = "//iframe[@class='payment-element-iframe']")
	WebElement thridFrame;
	
	@FindBy(xpath = "//div[@class='__PrivateStripeElement']/iframe")
	WebElement fourthFrame;
	
	@FindBy(xpath = "//input[@id='Field-numberInput']")
	WebElement cardNumber;
	
	@FindBy(xpath = "//input[@id='Field-expiryInput']")
	WebElement expiryDate;
	
	@FindBy(xpath = "//input[@id='Field-cvcInput']")
	WebElement cvcField;
	
	@FindBy(xpath = "//input[@id='savePaymentMethodCheckbox']")
	WebElement savePaymentMethod;
	
	@FindBy(xpath = "//span[text()='Process Payment']/parent::button")
	WebElement NewprocessPaymentBtn;
	
	@FindBy(xpath = "//input[@name='postalCode']")
	WebElement zipCode;
	
	@FindBy(xpath = "//select[@name='country']")
	WebElement selectCountry;
	
	@FindBy(xpath = "//span//input[@name='autorenewcheckbox']/following::label/span[@part='indicator']")
	WebElement autoRenewCheckBoxInSalesOrder;
	
	String fName;
	String lName;
	String fullname;
	String emailPrefix;
	String newEmailPrefix;
	String emailDomain;
	String emailaddressdata;
	String newEmailaddressdata;
	ArrayList<String> userList = new ArrayList<String>();

	/**
	 * @param null
	 * @return null
	 */
	/*
	 * public void signInFonteva() { util.enterText(driver, userName,
	 * DataProviderFactory.getConfig().getValue("fontevaUserName"));
	 * util.enterText(driver, password,
	 * DataProviderFactory.getConfig().getValue("fontevaPassWord"));
	 * loginBtn.click(); }
	 */
	public void pointOffset() {
		//action.moveByOffset(1300, 700).build().perform();
	}

	/**
	 * @param null
	 * @return ArraList<String>
	 * @throws null
	 */
	public ArrayList<String> userData() {
		fName = "autofn" + RandomStringUtils.randomAlphabetic(4);
		userList.add(0, fName);
		log.info("Users First Name:" + fName);
		lName = "autoln" + RandomStringUtils.randomAlphabetic(4);
		userList.add(1, lName);
		log.info("Users Last Name:" + lName);
		DateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		Date date = new Date();
		String date1 = dateFormat.format(date);
		emailPrefix = "auto_" + RandomStringUtils.randomAlphabetic(4).toLowerCase() + date1;
		userList.add(2, emailPrefix);
		emailDomain = "@architects-team.m8r.co";
		userList.add(3, emailDomain);
		emailaddressdata = emailPrefix + emailDomain;
		log.info("Email:" + emailaddressdata);
		userList.add(4, emailaddressdata);
		fullname = fName + " " + lName;
		userList.add(5, fullname);
		newEmailPrefix = "auto_" + RandomStringUtils.randomAlphabetic(3).toLowerCase() + date1;
		userList.add(6, newEmailPrefix);
		newEmailaddressdata = newEmailPrefix + emailDomain;
		userList.add(7, newEmailaddressdata);
		return userList;
	}

	/**
	 * @param null
	 * @return null
	 * @throws null
	 */
	public void createUserInFonteva() {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		util.waitUntilElement(driver, newBtn);
		newBtn.click();
		util.waitUntilElement(driver, firstName);
		firstName.sendKeys(fName);
		util.waitUntilElement(driver, lastName);
		lastName.sendKeys(lName);
		executor.executeScript("arguments[0].scrollIntoView(true);", emailAddress);
		emailAddress.sendKeys(emailaddressdata);
		saveBtn.click();
	}

	/**
	 * @param membership
	 * @param career
	 * @throws InterruptedException
	 */
	public void joinCreatedUser(String membership, String career) throws InterruptedException {
		Thread.sleep(20000);
		util.waitUntilElement(driver, joinBtn);
		joinBtn.click();
		util.waitUntilElement(driver, selectMemTypeBtn);
		selectMemTypeBtn.click();
		WebElement membershipType = driver.findElement(By.xpath(String.format(memType, membership)));
		util.waitUntilElement(driver, membershipType);
		membershipType.click();
		action.moveToElement(enterZipCode);
		util.enterText(driver, enterZipCode, data.testDataProvider().getProperty("zipCode"));
		util.waitUntilElement(driver, careerTypeDrp);
		executor.executeScript("arguments[0].click();",careerTypeDrp);
//		careerTypeDrp.click();
		WebElement selectCareerType = driver.findElement(By.xpath(String.format(careerType, career)));
		util.clickUsingJS(driver, selectCareerType);
		// action.scrollToElement(nextBtn);
		executor.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
		util.waitUntilElement(driver, nextBtn);
		nextBtn.click();
	}

	/**
	 * @param null
	 * @return null
	 * @throws InterruptedException 
	 * @throws null
	 */
	public void enterLicenseDetail() throws InterruptedException {
		util.enterText(driver, enterLicenseNumber, data.testDataProvider().getProperty("LICENSE_NUMBER"));
		util.waitUntilElement(driver, licenseCountryDrp);
		licenseCountryDrp.click();
		executor.executeScript("arguments[0].click();",
				util.getCustomizedWebElement(driver, country, data.testDataProvider().getProperty("LICENSE_COUNTRY")));
		licenseStateDrp.click();
		WebElement enterState = driver
				.findElement(By.xpath(String.format(state, data.testDataProvider().getProperty("LICENSE_STATE"))));
		util.clickUsingJS(driver, enterState);
//		enterState.click();
		action.scrollToElement(licenseStartDate);
		util.clickUsingJS(driver, licenseStartDate);
//		licenseStartDate.click();
		util.waitUntilElement(driver, selectTodayDate);
		util.clickUsingJS(driver, selectTodayDate);
		util.enterText(driver, licenseExpireDate, data.testDataProvider().getProperty("LICENSE_EXP_DATE"));
		licenseExpireDate.sendKeys(Keys.ENTER);
		executor.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
		nextBtn.click();
	}

	/**
	 * @param null Use for loop to getting all drop-down elements select payment
	 *             option from dropdown
	 * @throws InterruptedException
	 */
	public void createSalesOrder(String paymentOpt) throws InterruptedException {
		util.waitUntilElement(driver, selectDuesDrp);
		selectDuesDrp.click();
		// executor.executeScript("arguments[0].click();", selectDeusOpt);
		selectDeusOpt.click();
		createSalesOrder.click();
		util.waitUntilElement(driver, readyForPaymentBtn);
		readyForPaymentBtn.click();
		util.waitUntilElement(driver, applyPayment);
		util.clickUsingJS(driver, applyPayment);
//		applyPayment.click();
//		Thread.sleep(15000);
		// check wait
		util.waitUntilElement(driver, drpIframe);
		driver.switchTo().frame(drpIframe);
		Thread.sleep(30000);
		// check wait
		util.waitUntilElement(driver, option);
        util.selectDropDownByText(option, paymentOpt);
        System.out.println("===============Online payment selected=================================");
//		for (WebElement drpOption : options) {
//			System.out.println(drpOption.getText());
//			if (drpOption.getText().equalsIgnoreCase(paymentOpt)) {
//				drpOption.click();
//			}
//		}
		util.waitUntilElement(driver, applyLastPayment);
 		util.clickUsingJS(driver, applyLastPayment);
		System.out.println("===================Click on payment button");
//		applyLastPayment.click();
		driver.switchTo().defaultContent();
	}

	
	public void createSalesOrderAutoRenew(String paymentOpt) throws InterruptedException {
		util.waitUntilElement(driver, selectDuesDrp);
		selectDuesDrp.click();
		// executor.executeScript("arguments[0].click();", selectDeusOpt);
		selectDeusOpt.click();
		util.waitUntilElement(driver, autoRenewCheckBoxInSalesOrder);
		autoRenewCheckBoxInSalesOrder.click();
		log.info("Auto renew checkbox is clicked successfully");
		util.waitUntilElement(driver, createSalesOrder);
		createSalesOrder.click();
		util.waitUntilElement(driver, readyForPaymentBtn);
		readyForPaymentBtn.click();
		util.waitUntilElement(driver, applyPayment);
		applyPayment.click();
		Thread.sleep(15000);
		// check wait
		driver.switchTo().frame(drpIframe);
		Thread.sleep(60000);
		// check wait
		List<WebElement> options = driver.findElements(By.xpath("//select[@aria-label='Payment Type']/option"));
		for (WebElement drpOption : options) {
			System.out.println(drpOption.getText());
			if (drpOption.getText().equalsIgnoreCase(paymentOpt)) {
				drpOption.click();
			}
		}
		util.waitUntilElement(driver, applyLastPayment);
		applyLastPayment.click();
		driver.switchTo().defaultContent();
	}
	
	public void createDipSalesOrder(String paymentOpt) throws InterruptedException {
		util.waitUntilElement(driver, selectDuesDrp);
		selectDuesDrp.click();
		// executor.executeScript("arguments[0].click();", selectDeusOpt);
		selectDueDip.click();
		createSalesOrder.click();
		util.waitUntilElement(driver, readyForPaymentBtn);
		readyForPaymentBtn.click();
		util.waitUntilElement(driver, applyPayment);
		applyPayment.click();
		Thread.sleep(10000);
		// check wait
		driver.switchTo().frame(drpIframe);
		Thread.sleep(60000);
		// check wait
		List<WebElement> options = driver.findElements(By.xpath("//select[@aria-label='Payment Type']/option"));
		for (WebElement drpOption : options) {
			System.out.println(drpOption.getText());
			if (drpOption.getText().equalsIgnoreCase(paymentOpt)) {
				drpOption.click();
			}
		}
		util.waitUntilElement(driver, applyLastPayment);
		applyLastPayment.click();
	}

	/**
	 * @param fullName
	 * @param null
	 * @throws InterruptedException
	 */
	public void applyPayment(String fullName) throws InterruptedException {
		Thread.sleep(15000);
		util.switchToFrame(driver,firstFrame);
		util.switchToFrame(driver,secondFrame);
		util.switchToFrame(driver,thridFrame);
		util.switchToFrame(driver,fourthFrame);
		util.waitUntilElement(driver, cardNumber);
		util.clickUsingJS(driver, cardNumber);
	    util.enterText(driver, cardNumber, "4242424242424242");
		util.waitUntilElement(driver, expiryDate);
		util.clickUsingJS(driver, expiryDate);
		util.enterText(driver, expiryDate, "09/27");
		util.waitUntilElement(driver, cvcField);
		util.clickUsingJS(driver, cvcField);
		util.enterText(driver, cvcField, "879");
		System.out.println("=======Please Wait for payment done========");
		util.waitUntilElement(driver, selectCountry);
		util.selectDropDownByText(selectCountry, "United States");
		util.enterText(driver, zipCode, "38671");
		driver.switchTo().defaultContent();
		util.switchToFrame(driver,firstFrame);
		util.clickUsingJS(driver, NewprocessPaymentBtn);
		driver.switchTo().defaultContent();
		Thread.sleep(20000);
		System.out.println("------------------------------Payment is in inprogress--------------------------------------");
//		util.switchToFrame(thridFrame);
//		util.waitUntilElement(driver, savePaymentMethod);
//        util.clickUsingJS(driver,savePaymentMethod);
        
		
		
//		util.enterText(driver, cardHolderName, fullName);
//		Thread.sleep(5000);
//		util.waitUntilElement(driver, cardNumIframe1);
//		driver.switchTo().frame(cardNumIframe1);
//		util.waitUntilElement(driver, cardNumIframe2);
//		driver.switchTo().frame(cardNumIframe2);
//		action.scrollToElement(cardNum);
//		util.enterText(driver, cardNum, data.testDataProvider().getProperty("CREDIT_CARD_NUMBER"));
//		driver.switchTo().defaultContent();
//		// check wait
//		Thread.sleep(5000);
//		driver.switchTo().frame(drpIframe);
//		util.waitUntilElement(driver, expMonth);
//		action.scrollToElement(expMonth);
//		util.selectDrp(expMonth).selectByValue(data.testDataProvider().getProperty("CREDIT_CARD_EXP_MONTH"));
//		util.waitUntilElement(driver, expYear);
//		util.selectDrp(expYear).selectByValue(data.testDataProvider().getProperty("CREDIT_CARD_EXP_YEAR"));
//		processPaymentBtn.click();
//		driver.switchTo().defaultContent();
//		Thread.sleep(20000);
	}

	/**
	 * @return
	 * @throws InterruptedException
	 * 
	 */
	public ArrayList<Object> getPaymentReceiptData() throws InterruptedException {
		ArrayList<Object> receiptData = new ArrayList<Object>();
		Thread.sleep(10000);
		System.out.println("===================I am on receipt page=======================");
//		driver.navigate().refresh();
		Thread.sleep(10000);
		System.out.println("===================I am on receipt page=======================");
		util.waitUntilElement(driver, receiptNo);
		String receiptNumber = receiptNo.getText();
		System.out.println("original Receipt:"+receiptNumber);
		receiptData.add(0, receiptNumber);
//		util.waitUntilElement(driver, aiaNumber);
//		String customerAIANumber = aiaNumber.getText();
//		receiptData.add(1, customerAIANumber);
		String totalAmmountText = totalAmmount.getText().replaceAll("[$]*", "").trim();
		String result = totalAmmountText.substring(0, 5) + totalAmmountText.substring(5 + 1);
		System.out.println("After removing 0:"+result);
		receiptData.add(1, result);
		return receiptData;
	}

	/**
	 * @param Userfullname
	 * @throws InterruptedException
	 * 
	 */
	public void selectContact(String userFullname) throws InterruptedException {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		Thread.sleep(5000);
		driver.navigate().refresh();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
		Thread.sleep(15000);
		util.waitUntilElement(driver, contactSearchInputBox);
		contactSearchInputBox.sendKeys(userFullname);
		Thread.sleep(20000);
		//contactSearchInputBox.click();
		contactSearchInputBox.sendKeys(Keys.ENTER);
		//action.sendKeys(Keys.ENTER).build().perform();
		Thread.sleep(10000);
		try {
			driver.findElement(
					By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='" + userFullname + "']"))
					.click();
		} catch (Exception e) {
			boolean accountName = false;
			if (noItemHeading.isDisplayed()) {
				int count = 7;
				for (int i = 0; i < count && accountName == false; i++) {
					contactSearchInputBox.clear();
					Thread.sleep(20000);
					driver.findElement(
							By.cssSelector("a[class='toggle slds-th__action slds-text-link--reset ']:nth-child(1)"))
							.click();
					Thread.sleep(4000);
					contactSearchInputBox.sendKeys(userFullname);
					Thread.sleep(4000);
					contactSearchInputBox.sendKeys(Keys.ENTER);
					Thread.sleep(10000);
					try {
						if (driver.findElement(By.xpath(
								"//table[@aria-label='All Contacts']//td[3]//span/a[@title='" + userFullname + "']"))
								.isDisplayed()) {
							accountName = true;
							if (accountName == true) {
								driver.findElement(
										By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"
												+ userFullname + "']"))
										.click();
								util.waitForJavascript(driver, 20000, 2000);
							} else {
								System.out.println("Account name is not clicked");
							}

						} else {
							System.out.println("Account name is not displayed");
						}
					} catch (Exception a) {
						System.out.println(a);
					}

				}
			}
		}
//		WebElement contactInTable = driver.findElement(By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"+userFullname+"']"));
//		util.clickUsingJS(driver, contactInTable);
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactInTable, userFullname));
//		util.getCustomizedWebElement(driver, contactInTable, userFullname).click();
		// util.getCustomizedWebElement(driver, contactName, userFullname).click();
//		util.waitUntilElement(driver, contactallLink);
//		contactallLink.click();
//		Thread.sleep(14000);
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactName, userFullname));
//		executor.executeScript("arguments[0].scrollIntoView(true);",
//				util.getCustomizedWebElement(driver, contactName, userFullname));
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactName, userFullname));
//		executor.executeScript("arguments[0].click();",
//				util.getCustomizedWebElement(driver, contactName, userFullname));
		Thread.sleep(4000);

		util.waitUntilElement(driver, showAll);
		showAll.click();
	}

	/**
	 * @param fullName
	 * @throws InterruptedException
	 * 
	 */
	public void savingNewMVO(String fullName) throws InterruptedException {
		Thread.sleep(30000);
//		executor.executeScript("arguments[0].scrollIntoView(true);",
//				util.getCustomizedWebElement(driver, contact, fullName));
//		WebElement selectContact = util.getCustomizedWebElement(driver, contact, fullName);
//		executor.executeScript("arguments[0].click();", selectContact);
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.ARROW_UP).build().perform();
		actions.keyUp(Keys.ARROW_UP).build().perform();
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_DOWN).build().perform();
//		util.waitUntilElement(driver, showAll);
//		util.clickUsingJS(driver, showAll);
		//showAll.click();
		Utility.waitForWebElement(driver, mvoTab, 0);
		util.clickUsingJS(driver, mvoTab);
		util.waitUntilElement(driver, mvoNewBtn);
		assertTrue(mvoNewBtn.isDisplayed());
		mvoNewBtn.click();
	}

	/* 
	*/
	public void createSaleorderinInstallments() {
		util.waitUntilElement(driver, selectDuesDrp);
		selectDuesDrp.click();
		// executor.executeScript("arguments[0].click();", selectDeusOpt);
		selectPayInInsatllmentElement.click();
		createSalesOrder.click();
		assertTrue(driver.getTitle().contains(data.testDataProvider().getProperty("salesorderPage")));
	}

	public void validateCertificateName() throws InterruptedException {
		util.waitUntilElement(driver, nameonContactpage);
		String nameonContactpageValue = nameonContactpage.getText();
		System.out.println("nameonContactpageValue:" + nameonContactpageValue);
		util.waitUntilElement(driver, aiaDesignationonContactpage);
		String aiaDesignationContactpageValue = aiaDesignationonContactpage.getText();
		System.out.println("aiaDesignationonContactpageValue:" + aiaDesignationContactpageValue);
		util.waitUntilElement(driver, certificatenameonContactpage);
		String certificatenameonContactpagevalue = certificatenameonContactpage.getText();
		System.out.println("certificatenameonContactpagevalue:" + certificatenameonContactpagevalue);
	    assertTrue(certificatenameonContactpagevalue.contains(nameonContactpageValue+", "+aiaDesignationContactpageValue));
	    
	}

	public void verifyCertificateComponents(String allFielddetails) throws InterruptedException {
		util.waitUntilElement(driver, certificatenameonContactpage);
		String certificatenameonContactpagevalue = certificatenameonContactpage.getText();
		System.out.println("certificatenameonContactpagevalue:" + certificatenameonContactpagevalue);
		assertEquals(certificatenameonContactpagevalue, allFielddetails);

	}

	public void editDetailsonContact() throws InterruptedException {
		driver.navigate().refresh();
		util.waitUntilElement(driver, nameEditBtn);
		executor.executeScript("arguments[0].click();", nameEditBtn);
//		util.waitUntilElement(driver, prefixBtn);
//		executor.executeScript("arguments[0].click();", prefixBtn);
		util.waitUntilElement(driver, prefixTypeMr);
		util.clickUsingJS(driver, prefixTypeMr);
//		prefixTypeMr.click();
		util.waitUntilElement(driver, selectPrefix);
		util.clickUsingJS(driver, selectPrefix);
		util.enterText(driver, firstName, data.testDataProvider().getProperty("firstName"));
		util.enterText(driver, middleName, data.testDataProvider().getProperty("middleName"));
		util.enterText(driver, lastName, data.testDataProvider().getProperty("lastName"));
		util.enterText(driver, otherCreds, data.testDataProvider().getProperty("otherCreds"));
		util.waitUntilElement(driver, saveBtn);
		executor.executeScript("arguments[0].click();", saveBtn);
	}
	
	/**
	 * Clicking chevron button
	 * @throws InterruptedException 
	 */
	public void clickChevronBtn() throws InterruptedException {
		Thread.sleep(10000);
		util.waitUntilElement(driver, chevronButton);
		chevronButton.click();
	}

	public void upgradeMembership(String upgradeMembershipType) {
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_UP).build().perform();
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_UP).build().perform();
		actions.keyDown(Keys.ENTER).build().perform();
		actions.keyUp(Keys.ENTER).build().perform();
//		util.waitUntilElement(driver, upgradeBtn);
//		upgradeBtn.click();
		util.waitUntilElement(driver, upgradeDevPopUp);
		upgradeDevPopUp.isDisplayed();
		util.waitUntilElement(driver, membershipTypeDrp);
		util.clickUsingJS(driver, membershipTypeDrp);
		util.getCustomizedWebElement(driver, membershipTypeSelect, upgradeMembershipType).click();
		executor.executeScript("arguments[0].scrollIntoView(true);", upgradeMemBtn);
		util.waitUntilElement(driver, upgradeMemBtn);
		upgradeMemBtn.click();
		try {
			Thread.sleep(60000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void clickNextBtn() {
		util.waitUntilElement(driver, supervisorName);
		executor.executeScript("arguments[0].scrollIntoView(true);", supervisorName);
		executor.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
		util.waitUntilElement(driver, nextBtn);
		executor.executeScript("arguments[0].click();", nextBtn);
	}
	
   public void selectContactNewLogic(String fullName) throws InterruptedException {
	   JavascriptExecutor js = (JavascriptExecutor) driver;
		Actions actions = new Actions(driver);
		util.waitUntilElement(driver, contacts);
		util.clickUsingJS(driver, contacts);
//		contacts.click();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
		util.waitUntilElement(driver, localSearch);
		localSearch.sendKeys(fullName);
		actions.keyDown(Keys.ENTER).build().perform();
		actions.keyUp(Keys.ENTER).build().perform();
		Thread.sleep(5000);
		int retryCount=0;
		while (retryCount<5) {
		  String item = itemCount.getText();
          if(item.contains("1 item")) {
        	  util.getCustomizedWebElement(driver, contactName, fullName).click();
        	  break;
        	  
          }
          else {
        	  Thread.sleep(5000);
        	util.waitUntilElement(driver, assendingToggleBtn);
			assendingToggleBtn.click();
			Thread.sleep(5000);
		}
          retryCount++;
		}
//		driver.findElement(By.xpath(startLocator+fullName+endLocator)).click();
		
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_DOWN).build().perform();
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_DOWN).build().perform();
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, showAll);
		actions.moveToElement(showAll).build().perform();
		showAll.click();
   }
	
}
