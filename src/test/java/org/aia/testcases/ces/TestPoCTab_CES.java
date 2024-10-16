package org.aia.testcases.ces;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.ces.JoinCESAPIValidation;
import org.aia.pages.api.ces.FontevaConnectionSOAP;
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
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestPoCTab_CES extends BaseClass {
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

	/**
	 * @throws Exception
	 */
	@Test(priority = 2, description = "Validate work fon number in Primary point of contact tab",enabled = true)
	public void validateWorkPhoneCountryInPoc() throws Exception {
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
		primarypocPage.enterInvalidWorkNumber();
	}

	@Test(priority = 3, description = "Validate refresh functionality Primary point of contact tab",enabled = true)
	public void validateRefreshFuntionInPoc() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.refreshFunction();
		// Get Provider application ID
		String paId = apiValidation.getProviderApplicationID(dataList.get(0) + " " + dataList.get(1));
		driver.get(
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + FontevaConnectionSOAP.getSessionID());
		fontevaPage.checkUserInProviderApplication(dataList.get(0) + " " + dataList.get(1));

	}

	@Test(priority = 4, description = "Validate country code without selecting us or canada Primary point of contact tab",enabled = true)
	public void validateWithoutUSCodeInPocTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.changeWorkPhoneCountryInPOC(testData.testDataProvider().getProperty("newCountry"));
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
	}

	@Test(priority = 5, description = "FC-333 Validate 'Mobile phone country' in Primary POC tab",enabled = true)
	public void validateMobilePhoneCountryInPocTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		System.out.println("datalist value:" + dataList.get(2));
		primarypocPage.enterPocDetailsWithOutMobilePone(prefix, suffix, "--None--", dataList.get(2));
		primarypocPage.validateWorkPhoneError(prefix, suffix, "United States of America (+1)");
	}

	@Test(priority = 6, description = "FC-334 Validate 'Mobile phone' in Primary POC tab",enabled = true)
	public void validateMobilePhoneInPocTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPocDetailsWithOutMobilePone(prefix, suffix, "--None--", dataList.get(2));
		primarypocPage.enterPrimaryPocAllDetails(prefix, suffix, "Algeria (+213)", dataList.get(2));
		organizationPage.verifyOrganizationTab();
	}

	@Test(priority = 6, description = "FC-335 Validate 'Mobile phone/Country' in Primary POC tab",enabled = true)
	public void validateMobileTabInPocTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocAllDetails(prefix, suffix, "United States of America (+1)", dataList.get(2));
		organizationPage.verifyOrganizationTab();
	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
