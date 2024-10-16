package org.aia.pages.fonteva.chapterPortal;

import java.util.ArrayList;

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

public class GlobalSearch {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(FullMemberRoster.class);
	
	public GlobalSearch(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(css = "button[class*='_neutral search-button slds-truncate']")
	WebElement globSearchInContact;

	@FindBy(xpath = "//label[contains(text(),'Search')]/following-sibling::div/input[contains(@aria-controls,'suggestionsList')]")
	WebElement globSearchInputInContact;
	
	@FindBy(xpath = "//li/a[@title='Contacts']")
	WebElement contactsInGlobalSearch;
	
	@FindBy(xpath  = "//button[contains(@class,'search-inputSearchButton search-inputSearchButton')]")
	WebElement globSearch;

	@FindBy(xpath = "//button[contains(@class,'search-inputSearchButton search-inputSearchButton')]/following::input")
	WebElement globSearchInput;
	
	@FindBy(xpath = "(//table[contains(@class,'uiVirtualDataGrid--default uiVirtualDataGrid')]/tbody)[2]") WebElement contactData;
	
	@FindBy(xpath = " //li/a[@title='Community Groups']") WebElement comunityGroupInSearchResults;
	
	@FindBy(xpath = " //li/a[@title='Contacts']") WebElement contactsInSearchResults;
	
	@FindBy(xpath = " //li/a[@title='Accounts']") WebElement accountsInSearchResults;
	
	public void globalSearchInContact(String contactName) throws Throwable {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, globSearchInContact, 30);
		globSearchInContact.click();
		globSearchInputInContact.sendKeys(contactName);
		Thread.sleep(5000);
		globSearchInputInContact.sendKeys(Keys.ENTER);
	}
	
	public void clickContactsInGlobalSearch(String contactName) throws Throwable {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, contactsInGlobalSearch, 10);
		contactsInGlobalSearch.click();
		Thread.sleep(6000);
		WebElement contactElement = driver.findElement(By.xpath("//table[@data-aura-class='uiVirtualDataTable'][not(contains(@aria-label,'Recently Viewed'))]/tbody/tr/th/span/a[@title='"+contactName+"']"));
		util.waitUntilElement(driver, contactElement);
		contactElement.click();
	}
	
	public void globalSearch(String contactName) throws Throwable {
		Utility.waitForWebElement(driver, globSearch, 30);
		globSearch.click();
		globSearchInput.sendKeys(contactName);
		Thread.sleep(4000);
		globSearchInput.sendKeys(Keys.ENTER);
	}
	
	public ArrayList<String> getContactListInGlobalSearch() throws Throwable {
		Thread.sleep(7000);
		ArrayList<String> contactsList =new ArrayList<String>();
		System.out.println(contactData.getText());
		contactsList.add(0, contactData.getText());
		Assert.assertTrue(contactData.isDisplayed());
		return contactsList;
	}
	
	public void validateSearchRefineItems() throws Throwable {
		Thread.sleep(6000);
		Assert.assertTrue(comunityGroupInSearchResults.isDisplayed());
		Assert.assertTrue(contactsInSearchResults.isDisplayed());
		Assert.assertTrue(accountsInSearchResults.isDisplayed());
	}


}
