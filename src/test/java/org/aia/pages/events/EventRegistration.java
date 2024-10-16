package org.aia.pages.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.aia.pages.fonteva.events.EventInfoModule;
import org.aia.pages.fonteva.events.EventConfig;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import groovy.transform.Final;

public class EventRegistration {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	ConfigDataProvider testData;

	public String newEvent = "";
	public String receiptNum = "";
	public String postedDate = "";
	public String totalAmount = "";
	public String userName = "";
	public String aiaNumber = "";
	public String aiamemeberfullName = "";
	ArrayList<Object> receiptData = new ArrayList<Object>();
	static Logger log = Logger.getLogger(EventRegistration.class);

	public EventRegistration(WebDriver Idriver)

	{
		this.driver = Idriver;
		executor = (JavascriptExecutor) driver;
		testData = new ConfigDataProvider();
	}

	// event new tab
	@FindBy(css = "div[id='navEventMenuItems'] button[data-title='Register']")
	WebElement eventRegister;

	@FindBy(xpath = "(//select[@name='Quantity'])[1]")
	WebElement ticketTypeQuantity;

	@FindBy(css = "div[id='ticketRegButton'] button")
	WebElement registerButton;

	@FindBy(css = "div[id='ticketRegButton'] button[data-name='singleTicketBtn']")
	WebElement singleTicketRegButton;

	@FindBy(css = "div[data-name='firstName'] div[data-name='matchFields'] input")
	WebElement firstName;

	@FindBy(css = "div[data-name='lastName'] div[data-name='matchFields'] input")
	WebElement lastName;

	@FindBy(css = "div[data-name='email'] div[data-name='matchFields'] input")
	WebElement email;

	@FindBy(css = "div[data-name='Badge name (first/nickname)-input'] input")
	WebElement badgeName;

	@FindBy(css = "div[data-name='Company-input'] input")
	WebElement companyName;

	@FindBy(css = "div[data-name='Title-input'] input")
	WebElement titleName;

	@FindBy(css = "div[data-name='Phone-input'] input")
	WebElement phoneNumber;

	@FindBy(css = "div[data-name='City-input'] input")
	WebElement cityName;

	@FindBy(css = "select[name='State']")
	WebElement selectState;

	@FindBy(css = "div[data-name='Degree Information-input'] input")
	WebElement degreeInformation;

	@FindBy(css = "select[name='Accessibility']")
	WebElement selectAccessibility; // Mobility

	@FindBy(css = "div[data-name='Emergency contact name-input'] input")
	WebElement emgContactName;

	@FindBy(css = "div[data-name='Emergency contact phone number-input'] input")
	WebElement emgphoneNumber;

	@FindBy(css = " select[name='How many times have you attended this conference?']")
	WebElement selectAttendedTimes;// First time

	@FindBy(css = "select[name='Ethnicity']")
	WebElement selectEthnicity;

	@FindBy(css = "select[name='Gender identity']")
	WebElement selectGender;

	@FindBy(css = "select[name='Professional Level']")
	WebElement selectProfessionalLevel;

	@FindBy(css = "select[name='Years in Profession']")
	WebElement selectYearsInProfession;

	@FindBy(xpath = "(//span[@class='slds-checkbox--faux'])[2]")
	WebElement attestCheckobox;

	@FindBy(css = "select[name='I attest to the above']")
	WebElement selectAttest;

	@FindBy(css = "div[data-name='Printed Name and Signature:-input'] input")
	WebElement printedNameAndSignature;

	@FindBy(css = "button[name='continueAttendeeSetup']")
	WebElement continueButtonInRegistration;

	// Agenda

	@FindBy(css = "a[data-name='agenda']")
	WebElement agenda;

	@FindBy(xpath = "//div[@data-name='subTotalPrice']/strong/span")
	WebElement subTotalAmount;
	
	public static String scheduleInagenda() {
		String xpath = null;
		for (int i = 1; i <=3; i++) {
			xpath = "(//div[@data-name='itemDiv'])'" + i + "'";
		}
	return xpath;
	}

	@FindBy(xpath = "//div[@data-aura-class='LTEEventRegistrationSummary']/following::button[text()='Continue']")
	WebElement continueButtonInAgenda;

	// checkout

	@FindBy(xpath = "//iframe[@title='Credit Card Input Frame']")
	WebElement creditCardPaymentFrame;

