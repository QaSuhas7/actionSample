package org.aia.pages.fonteva.ces;

import static org.testng.Assert.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.ces.SubscriptionPlanPrice;
import org.aia.pages.ces.Organization;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DateUtils;
import org.aia.utility.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

/**
 * @author IM-RT-LP-1483 (Suhas)
 *
 */
public class CES_ContactPage {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	SubscriptionPlanPrice subscriptionAPI = new SubscriptionPlanPrice(driver);
	Organization org;
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;
	DateUtils dateUtils;

	public CES_ContactPage(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
		org = new Organization(driver);
		dateUtils = new DateUtils();
		
	}
	
	String  contactPage = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[@field-label='Contact']//a//slot//slot/span[contains(text(),'%s')]";


	@FindBy(xpath = "//*[@title='Contacts']/span")
	WebElement contacts;

	@FindBy(xpath = "//a[@title='Contacts']/parent::one-app-nav-bar-item-root")
	WebElement contactsDiv;

	@FindBy(xpath = "//a/span[@title='Name']")
	WebElement tableheaderName;

	// @FindBy(xpath =
	// "//h1/span[text()='Contacts']/parent::h1/parent::div/parent::div//button")

	@FindBy(xpath = "//button[contains(@title,'Select a List View: Contacts')]")
	WebElement contactallBtn;

	@FindBy(xpath = "//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//a//span[text()='All Contacts'][1]")
	WebElement contactallLink;

	@FindBy(xpath = "//span[text()='App Launcher']/parent::div//parent::button")
	WebElement appLauncherIcn;

	@FindBy(xpath = "//label[text()='Search apps and items...']/parent::div/div/input")
	WebElement appSearchtxtbx;

	@FindBy(xpath = "//b[text()='Provider Application']")
	WebElement searchedAppPA;

	String providerApp = "//b[text()='%s']";

	@FindBy(xpath = "//table[@aria-label='Recently Viewed']/tbody/tr/th")
	WebElement tableProviderApp;

	@FindBy(xpath = "//button[@title='Select a List View'] | //button[contains(@title,'Select a List View')]")
	WebElement selectList;

	@FindBy(xpath = "//span[@class=' virtualAutocompleteOptionText'and text()='All']")
	WebElement allBtn;

	@FindBy(xpath = "//table[@aria-label='All']/tbody/tr")
	WebElement tableAllProviders;

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

	@FindBy(xpath = "//button[contains(@aria-label,'Join License Country')]")
	WebElement licenseCountryDrp;

	@FindBy(xpath = "//button[contains(@aria-label,'Subscription Plans')]")
	WebElement selectDuesDrp;

	@FindBy(xpath = "//span[contains(@title,'Payment in Full')]")
	WebElement selectDeusOpt;

	@FindBy(xpath = "//span[contains(@title,'Dues Installment Plan ')]")
	WebElement selectPayInInsatllmentElement;

	@FindBy(xpath = "//button[contains(text(),'Create sales order')]")
	WebElement createSalesOrder;

	@FindBy(xpath = "//button[text()='Ready For Payment']")
	WebElement readyForPaymentBtn;

	@FindBy(xpath = "//button[text()='Apply Payment']")
	WebElement applyPayment;

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

	@FindBy(xpath = "//iframe[@title='Payment Form']")
	WebElement cardNumIframe1;

	@FindBy(xpath = "//iframe[@title='Card number']")
	WebElement cardNumIframe2;

	@FindBy(xpath = "//span[text()='Process Payment']/parent::button")
	WebElement processPaymentBtn;

	@FindBy(xpath = "//lightning-formatted-text[@slot='primaryField']")
	WebElement receiptNo;

	@FindBy(xpath = "(//a[contains(@href,'OrderApi__Sales_Order__c')])[2]/slot/slot/span")
	WebElement aiaNumber;

	@FindBy(xpath = "(//p[text()='Total']/parent::div/p)[2]/slot/lightning-formatted-text")
	WebElement totalAmmount;

	String contactName = "//a[text()='%s']";
	
	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	WebElement localSearch;
	
	@FindBy(xpath = "//span[@class='countSortedByFilteredBy']")
	WebElement itemCount;
	
	@FindBy(xpath = "//span[@title='AIA Number']/parent::a")
	WebElement assendingToggleBtn;

	@FindBy(xpath = "//a[contains(text(),'Show All')]")
	WebElement showAll;

	// @FindBy(xpath = "//span[text()='Show more actions']//ancestor::button")
	@FindBy(xpath = "//lightning-button-menu[contains(@data-target-reveals,'Disable_Auto_Renew')]//button")
	WebElement moreActionBtn;

	@FindBy(xpath = "//span[text()='Log in to Experience as User']//ancestor::a")
	WebElement loginAsExpUserOpt;

	@FindBy(xpath = "//h2[text()='Log in as Site User']")
	WebElement siteUserOpt;

