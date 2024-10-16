package org.aia.pages.fonteva.abi;

import org.aia.pages.api.ces.SubscriptionPlanPrice;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class FontevaLoginPage {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;

	public FontevaLoginPage(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	@FindBy(xpath = "//button[@aria-label='Search' and @type='button']")
	WebElement search;
	
	@FindBy(xpath = "//div[@class='forceSearchAssistantDialog']//input[@type='search']")
	WebElement searchInput;
	
	@FindBy(xpath = "//li[@data-aura-class='forceSearchScopeItem']//a[@title='Sales Orders']")
	WebElement salesOrders;
	
	@FindBy(xpath = "//span[@class='slds-grid slds-grid--align-spread']/a")
	WebElement salesOrderID;
	
	@FindBy(xpath = "//li[@data-aura-class='forceSearchScopeItem']//a[@title='Memberships']")
	WebElement memberships;
	
	@FindBy(xpath = "//tbody//th//a")
	WebElement subscriptionID;
	
	@FindBy(xpath = "//button[text()='Renew']")
	WebElement renewBtn;
	
	@FindBy(xpath = "//button[text()='Ready For Payment']")
	WebElement ready4Pymt;
	
	@FindBy(xpath = "//lightning-button-menu[contains(@data-target-reveals,'OrderApi__Sales_Order__c')]")
	WebElement dropDown;
	
	@FindBy(xpath = "//lightning-menu-item[contains(@data-target-selection-name,'Send_Public_Proforma_Invoice')]")
	WebElement sendPPInvoice;
	
	@FindBy(xpath = "//header[text()='Business Information']/following::button[1]")
	WebElement businessGroup;
	
	@FindBy(xpath = "//header[text()='Proforma Invoice Message']/following::input[1]")
	WebElement sendTo;
	
	@FindBy(xpath = "//label[text()='Subject']//following::input[@class='slds-input'][1]")
	WebElement subject;
	
	@FindBy(xpath = "//lightning-base-combobox//button[@aria-label='Select a site, Select a Community Site...']")
	WebElement selectACommunitySite;
	
	@FindBy(xpath = "//th[@data-label='SHOPPING CART']")
	WebElement shoppingCart;
	
	@FindBy(xpath = "//td[@data-label='QTY']")
	WebElement qty;
	
	@FindBy(xpath = "//td[@data-label='PRICE']")
	WebElement price;
	
	@FindBy(xpath = "//label[text()='Discount code']/following::input")
	WebElement discountCode;
	
	@FindBy(xpath = "//button[text()='Send Email']")
	WebElement sendEmailBtn;
	
	public void renewABI(String user) throws InterruptedException {
		util.waitUntilElement(driver, renewBtn);
		util.clickUsingJS(driver, renewBtn);
		Assert.assertEquals(false, renewBtn.isDisplayed());
		Assert.assertTrue(driver.getCurrentUrl().contains("OrderApi__Sales_Order__c"));
		Assert.assertEquals(true, ready4Pymt.isDisplayed());
		util.waitUntilElement(driver, ready4Pymt);
		ready4Pymt.click();
		util.waitUntilElement(driver, dropDown);
		dropDown.click();
		util.waitForJavascript(driver, 6000, 1000);
		util.waitUntilElement(driver, sendPPInvoice);
		sendPPInvoice.click();
		util.waitForJavascript(driver, 6000, 1000);
		Assert.assertEquals(false, sendPPInvoice.isDisplayed());
		util.waitUntilElement(driver, subject);
		subject.click();
		subject.sendKeys("Payment_For_Renewal");	
		util.waitForJavascript(driver, 6000, 1000);		
		util.waitUntilElement(driver, selectACommunitySite);
		selectACommunitySite.click();
		selectACommunitySite.sendKeys(Keys.ENTER);
		Assert.assertTrue(businessGroup.getText().contains("eCommerce"));
		Assert.assertTrue(subject.getText().contains("Payment_For_Renewal"));
		Assert.assertTrue(sendTo.getText().contains("auto_"));
		Assert.assertTrue(selectACommunitySite.getText().contains("Ecommerce"));
		Assert.assertTrue(shoppingCart.isDisplayed());
		Assert.assertTrue(qty.isDisplayed());
		Assert.assertTrue(price.isDisplayed());
		Assert.assertTrue(discountCode.isDisplayed());
		util.waitUntilElement(driver, sendEmailBtn);
		util.clickUsingJS(driver, sendEmailBtn);
	}
	
	public void viewSalesOrder(String user, String Id) {
		util.waitForJavascript(driver, 6000, 1000);
		util.waitUntilElement(driver, search);
		util.clickUsingJS(driver, search);
		util.waitUntilElement(driver, searchInput);
		util.clickUsingJS(driver, searchInput);
		searchInput.sendKeys(user+Keys.ENTER);
		util.waitUntilElement(driver, salesOrders);
		salesOrders.click();
		util.waitUntilElement(driver, salesOrderID);
		salesOrderID.click();				
	}
	
	public void viewSubscriptionOrder(String user) {
		util.waitForJavascript(driver, 6000, 1000);
		util.waitUntilElement(driver, search);
		search.click();
		util.waitUntilElement(driver, searchInput);
		searchInput.click();
		searchInput.sendKeys(user+Keys.ENTER);
		util.waitUntilElement(driver, memberships);
		memberships.click();
		util.waitUntilElement(driver, subscriptionID);
		subscriptionID.click();				
	}
}
