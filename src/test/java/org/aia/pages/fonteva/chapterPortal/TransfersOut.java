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

public class TransfersOut {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(TransfersOut.class);

	public TransfersOut(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Transfers Out']") WebElement transferOutTab;
	
	@FindBy(xpath = "//header[@class='slds-modal__header']/h2") WebElement selectReportDatePopUp;
	
	@FindBy(xpath  = "//input[@name='fromDate']") WebElement transferOutFromDate;
	
	@FindBy(xpath =  "//input[@name='toDate']") WebElement transferOutToDate;
	
	@FindBy(xpath = "//button[text()='Go to Report']") WebElement gotoReportButtonInSelectReportDatePopup;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement transferOutFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement transferOutRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement transferOutNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>transferOutRecordsData;
	
	
	public void clickTransfersOutTab() throws Throwable {
		util.waitUntilElement(driver, transferOutTab);
		transferOutTab.click();
	}
	
	public void selectDateReportPopupInTransferOut(String postDate) {
		util.waitUntilElement(driver, selectReportDatePopUp);
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		util.enterText(driver, transferOutFromDate, postDate);
		String fromDate = transferOutFromDate.getAttribute("value");
		log.info(fromDate);
		util.enterText(driver, transferOutToDate, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		String toDate = transferOutToDate.getAttribute("value");
		log.info(toDate);
		gotoReportButtonInSelectReportDatePopup.click();
	}
	
	public void getTransfersOutTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, transferOutFrame);
		Utility.waitForWebElement(driver, transferOutRecords, 0);
		String recordsCount = transferOutRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = transferOutNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(transferOutRecordsData.size());
			for (int i = 0; i < transferOutRecordsData.size(); i++) {
			String recordsData = transferOutRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}

