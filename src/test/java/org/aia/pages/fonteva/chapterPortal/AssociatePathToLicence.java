/**
 * 
 */
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

/**
 * 
 */
public class AssociatePathToLicence {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(AssociatePathToLicence.class);

	public AssociatePathToLicence(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	
    @FindBy(xpath = "//button[text()='Associates / Path to Licensure']") WebElement associatesPathtoLicensureTab;
	
    @FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement associatesPathtoLicensureFrame;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement associatesPathtoLicensureRecords;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement> associatesPathtoLicensureRecordsData;
	
	public void clickAssociatesPathtoLicensureTab() throws Throwable {
		associatesPathtoLicensureTab.click();
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
	}
	
	public void getAssociatesPathtoLicensureRecordsCount() throws InterruptedException {
		Thread.sleep(4000);
		util.waitForJavascript(driver, 10000, 2000);
		util.switchToFrameUsingWebElement(driver, associatesPathtoLicensureFrame);
		Utility.waitForWebElement(driver, associatesPathtoLicensureRecords, 0);
		String recordsCount = associatesPathtoLicensureRecords.getText();
		System.out.println(recordsCount);
	}
	
	public void getAssociatesPathtoLicensureRecordsData() throws Throwable {
		Thread.sleep(8000);
		System.out.println(associatesPathtoLicensureRecordsData.size());
		for (int i = 0; i < associatesPathtoLicensureRecordsData.size(); i++) {
		String recordsData = associatesPathtoLicensureRecordsData.get(i).getText();
		System.out.println(recordsData.replace("\n", ""));
		log.info(recordsData.replace("\n", ""));
		System.out.println("records count"+i);
		}
		util.switchToTabs(driver, 0);
		driver.switchTo().defaultContent();
	}

}
