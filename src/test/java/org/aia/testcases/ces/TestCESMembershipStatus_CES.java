package org.aia.testcases.ces;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.api.ces.FontevaCESTermDateChangeAPI;
import org.aia.pages.api.ces.RenewCESAPIValidation;
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
import org.aia.utility.GenerateReportsListener;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.apache.poi.util.PackageHelper;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestCESMembershipStatus_CES extends BaseClass {
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
		rapidOrderEntery = PageFactory.initElements(driver, CES_RapidOrderEntry.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);

	}

	@Test(priority = 1, description = "(FC-331) validate Term creation for $0 CES Membership type", enabled = true)
	public void validateTermCreation$0CESMembershipType() throws Throwable {
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
		Object amount = paymntSuccesFullPageCes.amountPaid();
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesmembershipType1"),
				testData.testDataProvider().getProperty("quickElement1"));
		rapidOrderEntery.validateDeleteCESMembership();
		// ces_ContactPage.validateAvailableMemType();
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3),
				(testData.testDataProvider().getProperty("changeTermDate")),
				testData.testDataProvider().getProperty("cesmembershipType0"));
		// Validate Provider Application & CES Provider account details - Fonteva API
		// validations
		apiValidation.verifyProviderApplicationDetails("Approved", dataList, "Passport",
				dataList.get(0) + " " + dataList.get(1), true, util.todaysDate().toString(), "AutomationOrg", "Other",
				"No");

		// Validate CES Provider account details - Fonteva API validations
		apiValidation.verifyProviderApplicationAccountDetails(
				testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType0"),
				testData.testDataProvider().getProperty("changeTermDate"), false);
		// Validate Primary POC
		apiValidation.verifyPointOfContact("CES Primary", dataList.get(5), dataList.get(0) + " " + dataList.get(1));
	}

	@Test(priority = 2, description = "(FC-172) Validate Membership Type when term date for CES Membership is updated",enabled = true)
	public void verifyTermDateCESMembershipType() throws Throwable {
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
		Object amount = paymntSuccesFullPageCes.amountPaid();
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesmembershipType1"),
				testData.testDataProvider().getProperty("quickElement1"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType1"));

	}

	@Test(priority = 3, description = "(FC-164) Verify Membership status when term date for CES Membership is updated",enabled = true, groups = {
			"Smoke" })
	public void validateCESMembershipStatus() throws Throwable {
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
		// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesmembershipType1"),
				testData.testDataProvider().getProperty("quickElement1"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
	}

	@Test(priority = 4, description = "(FC-165) Verify update the NON-CES membership as the latest Term end date",enabled = true)
	public void verifyTermDateNonCESMembershipType() throws Throwable {
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
		Object amount = paymntSuccesFullPageCes.amountPaid();
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("nonCESMembershipType0"),
				testData.testDataProvider().getProperty("quickElement1"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType0"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType0"));
	}

	@Test(priority = 5, description = "(FC-166) Verify membership status when Term date for one Membership is updated under multiple CES Membership's",enabled = true, groups = {
			"Smoke" })
	public void validateMultipleCESMembershipStatus() throws Throwable{
//Here we create the user
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
//Navigate to Fonteva side
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesmembershipType1"),
				testData.testDataProvider().getProperty("quickElement1"));
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesMembershipType2"),
				testData.testDataProvider().getProperty("quickElement2"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
	}

	@Test(priority = 6, description = "(FC-167) Verify membership status when updated same for multiple and different CES Membership types",enabled = true, groups = {
			"Smoke" })
	public void validateMultipleCESMembershipTypeStatus() throws Throwable {
//Here we create the user
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
//Navigate to Fonteva side
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesmembershipType1"),
				testData.testDataProvider().getProperty("quickElement1"));
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesMembershipType2"),
				testData.testDataProvider().getProperty("quickElement2"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
	}

	@Test(priority = 7, description = "(FC-168) Verify membership status after adding a NON-CES Membership",enabled = true)
	public void verifyNonCESMembershipStatus() throws Throwable {
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
		Object amount = paymntSuccesFullPageCes.amountPaid();
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("nonCESMembershipType0"),
				testData.testDataProvider().getProperty("quickElement1"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType0"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType0"));
	}

	@Test(priority = 8, description = "(FC-173) Verify CES Membership type when NON-CES membership updated as the latest Term end date",enabled = true)
	public void verifyNonCESMembershipType() throws Throwable {
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
		Object amount = paymntSuccesFullPageCes.amountPaid();
		// Navigate to Fonteva app and make record renew eligible.
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("nonCESMembershipType0"),
				testData.testDataProvider().getProperty("quickElement1"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType0"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType0"));
	}

	@Test(priority = 9, description = "(FC-174) Verify membership Type when Term date is updated under multiple CES Membership's",enabled = true, groups = {
			"Smoke" })
	public void validateMultipleCESMembershipType() throws Throwable {
//Here we create the user
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
//Navigate to Fonteva side
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesmembershipType1"),
				testData.testDataProvider().getProperty("quickElement1"));
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesMembershipType2"),
				testData.testDataProvider().getProperty("quickElement2"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType1"));
	}

	@Test(priority = 10, description = "(FC-175) Verify membership type when term updated for multiple and different CES Membership types of same Term End date",enabled = true, groups = {
			"Smoke" })
	public void validateMultipleCESMemTypes() throws Throwable {
//Here we create the user
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
//Navigate to Fonteva side
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesMembershipType2"),
				testData.testDataProvider().getProperty("quickElement2"));
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesmembershipType1"),
				testData.testDataProvider().getProperty("quickElement1"));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesMembershipType2"));
		termDateChangeApi.getCESAccountDetails(testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesMembershipType2"));
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
