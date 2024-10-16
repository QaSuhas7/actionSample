package org.aia.pages.fonteva.chapterPortal;

import org.testng.Assert;
import org.testng.AssertJUnit;
import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;

public class UpgradeToArchitect {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(UpgradeToArchitect.class);

	public UpgradeToArchitect(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}

	@FindBy(xpath = "//button[text()='Upgrade to Architect']")
	WebElement upgradeToArchitectTab;

	@FindBy(xpath = "//header[@class='slds-modal__header']/h2")
	WebElement selectReportDatePopUp;

	@FindBy(xpath = "//input[@name='fromDate']")
	WebElement upgradeFromDate;

	@FindBy(xpath = "//input[@name='toDate']")
	WebElement upgradeToDate;

	@FindBy(xpath = "//button[text()='Go to Report']")
	WebElement gotoReportButtonInSelectReportDatePopup;

	@FindBy(xpath = "//iframe[@title='Report Viewer']")
	WebElement upgradeToArchitectFrame;

	@FindBy(xpath = "//div[@title='Total Records']/parent::li")
	WebElement upgradeToArchitectRecords;

	@FindBy(xpath = "//div[@class='report-table-widget-noData']")
	WebElement upgradeNoDataMSg;

	@FindAll(value = {
			@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]") })
	List<WebElement> upgradeToArchitectRecordsData;

	public void clickUpgradeToArchitectTab() throws Throwable {
		Thread.sleep(7000);
		util.scrollingElementUsingJS(driver, upgradeToArchitectTab);
		util.waitUntilElement(driver, upgradeToArchitectTab);
		upgradeToArchitectTab.click();
	}

	public void selectDateReportPopupInUpGradeArchitect(String postDate) throws InterruptedException {
		util.waitUntilElement(driver, selectReportDatePopUp);
		Assert.assertTrue(selectReportDatePopUp.isDisplayed());
		String fromDate = upgradeFromDate.getAttribute("value");
		log.info(fromDate);
		String toDate = upgradeToDate.getAttribute("value");
		log.info(toDate);
		Thread.sleep(5000);
		gotoReportButtonInSelectReportDatePopup.click();
		Thread.sleep(10000);
	}

	public void getUpgradeToArchitectTabRecordsCount() throws Throwable {
		Thread.sleep(20000);
		util.switchToTabs(driver, 1);
		util.switchToFrameUsingWebElement(driver, upgradeToArchitectFrame);
		Utility.waitForWebElement(driver, upgradeToArchitectRecords, 0);
		String recordsCount = upgradeToArchitectRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if (recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = upgradeNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}
		driver.switchTo().defaultContent();
	}

}