	@FindBy(xpath = "//span[text()='Providers']//ancestor::a")
	WebElement providerAppLink;

	@FindBy(xpath = "//p[text()='Account Name']//parent::div//div//a//span")
	WebElement accountName;

	@FindBy(xpath = "//button[text()='Rapid Order Entry']")
	WebElement rapidOrderEnteryBtn;

	@FindBy(xpath = "//button[text()='Advanced Settings']")
	WebElement advanceSetting;

	@FindBy(xpath = "//h2[text()='Advanced Settings']")
	WebElement advancSettingPopUp;

	@FindBy(xpath = "//select[@name='Business Group']")
	WebElement businessGroupDrp;

	@FindBy(xpath = "//button[@title='Save']")
	WebElement advanceSettingsaveBtn;
	
	@FindBy(xpath = "//span[text()='My Account']//ancestor::a")
	WebElement myAccountLink;

	// @FindBy(xpath = "//strong[text()='Item Quick
	// Add']//parent::span//following-sibling::span//div//input")
	// (//strong[text()='Item Quick
	// Add']/ancestor::div[@data-name='itemQuickAdd']//div[@data-name='quickAddItem']/div/div/div/input)
	@FindBy(xpath = "//div[@data-name='itemQuickAdd']//div[@data-name='quickAddItem']/div/div/div/input")
	WebElement quickItemSelect;

	@FindBy(xpath = "//button[text()='Add to Order']")
	WebElement addOrderBtn;

	String quickItemNatinal = "(//span[text()='%s'])[1]";

	String providerApplication = "(//b[text()='%s'])[1]";

	// String quickItemNatinal = "(//span[text()='%s'])";

	String discountCodeInput = "//span[text()='%s']";
	
	String selectContact = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a//slot//slot/span[text()='%s']";

	@FindBy(xpath = "//button[text()='Go']") // (//button[normalize-space()='Go'])")
	WebElement goBtn;

	@FindBy(xpath = "//*[contains(text(),'Open Memberships')]")
	WebElement Membershipslnk;

	@FindBy(xpath = "//span[text()= 'Account']/../..//span//a")
	WebElement SelectAccount;

	@FindBy(xpath = "//*[@role = 'table']//tbody//tr")
	WebElement RecordsCount;

	@FindBy(xpath = "((//*[@role = 'table']//tbody//tr)[2]//td)[10]")
	WebElement Chevronbtn;

	@FindBy(xpath = "//*[contains(text(), \"Are you sure you want to delete this Membership?\")]")
	WebElement DeleteMsg;

	@FindBy(xpath = "//span[text()='Delete']")
	WebElement Delete_membership;

	@FindBy(xpath = "(//*[@role = 'table']//tbody//tr//td)[4]//div//div")
	WebElement AvailableMemType;

	@FindBy(xpath = "//span[text()='Refresh']")
	WebElement RefreshBtn;

	@FindBy(xpath = "//div[text()='Delete']")
	WebElement DeleteBtn_chevrontype;

	@FindBy(xpath = "//div[@data-label='Discount Code']//input")
	WebElement discountCodeInputInROE;

	@FindBy(xpath = "//p[@class='slideIn']")
	WebElement discountAplliedPopUp;

	@FindBy(xpath = "//button[@class='iziToast-close']")
	WebElement popUpCloseButton;

	@FindBy(xpath = "//button[text()='Apply']")
	WebElement discontApplyBtn;

	@FindBy(xpath = "//input[@name='referenceNumber']")
	WebElement referenceNumber;

	@FindBy(xpath = "//button//span[text()= 'Apply Payment']")
	WebElement applyPaymentBtn;

	@FindBy(xpath = "//*[@data-name='applyPaymentPage']")
	WebElement applyPaymentPage;

	@FindBy(xpath = "//div/strong[text()='Items']/span")
	WebElement itemsFees;

	@FindBy(xpath = "//div[text()='Receipt']/parent::h1")
	WebElement receiptElement;

	@FindBy(xpath = "//span[text()= 'Account Name']/parent::div/parent::div//a//span")
	WebElement account;
	// Point of contact
	@FindBy(xpath = "//table[@aria-label='Points of contact']//tbody//tr[1]//th//a")
	WebElement selectPrimaryContact;

	@FindBy(xpath = "//table[@aria-label='Points of contact']//tbody//tr[2]//th//a")
	WebElement selectSecondaryContact;

	//@FindBy(xpath = "//a[contains(normalize-space(),'Show All']")
	@FindBy(xpath = "//*[contains(@class,'windowViewMode-normal')]//a[contains(normalize-space(),'Show All')]")
	WebElement showallBtn;

	@FindBy(xpath = "//*[contains(text(),'Open Supplemental dues')]/following::div[1]//a//span")
	WebElement pointofContact;
	
	/// Deceased marking Process
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a//slot//slot/span[text()='autofnoaZd autolnYGuo']")
	WebElement selectContactonReceipt;

