package org.aia.testcases.membership;

import java.io.IOException;

import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.FontevaMemStatusChangeAPI;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.ReJoinAPIValidation;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.api.membership.usBankFontevaValidationAPI;
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
import org.aia.utility.GenerateReportsListener;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

public class TestReJoin_Membership extends BaseClass {
	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	Memberships fontevaPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	FinalPageThankYou finalPage;
	JoinAPIValidation apiValidation;
	TellusAboutYourselfPage tellAbtPage;
	ReJoinAPIValidation reJoinValidate;
	ContactCreateUser fontevaJoin;
	ReJoinAPIValidation reJoinAPIValidation;
	JoinAPIValidation offlinApiValidation;
	usBankFontevaValidationAPI usBankEpaymentAPI;
	RejoinPage rejoinPage;
	SalesOrder salesOrder;
	SignUpAPI signUpAPI;
	FontevaMemStatusChangeAPI fontevaMemStatusAPI;
	public String inbox;
	static Logger log = Logger.getLogger(TestReJoin_Membership.class);

	@BeforeMethod
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		apiValidation = new JoinAPIValidation(driver);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		reJoinAPIValidation = new ReJoinAPIValidation(driver);
		reJoinValidate = new ReJoinAPIValidation(driver);
		offlinApiValidation = new JoinAPIValidation(driver);
		fontevaPage = PageFactory.initElements(driver, Memberships.class);
		rejoinPage = PageFactory.initElements(driver, RejoinPage.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		usBankEpaymentAPI = PageFactory.initElements(driver, usBankFontevaValidationAPI.class);
		fontevaMemStatusAPI = PageFactory.initElements(driver, FontevaMemStatusChangeAPI.class);
		signUpAPI =PageFactory.initElements(driver, SignUpAPI.class);
		Logging.configure();
	}

	/**
	 */
	@Test(priority = 1, description = "verify the online membership rejoin in UI Application",enabled = true)
	public void validateReJoin() throws Exception {
		// User creating is starting
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		Logging.logger.info("User get created successfully");

		// Navigate to Fonteva app and make record rejoin eligible.
		fontevaMemStatusAPI.changeMembershipStatusExpDate(dataList.get(3), "2021-12-31");
		Logging.logger.info("Set status as Canclled");

		// Navigate membership portal
		driver.get(DataProviderFactory.getConfig().getValue("stagingurl_membership"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.reJoinTellUs();
		
//		driver.navigate().refresh();
//		Thread.sleep(6000);
//		tellAbtPage.reJoinTellUs();
		// Fetch the details on receipt & add details in receiptData array list.
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData2 = finalPage.getFinalReceiptData();
		// Validate Membership Rejoin - Fonteva API validations
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		reJoinValidate.verifyReciptDetails(receiptData2.get(0), receiptData2.get(2));
		util.deleteAllFile();

	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 2, description = "verify the online allied membership rejoin in UI Application",enabled = true)
	public void validateAlliedReJoin() throws Exception {
		// User creating is starting
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage
				.paymentDetails(testData.testDataProvider().getProperty("membershipSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("membershipSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		Logging.logger.info("User get created successfully");

		// Navigate to Fonteva app and make record rejoin eligible.
		fontevaMemStatusAPI.changeMembershipStatusExpDate(dataList.get(3), "2021-12-31");
		Logging.logger.info("Set status as Canclled");
		// Navigate membership portal
		driver.get(DataProviderFactory.getConfig().getValue("stagingurl_membership"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipSelection"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("membershipSelection"));
		tellAbtPage.reJoinTellUs();
		// Fetch the details on receipt & add details in receiptData array list.
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData2 = finalPage.getFinalReceiptData();
		// Validate Membership Rejoin - Fonteva API validations
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		reJoinValidate.verifyReciptDetails(receiptData2.get(0), receiptData2.get(2));
		util.deleteAllFile();

	}

	@Test(priority = 3, description = "verify the online Associate membership rejoin in UI Application",enabled = true)
	public void validateAssociateReJoin() throws Exception {
		// User creating is starting
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipFaculty"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipFaculty"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage
				.paymentDetails(testData.testDataProvider().getProperty("membershipFaculty"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("membershipFaculty"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		Logging.logger.info("User get created successfully");

		// Navigate to Fonteva app and make record rejoin eligible.
		fontevaMemStatusAPI.changeMembershipStatusExpDate(dataList.get(3), "2021-12-31");
		Logging.logger.info("Set status as Canclled");
		// Navigate membership portal
		driver.get(DataProviderFactory.getConfig().getValue("stagingurl_membership"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("membershipFaculty"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("membershipFaculty"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("membershipFaculty"));
		tellAbtPage.reJoinTellUs();
		// Fetch the details on receipt & add details in receiptData array list.
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData2 = finalPage.getFinalReceiptData();
		// Validate Membership Rejoin - Fonteva API validations
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipAssociate"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData2.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		reJoinValidate.verifyReciptDetails(receiptData2.get(0), receiptData2.get(2));
		util.deleteAllFile();

	}

	@Test(priority = 4, description = "Validate visibility of download pdf button in rejoin  ",enabled = true)
	public void validateVisibilityDownloadPdfBtn() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		fontevaMemStatusAPI.changeMembershipStatusExpDate(dataList.get(3), "2021-12-31");
//		 Navigate to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("stagingurl_membership"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.reJoinTellUs();
		util.switchToTab(driver, 1).get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContactNewLogic(dataList.get(0) + " " + dataList.get(1));
		salesOrder.selectSalesOrder();
		salesOrder.reJoinReceipt();
		util.deleteAllFile();
	}

	@Test(priority = 5, description = "verify the online membership rejoin in UI Application using us bank payment method",enabled = true)
	public void validateReJoinUSPaymentBank() throws Exception {
		// User creating is starting
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		Logging.logger.info("User get created successfully");

		// Navigate to Fonteva app and make record rejoin eligible.
		fontevaMemStatusAPI.changeMembershipStatusExpDate(dataList.get(3), "2021-12-31");
		Logging.logger.info("Set status as Canclled");

		// Navigate membership portal
		driver.get(DataProviderFactory.getConfig().getValue("stagingurl_membership"));
		// Enter Email in membership page
		rejoinPage.reJoinMembership(dataList.get(5));
		// Enter detail in primary info page
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		// Confirm terms and proceed for payment.
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1), "Test Institution",
				"Success", "Your account was connected.");
		tellAbtPage.reJoinTellUs();
		// Fetch the details on receipt & add details in receiptData array list.
		String encryptedId = apiValidation.validatePDFreceiptUsBankPayments(dataList.get(0) + " " + dataList.get(1));
		ArrayList<Object> usBankReceipt = finalPage.validateUSBankFinalPage(encryptedId);
		// Validate Membership Rejoin - Fonteva API validations
		reJoinValidate.validateReJoinMemebership(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("careerType"));
//		 Validate sales order
		reJoinValidate.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), usBankReceipt.get(0),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validating epayment
		usBankEpaymentAPI.validateEpayment(dataList.get(3), "bank_account", "USD", "succeeded", usBankReceipt.get(0), "Success");
		// Validating receipt
		reJoinValidate.verifyReciptDetails(usBankReceipt.get(1), usBankReceipt.get(0));
		util.deleteAllFile();

	}

	@AfterMethod
	public void teardown() throws IOException {
		
		BrowserSetup.closeBrowser(driver);
	}
}
