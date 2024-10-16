package org.aia.testcases.ces;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.api.ces.JoinCESAPIValidation;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.usBankFontevaValidationAPI;
import org.aia.pages.ces.*;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.google.inject.Key;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

public class TestJoinPassport_CES extends BaseClass {

	SignUpPageCes signUpPage;
	SignInPage signInpage;
	CloseBtnPageCes closeButtnPage;
	MailinatorCESAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	LoginPageCes loginPageCes;
	PrimaryPointOfContact primarypocPage;
	Organization organizationPage;
	Subscription subscribePage;
	SecondaryPointOfContact secPoc;
	AdditionalUsers additionalUsers;
	AdditionalProviderUser additionalProviderUser;
	ProviderStatement providerStatement;
	CheckOutPageCes checkOutPageCes;
	PaymentSuccessFullPageCes paymntSuccesFullPageCes;
	JoinCESAPIValidation apiValidation;
	SignUpAPI signUpAPI;
	usBankFontevaValidationAPI usBankEpaymentAPI;

	public ExtentReports extent;
	public ExtentTest extentTest;
	final static Logger logger = Logger.getLogger(TestJoinPassport_CES.class);

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("ces_signinUpgradeStg"));
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		signUpPage = PageFactory.initElements(driver, SignUpPageCes.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CloseBtnPageCes.class);
		mailinator = PageFactory.initElements(driver, MailinatorCESAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		loginPageCes = PageFactory.initElements(driver, LoginPageCes.class);
		primarypocPage = PageFactory.initElements(driver, PrimaryPointOfContact.class);
		organizationPage = PageFactory.initElements(driver, Organization.class);
		subscribePage = PageFactory.initElements(driver, Subscription.class);
		secPoc = PageFactory.initElements(driver, SecondaryPointOfContact.class);
		additionalUsers = PageFactory.initElements(driver, AdditionalUsers.class);
		additionalProviderUser = PageFactory.initElements(driver, AdditionalProviderUser.class);
		providerStatement = PageFactory.initElements(driver, ProviderStatement.class);
		checkOutPageCes = PageFactory.initElements(driver, CheckOutPageCes.class);
		paymntSuccesFullPageCes = PageFactory.initElements(driver, PaymentSuccessFullPageCes.class);
		apiValidation = PageFactory.initElements(driver, JoinCESAPIValidation.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);
		usBankEpaymentAPI= PageFactory.initElements(driver, usBankFontevaValidationAPI.class);
		paymentInfoPage =PageFactory.initElements(driver, PaymentInformation.class);
	}

	@Test(priority = 1, description = "Validate creation of CES passport membership and view receipt.",enabled = false, groups = {
			"Smoke" })
	public void ValidateReceiptForPassportJoin() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
//		checkOutPageCes.usBankPayments(dataList.get(5),dataList.get(0)+" "+dataList.get(1),"Test Institution","Success","Your account was connected.");
		checkOutPageCes.SubscriptionType(text);
//		Commented below line due to some issue on view receipt page.
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
//		Modification made in below function
//		util.deleteFile();
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt(); 
		String encryptedId = apiValidation.validatePDFreceiptUsBankPayments(dataList.get(3));
		driver.get(DataProviderFactory.getConfig().getValue("pdfReceiptURL") + encryptedId);
		System.out.println("Please wait for Receipt download....................................");
		Thread.sleep(25000);
		String dues = util.replaceStringvalues(util.getPDFReceiptValue("Total"));
		System.out.println("Original receipt"+util.getPDFReceiptValue("Receipt Number"));
		String receiptNumber= util.replaceStringvalues(util.getPDFReceiptValue("Receipt Number"));
		util.deleteFile();
		System.out.println("My ReceiptData:"+reciptData);
		//Get Receipt number 
		String reciptNumber = util.getSubString(reciptData, "" );
		System.out.println("Receipt Number is :"+reciptNumber);
		Logging.logger.info("Receipt Number is :" + reciptNumber); 
		Logging.logger.info("Account Name is : " + dataList.get(0));
	}

	@Test(priority = 2, description = "Validate email after making the payment.",enabled = false)
	public void ValidateEmailPassportJoin() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
