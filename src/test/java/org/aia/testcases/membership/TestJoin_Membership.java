package org.aia.testcases.membership;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.api.membership.usBankFontevaValidationAPI;
import org.aia.pages.ces.CardPayments;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.*;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.GenerateReportsListener;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import org.aia.utility.Logging;

public class TestJoin_Membership extends BaseClass {

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
	TellusAboutYourselfPage tellAbtPage;
	ContactCreateUser fontevaJoin;
	usBankFontevaValidationAPI usBankEpaymentAPI;
	SalesOrder salesOrder;
	CardPayments cardPayments;
	SignUpAPI signUpAPI;
	public ExtentReports extent;
	public ExtentTest extentTest;
	public String inbox;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
		inbox = DataProviderFactory.getConfig().getValue("inbox");
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		apiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		cardPayments = PageFactory.initElements(driver, CardPayments.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		usBankEpaymentAPI = PageFactory.initElements(driver, usBankFontevaValidationAPI.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	@Test(groups = { "Smoke" }, priority = 1, description = "Validate Membership Signup",enabled = false)
	public void ValidateSignUpPageISOpened() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
//	    paymentInfoPage.usBankPayments(dataList.get(5),dataList.get(0)+" "+dataList.get(1),"Test Institution","Success","Your account was connected.");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
//		data.add(3, aiaNational);
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational;
		Logging.logger.info("Receipt Number is." + data.get(0));
		Logging.logger.info("Total Amount is : " + data.get(2));
		Logging.logger.info("FN : " + dataList.get(0));
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 2, description = "Validate activeUSLicense", enabled = false)
	public void ValidateActiveUSLicense() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		Logging.logger.info("Receipt Number is." + data.get(0));
		Logging.logger.info("Total Amount is : " + data.get(2));
		Logging.logger.info("FN : " + dataList.get(0));
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 3, description = "Validate activeNonUSLicense",enabled = false)
	public void ValidateActiveNonUSLicense() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeNonUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeNonUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeNonUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeNonUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Intl Associate", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 4, description = "Validate graduate",enabled = false, groups = { "Smoke", "reg" })
	public void ValidateGraduate() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("graduate", "None Selected");
		orderSummaryPage.confirmTerms("graduate");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("graduate");
		tellAbtPage.enterTellUsAboutYourSelfdetails("graduate", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 5, description = "Validate axp",enabled = false)
	public void ValidateAxp() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("axp", "None Selected");
		orderSummaryPage.confirmTerms("axp");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("axp");
		tellAbtPage.enterTellUsAboutYourSelfdetails("axp", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 6, description = "Validate noLicense",enabled = false)
	public void ValidateNoLicense() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("noLicense", "None Selected");
		orderSummaryPage.confirmTerms("noLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("noLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("noLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 7, description = "Validate supervision",enabled = false)
	public void ValidateSupervision() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("supervision", "None Selected");
		orderSummaryPage.confirmTerms("supervision");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("supervision");
		tellAbtPage.enterTellUsAboutYourSelfdetails("supervision", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 8, description = "Validate faculty",enabled = false)
	public void ValidateFaculty() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("faculty", "None Selected");
		orderSummaryPage.confirmTerms("faculty");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("faculty");
		tellAbtPage.enterTellUsAboutYourSelfdetails("faculty", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Associate", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 9, description = "Validate allied",enabled = false)
	public void ValidateAllied() throws Exception {
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
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Allied", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 10, description = "Validate For Profit CarrerType",enabled = false)
	public void ValidateForProfitCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "For Profit");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "For Profit");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "For Profit");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 11, description = "Validate Govt CarrerType",enabled = false)
	public void ValidateGovtCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Govt");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Govt");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Govt");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 12, description = "Validate Education CarrerType",enabled = false)
	public void ValidateEducationCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Education");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Education");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Education");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 13, description = "Validate Retired CarrerType",enabled = false)
	/**
	 * @throws Exception
	 */
	public void ValidateRetiredCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "Retired");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "Retired");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "Retired");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	@Test(priority = 14, description = "Validate None Selected CarrerType",enabled = false)
	/**
	 * @throws Exception
	 */
	public void ValidateNoneSelectedCarrerType() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
