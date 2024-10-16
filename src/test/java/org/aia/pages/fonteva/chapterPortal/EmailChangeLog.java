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

public class EmailChangeLog {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(EmailChangeLog.class);

	public EmailChangeLog(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Email Change Log']") WebElement emailChangeLogTab;
	
    @FindBy(xpath = "//header[@class='slds-modal__header']/h2") WebElement selectReportDatePopUp;
	
	@FindBy(xpath  = "//input[@name='fromDate']") WebElement emailChangeLogFromDate;
	
	@FindBy(xpath =  "//input[@name='toDate']") WebElement emailChangeLogToDate;
	
	@FindBy(xpath = "//button[text()='Go to Report']") WebElement gotoReportButtonInSelectReportDatePopup;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement emailChangeLogFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement emailChangeLogRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement emailChangeLogNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>emailChangeLogRecordsData;
	
	
	public void clickEmailChangeLogTab() throws Throwable {
        util.scrollingElementUsingJS(driver, emailChangeLogTab);
		Utility.waitForWebElement(driver, emailChangeLogTab, 0);
		emailChangeLogTab.click();
	}
	
	public void selectDateReportPopupInEmailChange(String pastDate) throws Throwable {
		util.waitUntilElement(driver, selectReportDatePopUp);
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		Thread.sleep(5000);
		util.enterText(driver, emailChangeLogFromDate, pastDate);
		String fromDate = emailChangeLogFromDate.getAttribute("value");
		log.info(fromDate);
		util.enterText(driver, emailChangeLogToDate, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		String toDate = emailChangeLogToDate.getAttribute("value");
		log.info(toDate);
		Thread.sleep(5000);
		gotoReportButtonInSelectReportDatePopup.click();
		Thread.sleep(8000);
	}
	
	public void getEmailChangeLogTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, emailChangeLogFrame);
		Utility.waitForWebElement(driver, emailChangeLogRecords, 0);
		String recordsCount = emailChangeLogRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = emailChangeLogNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(emailChangeLogRecordsData.size());
			for (int i = 0; i < emailChangeLogRecordsData.size(); i++) {
			String recordsData = emailChangeLogRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}
