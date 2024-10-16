package org.aia.pages.fonteva.membership;

import static org.testng.Assert.assertTrue;

import org.aia.pages.api.ces.SubscriptionPlanPrice;
import org.aia.pages.ces.Organization;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DateUtils;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * @author sghodake
 *
 */
public class TransferMembership {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();
	SubscriptionPlanPrice subscriptionAPI = new SubscriptionPlanPrice(driver);
	Organization org;
	static Logger log = Logger.getLogger(ContactCreateUser.class);
	Actions action;
	JavascriptExecutor executor;
	DateUtils dateUtils;

	public TransferMembership(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
		org = new Organization(driver);
		dateUtils = new DateUtils();
		
	}
	
	/// transfer request
		// **********************
		@FindBy(xpath = "(//ul[@role='presentation']//li[4]//button)")
		WebElement showMoreActionsBtn;

		@FindBy(xpath = "//*[contains(text(),'New Transfer Request')]")
		WebElement newTransferRequestOptn;

		@FindBy(xpath = "//span[contains(text(),'If you have moved and need to')]")
		WebElement transferRequestPopupMsg;

		@FindBy(xpath = "//p[contains(text(),'Thank you for your interest in transferring your AIA membership.')]")
		WebElement transferRequestThankYouMsg;
		
		@FindBy(xpath = "//p[contains(text(),'Please tell us your new address')]")
		WebElement tellYourNewAddressMsg;

		@FindBy(xpath = "//select[@name='Address_Type']")
		WebElement addressType;

		String adressTypeOptn = "//option[@value='%s']";

		String countryOptn = "//option[contains(text(),'%s')]";

		@FindBy(xpath = "//option[@value='Home']")
		WebElement homeOptn;

		@FindBy(xpath = "//option[@value='Work']")
		WebElement workOptn;

		@FindBy(xpath = "//label[contains(text(),'Country')]/following::select")
		WebElement selectCountry;

		@FindBy(xpath = "//input[@name='Street_Address']")
		WebElement streetAddress;

		@FindBy(xpath = "//input[@name='City']")
		WebElement city;

		@FindBy(xpath = "//input[@name='Postal_Code']")
		WebElement postalCode;

		@FindBy(xpath = "//*[contains(text(),'Membership Transfer')]//ancestor::h1")
		WebElement membershipTransferHeading;

		@FindBy(xpath = "//a[contains(text(),'Contact Details')]")
		WebElement contactDetails;

		@FindBy(xpath = "//a[contains(text(),'Current Membership Assignments')]")
		WebElement currentMembershipAssignments;
		
		@FindBy(xpath = "//a[contains(text(),'Application Details')]")
		WebElement applicationDetails;
		
		@FindBy(xpath = "//button[text()='Next']")
		WebElement nextBtn;
		
		@FindBy(xpath = "//span[text()='New Transfer Request']/parent::a")
		WebElement newTransferBtn;
		
		@FindBy(xpath = "//h2[text()='New Transfer Request']")
		WebElement memTranserPopup;
		
		@FindBy(xpath = "//button[text()='Approve/Reject']")
		WebElement approveBtn;

		@FindBy(xpath = "(//span[contains(text(),'membership transfer?')]/parent::label/*[position() mod 3 = 0]/span)[1]")
        WebElement approveToggle;	
	/**
	 * @throws InterruptedException selects Contact on the Receipt Page
	 * @param addressTypevalue
	 * @param countryValue
	 * @throws InterruptedException
	 */
	public void verifyMemTransferApplicationProcess(String addressTypevalue, String countryValue)
			throws InterruptedException {
		util.waitUntilElement(driver, showMoreActionsBtn);
//		util.clickUsingJS(driver, showMoreActionsBtn);
		showMoreActionsBtn.click();
		Actions actions = new Actions(driver);
		actions.keyDown(Keys.ARROW_DOWN).build().perform();
		actions.keyUp(Keys.ARROW_UP).build().perform();
		actions.keyDown(Keys.ENTER).build().perform();
		actions.keyUp(Keys.ENTER).build().perform();
//		action.moveToElement(showMoreActionsBtn).click().build().perform();
//		util.waitUntilElement(driver, newTransferBtn);
//		action.moveToElement(newTransferBtn).click().perform();
		util.waitUntilElement(driver, transferRequestPopupMsg);
		String transferRequestPopuptext = transferRequestPopupMsg.getText();
		System.out.println("popUpTxtValue:" + transferRequestPopuptext);
		assertTrue(transferRequestPopuptext
				.equalsIgnoreCase(data.testDataProvider().getProperty("transferRequestPopupMessage")));
		util.waitUntilElement(driver, nextBtn);
		nextBtn.isDisplayed();
		action.moveToElement(nextBtn).click().perform();
		util.waitUntilElement(driver, transferRequestThankYouMsg);
		String transferRequestThankYouMsgValue = transferRequestThankYouMsg.getText();
		System.out.println("transferRequestThankYouMsgValue:" + transferRequestThankYouMsgValue);
		assertTrue(transferRequestThankYouMsgValue
				.equalsIgnoreCase(data.testDataProvider().getProperty("transferRequestThankYouMessage")));
		util.waitUntilElement(driver, tellYourNewAddressMsg);
		String tellYourNewAddressMsgValue = tellYourNewAddressMsg.getText();
		System.out.println("tellYourNewAddressMsgValue:" + tellYourNewAddressMsgValue);
		assertTrue(tellYourNewAddressMsgValue
				.equalsIgnoreCase(data.testDataProvider().getProperty("tellYourNewAddressMessage")));
		util.waitUntilElement(driver, addressType);
		action.moveToElement(addressType).click().perform();
		util.getCustomizedWebElement(driver, adressTypeOptn, addressTypevalue).click();
		util.getCustomizedWebElement(driver, countryOptn, countryValue).click();
		util.waitUntilElement(driver, streetAddress);
		streetAddress.sendKeys("Australia");
		util.waitUntilElement(driver, city);
		city.sendKeys("new street");
		util.waitUntilElement(driver, postalCode);
		postalCode.sendKeys("4321");
		util.waitUntilElement(driver, nextBtn);
		action.moveToElement(nextBtn).click().perform();
		util.waitUntilElement(driver, membershipTransferHeading);
		assertTrue(membershipTransferHeading.isDisplayed()); 
		util.waitUntilElement(driver, contactDetails);
		assertTrue(contactDetails.isDisplayed()); 
		util.waitUntilElement(driver, currentMembershipAssignments);
		assertTrue(currentMembershipAssignments.isDisplayed()); 
		util.waitUntilElement(driver, applicationDetails);
		assertTrue(applicationDetails.isDisplayed()); 
	}
	
	public void approveMembershipTransfer() {
		util.waitUntilElement(driver, approveBtn);
		approveBtn.click();
		util.waitUntilElement(driver, approveToggle);
		approveToggle.click();
		util.waitUntilElement(driver, nextBtn);
		action.moveToElement(nextBtn).click().perform();
		util.waitUntilElement(driver, nextBtn);
		action.moveToElement(nextBtn).click().perform();
	}
	
}
