package org.aia.pages.fonteva.events;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import groovy.transform.Final;

public class PagesModule {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Events events;
	Actions act;
	static Logger log = Logger.getLogger(AgendaModule.class);
	public String salesOrder = "";
	public String aiaNumber = "";

	public PagesModule(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
		events = PageFactory.initElements(driver, Events.class);
	}

	// EventApi:EventBuilderPages
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderPages']")
	WebElement eventBuilderPages;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[contains(text(),'pages for your selected status')]")
	WebElement eventPagesText;

	@FindBy(xpath = "//button[text()='New Page']")
	WebElement newPageInPageModule;

	@FindBy(xpath = "//tr[contains(@data-pageid,'NewPage')]")
	WebElement validateAddNewRowInPageModule;

	@FindBy(xpath = "//tr[contains(@data-pageid,'NewPage')]//div[@data-name='navigationName']/input")
	WebElement navigationLabelName;

	@FindBy(xpath = "//tr[contains(@data-pageid,'NewPage')]//div[@data-name='pageName']/input")
	WebElement browserLabelName;

	@FindBy(xpath = "//tr[contains(@data-pageid,'NewPage')]//label[@data-name='isPublished']/input")
	WebElement publishedCheckboxInPageModule;

	@FindBy(xpath = "//tr[contains(@data-pageid,'NewPage')]//button[@title='More Options']")
	WebElement moreOptionsInActionsInPageModule;

	@FindBy(xpath = "//tr[contains(@data-pageid,'NewPage')]//a[text()='Manage Components']")
	WebElement manageComponentsOption;

	@FindBy(xpath = "//li[@class='componentAvailable' and text()='AIA Event SSO']")
	WebElement aiaEventSSOInRegisterPopUp;

	@FindBy(css = "button[data-name='addPageComponentButton']")
	WebElement addPageComponentButton;

	@FindBy(xpath = "//div[@id='selectedPageComponents']/div[1]/div/div/div[text()='AIA Event SSO']")
	WebElement validateAddOneRow;

	@FindBy(xpath = "//li[@class='componentAvailable' and text()='AIA Event Footer']")
	WebElement aiaEventFooterInRegisterPopUp;

	@FindBy(xpath = "//div[@id='selectedPageComponents']/div[2]/div/div/div[text()='Registration']")
	WebElement validateAddSecondRow;

	@FindBy(xpath = "//div[@id='selectedPageComponents']/div[3]/div/div/div[text()='AIA Event Footer']")
	WebElement validateAddThridRow;

	@FindBy(xpath = "//li[@class='componentAvailable' and text()='Registration']")
	WebElement aiaEventRegistrationInRegisterPopUp;

	@FindBy(css = "button[data-name='saveClosePageObj']")
	WebElement saveCloseButtonInPageModule;
	
	@FindBy(css = "tr[data-pageid*='NewPage'] button[title='down']") WebElement downButtonInRegPage;
	
	@FindBy(xpath = "//tr[@data-name='Register']//button[@title='More Options']") WebElement multipleOptionsInPages;
	
	@FindBy(xpath = "//tr[@data-name='Register']//ul[@class='dropdown__list']/li/a[text()='Delete']") WebElement deleteButtonInRegColum;
	
	@FindBy(xpath = "//div[@class='slds-modal__container']//p[text()='Delete Page']") WebElement headerDeleteInPagesModule;
	
	@FindBy(xpath = "//pre[contains(text(),'Are you sure you want to delete the')]") WebElement validateTextInDeletePageColumn;
	
	@FindBy(xpath = "//span[@class='deletePrompt']//button[@data-name='submitButton']") WebElement deleteButtonInDeletePopUp;

	public void eventPagesTab() {
		util.waitUntilElement(driver, eventBuilderPages);
		eventBuilderPages.click();
		log.info("Event Pages is clicked successfully");
		util.waitUntilElement(driver, eventPagesText);
		Assert.assertTrue(eventPagesText.isDisplayed());
		log.info("eventPagesText is displayed");
	}
	