	@FindBy(css = "[data-name='totalPrice'] span[data-aura-class='FrameworkCurrencyField']")
	WebElement totalAmountInCheckout;

	@FindBy(xpath = "//iframe[@title='Card number']")
	WebElement cardNumFrame2;

	@FindBy(xpath = "//div[@data-name='payNow']//div/following::div/following::div//div[@class='slds-card slds-theme_default']/div[1]")
	WebElement choosePaymentText;

	@FindBy(xpath = "//div[@data-name='full_name']/input")
	WebElement cardHolderName;

	@FindBy(xpath = "//input[@name='card_number']")
	WebElement creditCardNumber;

	@FindBy(css = "select[name='Exp month']")
	WebElement expMonth;

	@FindBy(css = "select[name='Exp year']")
	WebElement expYear;

	@FindBy(css = "button[data-name='processBtn']")
	WebElement processPayment;

	public static String paymentButonfun(String paymentButton) {
		String xpath = "//button[@data-name='" + paymentButton + "']";
		return xpath;
	}

	@FindBy(css = "button[data-name='Confirm_Order']")
	WebElement confirmOrderPayment;

	// New Billing Address
	@FindBy(css = "button[data-name='addressCreateButton']")
	WebElement addressCreateButton;

	@FindBy(css = "div[data-name='name'] input")
	WebElement billingAddressName;

	@FindBy(css = "select[name='Type']")
	WebElement billingType;// Home

	@FindBy(css = "input[placeholder='Enter your address']")
	WebElement addressSearch;// India

	@FindAll(value = { @FindBy(xpath = "//div[@class='selectize-dropdown-content']//div/span[2]") })
	List<WebElement> addressSearchOptions;

	@FindBy(xpath = "//label[@data-name='manualAddress']")
	WebElement manualAddressCheckbox;

	@FindBy(css = "div[data-name='street_name'] textarea")
	WebElement streetName;

	@FindBy(css = "div[data-name='city'] input")
	WebElement city;

	@FindBy(css = "select[name='Country']")
	WebElement country;

	@FindBy(css = "div[data-name='postal_code'] input")
	WebElement zipCode;

	@FindBy(css = "button[data-name='ka_modal_save']")
	WebElement saveButtonInBiilingaddress;

	// view recipt
	@FindBy(xpath = "//span[text()='View Receipt']//parent::button")
	WebElement viewRecieptInCheckout;

	@FindBy(css = "div[class*='slds-text-heading--medium slds']")
	WebElement paymentSuccessMessage;

	@FindBy(css = "div[class*='body--regular slds-m-bottom--small']")
	WebElement receiptNumber;

	@FindBy(css = "div[class*='slds-p-horizontal_large slds-m-bottom_medium\'] div:nth-child(2)")
	WebElement postdDate;

	@FindBy(css = "[data-name='firstName'] input")
	WebElement firstNameInReg;

	@FindBy(css = "[data-name='lastName'] input")
	WebElement lastNameInReg;

	@FindBy(css = "[data-name='email'] input")
	WebElement emailInReg;

	@FindBy(xpath = "//p[@class='aia-number'][text()]")
	WebElement getaiaNumber;

	@FindBy(xpath = "//p[@class='aia-number']/span[text()]")
	WebElement getAiaText;

	@FindBy(xpath = "//div[@class='member-name']")
	WebElement aiaMemberName;
	
	//overveiw
	@FindBy(xpath = "//div[@id='navEventMenuItems']//button[@data-title='Details']") WebElement detailsNavigationButton;
	
	@FindBy(xpath  = "//lightning-formatted-rich-text[@class='event-rich-text slds-rich-text-editor__output']/parent::div") WebElement eventOverviewText;

	// speakers
	@FindBy(css = "div[id='navEventMenuItems'] button[data-title='Speakers']")
	WebElement speakersButtonInAIAPage;

	// Agenda
	@FindBy(xpath = "//div[@id='navEventMenuItems']//div[text()='Agenda']")
	WebElement agendaInAIA;
	
	@FindBy(xpath ="//c-pfm-text[@tabindex='0']")
	WebElement time;

	public static String scheduleNameInAgenda(String scheduleName) {
		String xpath = "//div/a/span[text()='" + scheduleName + "']";
		return xpath;
	}

	public static String speakerName(String speakerName) {
		String venueName1 = "//div[text()='" + speakerName + "']";
		return venueName1;
	}

