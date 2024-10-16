package org.aia.pages.fonteva.chapterPortal;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class CallForDues {
	
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(Finance.class);

	
	public CallForDues(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Call for Dues']") WebElement callForDuesInfoTab;
	
	@FindBy(xpath = "//c-a-i-a-chapter-component-contact//div[@class='slds-m-vertical_medium']") WebElement paragraphInComponentContact;
	
	@FindBy(xpath = "//c-a-i-a-chapter-component-contact//div[@class='slds-grid slds-wrap']")WebElement contactDetailsInComponent;
	
	public void clickCallForDuesInfoTab() {
		Utility.waitForWebElement(driver, callForDuesInfoTab, 0);
		Assert.assertTrue(callForDuesInfoTab.isDisplayed());
		callForDuesInfoTab.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void getParagraphTextInComponent() {
		util.waitUntilElement(driver, paragraphInComponentContact);
		System.out.println(paragraphInComponentContact.getText()); 
	}
	
	public void getContactDetailsTextInComponent() {
		util.waitUntilElement(driver, contactDetailsInComponent);
	    String recordsData = contactDetailsInComponent.getText();
	    System.out.println(recordsData);
		log.info(recordsData.replace("\n", ""));
	}
	
	

}
