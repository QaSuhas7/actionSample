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

public class TransfersIn {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(TransfersIn.class);

	public TransfersIn(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Transfers In']") WebElement transferInTab;
	
	@FindBy(xpath = "//header[@class='slds-modal__header']/h2") WebElement selectReportDatePopUp;
	
	@FindBy(xpath  = "//input[@name='fromDate']") WebElement transferInFromDate;
	
	@FindBy(xpath =  "//input[@name='toDate']") WebElement transferInToDate;
	
	@FindBy(xpath = "//button[text()='Go to Report']") WebElement gotoReportButtonInSelectReportDatePopup;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement transferInFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement transferInRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement transferInNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>transferInRecordsData;
	
	
	public void clickTransfersInTab() throws Throwable {
		Utility.waitForWebElement(driver, transferInTab, 0);
		transferInTab.click();
	}
	
	public void selectDateReportPopupInTransferIn(String pastDate) {
		util.waitUntilElement(driver, selectReportDatePopUp);
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		util.enterText(driver, transferInFromDate, pastDate);
		String fromDate = transferInFromDate.getAttribute("value");
		log.info(fromDate);
		util.enterText(driver, transferInToDate, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		String toDate = transferInToDate.getAttribute("value");
		log.info(toDate);
		gotoReportButtonInSelectReportDatePopup.click();
	}
	
	public void getTransfersInTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, transferInFrame);
		Utility.waitForWebElement(driver, transferInRecords, 0);
		String recordsCount = transferInRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = transferInNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(transferInRecordsData.size());
			for (int i = 0; i < transferInRecordsData.size(); i++) {
			String recordsData = transferInRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}
