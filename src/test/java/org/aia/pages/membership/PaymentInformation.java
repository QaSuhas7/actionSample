package org.aia.pages.membership;

import static org.testng.Assert.*;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

public class PaymentInformation {

	WebDriver driver;
	Actions action;
	Utility util = new Utility(driver, 10);
	ConfigDataProvider data = new ConfigDataProvider();
	JavascriptExecutor executor;
	ConfigDataProvider testData;

	public PaymentInformation(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		executor = (JavascriptExecutor) driver;
		testData = new ConfigDataProvider();
	}

	String creditCardNum_old = "4111111111111111";
	String cardExpMonth = "02";
	String cardExpYr = "2027";

	@FindBy(xpath = "//*[@id='checkout-form-wrapper']/div[1]/h5/text()")
	WebElement paymentInfo;

	@FindBy(xpath = "//a[text()='Credit card']")
	WebElement creditCard_old;

	@FindBy(xpath = "//a[text()='ECheck']")
	WebElement eCheck;

	@FindBy(xpath = "//div[@data-name='full_name']/input")
	WebElement fullName;

	@FindBy(xpath = "//iframe[@title='Credit Card Input Frame']")
	WebElement cardNumFrame1;

	@FindBy(xpath = "//iframe[@title='Card number']")
	WebElement cardNumFrame2;

	@FindBy(xpath = "//input[@name='card_number']")
	WebElement cardNum;

	@FindBy(xpath = "//select[@name='Exp month']")
	WebElement expMonth;

	@FindBy(xpath = "//select[@name='Exp year']")
	WebElement expYr;

	@FindBy(xpath = "//label[@data-name='savePaymentMethod']")
	WebElement chckBox;

	@FindBy(xpath = "(//table)[1]//tbody//tr[3]//td[text()='Installment Amount: ']")
	WebElement isInstallment;

	@FindBy(xpath = "//span[contains(text(),'subscription terms')]/parent::label//input")
	WebElement agreetermChckbox;

	@FindBy(xpath = "//button[@data-name='processBtn']")
	WebElement procssPaymntBtn;

	@FindBy(xpath = "//button[text()='Process payment']")
	WebElement processPaymnt;

	@FindBy(xpath = "//*[@class=\"shopping-cart-summary-component\"]//li[1]//span/table/tbody/tr/td[4]/span/span/span")
	WebElement aiaNational;

	@FindBy(xpath = "//a[@id='completePayment']")
	WebElement completeOrder;

	@FindBy(xpath = "//li[@title=\"Credit card\"]/a")
	WebElement creditCardLink;

	// @FindBy(xpath = "//span[@id='order_total']")
	@FindBy(xpath = "(//td[@class='subtotal']//span[@name='currencyInputSpan'])[1]")
	WebElement afterZeroSalesOrderAmtText;

	@FindBy(xpath = "//a[@id='completePayment']")
	WebElement complatePaymentBtn;
	@FindBy(xpath = "//a[text()='ECheck']")
	WebElement echeckTab;

	@FindBy(xpath = "//div[@data-label='Account Holder Name']/input")
	WebElement accountHolderName;

	@FindBy(xpath = "//div[@data-label='Bank Name']/input")
	WebElement bankName;

	@FindBy(xpath = "//div[@data-name='bankRoutingNumber']/input")
	WebElement bankRoutingNumber;

	@FindBy(xpath = "//div[@data-name='bankAccountNumber']/input")
	WebElement accountNumber;

	@FindBy(xpath = "//select[contains(@name,'Account Type')]")
	WebElement accountType;

	@FindBy(xpath = "//select[contains(@name,'Account Holder Type')]")
	WebElement holderType;

	@FindBy(xpath = "//button[text()='Process payment']")
	WebElement processPaymentBtn_old;

	// Below locator we have for new payment gateways.

	@FindBy(xpath = "//iframe[@title='Secure payment input frame']")
	WebElement usBankIframe;

	@FindBy(xpath = "//iframe[contains(@id,'paymentElementIframe')]")
	WebElement usBankFrame_2;

	@FindBy(xpath = "//button[@id='us_bank_account-tab']")
	WebElement usBankBtn;

	@FindBy(xpath = "//input[@name='email']")
	WebElement emailInput;

	@FindBy(xpath = "//input[@name='name']")
	WebElement userNameIput;

	@FindBy(xpath = "//input[@name='bank']")
	WebElement choseBankInput;

