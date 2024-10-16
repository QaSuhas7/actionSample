package org.aia.pages.fonteva.chapterPortal;

import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class FullMemberRoster {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(FullMemberRoster.class);

	public FullMemberRoster(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Full Member Roster']") WebElement fullMemberRoasterTab;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement fullMemberRosterFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement fullMemberRosterRecords;
	
	@FindBy(xpath = "//div[@class='reports-metrics-message']") WebElement fullMemberRosterWarningMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> fullMemberRecordsData;
	
	
	public void clickFullMemberRosterTab() throws Throwable {
		fullMemberRoasterTab.click();
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
	}
	
	public void getFullMemberRosterRecordsCount() throws InterruptedException {
		Thread.sleep(4000);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, fullMemberRosterFrame);
		Utility.waitForWebElement(driver, fullMemberRosterRecords, 0);
		String recordsCount = fullMemberRosterRecords.getText();
		System.out.println(recordsCount);
		Utility.waitForWebElement(driver, fullMemberRosterWarningMSg, 0);
		if(fullMemberRosterWarningMSg.isDisplayed()) {
		String warningMsg = fullMemberRosterWarningMSg.getText();
		System.out.println(warningMsg);
		log.info(warningMsg);
		}
	}
	
	public void getRecordsData() throws Throwable {
		Thread.sleep(8000);
		System.out.println(fullMemberRecordsData.size());
		for (int i = 0; i < fullMemberRecordsData.size(); i++) {
		String recordsData = fullMemberRecordsData.get(i).getText();
		System.out.println(recordsData.replace("\n", ""));
		log.info(recordsData.replace("\n", ""));
		System.out.println("records count"+i);
		}
		util.switchToTabs(driver, 0);
		driver.switchTo().defaultContent();
	}

}
