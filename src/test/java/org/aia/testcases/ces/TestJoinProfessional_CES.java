package org.aia.testcases.ces;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.api.ces.FontevaConnection;
import org.aia.pages.api.ces.FontevaConnectionSOAP;
import org.aia.pages.api.ces.JoinCESAPIValidation;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.ces.*;
import org.aia.pages.membership.DevSandBoxFonteva;
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
import org.openqa.selenium.JavascriptExecutor;
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

public class TestJoinProfessional_CES extends BaseClass {

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
	FontevaCES fontevaPage;
	SignUpAPI signUpAPI;

	public ExtentReports extent;
	public ExtentTest extentTest;

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("ces_signinUpgradeStg"));
		util = new Utility(driver, 30);
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
		fontevaPage = PageFactory.initElements(driver, FontevaCES.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);
	}

	@Test(priority = 1, description = "Validate Online JOIN for Professional E-Check.", enabled = false, groups= {"Smoke"})
	public void ValidateJoinECheckProfessional() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String subType = organizationPage.enterOrganizationDetails(dataList, "Institutional", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "Yes", "United States of America (+1)");
		additionalUsers.verifyCesPrimDetails(dataList);
		additionalUsers.addAdditionalUsers(dataList);
		additionalProviderUser.enterAdditionalProviderUserPocDetails(dataList, prefix, suffix,
				"United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(subType);
		mailinator.ProviderApplicationReviewEmailLink(userAccount);

		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(userAccount.get(0) + " " + userAccount.get(1));

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP(); 
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		//driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeProviderApplicationStatus(userAccount.get(0) + " " + userAccount.get(1), paId, "Approved");

		String checkoutpagelink = mailinator.cesProviderApprovedEmailLink(userAccount);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		// Navigate to CES toolkit link and validate link is working.
		driver.get(checkoutpagelink);
		Thread.sleep(1000);
		String pageTitle = driver.getTitle();
		// assertTrue(pageTitle.contains("Checkout"), "Checkout Provider page is
		// loaded.");
		// checkOutPageCes.enterCardDetailsCes();
		checkOutPageCes.usBankPayments(dataList.get(5),dataList.get(0)+" "+dataList.get(1),"Test Institution","Success","Your account was connected.");
		util.deleteFile();
		String encryptedId = apiValidation.validatePDFreceiptUsBankPayments(dataList.get(3));
		driver.get(DataProviderFactory.getConfig().getValue("pdfReceiptURL") + encryptedId);
		System.out.println("Please wait for Receipt download....................................");
		Thread.sleep(40000);
		String amount = util.replaceStringvalues(util.getPDFReceiptValue("Total"));
		System.out.println("Original receipt" + util.getPDFReceiptValue("Receipt Number"));
		String reciptData = util.replaceStringvalues(util.getPDFReceiptValue("Receipt Number")); 
		util.deleteFile();
		Reporter.log("LOG : INFO -Receipt Number is" + reciptData);
		Reporter.log("LOG : INFO -Customer AIA Number is : " + userAccount.get(1));
		// Verify welcome email details.
		mailinator.welcomeAIAEmailLink(userAccount);

		// Validate Provider Application & CES Provider account details - Fonteva API
		// validations
		apiValidation.verifyProviderApplicationDetails("Approved", dataList, "Professional",
				userAccount.get(0) + " " + userAccount.get(1), true, java.time.LocalDate.now().toString(),
				"AutomationOrg", "Institutional", "No");

		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Professional", "2024-12-31", false);

		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), amount,
				DataProviderFactory.getConfig().getValue("postingStatus"));

		// Validate Receipt Details
		apiValidation.verifyReciptDetails(reciptData, amount, "CES Professional");

		// Validate Primary POC
		apiValidation.verifyPointOfContact("CES Primary", userAccount.get(5),
				userAccount.get(0) + " " + userAccount.get(1));
	}

	@Test(priority = 2, description = "Validate Online JOIN - Approved for Passport.", enabled = true)
	public void ValidateJoinApproveForPassport() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String subType = organizationPage.enterOrganizationDetails(dataList, "Institutional", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "Yes", "United States of America (+1)");
		additionalUsers.verifyCesPrimDetails(dataList);
		additionalUsers.addAdditionalUsers(dataList);
		additionalProviderUser.enterAdditionalProviderUserPocDetails(dataList, prefix, suffix,
				"United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(subType);
		mailinator.ProviderApplicationReviewEmailLink(userAccount);

		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(userAccount.get(0) + " " + userAccount.get(1));

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP(); 
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		//driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaPage.changeProviderApplicationStatus(userAccount.get(0) + " " + userAccount.get(1), paId,
				"Approved for Passport");

		String checkoutpagelink = mailinator.cesProviderApprovedNewProviders(userAccount);
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		// Navigate to CES toolkit link and validate link is working.
		driver.get(checkoutpagelink);
		Thread.sleep(2000);
		String pageTitle = driver.getTitle();
		//assertTrue(pageTitle.contains("Checkout"), "Checkout Provider page is loaded.");
		checkOutPageCes.enterCardDetails();
		checkOutPageCes.savePaymentcheckBoxInpayemntGateWay();
		checkOutPageCes.clickProcessButton();
		Object amount = paymntSuccesFullPageCes.amountPaid();
		Logging.logger.info("Total Amount is : " + amount);
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		// Get Receipt number
		String reciptNumber = util.getSubString(reciptData, "");
		Reporter.log("LOG : INFO -Receipt Number is" + reciptNumber);
		Reporter.log("LOG : INFO -Customer AIA Number is : " + userAccount.get(1));
		// Verify welcome email details.
		mailinator.welcomeAIAEmailLink(userAccount);

		// Validate Provider Application & CES Provider account details - Fonteva API
		// validations
		apiValidation.verifyProviderApplicationDetails("Approved for Passport", dataList, "Professional",
				userAccount.get(0) + " " + userAccount.get(1), true, java.time.LocalDate.now().toString(),
				"AutomationOrg", "Institutional", "No");

		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails("Active", "CES Passport", "2024-12-31", false);

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

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}

}