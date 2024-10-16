package org.aia.pages.ces;

import static org.junit.Assert.assertTrue;
import static org.testng.Assert.*;

import java.util.ArrayList;

import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;

/**
 * 
 */
/**
 * 
 */
/**
 * 
 */
public class Organization {

	WebDriver driver;
	Actions action;
 public	String orgName = "AutomationOrg";
	String orgStreet = "Street No-1";
	String orgCity = "New York.";
	String orgState = "Arizona";
	String orgPostalCode = "055443";
	String cesProviderNumber = "12345";

	Utility util = new Utility(driver, 30);

	public Organization(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
	}

	@FindBy(xpath = "//input[@name='Organization_Name']")
	WebElement organizationName;

	@FindBy(xpath = "//select[@name='Organization_Type']")
	WebElement organizationType;
	
	@FindBy(xpath = "//label[text()='Sub Type']/following-sibling::div//input")
	WebElement subType;

	@FindBy(xpath = "//select[@name='Prior_Provider']")
	WebElement organizationPriorProvider;

	@FindBy(xpath = "//input[@name='Former_CES_Provider_Number']")
	WebElement orgFormerCesProviderNumber;

	@FindBy(xpath = "//input[@name='country']")
	WebElement organizationCountry;

	@FindBy(xpath = "//input[@name='country']")
	WebElement organizationUnitedStatesOption;

	@FindBy(xpath = "//textarea[@name='street']")
	WebElement organizationStreet;

	@FindBy(xpath = "//input[@name='city']")
	WebElement organizationCity;

	@FindBy(xpath = "//input[@name='province']")
	WebElement organizationState;

	@FindBy(xpath = "//input[@name='postalCode']")
	WebElement organizationPostalCode;

	@FindBy(xpath = "//*[text()='Work Phone Country']/following-sibling::lightning-input-field/lightning-picklist")
	WebElement orgWorkPhoneCountry;

	@FindBy(xpath = "//label[text()='Work Phone Country']/following-sibling::*[text()='Please provide "
			+ "a valid input.']")
	WebElement workPhoneCountryValidation;

	@FindBy(xpath = "//label[text()='Work Phone']/following-sibling::lightning-input//div/input")
	WebElement organizationWorkPhoneNum;

	@FindBy(xpath = "//label[text()='Work Phone']/following-sibling::*[text()='Please provide a valid input.']")
	WebElement orgWorkPhoneValidation;

	@FindBy(xpath = "//input[@name='Website']")
	WebElement orgWebsite;

	@FindBy(xpath = "//button[text()='Previous']")
	WebElement orgPrevious;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement orgNext;

	@FindBy(xpath = "//input[@name='Organization_Tax_ID_number_EIN']")
	WebElement orgTaxIDTxtbox;

	@FindBy(xpath = "//input[@name='Estimated_annual_organization_revenue']")
	WebElement orgrevenueTxtbox;

	@FindBy(xpath = "//select[@name='Where_do_you_offer_courses']")
	WebElement orgCoursesSelect;

//		@FindBy(xpath = "//b[text()= 'Primary Point of Contact']")
//		WebElement tabTitleOrganization;

	@FindBy(xpath = "//div/span[@class='main']")
	WebElement tabTitleOrganization;

	@FindBy(xpath = "(//span[contains(normalize-space(), 'Please enter some valid input. Input is not optional.')])[1]")
	WebElement orgNameError;

	@FindBy(xpath = "//div//lightning-formatted-rich-text/span[text()='Please select a choice.']")
	WebElement orgPriorProviderError;

	@FindBy(xpath = "//span[@class='error']")
	WebElement workPhoneCountryError;

	@FindBy(xpath = "//span[@class='error']")
	WebElement workPhoneError;

	@FindBy(xpath = "//span[text()=\"Please select the country code as you've already entered the work phone number\"]")
	WebElement workPhoneCountryError2;

	@FindBy(xpath = "//span[@class='error']")
	WebElement workPhoneError2;

	@FindBy(xpath = "//span[contains(text(), 'Phone number must be 10 digits.')]")
	WebElement invalidNumberValidation;

	@FindBy(xpath = "//span[contains(text(), 'Please select the country code')]")
	WebElement workCountryText;

	String accountName = "//span[text()='%s']/ancestor::a";

