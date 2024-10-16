package org.aia.pages.ces;

import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.aia.utility.Utility;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class SecondaryPointOfContact {

	WebDriver driver;

	Utility util = new Utility(driver, 30);

	public SecondaryPointOfContact(WebDriver Idriver) {
		this.driver = Idriver;
	}
	// ArchitectureElements

	@FindBy(xpath = "//select[@name='Would_you_like_to_add_a_Secondary_Point_of_Contact']")
	WebElement organizationArchFirmSubscription;

	@FindBy(xpath = "//select[@name='PrefixSecondaryPOC']")
	WebElement prefixSecondary;

	@FindBy(xpath = "//*[@name='First_NameSecondaryPOC']")
	WebElement firstNameSecondary;

	@FindBy(xpath = "//*[@name='Last_NameSecondaryPOC']")
	WebElement lasttNameSecondary;

	@FindBy(xpath = "//*[@name='SuffixSecondaryPOC']")
	WebElement SuffixPicklistSecondary;

	@FindBy(xpath = "//*[@name='AIA_Mobile_Phone_Country__c']")
	WebElement mobilePhoneCountrySecondary;

//	@FindBy(xpath = "//label[text()='Mobile Phone']/following-sibling::lightning-input/div/input")
//	WebElement mobilePhoneNumSecondary;

	@FindBy(xpath = "//label[text()='Mobile Phone']/following-sibling::lightning-input//input")
	WebElement mobilePhoneNumSecondary;

	@FindBy(xpath = "//*[@name='AIA_Work_Phone_Country__c']")
	WebElement workPhoneCountrySecondary;

	@FindBy(xpath = "//label[text()='Work Phone']/following-sibling::lightning-input//input")
	WebElement workPhoneNumSecondary;

	@FindBy(xpath = "//input[@name='TitleSecondaryPOC']")
	WebElement titleSecondary;

	@FindBy(xpath = "//input[@name='Primary_Email_AddressSecondaryPOC']")
	WebElement primaryEmailAddress;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement nextBtnSecondary;

	@FindBy(xpath = "//*[@name='Middle_InitialSecondaryPOC")
	WebElement middleIntialSecondary;

	@FindBy(xpath = "//span[contains(text(), 'Phone number must be 10 digits.')]")
	WebElement invalidNumberValidation;

	@FindBy(xpath = "(//span[@class='error'])")
	WebElement workPhoneError;

	@FindBy(xpath = "//span[contains(text(), 'Please select the country code')]")
	WebElement workCountryText;

	@FindBy(xpath = "//span[contains(text(), 'Please enter a mobile phone number')]")
	WebElement mobilePhoneText;

	@FindBy(xpath = "//span[contains(text(), 'Please select the country code')]")
	WebElement mobileCountryText;

	@FindBy(xpath = "//div//span[@class='main']")
	WebElement tabTitleSecondarypoc;

	String fName_sec;
	String lName_sec;
	String workmobNumb;
	public String emailaddressdata;
	public String emailPrefix;
	public String emailDomain;
	ArrayList<String> list = new ArrayList<String>();

	public ArrayList<String> secPOCData() throws Exception {

		fName_sec = "autofn" + RandomStringUtils.randomAlphabetic(4);
		list.add(0, fName_sec);
		System.out.println(fName_sec);
		lName_sec = "autoln" + RandomStringUtils.randomAlphabetic(4);
		list.add(1, lName_sec);
		workmobNumb = "09999" + String.format("%05d", new Random().nextInt(100000));
		list.add(2, workmobNumb);
		System.out.println(workmobNumb);
		DateFormat dateFormat = new SimpleDateFormat("mmmddyyyy");
		Date date = new Date();
		System.out.println(date.toString());
		String date1 = dateFormat.format(date);
		System.out.println(date1);
		emailPrefix = "auto_secuser_" + RandomStringUtils.randomAlphabetic(4).toLowerCase() + date1;
		list.add(3, emailPrefix);
		emailDomain = "@architects-team.m8r.co";
		list.add(4, emailDomain);
		emailaddressdata = emailPrefix + emailDomain;
		list.add(5, emailaddressdata);
		System.out.println("Additional User emai ID: " + emailaddressdata);
		return list;
	}

	public void enterSecondaryPocDetails(ArrayList<String> dataList, String value, String suffix, String select,
			String country) throws Exception {
		util.waitForJavascript(driver, 20000, 3000);
		Utility.waitForWebElement(driver, organizationArchFirmSubscription, 20);
		util.selectDropDownByText(organizationArchFirmSubscription, select);
		if (select.equalsIgnoreCase("yes")) {
			util.waitUntilElement(driver, prefixSecondary);
			Thread.sleep(5000);
			util.selectDropDownByText(prefixSecondary, value);
			util.selectDropDownByText(SuffixPicklistSecondary, suffix);
			ArrayList<String> sec_pocList = secPOCData();
			firstNameSecondary.sendKeys(sec_pocList.get(0));
			lasttNameSecondary.sendKeys(sec_pocList.get(1));
			util.waitUntilElement(driver, workPhoneCountrySecondary);
			// workPhoneCountrySecondary.click();
			// driver.findElement(By.xpath("//span[text()='United States of America
			// (+1)']")).click();
			selectWorkPhoneCountry(country);
			workPhoneNumSecondary.clear();
			workPhoneNumSecondary.sendKeys(sec_pocList.get(2));
			primaryEmailAddress.sendKeys(sec_pocList.get(5));
		}
		nextBtnSecondary.click();
	}

	public void selectWorkPhoneCountry(String country) {
		WebElement countrydrpdwn = Utility.waitForWebElement(driver, workPhoneCountrySecondary, 10);
		countrydrpdwn.click();
		WebElement e2 = Utility.waitForWebElement(driver,
				"//label[text()='Work Phone Country']//parent::div//span[@title='" + country + "']", 10);
		e2.click();
	}

	public void selectMobilePhoneCountry(String country) {
		WebElement countrydrpdwn = Utility.waitForWebElement(driver, mobilePhoneCountrySecondary, 10);
		countrydrpdwn.click();
		WebElement e2 = Utility.waitForWebElement(driver,
				"//label[text()='Mobile Phone Country']//parent::div//span[@title='" + country + "']", 10);
		e2.click();
	}

	public void enterInvalidWorkPhoneNumber(ArrayList<String> dataList, String value, String suffix, String select,
			String country) throws Exception {
		util.waitUntilElement(driver, organizationArchFirmSubscription);
		util.selectDropDownByText(organizationArchFirmSubscription, select);
		if (select.equalsIgnoreCase("yes")) {
			util.waitUntilElement(driver, prefixSecondary);
			Thread.sleep(5000);
			util.selectDropDownByText(prefixSecondary, value);
			util.selectDropDownByText(SuffixPicklistSecondary, suffix);
			ArrayList<String> sec_pocList = secPOCData();
			firstNameSecondary.sendKeys(sec_pocList.get(0));
			lasttNameSecondary.sendKeys(sec_pocList.get(1));
			util.waitUntilElement(driver, workPhoneCountrySecondary);
			selectWorkPhoneCountry(country);
			workPhoneNumSecondary.clear();
			workPhoneNumSecondary.sendKeys("9999" + String.format("%04d", new Random().nextInt(10000)));
			primaryEmailAddress.sendKeys(sec_pocList.get(5));
		}
		nextBtnSecondary.click();
		WebElement textValidation = Utility.waitForWebElement(driver, invalidNumberValidation, 10);
		Assert.assertEquals("Phone number must be 10 digits.", textValidation.getText());
	}

	public void enterInvalidWorkPhoneCountry(ArrayList<String> dataList, String value, String suffix, String select)
			throws Exception {
		util.waitUntilElement(driver, organizationArchFirmSubscription);
		util.selectDropDownByText(organizationArchFirmSubscription, select);
		if (select.equalsIgnoreCase("yes")) {
			util.waitUntilElement(driver, prefixSecondary);
			Thread.sleep(5000);
			util.selectDropDownByText(prefixSecondary, value);
			util.selectDropDownByText(SuffixPicklistSecondary, suffix);
			ArrayList<String> sec_pocList = secPOCData();
			firstNameSecondary.sendKeys(sec_pocList.get(0));
			lasttNameSecondary.sendKeys(sec_pocList.get(1));
			util.waitUntilElement(driver, workPhoneCountrySecondary);
			workPhoneNumSecondary.clear();
			primaryEmailAddress.sendKeys(sec_pocList.get(5));
		}
		nextBtnSecondary.click();
		util.waitUntilElement(driver, workPhoneError);
		assertTrue(workPhoneError.isDisplayed());

	}

	public void enterDetailsWithoutWorkCountry(ArrayList<String> dataList) throws Exception {

		ArrayList<String> sec_pocList = secPOCData();
		util.waitUntilElement(driver, workPhoneCountrySecondary);
		workPhoneNumSecondary.clear();
		workPhoneNumSecondary.sendKeys(sec_pocList.get(2));
		nextBtnSecondary.click();
		WebElement textValidation = Utility.waitForWebElement(driver, workCountryText, 10);
		Assert.assertEquals("Please select the country code as you've already entered the work phone number",
				textValidation.getText());

	}

	public void enterOtherWorkCountry(ArrayList<String> dataList, String country) throws Exception {

		ArrayList<String> sec_pocList = secPOCData();
		util.waitUntilElement(driver, workPhoneCountrySecondary);
		selectWorkPhoneCountry(country);
		workPhoneNumSecondary.clear();
		workPhoneNumSecondary.sendKeys(sec_pocList.get(2));
		nextBtnSecondary.click();

	}

	public void enterDetailswithMobileCountry(ArrayList<String> dataList, String value, String suffix, String select,
			String country) throws Exception {
		util.waitUntilElement(driver, organizationArchFirmSubscription);
		util.selectDropDownByText(organizationArchFirmSubscription, select);
		if (select.equalsIgnoreCase("yes")) {
			util.waitUntilElement(driver, prefixSecondary);
			Thread.sleep(5000);
			util.selectDropDownByText(prefixSecondary, value);
			util.selectDropDownByText(SuffixPicklistSecondary, suffix);
			ArrayList<String> sec_pocList = secPOCData();
			firstNameSecondary.sendKeys(sec_pocList.get(0));
			lasttNameSecondary.sendKeys(sec_pocList.get(1));
			util.waitUntilElement(driver, mobilePhoneCountrySecondary);
			selectMobilePhoneCountry(country);
			util.waitUntilElement(driver, workPhoneCountrySecondary);
			selectWorkPhoneCountry(country);
			workPhoneNumSecondary.clear();
			workPhoneNumSecondary.sendKeys(sec_pocList.get(2));
			primaryEmailAddress.sendKeys(sec_pocList.get(5));
		}
		nextBtnSecondary.click();
		WebElement textValidation = Utility.waitForWebElement(driver, mobilePhoneText, 10);
		Assert.assertEquals("Please enter a mobile phone number as you've already selected the country code.",
				textValidation.getText());
	}

	public void enterDetailswithMobilePhone(ArrayList<String> dataList, String value, String suffix, String select,
			String country) throws Exception {
		util.waitUntilElement(driver, organizationArchFirmSubscription);
		util.selectDropDownByText(organizationArchFirmSubscription, select);
		if (select.equalsIgnoreCase("yes")) {
			util.waitUntilElement(driver, prefixSecondary);
			Thread.sleep(5000);
			util.selectDropDownByText(prefixSecondary, value);
			util.selectDropDownByText(SuffixPicklistSecondary, suffix);
			ArrayList<String> sec_pocList = secPOCData();
			firstNameSecondary.sendKeys(sec_pocList.get(0));
			lasttNameSecondary.sendKeys(sec_pocList.get(1));
			mobilePhoneNumSecondary.clear();
			mobilePhoneNumSecondary.sendKeys(sec_pocList.get(2));
			util.waitUntilElement(driver, workPhoneCountrySecondary);
			selectWorkPhoneCountry(country);
			workPhoneNumSecondary.clear();
			workPhoneNumSecondary.sendKeys(sec_pocList.get(2));
			primaryEmailAddress.sendKeys(sec_pocList.get(5));
		}
		nextBtnSecondary.click();
		WebElement textValidation = Utility.waitForWebElement(driver, mobileCountryText, 10);
		Assert.assertEquals("Please select the country code as you've already entered the mobile phone number",
				textValidation.getText());
	}

	/**
	 * Validating Secondary POC Tab
	 */
	public void verifySecondaryPOCTab() {
		util.waitUntilElement(driver, tabTitleSecondarypoc);
		assertTrue(tabTitleSecondarypoc.isDisplayed());
	}

	/**
	 * Refresh Page
	 */
	public void secondaryPOCTabRefresh() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(20000);
	}
}
