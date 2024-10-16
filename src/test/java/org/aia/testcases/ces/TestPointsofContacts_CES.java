package org.aia.testcases.ces;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.api.ces.FontevaConnectionSOAP;
import org.aia.pages.api.ces.JoinCESAPIValidation;
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
import org.aia.pages.ces.SecondaryPointOfContact;
import org.aia.pages.ces.SignUpPageCes;
import org.aia.pages.ces.Subscription;
import org.aia.pages.fonteva.ces.CES_PointsOfContact;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestPointsofContacts_CES extends BaseClass {

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
	CES_PointsOfContact pointsOfContact;
	SignUpAPI signUpAPI;
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
		fontevaPage = PageFactory.initElements(driver, FontevaCES.class);
		pointsOfContact = PageFactory.initElements(driver, CES_PointsOfContact.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);
   
		
	}

	/**
	 * @throws Exception
	 */
	@Test(priority = 1, description = "Validate Primary point of contact tab",enabled = true)
	public void validatePrimaryPOCTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
	}

	@Test(priority = 2, description = "(FC-291,FC-293) Verify Edit for existing Role in contact",enabled = true)
	public void validateEditForExistingRoleInContact() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String subType = organizationPage.enterOrganizationDetails(dataList, "Architecture Firm", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		// subscribePage.proratedSubscriptionNext();
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(subType);
		mailinator.ProviderApplicationReviewEmailLink(userAccount);

		for (int i = 0; i < userAccount.size(); i++)
			System.out.println("useraccount value is ***:" + userAccount.get(i));
		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(userAccount.get(0) + " " + userAccount.get(1));

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		System.out.println("sessionID is :" + sessionID);
		final String sID = sessionID.getSessionID();
		System.out.println("sessionID 2 is :" + sID);
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		pointsOfContact.clickPointsOfContact(userAccount.get(0) + " " + userAccount.get(1), paId, "Approved");
		// pointsOfContact.newPointsOfContact("CES Secondary");
		pointsOfContact.validateErrorPointsOfContact();
	}

	@Test(priority = 3, description = "(FC-292) Verify 'Delete' option for a contact",enabled = true)
	public void validateDeleteBtnPOC() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String subType = organizationPage.enterOrganizationDetails(dataList, "Architecture Firm", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		// subscribePage.proratedSubscriptionNext();
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(subType);
		mailinator.ProviderApplicationReviewEmailLink(userAccount);

		for (int i = 0; i < userAccount.size(); i++)
			System.out.println("useraccount value is ***:" + userAccount.get(i));
		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(userAccount.get(0) + " " + userAccount.get(1));

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		System.out.println("sessionID is :" + sessionID);
		final String sID = sessionID.getSessionID();
		System.out.println("sessionID 2 is :" + sID);
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		pointsOfContact.clickPointsOfContact(userAccount.get(0) + " " + userAccount.get(1), paId, "Approved");
		pointsOfContact.deletePointsOfContact();
	}

	@Test(priority = 4, description = "(FC-294,FC-295) Verify Role change of contact from Points of contact",enabled = true)
	public void validateRoleChangePOC() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String subType = organizationPage.enterOrganizationDetails(dataList, "Architecture Firm", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		// subscribePage.proratedSubscriptionNext();
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(subType);
		mailinator.ProviderApplicationReviewEmailLink(userAccount);

		for (int i = 0; i < userAccount.size(); i++)
			System.out.println("useraccount value is ***:" + userAccount.get(i));
		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(userAccount.get(0) + " " + userAccount.get(1));

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		System.out.println("sessionID is :" + sessionID);
		final String sID = sessionID.getSessionID();
		System.out.println("sessionID 2 is :" + sID);
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		pointsOfContact.clickPointsOfContact(userAccount.get(0) + " " + userAccount.get(1), paId, "Approved");
		pointsOfContact.accountroleChangePOC("CES Secondary");
	}

	@Test(priority = 5, description = "(FC-297)Verify Creation of New contact from point of contacts",enabled = true)
	public void validateNewcontact() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String subType = organizationPage.enterOrganizationDetails(dataList, "Architecture Firm", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		// subscribePage.proratedSubscriptionNext();
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(subType);
		mailinator.ProviderApplicationReviewEmailLink(userAccount);

		for (int i = 0; i < userAccount.size(); i++)
			System.out.println("useraccount value is ***:" + userAccount.get(i));
		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(userAccount.get(0) + " " + userAccount.get(1));

		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		System.out.println("sessionID is :" + sessionID);
		final String sID = sessionID.getSessionID();
		System.out.println("sessionID 2 is :" + sID);
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		pointsOfContact.clickPointsOfContact(userAccount.get(0) + " " + userAccount.get(1), paId, "Approved");
		pointsOfContact.newPointsOfContact("CES Secondary");
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
