package org.aia.pages.fonteva.membership;

import static org.testng.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * @author IM-RT-LP-1483 (Suhas)
 *
 */
public class MemberValueOutreach {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;
	final static ArrayList<String> mvoRightTextList = new ArrayList<String>();
	final static ArrayList<String> getMVO = new ArrayList<String>();
	final static ArrayList<String> getEditMVO = new ArrayList<String>();
	final static ArrayList<String> getCloneMVO = new ArrayList<String>();

	/**
	 * @param Idriver
	 */
	public MemberValueOutreach(WebDriver Idriver) {
		driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
	}

	@FindBy(xpath = "//div[contains(@class,'modal__header')]//h2")
	WebElement mvoPopupHeading;

	String getMVOFields = "//lightning-combobox//label";

	String moveDrpSelection = "//span[contains(@class,'media__body')]//span[text()='%s']";

	@FindBy(xpath = "//label[text()='Contact']")
	WebElement contactDrpLable;

	@FindBy(xpath = "//button[contains(@aria-label,'Membership Year')]")
	WebElement membershipYearDrp;

	@FindBy(xpath = "//button[contains(@aria-label,'Round ')]")
	WebElement roundDrp;

	@FindBy(xpath = "(//button[contains(@aria-label,'Call Category')])[1]")
	WebElement contactCatogaryDrp;

	@FindBy(xpath = "(//button[contains(@aria-label,'Call Outcome')])[1]")
	WebElement callOutComeDrp;

	@FindBy(xpath = "//textarea")
	WebElement generalNotes;

	@FindBy(xpath = "//button[@title='Edit Membership Year']")
	WebElement membershipeditBtn;

	@FindBy(xpath = "//button[text()='Save']")
	WebElement saveBtn;

	@FindBy(xpath = "//table//tbody//tr//th//a")
	WebElement mvoId;

	@FindBy(xpath = "(//button[text()='Edit'])[2]")
	WebElement editMVOBtn;

	@FindBy(xpath = "//h2[contains(text(),'Edit MVO')]")
	WebElement editMVOPopup;

	@FindBy(xpath = "//button[contains(@aria-label,'Membership ')]//span")
	WebElement membershipYearText;
	
	@FindBy(xpath = "//button[contains(@aria-label,'Round')]//span")
	WebElement roundText;

	@FindBy(xpath = "//button[contains(@aria-label,'Category')]//span")
	WebElement callCatogaryText;

	@FindBy(xpath = "//button[contains(@aria-label,'Call Outcome')]//span")
	WebElement callOutComeText;

	@FindBy(xpath = "//*[contains(@field-label,'Membership Year')]")
	WebElement existingMVOpopUp;

	@FindBy(xpath = "//button[text()='Delete']//ancestor::li//following-sibling::li//button")
	WebElement cloneDrpBtn;

	//@FindBy(xpath = "//span[text()='Clone']//parent::a")
	@FindBy(xpath = "//button[@name='Clone']")
	WebElement cloneBtn;

	@FindBy(xpath = "//div[@class='actionBody']")
	WebElement cloneMVOpopUp;

	/**
	 * @param round
	 * @param contactCatogary
	 * @param callOutCome
	 * @param generalNote
	 * @throws InterruptedException 
	 * 
	 */
	public void createNewMVO(String membershipYear, String round, String contactCatogary, String callOutCome,
			String generalNote) throws InterruptedException {
		util.waitUntilElement(driver, mvoPopupHeading);
		assertTrue(mvoPopupHeading.isDisplayed());
		assertEquals(mvoPopupHeading.getText(), data.testDataProvider().getProperty("movPopUp"));
		List<WebElement> mvoRightList = driver.findElements(By.xpath(getMVOFields));
		for (WebElement fieldList : mvoRightList) {
			mvoRightTextList.add(fieldList.getText());
		}
		assertTrue(contactDrpLable.isDisplayed());
		assertEquals(mvoRightTextList.toString(), data.testDataProvider().getProperty("movPopUpFields"));
		membershipYearDrp.click();
		util.getCustomizedWebElement(driver, moveDrpSelection, membershipYear).click();
		util.waitUntilElement(driver, roundDrp);
		roundDrp.click();
		util.getCustomizedWebElement(driver, moveDrpSelection, round).click();
		util.waitUntilElement(driver, contactCatogaryDrp);
		contactCatogaryDrp.click();
		util.clickUsingJS(driver, util.getCustomizedWebElement(driver, moveDrpSelection, contactCatogary));
//		util.getCustomizedWebElement(driver, moveDrpSelection, contactCatogary).click();
		Thread.sleep(2000);
		util.scrollingElementUsingJS(driver, callOutComeDrp);
		util.waitUntilElement(driver, callOutComeDrp);
	    util.clickUsingJS(driver, callOutComeDrp);
		WebElement moveDrpSelection = driver.findElement(By.xpath("//span[contains(@class,'media__body')]//span[text()='"+callOutCome+"']"));
		moveDrpSelection.click();
//		util.getCustomizedWebElement(driver, moveDrpSelection, callOutCome).click();
		util.enterText(driver, generalNotes, generalNote);
		saveBtn.click();
		mvoRightTextList.clear();
	}

	/**
	 * 
	 */
	public void getNewMVOText() {
		util.waitUntilElement(driver, mvoId);
		executor.executeScript("arguments[0].click();", mvoId);
		util.waitUntilElement(driver, membershipeditBtn);
		membershipeditBtn.click();
		getMVO.add(membershipYearText.getText());
		getMVO.add(roundText.getText());
		getMVO.add(callCatogaryText.getText());
		getMVO.add(callOutComeText.getText());
		getMVO.add(generalNotes.getText());
		util.waitUntilElement(driver, saveBtn);
		saveBtn.click();
	}