	String accountTypeNew = "//div[@aria-label='%s']";

	@FindBy(xpath = "//span[text()='Agree and continue']/parent::button")
	WebElement stripAgreeAndContinueBtn;

	@FindBy(xpath = "//iframe[contains(@id,'paymentElementIframe')]")
	WebElement stripConnectioniframe_1_Bank;

	@FindBy(xpath = "/html/body/div/iframe")
	WebElement stripConnectioniframe_2;

	String typeOfAccount = "//p[text()='%s']/ancestor::button";

	@FindBy(xpath = "//span[text()='Connect account']/parent::button")
	WebElement connectAccountBtn;

	@FindBy(xpath = "//h1[text()='Success']")
	WebElement connectionSuccessMsg;

	@FindBy(xpath = "//p[text()='Your account was connected.']")
	WebElement connectSuccessfulmsg;

	@FindBy(xpath = "//span[text()='Back to Fonteva Test Gateway Account']/parent::button")
	WebElement backToFontevaPayment;

	@FindBy(xpath = "//input[@id='savePaymentMethodCheckbox']")
	WebElement savePaymentMethodChkBox;

	@FindBy(xpath = "//button[@aria-label='Process payment']")
	WebElement processPaymentBtnNew;
	
	//New credit card flow
	
	@FindBy(xpath = "//iframe[contains(@id,'paymentElementIframe')]")
	WebElement iframe1;
	
	@FindBy(xpath = "(//iframe[contains(@name,'__privateStripeFrame')])[1]")
	WebElement iframe2;
	
	@FindBy(xpath = "//button[@id='card-tab']")
	WebElement creditCard;
	
	@FindBy(xpath = "//input[@id='Field-numberInput']")
	WebElement creditCardNum;
	
	@FindBy(xpath = "//input[@id='Field-expiryInput']")
	WebElement monthAndYear;
	
	@FindBy(xpath = "//input[@id='Field-cvcInput']")
	WebElement cvcNum;
	
	@FindBy(xpath = "//select[@name='country']/option[text()='United States']")
	WebElement unitedStatesCountry;
	
	@FindBy(xpath = "//input[@name='postalCode']")
	WebElement zipCode;
	
	@FindBy(xpath = "//iframe[contains(@id,'paymentElementIframe')]")
	WebElement stripConnectioniframe_1;
	
	@FindBy(xpath = "//input[@id='savePaymentMethodCheckbox']")
	WebElement savePayementCheckBox;
	
	@FindBy(xpath = "//button[@aria-label='Process payment']")
	WebElement processPaymentBtn;
	
	@FindBy(xpath = "//div[text()='Payment Successful']")
	WebElement paymentDone;
	
	@FindBy (xpath = "//a[text()='Online Payment']")
	WebElement onlinePayment;
	
	@FindBy(xpath = "//select[@name='country']")
	WebElement selectCountry;

	public void clickOnProcesspaymnt() {

		util.waitUntilElement(driver, processPaymnt);
		processPaymnt.click();

	}

	public void clickOnCreditCard() {
		util.waitUntilElement(driver, onlinePayment);
        driver.navigate().refresh();
        util.waitUntilElement(driver, onlinePayment);
        onlinePayment.click();

	}

	public String paymentDetails(String text) throws InterruptedException {
		String aiaNatnl = null;
		if (text.contentEquals("noLicense") || text.contentEquals("graduate") || text.contentEquals("axp")) {
			// util.waitUntilElement(driver, completeOrder);
			aiaNatnl = enterCrditCardDetails();
			// Thread.sleep(30000);
			// completeOrder.click();
			// Thread.sleep(30000);
		} else if (text.contentEquals("activeUSLicense") || text.contentEquals("activeNonUSLicense")
				|| text.contentEquals("supervision") || text.contentEquals("faculty") || text.contentEquals("allied")) {
			aiaNatnl = enterCrditCardDetails();

			// Thread.sleep(10000);
		}

		return aiaNatnl;
	}

