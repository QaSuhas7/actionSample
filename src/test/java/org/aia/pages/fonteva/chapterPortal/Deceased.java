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

public class Deceased {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(Deceased.class);

	public Deceased(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Deceased']") WebElement deceasedTab;
	
    @FindBy(xpath = "//header[@class='slds-modal__header']/h2") WebElement selectReportDatePopUp;
	
	@FindBy(xpath  = "//input[@name='fromDate']") WebElement deceasedFromDate;
	
	@FindBy(xpath =  "//input[@name='toDate']") WebElement deceasedToDate;
	
	@FindBy(xpath = "//button[text()='Go to Report']") WebElement gotoReportButtonInSelectReportDatePopup;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement deceasedFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement deceasedRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement deceasedNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>deceasedRecordsData;
	
	
	public void clickDeceasedTab() throws Throwable {
		Utility.waitForWebElement(driver, deceasedTab, 0);
		deceasedTab.click();
	}
	
	public void selectDateReportPopupInDeceased(String postdate) throws Throwable {
		util.waitUntilElement(driver, selectReportDatePopUp);
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		Thread.sleep(3000);
		util.enterText(driver, deceasedFromDate, postdate);
		String fromDate = deceasedFromDate.getAttribute("value");
		log.info(fromDate);
		util.enterText(driver, deceasedToDate, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		String toDate = deceasedToDate.getAttribute("value");
		log.info(toDate);
		Thread.sleep(5000);
		gotoReportButtonInSelectReportDatePopup.click();
		Thread.sleep(7000);
	}
	
	public void getDeceasedTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, deceasedFrame);
		Utility.waitForWebElement(driver, deceasedRecords, 0);
		String recordsCount = deceasedRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = deceasedNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(deceasedRecordsData.size());
			for (int i = 0; i < deceasedRecordsData.size(); i++) {
			String recordsData = deceasedRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}
