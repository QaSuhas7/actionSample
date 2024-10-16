package org.aia.pages.ces;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Set;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

public class CheckOutPageCes {

	WebDriver driver;
	Actions action;
	Utility util = new Utility(driver, 30);
	ConfigDataProvider testData;
	JavascriptExecutor executor;

	public CheckOutPageCes(WebDriver Idriver) {
		this.driver = Idriver;
		action = new Actions(driver);
		testData = new ConfigDataProvider();
		executor = (JavascriptExecutor) driver;
	}

	String creditCardNum_old= "4111111111111111";
	String cardExpMonth = "02";
	String cardExpYr = "2027";
	// ArchitectureElements

	@FindBy(xpath = "//a[text()='Credit Card']")
	WebElement creditCardCheckoutCes;

	@FindBy(xpath = "//span[@data-name='full_name']/following-sibling::div/div/input")
	WebElement cardHolderNameCheckOutCes;

	@FindBy(xpath = "//select[@name='Exp month']")
	WebElement expMonthCheckOutCes;

	@FindBy(xpath = "//select[@name='Exp year']")
	WebElement expYearCheckOutCes;

	@FindBy(xpath = "//button[text()='Process payment']")
	WebElement btnProcessPaymnt;

	@FindBy(xpath = "//button[contains(text(),'Create address')]")
	WebElement creditCrdCreateAddressCes;

	@FindBy(xpath = "//iframe[@title='Credit Card Input Frame']")
	WebElement creditCardNumFrame1Ces;

	@FindBy(xpath = "//iframe[@title='Card number']")
	WebElement creditCardNumFrame2Ces;

	@FindBy(xpath = "//input[@id='card_number']")
	WebElement cardNumInputCes;

	@FindBy(xpath = "//a[text()='ECheck']")
	WebElement eCheckCes;

	@FindBy(xpath = "//a[contains(text(),'Download invoice')]")
	WebElement downloadInvoiceCes;

	@FindBy(xpath = "//div[@data-name='name']")
	WebElement nameAddressCes;

	@FindBy(xpath = "//select[@name='Type']")
	WebElement typeAddressCes;

	@FindBy(xpath = "//*[@role='textbox']")
	WebElement enterYourAddressCes;

	@FindBy(xpath = "//div[@class='selectize-dropdown-content']/div[1]")
	WebElement selectfirstAddressCes;

	@FindBy(xpath = "//div[@data-label='Discount Code']/input")
	WebElement discountCodeOrderCes;

	@FindBy(xpath = "//*[text()='Save']")
	WebElement saveBtnAddressCes;

	@FindBy(xpath = "//div[@data-name='fullName']/input")
	WebElement fullNameECheckoutCes;

	@FindBy(xpath = "//div[@data-name='bankName']/input")
	WebElement bankNameECheckoutCes;

	@FindBy(xpath = "//div[@data-name='bankRoutingNumber']/input")
	WebElement bankRoutingECheckoutCes;

	@FindBy(xpath = "//div[@data-name='bankAccountNumber']/input")
	WebElement bankAccntNumECheckoutCes;

	@FindBy(xpath = "//select[@name='Bank Account Type']")
	WebElement bankAccntTypeECheckoutCes;

	@FindBy(xpath = "//select[@name='Bank Account Holder Type']")
	WebElement bankAccntHolderTypeECheckoutCes;

	@FindBy(xpath = "//button[text()='Process payment']")
	WebElement btnProcessPaymntECheckoutCes;

	@FindBy(xpath = "div[data-name='paymentMethodsDiv']>div:nth-child(2)>div>button")
	WebElement sendPerformaInvoiceBtnCes;

	@FindBy(xpath = "//p[contains(text(), 'Thank you for submitting your application')]")
	WebElement passportConfirmtxtCes;

	@FindBy(xpath = "//button[text()='Confirm Order']")
	WebElement confirmOrderBtn;
	
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
	
//	@FindBy(xpath = "//iframe[contains(@id,'paymentElementIframe')]")
//	WebElement stripConnectioniframe_1;
	
	@FindBy(xpath = "//input[@id='savePaymentMethodCheckbox']")
	WebElement savePayementCheckBox;
	
	@FindBy(xpath = "//button[@aria-label='Process payment']")
	WebElement processPaymentBtn;
	
	@FindBy(xpath = "//slot/div[text()='Payment Successful']")
	WebElement paymentDone;
	
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
			
			String accountTypeNew ="//div[@aria-label='%s']";