	public String enterCrditCardDetails() throws InterruptedException {
		Thread.sleep(40000);
//		util.waitUntilElement(driver, creditCard);
//		util.waitUntilElement(driver, cardNumFrame1);
//
//		String aiaNatnl = aiaNational.getText();
//		util.waitUntilElement(driver, cardNumFrame1);
//		driver.switchTo().frame(cardNumFrame1);
//		Thread.sleep(2000);
//		util.waitUntilElement(driver, cardNumFrame2);
//		driver.switchTo().frame(cardNumFrame2);
//		Thread.sleep(2000);
//		util.enterText(driver, cardNum, creditCardNum);
//		driver.switchTo().defaultContent();
//		Select s1 = new Select(expMonth);
//		s1.selectByValue(cardExpMonth);
//
//		Select s2 = new Select(expYr);
//		s2.selectByValue(cardExpYr);
//		chckBox.click();
		util.waitForJavascript(driver, 20000, 4000);
		Thread.sleep(30000);
		String aiaNatnl = aiaNational.getText();
		util.waitUntilElement(driver, iframe1);
		driver.switchTo().frame(iframe1);
		driver.switchTo().frame(iframe2);
		System.out.println("child frame");
		util.waitUntilElement(driver, creditCard);
		util.clickUsingJS(driver, creditCard);
		util.waitUntilElement(driver, creditCardNum);
		String cardNumber = creditCardNum.getAttribute("value");
		System.out.println("credit card number:" + cardNumber);
		util.enterText(driver, creditCardNum, testData.testDataProvider().getProperty("paymentGateCardNum"));
		util.waitUntilElement(driver, monthAndYear);
		util.enterText(driver, monthAndYear, testData.testDataProvider().getProperty("monthAndYear"));
		util.waitUntilElement(driver, cvcNum);
		util.enterText(driver, cvcNum, testData.testDataProvider().getProperty("cardCvv"));
		util.selectDropDownByText(selectCountry, "United States");
		util.enterText(driver, zipCode, "38671");
		driver.switchTo().defaultContent();
		savePaymentcheckBoxInpayemntGateWay();
		clickProcessButton();
		
		
//		validatePaymentProcessDone();
		

//		try {
//			isInstallment.getText().contains("Installment Amount:");
//			util.waitUntilElement(driver, agreetermChckbox);
//			executor.executeScript("arguments[0].click();", agreetermChckbox);
//			util.waitUntilElement(driver, procssPaymntBtn);
//			procssPaymntBtn.click();
//		} catch (Exception e) {
//			clickProcessButton();
//		}

		return aiaNatnl;
	}

	/**
	 * Owner: Suhas this method is created for $0 sales order
	 */
	public void makeZeroOrderPayment() {
		util.waitUntilElement(driver, afterZeroSalesOrderAmtText);
		// Validate final price is become zero in UI.
		assertEquals(afterZeroSalesOrderAmtText.getText(), data.testDataProvider().getProperty("replacatedAmt"));
		util.waitUntilElement(driver, complatePaymentBtn);
		complatePaymentBtn.click();
	}

	/**
	 * @param accountHolder
	 * @param accountTypeOpt
	 * @param holderType
	 */
	public void paymentViaEcheck(String accountHolder, String accountTypeOpt, String accountHolderType) {
		// driver.navigate().refresh();
		util.waitUntilElement(driver, echeckTab);
		echeckTab.click();
		util.waitUntilElement(driver, accountHolderName);
		util.enterText(driver, accountHolderName, accountHolder);
		util.enterText(driver, bankName, data.testDataProvider().getProperty("bankName"));
		util.enterText(driver, bankRoutingNumber, data.testDataProvider().getProperty("bankRoutingNo"));
		util.enterText(driver, accountNumber, data.testDataProvider().getProperty("bankAccountNo"));
		action.moveToElement(accountType).build().perform();
		util.selectDrp(accountType).selectByValue(accountTypeOpt);
		util.selectDrp(holderType).selectByValue(accountHolderType);
		processPaymentBtn.click();
	}

