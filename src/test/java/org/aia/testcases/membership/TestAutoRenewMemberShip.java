package org.aia.testcases.membership;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.fonteva.ces.CES_ContactPage;
import org.aia.pages.fonteva.memberPortal.AccountAcessForContact;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.Memberships;
import org.aia.pages.memberPortal.MemberPortal;
import org.aia.testcases.memberPortal.CommonMethodsForMP;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestAutoRenewMemberShip extends BaseClass  {
	ContactCreateUser fontevaJoin;
	MailinatorAPI malinator;
	Memberships fontevaPage;
	CES_ContactPage ces_ContactPage;
	JoinAPIValidation offlinApiValidation;
	CommonMethodsForMP comMethodsForMP;
	MailinatorAPI mailinator;
	AccountAcessForContact accAcessForContact;
	MemberPortal memberPortal;
	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		sessionID=new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl")+sessionID.getSessionID());
		util = new Utility(driver, 30);
		mailinator = new MailinatorAPI(driver);
		testData = new ConfigDataProvider();
		comMethodsForMP = new CommonMethodsForMP(driver);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		malinator = PageFactory.initElements(driver, MailinatorAPI.class);
		fontevaPage = PageFactory.initElements(driver, Memberships.class);
		ces_ContactPage = PageFactory.initElements(driver, CES_ContactPage.class);
		offlinApiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		accAcessForContact = PageFactory.initElements(driver, AccountAcessForContact.class);
		memberPortal = PageFactory.initElements(driver, MemberPortal.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	/**
	 * @throws InterruptedException
	 */
	@Test(priority = 1, description = "Fm-504 Auto renew option for membership Offline Join Process(Admin Flow)",enabled = true)
	public void offlineJoinProcessAutoRenewOptionAdminFlow() throws InterruptedException {

		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		ArrayList<String> dataList = fontevaJoin.userData();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrderAutoRenew(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		ArrayList<Object> data =fontevaJoin.getPaymentReceiptData();
		System.out.println(data);
		fontevaPage.navigateToMemberShip();
		fontevaPage.autoRenewSectionValidation();
	}
	
	@Test(priority = 2, description = "Fm-506 Auto renew option for membership Offline Join Process(days to renew>30))",enabled = true)
	public void offlineJoinProcessAutoRenewOptionDaysToRenewThirty() throws InterruptedException {
		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		ArrayList<String> dataList = fontevaJoin.userData();
		//fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrderAutoRenew(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		ArrayList<Object> data =fontevaJoin.getPaymentReceiptData();
		System.out.println(data);
		malinator.thankYouEmailforOfflineJoin(dataList.get(2));
		//Validation of Thank you massage in email inbox after register.
		malinator.thankYouEmailforOfflineJoinAutoRenew(dataList.get(2));
	}
	
	@Test(priority = 3, description = "Fm-514 Auto renew details after contact transfer(Admin Flow)",enabled = true)
	public void offlineJoinProcessAutoRenewDetails_AfterContactTransfer() throws InterruptedException {

		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		ArrayList<String> dataList = fontevaJoin.userData();
		//fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrderAutoRenew(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		ArrayList<Object> data =fontevaJoin.getPaymentReceiptData();
		System.out.println(data);
		fontevaPage.navigateToMemberShip();
		fontevaPage.autoRenewSectionValidation();
		fontevaJoin.selectContactNewLogic(dataList.get(5));
		ces_ContactPage.verifyMemTransferApplicationProcess("Home", "United States", "94102");
		ces_ContactPage.approveORRejectButton();
		fontevaJoin.selectContactNewLogic(dataList.get(5));
//		ces_ContactPage.navigateToAccount(dataList.get(5));
		fontevaPage.selectMembership();
		fontevaPage.autoRenewSectionValidation();
		fontevaJoin.selectContactNewLogic(dataList.get(5));
//		fontevaPage.navigateTOContactPage(dataList.get(5));
		fontevaPage.navigatememberShipAccountLinks("Membership_Transfers","Membership Transfers"); 
		driver.navigate().back();
		fontevaPage.navigatememberShipAccountLinks("Sales_Orders","Sales Orders");
	}
	
	@Test(description = "FM-517 Messaging for Updating payment information(My Account)",enabled = true, priority = 4)
	public void myAccountAccessForContact() throws Throwable {
		ArrayList<String> dataList = fontevaJoin.userData();
		//fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrderAutoRenew(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		ArrayList<Object> data =fontevaJoin.getPaymentReceiptData();
		System.out.println(data);
		fontevaPage.navigateToMemberShip();
		fontevaPage.autoRenewSectionValidation();
		fontevaJoin.selectContactNewLogic(dataList.get(5));
//		fontevaPage.navigateTOContactPage(dataList.get(5));
		accAcessForContact.clickDropDownInActionContainer();
		accAcessForContact.verifyFieldsMemberPortal("AIA Customer");
		accAcessForContact.clickDropDownInActionContainer();
		accAcessForContact.optionsInactionContainer();
		ArrayList<String> memberShipInfo = memberPortal.validateMembershipFields();
		memberPortal.validateAutorenwalSection();
	}



}
