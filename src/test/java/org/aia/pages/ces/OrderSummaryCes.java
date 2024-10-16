package org.aia.pages.ces;

import static org.junit.Assert.*;
import static org.testng.Assert.*;

import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class OrderSummaryCes {
	WebDriver driver;

	Utility util = new Utility(driver, 30);

	public OrderSummaryCes(WebDriver Idriver) {
		this.driver = Idriver;
	}

	@FindBy(xpath = "//div[@data-name='totalAmount']//div//strong//span")
	WebElement checkoutAmount;

	@FindBy(xpath = "//div[@data-label='Discount Code']//input")
	WebElement discountCodeBox;

	@FindBy(xpath = "//button[text()='Apply']")
	WebElement applyBtn;

	@FindBy(xpath = "//div[text()='Discount Applied']//parent::div//strong")
	WebElement discountCodeApplied;

	String beforApplyCodeAmnt;
	
	/**
	 * @param discountCode
	 * @throws InterruptedException 
	 */
	public void applyDiscountCode(String discountCode) throws InterruptedException {
		Thread.sleep(20000);
		util.waitUntilElement(driver, checkoutAmount);
	    beforApplyCodeAmnt = checkoutAmount.getText();
		discountCodeBox.sendKeys(discountCode);
		applyBtn.click();
	}
	
	/**
	 * Here we validate amount after discount code apply for same membership 
	 */
	public void validateAmountAfterApplyDiscountCode() {
		util.waitUntilElement(driver, checkoutAmount);
		String afterApplyCodeAmnt = checkoutAmount.getText();
		assertEquals(afterApplyCodeAmnt,beforApplyCodeAmnt);
	}

	
	
}