	/**
	 * This function is created for new US bank payment gateways
	 * 
	 * @param userEmail
	 * @param fullName
	 * @param yourBank
	 * @param selectAccount
	 * @param connectionMsg
	 * @throws InterruptedException
	 */
	public void usBankPayments(String userEmail, String fullName, String yourBank, String selectAccount,
			String connectionMsg) throws InterruptedException {
		Thread.sleep(7000);
		util.waitUntilElement(driver, usBankFrame_2);
		driver.switchTo().frame(usBankFrame_2);
		util.waitUntilElement(driver, usBankIframe);
		driver.switchTo().frame(usBankIframe);
		util.waitUntilElement(driver, usBankBtn);
		usBankBtn.click();
		util.waitUntilElement(driver, emailInput);
		emailInput.sendKeys(userEmail);
		util.waitUntilElement(driver, userNameIput);
		userNameIput.sendKeys(fullName);
		util.waitUntilElement(driver, choseBankInput);
//		choseBankInput.sendKeys(yourBank);
		// need to hit enter
		util.clickUsingJS(driver, util.getCustomizedWebElement(driver, accountTypeNew, yourBank));
		driver.switchTo().defaultContent();
		driver.switchTo().frame(stripConnectioniframe_1);
		TargetLocator currentFrame = driver.switchTo();
		currentFrame.frame(stripConnectioniframe_2);
		util.waitUntilElement(driver, stripAgreeAndContinueBtn);
		util.clickUsingJS(driver, stripAgreeAndContinueBtn);
//		stripAgreeAndContinueBtn.click();
		Thread.sleep(5000);
		currentFrame.activeElement().sendKeys(Keys.TAB);
		currentFrame.activeElement().sendKeys(Keys.DOWN);
		executor.executeScript("window.scrollBy(0,350)");
		util.waitUntilElement(driver, util.getCustomizedWebElement(driver, typeOfAccount, selectAccount));
		action.scrollToElement(util.getCustomizedWebElement(driver, typeOfAccount, selectAccount)).build().perform();
//		executor.executeScript("arguments[0].scrollIntoView(true);", util.getCustomizedWebElement(driver, typeOfAccount, selectAccount));
		util.clickUsingJS(driver, util.getCustomizedWebElement(driver, typeOfAccount, selectAccount));
//		util.getCustomizedWebElement(driver, typeOfAccount, selectAccount).click();
		util.waitUntilElement(driver, connectAccountBtn);
		connectAccountBtn.click();
		util.waitUntilElement(driver, connectionSuccessMsg);
		assertTrue(connectionSuccessMsg.isDisplayed());
		String connectionSuccessMsgText = connectSuccessfulmsg.getText();
		assertEquals(connectionSuccessMsgText, connectionMsg);
		util.waitUntilElement(driver, backToFontevaPayment);
		backToFontevaPayment.click();
		// Need to switch on default content
		driver.switchTo().defaultContent();
		driver.switchTo().frame(stripConnectioniframe_1);
//		util.waitUntilElement(driver, savePaymentMethodChkBox);
//		util.clickUsingJS(driver, savePaymentMethodChkBox);
//		savePaymentMethodChkBox.click();
		driver.switchTo().defaultContent();
		util.waitUntilElement(driver, processPaymentBtn);
        util.clickUsingJS(driver, processPaymentBtn);
	}

	/*public void enterCardDetails() throws InterruptedException {
		util.waitForJavascript(driver, 20000, 4000);
		Thread.sleep(30000);
		util.waitUntilElement(driver, iframe1);
		driver.switchTo().frame(iframe1);
		driver.switchTo().frame(iframe2);
		System.out.println("child frame");
		util.waitUntilElement(driver, creditCard);
		util.clickUsingJS(driver, creditCard);
		util.waitUntilElement(driver, creditCardNum);
		String cardNumber = creditCardNum.getAttribute("value");
		System.out.println("credit card number:" + cardNumber);
		util.enterText(driver, creditCardNum, testData.testDataProvider().getProperty("paymentGateCardNum"));
		util.waitUntilElement(driver, monthAndYear);
		util.enterText(driver, monthAndYear, testData.testDataProvider().getProperty("monthAndYear"));
		util.waitUntilElement(driver, cvcNum);
		util.enterText(driver, cvcNum, testData.testDataProvider().getProperty("cardCvv"));
		driver.switchTo().defaultContent();
		savePaymentcheckBoxInpayemntGateWay();
		clickProcessButton();
//		validatePaymentProcessDone();
	}*/

	public void savePaymentcheckBoxInpayemntGateWay() {
//		driver.switchTo().frame(stripConnectioniframe_1);
//		util.waitUntilElement(driver, savePayementCheckBox);
//		util.clickUsingJS(driver, savePayementCheckBox);
		driver.switchTo().defaultContent();
	}

	public void clickProcessButton() {
		util.scrollingElementUsingJS(driver, processPaymentBtn);
		util.waitUntilElement(driver, processPaymentBtn);
		util.clickUsingJS(driver, processPaymentBtn);
	}

	public void validatePaymentProcessDone() {
		util.waitUntilElement(driver, paymentDone);
		assertTrue(paymentDone.isDisplayed());
	}

}