//		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		// Get Receipt number
		String reciptNumber = util.getSubString(reciptData, "");
		Reporter.log("LOG : INFO -Receipt Number is" + reciptNumber);
		Reporter.log("LOG : INFO -Customer AIA Number is : " + userAccount.get(1));
		// Verify welcome email details.
		mailinator.welcomeAIAEmailLink(userAccount);
	}
	
	
	@Test(priority=3, description="Validate Join Passport.",enabled = false)
	public void ValidatePassportJoin() throws Throwable
	{
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
//		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid();
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		// Get Receipt number
		String reciptNumber = util.getSubString(reciptData, "");
		System.out.println(reciptNumber);
		String encryptedId = apiValidation.validatePDFreceiptUsBankPayments(dataList.get(3));
		driver.get(DataProviderFactory.getConfig().getValue("pdfReceiptURL") + encryptedId);
		System.out.println("Please wait for Receipt download....................................");
		Thread.sleep(20000);
		// Validate Provider Application & CES Provider account details - Fonteva API
		// validations
		apiValidation.verifyProviderApplicationDetails("Approved", userAccount, "Passport",
				userAccount.get(0) + " " + userAccount.get(1), true, java.time.LocalDate.now().toString(),
				"AutomationOrg", "Other", "No");

		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Passport", "2025-12-31", false);

		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), amount,
				DataProviderFactory.getConfig().getValue("postingStatus"));

		// Validate Receipt Details
		apiValidation.verifyReciptDetails(reciptData, amount, "CES Passport");

		// Validate Primary POC
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5),
				userAccount.get(0) + " " + userAccount.get(1));

	}

	@Test(priority = 4, description = "Validate Join Passport, with additional users.",enabled = false, groups = {
			"Smoke" })
	public void ValidatePassportJoinWithAdditionalUser() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "Yes", "United States of America (+1)");
		additionalUsers.verifyCesPrimDetails(dataList);
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid();
		logger.info("Total Amount is 2: " + amount);
		System.out.println("Total Amount is 2: " +amount);
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt(); 
		//Get Receipt number 
		String reciptNumber = util.getSubString(reciptData, "" );
		
		// Validate Provider Application & CES Provider account details - Fonteva API validations
		  apiValidation.verifyProviderApplicationDetails("Approved", userAccount, "Passport", userAccount.get(0)+" "+userAccount.get(1), 
				  true, java.time.LocalDate.now().toString(), "AutomationOrg", "Other", "No"); 
		  
		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Passport", "2025-12-31", false);

		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), amount,
				DataProviderFactory.getConfig().getValue("postingStatus"));

		// Validate Receipt Details
		apiValidation.verifyReciptDetails(reciptData, amount, "CES Passport");

		// Validate Primary POC
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5),
				userAccount.get(0) + " " + userAccount.get(1));

	}

	@Test(priority = 5, description = "Validate Join Passport with US payment bank.",enabled = true)
	public void ValidatePassportJoinWithUSBankPayment() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("bankType"),
				testData.testDataProvider().getProperty("selectAccountType"),
				testData.testDataProvider().getProperty("connectionMsg"));
		mailinator.validatePaymentInfoInMail(dataList.get(3), testData.testDataProvider().getProperty("successEmailCES"));
//		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		util.deleteFile();
		String encryptedId = apiValidation.validatePDFreceiptUsBankPayments(dataList.get(3));
		driver.get(DataProviderFactory.getConfig().getValue("pdfReceiptURL") + encryptedId);
		System.out.println("Please wait for Receipt download....................................");
		Thread.sleep(40000);
		String amount = util.replaceStringvalues(util.getPDFReceiptValue("Total"));
		System.out.println("Original receipt" + util.getPDFReceiptValue("Receipt Number"));
		String reciptData = util.replaceStringvalues(util.getPDFReceiptValue("Receipt Number")); 
		util.deleteFile();
		// Validate Provider Application & CES Provider account details - Fonteva API
		// validations
		apiValidation.verifyProviderApplicationDetails("Approved", userAccount, "Passport",
				userAccount.get(0) + " " + userAccount.get(1), true, java.time.LocalDate.now().toString(),
				"AutomationOrg", "Other", "No");

		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Passport", "2025-12-31", false);

		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), amount,
				DataProviderFactory.getConfig().getValue("postingStatus"));
		//Validation of ePayment 
		usBankEpaymentAPI.validateEpayment(dataList.get(3), testData.testDataProvider().getProperty("transactionType"),
				testData.testDataProvider().getProperty("currency"),
				testData.testDataProvider().getProperty("paymentMsg"),
				testData.testDataProvider().getProperty("amountForNegativeJoin"),
				testData.testDataProvider().getProperty("paymentStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(reciptData, amount, "CES Passport");

		// Validate Primary POC
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5),
				userAccount.get(0) + " " + userAccount.get(1));

	}

	@Test(priority = 6, description = "Validate Join Passport with failure US payment bank.",enabled = false)
	public void ValidatePassportJoinWithFailureUSBankPayment() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("bankType"),
				testData.testDataProvider().getProperty("selectFailureAccountType"),
				testData.testDataProvider().getProperty("connectionMsg"));
		mailinator.validatePaymentInfoInMail(dataList.get(3), testData.testDataProvider().getProperty("unSuccessEmail"));
		//Validate ePyament in fonteva
		usBankEpaymentAPI.validateEpayment(dataList.get(3),
				testData.testDataProvider().getProperty("transactionTypeNeg"),
				testData.testDataProvider().getProperty("currencyNeg"),
				testData.testDataProvider().getProperty("paymentMsgNeg"),
				testData.testDataProvider().getProperty("amountForNegativeJoinNeg"),
				testData.testDataProvider().getProperty("paymentStatusNeg"));
	}

	@Test(priority = 7, description = "Validate Join Passport with Insufficient Funds US payment bank.",enabled = false)
	public void ValidatePassportJoinWithInsufficientFundsUSBankPayment() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("bankType"),
				testData.testDataProvider().getProperty("selectInsuFunds"),
				testData.testDataProvider().getProperty("connectionMsg"));
		mailinator.validatePaymentInfoInMail(dataList.get(3), testData.testDataProvider().getProperty("unSuccessEmail"));
		//Validate ePyament in fonteva
		usBankEpaymentAPI.validateEpayment(dataList.get(3),
				testData.testDataProvider().getProperty("transactionTypeNeg"),
				testData.testDataProvider().getProperty("currencyNeg"),
				testData.testDataProvider().getProperty("paymentMsgNeg"),
				testData.testDataProvider().getProperty("amountForNegativeJoinNeg"),
				testData.testDataProvider().getProperty("paymentStatusNeg"));
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		util.deleteFile();
		BrowserSetup.closeBrowser(driver);
	}

}