	@FindBy(xpath = "//a//span[contains(text(),'Deceased')]")
	WebElement deceasedOption;
	
	@FindBy(xpath="//input[@name='DonorApi__Deceased__c']")
	WebElement deceasedCheckbox;
	
	//@FindBy(xpath="//span[text()='Deceased Date']/parent::div")
	@FindBy(xpath = "(//span[text()='Deceased Date']/following::div//lightning-formatted-text)[1]")
	WebElement deceasedDate;

	/// transfer request
	// **********************
	//@FindBy(xpath = "//button[contains(text(),'Renew')]/following::span[contains(text(),'Show more actions')]")
	@FindBy(xpath = "(//li[@data-target-selection-name='sfdc:QuickAction.Contact.Email_Change_Request']/parent::ul[@class='slds-button-group-list'])//li[4]//button")
	WebElement showMoreActionsBtn;

	@FindBy(xpath = "//*[contains(text(),'New Transfer Request')]")
	WebElement newTransferRequestOptn;

	@FindBy(xpath = "//span[contains(text(),'If you have moved and need to')]")
	WebElement transferRequestPopupMsg;

	@FindBy(xpath = "//p[contains(text(),'Thank you for your interest in transferring your AIA membership.')]")
	WebElement transferRequestThankYouMsg;
	
	@FindBy(xpath = "//p[contains(text(),'Please tell us your new address')]")
	WebElement tellYourNewAddressMsg;

	@FindBy(xpath = "//select[@name='Address_Type']")
	WebElement addressType;

	String adressTypeOptn = "//option[@value='%s']";

	String countryOptn = "//option[contains(text(),'%s')]";

	@FindBy(xpath = "//option[@value='Home']")
	WebElement homeOptn;

	@FindBy(xpath = "//option[@value='Work']")
	WebElement workOptn;

	@FindBy(xpath = "//label[contains(text(),'Country')]/following::select")
	WebElement selectCountry;
	
	@FindBy(xpath = "//label[text()='State/Province']/following::select[@class='slds-select']")
	WebElement selectState;

	@FindBy(xpath = "//input[@name='Street_Address']")
	WebElement streetAddress;

	@FindBy(xpath = "//input[@name='City']")
	WebElement city;

	@FindBy(xpath = "//input[@name='Postal_Code']")
	WebElement postalCode;

	@FindBy(xpath = "//div/slot/records-entity-label[contains(text(),'Membership Transfer')]")
	WebElement membershipTransferHeading;

	@FindBy(xpath = "//a[contains(text(),'Contact Details')]")
	WebElement contactDetails;

	@FindBy(xpath = "//a[contains(text(),'Current Membership Assignments')]")
	WebElement currentMembershipAssignments;

	@FindBy(xpath = "//a[contains(text(),'Application Details')]")
	WebElement applicationDetails;
	
	@FindBy(xpath = "//button[@name='AIA_Membership_Transfer__c.Approve_Reject']")
	WebElement approveAndReject;
	
	@FindBy(xpath = "//span[contains(@id,'toggle-description')]")
	WebElement toggleDescription;
	
	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextButton;

	@FindBy(xpath = "//button[@name='Contact.Email_Change_Request']")
	WebElement emailChangeRequestBtn;
	
	@FindBy(xpath = "//label[contains(text(),'Personal Email')]/following::div[1]/input")
	WebElement personalEmail;
	
	@FindBy(xpath = "//h2[contains(text(),'Email Change Request')]")
	WebElement emailChangeRequestPopUp;
	
	@FindBy(xpath = "//button[@title='Close this window']")
	WebElement closeEmailChangeRequestPopUp;
	
	@FindBy(xpath = "//span[contains(text(),'Your email has been successfully changed.')]")
	WebElement successfulEmailChangeMsg;
	
	@FindBy(xpath = "//span[contains(text(),'Personal Email')]/following::div[2]//a")
	WebElement personalEmailunderEmailSection;
	
	@FindBy(xpath = "//input[@aria-label='Search All Contacts list view.']")
	WebElement contactSearchInputBox;
	
	@FindBy(xpath = "//span[text()='No items to display.']")
	WebElement noItemHeading;
	
	String contactInTable = "//table[@aria-label='All Contacts']//td[3]//span/a[@title='%s']";
	
	@FindBy(css = "button[class*='_neutral search-button slds-truncate']")
	WebElement globSearch;

	@FindBy(xpath = "//label[contains(text(),'Search')]/following-sibling::div/input[contains(@aria-controls,'suggestionsList')]")
	WebElement globSearchInput;
	
