package org.aia.testcases.membership;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.ces.FontevaCESTermDateChangeAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.FontevaMemTermDateChangeAPI;
import org.aia.pages.api.membership.FontevaSetDiscountAPI;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.RenewAPIValidation;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.ReNewUser;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.*;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.GenerateReportsListener;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;

public class TestRenew_Membership extends BaseClass {

	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	FinalPageThankYou finalPage;
	JoinAPIValidation apiValidation;
	RenewAPIValidation apiValidationRenew;
	TellusAboutYourselfPage tellAbtPage;
	DevSandBoxFonteva fontevaPage;
	RenewPage renew;
	ReNewUser fontevaRenew;
	ContactCreateUser fontevaJoin;
	SalesOrder salesOrder;
	FontevaMemTermDateChangeAPI termDateChangeAPI;
	SignUpAPI signUpAPI;
	public String inbox;
	FontevaSetDiscountAPI stDiscountAPI;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
		inbox = DataProviderFactory.getConfig().getValue("inbox");
		util = new Utility(driver, 30);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		apiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		apiValidationRenew = PageFactory.initElements(driver, RenewAPIValidation.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		fontevaPage = PageFactory.initElements(driver, DevSandBoxFonteva.class);
		renew = PageFactory.initElements(driver, RenewPage.class);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		testData = new ConfigDataProvider();
		fontevaRenew = PageFactory.initElements(driver, ReNewUser.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		termDateChangeAPI = PageFactory.initElements(driver, FontevaMemTermDateChangeAPI.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);
		stDiscountAPI = PageFactory.initElements(driver, FontevaSetDiscountAPI.class);
	}

	@Test(priority = 1, description = "Validate Renew without supplemental dues", enabled = true)
	public void ValidateRenew() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("allied", "None Selected");
		orderSummaryPage.confirmTerms("allied");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("allied");
		tellAbtPage.enterTellUsAboutYourSelfdetails("allied", "None Selected");
		// Navigate to Fonteva app and make record renew eligible.
//		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
//		final String sID = sessionID.getSessionID();
//		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
//		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
//		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
//		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));

		// Renew user
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.confirmTerms("allied");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("allied");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();

		// Verify renew mail - Commenting it as we are not receiving main within 1 or
		// 2mins (It takes around 10 mins for renew mails
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData1.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData1.get(0), receiptData1.get(2));
		util.writeCsv(dataList.get(7), dataList.get(5));
	}

	@Test(priority = 2, description = "Validate Renew for architectural Firm Owner - supplemental Dues", enabled = true, groups = {
			"Smoke" })
	public void ValidateRenewWithSupplementalDuesAFO() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		// PAC workflow
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		//// mailinator.welcomeAIAEmailLink(dataList, receiptData);

		// Navigate to Fonteva app and make record renew eligible.
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");
		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));

		// Renew user
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("architecturalFirmOwner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		// finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData1.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
		util.writeCsv(dataList.get(7), dataList.get(5));
		util.writeCsv(dataList.get(7), dataList.get(5));
	}

	@Test(priority = 3, description = "Validate Renew for sole Practitioner - supplemental Dues", enabled = true)
	public void ValidateRenewWithSupplementalDuesSP() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		//// mailinator.welcomeAIAEmailLink(dataList, receiptData);

		// Navigate to Fonteva app and make record renew eligible.
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));

		// Renew user
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("solePractitioner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		// int pac = orderSummaryPage.GetPacDonationAmount();
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
//		mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData1.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
		util.writeCsv(dataList.get(7), dataList.get(5));
	}

	@Test(priority = 4, description = "Validate Renew for architecture Firm Manager - supplemental Dues", enabled = true)
	public void ValidateRenewWithSupplementalDuesAFM() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();

		// Navigate to Fonteva app and make record renew eligible.
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("architectureFirmManager", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		//// mailinator.welcomeAIAEmailLink(dataList, receiptData);
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
		util.writeCsv(dataList.get(7), dataList.get(5));
	}

	@Test(priority = 5, description = "Validate Renew for not Sole Practitioner - supplemental Dues", enabled = true, groups = {
			"Smoke" })
	public void ValidateRenewWithSupplementalDuesNSP() throws Exception {
		ArrayList<Object> receiptData;
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		//// mailinator.welcomeAIAEmailLink(dataList, receiptData);

		// Navigate to Fonteva app and make record renew eligible.
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));

		// Renew user
