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

public class NewGrad {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(NewGrad.class);

	public NewGrad(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='New Grad']") WebElement newGradTab;
	
	@FindBy(xpath = "//header[@class='slds-modal__header']/h2") WebElement selectReportDatePopUp;
	
	@FindBy(xpath  = "//input[@name='fromDate']") WebElement newGradFromDate;
	
	@FindBy(xpath =  "//input[@name='toDate']") WebElement newGradToDate;
	
	@FindBy(xpath = "//button[text()='Go to Report']") WebElement gotoReportButtonInSelectReportDatePopup;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement newGradFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement NewGradRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement upgradeNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> NewGradRecordsData;
	
	
	public void clicknewGradTab() throws Throwable {
		newGradTab.click();
		util.waitUntilElement(driver, selectReportDatePopUp);
	}
	
	public void selectDateReportPopupInNewGrad(String pastDate) {
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		util.enterText(driver, newGradFromDate, pastDate);
		String fromDate = newGradFromDate.getAttribute("value");
		log.info(fromDate);
		util.enterText(driver, newGradToDate, new SimpleDateFormat("MM/dd/yyyy").format(new Date()));
		String toDate = newGradToDate.getAttribute("value");
		log.info(toDate);
		gotoReportButtonInSelectReportDatePopup.click();
	}
	
	public void getnewGradTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		Thread.sleep(2000);
		util.switchToFrameUsingWebElement(driver, newGradFrame);
		Utility.waitForWebElement(driver, NewGradRecords, 0);
		String recordsCount = NewGradRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = upgradeNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(NewGradRecordsData.size());
			for (int i = 0; i < NewGradRecordsData.size(); i++) {
			String recordsData = NewGradRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
	   }
	
	}
	
}
