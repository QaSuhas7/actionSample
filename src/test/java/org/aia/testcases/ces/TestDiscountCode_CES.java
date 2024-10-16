package org.aia.testcases.ces;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorCESAPI;
import org.aia.pages.api.ces.JoinCESAPIValidation;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.ces.AdditionalProviderUser;
import org.aia.pages.ces.AdditionalUsers;
import org.aia.pages.ces.CheckOutPageCes;
import org.aia.pages.ces.CloseBtnPageCes;
import org.aia.pages.ces.LoginPageCes;
import org.aia.pages.ces.OrderSummaryCes;
import org.aia.pages.ces.Organization;
import org.aia.pages.ces.PaymentSuccessFullPageCes;
import org.aia.pages.ces.PrimaryPointOfContact;
import org.aia.pages.ces.ProviderStatement;
import org.aia.pages.ces.SecondaryPointOfContact;
import org.aia.pages.ces.SignUpPageCes;
import org.aia.pages.ces.Subscription;
import org.aia.pages.fonteva.ces.CES_ContactPage;
import org.aia.pages.fonteva.ces.CES_RapidOrderEntry;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.GenerateReportsListener;
import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestDiscountCode_CES extends BaseClass {
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
	OrderSummaryCes orderSummary;
	CES_RapidOrderEntry rapidOrderEntery;
	CES_ContactPage cesContactPage;

	public ExtentReports extent;
	public ExtentTest extentTest;
	final static Logger logger = Logger.getLogger(TestJoinPassport_CES.class);

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
		apiValidation = PageFactory.initElements(driver, JoinCESAPIValidation.class);
		orderSummary = PageFactory.initElements(driver, OrderSummaryCes.class);
		cesContactPage = PageFactory.initElements(driver, CES_ContactPage.class);
		rapidOrderEntery = PageFactory.initElements(driver, CES_RapidOrderEntry.class);
	}

	@Test(priority = 1, description = "[FC-270]Verify CES Basic Discount on Checkout tab for CES passport JOIN.",enabled = true)
	public void validateDiscountCodeCESBasic() throws Throwable {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.signUpUser();
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
		orderSummary.applyDiscountCode(testData.testDataProvider().getProperty("cesBasicDiscountCode"));
		orderSummary.validateAmountAfterApplyDiscountCode();
		checkOutPageCes.SubscriptionType(text);
		Object amount = paymntSuccesFullPageCes.amountPaid();
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		apiValidation.getAccountId(dataList.get(3));
		// Here we validate receipt
		apiValidation.verifyReciptDetails(reciptData, amount,
				testData.testDataProvider().getProperty("availableMemType"));
	}

	@Test(priority = 2, description = "[FC-271]Verify CES Basic Discount code for other (non ces) Membership types",enabled = true)
	public void validateDiscountCodeDiffMembership() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.signUpUser();
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
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		cesContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.selectDiscountCode(testData.testDataProvider().getProperty("cesBasicDiscountCode"),
				testData.testDataProvider().getProperty("quickElement1"),
				testData.testDataProvider().getProperty("cesBasicPriceId"),dataList.get(0) + " " + dataList.get(1));

	}

	@Test(priority = 3, description = "[FC-273]Verify discount code for CES Basic membership",enabled = true)
	public void validateDiscountCodeForCESMembership() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.signUpUser();
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
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		cesContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.selectDiscountCode(testData.testDataProvider().getProperty("cesBasicDiscountCode"),
				testData.testDataProvider().getProperty("quickElement2"),
				testData.testDataProvider().getProperty("cesBasicPriceId"),dataList.get(0) + " " + dataList.get(1));

	}

	@Test(priority = 4, description = "[FC-273]Verify discount code for Other CES membership", enabled = true)
	public void validateDiscountCodeForOtherCESMem() throws Exception {
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.signUpUser();
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
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		cesContactPage.selectContactNewLogic(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.selectAccountName();
		rapidOrderEntery.selectDiscountCode(testData.testDataProvider().getProperty("cesBasicDiscountCode"),
				testData.testDataProvider().getProperty("quickElement0"),
				testData.testDataProvider().getProperty("cesPassportPriceId"),dataList.get(0) + " " + dataList.get(1));

	}

	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}

}
