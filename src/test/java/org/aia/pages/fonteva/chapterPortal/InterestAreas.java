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

public class InterestAreas {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(InterestAreas.class);

	public InterestAreas(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Interest Areas']") WebElement interestAreasTab;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement interestAreasFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement interestAreasRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement interestAreasNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>interestAreasRecordsData;
	
	
	public void clickInterestAreasTab() throws Throwable {
		Utility.waitForWebElement(driver, interestAreasTab, 0);
		interestAreasTab.click();
	}
	
	public void getInterestAreasTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		Thread.sleep(5000);
		util.switchToFrameUsingWebElement(driver, interestAreasFrame);
		Utility.waitForWebElement(driver, interestAreasRecords, 0);
		String recordsCount = interestAreasRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = interestAreasNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(interestAreasRecordsData.size());
			for (int i = 0; i < interestAreasRecordsData.size(); i++) {
			String recordsData = interestAreasRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}
