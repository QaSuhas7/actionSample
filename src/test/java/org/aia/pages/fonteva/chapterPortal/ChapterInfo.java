package org.aia.pages.fonteva.chapterPortal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.aia.pages.fonteva.events.EventConfig;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import groovy.transform.Final;

public class ChapterInfo {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(ChapterInfo.class);

	public ChapterInfo(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Chapter Info']") WebElement chapterInfoTab;
	// 
	@FindAll(value = {@FindBy(xpath  = "//table[@data-aura-class='uiVirtualDataTable' and @aria-label='Leadership']/tbody//tr")}) List<WebElement> leaderhipTableDataInChatpterInfo;
	
	@FindAll(value = {@FindBy(xpath  = "//table[@data-aura-class='uiVirtualDataTable' and @aria-label='Service Area Contacts']/tbody//tr")}) List<WebElement> serviceAreaContactsTableDataInChatpterInfo;
	
	@FindAll(value = {@FindBy(xpath  = "//article[@class='slds-card']")}) List<WebElement> detailsStaffInfoInChatpterInfo;
	
	@FindBy(xpath = "//input[@name='fileInput']") WebElement uploadFileInChapterInfo; 
	
	@FindBy(xpath = "//div[contains(@id,'toastDescription')]/span")WebElement uploadFileToastMsgInNotesAndAttachments;
	
	//@FindBy(xpath = "//div[@title='Upload Files']/parent::a") WebElement uploadFileInChapterInfo;
	
	@FindBy(xpath = "//div[@title='screenshoot.jpg']/parent::div") WebElement uploadFileDescription;
	
	@FindBy(xpath = "//span[@class='slds-progress-bar__value']/span") WebElement uploadFileProgress;
	
	@FindBy(xpath  = "//lightning-icon[@icon-name='utility:success']/parent::div") WebElement uploadSuccessImg;
	
	@FindBy(xpath = "//button[contains(@class,'ok desktop uiButton')]/span[text()='Done']") WebElement doneButtonInUploadFilePopup;
	
	@FindBy(xpath = "//img[contains(@src,'renditionDownload')]") WebElement fileAddedInNotesAndAttachments;
	
	@FindBy(xpath = "//img[@alt='screenshoot']")WebElement fileOnMouseOver;
	
	@FindBy(xpath = "//a[@title='Show More']") WebElement showMoreElementInNotesAndAttchments;
	
	@FindBy(xpath = "//div[@class='modal-header slds-modal__header']/h2") WebElement headerInDeletePopup;
	
	@FindBy(xpath = "//div[@class='detail slds-text-align--center']") WebElement deleteTextInDeletepopup;

	public void clickChapterInfoTab() {
		Utility.waitForWebElement(driver, chapterInfoTab, 0);
		Assert.assertTrue(chapterInfoTab.isDisplayed());
		chapterInfoTab.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void getdetailsStaffInChapterInfo() throws Throwable {
		Thread.sleep(3000);
		ArrayList<String> detailsStaffInfo = new ArrayList<String>();
		for (int i = 0; i < detailsStaffInfoInChatpterInfo.size(); i++) {
			 detailsStaffInfo.add(detailsStaffInfoInChatpterInfo.get(i).getText());
			 Assert.assertTrue(detailsStaffInfoInChatpterInfo.get(i).isDisplayed());
			System.out.println(detailsStaffInfo);
		}
	}
	
	public void getLeaderShipTabledataInChapterInfo() throws Throwable {
		Thread.sleep(3000);
		ArrayList<String> leaderShipDataInfo = new ArrayList<String>();
		for (int i = 0; i < leaderhipTableDataInChatpterInfo.size(); i++) {
			leaderShipDataInfo.add(leaderhipTableDataInChatpterInfo.get(i).getText());
			Assert.assertTrue(leaderhipTableDataInChatpterInfo.get(i).isDisplayed());
		}
		System.out.println(leaderShipDataInfo.size());
	}
	
	public void getServiceAreaContactsInChapterINfo() throws Throwable {
		Thread.sleep(3000);
		ArrayList<String> serviceAreaContacsInfo = new ArrayList<String>();
		for (int i = 0; i < serviceAreaContactsTableDataInChatpterInfo.size(); i++) {
			serviceAreaContacsInfo.add(serviceAreaContactsTableDataInChatpterInfo.get(i).getText());
			Assert.assertTrue(serviceAreaContactsTableDataInChatpterInfo.get(i).isDisplayed());
			System.out.println(serviceAreaContacsInfo);
		}
	}
	
	public void fileUploadInChapterInfo() {
			util.scrollingBottomOfPage(driver);
			util.waitUntilElement(driver, uploadFileInChapterInfo);
			uploadFileInChapterInfo.sendKeys(EventConfig.venueImageURLInput);
	}
	
	public void validateUploadFilePopup() throws Throwable {
		Thread.sleep(5000);
		System.out.println(uploadFileDescription.getText()); 
		util.waitUntilElement(driver, uploadFileProgress);
		System.out.println(uploadFileProgress.getText());
		Assert.assertTrue(uploadSuccessImg.isDisplayed()); 
		Thread.sleep(4000);
		util.waitUntilElement(driver, doneButtonInUploadFilePopup);
		doneButtonInUploadFilePopup.click();
		Thread.sleep(2300);
		System.out.println(uploadFileToastMsgInNotesAndAttachments.getText());
	}
	
	public void deleteFileInNotesAndAttachments(String option) throws Throwable {
		Thread.sleep(8000);
		if (fileAddedInNotesAndAttachments.isDisplayed()) {
			fileAddedInNotesAndAttachments.click();
			Thread.sleep(5000);
			util.mouseOverToElement(driver, fileOnMouseOver);
			showMoreElementInNotesAndAttchments.click();
			WebElement actionEle = driver.findElement(
					By.xpath("//li//a[@title='" + option + "']//div[@class='forceContentPreviewerAction']"));
			actionEle.click();
			Thread.sleep(1000);
			System.out.println(headerInDeletePopup.getText());
			System.out.println(deleteTextInDeletepopup.getText());
			Thread.sleep(500);
			WebElement actionElem2 = driver.findElement(
					By.xpath("//div[contains(@class,'forceModalActionContainer')]//button[@title='Delete']"));
			actionElem2.click();
		}
	}
	
}
