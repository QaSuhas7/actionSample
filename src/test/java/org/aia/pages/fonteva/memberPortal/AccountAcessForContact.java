package org.aia.pages.fonteva.memberPortal;

import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class AccountAcessForContact {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(AccountAcessForContact.class);

	public AccountAcessForContact(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}

	@FindBy(xpath = "//a[@href='/lightning/o/Contact/home']")
	WebElement contactsModule;

	@FindBy(css = "button[class*='_neutral search-button slds-truncate']")
	WebElement globSearchInContact;

	@FindBy(xpath = "//label[contains(text(),'Search')]/following-sibling::div/input[contains(@aria-controls,'suggestionsList')]")
	WebElement globSearchInputInContact;

	@FindBy(xpath = "//span[text()='App Launcher']//parent::div")
	WebElement appLauncher;

	@FindBy(xpath = "//input[@placeholder='Search apps and items...']")
	WebElement appLauncherSearchBox;

	@FindBy(xpath = "//a[@href='/lightning/o/Contact/home' and @id='Contact']")
	WebElement appLauncherContactsValue;

	@FindBy(xpath = "//li/a[@title='Contacts']")
	WebElement contactsInGlobalSearch;

	@FindBy(xpath = "//a[@href='javascript:void(0)']/parent::div")
	WebElement showAllLink;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//span[text()='Show more actions']/parent::button[@part='button button-icon']")
	WebElement dropDownButtonActionContainer;

	@FindAll(value = {
			@FindBy(xpath = "//div[@class='slds-dropdown slds-dropdown_right']//runtime_platform_actions-action-renderer") })
	List<WebElement> showAllOptionInActionContainer;

	@FindAll(value = { @FindBy(xpath = "//div[@class='forceCommunityShowPortalUserMemberList']/ul/li") })
	List<WebElement> portalUserMemberList;

	@FindBy(xpath = "//span[text()='Enable Customer User']/parent::a")
	WebElement enableCustomerUserOpt;

	@FindBy(xpath = "//span//select[@id='Profile']")
	WebElement userProfiledrpdwn;

	@FindBy(xpath = "//td[@id='bottomButtonRow']//input[@value= ' Save ' ]")
	WebElement userProfileSave;

	public void clickContactsModule() {
		Utility.waitForWebElement(driver, contactsModule, 0);
		try {
			util.clickUsingJS(driver, contactsModule);
		} catch (Exception e) {
			System.out.println("js not clicked");
			contactsModule.click();
		}
	}

	public void globalSearchInContact(String contactName) throws Throwable {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, globSearchInContact, 30);
		globSearchInContact.click();
		globSearchInputInContact.sendKeys(contactName);
		Thread.sleep(4000);
		globSearchInputInContact.sendKeys(Keys.ENTER);
	}

	public void clickContactsInGlobalSearch(String contactName) throws Throwable {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, contactsInGlobalSearch, 10);
		contactsInGlobalSearch.click();
		Thread.sleep(4000);
		WebElement contactElement = driver.findElement(By.xpath(
				"//table[@data-aura-class='uiVirtualDataTable'][not(contains(@aria-label,'Recently Viewed'))]/tbody/tr/th/span/a[@title='"
						+ contactName + "']"));
		util.waitUntilElement(driver, contactElement);
		contactElement.click();
	}

	public void showAllInRealtedQuickLinks() {
		Utility.waitForWebElement(driver, showAllLink, 0);
		showAllLink.click();
	}

	public void clickDropDownInActionContainer() {
		Utility.waitForWebElement(driver, dropDownButtonActionContainer, 0);
		dropDownButtonActionContainer.click();
	}

	public void verifyFieldsMemberPortal(String profile) throws InterruptedException {
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		util.waitUntilElement(driver, enableCustomerUserOpt);
		js.executeScript("arguments[0].click();", enableCustomerUserOpt);
		Thread.sleep(20000);
		WebElement profileFrame = driver.findElement(By.xpath("//iframe[contains(@name,'vfFrameId_')]"));
		driver.switchTo().frame(profileFrame);
		WebElement userProfiledrpdwn = driver.findElement(By.cssSelector("select[id='Profile']"));
		Select dropdown = new Select(userProfiledrpdwn);
		dropdown.selectByVisibleText(profile);
		util.waitUntilElement(driver, userProfileSave);
		util.scrollingElementUsingJS(driver, userProfileSave);
		userProfileSave.click();
		driver.navigate().back();
		Thread.sleep(2000);
		driver.navigate().back();
		Thread.sleep(1000);
		driver.navigate().back();
	}

	public void navigateContactsModule() throws Throwable {
		System.out.println("Contacts module is not displayed");
		util.waitUntilElement(driver, appLauncher);
		appLauncher.click();
		util.waitUntilElement(driver, appLauncherSearchBox);
		appLauncherSearchBox.sendKeys("Contacts");
		Thread.sleep(2000);
		util.scrollingElementUsingJS(driver, appLauncherContactsValue);
		appLauncherContactsValue.click();
	}

	public void optionsInactionContainer() throws Throwable {
		Thread.sleep(5000);
		log.info(showAllOptionInActionContainer.size());
		for (int i = 0; i < showAllOptionInActionContainer.size(); i++) {
			String option = showAllOptionInActionContainer.get(i).getText();
			log.info(option);
			switch (option) {
			case "Log in to Experience as User":
				showAllOptionInActionContainer.get(i).click();
				log.info("Log in to Experience as User is clicked");
				Thread.sleep(8000);
				System.out.println(portalUserMemberList.size());
				for (int j = 0; j < portalUserMemberList.size(); j++) {
					String options2 = portalUserMemberList.get(j).getText();
					Thread.sleep(3000);
					log.info(options2);
					if (options2.equalsIgnoreCase("My Account")) {
						portalUserMemberList.get(j).click();
						log.info("memberPortal is clicked successfully");
						Thread.sleep(10000);
					} else {
						log.info("memberPortal is not clicked successfully");
					}
				}
				break;
			}
		}
	}

}
