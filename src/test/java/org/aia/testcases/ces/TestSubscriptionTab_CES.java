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

public class TestSubscriptionTab_CES extends BaseClass {
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
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);
		
	}

	@Test(priority = 1, description = "(FC- 299,FC-300) Validate Subscription Tab", enabled = true)
	public void validateSubscriptionTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.enterOrganizationDetails(dataList, "Other", "No", "United States of America (+1)");
		subscribePage.verifySubscriptionTabText();
		subscribePage.refreshFunction();
		// Get Provider application ID
				String paId = apiValidation.getProviderApplicationID(userAccount.get(0) + " " + userAccount.get(1));
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		System.out.println("sessionID is :" + sessionID);
		final String sID = sessionID.getSessionID();
		System.out.println("sessionID 2 is :" + sID);
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		fontevaPage.checkUserInProviderApplication(paId);
	}


	@Test(priority = 2, description = "(FC- 305) Validate Secondary POC Refresh Tab", enabled = true)

	public void validateSecondaryPOCRefreshTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String subType = organizationPage.enterOrganizationDetails(dataList, "Architecture Firm", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(subType, "Yes", null, "Non-profit");
		secPoc.secondaryPOCTabRefresh();
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		System.out.println("sessionID is :" + sessionID);
		final String sID = sessionID.getSessionID();
		System.out.println("sessionID 2 is :" + sID);
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		fontevaPage.checkUserInProviderApplication(dataList.get(0) + " " + dataList.get(1));
		
	}


	@Test(priority = 3, description = "(FC-301) Verify secondary POC tab", enabled = true)
	public void validateSecondaryPOCTab() throws Exception {
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
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "Yes", "United States of America (+1)");
		additionalUsers.verifyAUTab();

	}


	@Test(priority = 4, description = "(FC-302) Verify 'Work phone country' in secondary POC tab", enabled = true)

	public void validateWorkPhoneSecondaryPOCTab() throws Exception {

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
		secPoc.enterInvalidWorkPhoneNumber(dataList, prefix, suffix, "Yes", "United States of America (+1)");

	}



	@Test(priority = 5, description = "(FC-303) Verify 'Work phone country' in secondary POC tab", enabled = true)
	public void validateWorkPhoneCountrySecondaryPOCTab() throws Exception {

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
		secPoc.enterInvalidWorkPhoneCountry(dataList, prefix, suffix, "Yes");
		secPoc.enterDetailsWithoutWorkCountry(dataList);
	}


	@Test(priority = 6, description = "(FC-304)Validating the 'Work phone country'without US or Canada", enabled = true)

	public void validateOtherWorkPhoneCountry() throws Exception {

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
		secPoc.enterInvalidWorkPhoneCountry(dataList, prefix, suffix, "Yes");
		secPoc.enterOtherWorkCountry(dataList, "Algeria (+213)");
		additionalUsers.verifyAUTab();

	}


	@Test(priority = 7, description = "(FC-336)Verify the 'Mobile phone country' in secondary POC tab ", enabled = true)
	public void validateOtherMobilePhoneCountry() throws Exception {


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
		secPoc.enterDetailswithMobileCountry(dataList, prefix, suffix, "Yes", "United States of America (+1)");

	}


	@Test(priority = 8, description = "(FC-337)Verify Validating the 'Mobile phone'secondary POCTab", enabled = true)

	public void validateMobileCountryError() throws Exception {

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
		secPoc.enterDetailswithMobilePhone(dataList, prefix, suffix, "Yes", "United States of America (+1)");

	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}

}