	public void validateFirstNameInRegistartion() {
		Utility.waitForWebElement(driver, firstNameInReg, 20);
		util.scrollingElementUsingJS(driver, firstNameInReg);
		log.info(firstNameInReg.getAttribute("value"));
		System.out.println(firstNameInReg.getAttribute("value"));
	}

	public void validateLastNameInRegistartion() {
		Utility.waitForWebElement(driver, lastNameInReg, 20);
		log.info(lastNameInReg.getAttribute("value"));
		System.out.println(lastNameInReg.getAttribute("value"));
	}

	public void ValidateEmailInRegistartion() {
		Utility.waitForWebElement(driver, emailInReg, 20);
		log.info(emailInReg.getAttribute("value"));
		System.out.println(emailInReg.getAttribute("value"));
	}

	/**
	 * @param tabIdx
	 * @throws Throwable
	 */
	public void RegisterLink(int tabIdx) throws Throwable {
		util.switchToTabs(driver, tabIdx);
		Thread.sleep(6000);
		util.waitUntilElement(driver, eventRegister);
		util.clickUsingJS(driver, eventRegister);
		log.info("Event Register button is clicked sucessfully");
		Thread.sleep(8000);
	}

	public void selectTicketQuantity() throws Throwable

	{
//		Thread.sleep(9000);
		util.waitUntilElement(driver, ticketTypeQuantity);
		util.scrollingElementUsingJS(driver, ticketTypeQuantity);
		Utility.highLightElement(driver, ticketTypeQuantity);
		util.selectDropDownByText(ticketTypeQuantity, testData.testDataProvider().getProperty("ticketQuantity"));
		log.info("ticketTypeQuantity dropdown selected as 1");
	}

	public void clickRegisterButton() throws Throwable {
		util.waitUntilElement(driver, registerButton);
		Utility.highLightElement(driver, registerButton);
		registerButton.click();
		log.info("Register Now button is cliked sucessfully");
		Thread.sleep(14000);
	}

	public void singleticketRegistratioButton() {
		Utility.waitForWebElement(driver, singleTicketRegButton, 30);
		singleTicketRegButton.click();
	}

	public String getAIAData() throws Throwable {
		Thread.sleep(30000);
		Utility.waitForWebElement(driver, getaiaNumber, 10);
		String ss = getAiaText.getText();
		System.out.println(ss);
		String aiaNum = getaiaNumber.getText();
		aiaNumber = aiaNum.replace("AIA number: ", "");
		System.out.println(aiaNumber);
		Utility.waitForWebElement(driver, aiaMemberName, 10);
		aiamemeberfullName = aiaMemberName.getText();
		return aiaNumber;
	}

	/**
	 * @return
	 */
	public Map<String, String> attendeeInfo() {
		Map<String, String> information = new HashMap<String, String>();
		String mobNumb = "012345" + String.format("%05d", new Random().nextInt(10000));
		information.put("phoneNumber", mobNumb);
		String emgMobNumb = "112345" + String.format("%05d", new Random().nextInt(10000));
		information.put("EmgPhoneNumber", emgMobNumb);
		String nickName = "autoln" + RandomStringUtils.randomAlphabetic(4);
		information.put("nickName", nickName);
		String company = "poiu" + RandomStringUtils.randomAlphabetic(4);
		information.put("company", company);
		String title = "jhgf" + RandomStringUtils.randomAlphabetic(4);
		information.put("title", title);
		String city = "apk" + RandomStringUtils.randomAlphabetic(4);
		information.put("city", city);
		String degreeInfo = "mba" + RandomStringUtils.randomAlphabetic(4);
		information.put("degree", degreeInfo);
		String state = "AP";
		information.put("state", state);
		String accessibility = "Mobility";
		information.put("accessbility", accessibility);
		String attendedTimes = "First time";
		information.put("attendeeTimes", attendedTimes);
		String ethnicity = "Asian - Other";
		information.put("ethnicity", ethnicity);
		String gender = "Male";
		information.put("gender", gender);
		String professionalLevel = "Student";
		information.put("profLevel", professionalLevel);
		String yearsInProfession = "0-5 years";
		information.put("yearsProf", yearsInProfession);
		String attest = "Yes";
		information.put("attest", attest);
		return information;
	}

