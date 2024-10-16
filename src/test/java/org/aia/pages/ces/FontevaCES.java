package org.aia.pages.ces;

import static org.testng.Assert.assertTrue;


import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class FontevaCES {

	WebDriver driver;

	public FontevaCES(WebDriver Idriver) {
		this.driver = Idriver;
	}

	Utility util = new Utility(driver, 10);


	@FindBy(xpath = "//input[@id='username']")
	WebElement userName;

	@FindBy(xpath = "//input[@id='password']")
	WebElement password;

	@FindBy(xpath = "//input[@id='Login']")
	WebElement loginBtn;

//	@FindBy(xpath="//*[@title='Contact']/span") WebElement contacts;
	// Incorrect Spelling -- corrected below

	@FindBy(xpath = "//*[@title='Contacts']/span")
	WebElement contacts;

	@FindBy(xpath = "//span[text()='App Launcher']/parent::div//parent::button")
	WebElement appLauncherIcn;

	@FindBy(xpath = "//label[text()='Search apps and items...']/parent::div/div/input")
	WebElement appSearchtxtbx;

	@FindBy(xpath = "//b[text()='Provider Application']")
	WebElement searchedAppPA;

	@FindBy(xpath = "//table[@aria-label='Recently Viewed']/tbody/tr/th")
	WebElement tableProviderApp;

	@FindBy(xpath = "//button[@title='Select a List View'] | //button[contains(@title,'Select a List View')]")
	WebElement selectList;

	@FindBy(xpath = "//lightning-base-combobox-item[@data-value='All']")
	WebElement allBtn;

	@FindBy(xpath = "//table[@aria-label='All']/tbody/tr")
	WebElement tableAllProviders;

//	@FindBy(xpath = "//span[text()='Application Status']/parent::div/parent::div//button")
//	WebElement editAppStatusIon;
	
	@FindBy(xpath = "//button[@title='Edit Application Status']")
	WebElement editAppStatusIon;

	@FindBy(xpath = "//button[contains(@aria-label, 'Application Status')]")
	WebElement appStatusBtnDrpdwn;
	

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;


	////////////// Membership

	@FindBy(xpath = "//a[@title='Contacts']/parent::one-app-nav-bar-item-root")
	WebElement contactsDiv;

	@FindBy(xpath = "//div[@class='uiVirtualDataTable indicator']")
	WebElement tableDiv;

	// @FindBy(xpath="//a/slot/span[contains(text(),'Memberships')]") WebElement
	// memberShip;
	@FindBy(xpath = "//a/slot/span[contains(text(),'Memberships')]//ancestor::a")
	WebElement memberShip;

	@FindBy(xpath = "//a/span[@title='Name']")
	WebElement tableheaderName;

	@FindBy(xpath = "//h2//span[@title='Terms']")
	WebElement Terms;

	// @FindBy(xpath="//table[@aria-label='Terms']/tbody/tr/th//span/a |
	// //table[@aria-label='Terms']/tbody/tr/th//span//ancestor::a") WebElement
	// termId;
	@FindBy(xpath = "//table[@aria-label='Terms']/tbody/tr/th//span//ancestor::a")
	WebElement termId;

	@FindBy(xpath = "//table[@aria-label='Memberships']/tbody/tr/th//div")
	WebElement tableSubscriptionId;

	@FindBy(xpath = "//input[@name='OrderApi__Term_End_Date__c']")
	WebElement inputTermEndDate;

	@FindBy(xpath = "//input[@name='OrderApi__Grace_Period_End_Date__c']")
	WebElement inputTermGraceDate;

	@FindBy(xpath = "//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody/tr/th")
	WebElement Name;

	// @FindBy(xpath="//span[text()='Term End
	// Date']/parent::div/following-sibling::div//button") WebElement editBtn;

	@FindBy(xpath = "//button[@title='Edit Term End Date']/span")
	WebElement editBtn;

	// @FindBy(xpath="//a[contains(text(),'Show')]") WebElement showallBtn;

	@FindBy(xpath = "//a[contains(text(),'Show All (1')]")
	WebElement showallBtn;

	// @FindBy(xpath="//lst-related-list-quick-links-grid//div//div[@class='slds-card__body
	// slds-card__body--inner']//div[@class='rlql-toggle
	// slds-text-align_center']//a[contains(text(),'Show All')]") WebElement
	// showallBtn;

	@FindBy(xpath = "//h1/span[text()='Contacts']/parent::h1/parent::div/parent::div//button")
	WebElement contactallBtn;

	@FindBy(xpath = "//li[contains(@class,'forceVirtualAutocompleteMenuOption')]//span[text()='All Contacts'][1]")
	WebElement contactallLink;
	
	@FindBy(xpath = "//input[@aria-label='Search All Contacts list view.']")
	WebElement contactSearchInputBox;
	
	String contactInTable = "//table[@aria-label='All Contacts']//td[3]//span/a[@title='%s']";

	@FindBy(xpath = "//div[text()='Contact']")
	WebElement contactTitle;

	@FindBy(xpath = "//input[@placeholder='Search this list...']")
	WebElement searchBox;

	@FindBy(xpath = "//span[contains(text(),'0 items � Sorted by Provider Application Number')]")
	WebElement noItemHeading;

	@FindBy(xpath = "//p[text()='Account Name']//parent::div//div//a")
	WebElement accountName;

	@FindBy(xpath = "//span[text()='AutomationOrg']")
	WebElement accountNameLink;

	@FindBy(xpath = "//span[text()='Annual Revenue']")
	WebElement annualRevenueTxt;

	@FindBy(xpath = "//span[text()='Where do you offer courses']")
	WebElement offerCoursesTxt;

	@FindBy(xpath = "//span[text()='Organization Tax Id']")
	WebElement orgTaxIDTxt;
	
	@FindBy(xpath = "//a/slot/span[contains(text(),'Sales Orders')]//ancestor::a")
	WebElement salesOrder;
	
	@FindBy(xpath = "//div//span//slot//lightning-formatted-text[contains(text(), 'CES Provider Join')]")
	WebElement salesOrderOriginTxt;
	
	@FindBy(xpath = "//table[@aria-label='Sales Orders']/tbody//a//span[contains(text(), '000')]")
	WebElement salesOrderID;
	
	

	

	String startLocator = "//div[@class='uiVirtualDataTable indicator']/following-sibling::table/tbody//a[text()='";
	String endLocator = "']";
	String appName = "Provider Application";

	public void changeProviderApplicationStatus(String fullName, String providerID, String providerStatus)
			throws InterruptedException {
		/*
		 * util.waitUntilElement(driver, userName);
		 * userName.sendKeys("sgopisetty@innominds.com.aia.testing");
		 * password.sendKeys("Harshi@437"); loginBtn.click();
		 */
		util.waitUntilElement(driver, appLauncherIcn);
		Thread.sleep(10000);
		appLauncherIcn.click();
		util.waitUntilElement(driver, appSearchtxtbx);
		util.enterText(driver, appSearchtxtbx, appName);
		util.waitUntilElement(driver, searchedAppPA);
		WebElement provAppElement = Utility.waitForWebElement(driver, "//b[text()='" + appName + "']", 10);
		provAppElement.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, tableProviderApp);
		util.waitUntilElement(driver, selectList);
		selectList.click();
		util.waitUntilElement(driver, allBtn);
		allBtn.click();
		Thread.sleep(10000);
		Thread.sleep(5000);
		util.waitUntilElement(driver, searchBox);
		searchBox.sendKeys(providerID);
		Thread.sleep(3000);
		searchBox.sendKeys(Keys.ENTER);
		Thread.sleep(30000);
		try {
			driver.findElement(By.xpath("//table[@aria-label='All']//tbody//a[@title='"+providerID+"']")).click();
		}catch (Exception e) {
			System.out.println("No items display");
		boolean ponumber = false;
		if (noItemHeading.isDisplayed()) {
			int count = 8;
			for (int i = 0; i < count && ponumber == false; i++) {
				Thread.sleep(10000);
				searchBox.clear();
				Thread.sleep(4000);
				searchBox.sendKeys(Keys.ENTER);
				Thread.sleep(4000);
				driver.findElement(
						By.cssSelector("a[class='toggle slds-th__action slds-text-link--reset '] span[title='Provider Application Number']"))
						.click();
				searchBox.sendKeys(providerID);
				Thread.sleep(3000);
				searchBox.sendKeys(Keys.ENTER);
				Thread.sleep(3000);
				try {
					WebElement providerElemnt = driver.findElement(By.xpath("//table[@aria-label='All']//tbody//a[@title='"+providerID+"']"));
					if (providerElemnt.isDisplayed()) {
						System.out.println("provider id is displayed");
						ponumber = true;
						if (ponumber == true) {
							util.clickUsingJS(driver, provAppElement);
							System.out.println("provider id is clicked successfully");
						} else {
							System.out.println("ponumber is not clicked");
						}

					} else {
						System.out.println("ponumber is not displayed");
					}
				} catch (Exception s) {
					System.out.println(s);
				}

			}
		}
		}

//		WebElement provAppIDElement = Utility.waitForWebElement(driver,
//				"//table[@aria-label='All']/tbody//a[@title='" + providerID + "']", 10);
//		provAppIDElement.click();
		Thread.sleep(2000);
		Actions act = new Actions(driver);
		act.scrollToElement(editAppStatusIon);
		util.waitUntilElement(driver, editAppStatusIon);
		editAppStatusIon.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, appStatusBtnDrpdwn);
		util.clickUsingJS(driver, appStatusBtnDrpdwn);