//		renew.renewMembership(dataList.get(5));
		// signInpage.login(dataList.get(5), dataList.get(6));
		orderSummaryPage.enterSupplementalDuesDetails("notSolePractitioner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		// int pac = orderSummaryPage.GetPacDonationAmount();
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		receiptData = finalPage.getFinalReceiptData();

		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));

		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);

		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), receiptData.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(receiptData.get(0), receiptData.get(2));
		util.writeCsv(dataList.get(7), dataList.get(5));
	}

	@Test(priority = 6, description = "Validate sales price in sales order lines for renew  ", enabled = true)
	public void validateSalesOrderLineRenew() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.pointOffset();
		fontevaJoin.selectContactNewLogic(dataList.get(0) + " " + dataList.get(1));
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(0) + " " + dataList.get(1));
		ArrayList<Object> data = fontevaJoin.getPaymentReceiptData();
		fontevaJoin.selectContactNewLogic(dataList.get(0) + " " + dataList.get(1));
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");
//		fontevaRenew.changeTermDate(dataList.get(0)+" "+dataList.get(1));
		fontevaRenew.renewUserForSOLine(dataList.get(0) + " " + dataList.get(1));
		fontevaRenew.createSaleorderinInstallments();
		Double salesPrice = salesOrder.checkSaleorderLine();
		util.switchToTab(driver, 1).get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
//		renew.renewMembership(dataList.get(5));
		signInpage.login(dataList.get(5), testData.testDataProvider().getProperty("password"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		String aiaNational = paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		// Verify Membership renewal
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndD   ate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		apiValidationRenew.validateSalesOrderLine(salesPrice);
		util.writeCsv(dataList.get(7), dataList.get(5));
	}

	/**
	 * Suhas
	 * 
	 * @throws Exception
	 */
	@Test(priority = 7, description = "Validate visibility of download pdf button in renew  ", enabled = true)
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
//		util.switchToTab(driver, 1)
//				.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");
		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
		// Renew user
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails(testData.testDataProvider().getProperty("radioSelection"));
		util.switchToTab(driver, 1).get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContactNewLogic(dataList.get(0) + " " + dataList.get(1));
		salesOrder.selectSalesOrder();
		salesOrder.renewReceipt();
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 8, description = "Membership Renew Archipac Donation(Architect)", enabled = true)
	public void validateArchipacDonation() throws Exception {
		// Create a renew eligible member with any on from this South
		// Carolina,Oregon,Oklahoma
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pacDonation("activeUSLicense", "None Selected", "Donation");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		//
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");
		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.checkAdditionalProduct();
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
//		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
		// Validate Receipt Details
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndD   ate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		apiValidationRenew.verifyReciptDetails(receiptData1.get(0), receiptData1.get(2));
	}

	@Test(priority = 9, description = "Validate Renew flow with US bank payment", enabled = true)
	public void ValidateRenewBankPayment() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("allied", "None Selected");
		orderSummaryPage.confirmTerms("allied");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("allied");
		tellAbtPage.enterTellUsAboutYourSelfdetails("allied", "None Selected");
		// Navigate to Fonteva app and make record renew eligible.
