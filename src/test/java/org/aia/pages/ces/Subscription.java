package org.aia.pages.ces;


import static org.testng.Assert.assertTrue;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * 
 */
/**
 * 
 */
public class Subscription {

	WebDriver driver;

	Utility util = new Utility(driver, 30);

	ConfigDataProvider data = new ConfigDataProvider();

	public Subscription(WebDriver Idriver) {
		this.driver = Idriver;
	}
	// ArchitectureElements

	@FindBy(xpath = "//select[@name='Is_your_organization_an_architecture_firm_or_design_firm_in_which_a_principal_or']")
	WebElement archFirmSubscription;

	@FindBy(xpath = "//input[@name='Enter_the_AIA_member_number_of_the_principal_owner']")
	WebElement archFirmAIAMemNum;

	@FindBy(xpath = "//button[text()='Previous']")
	WebElement subscriptionPrevious;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement subscriptionNext;

	// PassportElements

	@FindBy(xpath = "//button[text()='Previous']")
	WebElement passportPrevious;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement passportNext;

	// ProfessionElements

	@FindBy(xpath = "//select[@name='s_your_organization_any_of_the_following']")
	WebElement profQualifyingQues;

	@FindBy(xpath = "//*[text()='Upload Files']")
	WebElement profUploadFile;

	@FindBy(xpath = "//input[@name='uploadFile']")
	WebElement pdfUploadElement;

	// @FindBy(xpath="//span[text()='Done'] |
	// //span[text()='Done']//parent::button") WebElement
	// pofessionalFileUplaodDoneButton;

	@FindBy(xpath = "//span[text()='Done']//parent::button")
	WebElement pofessionalFileUplaodDoneButton;

	@FindBy(xpath = "//button[@title='Delete Document']")
	WebElement pofessionalDeleteBtn;

	// Alert : Your company doesn't support the following file types: .txt
	@FindBy(xpath = "//div[contains(@id,'help-message')]")
	WebElement pofessionalUnsupportedFilTypes;

	


	// ConfirmDetailsPage
	@FindBy(xpath = "//*[contains(text(),'You will not be able to go back once you continue.')]")
	WebElement confirmContinueText;

	@FindBy(xpath = "//*[contains(text(),'Organization Name')]/parent::p")
	WebElement orgNameValue;

	@FindBy(xpath = "//button[text()='Previous']")
	WebElement confirmPrevious;

	@FindBy(xpath = "//button[text()='Next']")
	WebElement confirmNext;

	@FindBy(xpath = "//span[text()='What is your employee size?']")
	WebElement empSizetxt;




	@FindBy(xpath = "//div[@class='slds-m-bottom_x-small']/span[@class='main']/img[contains(@src, 'Subscription')]")
	WebElement tabTitleSubscription;

	@FindBy(xpath = "//b[text()= 'Primary Point of Contact']")
	WebElement tabTitlePrimarypoc;

	@FindBy(xpath = "//span/p/b[contains(text(), 'Based on the organization type you have chosen, you are eligible for')]")
	WebElement subscriptionTabText;

	@FindBy(xpath = "//*[contains(text(),'﻿Based on the organization type you have chosen, you are eligible for:')]")
	WebElement subscriptiontxt;

	@FindBy(xpath = "//span[contains(text(),'Based on the organization type you had chosen you are eligible for the following subscription type:')]/..")
	WebElement confirmationTxtLine2;

	@FindBy(xpath = "//*[text()[contains(.,' to edit details or if the information looks correct click \"')]]/..")
	WebElement confirmationTxtLine1;

	@FindBy(xpath = "//*[text()[contains(.,'Confirm Details')]]")
	WebElement confirmationTxt;

	@FindBy(xpath = "//strong[text()='Organization Name: ']")
	WebElement organizationName;

	@FindBy(xpath = "//strong[text()='Organization Type: ']")
	WebElement organazitionType;

	@FindBy(xpath = "//strong[text()='Prior Provider: ']")
	WebElement priorProvider;

	@FindBy(xpath = "//strong[text()='Former CES Provider Number: ']")
	WebElement formerCESProviderNum;

