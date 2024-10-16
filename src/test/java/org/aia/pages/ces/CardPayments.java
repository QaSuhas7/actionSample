package org.aia.pages.ces;

import static org.testng.Assert.assertTrue;

import org.aia.pages.fonteva.events.AgendaModule;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import groovy.transform.Final;

public class CardPayments {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	ConfigDataProvider testData;
	static Logger log = Logger.getLogger(CardPayments.class);

	public CardPayments(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
		testData = new ConfigDataProvider();
	}
	
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
	
	@FindBy(xpath = "//iframe[contains(@id,'paymentElementIframe')]")
	WebElement stripConnectioniframe_1;
	
	@FindBy(xpath = "//input[@id='savePaymentMethodCheckbox']")
	WebElement savePayementCheckBox;
	
	@FindBy(xpath = "//button[@aria-label='Process payment']")
	WebElement processPaymentBtn;
	
	@FindBy(xpath = "//div[text()='Payment Successful']")
	WebElement paymentDone;
	
	
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
		driver.switchTo().defaultContent();
		savePaymentcheckBoxInpayemntGateWay();
		clickProcessButton();
//		validatePaymentProcessDone();
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
	
}