	/**
	 * Here we entered badgename, company, title, mobileNumber,
	 * emergencyMobileNUmber. Select state, degreeInformation, Accessbility,
	 * AttendenceTimes, ethnicity, professional level, years in professional,
	 * attest.
	 */
	public void rigisterRequiredInfo() {
		util.waitUntilElement(driver, firstName);
		System.out.println(firstName.getAttribute("value"));
		log.info(firstName.getAttribute("value"));
		System.out.println(lastName.getAttribute("value"));
		log.info(lastName.getAttribute("value"));
		System.out.println(email.getAttribute("value"));
		log.info(email.getAttribute("value"));

		// Attendee information
		Map<String, String> attendeInfo = attendeeInfo();

		util.enterText(driver, badgeName, attendeInfo.get("nickName"));

		log.info("BadgeName enterd as" + attendeInfo.get("nickName"));
		util.enterText(driver, companyName, attendeInfo.get("company"));
		log.info("CompanyName enterd as " + attendeInfo.get("company"));

		util.enterText(driver, titleName, attendeInfo.get("title"));
		log.info("TitleName enterd as " + attendeInfo.get("title"));
		util.enterText(driver, phoneNumber, attendeInfo.get("EmgPhoneNumber"));
		log.info("PhoneNumber enterd as" + attendeInfo.get("EmgPhoneNumber"));
		util.enterText(driver, cityName, attendeInfo.get("city"));
		log.info("CityName enterd as" + attendeInfo.get("city"));
		util.selectDropDownByText(selectState, attendeInfo.get("state"));
		log.info("State dropdown selected as" + attendeInfo.get("state"));
		util.enterText(driver, degreeInformation, attendeInfo.get("degree"));
		log.info("degreeInformation dropdown selected as" + attendeInfo.get("degree"));
		util.selectDropDownByText(selectAccessibility, attendeInfo.get("accessbility"));
		log.info("Accessibility dropdown selected as" + attendeInfo.get("accessbility"));
		String emgName = "awer" + RandomStringUtils.randomAlphabetic(4);
		util.enterText(driver, emgContactName, emgName);
		log.info("Emergency contact name enterd as" + emgName);
		util.enterText(driver, emgphoneNumber, attendeInfo.get("phoneNumber"));
		log.info("Emergency phone number enterd as" + attendeInfo.get("phoneNumber"));
		util.selectDropDownByText(selectAttendedTimes, attendeInfo.get("attendeeTimes"));
		log.info("AttendedTimes dropdown selected as" + attendeInfo.get("attendeeTimes"));
		util.selectDropDownByText(selectEthnicity, attendeInfo.get("ethnicity"));
		log.info("Ethnicity dropdown selected as" + attendeInfo.get("ethnicity"));
		util.selectDropDownByText(selectGender, attendeInfo.get("gender"));
		log.info("Gender dropdown selected as" + attendeInfo.get("gender"));
		util.selectDropDownByText(selectProfessionalLevel, attendeInfo.get("profLevel"));
		log.info("ProfessionalLevel dropdown selected as" + attendeInfo.get("profLevel"));
		util.selectDropDownByText(selectYearsInProfession, attendeInfo.get("yearsProf"));
		log.info("YearsInProfession dropdown selected as" + attendeInfo.get("yearsProf"));
		Utility.highLightElement(driver, attestCheckobox);
		attestCheckobox.click();
		log.info("attest checkbox is clicked sucessfully");
		util.selectDropDownByText(selectAttest, attendeInfo.get("attest"));
		log.info("attest dropdown selected as" + attendeInfo.get("attest"));
		String nameAndSign = "lkjh" + RandomStringUtils.randomAlphabetic(4);
		util.enterText(driver, printedNameAndSignature, nameAndSign);
		log.info("printedNameAndSignature enterd as" + nameAndSign);
	}

	public void clickRegistrationButton() throws Throwable {
		Utility.waitForWebElement(driver, continueButtonInRegistration, 0);
		continueButtonInRegistration.click();
		log.info("Continue button is clicked in registration");
		Thread.sleep(18000);
	}

	public void validateRegisterReq() {
		Utility.waitForWebElement(driver, firstName, 0);
		log.info(firstName.getAttribute("value"));
		System.out.println(lastName.getAttribute("value"));
		log.info(lastName.getAttribute("value"));
		System.out.println(email.getAttribute("value"));
		log.info(email.getAttribute("value"));
	}
	
