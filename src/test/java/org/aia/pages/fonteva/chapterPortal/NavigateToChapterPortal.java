package org.aia.pages.fonteva.chapterPortal;
import org.testng.Assert;
import org.testng.AssertJUnit;

import groovyjarjarantlr4.v4.parse.ANTLRParser.throwsSpec_return;

import java.util.ArrayList;
import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class NavigateToChapterPortal {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(NavigateToChapterPortal.class);
	public String salesOrder = "";
	public String aiaNumber = "";
	public  ArrayList<String> groupList = new ArrayList<String>();

	public NavigateToChapterPortal(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}

	@FindBy(xpath = "//a[@href='/lightning/o/Contact/home']")
	WebElement contactsModule;

//	@FindBy(css = "a[title='Allison Garwood Freedland']")
//	WebElement contactAccessCP;

	@FindBy(xpath = "//a[@href='javascript:void(0)']/parent::div")
	WebElement showAllLink;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a/slot/span[contains(text(),'Portal access')]")
	WebElement portalAccessCount;
	// innerText: "Portal access (10+)"
	
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a/slot/span[contains(text(),'Memberships')]")
	WebElement memebershipCountInRLQL;
	
	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//a/slot/span[contains(text(),'Sales Orders')]")
	WebElement salesOrderCountInRLQL;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//span[text()='Show more actions']/parent::button[@part='button button-icon']")
	WebElement dropDownButtonActionContainer;

	@FindAll(value = {
			@FindBy(xpath = "//div[@class='slds-dropdown slds-dropdown_right']//runtime_platform_actions-action-renderer") })
	List<WebElement> showAllOptionInActionContainer;

	@FindAll(value = { @FindBy(xpath = "//div[@class='forceCommunityShowPortalUserMemberList']/ul/li") })
	List<WebElement> portalUserMemberList;

	@FindBy(xpath = "//a[@href='/Chapter/s/my-chapters']")
	WebElement myChapterTab;

	@FindAll(value = {@FindBy(xpath = "//table[@aria-label='All Communities - Chapter Portal']/tbody//th/span/a")})
	List<WebElement> comunityGroupName;

	@FindBy(xpath = "//button[text()='Membership']")
	WebElement memberShipButtonInCP;

	public void clickContactsModule() {
		Utility.waitForWebElement(driver, contactsModule, 0);
		try {
			util.clickUsingJS(driver, contactsModule);
		} catch (Exception e) {
			System.out.println("js not clicked");
			contactsModule.click();
		}
	}

	public void clickContactsCPAccess(String contactName) throws Throwable {
		Thread.sleep(5000);
		WebElement contactAccessCP = driver.findElement(By.xpath("//a[@title='"+contactName+"']"));
		util.scrollingElementUsingJS(driver, contactAccessCP);
		Utility.waitForWebElement(driver, contactAccessCP, 0);
		contactAccessCP.click();
	}

	public void showAllInRealtedQuickLinks() throws Throwable {
	    Thread.sleep(4000);
		Utility.waitForWebElement(driver, showAllLink, 0);
		showAllLink.click();
	}

	public void getPortalAccessCount() throws Throwable {
		Thread.sleep(5000);
		Utility.waitForWebElement(driver, memebershipCountInRLQL, 0);
	    System.out.println(memebershipCountInRLQL.getText());
	    Thread.sleep(2000);
	    System.out.println(salesOrderCountInRLQL.getText());
		Utility.waitForWebElement(driver, portalAccessCount, 0);
		System.out.println(portalAccessCount.getText());
		Assert.assertTrue(portalAccessCount.getText()!="0");
	}

	public void clickDropDownInActionContainer() throws Throwable {
		Thread.sleep(4000);
		util.mosueOverUsingAction(driver, dropDownButtonActionContainer); 
		Utility.waitForWebElement(driver, dropDownButtonActionContainer, 0);
		dropDownButtonActionContainer.click();
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
					Thread.sleep(7000);
					log.info(options2);
					if (options2.equalsIgnoreCase("Chapters")) {
						portalUserMemberList.get(j).click();
						log.info("chaptersPortal is clicked successfully");
						Thread.sleep(10000);
					} else {
						log.info("chaptersPortal is not clicked successfully");
					}
				}
				break;
			}
		}
	}

	public void clickMyChapterTab() {
		Utility.waitForWebElement(driver, myChapterTab, 0);
		myChapterTab.click();
	}

	public void getComunityGroup(int index) throws Throwable {
        Thread.sleep(15000);
        for (int i = 1; i <= comunityGroupName.size(); i++) {
        	String group = comunityGroupName.get(i-1).getText();
        	Thread.sleep(2000);
        	System.out.println(group);
        if(i == index) {
        	groupList.add(group);
        	comunityGroupName.get(i-1).click();
        	break;
       	}
       	}
        
		Thread.sleep(6000);
		Utility.waitForWebElement(driver, memberShipButtonInCP, 0);
		if (memberShipButtonInCP.isDisplayed()) {
			log.info("memberShip button is displayed");
		}
	}

}