			@FindBy(xpath = "//span[text()='Agree and continue']/parent::button")
			WebElement stripAgreeAndContinueBtn;

			@FindBy(xpath = "//iframe[contains(@id,'paymentElementIframe')]")
			WebElement stripConnectioniframe_1;

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
			
			@FindBy(xpath = "//p[contains(text(),'Payment has been requested ')]")
			WebElement msgAfterBankPayement;
			
			@FindBy(xpath = "//input[@name='postalCode']")
			WebElement zipCode;


	/*
	 * @param : text
	 * 
	 * @param : aiaMemberNumber
	 * 
	 * @param : orgType
	 */
	public void SubscriptionType(String text) throws Exception {
		if (text.contentEquals("Architecture Firm") || text.contentEquals("Architecture - Single Discipline")
				|| text.toLowerCase().contentEquals("Multi-Disciplinary (Architect Led)")
				|| text.contentEquals("Multi-disciplinary (engineer led)")
				|| text.contentEquals("Multi-disciplinary (interior led)")
				|| text.contentEquals("Multi-disciplinary (planning led)")
				|| text.contentEquals("Design & Construction Services")) {
			verifyConfirmationTxt();
		}

		else if (text.contentEquals("Building product manufacturer") || text.contentEquals("Construction")
				|| text.contentEquals("Consulting") || text.contentEquals("Engineering")
				|| text.contentEquals("Interior Design") || text.contentEquals("Landscape")
				|| text.contentEquals("Real Estate/Building owner") || text.contentEquals("Law")
				|| text.contentEquals("Press") || text.contentEquals("Other")) {
//			enterCardDetailsCes();
			enterCardDetails();
//			savePaymentcheckBoxInpayemntGateWay();
			clickProcessButton();
			validatePaymentProcessDone();
		}

		else if (text.contentEquals("Institutional") || text.contentEquals("Government/public")
				|| text.contentEquals("Non-profit/trade association") || text.contentEquals("Licensing Board")) {
			verifyConfirmationTxt();
		}

	}

	public void enterCardDetailsCes() throws InterruptedException {
		Thread.sleep(10000);
		util.waitUntilElement(driver, creditCardCheckoutCes);
		util.waitUntilElement(driver, creditCardNumFrame1Ces);
		Thread.sleep(5000);
		driver.switchTo().frame(creditCardNumFrame1Ces);
		Thread.sleep(15000);
		driver.switchTo().frame(creditCardNumFrame2Ces);
		cardNumInputCes.sendKeys(creditCardNum_old);
		driver.switchTo().defaultContent();
		util.selectDropDownByText(expMonthCheckOutCes, cardExpMonth);
		Thread.sleep(2000);
		util.waitUntilElement(driver, expYearCheckOutCes);
		util.selectDropDownByText(expYearCheckOutCes, cardExpYr);
		discountCodeOrderCes.sendKeys("");
		util.waitUntilElement(driver, btnProcessPaymnt);
		util.clickUsingJS(driver, btnProcessPaymnt);
		//btnProcessPaymnt.click();
	}

	public void enterECheckDetailsCes(String accountHolderName, String bankName, String bankRoutingNumber,
			String bankAccountNumber) throws InterruptedException {
		// util.waitUntilElement(driver, eCheckCes);
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		Thread.sleep(50000);
		executor.executeScript("arguments[0].scrollIntoView(true);", eCheckCes);
		util.waitUntilElement(driver, eCheckCes);
		Thread.sleep(20000);
		executor.executeScript("arguments[0].click();", eCheckCes);
		// eCheckCes.click();
		Thread.sleep(2000);
		util.waitUntilElement(driver, fullNameECheckoutCes);
		fullNameECheckoutCes.sendKeys(accountHolderName);
		bankNameECheckoutCes.sendKeys(bankName);
		bankRoutingECheckoutCes.sendKeys(bankRoutingNumber);
		bankAccntNumECheckoutCes.sendKeys(bankAccountNumber);
		util.selectDropDownByText(bankAccntTypeECheckoutCes, "savings");
		util.selectDropDownByText(bankAccntHolderTypeECheckoutCes, "personal");
		// checkboxECheckoutCes.click();
		// discountCodeOrderCes.sendKeys("");
		btnProcessPaymntECheckoutCes.click();
	}