	@FindBy(xpath = "//h1[text()='Search Results']/following::span[text()='Contacts']")
	WebElement contactsInGlobalSearch;
	
	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]")
	WebElement membership;

	@FindBy(xpath = "//table//tbody//tr//th//div//a")
	WebElement membershipSubId;
	
	String fName;
	String lName;
	String fullname;
	String emailPrefix;
	String emailDomain;
	String emailaddressdata;
	ArrayList<String> userList = new ArrayList<String>();

	/**
	 * @return
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
		return userList;
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void selectContact() throws InterruptedException {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
	}

	/**
	 * 
	 */
	public void createNewContactInFonteva() {
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
		Thread.sleep(8000);
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
		careerTypeDrp.click();
		WebElement selectCareerType = driver.findElement(By.xpath(String.format(careerType, career)));
		selectCareerType.click();
		// action.scrollToElement(nextBtn);
		executor.executeScript("arguments[0].scrollIntoView(true);", nextBtn);
		util.waitUntilElement(driver, nextBtn);
		nextBtn.click();
	}

	/**
	 * 
	 */
	public void enterLicenseDetail() {
		util.enterText(driver, enterLicenseNumber, data.testDataProvider().getProperty("LICENSE_NUMBER"));
		util.waitUntilElement(driver, licenseCountryDrp);
		licenseCountryDrp.click();
		executor.executeScript("arguments[0].click();",
				util.getCustomizedWebElement(driver, country, data.testDataProvider().getProperty("LICENSE_COUNTRY")));
		licenseStateDrp.click();
		WebElement enterState = driver
				.findElement(By.xpath(String.format(state, data.testDataProvider().getProperty("LICENSE_STATE"))));
		enterState.click();
		action.scrollToElement(licenseStartDate);
		licenseStartDate.click();
		util.waitUntilElement(driver, selectTodayDate);
		selectTodayDate.click();
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
		util.waitUntilElement(driver, cardHolderName);
		cardHolderName.sendKeys(fullName);
		util.waitUntilElement(driver, cardNumIframe1);
		driver.switchTo().frame(cardNumIframe1);
		util.waitUntilElement(driver, cardNumIframe2);
		driver.switchTo().frame(cardNumIframe2);
		action.scrollToElement(cardNum);
		util.enterText(driver, cardNum, data.testDataProvider().getProperty("CREDIT_CARD_NUMBER"));
		driver.switchTo().defaultContent();
		// check wait
		Thread.sleep(5000);
		driver.switchTo().frame(drpIframe);
		util.waitUntilElement(driver, expMonth);
		action.scrollToElement(expMonth);
		util.selectDrp(expMonth).selectByValue(data.testDataProvider().getProperty("CREDIT_CARD_EXP_MONTH"));
		util.waitUntilElement(driver, expYear);
		util.selectDrp(expYear).selectByValue(data.testDataProvider().getProperty("CREDIT_CARD_EXP_YEAR"));
		processPaymentBtn.click();
	}

	/**
	 * @return
	 * 
	 */
	public ArrayList<Object> getPaymentReceiptData() {
		ArrayList<Object> receiptData = new ArrayList<Object>();
		util.waitUntilElement(driver, receiptNo);
		String receiptNumber = receiptNo.getText();
		receiptData.add(0, receiptNumber);
		util.waitUntilElement(driver, aiaNumber);
		String customerAIANumber = aiaNumber.getText();
		receiptData.add(1, customerAIANumber);
		String totalAmmountText = totalAmmount.getText().replaceAll("[$]*", "").trim();
		System.out.println(totalAmmountText);
		receiptData.add(2, totalAmmountText);
		return receiptData;
	}

	/**
	 * @param Userfullname
	 * @throws InterruptedException
	 * 
	 */
	public void selectCreatedContact(String userFullname) throws InterruptedException {
		util.waitUntilElement(driver, contacts);
		contactsDiv.click();
		Thread.sleep(5000);
//		driver.navigate().refresh();
		util.waitForJavascript(driver, 20000, 2000);
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitForJavascript(driver, 20000, 5000);
 		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactSearchInputBox);
		contactSearchInputBox.sendKeys(userFullname);
		Thread.sleep(5000);
		contactSearchInputBox.sendKeys(Keys.ENTER);
		Thread.sleep(20000);
		try {
		Thread.sleep(5000);
		driver.findElement(By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"+userFullname+"']")).click(); 
		}
		catch (Exception e){
		boolean accountName = false;
		if(noItemHeading.isDisplayed()) {
			 int count = 10;
			 for (int i = 0; i < count && accountName == false; i++) 
			 {
				 contactSearchInputBox.clear();
				 Thread.sleep(20000);
				 driver.findElement(By.cssSelector("a[class='toggle slds-th__action slds-text-link--reset ']:nth-child(1)")).click();
				 Thread.sleep(4000);
				 contactSearchInputBox.sendKeys(userFullname);
				 contactSearchInputBox.sendKeys(Keys.ENTER);
				Thread.sleep(10000);
				 try {
				 if(driver.findElement(By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"+userFullname+"']")).isDisplayed()) {
					 accountName = true;
					 if(accountName==true) {
						 driver.findElement(By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"+userFullname+"']")).click(); 
						 util.waitForJavascript(driver, 20000, 2000);
					 }else {
						 System.out.println("Account name is not clicked");
					 }
				 }else {
					 System.out.println("Account name is not displayed");
				 }
				 }
				 catch (Exception a) {
						System.out.println(a);
					}
				
				 }
		}
	}
		
//		Thread.sleep(10000);
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactInTable, userFullname));
//		util.getCustomizedWebElement(driver, contactInTable, userFullname).click();
		//Thread.sleep(20000);
		//contactSearchInputBox.click();
//		action.moveToElement(contactSearchInputBox).build().perform();
//		action.sendKeys(Keys.ENTER).build().perform();
		
//		Thread.sleep(20000);
//		executor.executeScript("arguments[0].scrollIntoView(true);",
//				util.getCustomizedWebElement(driver, contactName, userFullname));
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactName, userFullname));
//		executor.executeScript("arguments[0].click();",
//				util.getCustomizedWebElement(driver, contactName, userFullname));
		Thread.sleep(40000);
		util.waitUntilElement(driver, showAll);
		// action.moveToElement(showAll).click().perform();
		showAll.click();
	}
	
	public void clickContactsInGlobalSearch(String contactName) throws InterruptedException {
		Utility.waitForWebElement(driver, contactsInGlobalSearch, 10);
		contactsInGlobalSearch.click();
		Thread.sleep(4000);
		WebElement contactElement = driver.findElement(By.xpath(
				"//table[@data-aura-class='uiVirtualDataTable'][not(contains(@aria-label,'Recently Viewed'))]/tbody/tr/th/span/a[@title='"
						+ contactName + "']"));
		util.waitUntilElement(driver, contactElement);
		contactElement.click();
		util.waitForJavascript(driver, 10000, 3000);
	}

	/**
	 * @param email
	 * @throws Throwable
	 */
	public void globalSearch(String email) throws Throwable {
		Thread.sleep(10000);
		Utility.waitForWebElement(driver, globSearch, 30);
		globSearch.click();
		globSearchInput.sendKeys(email);
		Thread.sleep(4000);
		globSearchInput.sendKeys(Keys.ENTER);
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	public void selectExpAsUserOpt() throws InterruptedException {
		util.waitUntilElement(driver, moreActionBtn);
		moreActionBtn.click();
		Thread.sleep(3000);
		util.waitUntilElement(driver, loginAsExpUserOpt);
		loginAsExpUserOpt.click();
		Thread.sleep(3000);
		util.waitUntilElement(driver, siteUserOpt);
		assertTrue(siteUserOpt.isDisplayed());
		util.waitUntilElement(driver, providerAppLink);
		providerAppLink.click();
		Thread.sleep(5000);
	}

	/**
	 * @param userFullname
	 * @param itemQuick
	 * @throws InterruptedException
	 * @throws AWTException
	 */
	public void selectRapidOrderEntry(String userFullname, String itemQuick, String quickElement)
			throws InterruptedException {
		selectCreatedContact(userFullname);
		util.waitUntilElement(driver, accountName);
		executor.executeScript("arguments[0].click();", accountName);
		util.waitUntilElement(driver, rapidOrderEnteryBtn);
		rapidOrderEnteryBtn.click();
		util.waitForJavascript(driver, 20000, 2000);
		util.waitUntilElement(driver, quickItemSelect);
		executor.executeScript("arguments[0].click();", quickItemSelect);
		// executor.executeScript("arguments[0].value='"+itemQuick+"';",
		// quickItemSelect);
		quickItemSelect.sendKeys(itemQuick);
		Thread.sleep(20000);
		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, quickItemNatinal, quickElement));
		util.getCustomizedWebElement(driver, quickItemNatinal, quickElement).click();
		util.waitForJavascript(driver, 20000, 2000);
		util.waitUntilElement(driver, addOrderBtn);
		addOrderBtn.click();
		util.waitUntilElement(driver, goBtn);
		goBtn.click();
		util.waitForJavascript(driver, 20000, 2000);

	}

	public void cesRapidOrderEntry(String userFullname, String itemQuick, String quickElement)
			throws InterruptedException {
		util.waitForJavascript(driver, 20000, 2000);
		selectCreatedContact(userFullname);
		util.waitUntilElement(driver, accountName);
		executor.executeScript("arguments[0].click();", accountName);
		util.waitUntilElement(driver, rapidOrderEnteryBtn);
		rapidOrderEnteryBtn.click();
		util.waitUntilElement(driver, quickItemSelect);
		executor.executeScript("arguments[0].click();", quickItemSelect);
		Thread.sleep(20000);
		// executor.executeScript("arguments[0].value='"+itemQuick+"';",
		// quickItemSelect);
		quickItemSelect.sendKeys(itemQuick);
		Thread.sleep(10000);
		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, quickItemNatinal, quickElement));
		util.getCustomizedWebElement(driver, quickItemNatinal, quickElement).click();
		Thread.sleep(10000);
		util.waitUntilElement(driver, addOrderBtn);
		addOrderBtn.click();
		Thread.sleep(20000);
		util.waitUntilElement(driver, itemsFees);
		String fee = itemsFees.getText();
		System.out.println("Item fee: " + fee);
		if (fee.equalsIgnoreCase("Free")) {
			Thread.sleep(10000);
			util.waitUntilElement(driver, goBtn);
			action.moveToElement(goBtn).click().perform();
			System.out.println("GO button clicked");
			Thread.sleep(10000);
			util.waitUntilElement(driver, SelectAccount);
			action.moveToElement(SelectAccount).click().perform();
			System.out.println("Account selected");
			Thread.sleep(10000);
			util.waitUntilElement(driver, Membershipslnk);
			action.moveToElement(Membershipslnk).click().perform();
			System.out.println("Memberships clicked");
			driver.navigate().refresh();
		} else {
			util.waitUntilElement(driver, goBtn);
			action.moveToElement(goBtn).click().perform();
			System.out.println("GO button clicked");
			Thread.sleep(10000);
			util.waitUntilElement(driver, referenceNumber);
			referenceNumber.sendKeys(data.testDataProvider().getProperty("referenceNum"));
			util.waitUntilElement(driver, applyPaymentBtn);
			action.moveToElement(applyPaymentBtn).click().perform();
			System.out.println("applyPaymentButton clicked");
			Thread.sleep(20000);
		}

	}

	public void validateDeleteCESMembership() throws InterruptedException {
		List<WebElement> rows = driver.findElements(By.xpath("//*[@role ='table']//tbody//tr"));
		System.out.println("Number of records:" + rows.size());
		Thread.sleep(30000);
		util.waitUntilElement(driver, Chevronbtn);
		action.moveToElement(Chevronbtn).click().perform();
		System.out.println("Chevron button clicked");
		util.waitUntilElement(driver, DeleteBtn_chevrontype);
		action.moveToElement(DeleteBtn_chevrontype).click().perform();
		System.out.println("Delete Option clicked");
		util.waitUntilElement(driver, DeleteMsg);
		System.out.println("MyError:" + DeleteMsg.getText());
		assertTrue(DeleteMsg.getText().equalsIgnoreCase(data.testDataProvider().getProperty("DeleteMsg")));
		util.waitUntilElement(driver, Delete_membership);
		action.moveToElement(Delete_membership).click().perform();
		Thread.sleep(20000);
	}

	public void validateAvailableMemType() {
		util.waitUntilElement(driver, AvailableMemType);
		assertTrue(
				AvailableMemType.getText().equalsIgnoreCase(data.testDataProvider().getProperty("availableMemType")));

	}

