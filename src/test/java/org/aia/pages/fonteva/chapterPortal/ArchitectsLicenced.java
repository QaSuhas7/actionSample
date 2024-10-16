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
public class ArchitectsLicenced {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(ArchitectsLicenced.class);

	public ArchitectsLicenced(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	
	
	@FindBy(xpath = "//button[text()='Architects Licensed =/<10 years']") WebElement architectsLicensedTenyearsTab;
	
    @FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement architectsLicensedTenyearsFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement architectsLicensedTenyearsRecords;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> architectsLicensedTenyearsRecordsData;
	
	
	public void clickArchitectsLicensedTenyearsTab() throws Throwable {
		architectsLicensedTenyearsTab.click();
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
	}
	
	public void getArchitectsLicensedTenyearsRecordsCount() throws InterruptedException {
		Thread.sleep(4000);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, architectsLicensedTenyearsFrame);
		Utility.waitForWebElement(driver, architectsLicensedTenyearsRecords, 0);
		String recordsCount = architectsLicensedTenyearsRecords.getText();
		System.out.println(recordsCount);
	}
	
	public void getArchitectsLicensedTenyearsRecordsData() throws Throwable {
		Thread.sleep(8000);
		System.out.println(architectsLicensedTenyearsRecordsData.size());
		for (int i = 0; i < architectsLicensedTenyearsRecordsData.size(); i++) {
		String recordsData = architectsLicensedTenyearsRecordsData.get(i).getText();
		System.out.println(recordsData.replace("\n", ""));
		log.info(recordsData.replace("\n", ""));
		System.out.println("records count"+i);
		}
		util.switchToTabs(driver, 0);
		driver.switchTo().defaultContent();
	}

}