	public void enterBillingAddressDetailsCes() throws InterruptedException {
		util.waitUntilElement(driver, creditCrdCreateAddressCes);
		creditCrdCreateAddressCes.click();
		util.waitUntilElement(driver, nameAddressCes);
		nameAddressCes.sendKeys("AddressFN");
		util.selectDropDownByText(typeAddressCes, "Home");
		enterYourAddressCes.sendKeys("Jasonville, IN, USA");
		selectfirstAddressCes.click();
		saveBtnAddressCes.click();
	}

	public void downloadInvoice() {
		util.waitUntilElement(driver, downloadInvoiceCes);
		downloadInvoiceCes.click();
		sendPerformaInvoiceBtnCes.click();
		Set<String> links = driver.getWindowHandles();
		String currWin = driver.getWindowHandle();
		for (String s1 : links)
			if (!s1.contentEquals(currWin)) {
				driver.switchTo().window(s1);
				System.out.println("Link is identified");
			}

	}

	public void verifyConfirmationTxt() throws Exception {
		Thread.sleep(1000);
		util.waitUntilElement(driver, passportConfirmtxtCes);
		assertTrue(passportConfirmtxtCes.isDisplayed(), "Submission Confirmation text is visible.");
	}

	public void confirmOrderWithNoAmt() {
		util.waitUntilElement(driver, confirmOrderBtn);
		confirmOrderBtn.click();
	}
	

	/**
	 * @throws InterruptedException
	 * This function is use for new payment gateway credit card flow
	 */
	public void enterCardDetails() throws InterruptedException {
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
		System.out.println("credit card number:"+cardNumber);
		util.enterText(driver, creditCardNum, testData.testDataProvider().getProperty("paymentGateCardNum"));
		util.waitUntilElement(driver, monthAndYear);
		util.enterText(driver, monthAndYear, testData.testDataProvider().getProperty("monthAndYear"));
		util.waitUntilElement(driver, cvcNum);
		util.enterText(driver, cvcNum, testData.testDataProvider().getProperty("cardCvv"));
		try {
			util.enterText(driver, zipCode, "38671");
			driver.switchTo().defaultContent();
//			savePaymentcheckBoxInpayemntGateWay();
		}
		catch (Exception e) {
			driver.switchTo().defaultContent();
//			savePaymentcheckBoxInpayemntGateWay();
			clickProcessButton();
		}
		
//		driver.switchTo().defaultContent();
		
	}
	
	public void savePaymentcheckBoxInpayemntGateWay() {
		driver.switchTo().frame(stripConnectioniframe_1);
		util.waitUntilElement(driver, savePayementCheckBox);
		util.clickUsingJS(driver, savePayementCheckBox);
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
        util.getCustomizedWebElement(driver, accountTypeNew, yourBank).click();
		
		driver.switchTo().defaultContent();
		driver.switchTo().frame(stripConnectioniframe_1);
		TargetLocator currentFrame = driver.switchTo();
		currentFrame.frame(stripConnectioniframe_2);
		util.waitUntilElement(driver, stripAgreeAndContinueBtn);
		stripAgreeAndContinueBtn.click();
		Thread.sleep(5000);
	    currentFrame.activeElement().sendKeys(Keys.TAB);
	    currentFrame.activeElement().sendKeys(Keys.DOWN);
		executor.executeScript("window.scrollBy(0,250)");
		action.scrollToElement(util.getCustomizedWebElement(driver, typeOfAccount, selectAccount)).build().perform();
//		executor.executeScript("arguments[0].scrollIntoView(true);", util.getCustomizedWebElement(driver, typeOfAccount, selectAccount));
		util.getCustomizedWebElement(driver, typeOfAccount, selectAccount).click();
		util.waitUntilElement(driver, connectAccountBtn);
		connectAccountBtn.click();
		util.waitUntilElement(driver, connectionSuccessMsg);
		assertTrue(connectionSuccessMsg.isDisplayed());
		String connectionSuccessMsgText = connectSuccessfulmsg.getText();
		assertEquals(connectionSuccessMsgText, connectionMsg);
		util.waitUntilElement(driver, backToFontevaPayment);
		backToFontevaPayment.click();
		//Need to switch on default content
		driver.switchTo().defaultContent();
		driver.switchTo().frame(stripConnectioniframe_1);
//		util.waitUntilElement(driver, savePaymentMethodChkBox);
//		savePaymentMethodChkBox.click();
		driver.switchTo().defaultContent();
		util.waitUntilElement(driver, processPaymentBtn);
		processPaymentBtn.click();
        
	}
	
	public void validateUSBankFinalPage() {
	       util.waitUntilElement(driver, msgAfterBankPayement);
	       assertTrue(msgAfterBankPayement.isDisplayed());
		}
}
