package org.aia.pages.events;

import static org.testng.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Set;
import org.aia.pages.fonteva.events.EventInfoModule;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class ViewRecipts {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	ConfigDataProvider testData;
	EventRegistration eventRegistration;
	EventInfoModule editCloneEvent;
	static Logger log = Logger.getLogger(EventRegistration.class);
	String pdfContent = "";

	public ViewRecipts(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		testData = new ConfigDataProvider();
		eventRegistration = PageFactory.initElements(driver, EventRegistration.class);
		editCloneEvent = PageFactory.initElements(driver, EventInfoModule.class);
	}

	/**
	 * @param receiptNo
	 * @param total
	 * @return
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public String viewReceiptValidationsForEvents(Object receiptNo, Object total, String paymentType,
			String paymentMethodDescr,String aiaNumber) throws InterruptedException, IOException {
		Thread.sleep(15000);
		Set<String> links = driver.getWindowHandles();
		String currWin = driver.getWindowHandle();
		Thread.sleep(1000);
		String pdfContent = null;
		for (String s1 : links)
			if (!s1.contentEquals(currWin)) {
				driver.switchTo().window(s1);
				String currentUrl = driver.getCurrentUrl();
				if (currentUrl.contains("NationalEvents")) {
					continue;
				} else if (currentUrl.contains("generateMultiplePDF")) {
					URL url = new URL(currentUrl);

					// Open stream method is used to open the pdf file
					InputStream is = url.openStream();

					// using the Buffered input class(creating the object file parse)
					BufferedInputStream fileParse = new BufferedInputStream(is);

					// PD document is coming from PDF box
					PDDocument document = null;

					// Initialize the document from load method(load buffered input class)
					document = PDDocument.load(fileParse);

					// creating object he he & returning the content
					PDFTextStripper strip = new PDFTextStripper();

					strip.setStartPage(1);
					pdfContent = strip.getText(document);

					// Printing the content on console
					System.out.println(pdfContent);
					if (pdfContent.contains(total.toString())) {
						assertTrue(pdfContent.contains(total.toString()), "Total amount paid is present in Recipt.");
					}
					Assert.assertTrue(pdfContent.contains(aiaNumber.replace(" ", "")),
							"verified customer AIA number in receipt documnet" + aiaNumber);
					log.info("verified customer AIA number in receipt documnet" + aiaNumber);

					Assert.assertTrue(pdfContent.contains(paymentType));
					log.info("verified customer AIA number in receipt documnet" + paymentType);

					Assert.assertTrue(pdfContent.contains(total.toString()));
					log.info("verified total amount in receipt documnet" + total);

					Assert.assertTrue(pdfContent.contains((receiptNo.toString()).replace("Receipt: #", "").trim()));
					log.info("verified Receipt number in receipt document" + receiptNo);

					Assert.assertTrue(pdfContent.contains(eventRegistration.postedDate));
					log.info("verified postedDate in receipt documnet" + eventRegistration.postedDate);

					Assert.assertTrue(pdfContent.contains(paymentMethodDescr));
					log.info("verified Payment Method Description in receipt documnet" + paymentMethodDescr);

					Assert.assertTrue(pdfContent.contains(eventRegistration.userName));
					log.info("verified To address in receipt documnet" + eventRegistration.userName);

					Assert.assertTrue(pdfContent.contains(testData.testDataProvider().getProperty("fromAddress")));
					log.info("verified From address in receipt documnet"
							+ testData.testDataProvider().getProperty("fromAddress"));
					System.out.println("Link is identified");
					Thread.sleep(5000);
					log.info("Receipt validations are done");
					break;
				}
			}
		return pdfContent;

	}
}