	/*
	 * Enter Mandatory Organization Details.
	 */
	public String enterOrganizationDetails(ArrayList<String> dataList, String orgType, String provider,
			String countryCode) throws InterruptedException {
		util.waitUntilElement(driver, organizationName);
		organizationName.sendKeys(orgName);
		util.waitUntilElement(driver, organizationType); // added
		util.selectDropDownByText(organizationType, orgType);
		// added
//		util.enterText(driver,subType, "other");
//	    action.keyDown(Keys.ARROW_DOWN).build().perform();
//	    action.keyUp(Keys.ARROW_DOWN).build().perform();
//	    action.keyDown(Keys.ENTER).build().perform();
//	    action.keyUp(Keys.ENTER).build().perform();
		util.waitUntilElement(driver, organizationPriorProvider);
		util.selectDropDownByText(organizationPriorProvider, provider);
		if (provider.equalsIgnoreCase("Yes")) {
			util.waitUntilElement(driver, orgFormerCesProviderNumber);
			orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
		}
		orgWorkPhoneCountry.click();
		// Sample : driver.findElement(By.xpath("//span[@title=\"United States of
		// America (+1)\"]")).click();
		driver.findElement(By.xpath("//span[@title='" + countryCode + "']")).click();
		organizationWorkPhoneNum.sendKeys(dataList.get(2));
		orgTaxIDTxtbox.sendKeys(orgName);
		orgrevenueTxtbox.sendKeys("1000");
		util.selectDropDownByText(orgCoursesSelect, "National");
		orgNext.click();
		System.out.println("MY ORG type:" + orgType);
		return orgType;

	}

	/*
	 * Validate Organization Tab.
	 */

	public void verifyOrganizationTab() {
		util.waitUntilElement(driver, tabTitleOrganization);
		assertTrue(tabTitleOrganization.isDisplayed());

	}

	/*
	 * Validate Organization Tab Refresh Function.
	 */
	public void refreshFunction() {
		util.waitUntilElement(driver, tabTitleOrganization);
		assertTrue(tabTitleOrganization.isDisplayed());
		driver.navigate().refresh();
	}
	/*
	 * Enter Organization Tab Details Without Next
	 */

	public void enterOrganizationDetailsWithoutNext(ArrayList<String> dataList, String orgType, String provider,
			String countryCode) throws InterruptedException {
		Thread.sleep(20000);
		util.waitUntilElement(driver, organizationName);
		organizationName.sendKeys(orgName);
		util.waitUntilElement(driver, organizationType);
		util.selectDropDownByText(organizationType, orgType);
		// added
		util.waitUntilElement(driver, organizationPriorProvider);
		util.selectDropDownByText(organizationPriorProvider, provider);
		if (provider.equalsIgnoreCase("Yes")) {
			util.waitUntilElement(driver, orgFormerCesProviderNumber);
			orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
		}
		orgWorkPhoneCountry.click();
		// Sample : driver.findElement(By.xpath("//span[@title=\"United States of
		// America (+1)\"]")).click();
		driver.findElement(By.xpath("//span[@title='" + countryCode + "']")).click();
		organizationWorkPhoneNum.sendKeys(dataList.get(2));
		orgTaxIDTxtbox.sendKeys(orgName);
		orgrevenueTxtbox.sendKeys("1000");
		util.selectDropDownByText(orgCoursesSelect, "National");
		System.out.println("MY ORG type:" + orgType);

	}

	/*
	 * Validate Organization Name Error
	 */

	public void validateORGNameError() {
		organizationName.clear();
		orgNext.click();
		util.waitUntilElement(driver, orgNameError);
		assertTrue(orgNameError.isDisplayed());
	}

	/*
	 * Validate Organization Type Error
	 */

	public void validateORGTypeError() {
		organizationName.clear();
		orgNext.click();
		util.waitUntilElement(driver, orgNameError);
		assertTrue(orgNameError.isDisplayed());
	}

	/*
	 * Validate Prior Provider Error
	 */

	public void validatePriorProviderError(ArrayList<String> dataList, String orgType, String countryName,
			String countryCode) {
		util.waitUntilElement(driver, organizationName);
		organizationName.sendKeys(orgName);
		util.selectDropDownByText(organizationType, orgType);
		organizationCountry.click();
		driver.findElement(By.xpath("//span[@title='" + countryName + "']")).click();
		util.waitUntilElement(driver, organizationCity);
		organizationStreet.sendKeys(orgStreet);
		organizationCity.sendKeys(orgCity);
		organizationState.sendKeys(orgState);
		organizationPostalCode.sendKeys(orgPostalCode);
		orgWorkPhoneCountry.click();
		driver.findElement(By.xpath("//span[@title='" + countryCode + "']")).click();
		organizationWorkPhoneNum.sendKeys(dataList.get(2));
		orgWebsite.sendKeys(dataList.get(7));
		orgNext.click();
		util.waitUntilElement(driver, orgPriorProviderError);
		assertTrue(orgPriorProviderError.isDisplayed());
	}

	/**
	 * Validate Error on Organization Tab without providing any details
	 */

	public void validateAllErrorsOnORGTab() {
		util.waitUntilElement(driver, orgNext);
		orgNext.click();
		util.waitUntilElement(driver, orgNameError);
		assertTrue(orgNameError.isDisplayed());
		assertTrue(orgPriorProviderError.isDisplayed());
		util.waitUntilElement(driver, workPhoneCountryError);
		assertTrue(workPhoneCountryError.isDisplayed());
	}

	/**
	 * Validate Error on Organization Tab without providing Organization Name
	 */

