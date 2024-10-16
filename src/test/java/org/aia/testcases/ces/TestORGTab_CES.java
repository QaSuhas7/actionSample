package org.aia.testcases.ces;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorCESAPI;
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

public class TestORGTab_CES extends BaseClass {
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
	
	@Test(priority = 1, description = "Validate Primary point of contact tab", enabled = true)
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
	
	@Test(priority = 2, description = "Validate Error Message without entering values on Organizataion Tab", enabled = true)
	public void validateErrorMsgAllDetailsOrgTab() throws Exception {
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
		organizationPage.verifyOrganizationTab();
		organizationPage.validateAllErrorsOnORGTab();
	}
	 
	/**
	 * @throws Exception
	 */
    
	@Test(priority = 3, description = "Validate Subscription Tab", enabled = true)
	public void validateSubscriptionTab() throws Exception {
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
		organizationPage.verifyOrganizationTab();
		organizationPage.enterOrganizationDetails(dataList, "Other", "No", "United States of America (+1)");
		subscribePage.verifySubscriptionTab();
	}
	
	/**
	 * @throws Exception
	 */
	
	@Test(priority = 4, description = "Validate error for all the details in Organization tab except Organization Name", enabled = true)
	public void validateErrorORGNameInOrg() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		System.out.println("datalist value is : " + dataList);
		System.out.println(dataList.get(3));
		System.out.println(dataList.get(2));
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.verifyOrganizationTab();
		// Provide details in other fields except for 'Organization Name'
		organizationPage.validateErrorForOrgName(dataList, "Other", "No", "United States of America (+1)");

	}
	
	/**
	 * @throws Exception
	 */

	@Test(priority = 5, description = "Validate error for all the details in Organization tab except Organization Type", enabled = true)
	public void validateErrorORGTypeInOrg() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		System.out.println("datalist value is : " + dataList);
		System.out.println(dataList.get(3));
		System.out.println(dataList.get(2));
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.verifyOrganizationTab();
		organizationPage.enterOrganizationDetailsWithoutNext(dataList, "Other", "No", "United States of America (+1)");
		// Provide details in other fields except for 'Organization Type'
		organizationPage.validateORGTypeError();

	}
	
	/**
	 * @throws Exception
	 */
	
	@Test(priority = 6, description = "Validate error for all the details in Organization tab except Prior Provider", enabled = true)
	public void validatePriorProviderErrorOrg() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		System.out.println("datalist value is : " + dataList);
		System.out.println(dataList.get(3));
		System.out.println(dataList.get(2));
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.verifyOrganizationTab();
		// Provide details in other fields except for 'Prior Provider
		organizationPage.validatePriorProviderError(dataList, "Other", "United States",
				"United States of America (+1)");

	}
	
	
	@Test(priority = 7, description = "Enter all the details in Organization tab and Select 'Work phone country'other than  'United States of America' or 'Canada", enabled = true)
	public void validateWorkPhoneCountryInOrg() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		System.out.println("datalist value is : "+dataList);
		System.out.println(dataList.get(3));
		System.out.println(dataList.get(2));
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.verifyOrganizationTab();
		//Select 'Work phone country' as any country other than  'United States of America' or 'Canada'
        organizationPage.enterOrganizationDetails(dataList, "Other", "No", "Albania (+355)");
        subscribePage.verifySubscriptionTab();
       
}
	
	@Test(priority = 8, description = "Validate 'Next' without providing any details for 'Work phone country' and 'Work phone'", enabled = true)
	public void validateWorkPhoenCountryErrorInOrgTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		System.out.println("datalist value is : "+dataList);
		System.out.println(dataList.get(3));
		System.out.println(dataList.get(2));
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.verifyOrganizationTab();
		organizationPage.validateWorkPhoneCountyError(dataList, "Other", "No");
	
	}
	
	@Test(priority = 9, description = "Validate 'Next' without providing any details for 'Work phone country' and 'Work phone'", enabled = true)
	public void validateWorkPhoneErrorORGTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		System.out.println("datalist value is : "+dataList);
		System.out.println(dataList.get(3));
		System.out.println(dataList.get(2));
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.verifyOrganizationTab();
		//organizationPage.validateWorkPhoneCountyError(dataList, "Other", "No");
		organizationPage.validateWorkPhoneError(dataList, "Other", "No", "United States of America (+1)");
	
	}
	
	@Test(priority = 10, description = "FC-275 Validating the 'Work phone' in Organization tab", enabled = true)
	public void validateWorkPhoneORGTab() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		System.out.println("datalist value is : "+dataList);
		System.out.println(dataList.get(3));
		System.out.println(dataList.get(2));
		String mobileCountry = signUpPage.signUpUserDetail();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.verifyPOCTab();
		primarypocPage.validateErrorOnPOCTab();
		primarypocPage.enterPOCdetail(prefix, suffix, dataList.get(2), dataList, mobileCountry);
		organizationPage.enterInvalidWorkPhoneCountry(dataList, "Other", "No");
		organizationPage.enterDetailsWithoutWorkCountry(dataList);
	
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}

}
