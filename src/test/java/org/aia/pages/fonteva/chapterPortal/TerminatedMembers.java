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

public class TerminatedMembers {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(TerminatedMembers.class);

	public TerminatedMembers(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Terminated Members']") WebElement terminatedMembersTab;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement terminatedMembersFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement terminatedMembersRecords;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> terminatedMembersRecordsData;
	
	
	public void clickTerminatedMemberRosterTab() throws Throwable {
		terminatedMembersTab.click();
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
	}
	
	public void getTerminatedMemberRosterRecordsCount() {
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, terminatedMembersFrame);
		Utility.waitForWebElement(driver, terminatedMembersRecords, 0);
		String recordsCount = terminatedMembersRecords.getText();
		System.out.println(recordsCount);
	}
	
	public void getTerminatedMembersRecordsData() throws Throwable {
		Thread.sleep(8000);
		System.out.println(terminatedMembersRecordsData.size());
		for (int i = 0; i < terminatedMembersRecordsData.size(); i++) {
		String recordsData = terminatedMembersRecordsData.get(i).getText();
		System.out.println(recordsData.replace("\n", ""));
		log.info(recordsData.replace("\n", ""));
		System.out.println("records count"+i);
		}
		util.switchToTabs(driver, 0);
		driver.switchTo().defaultContent();
	}

}

