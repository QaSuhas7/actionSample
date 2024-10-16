package org.aia.pages.membership;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class FinalPageThankYou {

	WebDriver driver;

	public FinalPageThankYou(WebDriver Idriver) {
		this.driver = Idriver;
	}

	Utility util = new Utility(driver, 10);

	@FindBy(xpath = "//strong[text()='THANK YOU!']")
	WebElement thankYou;

	@FindBy(xpath = "//iframe[@id='transactionFrame']")
	WebElement frame;
	
	@FindBy(xpath = "//iframe[@title='Receipt']")
	WebElement receiptFrame;

	@FindBy(xpath = "//body[@id='document']/span/table/tbody/tr[3]/td/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[2]/td[2]")
	WebElement receiptNum;

	@FindBy(xpath = "//body[@id='document']/span/table/tbody/tr[3]/td/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[5]/td[2]")
	WebElement custAIANum;

//	@FindBy(xpath = "//body[@id='document']/span/table/tbody/tr[3]/td/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[7]/td[2]")
//	WebElement totalAmount;

	@FindBy(xpath = "//body[@id='document']/span/table/tbody/tr[3]/td/table/tbody/tr/td[1]/table/tbody/tr[3]/td/table/tbody/tr[7]//td[2]/span/span//span")
	WebElement totalAmount;

	@FindBy(xpath = "//p[contains(text(),'Payment has been requested ')]")
	WebElement msgAfterBankPayement;

	String totalAmnt = null;
	String finalPagetotal = null;

	public void verifyThankYouMessage() throws InterruptedException {
		Thread.sleep(15000);
		util.waitUntilElement(driver, frame);
		driver.switchTo().frame(frame);
		util.waitUntilElement(driver, thankYou);
		System.out.println("Thank you !  Message is Displayed");
		Thread.sleep(12000);
	}

	public ArrayList<Object> getFinalReceiptData() {
		ArrayList<Object> receiptData = new ArrayList<Object>();
		util.waitUntilElement(driver, receiptNum);
		String receiptNumber = receiptNum.getText();
		receiptData.add(0, receiptNumber);
		String customerAIANumber = custAIANum.getText();
		receiptData.add(1, customerAIANumber);
		totalAmnt = totalAmount.getText();
		int i = totalAmnt.indexOf(".");
		finalPagetotal = totalAmnt.substring(1, i);
		String totalAmnt = totalAmount.getText().replaceAll("[$]*", "").trim();
		Object totalAmnt1 = Double.valueOf(((String) totalAmnt).replaceAll(",", "").trim());
		receiptData.add(2, totalAmnt1);
		receiptData.add(3, finalPagetotal);
		System.out.println("My receipt Data:" + receiptData);
		return receiptData;
	}

	public void ValidateTotalAmount(String totalMembership) {
		String total = null;
		if (totalMembership.contentEquals("$ 0")) {
			total = totalMembership.substring(2);
		} else {
			int i = totalMembership.indexOf(".");
			String t = totalMembership.substring(2, i);

			int amnt = Integer.parseInt(t) + 40;
			int totalamt = amnt / 6;
			total = Integer.toString(totalamt);

			System.out.println("Total amount is " + total);
		}
		if (finalPagetotal.contentEquals(total)) {
			System.out.println("Total amount is validated");
		}
	}

	public void ValidatePacAndTotal(int pac) {

		totalAmnt = totalAmount.getText();
		int i = totalAmnt.indexOf(".");
		String t = totalAmnt.substring(1, i);
		int finalPagetotal = Integer.parseInt(t);

		if (finalPagetotal == pac) {
			System.out.println("Total amount with PAC is same");
		}
	}

	/**
	 * @return
	 */
	public ArrayList<Object> getFinalReceiptDataOFDip() {
		ArrayList<Object> receiptDataDip = new ArrayList<Object>();
		String receiptNumber = receiptNum.getText();
		receiptDataDip.add(0, receiptNumber);
		String customerAIANumber = custAIANum.getText();
		receiptDataDip.add(1, customerAIANumber);
		totalAmnt = totalAmount.getText();
		int i = totalAmnt.indexOf(".");
		finalPagetotal = totalAmnt.substring(1, i);
		String totalAmnt = totalAmount.getText().replaceAll("[$]*", "").trim();
		Object totalAmnt1 = Double.valueOf(((String) totalAmnt).replaceAll(",", "").trim());
		receiptDataDip.add(2, totalAmnt1);
		receiptDataDip.add(3, finalPagetotal);
		return receiptDataDip;
	}

	/**
	 * @param encryptedId
	 * @return
	 * Here we are checking the termination page of US payment bank, in the try block we are going with 4 business days msg a
	 * and in catch block we are going with "Thank you Page".
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public ArrayList<Object> validateUSBankFinalPage(String encryptedId) throws InterruptedException, IOException {
		ArrayList<Object> receiptData = new ArrayList<Object> ();
		try {
			util.waitUntilElement(driver, msgAfterBankPayement);
			assertTrue(msgAfterBankPayement.isDisplayed());
			util.deleteFile();
			driver.get(DataProviderFactory.getConfig().getValue("pdfReceiptURL") + encryptedId);
			System.out.println("Please wait for Receipt download....................................");
			Thread.sleep(25000);
			String dues = util.replaceStringvalues(util.getPDFReceiptValue("Total"));
			System.out.println("Original receipt" + util.getPDFReceiptValue("Receipt Number"));
			String receiptNumber = util.replaceStringvalues(util.getPDFReceiptValue("Receipt Number"));
			receiptData.add(0,dues);
			receiptData.add(1,receiptNumber);
			System.out.println("My receipt Data:" +receiptData);
			util.deleteFile();
			return receiptData;
		}
		catch (Exception e) {
			verifyThankYouMessage();
			util.waitUntilElement(driver, receiptNum);
			String receiptNumber = receiptNum.getText();
			receiptData.add(1, receiptNumber);
			String customerAIANumber = custAIANum.getText();
			totalAmnt = totalAmount.getText();
			int i = totalAmnt.indexOf(".");
			finalPagetotal = totalAmnt.substring(1, i);
			String totalAmnt = totalAmount.getText().replaceAll("[$]*", "").trim();
			Object totalAmnt1 = Double.valueOf(((String) totalAmnt).replaceAll(",", "").trim());
			receiptData.add(0, totalAmnt1);
			System.out.println("My receipt Data:" + receiptData);
			return receiptData;
		}
	}

	public void validateUSBankFinalPageInNegativeCase() {
		util.waitUntilElement(driver, msgAfterBankPayement);
		assertTrue(msgAfterBankPayement.isDisplayed());
		
	}

}