	public void validateErrorForOrgName(ArrayList<String> dataList, String orgType, String provider,
			String countryCode) {
		util.waitUntilElement(driver, organizationType);
		util.selectDropDownByText(organizationType, orgType);
		util.waitUntilElement(driver, organizationPriorProvider);
		util.selectDropDownByText(organizationPriorProvider, provider);
		if (provider.equalsIgnoreCase("Yes")) {
			util.waitUntilElement(driver, orgFormerCesProviderNumber);
			orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
		}
		orgWorkPhoneCountry.click();
		// Sample : driver.findElement(By.xpath("//span[@title=\"United States of
		// America (+1)\"]")).click();
		driver.findElement(By.xpath("//span[@title='" + countryCode + "']")).click();
		organizationWorkPhoneNum.sendKeys(dataList.get(2));
		orgTaxIDTxtbox.sendKeys(orgName);
		orgrevenueTxtbox.sendKeys("1000");
		util.selectDropDownByText(orgCoursesSelect, "National");
		orgNext.click();
		util.waitUntilElement(driver, orgNameError);
		assertTrue(orgNameError.isDisplayed());

	}

	/**
	 * Validate Error for 'Work phone country'
	 */

	public void validateWorkPhoneCountyError(ArrayList<String> dataList, String orgType, String provider)
			throws InterruptedException {
		util.waitUntilElement(driver, organizationName);
		organizationName.sendKeys(orgName);
		util.waitUntilElement(driver, organizationType);
		util.selectDropDownByText(organizationType, orgType);
		util.waitUntilElement(driver, organizationPriorProvider);
		util.selectDropDownByText(organizationPriorProvider, provider);
		if (provider.equalsIgnoreCase("Yes")) {
			util.waitUntilElement(driver, orgFormerCesProviderNumber);
			orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
		}
		orgTaxIDTxtbox.sendKeys(orgName);
		orgrevenueTxtbox.sendKeys("1000");
		util.selectDropDownByText(orgCoursesSelect, "National");
		util.waitUntilElement(driver, workPhoneCountryError);
		assertTrue(workPhoneCountryError.isDisplayed());
		util.waitUntilElement(driver, workPhoneError);
		assertTrue(workPhoneError.isDisplayed());
		// Enter number in 'Work phone' tab
		organizationWorkPhoneNum.sendKeys(dataList.get(2));
		orgNext.click();
		Thread.sleep(5000);
		util.scrollingElementUsingJS(driver, workPhoneCountryError2);
		util.waitUntilElement(driver, workPhoneCountryError2);
		assertTrue(workPhoneCountryError2.isDisplayed());
	}

	/**
	 * Validate Error for 'Work phone'
	 */

	public void validateWorkPhoneError(ArrayList<String> dataList, String orgType, String provider, String countryCode)
			throws InterruptedException {
		util.waitUntilElement(driver, organizationName);
		organizationName.sendKeys(orgName);
		util.waitUntilElement(driver, organizationType);
		util.selectDropDownByText(organizationType, orgType);
		util.waitUntilElement(driver, organizationPriorProvider);
		util.selectDropDownByText(organizationPriorProvider, provider);
		if (provider.equalsIgnoreCase("Yes")) {
			util.waitUntilElement(driver, orgFormerCesProviderNumber);
			orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
		}
		orgTaxIDTxtbox.sendKeys(orgName);
		orgrevenueTxtbox.sendKeys("1000");
		util.selectDropDownByText(orgCoursesSelect, "National");
		orgNext.click();
		util.waitUntilElement(driver, workPhoneCountryError);
		assertTrue(workPhoneCountryError.isDisplayed());
		util.waitUntilElement(driver, workPhoneError);
		assertTrue(workPhoneError.isDisplayed());
		orgWorkPhoneCountry.click();
		orgNext.click();
		util.waitUntilElement(driver, workPhoneError2);
		assertTrue(workPhoneError2.isDisplayed());

	}

	public void enterInvalidWorkPhoneCountry(ArrayList<String> dataList, String orgType, String provider)
			throws InterruptedException {
		util.waitUntilElement(driver, organizationName);
		organizationName.sendKeys(orgName);
		util.waitUntilElement(driver, organizationType);
		util.selectDropDownByText(organizationType, orgType);
		util.waitUntilElement(driver, organizationPriorProvider);
		util.selectDropDownByText(organizationPriorProvider, provider);
		if (provider.equalsIgnoreCase("Yes")) {
			util.waitUntilElement(driver, orgFormerCesProviderNumber);
			orgFormerCesProviderNumber.sendKeys(cesProviderNumber);
		}
		orgTaxIDTxtbox.sendKeys(orgName);
		orgrevenueTxtbox.sendKeys("1000");
		util.selectDropDownByText(orgCoursesSelect, "National");
		orgNext.click();
		util.waitUntilElement(driver, workPhoneError);
		assertTrue(workPhoneError.isDisplayed());

	}

	public void enterDetailsWithoutWorkCountry(ArrayList<String> dataList) throws InterruptedException {

		organizationWorkPhoneNum.sendKeys(dataList.get(2));
		orgNext.click();
		WebElement textValidation = Utility.waitForWebElement(driver, workCountryText, 10);
		Assert.assertEquals("Please select the country code as you've already entered the work phone number",
				textValidation.getText());

	}

}
