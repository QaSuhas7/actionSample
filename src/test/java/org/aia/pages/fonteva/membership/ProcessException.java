package org.aia.pages.fonteva.membership;

import static org.testng.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class ProcessException {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;

	public ProcessException(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	String contact = "//span[text()='%s']//ancestor::a";

	String exceptionContact = "(//slot[text()='%s']/ancestor::a)[2]";

	@FindBy(xpath = "//a[contains(@href,'Processing_Exceptions__r')]")
	WebElement processExceptionTab;

	@FindBy(xpath = "//button[@name='New']")
	WebElement newBtn;

	@FindBy(xpath = "//button[contains(@aria-label,'Activity')]")
	WebElement activityDrp;

	String activityOpt = "//span[contains(@title,'%s')]";

	@FindBy(xpath = "//textarea")
	WebElement noteInput;

	@FindBy(xpath = "//button[contains(@aria-label,'Reason')]")
	WebElement reasonDrp;

	String reasonOpt = "//span[text()='%s']";

	@FindBy(xpath = "//button[contains(@aria-label,'Initial ')]")
	WebElement initialReachOutDrp;

	String initialReachOutOpt = "//span[text()='%s']";

	@FindBy(xpath = "//button[contains(@aria-label,'Status')]")
	WebElement statusDrp;

	String statusOpt = "//div[@aria-label='Status']//lightning-base-combobox-item//span[@title='%s']";
			//"//span[text()='%s']";

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	@FindBy(xpath = "//table[@aria-label='Processing Exceptions']")
	WebElement exceptionTable;

	@FindBy(xpath = "//table[@aria-label='Processing Exceptions']//tbody//tr[1]")
	WebElement existingExceptionTable;

	@FindBy(xpath = "//button[@title='Close this window']")
	WebElement closeTheWindow;

	@FindBy(xpath = "//table[@aria-label='Processing Exceptions']//tbody//tr[2]")
	WebElement cloneExceptionTable;

	@FindBy(xpath = "//table[@aria-label]//tbody//td[3]//lst-formatted-text")
	WebElement activityText;

	@FindBy(xpath = "//table[@aria-label]//tbody//td[4]//lst-formatted-text")
	WebElement initialReachOutText;

	@FindBy(xpath = "//table[@aria-label]//tbody//td[5]//lst-formatted-text")
	WebElement reasonText;

	@FindBy(xpath = "//table[@aria-label]//tbody//td[6]//lightning-base-formatted-text")
	WebElement noteText;

	@FindBy(xpath = "//h2[text()='New Processing Exception']")
	WebElement heading;

	String contactField = "//input[@placeholder='%s']";

	String dialogBoxDrpOpt = "//span[contains(@class,'media__body')]/span";

	//@FindBy(xpath = "(//table[@aria-label='Processing Exceptions']//tr)[2]/th/span/a")
	@FindBy(xpath = "(//table[@aria-label='Processing Exceptions']//tr)[2]/th//a")
	//@FindBy(xpath = "(//table[@aria-label='Processing Exceptions']//tr)[2]/th/span/a")
	WebElement processExceptionId;

	@FindBy(xpath = "(//button[text()='Edit'])[2]")
	WebElement editBtn;

	@FindBy(xpath = "(//slot//lightning-formatted-text[@slot='primaryField'])[2]")
	WebElement exceptionId;

	@FindBy(xpath = "//div[@class='actionBody']//h2")
	WebElement PopUpheading;

	@FindBy(xpath = "//button[text()='Clone']")
	WebElement cloneBtn;

	@FindBy(xpath = "(//a[text()='Related'])[2]")
	WebElement relatedTab;


//	@FindBy(xpath = "//a[@title='Upload Files']")

	@FindBy(xpath = "//input[@name='fileInput']")
	WebElement fileUpload;

	@FindBy(xpath = "//h1[text()='Upload Files']")
	WebElement uploadPopUp;

	@FindBy(xpath = "(//span[text()='1 of 1 file uploaded'])")
	WebElement isfileUploaded;

	@FindBy(xpath = "//span[text()='Done']//parent::button")
	WebElement doneBtn;

	@FindBy(xpath = "//ul[@class='uiAbstractList']/li")
	WebElement fileVisible;

	@FindBy(xpath = "//lightning-icon[@icon-name='utility:success']")
	WebElement successIcon;

	static ArrayList<String> valueList = new ArrayList<String>();

	/**
	 * @param fullName
	 * @param reasonOption
	 * @param intitialReachOutOption
	 * @param statusOption
	 * @throws InterruptedException
	 */
	public void createNewProcessException(String fullName, String activityOption, String enterNote, String reasonOption,
			String intitialReachOutOption, String statusOption) throws InterruptedException {
//		Thread.sleep(70000);
//		WebElement selectContact = util.getCustomizedWebElement(driver, contact, fullName);
//		executor.executeScript("arguments[0].click();", selectContact);
//		Thread.sleep(2000);
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.ARROW_UP).build().perform();
		actions.keyUp(Keys.ARROW_UP).build().perform();
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_DOWN).build().perform();
		util.waitUntilElement(driver, processExceptionTab);
		util.clickUsingJS(driver, processExceptionTab);
//		processExceptionTab.click();
		util.waitUntilElement(driver, newBtn);
		util.clickUsingJS(driver, newBtn);
//		newBtn.click();
		util.waitUntilElement(driver, heading);
		assertTrue(heading.isDisplayed());
		util.waitUntilElement(driver, activityDrp);
		util.clickUsingJS(driver, activityDrp);
//		activityDrp.click();
		WebElement  activityOpt =  driver.findElement(By.xpath("//span[contains(@title,'"+activityOption+"')]"));
		activityOpt.click();
		//util.getCustomizedWebElement(driver, activityOpt, activityOption).click();
		util.enterText(driver, noteInput, enterNote);
		util.waitUntilElement(driver, reasonDrp);
		reasonDrp.click();
		WebElement  reasonOpt =  driver.findElement(By.xpath("//span[contains(@title,'"+reasonOption+"')]"));
		reasonOpt.click();
//		util.getCustomizedWebElement(driver, reasonOpt, reasonOption).click();
		util.waitUntilElement(driver, initialReachOutDrp);
		initialReachOutDrp.click();
		WebElement  initialReachOutOpt =  driver.findElement(By.xpath("//span[contains(@title,'"+intitialReachOutOption+"')]"));
		initialReachOutOpt.click();
//		util.getCustomizedWebElement(driver, initialReachOutOpt, intitialReachOutOption).click();
		Thread.sleep(3000);
		util.scrollingElementUsingJS(driver, statusDrp);
		util.waitUntilElement(driver, statusDrp);
		statusDrp.click();
		Thread.sleep(3000);
		WebElement statusOpt =  driver.findElement(By.xpath("//span[text()='"+statusOption+"']"));
		statusOpt.click();
		//util.getCustomizedWebElement(driver, statusOpt, statusOption).click();
		saveBtn.click();
	}

	/**
	 * @param activity
	 * @param reason
	 * @param initialReach
	 * @param status
	 */
	public void validateProcessException(String activity, String reason, String initialReach, String note) {
		util.waitUntilElement(driver, exceptionTable);
		assertTrue(exceptionTable.isDisplayed());
		util.waitUntilElement(driver, activityText);
		assertEquals(activityText.getText(), activity);
		assertEquals(reasonText.getText(), reason);
		assertEquals(initialReachOutText.getText(), initialReach);
		assertEquals(noteText.getText(), note);
		valueList.add(0, activityText.getText());
		valueList.add(1, reasonText.getText());
		valueList.add(2, initialReachOutText.getText());
		valueList.add(3, noteText.getText());

	}

	/**
	 * @param activityOption
	 * @param enterNote
	 * @param reasonOption
	 * @param intitialReachOutOption
	 * @param statusOption
	 * @throws InterruptedException 
	 */
	public void editProcessException(String activityOption, String enterNote, String reasonOption,
			String intitialReachOutOption, String statusOption) throws InterruptedException {
		util.waitUntilElement(driver, processExceptionId);
		executor.executeScript("arguments[0].click();", processExceptionId);
		//processExceptionId.click();
		util.waitUntilElement(driver, editBtn);
		editBtn.click();
		util.waitUntilElement(driver, PopUpheading);
		assertTrue(PopUpheading.getText().contains(exceptionId.getText()));
		util.waitUntilElement(driver, activityDrp);
		activityDrp.click();
		util.getCustomizedWebElement(driver, activityOpt, activityOption).click();
		util.enterText(driver, noteInput, enterNote);
		util.waitUntilElement(driver, reasonDrp);
		reasonDrp.click();
		util.getCustomizedWebElement(driver, reasonOpt, reasonOption).click();
		util.waitUntilElement(driver, initialReachOutDrp);
		initialReachOutDrp.click();
		util.getCustomizedWebElement(driver, initialReachOutOpt, intitialReachOutOption).click();
		Thread.sleep(2000);
		util.scrollingElementUsingJS(driver, statusDrp);
		util.clickUsingJS(driver, statusDrp);
		statusOption = "Unresolved/closed";
		util.getCustomizedWebElement(driver, statusOpt, statusOption).click();
		saveBtn.click();
	}

	public void validateEditedProcessException(String editactivity, String editreason, String editinitialReach,
			String editnote) {
		assertNotEquals(valueList.get(0), editactivity);
		assertNotEquals(valueList.get(1), editreason);
		assertNotEquals(valueList.get(2), editinitialReach);
		assertNotEquals(valueList.get(3), editnote);
	}

	/**
	 * @param fullName
	 * @throws InterruptedException
	 */
	public void cloneExistingProcessException(String fullName) throws InterruptedException {
		util.waitUntilElement(driver, processExceptionId);
		executor.executeScript("arguments[0].click();", processExceptionId);
		//processExceptionId.click();
		util.waitUntilElement(driver, cloneBtn);
		cloneBtn.click();
		util.waitUntilElement(driver, heading);
		assertTrue(heading.isDisplayed());
		saveBtn.click();
		Thread.sleep(60000);
		executor.executeScript("arguments[0].click();",
				util.getCustomizedWebElement(driver, exceptionContact, fullName));
		util.waitUntilElement(driver, processExceptionTab);
		processExceptionTab.click();
		util.waitUntilElement(driver, existingExceptionTable);
		assertTrue(existingExceptionTable.isDisplayed());
		util.waitUntilElement(driver, cloneExceptionTable);
		assertTrue(cloneExceptionTable.isDisplayed());
	}

	/**
	 * We upload pdf file in exception
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void attachFile() throws InterruptedException, IOException {
		//processExceptionId.click();
		executor.executeScript("arguments[0].click();", processExceptionId);
		util.waitUntilElement(driver, relatedTab);
		relatedTab.click();
		System.out.println("Related tab is clicked sucessfully");
		String filePath = System.getProperty("user.dir") + File.separator + "UploadFiles" + File.separator + "FileAIA.pdf";
		Thread.sleep(5000);
		executor.executeScript("arguments[0].style.display = 'block';", fileUpload);
		util.waitUntilElement(driver, fileUpload);
		assertTrue(fileUpload.isDisplayed());
		fileUpload.sendKeys(filePath);
		//fileUpload.click();
		//fileUpload.sendKeys(filePath);
//		try {
//			Robot robot = new Robot();
//			String filePath = System.getProperty("user.dir") + File.separator + "UploadFiles" + File.separator + "FileAIA.pdf";
//			System.out.println("My path name is:" + filePath);
//			StringSelection strSelection = new StringSelection(filePath);
//			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(strSelection, null);
//			Thread.sleep(3000);
//			robot.keyPress(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_V);
//			robot.keyRelease(KeyEvent.VK_V);
//			Thread.sleep(3000);
//			robot.keyRelease(KeyEvent.VK_CONTROL);
//			robot.keyPress(KeyEvent.VK_ENTER);
//			robot.keyRelease(KeyEvent.VK_ENTER);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("File Path is incorrect or file is not in directory");
//		}
	}

	/**
	 * Validate File is uploaded
	 * @throws InterruptedException 
	 */
	public void validateFileUpload() throws InterruptedException {
		Thread.sleep(5000);
		util.waitUntilElement(driver, uploadPopUp);
		assertTrue(uploadPopUp.isDisplayed());
		util.waitUntilElement(driver, successIcon);
		assertEquals(isfileUploaded.getText(), data.testDataProvider().getProperty("fileUpload"));
		doneBtn.click();
	}

	/**
	 * @param fullName
	 * @throws InterruptedException
	 */
	public void validateProcessExceptionFlow(String fullName) throws InterruptedException {
//		Thread.sleep(60000);
//		WebElement selectContact = util.getCustomizedWebElement(driver, contact, fullName);
//		executor.executeScript("arguments[0].click();", selectContact);
		util.waitUntilElement(driver, processExceptionTab);
		util.clickUsingJS(driver, processExceptionTab);
//		processExceptionTab.click();
		util.waitUntilElement(driver, newBtn);
		newBtn.click();
		util.waitUntilElement(driver, heading);
		assertTrue(heading.isDisplayed());
		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, contactField, fullName));
		assertTrue(util.getCustomizedWebElement(driver, contactField, fullName).isDisplayed());
		util.waitUntilElement(driver, activityDrp);
		util.clickUsingJS(driver, activityDrp);
		ArrayList<String> activityTextList = new ArrayList<String>();
		List<WebElement> activityList = driver.findElements(By.xpath("//div[@aria-label='Activity']//span[@class='slds-media__body']"));
		for (WebElement drpList : activityList) {
			drpList.getText();
			activityTextList.add(drpList.getText());
		}
		System.out.println(activityTextList.toString());
		System.out.println(data.testDataProvider().getProperty("validateActivityDrp"));
		assertEquals(activityTextList.toString(), data.testDataProvider().getProperty("validateActivityDrp"));
		util.waitUntilElement(driver, reasonDrp);
		util.clickUsingJS(driver, reasonDrp);
		ArrayList<String> resonTextList = new ArrayList<String>();
		List<WebElement> reasonList = driver.findElements(By.xpath("//div[@aria-label='Reason']//span[@class='slds-media__body']"));
		for (WebElement drpList : reasonList) {
			drpList.getText();
			resonTextList.add(drpList.getText());
		}
		System.out.println(resonTextList.toString());
		System.out.println( data.testDataProvider().getProperty("validateResonDrp"));
		assertEquals(resonTextList.toString(), data.testDataProvider().getProperty("validateResonDrp"));
		util.waitUntilElement(driver, heading);
		heading.click();
		util.waitUntilElement(driver, initialReachOutDrp);
		initialReachOutDrp.click();
		ArrayList<String> initialReachTextList = new ArrayList<String>();
		List<WebElement> initialReachList = driver.findElements(By.xpath("//div[@aria-label='Initial Reach Out']//span[@class='slds-media__body']"));
		for (WebElement drpList : initialReachList) {
			drpList.getText();
			initialReachTextList.add(drpList.getText());
		}
		assertEquals(initialReachTextList.toString(), data.testDataProvider().getProperty("validateInitialReachDrp"));
		util.waitUntilElement(driver, heading);
		heading.click();
		util.scrollingElementUsingJS(driver, statusDrp);
		util.waitUntilElement(driver, statusDrp);
		statusDrp.click();
		ArrayList<String> statusTextList = new ArrayList<String>();
		List<WebElement> statusList = driver.findElements(By.xpath("//div[@aria-label='Status']//lightning-base-combobox-item//span[@class='slds-media__body']"));
		for (WebElement drpList : statusList) {
			drpList.getText();
			statusTextList.add(drpList.getText());
		}
		System.out.println(statusTextList);
		System.out.println(data.testDataProvider().getProperty("validateStatusDrp")); 
		assertEquals(statusTextList.toString().trim(), data.testDataProvider().getProperty("validateStatusDrp").trim());
	}

}
