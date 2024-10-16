package org.aia.pages.fonteva.chapterPortal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.aia.utility.ExcelDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class ActiveMemberRoster {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	ExcelDataProvider excelDataProvider;
	
	static Logger log = Logger.getLogger(ActiveMemberRoster.class);

	public ActiveMemberRoster(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Active Member Roster']") WebElement activeMemberRoasterTab;
	
	@FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement activeMemberRoasterFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement activeMemberRoasterRecords;
	
	@FindBy(xpath = "//button[text()='Export']") WebElement exportButtonInActiveMem;
	
	@FindBy(css = "select[class='slds-select']") WebElement formatDropDown;
	 
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> activeMemberRecordsData;
	
	@FindBy(xpath = "//button[contains(@class,'action-bar-action-searchTable reportAction')]")WebElement searchButton;
	
	@FindBy(xpath = "//input[@placeholder='Search report table...']")WebElement enterDataInSearch;
	
	@FindBy(xpath = "//input[@placeholder='Search report table...']/following::div[@class='num-of-results']/p") WebElement searchResult;
	
	@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr//td[contains(@style,'background-color')]")WebElement backGroundColorElement;
	
	
	public void clickActiveMemberRosterTab() throws Throwable {
		Utility.waitForWebElement(driver, activeMemberRoasterTab, 0);
		activeMemberRoasterTab.click();
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
	}
	
	public void clickExportButton() throws Throwable {
		Utility.waitForWebElement(driver, exportButtonInActiveMem, 0);
		exportButtonInActiveMem.click();
		Thread.sleep(5000);
		driver.switchTo().defaultContent();
	}
	
	public void selectExportView(String option) throws Throwable {
		switch (option) {
		case "Formatted":
			Thread.sleep(7000);
			WebElement formatElem = driver.findElement(By.cssSelector("label[for='formatted-export']"));
			Assert.assertTrue(formatElem.isDisplayed());
			formatElem.click();
			Assert.assertFalse(formatDropDown.isEnabled());
			log.info("format drop down is disabled");
			break;
		case "DetailsOnly":
			WebElement detilsElem = driver.findElement(By.cssSelector("label[for='data-export']"));	
			detilsElem.click();
			Assert.assertTrue(formatDropDown.isEnabled());
		}
	}
	
	public void clickExportButtonInexportPopup(String option) throws Throwable {
		switch (option) {
		case "Export":
		WebElement exportEle =	driver.findElement(By.xpath("//div[@class='modal-footer slds-modal__footer']//button[@title='"+option+"']"));
		exportEle.click();
		Thread.sleep(8000);
			break;
		case "Cancel":
			//TODO:
		}
	}
	
	public String getActiveMemberRosterRecordsCount() {
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, activeMemberRoasterFrame);
		Utility.waitForWebElement(driver, activeMemberRoasterRecords, 0);
		String recordsCount = activeMemberRoasterRecords.getText();
		String count = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		return count;
	}
	
	public void getActiveMembersRecordsData() throws Throwable {
		Thread.sleep(8000);
		System.out.println(activeMemberRecordsData.size());
		for (int i = 0; i < activeMemberRecordsData.size(); i++) {
		String recordsData = activeMemberRecordsData.get(i).getText();
		System.out.println(recordsData.replace("\n", ""));
		log.info(recordsData.replace("\n", ""));
		System.out.println("records count"+i);
		}
	}
	
	public void validateDownloafFileDataAndApplicationdata(String uiActiveMemRecordCount) {
		excelDataProvider = new ExcelDataProvider("Portal Active Member Roster");
		String recordCount = excelDataProvider.getCellData("Portal Active Member Roster", 69, 3);
		double value = Double.parseDouble(recordCount);
		double uiValue = Double.parseDouble(uiActiveMemRecordCount);
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
	    String formattedRecordCount = decimalFormat.format(value);
	    String uiRecordCount = decimalFormat.format(uiValue);
		System.out.println(formattedRecordCount);
		Assert.assertEquals(formattedRecordCount,uiRecordCount, "Count not matched");
	}
	
	public void SearchButtonInActiveMemberRoaster() throws InterruptedException {
		excelDataProvider = new ExcelDataProvider("Portal Active Member Roster");
		String aiaNumber = excelDataProvider.getCellData("Portal Active Member Roster", 17, 1);
		String backGroundColor = "rgb(255, 240, 63);";
		Thread.sleep(7000);
		util.switchToFrameUsingWebElement(driver, activeMemberRoasterFrame);
		util.waitUntilElement(driver, searchButton);
		searchButton.click();
		System.out.println(aiaNumber);
		Thread.sleep(4000);
		enterDataInSearch.sendKeys(aiaNumber);
		System.out.println(searchResult.getText());
		Thread.sleep(2000);
		String bgcolor = backGroundColorElement.getAttribute("style");
		System.out.println(bgcolor.replace("background-color: ", ""));
		Assert.assertEquals(bgcolor.replace("background-color: ", ""), backGroundColor);
	}

}

