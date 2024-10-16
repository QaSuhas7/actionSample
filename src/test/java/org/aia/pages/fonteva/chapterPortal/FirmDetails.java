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

public class FirmDetails {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(FirmDetails.class);

	public FirmDetails(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Firm Details']") WebElement firmDetailsTab;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement firmDetailsFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement firmDetailsRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement firmDetailsNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>firmDetailsRecordsData;
	
	
	public void clickFirmDetailsTab() throws Throwable {
		Utility.waitForWebElement(driver, firmDetailsTab, 0);
		firmDetailsTab.click();
	}
	
	public void getFirmDetailsTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, firmDetailsFrame);
		Utility.waitForWebElement(driver, firmDetailsRecords, 0);
		String recordsCount = firmDetailsRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = firmDetailsNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(firmDetailsRecordsData.size());
			for (int i = 0; i < firmDetailsRecordsData.size(); i++) {
			String recordsData = firmDetailsRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}