//		appStatusBtnDrpdwn.click();
		Thread.sleep(5000); // added wait
		WebElement provStatusElement = Utility.waitForWebElement(driver, "//span[@title='" + providerStatus + "']", 10);
        util.clickUsingJS(driver, provStatusElement);
//	    provStatusElement.click();
		saveBtn.click();
		Thread.sleep(1000);
		act.sendKeys(Keys.F5);
		Thread.sleep(5000);
	}

	public void changeTermDates(String fullName) throws InterruptedException {
		Actions actions = new Actions(driver);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, contacts);
		Thread.sleep(5000);
		contactsDiv.click();
		// driver.navigate().refresh();
		util.waitUntilElement(driver, tableheaderName);
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactallBtn);
		contactallBtn.click();
		util.waitUntilElement(driver, contactallLink);
		contactallLink.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, contactSearchInputBox);
		contactSearchInputBox.sendKeys(fullName);
		//Thread.sleep(20000);
		contactSearchInputBox.click();
		Thread.sleep(5000);
		contactSearchInputBox.sendKeys(Keys.ENTER);
		Thread.sleep(20000);
		boolean accounNameStatus = false;
		if(noItemHeading.isDisplayed()) {
			 int count =8;
			 for (int i = 0; i < count && accounNameStatus == false; i++) {
				 searchBox.clear();
				 Thread.sleep(10000);
				 driver.findElement(By.cssSelector("a[class='toggle slds-th__action slds-text-link--reset ']:nth-child(1)")).click();
				 Thread.sleep(4000);
				 searchBox.sendKeys(fullName);
				 Thread.sleep(2000);
				 searchBox.sendKeys(Keys.ENTER);
				 Thread.sleep(10000);
				 try{
				 if(driver.findElement(By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"+fullName+"']")).isDisplayed()) {
					 accounNameStatus = true;
					 if(accounNameStatus==true){
						 driver.findElement(By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"+fullName+"']")).click(); 
						 util.waitForJavascript(driver, 20000, 2000);
					 }else{
						 System.out.println("accountName is not clicked");
					 }
				 }else{
					 System.out.println("accountName is not displayed");
				 }
			  }
				 catch (Exception e) {
						System.out.println(e);
				     }
	      }
		}else {
			driver.findElement(By.xpath("//table[@aria-label='All Contacts']//td[3]//span/a[@title='"+fullName+"']")).click();
			util.waitForJavascript(driver, 30000, 5000);
		}
		
		util.waitUntilElement(driver, accountName);
		js.executeScript("arguments[0].click();", accountName);
		// accountName.click();
		util.waitUntilElement(driver, showallBtn);
		Thread.sleep(5000);
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.moveToElement(showallBtn).build().perform();
		showallBtn.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, memberShip);
		// Instantiating Actions class
		// Actions actions = new Actions(driver);
		// Hovering on main menu
		// actions.moveToElement(contactTitle);
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		actions.sendKeys(Keys.ARROW_DOWN).build().perform();
		Thread.sleep(5000);
		util.waitUntilElement(driver, memberShip);
		memberShip.click();
		util.waitUntilElement(driver, tableSubscriptionId);
		Thread.sleep(1000);
		tableSubscriptionId.click();
		util.waitUntilElement(driver, Terms);
		Terms.click();
		util.waitUntilElement(driver, termId);
		js.executeScript("arguments[0].click();", termId);
		// termId.click();
		Thread.sleep(5000);
		util.waitUntilElement(driver, editBtn);
		Thread.sleep(5000);
		Actions act = new Actions(driver);
		act.scrollToElement(editBtn);
		// JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,200)", editBtn);
		editBtn.click();
		util.waitUntilElement(driver, inputTermEndDate);
		inputTermEndDate.clear();
		inputTermEndDate.sendKeys("12/31/2023");
		util.waitUntilElement(driver, inputTermGraceDate);
		inputTermGraceDate.clear();
		inputTermGraceDate.sendKeys("4/4/2024");
		saveBtn.click();
		Thread.sleep(1000);
		act.sendKeys(Keys.F5);
		Thread.sleep(2000);
	}

	/**
	 * @throws InterruptedException
	 */
	public void checkUserInProviderApplication(String user) throws InterruptedException {
		Actions actions = new Actions(driver);
		util.waitUntilElement(driver, appLauncherIcn);
		Thread.sleep(10000);
		appLauncherIcn.click();
		util.waitUntilElement(driver, appSearchtxtbx);
		util.enterText(driver, appSearchtxtbx, appName);
		util.waitUntilElement(driver, searchedAppPA);
		WebElement provAppElement = Utility.waitForWebElement(driver, "//b[text()='" + appName + "']", 10);
		provAppElement.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, tableProviderApp);
		util.waitUntilElement(driver, selectList);
		selectList.click();
		util.waitUntilElement(driver, allBtn);
		allBtn.click();
		util.waitUntilElement(driver, searchBox);
		searchBox.click();
		searchBox.sendKeys(user);
		Thread.sleep(5000);
		searchBox.sendKeys(Keys.ENTER);
		boolean ponumber = false;
		if(noItemHeading.isDisplayed()) {
			 int count =10;
			 for (int i = 0; i < count  && ponumber == false; i++) 
			 {
				 Thread.sleep(2000);
				 searchBox.clear();
				 driver.findElement(By.cssSelector("a[class='toggle slds-th__action slds-text-link--reset ']:nth-child(1)")).click();
				 Thread.sleep(4000);
				 searchBox.sendKeys(user);
				searchBox.sendKeys(Keys.ENTER);
				Thread.sleep(10000);
				 try {
				 if(driver.findElement(By.xpath("//table[@aria-label='All']//tbody//th//a")).isDisplayed()) {
					 ponumber = true;
					 if(ponumber==true) {
						 driver.findElement(By.xpath("//table[@aria-label='All']//tbody//th//a")).click(); 
						 util.waitForJavascript(driver, 20000, 2000);
					 }else {
						 System.out.println("ponumber is not clicked");
					 }
				 }else {
					 System.out.println("ponumber is not displayed");
				 }
				 }
				 catch (Exception b) {
						System.out.println(b);
					}
				
				 }
			 
		}else {
			util.waitForJavascript(driver, 30000, 5000);
			driver.findElement(By.xpath("//table[@aria-label='All']//tbody//th//a")).click(); 
		}
		
	}



	public void changeProviderApplicationRenew(String fullName, String providerID, String providerStatus)
			throws InterruptedException {
		/*
		 * util.waitUntilElement(driver, userName);
		 * userName.sendKeys("sgopisetty@innominds.com.aia.testing");
		 * password.sendKeys("Harshi@437"); loginBtn.click();
		 */
		util.waitUntilElement(driver, appLauncherIcn);
		Thread.sleep(10000);
		appLauncherIcn.click();
		util.waitUntilElement(driver, appSearchtxtbx);
		util.enterText(driver, appSearchtxtbx, appName);
		util.waitUntilElement(driver, searchedAppPA);
		WebElement provAppElement = Utility.waitForWebElement(driver, "//b[text()='" + appName + "']", 10);
		provAppElement.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, tableProviderApp);
		util.waitUntilElement(driver, selectList);
		selectList.click();
		util.waitUntilElement(driver, allBtn);
		allBtn.click();
		WebElement provAppIDElement = Utility.waitForWebElement(driver,
				"//table[@aria-label='All']/tbody//a[@title='" + providerID + "']", 10);
		provAppIDElement.click();
		Thread.sleep(2000);
		Actions act = new Actions(driver);
		act.scrollToElement(editAppStatusIon);
		util.waitUntilElement(driver, editAppStatusIon);
		editAppStatusIon.click();
		util.waitUntilElement(driver, appStatusBtnDrpdwn);
		appStatusBtnDrpdwn.click();
		WebElement provStatusElement = Utility.waitForWebElement(driver, "//span[@title='" + providerStatus + "']", 10);
		provStatusElement.click();
		saveBtn.click();
		Thread.sleep(1000);
		act.sendKeys(Keys.F5);
		Thread.sleep(5000);
	}



	public void checkProviderApplicationFields(String fullName, String providerID) throws InterruptedException {
		/*
		 * util.waitUntilElement(driver, userName);
		 * userName.sendKeys("sgopisetty@innominds.com.aia.testing");
		 * password.sendKeys("Harshi@437"); loginBtn.click();
		 */
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, appLauncherIcn);
		Thread.sleep(10000);
		appLauncherIcn.click();
		util.waitUntilElement(driver, appSearchtxtbx);
		util.enterText(driver, appSearchtxtbx, appName);
		util.waitUntilElement(driver, searchedAppPA);
		WebElement provAppElement = Utility.waitForWebElement(driver, "//b[text()='" + appName + "']", 10);
		provAppElement.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, tableProviderApp);
		util.waitUntilElement(driver, selectList);
		selectList.click();
		util.waitUntilElement(driver, allBtn);
		allBtn.click();
		WebElement provAppIDElement = Utility.waitForWebElement(driver,
				"//table[@aria-label='All']/tbody//a[@title='" + providerID + "']", 10);
		provAppIDElement.click();
		util.waitUntilElement(driver, accountNameLink);
		js.executeScript("arguments[0].click();", accountNameLink);
		util.waitUntilElement(driver, annualRevenueTxt);
		assertTrue(annualRevenueTxt.isDisplayed());
		util.waitUntilElement(driver, offerCoursesTxt);
		assertTrue(offerCoursesTxt.isDisplayed());
		util.waitUntilElement(driver, orgTaxIDTxt);
		assertTrue(orgTaxIDTxt.isDisplayed());

	}
	
	
	public void checkOriginField(String fullName, String providerID) throws InterruptedException {
		/*
		 * util.waitUntilElement(driver, userName);
		 * userName.sendKeys("sgopisetty@innominds.com.aia.testing");
		 * password.sendKeys("Harshi@437"); loginBtn.click();
		 */
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, appLauncherIcn);
		Thread.sleep(10000);
		appLauncherIcn.click();
		util.waitUntilElement(driver, appSearchtxtbx);
		util.enterText(driver, appSearchtxtbx, appName);
		util.waitUntilElement(driver, searchedAppPA);
		WebElement provAppElement = Utility.waitForWebElement(driver, "//b[text()='" + appName + "']", 10);
		provAppElement.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, tableProviderApp);
		util.waitUntilElement(driver, selectList);
		selectList.click();
		util.waitUntilElement(driver, allBtn);
		allBtn.click();
		WebElement provAppIDElement = Utility.waitForWebElement(driver,
				"//table[@aria-label='All']/tbody//a[@title='" + providerID + "']", 10);
		provAppIDElement.click();
		util.waitUntilElement(driver, accountNameLink);
		js.executeScript("arguments[0].click();", accountNameLink);
		util.waitUntilElement(driver, salesOrder);
		salesOrder.click();
		util.waitUntilElement(driver, salesOrderID);
		salesOrderID.click();
		WebElement textValidation = Utility.waitForWebElement(driver, salesOrderOriginTxt, 10);
		Assert.assertEquals("CES Provider Join",textValidation.getText());
	}
	
	
	public void checkOriginFieldRenew() {
		util.waitUntilElement(driver, searchBox);
		searchBox.sendKeys("");
	}


   
}