//		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);

		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "None Selected");
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 15, description = "Validate price rule in sales order line",enabled = false, groups = { "Smoke" })
	public void validatePriceRuleInSalesOrder() throws Exception {
		// Start the creating user
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		Reporter.log("LOG : INFO -Receipt Number is" + data.get(0));
		Reporter.log("LOG : INFO -Customer AIA Number is : " + data.get(1));
		System.out.println("Total Amount is " + data.get(2));
		System.out.println("AIA National is " + aiaNational);
		mailinator.welcomeAIAEmailLink(dataList, data);
		// Validate Membership creation - Fonteva API validations
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "None Selected");
		// Here we validate price rule is not null
		apiValidation.verifySalesOrderForPriceRule("Architect");
		JoinAPIValidation.accountID = null;

	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 16, description = "price check for order line in join membership",enabled = false)
	public void salesOrderpriceCheckJoin() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		orderSummaryPage.confirmTerms(testData.testDataProvider().getProperty("radioSelection"));
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails(testData.testDataProvider().getProperty("radioSelection"),
				testData.testDataProvider().getProperty("careerType"));
		finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		mailinator.welcomeAIAEmailLink(dataList, data);

		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("careerType"));
		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), data.get(2),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validate Receipt Details
		apiValidation.verifyReciptDetails(data.get(0), data.get(2));
		JoinAPIValidation.accountID = null;
	}

	

	@Test(priority = 17, description = "Validate visibility of download pdf button in Join  ",enabled = false)
	public void validateVisibilityDownloadPdfInJoin() throws Exception {
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
		// Navigate to fonteva for checking pdf
		util.switchToTab(driver, 1).get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaJoin.selectContactNewLogic(dataList.get(0) + " " + dataList.get(1));
		salesOrder.selectSalesOrder();
		salesOrder.joinReceipt();
	}

	@Test(groups = {
			"Smoke" }, priority = 18, description = "Validate Membership join process with us bank payments",enabled = false)
	public void validateMembershipJoinWithUsBankFlow() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("bankType"),
				testData.testDataProvider().getProperty("selectAccountType"),
				testData.testDataProvider().getProperty("connectionMsg"));
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		String encryptedId = apiValidation.validatePDFreceiptUsBankPayments(dataList.get(0) + " " + dataList.get(1));
		ArrayList<Object> usBankReceipt = finalPage.validateUSBankFinalPage(encryptedId);
		mailinator.validatePaymentInfoInMail(dataList.get(3), testData.testDataProvider().getProperty("successEmail"));
		apiValidation.verifyMemebershipCreation(dataList.get(3),
				DataProviderFactory.getConfig().getValue("termEndDate"),
				DataProviderFactory.getConfig().getValue("type_aia_national"), "Architect", "None Selected");

		// Validate sales order
		apiValidation.verifySalesOrder(DataProviderFactory.getConfig().getValue("salesOrderStatus"),
				DataProviderFactory.getConfig().getValue("orderStatus"), usBankReceipt.get(0),
				DataProviderFactory.getConfig().getValue("postingStatus"));
		// Validating epayment
		usBankEpaymentAPI.validateEpayment(dataList.get(3), testData.testDataProvider().getProperty("transactionType"),
				testData.testDataProvider().getProperty("currency"),
				testData.testDataProvider().getProperty("paymentMsg"), usBankReceipt.get(0),
				testData.testDataProvider().getProperty("paymentStatus"));
		// Validating receipt
		apiValidation.verifyReciptDetails(usBankReceipt.get(1), usBankReceipt.get(0));
		JoinAPIValidation.accountID = null;
	}

	@Test(groups = {
			"Smoke" }, priority = 19, description = "Validate Membership join process with failure negative us bank payments",enabled = false)
	public void validateMembershipJoinWithFailureUsBankFlow() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "For Profit");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("bankType"),
				testData.testDataProvider().getProperty("selectFailureAccountType"),
				testData.testDataProvider().getProperty("connectionMsg"));
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.validateUSBankFinalPageInNegativeCase();
		mailinator.validatePaymentInfoInMail(dataList.get(3),
				testData.testDataProvider().getProperty("unSuccessEmail"));
		// Here we are not validating membership & sales order because, This negative
		// scenario in the above function we are
		// Asserting unsuccessful method.
		// Validating epayment
		usBankEpaymentAPI.validateEpayment(dataList.get(3),
				testData.testDataProvider().getProperty("transactionTypeNeg"),
				testData.testDataProvider().getProperty("currencyNeg"),
				testData.testDataProvider().getProperty("paymentMsgNeg"),
				testData.testDataProvider().getProperty("amountForNegativeJoinNeg"),
				testData.testDataProvider().getProperty("paymentStatusNeg"));
		// Validating receipt
	}

	@Test(groups = {
			"Smoke" }, priority = 20, description = "Validate Membership join process with Insufficient Funds negative us bank payments",enabled = true)
	public void validateMembershipJoinWithInsufficientFundsUsBankFlow() throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "For Profit");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		paymentInfoPage.usBankPayments(dataList.get(5), dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("bankType"),
				testData.testDataProvider().getProperty("selectInsuFunds"),
				testData.testDataProvider().getProperty("connectionMsg"));
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.validateUSBankFinalPageInNegativeCase();
		mailinator.validatePaymentInfoInMail(dataList.get(3),
				testData.testDataProvider().getProperty("unSuccessEmail"));
// Here we are not validating membership & sales order because, This negative
// scenario in the above function we are
// Asserting unsuccessful method.
// Validating epayment
		usBankEpaymentAPI.validateEpayment(dataList.get(3),
				testData.testDataProvider().getProperty("transactionTypeNeg"),
				testData.testDataProvider().getProperty("currencyNeg"),
				testData.testDataProvider().getProperty("paymentMsgNeg"),
				testData.testDataProvider().getProperty("amountForNegativeJoinNeg"),
				testData.testDataProvider().getProperty("paymentStatusNeg"));
// Validating receipt
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() throws IOException {
		BrowserSetup.closeBrowser(driver);
	}
}