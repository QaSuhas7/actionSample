package org.aia.pages.memberPortal;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.aia.pages.fonteva.memberPortal.AccountAcessForContact;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import groovy.transform.Final;
import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;

public class MemberPortal {
  
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(AccountAcessForContact.class);

	public MemberPortal(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//a[contains(@href, '/lightning/r/Account/')]")
	WebElement accountsLink;
	
	@FindBy(xpath = "//records-highlights-details-item//records-hoverable-link//a[contains(@href, 'lightning/r/Account')]")
	WebElement contactsLink;
	
	@FindBy(xpath = "//records-highlights-details-item//p[@title = 'Primary Contact']/following-sibling::*//a")
	WebElement primaryContactsLink;
	
	@FindBy(xpath = "//span[text()='Enable Customer User']/parent::a")
	WebElement enableCustomerUserOpt;
	
	@FindBy(xpath = "//lightning-button-menu[contains(@data-target-reveals,'Disable_Auto_Renew')]//button")
	WebElement moreActionBtn;

	@FindBy(xpath = "//span[text()='Log in to Experience as User']//ancestor::a")
	WebElement loginAsExpUserOpt;
	
	@FindBy(xpath = "//span//select[@id='Profile']")
	WebElement userProfiledrpdwn;
	
	@FindBy(xpath = "//td[@id='bottomButtonRow']//input[@value= ' Save ' ]")
	WebElement userProfileSave;
	
	@FindBy(xpath = "//a[@title='Manage Membership']")
	WebElement membership;
	
	@FindBy(xpath = "//div//h2[text()='Membership & Chapter Assignment']")
	WebElement chapterAssignment;
	
	@FindBy(xpath = "//h2//span[text()='Membership Transfer Request']")
	WebElement transferRequest;
	
	@FindBy(xpath = "//p[text()='AIA Number']")
	WebElement aiaNumberTxt;
	
	@FindBy(xpath = "//p[text()='Membership Type']")
	WebElement membershipTypeTxt;
	
	@FindBy(xpath = "//p[text()=' Local Assignment']")
	WebElement localAssignmetTxt;
	
	@FindBy(css = "//p[contains(text(), 'State Assignment')]")
	WebElement stateAssignmetTxt;

	@FindBy(xpath = "//p[contains(text(), ' Section Assignment')]")
	WebElement sectionAssignmetTxt;

	@FindBy(xpath = "//p[contains(text(), 'Status')]")
	WebElement statusTxt;

	@FindBy(xpath = "//a[@title='Interest Areas']")
	WebElement interestArea;
	
	@FindAll(value = {@FindBy(xpath = "//input[contains(@id,'checkbox')]")}) List<WebElement> interestAreasCheckBoxes;
	
	@FindBy(xpath = "//span[text()='Academy of Architecture for Health (AAH)']")
	WebElement aahText;
	
	@FindBy(xpath = "//span[text()='Academy of Architecture for Justice (AAJ)']")
	WebElement aajText;
	
	@FindBy(xpath = "//span[text()='Building Performance (BPKC)']")
	WebElement bpkcText;
	
	@FindBy(xpath = "//span[text()='Committee on Architecture for Education (CAE)']")
	WebElement caeTxt;
	
	@FindBy(xpath = "//span[text()='Committee on Design (COD)']")
	WebElement codTxt;
	
	@FindBy(xpath = "//span[text()='Committee on the Environment (COTE®)']")
	WebElement coteTxt;
	
	@FindBy(xpath = "//span[text()='Construction Contract Administration (CCA)']")
	WebElement ccaTxt;
	
	@FindBy(xpath = "//span[text()='Corporate Architects and Facility Management (CAFM)']")
	WebElement cafmTxt;
	
	@FindBy(xpath = "//span[text()='Custom Residential Architects Network (CRAN®)']")
	WebElement cranTxt;
	
	@FindBy(xpath = "//span[text()='Design for Aging (DFA)']")
	WebElement dfaTxt;
	
	@FindBy(xpath = "//span[text()='Historic Resources Committee (HRC)']")
	WebElement hrcTxt;
	
	@FindBy(xpath = "//span[text()='Housing and Community Development (HCD)']")
	WebElement hcdTxt;
	
	@FindBy(xpath = "//span[text()='Interfaith Design (ID)']")
	WebElement idTxt;
	
	@FindBy(xpath = "//span[text()='Interior Architecture Knowledge Community (IAKC)']")
	WebElement iakcTxt;
	
	@FindBy(xpath = "//span[text()='Practice Management Knowledge Community (PMKC)']")
	WebElement pmkcTxt;
	
	@FindBy(xpath = "//span[text()='Project Delivery Knowledge Community (PD)']")
	WebElement pdTxt;
	
	@FindBy(xpath = "//span[text()='Regional and Urban Design (RUDC)']")
	WebElement rudcTxt;
	
	@FindBy(xpath = "//span[text()='Retail and Entertainment (REKC)']")
	WebElement rekcTxt;
	
	@FindBy(xpath = "//span[text()='Small Project Design (SPD)']")
	WebElement spdTxt;
	
	@FindBy(xpath = "//span[text()='Technology in Architectural Practice (TAP)']")
	WebElement tapTxt;
	
	@FindBy(xpath = "//a[@title='Demographics']")
	WebElement demographics;
	
	@FindAll(value = @FindBy(xpath = "//p//lightning-formatted-text[@class='fieldBox_Value']")) List<WebElement> demographicsListData;
	
	@FindBy(xpath = "//p[text()='Birthdate']")
	WebElement birthdate;
	
	@FindBy(xpath = "//p[text()='Gender Identity']")
	WebElement genderIdentity;
	
	@FindBy(xpath = "//h2[text()='Demographics']")
	WebElement demographicsTxt;
	
	@FindBy(xpath = "//p[text()='Gender']")
	WebElement gender;
	
	@FindBy(xpath = "//p[text()='LGBTQIA+']")
	WebElement lgbtqTxt;
	
	@FindBy(xpath = "//p[text()='Preferred Pronouns']")
	WebElement preferredPronounsTxt;
	
	@FindBy(xpath = "//p[text()='Race and ethnicity']")
	WebElement raceEthnicityTxt;
	
	@FindBy(xpath = "//p[text()='Primary race/ethnicity']")
	WebElement primaryEthnicityTxt;
	
	@FindBy(xpath = "//p[text()='Secondary race/ethnicity']")
	WebElement secondaryEthnicityTxt;
	
	@FindBy(xpath = "//p[text()='Diverse abilities']")
	WebElement diverseAbilitiesTxt;
	
	@FindBy(xpath = "//p[text()='Diverse Ability']")
	WebElement diverseAbilityTxt;
	
	@FindBy(xpath = "//p[text()='Ability Notes']")
	WebElement abilityNotesTxt;
	
	@FindBy(xpath = "//a[@title='Contact Information']")
	WebElement contactInformation;
	
    @FindAll(value = {@FindBy(xpath = "//p//lightning-formatted-text[@class='fieldBox_Value']")})	List<WebElement> contactInfoList;
	
    @FindAll(value = {@FindBy(xpath = "//p//lightning-formatted-text[@class='fieldBox_Value']")})	List<WebElement> memberShipInfoList;
    
	@FindBy(xpath = "//p//lightning-formatted-text[contains(text(),'autoln')]")
	WebElement lastNameCI;
	
	@FindBy(xpath = "(//lightning-formatted-address//div[text()='United States'])[1]")
	WebElement homeAddressCI;
	
	@FindBy(xpath = "(//input[@placeholder='you@example.com'])[1]")
	WebElement personalEmail;
	
	@FindBy(xpath = "(//p//lightning-formatted-text[contains(text(),'40')]")
	WebElement aiaNumber;
	
	@FindBy(xpath = "//a[@title='Communication Preferences']")
	WebElement communicationPreferences;
	
	@FindAll(value= {@FindBy(xpath = "//div//p[text()='Do Not Contact by Phone']//following::input")}) List<WebElement> communicationsCheckBoxes;
	
	@FindBy(xpath = "(//p[text()='Do Not Contact by Phone']")
	WebElement contactByPhoneTxt;
	
	@FindBy(xpath = "(///p[text()='Do Not Contact by Email']")
	WebElement contactByEmailTxt;
	
	@FindBy(xpath = "(///p[text()='Do Not Contact by Post']")
	WebElement contactByPostTxt;
	
	@FindBy(xpath = "//div[@class='mainCard']//h3[contains(text(),'My Auto-renewal')]") 
	WebElement autorenewal;
	
	@FindBy(xpath = "//div[@class='mainCard']//h3[contains(text(),'Your Auto-renewal Payment Method')]")
	WebElement autorenwalPayment;
	
	@FindBy(xpath = "//div[@class='mainCard']//h3[contains(text(),'Your Auto-renewal Payment Method')]/following::div[@class='mainCard_fieldView']")
	WebElement autoRenwalText;
	
	 public void navigateToMemberPortal(String profile) throws InterruptedException {
		   JavascriptExecutor js = (JavascriptExecutor) driver;
		   util.waitUntilElement(driver, accountsLink);
			js.executeScript("arguments[0].click();", accountsLink);
			Thread.sleep(20000);
			util.waitUntilElement(driver, primaryContactsLink);
			js.executeScript("arguments[0].click();", primaryContactsLink);
			//primaryContactsLink.click();
			Thread.sleep(50000);
			util.waitUntilElement(driver, contactsLink);
			js.executeScript("arguments[0].click();", contactsLink);
			//contactsLink.click();
			Thread.sleep(20000);
			util.waitUntilElement(driver, primaryContactsLink);
			js.executeScript("arguments[0].click();", primaryContactsLink);
			Thread.sleep(50000);
			util.waitUntilElement(driver, moreActionBtn);
			moreActionBtn.click();
			util.waitUntilElement(driver, enableCustomerUserOpt);
			js.executeScript("arguments[0].click();", enableCustomerUserOpt);
			Thread.sleep(50000);
			driver.switchTo().frame(0);
			WebElement userProfiledrpdwn = driver.findElement(By.id("Profile"));
			Select dropdown = new Select(userProfiledrpdwn);
	        dropdown.selectByVisibleText(profile);
	        util.waitUntilElement(driver, userProfileSave);
	        userProfileSave.click();
	        driver.navigate().back();
	        driver.navigate().back();
	   }
	 
	public ArrayList<String> validateContactInformationpage( ) throws InterruptedException {
		Thread.sleep(15000);
		ArrayList<String>contactInfo = new ArrayList<String>();
		util.waitUntilElement(driver, contactInformation);
		contactInformation.click();
		Thread.sleep(10000);
		System.out.println(contactInfoList.size());
		for (int i = 0; i < contactInfoList.size(); i++) {
			Thread.sleep(2000);
		String contactDetails =	contactInfoList.get(i).getText();
		if(contactDetails !="") {
			contactInfo.add(contactDetails);
		}else {
			System.out.println("null");
		}
	}
		System.out.println(contactInfo);
		return contactInfo;
	}
	

	
	public ArrayList<String> validateMembershipFields() throws InterruptedException {
		Thread.sleep(15000);
		util.waitUntilElement(driver, membership);
		membership.click();
		Thread.sleep(15000);
		System.out.println(memberShipInfoList.size());
		ArrayList<String> memberShipInfo = new ArrayList<String>();
		for (int i = 0; i < memberShipInfoList.size(); i++) {
			Thread.sleep(2000);
			String memberDetails =	memberShipInfoList.get(i).getText();
			if(memberDetails !="") {
				memberShipInfo.add(memberDetails);
			}else {
				System.out.println("null");
			}
		}
			System.out.println(memberShipInfo);
			return memberShipInfo;
	}
	
	
	public void validateInterestPageFields() throws Throwable {
		Thread.sleep(15000);
		util.waitUntilElement(driver, interestArea);
		interestArea.click();
		Thread.sleep(5000);
		System.out.println(interestAreasCheckBoxes.size());
		for (int i = 0; i < interestAreasCheckBoxes.size(); i++) {
			if(interestAreasCheckBoxes.get(i).isEnabled()) {
				Assert.assertFalse(interestAreasCheckBoxes.get(i).isSelected());
			}else {
				System.out.println("Checkboxes is disabled");
			}
			
		}
	}
	
	public ArrayList<String> verifyDemographicsFields() throws Throwable {
		Thread.sleep(15000);
		util.waitUntilElement(driver, demographics);
		demographics.click();
		Thread.sleep(5000);
		ArrayList<String> demographicsData = new ArrayList<String>();
		System.out.println(demographicsListData.size());
		for (int i = 0; i < demographicsListData.size(); i++) {
			Thread.sleep(4000);
			String data = demographicsListData.get(i).getText();
			if(data=="") {
				data = null;
			demographicsData.add(data);
			}
		}
		System.out.println(demographicsData);
		return demographicsData;
		
	}
	
	public void verifycommunicationPreferences() throws Throwable {
		Thread.sleep(15000);
		util.waitUntilElement(driver, communicationPreferences);
		communicationPreferences.click();
		Thread.sleep(5000);
		System.out.println(communicationsCheckBoxes.size());
		for (int i = 0; i < communicationsCheckBoxes.size(); i++) {
			if(!(communicationsCheckBoxes.get(i).isEnabled())) {
				System.out.println("Checkbox is disabled"+""+i);
			}else {
				System.out.println("Checkbox is enabled"+""+i);
			}
		}
		
		
		}	
	
	public void validateAutorenwalSection() {
		util.scrollingBottomOfPage(driver);
		Assert.assertTrue(autorenewal.isDisplayed());
		Assert.assertTrue(autorenwalPayment.isDisplayed());
		String textrenwal = autoRenwalText.getText();
		if(textrenwal.contains("your auto-renewal payment method here.")) {
			System.out.println("Auto renewal payment is displayed");
		}
	}

	
	

}
