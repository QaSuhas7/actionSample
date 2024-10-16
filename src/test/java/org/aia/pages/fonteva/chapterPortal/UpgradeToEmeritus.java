package org.aia.pages.fonteva.chapterPortal;

import org.testng.Assert;
import org.testng.AssertJUnit;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class UpgradeToEmeritus {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(UpgradeToEmeritus.class);

	public UpgradeToEmeritus(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Upgrade to Emeritus']") WebElement upgradeToEmeritusTab;
	
	@FindBy(xpath = "//header[@class='slds-modal__header']/h2") WebElement selectReportDatePopUp;
	
	@FindBy(xpath  = "//input[@name='fromDate']") WebElement upgradeFromDate;
	
	@FindBy(xpath =  "//input[@name='toDate']") WebElement upgradeToDate;
	
	@FindBy(xpath = "//button[text()='Go to Report']") WebElement gotoReportButtonInSelectReportDatePopup;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement upgradeToEmeritusFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement upgradeToEmeritusRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement upgradeNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> upgradeToEmeritusRecordsData;
	
	
	public void clickUpgradeToEmeritusTab() throws Throwable {
		Thread.sleep(7000);
		util.scrollingElementUsingJS(driver, upgradeToEmeritusTab);
		upgradeToEmeritusTab.click();
		util.waitUntilElement(driver, selectReportDatePopUp);
	}
	
	public void selectDateReportPopupInUpGradeEmeritus(String postDate) throws InterruptedException {
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		util.waitUntilElement(driver, upgradeFromDate);
		util.enterText(driver, upgradeFromDate, postDate);
		String fromDate = upgradeFromDate.getAttribute("value");
		log.info(fromDate);
		util.enterText(driver, upgradeToDate, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		String toDate = upgradeToDate.getAttribute("value");
		log.info(toDate);
		Thread.sleep(5000);
		gotoReportButtonInSelectReportDatePopup.click();
		Thread.sleep(10000);
	}
	
	public void getUpgradeToEmeritusTabRecordsCount() throws Throwable {
		Thread.sleep(20000);
		util.switchToTabs(driver, 1);
		util.switchToFrameUsingWebElement(driver, upgradeToEmeritusFrame);
		Utility.waitForWebElement(driver, upgradeToEmeritusRecords, 0);
		String recordsCount = upgradeToEmeritusRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = upgradeNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}
		driver.switchTo().defaultContent();
	}
	
}