	public void multiOptionsInPages(String option) throws InterruptedException {
		Thread.sleep(4000);
		multipleOptionsInPages.click();
		switch (option) {
		case "Manage Components":
			//TODO
			break;
		case "Access Permissions":
			//TODO
			break;
		case "Delete":
			deleteButtonInRegColum.click();
			log.info(headerDeleteInPagesModule.getText()); 
			log.info(validateTextInDeletePageColumn.getText());
			if(option.equals("Delete")) {
				deleteButtonInDeletePopUp.click();
			}else {
				//TODO
			}
			break;
		}
	}

	public void clickNewPage() {
		Utility.waitForWebElement(driver, newPageInPageModule, 10);
		newPageInPageModule.click();
		Assert.assertTrue(validateAddNewRowInPageModule.isDisplayed());
	}

	public void enterNavigationLabelName() {
		Utility.waitForWebElement(driver, navigationLabelName, 0);
		util.enterText(driver, navigationLabelName, "Register");
	}

	public void enterBrowserLabelName() {
		Utility.waitForWebElement(driver, browserLabelName, 0);
		util.enterText(driver, browserLabelName, "Register");
	}

	public void SelectCheckboxForPublished() {
		Utility.waitForWebElement(driver, publishedCheckboxInPageModule, 0);
		if (publishedCheckboxInPageModule.isSelected()) {
			System.out.println("Active checkobox is enabled");
		} else {
			util.clickUsingJS(driver, publishedCheckboxInPageModule);
			log.info("Active CheckBox is clicked");
			Assert.assertTrue(publishedCheckboxInPageModule.isSelected());
		}
	}

	public void clickOnActions() {
		Utility.waitForWebElement(driver, moreOptionsInActionsInPageModule, 0);
		moreOptionsInActionsInPageModule.click();
	}

	public void clickManageComopnents() {
		Utility.waitForWebElement(driver, manageComponentsOption, 0);
		manageComponentsOption.click();
	}

	public void selectEventSSO() {
		Utility.waitForWebElement(driver, aiaEventSSOInRegisterPopUp, 0);
		aiaEventSSOInRegisterPopUp.click();
		Utility.waitForWebElement(driver, addPageComponentButton, 0);
		addPageComponentButton.click();
	}

	public void validate_AddedEventOne() {
		Utility.waitForWebElement(driver, validateAddOneRow, 0);
		Assert.assertTrue(validateAddOneRow.isDisplayed());
	}

	public void selectEventRegistration() {
		Utility.waitForWebElement(driver, aiaEventRegistrationInRegisterPopUp, 0);
		aiaEventRegistrationInRegisterPopUp.click();
		Utility.waitForWebElement(driver, addPageComponentButton, 0);
		addPageComponentButton.click();
	}

	public void validate_AddedEventTwo() {
		Utility.waitForWebElement(driver, validateAddSecondRow, 0);
		Assert.assertTrue(validateAddSecondRow.isDisplayed());
	}

	public void selectEventFooter() {
		Utility.waitForWebElement(driver, aiaEventFooterInRegisterPopUp, 0);
		aiaEventFooterInRegisterPopUp.click();
		Utility.waitForWebElement(driver, addPageComponentButton, 0);
		addPageComponentButton.click();
	}

	public void validate_AddedEventThird() {
		Utility.waitForWebElement(driver, validateAddThridRow, 0);
		Assert.assertTrue(validateAddThridRow.isDisplayed());
	}

	public void clickSaveCloseButtonInPageModule() throws InterruptedException {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, saveCloseButtonInPageModule, 0);
		saveCloseButtonInPageModule.click();
	}
	
	public void downButtonInRegColumnInPage() {
		for (int i = 0; i <=5; i++) {
			downButtonInRegPage.click();
		}
		

	}
	
	

}
