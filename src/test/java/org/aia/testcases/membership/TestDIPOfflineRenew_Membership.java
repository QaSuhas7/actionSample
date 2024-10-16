package org.aia.testcases.membership;

import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.RenewAPIValidation;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.ReNewUser;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestDIPOfflineRenew_Membership extends BaseClass {
	ContactCreateUser fontevaJoin;
	ReNewUser reNew;
	MailinatorAPI malinator;
	RenewAPIValidation offlinApiValidation;
	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl")+sessionID.getSessionID());
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		reNew = PageFactory.initElements(driver, ReNewUser.class);
		malinator = PageFactory.initElements(driver, MailinatorAPI.class);
		offlinApiValidation = PageFactory.initElements(driver, RenewAPIValidation.class);
	}

	/**
	 * @throws InterruptedException
	 */
	@Test(priority = 1, description = "(FM-383) Membership Offline Renew DIP Process", enabled = true, groups = {
			"Smoke" })
	public void offlineRenewProcessDIP() throws InterruptedException {
		LocalDate localDate = java.time.LocalDate.now();
		if (localDate.getMonthValue() >= 10 || localDate.getMonthValue() <= 04) {
			ArrayList<String> dataList = fontevaJoin.userData();
			// fontevaJoin.signInFonteva();
			// fontevaJoin.pointOffset();
			fontevaJoin.createUserInFonteva();
			fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
					testData.testDataProvider().getProperty("selection"));
			fontevaJoin.enterLicenseDetail();
			fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
			fontevaJoin.applyPayment(dataList.get(5));
			ArrayList<Object> data = fontevaJoin.getPaymentReceiptData();
			reNew.changeTermDate(dataList.get(5));
			reNew.renewMembershipDIP(dataList.get(5));
			reNew.applyForPayment(testData.testDataProvider().getProperty("paymentMethod"));
			fontevaJoin.applyPayment(dataList.get(5));
			ArrayList<Object> renewReciept = fontevaJoin.getPaymentReceiptData();
			// Validation of Thank you massage in email inbox after renew
			malinator.thankYouEmailforOfflineRenew(dataList.get(2));
		} else {
			System.out.println("We are not in DIP period");
		}
	}


	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
