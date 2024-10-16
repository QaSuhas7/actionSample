package org.aia.pages.fonteva.chapterPortal;

import org.testng.Assert;
import org.testng.AssertJUnit;

import groovy.transform.Final;

import java.util.Iterator;
import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class MemberShipInChapterPortal {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(MemberShipInChapterPortal.class);

	public MemberShipInChapterPortal(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Retention']") WebElement retentionTab;
	
	@FindBy(xpath = "//button[text()='Finance']") WebElement financeTab; 
	
	@FindBy(xpath = "//button[text()='Chapter Info']") WebElement chapterInfoTab; 
	
	@FindBy(xpath = "//button[text()='Call for Dues']") WebElement callForDuesInfoTab;
	
	@FindBy(xpath = "//button[text()='Full Member Roster']") WebElement fullMemberRoasterTab;
	
	@FindBy(xpath = "//button[text()='Active Member Roster']") WebElement activeMemberRoasterTab;
	
	@FindBy(xpath = "//button[text()='Lapsed Members']") WebElement lapsedMEmbersTab;
	
	@FindBy(xpath = "//button[text()='Join/Re-join']") WebElement joinRejoinTab;
	
	@FindBy(xpath = "//button[text()='New Grad']") WebElement newGradTab;
	
	@FindBy(xpath = "//button[text()='Terminated Members']") WebElement terminatedMembersTab;
	
	@FindBy(xpath = "//button[text()='Transfers In']") WebElement transferInTab;
	
	@FindBy(xpath = "//button[text()='Transfers Out']") WebElement transferOutTab;
	
	@FindBy(xpath = "//button[text()='Upgrade to Architect']") WebElement upgradeToArchitectTab;
	
	@FindBy(xpath = "//button[text()='Members in CES Audit']") WebElement membersInCESAuditTab;
	
	@FindBy(xpath = "//button[text()='Deceased']") WebElement deceasedTab;
	
	@FindBy(xpath = "//button[text()='Interest Areas']") WebElement interestAreasTab;
	
	@FindBy(xpath = "//button[text()='Email Change Log']") WebElement emailChangeLogTab;
	
	@FindBy(xpath = "//button[text()='Firm Details']") WebElement firmDetailsTab;
	
	@FindBy(xpath = "//button[text()='Associates / Path to Licensure']") WebElement associatesPathtoLicensureTab;
	
	@FindBy(xpath = "//button[text()='Architects Licensed =/<10 years']") WebElement architectsLicensedTenyearsTab;
	
	@FindAll(value = {@FindBy(xpath = "//div[@id='reportChartFrameWrapper']")}) List<WebElement> memberShipPieCharts;
	
	
	public void clickRetentionTab() {
		Utility.waitForWebElement(driver, retentionTab, 0);
		Assert.assertTrue(retentionTab.isDisplayed());
		retentionTab.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void clickFinanceTab() {
		Utility.waitForWebElement(driver, financeTab, 0);
		Assert.assertTrue(financeTab.isDisplayed());
		financeTab.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void clickChapterInfoTab() {
		Utility.waitForWebElement(driver, chapterInfoTab, 0);
		Assert.assertTrue(chapterInfoTab.isDisplayed());
		chapterInfoTab.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void clickCallForDuesInfoTab() {
		Utility.waitForWebElement(driver, callForDuesInfoTab, 0);
		Assert.assertTrue(callForDuesInfoTab.isDisplayed());
		callForDuesInfoTab.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void validateMemberShipTabSections() throws Throwable {
		Thread.sleep(5000);
		Assert.assertTrue(fullMemberRoasterTab.isDisplayed());
		Assert.assertTrue(activeMemberRoasterTab.isDisplayed());
		Assert.assertTrue(lapsedMEmbersTab.isDisplayed());
		Assert.assertTrue(joinRejoinTab.isDisplayed());
		Assert.assertTrue(newGradTab.isDisplayed());
		Assert.assertTrue(terminatedMembersTab.isDisplayed());
		Assert.assertTrue(transferInTab.isDisplayed());
		Assert.assertTrue(transferOutTab.isDisplayed());
		Assert.assertTrue(upgradeToArchitectTab.isDisplayed());
		Assert.assertTrue(membersInCESAuditTab.isDisplayed());
		Assert.assertTrue(deceasedTab.isDisplayed());
		Assert.assertTrue(interestAreasTab.isDisplayed());
		Assert.assertTrue(emailChangeLogTab.isDisplayed());
		Assert.assertTrue(firmDetailsTab.isDisplayed());
		Assert.assertTrue(associatesPathtoLicensureTab.isDisplayed());
		Assert.assertTrue(architectsLicensedTenyearsTab.isDisplayed());	
		Thread.sleep(5000);
	}
	
	public void validateMemberShipPiecharts() throws Throwable {
		int count = 4;
		log.info(memberShipPieCharts.size());
		for (int i = 0; i < memberShipPieCharts.size(); i++) {
			String pieChartText = memberShipPieCharts.get(i).getText();
			log.info(pieChartText);
			System.out.println(pieChartText);
		}
		Thread.sleep(4000);
		for (int i = 0; i < count; i++) {
			WebElement pieChart = driver.findElement(By.xpath("//div[@data-ngname='chart_"+i+"']"));
			log.info(pieChart+" "+i);
			Assert.assertTrue(pieChart.isDisplayed());
			log.info(pieChart+" "+ i +"is dsipalyed");
		}	
	}

}
