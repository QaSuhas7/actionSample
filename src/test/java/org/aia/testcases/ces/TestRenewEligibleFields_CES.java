package org.aia.testcases.ces;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.api.ces.FontevaCESTermDateChangeAPI;
import org.aia.pages.api.ces.JoinCESAPIValidation;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.ces.AdditionalProviderUser;
import org.aia.pages.ces.AdditionalUsers;
import org.aia.pages.ces.CheckOutPageCes;
import org.aia.pages.ces.CloseBtnPageCes;
import org.aia.pages.ces.FontevaCES;
import org.aia.pages.ces.LoginPageCes;
import org.aia.pages.ces.Organization;
import org.aia.pages.ces.PaymentSuccessFullPageCes;
import org.aia.pages.ces.PrimaryPointOfContact;
import org.aia.pages.ces.ProviderStatement;
import org.aia.pages.ces.RenewCESPage;
import org.aia.pages.ces.SecondaryPointOfContact;
import org.aia.pages.ces.SignUpPageCes;
import org.aia.pages.ces.Subscription;
import org.aia.pages.fonteva.ces.CES_ContactPage;
import org.aia.pages.fonteva.ces.CES_Memberships;
import org.aia.pages.fonteva.ces.CES_RapidOrderEntry;
import org.aia.pages.fonteva.ces.CES_ReNewUser;
import org.aia.pages.fonteva.ces.CES_SalesOrder;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.DateUtils;
import org.aia.utility.GenerateReportsListener;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestRenewEligibleFields_CES extends BaseClass {
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
	RenewCESPage renew;
	CES_ReNewUser reNewUser;
	CES_ContactPage ces_ContactPage;
	CES_Memberships ces_membership;
	FontevaCESTermDateChangeAPI termDateChangeApi;
	JoinCESAPIValidation apiValidation;
	FontevaCES fontevaPage;
	FontevaCESTermDateChangeAPI cesTermDateChangeAPI;
	CES_SalesOrder salesorder;
	CES_RapidOrderEntry rapidOrderEntery;
	SignUpAPI signUpAPI;

	public ExtentReports extent;
	public ExtentTest extentTest;
	final static Logger logger = Logger.getLogger(TestCESMembershipStatus_CES.class);
	DateUtils dateUtils = new DateUtils();

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
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
		fontevaPage = PageFactory.initElements(driver, FontevaCES.class);
		renew = PageFactory.initElements(driver, RenewCESPage.class);
		reNewUser = PageFactory.initElements(driver, CES_ReNewUser.class);
		ces_ContactPage = PageFactory.initElements(driver, CES_ContactPage.class);
		ces_membership = PageFactory.initElements(driver, CES_Memberships.class);
		termDateChangeApi = PageFactory.initElements(driver, FontevaCESTermDateChangeAPI.class);
		apiValidation = PageFactory.initElements(driver, JoinCESAPIValidation.class);
		fontevaPage = PageFactory.initElements(driver, FontevaCES.class);
		cesTermDateChangeAPI = PageFactory.initElements(driver, FontevaCESTermDateChangeAPI.class);
		ces_ContactPage = PageFactory.initElements(driver, CES_ContactPage.class);
		salesorder = PageFactory.initElements(driver, CES_SalesOrder.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);
	}

	@Test(priority = 1, description = "(FC-181) Verify renew eligible fields when the current date of renewal is with-in the Pre-Term Renewal window",enabled = true)
	public void validateRenewEligibleFieldWithinPreTermRenwal() throws Throwable {
// Here we create the user
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
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
// Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		ces_membership.selectMembership();
		ces_membership.changePreAndPostTermRenewalDays("1", "0");
		String changetermDate = DateUtils.getDate(1, "yyyy-MM-dd");
		System.out.println("changetermDate to:" + changetermDate);
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), changetermDate, "Ces Passport");
		cesTermDateChangeAPI.validateisproviderRenewEligible(true);
		ces_membership.changePreAndPostTermRenewalDays(
				testData.testDataProvider().getProperty("setPreviousPreRenwalDays"),
				testData.testDataProvider().getProperty("setPreviousPostRenwalDays"));
	}

	@Test(priority = 2, description = "(FC-197) Verify renew eligible fields when the current date of renewal is exactly on the first day of the Pre-Term Renewal window",enabled = true)
	public void validateRenewEligibleFieldFirstdayPreTermRenwal() throws Throwable {
// Here we create the user
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
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
// Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		ces_membership.selectMembership();
		ces_membership.changePreAndPostTermRenewalDays("1", "0");
		String changetermDate = DateUtils.getDate(0, "yyyy-MM-dd");
		System.out.println("changetermDate to:" + changetermDate);
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), changetermDate, "Ces Passport");
		cesTermDateChangeAPI.validateisproviderRenewEligible(false);
		ces_membership.changePreAndPostTermRenewalDays(
				testData.testDataProvider().getProperty("setPreviousPreRenwalDays"),
				testData.testDataProvider().getProperty("setPreviousPostRenwalDays"));
	}

	@Test(priority = 3, description = "(FC-199) Verify renew eligible fields when the current date of renewal is before the Pre-Term Renewal window",enabled = true)
	public void validateRenewEligibleFieldBeforePreTermRenwal() throws Throwable {
// Here we create the user
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
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
// Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		ces_membership.selectMembership();
		ces_membership.changePreAndPostTermRenewalDays("1", "0");
		String changetermDate = DateUtils.getDate(-1, "yyyy-MM-dd");
		System.out.println("changetermDate to:" + changetermDate);
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), changetermDate, "Ces Passport");
		cesTermDateChangeAPI.validateisproviderRenewEligible(false);
		ces_membership.changePreAndPostTermRenewalDays(
				testData.testDataProvider().getProperty("setPreviousPreRenwalDays"),
				testData.testDataProvider().getProperty("setPreviousPostRenwalDays"));
	}

	@Test(priority = 4, description = "(FC-182) Verify renew eligible fields when the current date of renewal is with-in the Post-Term Renewal window",enabled = true)
	public void validateRenewEligibleFieldWithinPostTermRenwal()throws Throwable{
// Here we create the user
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
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
// Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		ces_membership.selectMembership();
		ces_membership.changePreAndPostTermRenewalDays("0", "1");
		String changetermDate = DateUtils.getDate(-1, "yyyy-MM-dd");
		System.out.println("changetermDate to:" + changetermDate);
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), changetermDate, "Ces Passport");
		cesTermDateChangeAPI.validateisproviderRenewEligible(true);
		ces_membership.changePreAndPostTermRenewalDays(
				testData.testDataProvider().getProperty("setPreviousPreRenwalDays"),
				testData.testDataProvider().getProperty("setPreviousPostRenwalDays"));
	}

	@Test(priority = 5, description = "(FC-198) Verify renew eligible fields when the current date of renewal is exactly on the last day of the Post-Term Renewal window",enabled = true)
	public void validateRenewEligibleFieldLastdayPostTermRenwal() throws Throwable{
// Here we create the user
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
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
// Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		ces_membership.selectMembership();
		ces_membership.changePreAndPostTermRenewalDays("0", "1");
		String changetermDate = DateUtils.getDate(0, "yyyy-MM-dd");
		System.out.println("changetermDate to:" + changetermDate);
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), changetermDate, "Ces Passport");
		cesTermDateChangeAPI.validateisproviderRenewEligible(true);
		ces_membership.changePreAndPostTermRenewalDays(
				testData.testDataProvider().getProperty("setPreviousPreRenwalDays"),
				testData.testDataProvider().getProperty("setPreviousPostRenwalDays"));
	}

	@Test(priority = 6, description = "(FC-429) Verify renew eligible fields when the current date of renewal is after the Post-Term Renewal window",enabled = true)
	public void validateRenewEligibleFieldAfterPostTermRenwal() throws Throwable {
// Here we create the user
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
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
// Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		ces_membership.selectMembership();
		ces_membership.changePreAndPostTermRenewalDays("0", "1");
		String changetermDate = DateUtils.getDate(1, "yyyy-MM-dd");
		System.out.println("changetermDate to:" + changetermDate);
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), changetermDate, "Ces Passport");
		cesTermDateChangeAPI.validateisproviderRenewEligible(false);
		ces_membership.changePreAndPostTermRenewalDays(
				testData.testDataProvider().getProperty("setPreviousPreRenwalDays"),
				testData.testDataProvider().getProperty("setPreviousPostRenwalDays"));
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