//		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
//		final String sID = sessionID.getSessionID();
//		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
//		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
//		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
//		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");

		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));

		// Renew user
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.confirmTerms("allied");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1), "Test Institution",
				"Success", "Your account was connected.");
		String encryptedId = apiValidation.validatePDFreceiptUsBankPayments(dataList.get(0) + " " + dataList.get(1));
		ArrayList<Object> usBankReceipt = finalPage.validateUSBankFinalPage(encryptedId);
		// Verify renew mail - Commenting it as we are not receiving main within 1 or
		// 2mins (It takes around 10 mins for renew mails
		// mailinator.thanksForRenewingEmailLink(dataList, receiptData);
		util.deleteFile();
		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3),
				DataProviderFactory.getConfig().getValue("renewTermEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Non profit");
		// Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), usBankReceipt.get(0),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidationRenew.verifyReciptDetails(usBankReceipt.get(1), usBankReceipt.get(0));
		util.deleteFile();
		util.writeCsv(dataList.get(7), dataList.get(5));
	}
	
	@Test(priority=10,description="(FM-546)Replace previous year from PDF Invoice with Upcoming year on offline renew PDF invoice",enabled=true)

	public void validateYearafterRenewInOfflinePDF() throws Exception {
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
//		util.switchToTab(driver, 1)
//				.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		termDateChangeAPI.changeTermDateAPI(dataList.get(3),"2024-12-31");
		util.switchToTab(driver, 1)
				.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContactNewLogic(dataList.get(7));
		fontevaJoin.clickChevronBtn();
		salesOrder.clickOnOfflinePdfAndValidate();
		
	}

	@Test(priority = 11, description = "Dues Adjustment for Sanfrancisco chapter renewal (Architect)", enabled = true)
	public void ValidateRenewWithDuesAdjustmentForSanfranciscoChapter() throws Throwable {
		ArrayList<Object> receiptData;
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");

// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
//Renew user
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("architecturalFirmOwner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		Map<String, Double> paymentBeforeEdit = orderSummaryPage.paymentInfo("full");
		System.out.println(paymentBeforeEdit);
		// Update discount in salesorderLine API.
		stDiscountAPI.setDiscountAPI(dataList.get(3), 100.00);
		driver.navigate().refresh();
		Map<String, Double> paymentAfterEdit = orderSummaryPage.paymentInfo("full");
		System.out.println(paymentAfterEdit);
		Assert.assertNotEquals(paymentBeforeEdit, paymentAfterEdit);
		Assert.assertNotEquals(paymentBeforeEdit.get("AIA National"), paymentAfterEdit.get("AIA National"));
		Assert.assertNotEquals(paymentBeforeEdit.get("AIA California"), paymentAfterEdit.get("AIA California"));
		Assert.assertNotEquals(paymentBeforeEdit.get("Supplemental Dues - San Francisco"),
				paymentAfterEdit.get("Supplemental Dues - San Francisco"));
		Assert.assertNotEquals(paymentBeforeEdit.get("Special Assessment for the Center for Architecture + Design"),
				paymentAfterEdit.get("Special Assessment for the Center for Architecture + Design"));
		Assert.assertNotEquals(paymentBeforeEdit.get("AIA San Francisco"), paymentAfterEdit.get("AIA San Francisco"));

	}

	@Test(priority = 12, description = "Origin field value validation on SO.", enabled = true)
	public void ValidateOriginfieldvaluevalidationonSO() throws Throwable {
		ArrayList<Object> receiptData;
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);
		termDateChangeAPI.changeTermDateAPI(dataList.get(3), "2024-12-31");
//Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
//Renew user
//		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("architecturalFirmOwner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		finalPage.verifyThankYouMessage();
		finalPage.getFinalReceiptData();
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl")
				+ FontevaConnectionSOAP.getSessionID());
		fontevaJoin.selectContactNewLogic(dataList.get(0) + " " + dataList.get(1));
		fontevaRenew.navigateTOSalesOrder();
		fontevaRenew.validateSalesOrderOriginTypes();

	}

	@AfterMethod(alwaysRun = true)
	public void teardown() throws IOException {
		BrowserSetup.closeBrowser(driver);
	}
}