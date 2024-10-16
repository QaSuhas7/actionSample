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
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestArchitectureFirmWithoutSPOC_CSE extends BaseClass {
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
	SignUpAPI signUpAPI;

	public ExtentReports extent;
	public ExtentTest extentTest;
	final static Logger logger = Logger.getLogger(TestArchitectureFirmWithoutSPOC_CSE.class);

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
		// fontevaPage = PageFactory.initElements(driver, FontevaCES.class);
		cesTermDateChangeAPI = PageFactory.initElements(driver, FontevaCESTermDateChangeAPI.class);
		ces_ContactPage = PageFactory.initElements(driver, CES_ContactPage.class);
		salesorder = PageFactory.initElements(driver, CES_SalesOrder.class);
		signUpAPI = PageFactory.initElements(driver, SignUpAPI.class);

	}

	@Test(priority = 1, description = "(FC-120) Verify CES Architecture Firm Membership without Secondary POC and user on fonteva", enabled = true, groups = {
			"Smoke" })
	public void validateCESArchitectureFirm() throws Throwable {
//Here we create the user
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Design & Construction Services", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "No", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		String providerappID = apiValidation.getProviderApplicationID(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaPage.changeProviderApplicationStatus(dataList.get(0) + " " + dataList.get(1), providerappID,
				testData.testDataProvider().getProperty("applicationStatus"));
		util.createNewWindow(driver, mailinator.cesProviderApprovedEmailLink(dataList));
		checkOutPageCes.enterCardDetailsCes();
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid();
		apiValidation.verifyProviderApplicationDetails(testData.testDataProvider().getProperty("applicationStatus"),
				dataList, testData.testDataProvider().getProperty("providerApplicationType"),
				dataList.get(0) + " " + dataList.get(1), true, util.todaysDate().toString(),
				testData.testDataProvider().getProperty("orgName"), "Other",
				testData.testDataProvider().getProperty("priorProvider"));
		apiValidation.verifyProviderApplicationAccountDetails(
				testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesMembershipType3"),
				testData.testDataProvider().getProperty("termEndDate"), false);
		apiValidation.verifySalesOrder(testData.testDataProvider().getProperty("orderPaidStatus"),
				testData.testDataProvider().getProperty("closedStatus"), amount,
				testData.testDataProvider().getProperty("postingStatus"));
		apiValidation.verifyReciptDetails(null, amount, testData.testDataProvider().getProperty("cesMembershipType3"));
		apiValidation.verifyPointOfContact(testData.testDataProvider().getProperty("pocRole"), dataList.get(5),
				dataList.get(0) + " " + dataList.get(1));

	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