	/**
	 * @param membershipYear
	 * @param round
	 * @param contactCatogary
	 * @param callOutCome
	 * @param generalNote
	 * @throws InterruptedException 
	 * 
	 */
	public void editExistingMVO(String membershipYear, String round, String contactCatogary, String callOutCome,
			String generalNote) throws InterruptedException {
		// util.waitUntilElement(driver, mvoId);
		// executor.executeScript("arguments[0].click();", mvoId);
		util.waitUntilElement(driver, editMVOBtn);
		editMVOBtn.click();
		util.waitUntilElement(driver, editMVOPopup);
		assertTrue(editMVOPopup.isDisplayed());
		membershipYearDrp.click();
		util.getCustomizedWebElement(driver, moveDrpSelection, membershipYear).click();
		util.waitUntilElement(driver, roundDrp);
		roundDrp.click();
		util.getCustomizedWebElement(driver, moveDrpSelection, round).click();
		util.waitUntilElement(driver, contactCatogaryDrp);
		contactCatogaryDrp.click();
		WebElement moveDrpSelectionContactCatogary = driver.findElement(By.xpath("//span[contains(@class,'media__body')]//span[text()='"+contactCatogary+"']"));
		util.clickUsingJS(driver, moveDrpSelectionContactCatogary);
//		util.getCustomizedWebElement(driver, moveDrpSelection, contactCatogary).click();
		Thread.sleep(2000);
		util.scrollingElementUsingJS(driver, callOutComeDrp);
		util.waitUntilElement(driver, callOutComeDrp);
		callOutComeDrp.click();
		WebElement moveDrpSelectionCallOutCome = driver.findElement(By.xpath("//span[contains(@class,'media__body')]//span[text()='"+callOutCome+"']"));
		util.clickUsingJS(driver, moveDrpSelectionCallOutCome);
//		util.getCustomizedWebElement(driver, moveDrpSelection, callOutCome).click();
		util.enterText(driver, generalNotes, generalNote);
		saveBtn.click();
	}

	/**
	 */
	public void getEditMVOText() {
		util.waitUntilElement(driver, membershipeditBtn);
		membershipeditBtn.click();
		getEditMVO.add(membershipYearText.getText());
		getEditMVO.add(roundText.getText());
		getEditMVO.add(callCatogaryText.getText());
		getEditMVO.add(callOutComeText.getText());
		getEditMVO.add(generalNotes.getText());
	}

	/**
	 * 
	 */
	public void validateExistingMVO() {
		System.out.println(getMVO.toString());
		System.out.println(getEditMVO.toString());
		assertNotEquals(getMVO.toString(), getEditMVO.toString());
	}

	public void cloneExistingMVO(String membershipYear, String round, String contactCatogary, String callOutCome,
			String generalNote) {
		//util.waitUntilElement(driver, mvoId);
		//executor.executeScript("arguments[0].click();", mvoId);
//		assertTrue(existingMVOpopUp.isDisplayed());
//		util.waitUntilElement(driver, cloneDrpBtn);
//		cloneDrpBtn.click();
		util.waitUntilElement(driver, cloneBtn);
		cloneBtn.click();
		util.waitUntilElement(driver, cloneMVOpopUp);
		cloneMVOpopUp.click();
		Utility.waitForWebElement(driver, membershipYearDrp, 10);
		util.clickUsingJS(driver, membershipYearDrp);
		util.getCustomizedWebElement(driver, moveDrpSelection, membershipYear).click();
		util.waitUntilElement(driver, roundDrp);
		roundDrp.click();
		util.getCustomizedWebElement(driver, moveDrpSelection, round).click();
		util.waitUntilElement(driver, contactCatogaryDrp);
		contactCatogaryDrp.click();
		util.getCustomizedWebElement(driver, moveDrpSelection, contactCatogary).click();
		util.scrollingElementUsingJS(driver, callOutComeDrp);
		util.waitUntilElement(driver, callOutComeDrp);
		util.clickUsingJS(driver, callOutComeDrp);
		util.getCustomizedWebElement(driver, moveDrpSelection, callOutCome).click();
		util.enterText(driver, generalNotes, generalNote);
		saveBtn.click();
	}

	/**
	 * @throws InterruptedException 
	 * 
	 */
	public void getCloneMVOText() throws InterruptedException {
//		util.waitUntilElement(driver, mvoId);
//		executor.executeScript("arguments[0].click();", mvoId);
//		util.waitUntilElement(driver, membershipeditBtn);
//		membershipeditBtn.click();
		getCloneMVO.clear();
		getCloneMVO.add(driver.findElement(By.xpath("(//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[contains(@field-label,'Membership Year')]//dd//span)[1]")).getText());
		getCloneMVO.add(driver.findElement(By.xpath("(//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[contains(@field-label,'Round #')]//dd//span)[1]")).getText());
		getCloneMVO.add(driver.findElement(By.xpath("(//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[contains(@field-label,'Call Category')]//dd//span)[1]")).getText());
		getCloneMVO.add(driver.findElement(By.xpath("(//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[contains(@field-label,'Call Outcome')]//dd//span)[1]")).getText());
		Thread.sleep(4000);
		getCloneMVO.add(driver.findElement(By.xpath("(//div[@class='windowViewMode-normal oneContent active lafPageHost']//records-record-layout-item[contains(@field-label,'General Notes')]//dd//span)[1]")).getText());
	}
	/**
	 * validating existing and clone MVO
	 */
	public void validateCloneMVO() {
		System.out.println(getMVO.toString());
		System.out.println(getCloneMVO.toString());
		assertNotEquals(getMVO.toString(), getCloneMVO.toString());
	}

}