//	public void selectProviderApp(String userFullname,String quickElement)throws InterruptedException, AWTException {
//		Thread.sleep(10000);
//		util.waitUntilElement(driver, appLauncherIcn);
//		appLauncherIcn.click();
//		Thread.sleep(10000);
//		appSearchtxtbx.sendKeys(data.testDataProvider().getProperty("providerApp"));
//		Thread.sleep(10000);
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, providerApplication, quickElement));
//		util.getCustomizedWebElement(driver, providerApplication, quickElement).click();
//		util.waitUntilElement(driver, providerAppBtn);
//		providerAppBtn.click();
//		util.waitUntilElement(driver, allBtn);
//		allBtn.click();
//		executor.executeScript("arguments[0].scrollIntoView(true);",
//				util.getCustomizedWebElement(driver, contactName, userFullname));
//		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactName, userFullname));
//		executor.executeScript("arguments[0].click();",
//				util.getCustomizedWebElement(driver, contactName, userFullname));
//		util.waitUntilElement(driver, showAll);
//		showAll.click();
//		
//}
	/*
	 * @throws InterruptedException selects the Primary contact from POC and checks
	 * the account associated to contact
	 */
	public void verifyAccountAssociatedtoPrimaryPOC() throws InterruptedException {
		Thread.sleep(20000);
		util.waitUntilElement(driver, showallBtn);
		action.moveToElement(showallBtn).click().perform();
		Thread.sleep(5000);
		WebElement editElement = driver.findElement(By.xpath("//div[contains(@class,'windowViewMode-normal')]//button[@name='Edit']"));
		util.mosueOverUsingAction(driver, editElement);
		util.waitUntilElement(driver, pointofContact);
		action.moveToElement(pointofContact).click().perform();
		util.waitUntilElement(driver, selectPrimaryContact);
		action.moveToElement(selectPrimaryContact).click().perform();
		util.waitUntilElement(driver, accountName);
		String accountNameValue = accountName.getText();
		System.out.println("accountName is:" + accountNameValue);
		assertTrue(accountNameValue.equalsIgnoreCase(org.orgName));
	}

	/**
	 * @throws InterruptedException selects the Secondary contact from POC and
	 *                              checks the account associated to contact
	 */
	public void verifyAccountAssociatedtoSecondaryPOC() throws InterruptedException {
		util.waitUntilElement(driver, showallBtn);
		action.moveToElement(showallBtn).click().perform();
		Thread.sleep(5000);
		WebElement editElement = driver.findElement(By.xpath("//div[contains(@class,'windowViewMode-normal')]//button[@name='Edit']"));
		util.mosueOverUsingAction(driver, editElement);
		util.mosueOverUsingAction(driver, accountName);
		util.waitUntilElement(driver, pointofContact);
		action.moveToElement(pointofContact).click().perform();
		util.waitUntilElement(driver, selectPrimaryContact);
		action.moveToElement(selectPrimaryContact).click().perform();
		util.waitUntilElement(driver, accountName);
		String accountNameValue = accountName.getText();
		System.out.println("accountName is:" + accountNameValue);
		assertTrue(accountNameValue.equalsIgnoreCase(org.orgName));
	}

	/**
	 * @throws InterruptedException
	 * makes the member Deceased and checks the Deceased checkbox and date is displayed or not
	 */
	public void verifyDeceasedMarkingProcess() throws InterruptedException {
		util.waitUntilElement(driver, showMoreActionsBtn);
		action.moveToElement(showMoreActionsBtn).click().perform();
		util.waitUntilElement(driver, deceasedOption);
		action.moveToElement(deceasedOption).click().perform();
		util.waitUntilElement(driver, nextBtn);
		nextBtn.isDisplayed();
		action.moveToElement(nextBtn).click().perform();
		util.waitUntilElement(driver, deceasedCheckbox);
		deceasedCheckbox.isEnabled();
		util.waitUntilElement(driver, deceasedDate);
		String deceasedDateValue=deceasedDate.getAttribute("innerText");
		System.out.println("deceasedDateValue:"+deceasedDateValue);
		String todaysDate = dateUtils.getDate(0, "M/d/yyyy");
		System.out.println("today's Date is: " +todaysDate);
		assertEquals(deceasedDateValue, todaysDate);
	}

	/**
	 * @throws InterruptedException selects Contact on the Receipt Page
	 * @param addressTypevalue
	 * @param countryValue
	 * @throws InterruptedException
	 */
	public void verifyMemTransferApplicationProcess(String addressTypevalue, String countryValue, String postalZipCode)
			throws InterruptedException {
		util.waitUntilElement(driver, showMoreActionsBtn);
		action.moveToElement(showMoreActionsBtn).click().perform();
		util.waitUntilElement(driver, newTransferRequestOptn);
		action.moveToElement(newTransferRequestOptn).click().perform();
		util.waitUntilElement(driver, transferRequestPopupMsg);
		String transferRequestPopuptext = transferRequestPopupMsg.getText();
		System.out.println("popUpTxtValue:" + transferRequestPopuptext);
		assertTrue(transferRequestPopuptext
				.equalsIgnoreCase(data.testDataProvider().getProperty("transferRequestPopupMessage")));
		util.waitUntilElement(driver, nextBtn);
		nextBtn.isDisplayed();
		action.moveToElement(nextBtn).click().perform();
		util.waitUntilElement(driver, transferRequestThankYouMsg);
		String transferRequestThankYouMsgValue = transferRequestThankYouMsg.getText();
		System.out.println("transferRequestThankYouMsgValue:" + transferRequestThankYouMsgValue);
		assertTrue(transferRequestThankYouMsgValue
				.equalsIgnoreCase(data.testDataProvider().getProperty("transferRequestThankYouMessage")));
		util.waitUntilElement(driver, tellYourNewAddressMsg);
		String tellYourNewAddressMsgValue = tellYourNewAddressMsg.getText();
		System.out.println("tellYourNewAddressMsgValue:" + tellYourNewAddressMsgValue);
		assertTrue(tellYourNewAddressMsgValue
				.equalsIgnoreCase(data.testDataProvider().getProperty("tellYourNewAddressMessage")));
		util.waitUntilElement(driver, addressType);
		action.moveToElement(addressType).click().perform();
		util.getCustomizedWebElement(driver, adressTypeOptn, addressTypevalue).click();
		util.getCustomizedWebElement(driver, countryOptn, countryValue).click();
		if(countryValue.equals("United States")) {
			util.waitUntilElement(driver, selectState);
			Select option = new Select(selectState);
			option.selectByVisibleText("California");
		}
		util.waitUntilElement(driver, streetAddress);
		streetAddress.sendKeys(countryValue);
		util.waitUntilElement(driver, city);
		city.sendKeys("new street");
		util.waitUntilElement(driver, postalCode);
		postalCode.sendKeys(postalZipCode);
		util.waitUntilElement(driver, nextBtn);
		action.moveToElement(nextBtn).click().perform();
//		util.waitUntilElement(driver, membershipTransferHeading);
//		membershipTransferHeading.isDisplayed();
		util.waitUntilElement(driver, contactDetails);
		contactDetails.isDisplayed();
		Thread.sleep(2000);
		util.scrollingElementUsingJS(driver, currentMembershipAssignments);
		util.waitUntilElement(driver, currentMembershipAssignments);
		currentMembershipAssignments.isDisplayed();
		util.waitUntilElement(driver, applicationDetails);
		applicationDetails.isDisplayed();
	}
	
	public void approveORRejectButton() {
		String text;
		util.waitUntilElement(driver, approveAndReject);
		util.clickUsingJS(driver, approveAndReject);
		util.waitUntilElement(driver, toggleDescription);
		 text = toggleDescription.getText();
		System.out.println(text);
		util.clickUsingJS(driver, toggleDescription);
		 text = toggleDescription.getText();
		System.out.println(text);
		util.waitUntilElement(driver, nextButton);
		nextButton.click();
		util.waitUntilElement(driver, nextButton);
		nextButton.click();
	}
	
	/**
	 * @throws InterruptedException
	 * selects Contact on the Receipt Page
	 */

	public void selectContactonReceiptPage(String userFullname) throws InterruptedException {
		Thread.sleep(8000);
		((JavascriptExecutor)driver).executeScript("scroll(0,200)");
		Thread.sleep(4000);
		WebElement selectContactonReceipt = driver.findElement(By.xpath("//div[@class='windowViewMode-normal oneContent active lafPageHost']//a//slot//slot/span[text()='"+userFullname+"']"));
		util.waitUntilElement(driver, selectContactonReceipt);
		util.clickUsingJS(driver, selectContactonReceipt);
	}
	
	public void sendEmailChangeRequest(String newEmailAddress) throws InterruptedException {
		util.waitUntilElement(driver, emailChangeRequestBtn);
		action.moveToElement(emailChangeRequestBtn).click().perform();
		util.waitUntilElement(driver, personalEmail);
		personalEmail.clear();
		action.moveToElement(personalEmail).click().perform();
		personalEmail.sendKeys(newEmailAddress);
		util.waitUntilElement(driver, saveBtn);
		action.moveToElement(saveBtn).click().perform();
		util.waitUntilElement(driver, emailChangeRequestPopUp);
		emailChangeRequestPopUp.isDisplayed();
		util.waitUntilElement(driver, closeEmailChangeRequestPopUp);
		action.moveToElement(closeEmailChangeRequestPopUp).click().perform();
	}
	
	public void verifyChangesinEmailSection(String newEmailAddress){
		System.out.println("newEmailAddress:"+newEmailAddress);
		driver.navigate().refresh();
		util.waitUntilElement(driver, personalEmailunderEmailSection);
		String personalEmailValue= personalEmailunderEmailSection.getText();
		System.out.println("personalEmailValue:"+personalEmailValue);
		assertTrue(personalEmailValue.equalsIgnoreCase(newEmailAddress));
	}
	
	public void selectExpAsUserOpt2() throws InterruptedException {
		util.waitUntilElement(driver, moreActionBtn);
		moreActionBtn.click();
		util.waitUntilElement(driver, loginAsExpUserOpt);
		loginAsExpUserOpt.click();
		util.waitUntilElement(driver, siteUserOpt);
		assertTrue(siteUserOpt.isDisplayed());
		util.waitUntilElement(driver, myAccountLink);
		myAccountLink.click();
		Thread.sleep(5000);
	}
	//myAccountLink
	
	public void navigateToAccount(String userFullName) {
	util.clickUsingJS(driver, util.getCustomizedWebElement(driver, contactPage, userFullName));
	util.waitUntilElement(driver, membership);
	membership.click();
	util.waitUntilElement(driver, membershipSubId);
	util.clickUsingJS(driver, membershipSubId);
	}
	
	public void selectContactNewLogic(String fullName) throws InterruptedException {
		   JavascriptExecutor js = (JavascriptExecutor) driver;
			Actions actions = new Actions(driver);
			util.waitUntilElement(driver, contacts);
			util.clickUsingJS(driver, contacts);
//			contacts.click();
			util.waitUntilElement(driver, tableheaderName);
			Thread.sleep(5000);
			util.waitUntilElement(driver, contactallBtn);
			contactallBtn.click();
			util.waitUntilElement(driver, contactallLink);
			contactallLink.click();
			util.waitUntilElement(driver, localSearch);
			localSearch.sendKeys(fullName);
			actions.keyDown(Keys.ENTER);
			actions.keyUp(Keys.ENTER);
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
//			driver.findElement(By.xpath(startLocator+fullName+endLocator)).click();
			util.waitUntilElement(driver, showAll);
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			actions.moveToElement(showAll).build().perform();
			actions.sendKeys(Keys.ARROW_DOWN).build().perform();
			actions.sendKeys(Keys.ARROW_UP).build().perform();
			showAll.click();
	   }
		
}
