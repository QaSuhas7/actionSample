package org.aia.testcases.membership;
import java.io.IOException;

import java.time.LocalDate;
import java.util.ArrayList;
import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.ReJoinAPIValidation;
import org.aia.pages.fonteva.ces.CES_ContactPage;
import org.aia.pages.fonteva.ces.CES_RapidOrderEntry;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.Memberships;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.CheckYourEmailPage;
import org.aia.pages.membership.FinalPageThankYou;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.RejoinPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.pages.membership.TellusAboutYourselfPage;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestDIPOfflineJoin_Membership extends BaseClass {
	MailinatorAPI mailinator;
	ContactCreateUser fontevaJoin;
	CES_ContactPage ces_ContactPage;
	public String inbox;
	static Logger log = Logger.getLogger(TestDIPOfflineJoin_Membership.class);

	@BeforeMethod
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		ces_ContactPage = PageFactory.initElements(driver, CES_ContactPage.class);
		Logging.configure();
	}

	@Test(priority = 1, description = "(FM-382) Membership Offline Join DIP Process", enabled = true)
	public void verifyDIPOfflineJoin() throws InterruptedException {
		LocalDate localDate = java.time.LocalDate.now();
		if (localDate.getMonthValue() >= 10 || localDate.getMonthValue() <= 04) {
			ArrayList<String> dataList = fontevaJoin.userData();
			fontevaJoin.createUserInFonteva();
			fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
					testData.testDataProvider().getProperty("selection"));
			fontevaJoin.enterLicenseDetail();
			fontevaJoin.createDipSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
			fontevaJoin.applyPayment(dataList.get(5));
			mailinator.thankYouEmailforOfflineJoin(dataList.get(2));
		} else {
			System.out.println("We are not in DIP period");
		}
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() throws IOException {
		BrowserSetup.closeBrowser(driver);
	}
}
