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

public class MembersInCESAudit {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(MembersInCESAudit.class);

	public MembersInCESAudit(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Members in CES Audit']") WebElement membersInCESAuditTab;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement membersInCESAuditFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement membersInCESAuditRecords;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement membersInCESAuditNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>membersInCESAuditRecordsData;
	
	
	public void clickMembersInCESAuditTab() throws Throwable {
		Utility.waitForWebElement(driver, membersInCESAuditTab, 0);
		membersInCESAuditTab.click();
	}
	
	public void getMembersInCESAuditTabRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, membersInCESAuditFrame);
		Utility.waitForWebElement(driver, membersInCESAuditRecords, 0);
		String recordsCount = membersInCESAuditRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = membersInCESAuditNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(membersInCESAuditRecordsData.size());
			for (int i = 0; i < membersInCESAuditRecordsData.size(); i++) {
			String recordsData = membersInCESAuditRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
}