	@FindBy(xpath = "//strong[text()='Work Phone Country Code: ']")
	WebElement workPhoneCountryCode;

	@FindBy(xpath = "//strong[text()='Work Phone:']")
	WebElement workPhone;


	@FindBy(xpath="//button[text()='Previous']") WebElement professionalPrevious;
	
	@FindBy(xpath="//button[text()='Next']") WebElement professionalNext;
	

	
	
	
	/*
	 * @param : text
	 * 
	 * @param : aiaMemberNumber
	 * 
	 * @param : orgType
	 */

	public void SubscriptionType(String text, String isFirmSubscription, String aiaMemberNumber, String orgType)
			throws InterruptedException {
		if (text.contentEquals("Architecture Firm") || text.contentEquals("Architecture - Single Discipline")
				|| text.toLowerCase().contentEquals("Multi-Disciplinary (Architect Led)".toLowerCase())
				|| text.toLowerCase().contentEquals("Multi-disciplinary (engineer led)".toLowerCase()
						)
				|| text.toLowerCase().contentEquals("Multi-disciplinary (interior led)".toLowerCase())
				|| text.toLowerCase().contentEquals("Multi-disciplinary (planning led)".toLowerCase())
				|| text.toLowerCase().contentEquals("Design & construction services".toLowerCase())) {
			ArchitectType(isFirmSubscription, aiaMemberNumber);
		}

		else if (text.toLowerCase().contentEquals("Building product manufacturer".toLowerCase()) || text.toLowerCase().contentEquals("Construction".toLowerCase())
				|| text.toLowerCase().contentEquals("Consulting".toLowerCase()) || text.toLowerCase().contentEquals("Engineering".toLowerCase())
				|| text.toLowerCase().contentEquals("Interior Design".toLowerCase()) || text.toLowerCase().contentEquals("Landscape".toLowerCase())
				|| text.toLowerCase().contentEquals("Real Estate/Building owner".toLowerCase()) || text.toLowerCase().contentEquals("Law".toLowerCase())
				|| text.toLowerCase().contentEquals("Press".toLowerCase()) || text.toLowerCase().contentEquals("Other".toLowerCase())) {
			PassportType();
		}

		else if (text.toLowerCase().contentEquals("Institutional".toLowerCase()) || text.toLowerCase().contentEquals("Government/public".toLowerCase())
				|| text.toLowerCase().contentEquals("Non-profit/trade association".toLowerCase()) || text.toLowerCase().contentEquals("Licensing Board".toLowerCase())) {
			ProfessionalType(orgType);
		}

		Thread.sleep(4000);
		try {
			if (empSizetxt.isDisplayed()) {
				confirmNext.click();
			} else {
				System.out.println("Proration page is not available.");
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("Proration page is not available.");
		}


	}

	

	public void PassportType() throws InterruptedException {
		util.waitUntilElement(driver, subscriptiontxt);
		String subscriptiontxtValue = subscriptiontxt.getText();
		System.out.println("Text on subscription tab is: " + subscriptiontxtValue);
		assertTrue(subscriptiontxtValue.equalsIgnoreCase(data.testDataProvider().getProperty("subscriptiontypetxt")));
		Thread.sleep(1000);
		util.waitUntilElement(driver, passportNext);
		passportNext.click();
		util.waitUntilElement(driver, confirmationTxt);
		confirmationTxt.isDisplayed();
		util.waitUntilElement(driver, confirmationTxtLine1);
		String confirmationTxtLine1value = confirmationTxtLine1.getText();
		System.out.println("confirmation Text Line1 is:" + confirmationTxtLine1value);
		assertTrue(confirmationTxtLine1value.equalsIgnoreCase(data.testDataProvider().getProperty("confirmationTxt1")));
		util.waitUntilElement(driver, confirmationTxtLine2);
		String confirmationTxtLine2value = confirmationTxtLine2.getText();
		System.out.println("confirmation Text Line2 is:" + confirmationTxtLine2value);
		assertTrue(confirmationTxtLine2value.equalsIgnoreCase(data.testDataProvider().getProperty("confirmationTxt2")));
		util.waitUntilElement(driver, confirmNext);
		organizationName.isDisplayed();
		organazitionType.isDisplayed();
		priorProvider.isDisplayed();
		formerCESProviderNum.isDisplayed();
		workPhoneCountryCode.isDisplayed();
		workPhone.isDisplayed();
		Thread.sleep(3000);
		confirmNext.click();
	}

	public void ArchitectType(String isFirmSubscription, String aiaMemberNumber) throws InterruptedException {
        Thread.sleep(10000);
		util.waitUntilElement(driver, archFirmSubscription);
		util.selectDropDownByText(archFirmSubscription, isFirmSubscription);
		if (aiaMemberNumber == null || aiaMemberNumber.isEmpty() || aiaMemberNumber.trim().isEmpty()) {
			Thread.sleep(5000);
			subscriptionNext.click();
			util.waitUntilElement(driver, confirmNext);
			confirmNext.click();
		} else {

			archFirmAIAMemNum.sendKeys(aiaMemberNumber);
			subscriptionNext.click();
			util.waitUntilElement(driver, confirmNext);
			confirmNext.click();
		}
	}

	/*
	 * @param : orgDetails e.g. : "Government Agency",
	 * "Accredited Academic Institution", "Non-profit", "None"
	 */
	public void ProfessionalType(String orgType) throws InterruptedException {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		Utility.waitForWebElement(driver, profQualifyingQues, 20);
		if (orgType == null || orgType.isEmpty() || orgType.trim().isEmpty()) {
			util.selectDropDownByText(profQualifyingQues, "None");
		} else {
			util.selectDropDownByText(profQualifyingQues, orgType);
		}
		try {

			String PdfFile = System.getProperty("user.dir") + "/UploadFiles/FileAIA.pdf";
			util.waitUntilElement(driver, profUploadFile);
			// profUploadFile.sendKeys("C:\\AIA-code\\aia-fonteva-automation\\UploadFiles\\FileAIA.pdf");
			pdfUploadElement.sendKeys(System.getProperty("user.dir") + "/UploadFiles/FileAIA.pdf");
			/*
			 * profUploadFile.click(); Robot robot = new Robot(); StringSelection ss = new
			 * StringSelection(PdfFile);
			 * Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
			 * Thread.sleep(3000); robot.keyPress(KeyEvent.VK_CONTROL);
			 * robot.keyPress(KeyEvent.VK_V); robot.keyRelease(KeyEvent.VK_V);
			 * Thread.sleep(3000); robot.keyRelease(KeyEvent.VK_CONTROL);
			 * 
			 * robot.keyPress(KeyEvent.VK_ENTER); robot.keyRelease(KeyEvent.VK_ENTER);
			 */
		} catch (Exception exp) {
			exp.printStackTrace();
		}

		util.waitUntilElement(driver, pofessionalFileUplaodDoneButton);
		Thread.sleep(3000);
		js.executeScript("arguments[0].click();", pofessionalFileUplaodDoneButton);
		//pofessionalFileUplaodDoneButton.click();
		util.waitUntilElement(driver, pofessionalDeleteBtn);
		professionalNext.click();
		util.waitUntilElement(driver, confirmNext);
		confirmNext.click();
	}

	public void proratedSubscriptionNext() throws InterruptedException {
		Thread.sleep(3000);
		if (empSizetxt.isDisplayed()) {
			confirmNext.click();
		} else {
			System.out.println("Proration page is not available.");
		}
	}



	public void verifySubscriptionTab()
	{

		util.waitUntilElement(driver, tabTitleSubscription);
		assertTrue(tabTitleSubscription.isDisplayed());
		confirmNext.click();
		util.waitUntilElement(driver, confirmContinueText);
		assertTrue(confirmContinueText.isDisplayed());
		confirmNext.click();
	}
	

	/**
	 * Refresh Page
	 */
	public void refreshFunction() throws InterruptedException {
		driver.navigate().refresh();
		Thread.sleep(20000);
	}
	
	/**
	 * Validating user is on Subscription Tab
	 */
	public void verifySubscriptionTabText() {
		util.waitUntilElement(driver, subscriptionTabText);
		assertTrue(subscriptionTabText.isDisplayed());

	}

}
