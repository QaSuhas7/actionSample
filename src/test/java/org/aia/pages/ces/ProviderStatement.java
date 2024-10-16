package org.aia.pages.ces;


import static org.testng.Assert.assertTrue;

import static org.testng.Assert.assertTrue;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.testng.Assert.assertTrue;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProviderStatement {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();

	public ProviderStatement(WebDriver Idriver) {
		this.driver = Idriver;
	}



	@FindBy(xpath="//div/span[@class='main']") 
	WebElement tabTitleProvider;
	


	@FindBy(xpath = "//input[@name='Name']")
	WebElement nameProviderStatement;

	@FindBy(xpath = "//input[@name='Date']")
	WebElement dateProviderStatement;

	@FindBy(xpath = "//h2[@role='alert']")
	WebElement monthNamePrvdrStmnt;

	@FindBy(xpath = "//button[@title='Previous Month']")
	WebElement previousMnthPrvdrStmnt;

	@FindBy(xpath = "//button[@title='Next Month']")
	WebElement nextMnthPrvdrStmnt;

	@FindBy(xpath = "//label[text()='Pick a Year']/following-sibling::div/div/select")
	WebElement selectYearPrvdrStmnt;

	@FindBy(xpath = "//table[@class='slds-datepicker__month']")
	WebElement dateTablePrvdrStmnt;

	@FindBy(xpath = "//button[text()='Today']")
	WebElement todayDatePrvdrStmnt;

	@FindBy(xpath = "//span[text()='Date']/parent::lightning-formatted-rich-text/parent::div/parent::div//input")
	WebElement datePrvdrStmntInput;

	@FindBy(xpath = "//div/button[1]/text()/parent::button")
	WebElement providerStatementPrevious;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement providerStatementNext;

	@FindBy(xpath = "//div[contains(text(), 'Your entry does not match the allowed format MMM d, yyyy.')]")
	WebElement invalidDateFormatError;

	@FindBy(xpath = "//div[contains(text(), 'Complete this field.')]")
	WebElement completeFieldError;

	@FindBy(xpath = "//div[@role='status']//span")
	WebElement enterSomeValidInputError;
	
	





	public void providerStatementEnterNameDate(String firstname) throws InterruptedException {

		util.waitUntilElement(driver, nameProviderStatement);
		nameProviderStatement.sendKeys(firstname);
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

		String date = formatter.format(d);
		String splitter[] = date.split("-");
		String year = splitter[2];
		String month = splitter[1];
		String day = splitter[0];
		System.out.println(year);
		System.out.println(month);
		System.out.println(day);

		selectDate(day, month, year);
		// selectDate("05","07","2020");
		Thread.sleep(3000);

		util.waitUntilElement(driver, providerStatementNext);
		providerStatementNext.click();
	}

	public void providerStatementEnterNameDate2(String firstname) throws InterruptedException {

		util.waitUntilElement(driver, nameProviderStatement);
		nameProviderStatement.sendKeys(firstname);
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
		String date = formatter.format(d);
		Thread.sleep(3000);
		datePrvdrStmntInput.sendKeys(date);
		util.waitUntilElement(driver, providerStatementNext);
		providerStatementNext.click();
	}

	public void providerStatementEnterNameDateToday() {
		dateProviderStatement.click();
		util.waitUntilElement(driver, todayDatePrvdrStmnt);
		todayDatePrvdrStmnt.click();
		util.waitUntilElement(driver, providerStatementNext);
		providerStatementNext.click();
	}

	public void selectDate(String day, String mnth, String year) throws InterruptedException {

		dateProviderStatement.click();
		util.waitUntilElement(driver, selectYearPrvdrStmnt);
		util.selectDropDownByText(selectYearPrvdrStmnt, year);
		int currMnth = Integer.valueOf(monthNamePrvdrStmnt.getAttribute("data-index"));
		int m = Integer.valueOf(mnth);
		int month = m - 1;

		if (currMnth > month) {

			for (int i = currMnth; i < 12; i--) {
				if (i == month) {
					break;
				} else {
					previousMnthPrvdrStmnt.click();
				}
			}
		}

		else if (currMnth < month) {

			for (int i = currMnth; i < 12; i++) {
				if (i == month) {
					break;
				} else {
					nextMnthPrvdrStmnt.click();
				}
			}
		}

		// Selecting the date
		List<WebElement> days = driver.findElements(By.xpath("//lightning-calendar/div//table/tbody/tr/td/span"));
		for (WebElement d : days) {
			System.out.println(d.getText());
			if (d.getText().equals(day)) {
				d.click();
				Thread.sleep(10000);
				return;
			}
		}
	}
	public void providerStatementEnterNameInvalidDate(String firstname) throws InterruptedException {
		Date d = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/mm/dd");
		String date = formatter.format(d);
		Thread.sleep(3000);
		util.waitUntilElement(driver, datePrvdrStmntInput);
		datePrvdrStmntInput.sendKeys(date);
		util.waitUntilElement(driver, nameProviderStatement);
		nameProviderStatement.sendKeys(firstname);
		util.waitUntilElement(driver, invalidDateFormatError);
		String invalidDateFormatErrorValue = invalidDateFormatError.getText();
		System.out.println("invalid DateFormat Error: " + invalidDateFormatErrorValue);
		assertTrue(invalidDateFormatErrorValue
				.equalsIgnoreCase(data.testDataProvider().getProperty("invalidDateFormatError")));

	}

	public void providerStatementwithNamewithoutDate(String firstname) throws InterruptedException {
		util.waitUntilElement(driver, nameProviderStatement);
		nameProviderStatement.sendKeys(firstname);
		util.waitUntilElement(driver, providerStatementNext);
		providerStatementNext.click();
		util.waitUntilElement(driver, enterSomeValidInputError);
		String enterSomeValidInputErrorValue = enterSomeValidInputError.getText();
		assertTrue(enterSomeValidInputErrorValue
				.equalsIgnoreCase(data.testDataProvider().getProperty("enterSomeValidInputErrorMsg")));
	}

	public void providerStatementwithoutEnterNameDate() {
		util.waitUntilElement(driver, providerStatementNext);
		providerStatementNext.click();
		util.waitUntilElement(driver, completeFieldError);
		String completeFieldErrorvalue = completeFieldError.getText();
		assertTrue(
				completeFieldErrorvalue.equalsIgnoreCase(data.testDataProvider().getProperty("completeFieldErrorMsg")));

	}

	public void verifyPresenceofNameandDatefields() {
		util.waitUntilElement(driver, nameProviderStatement);
		nameProviderStatement.isDisplayed();
		util.waitUntilElement(driver, dateProviderStatement);
		dateProviderStatement.isDisplayed();
	}
	
	/*
	 * Validate Organization Tab.
	 */
	
	public void verifyProviderStatementTab() {
		util.waitUntilElement(driver, tabTitleProvider);
		assertTrue(tabTitleProvider.isDisplayed());
		
	}
}