	public void getScheduleDetailsInAgenda() throws InterruptedException{
		Thread.sleep(7000);
		ArrayList<String> scheduleinfo = new ArrayList<String>();
		String xpath = null;
		WebElement scheduleData=null;
		for (int i = 1; i <=3; i++) {
			xpath = "(//div[@data-name='itemDiv'])[" + i + "]";
		 scheduleData = driver.findElement(By.xpath(xpath));
		Utility.waitForWebElement(driver,scheduleData, 0);
		System.out.println(scheduleData.getAttribute("textContent"));
		log.info(scheduleData.getAttribute("textContent"));
		scheduleinfo.add(scheduleData.getAttribute("textContent"));
		}
		Assert.assertTrue(scheduleinfo.get(0).contains(EventConfig.scheduleDisplayNameInputFieldInAgenda));
	}

	public void agendaModule() throws Throwable {
		Thread.sleep(20000);
		Utility.waitForWebElement(driver, continueButtonInAgenda, 0);
		util.clickUsingJS(driver, continueButtonInAgenda);
		log.info("Continue button is clicked in agenda");
	}
	
	public ArrayList<Object> totalPaymentamountInCheckout() throws InterruptedException{
		Thread.sleep(12000);
		Utility.waitForWebElement(driver, totalAmountInCheckout, 0);
		totalAmount = totalAmountInCheckout.getText();
		if(totalAmount.equals("Free")) {
			totalAmount = "$0.00";
		}
		receiptData.add(0, totalAmount);
		log.info("Total Amount in checkout" + totalAmount);
		Thread.sleep(14000);
		return receiptData;
	}

	/**
	 * @return
	 * @throws Throwable
	 */
	public void paymentDataIncheckoutModule() throws Throwable {
		util.waitUntilElement(driver, cardHolderName);
		Utility.highLightElement(driver, cardHolderName);
		userName = cardHolderName.getAttribute("value");
		log.info(cardHolderName.getAttribute("value"));
		System.out.println(cardHolderName.getAttribute("value"));
		Thread.sleep(5000);
		util.waitUntilElement(driver, creditCardPaymentFrame);
		util.switchToFrameUsingWebElement(driver, creditCardPaymentFrame);
		Thread.sleep(5000);
		util.waitUntilElement(driver, cardNumFrame2);
		util.switchToFrameUsingWebElement(driver, cardNumFrame2);
		Utility.highLightElement(driver, creditCardNumber);
		util.waitUntilElement(driver, creditCardNumber);
		util.enterText(driver, creditCardNumber, testData.testDataProvider().getProperty("CREDIT_CARD_NUMBER"));
		log.info("Credit Card number enterd as" + testData.testDataProvider().getProperty("CREDIT_CARD_NUMBER"));
		driver.switchTo().defaultContent();
		Thread.sleep(2000);
		util.selectDropDownByText(expMonth, testData.testDataProvider().getProperty("CREDIT_CARD_EXP_MONTH"));
		log.info("ExpMonth selected as" + testData.testDataProvider().getProperty("CREDIT_CARD_EXP_MONTH"));
		util.selectDropDownByText(expYear, testData.testDataProvider().getProperty("CREDIT_CARD_EXP_YEAR"));
		log.info("ExpMonth selected as" + testData.testDataProvider().getProperty("CREDIT_CARD_EXP_YEAR"));
	}

	public void paymentProcessButton() throws Throwable {
//		WebElement buttonInPayment = driver.findElement(By.xpath(paymentButonfun(option)));
		util.scrollingElementUsingJS(driver, processPayment);
		util.clickUsingJS(driver, processPayment);
		log.info("processPayment is clicked successfully");
		Thread.sleep(7000);
		util.waitUntilElement(driver, paymentSuccessMessage);
		log.info("After Payment success message" + paymentSuccessMessage.getText());
	}

	public ArrayList<Object> confirmOrderIncheckout() throws Throwable {
		util.waitUntilElement(driver, totalAmountInCheckout);
		Utility.highLightElement(driver, totalAmountInCheckout);
		totalAmount = totalAmountInCheckout.getText();
		receiptData.add(0, "0.00");
		log.info("Total Amount in checkout" + totalAmount);
		Utility.waitForWebElement(driver, confirmOrderPayment, 20);
		util.clickUsingJS(driver, confirmOrderPayment);
		Thread.sleep(7000);
		util.waitUntilElement(driver, paymentSuccessMessage);
		log.info("After Payment success message" + paymentSuccessMessage.getText());
		return receiptData;
	}

