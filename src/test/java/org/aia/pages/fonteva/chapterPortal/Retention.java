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
import org.testng.Assert;

public class Retention {
	
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(ChapterInfo.class);

	public Retention(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "(//iframe[@title='Report Viewer'])[1]") WebElement renewedMembersFrame;
	
	@FindAll(value = {@FindBy(xpath = "//div[@class='reportChartFrameWrapper']")}) List<WebElement> retentionPieCharts;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> retentionRenewedMembersRecordsData;
	
	@FindBy(xpath = "//a[text()='Invoicing Cycle Resources']") WebElement invoicingCycleResourceLink;
	
	@FindBy(xpath = "//a[text()='Membership Applications & Forms']") WebElement membershipApplicationFormLink;
	
	
	public void validateRetentionPieCharts() throws Throwable {
		Thread.sleep(5000);
		System.out.println(retentionPieCharts.size()); 
		for (WebElement piechaElement : retentionPieCharts) {
			Assert.assertTrue(piechaElement.isDisplayed());
			System.out.println(piechaElement.getText());
		}
	}
	
	public void getRenewedMembersRecordsData() throws Throwable {
		Thread.sleep(8000);
		util.switchToFrameUsingWebElement(driver, renewedMembersFrame);
		System.out.println(retentionRenewedMembersRecordsData.size());
		for (int i = 0; i < retentionRenewedMembersRecordsData.size(); i++) {
		Assert.assertTrue(retentionRenewedMembersRecordsData.get(i).isDisplayed(), "Record "+i+"  is not displayed");
		System.out.println("RecordData "+i+" is displayed");
		String recordsData = retentionRenewedMembersRecordsData.get(i).getText();
		System.out.println(recordsData.replace("\n", ""));
		log.info(recordsData.replace("\n", ""));
		System.out.println("records count"+i);
		}
		driver.switchTo().defaultContent();
	}
	
	public void clickInvocingCycleLink() {
		util.scrollingBottomOfPage(driver);
		util.waitUntilElement(driver, invoicingCycleResourceLink);
		invoicingCycleResourceLink.click();
	}

	public void clickMembershipApplicationFormLink() {
		util.waitUntilElement(driver, membershipApplicationFormLink);
		membershipApplicationFormLink.click();
	}
}
