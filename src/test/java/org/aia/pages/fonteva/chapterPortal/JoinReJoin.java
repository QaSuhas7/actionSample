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

public class JoinReJoin {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(JoinReJoin.class);

	public JoinReJoin(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Join/Re-join']") WebElement joinReJoinTab;
	
	@FindBy(xpath = "//header[@class='slds-modal__header']/h2") WebElement selectReportDatePopUp;
	
	@FindBy(xpath  = "//input[@name='fromDate']") WebElement joinReJoinFromDate;
	
	@FindBy(xpath =  "//input[@name='toDate']") WebElement joinReJoinToDate;
	
	@FindBy(xpath = "//button[text()='Go to Report']") WebElement gotoReportButtonInSelectReportDatePopup;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement joinReJoinFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement joinReJoinRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement joinReJoinNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>joinReJoinRecordsData;
	
	
	public void clickJoinReJoinTabTab() throws Throwable {
		Thread.sleep(7000);
		util.scrollingElementUsingJS(driver, joinReJoinTab);
		joinReJoinTab.click();
		util.waitUntilElement(driver, selectReportDatePopUp);
		
	}
	
	public void selectDateReportPopupInJoinRejoin(String pastDate) throws Throwable {
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		util.waitUntilElement(driver, joinReJoinFromDate);
		util.enterText(driver, joinReJoinFromDate, pastDate);
		String fromDate = joinReJoinFromDate.getAttribute("value");
		log.info(fromDate);
		util.enterText(driver, joinReJoinToDate, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		String toDate = joinReJoinToDate.getAttribute("value");
		log.info(toDate);
		Thread.sleep(5000);
		gotoReportButtonInSelectReportDatePopup.click();
		Thread.sleep(10000);
	}
	
	public void getJoinReJoinTabRecordsCount() throws Throwable {
		Thread.sleep(15000);
		util.switchToTabs(driver, 1);
		util.switchToFrameUsingWebElement(driver, joinReJoinFrame);
		Utility.waitForWebElement(driver, joinReJoinRecords, 0);
		String recordsCount = joinReJoinRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = joinReJoinNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(joinReJoinRecordsData.size());
			for (int i = 0; i < joinReJoinRecordsData.size(); i++) {
			String recordsData = joinReJoinRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}