	public void biilingaddressInCheckoutModule() throws Throwable {
		String type = "Home";
		util.waitUntilElement(driver, addressCreateButton);
		addressCreateButton.click();
		String addressName = "apk" + RandomStringUtils.randomAlphabetic(4);
		util.enterText(driver, billingAddressName, addressName);
		util.selectDropDownByText(billingType, type);
		util.waitUntilElement(driver, addressSearch);
		util.enterText(driver, addressSearch, "In");
		Thread.sleep(4000);
		log.info("event list size" + addressSearchOptions.size());
		for (int i = 0; i < addressSearchOptions.size(); i++) {
			String event = addressSearchOptions.get(i).getText();
			if (event.contains("Indonesia")) {
				addressSearchOptions.get(i).click();
				System.out.println("matched");
				break;
			} else {
				System.out.println("not matched");
			}
		}
		manualAddressCheckbox.click();
		log.info("Manual adress checkbox is selected");
		String street = "hgjay" + RandomStringUtils.randomAlphabetic(4);
		util.enterText(driver, streetName, street);
		String cityy = "hgjay" + RandomStringUtils.randomAlphabetic(4);
		util.enterText(driver, city, cityy);
		Utility.highLightElement(driver, country);
		util.selectDropDownByText(country, "African Republic");
		Utility.highLightElement(driver, zipCode);
		util.enterText(driver, zipCode, "435623");
		util.waitUntilElement(driver, saveButtonInBiilingaddress);
		saveButtonInBiilingaddress.click();
		log.info("Save Button is clicked successfully in Biilingaddress checkout");
	}

	public ArrayList<Object> clickReceiptInChecout() {
		receiptNum = receiptNumber.getText();
		receiptData.add(1, receiptNum);
		log.info("Receipt Number" + receiptNum);
		postedDate = postdDate.getText();
		receiptData.add(2, postedDate);
		log.info(" Event start poseted date " + postedDate);
		util.waitUntilElement(driver, viewRecieptInCheckout);
		Utility.highLightElement(driver, viewRecieptInCheckout);
		util.clickUsingJS(driver, viewRecieptInCheckout);
		log.info("View Receipts is clicked successfully using js & contains generate multiple pdf");
		return receiptData;
	}
	
	public void detailsNavButton() throws InterruptedException {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, detailsNavigationButton, 0);
		detailsNavigationButton.click();
	}
	
	public void validateEventOverView() throws InterruptedException {
		Thread.sleep(10000);
		util.scrollingElementUsingJS(driver, eventOverviewText);
		Utility.waitForWebElement(driver, eventOverviewText, 0);
		Assert.assertTrue(eventOverviewText.getText().contains(EventConfig.eventOverView));
	}

	public void speakersButtonInAIA() {
		Utility.waitForWebElement(driver, speakersButtonInAIAPage, 0);
		speakersButtonInAIAPage.click();
	}

	public void validateSpeakerInAIA() throws InterruptedException {
		Thread.sleep(9000);
		WebElement speaker = driver.findElement(By.xpath(EventRegistration.speakerName(EventConfig.speakerNameInputField)));
		log.info(speaker.getText());
		Assert.assertTrue(speaker.isDisplayed());
	}

	public void agendaNavigationButtonInAIA() {
		Utility.waitForWebElement(driver, agendaInAIA, 0);
		util.clickUsingJS(driver, agendaInAIA);
	}

	public void validateScheduleInAgenda() throws InterruptedException {
		Thread.sleep(9000);
		WebElement scheduleName = driver.findElement(By.xpath(EventRegistration.scheduleNameInAgenda(EventConfig.scheduleDisplayNameInputFieldInAgenda)));
		Assert.assertTrue(scheduleName.isDisplayed());
	}
	
	public void validateTime(String fontevaTime)throws InterruptedException {
		System.out.println("print time");
		Thread.sleep(15000);
		util.waitForJavascript(driver, 30000, 5000);
		Utility.waitForWebElement(driver, time, 0);
		String initialTimerText = time.getText();
	    util.waitUntilElement(driver, time);
	    System.out.println(initialTimerText);
	    if (initialTimerText.compareTo(fontevaTime) < 0) {
	       log.info("Registration time fonteva time is less than the AIA registartion timer");
	    }else {
	    	log.info("notyhing");
	    }
		Assert.assertTrue(time.isDisplayed(), "Time is displayed");
	}

}
