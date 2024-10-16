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
import org.aia.pages.fonteva.membership.ContactCreateUser;
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
import org.aia.utility.VideoRecorder;
import org.apache.log4j.Logger;
import org.apache.poi.util.PackageHelper;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestAccountAssociated_CES extends BaseClass {
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

	@Test(priority = 1, description = "(FC-370) Verify adding Membership through ROE", enabled = true)
	public void validateMembershipthroughROE() throws Throwable {
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
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesMembershipType2"),
				testData.testDataProvider().getProperty("quickElement2"));
		rapidOrderEntery.verifyRecieptAfterROE();

	}

	@Test(priority = 2, description = "(FC-371) Verify account associated to POC after adding CES Membership through ROE", enabled = true)
	public void validateaccountAssociatedROE() throws Throwable {
//Here we create the user
		System.out.println("offline check payement Bug id FC-532");
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
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
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
//		ces_ContactPage.globalSearch(dataList.get(5));
//		ces_ContactPage.clickContactsInGlobalSearch(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.navigateRapidOrderEntry();
		rapidOrderEntery.cesRapidOrderEntry(dataList.get(0) + " " + dataList.get(1),
				testData.testDataProvider().getProperty("cesMembershipType2"),
				testData.testDataProvider().getProperty("quickElement2"));
		rapidOrderEntery.verifyRecieptAfterROE();
		rapidOrderEntery.selectAccount();
		ces_ContactPage.verifyAccountAssociatedtoPrimaryPOC();

	}

	@Test(priority = 3, description = "(FC-372) Verify account associated to POC after Renew process", enabled = true)
	public void validateaccountAssociatedafterRenew() throws Throwable {
//Here we create the user
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		ArrayList<String> userAccount = dataList;
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
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
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP();
		final String sID = sessionID.getSessionID();
//		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
//		// driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
//		fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		cesTermDateChangeAPI.changeTermDateAPI(dataList.get(3), testData.testDataProvider().getProperty("termEndDate"),
				testData.testDataProvider().getProperty("cesMembershipType2"));
		// Navigate back to renew CES portal
		driver.get(
				"https://account-dev.aia.org/signin?redirectUrl=https%3A%2F%2Faia--upgrade.sandbox.my.site.com%2FProviders%2Fs%2Frenew");
		driver.switchTo().alert().accept();
		// Renew user
		renew.renewMembership(dataList.get(5));
		checkOutPageCes.enterECheckDetailsCes(userAccount.get(1), testData.testDataProvider().getProperty("bankName"),
				testData.testDataProvider().getProperty("bankRoutingNo"),
				testData.testDataProvider().getProperty("bankAccountNo"));
		Object echeckamount = paymntSuccesFullPageCes.amountPaid();
		Logging.logger.info("Total Amount is : " + echeckamount);
		String renewreciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
		// Get Receipt number
		String renewreciptNumber = util.getSubString(renewreciptData, "");
		Reporter.log("LOG : INFO -Receipt Number is" + renewreciptNumber);
		Reporter.log("LOG : INFO -Customer AIA Number is : " + userAccount.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		// fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		ces_ContactPage.selectCreatedContact(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.selectAccountName();
		ces_ContactPage.verifyAccountAssociatedtoPrimaryPOC();
	}

	@Test(priority = 4, description = "(FC-373) Verify account associated to Primary POC on contact page after JOIN Process", enabled = true)
	public void validateaccountAssociatedtoPrimaryPOC() throws Throwable {
//Here we create the user
		String prefix = "Dr.";
		String suffix = "Sr.";
		signUpPage.clickSignUplink();
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
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
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
//Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaPage.checkUserInProviderApplication(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.selectAccount();
		apiValidation.verifyProviderApplicationAccountDetails(
				testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType0"),
				testData.testDataProvider().getProperty("termEndDate"), false);
		apiValidation.verifySalesOrder(testData.testDataProvider().getProperty("orderPaidStatus"),
				testData.testDataProvider().getProperty("closedStatus"), amount,
				testData.testDataProvider().getProperty("postingStatus"));
		apiValidation.verifyReciptDetails(null, amount, testData.testDataProvider().getProperty("cesmembershipType0"));
		apiValidation.verifyPointOfContact(testData.testDataProvider().getProperty("pocRole"), dataList.get(5),
				dataList.get(0) + " " + dataList.get(1));
		ces_ContactPage.verifyAccountAssociatedtoPrimaryPOC();

	}

	@Test(priority = 5, description = "(FC-374) Verify account associated to Secondary POC on contact page after JOIN Process", enabled = true)
	public void validateaccountAssociatedtoSecondaryPOC() throws Throwable {
//Here we create the user
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		loginPageCes.loginToCes(dataList.get(5), dataList.get(6));
		loginPageCes.checkLoginSuccess();
		primarypocPage.enterPrimaryPocDetails(prefix, suffix, dataList.get(2));
		String text = organizationPage.enterOrganizationDetails(dataList, "Other", "No",
				"United States of America (+1)");
		subscribePage.SubscriptionType(text, "Yes", null, "Non-profit");
		secPoc.enterSecondaryPocDetails(dataList, prefix, suffix, "Yes", "United States of America (+1)");
		additionalUsers.doneWithCreatingUsers();
		providerStatement.providerStatementEnterNameDate2("FNProviderStatement");
		checkOutPageCes.SubscriptionType(text);
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());
		Object amount = paymntSuccesFullPageCes.amountPaid();
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
//Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaPage.checkUserInProviderApplication(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.selectAccount();
		apiValidation.verifyProviderApplicationAccountDetails(
				testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType0"),
				testData.testDataProvider().getProperty("termEndDate"), false);
		apiValidation.verifySalesOrder(testData.testDataProvider().getProperty("orderPaidStatus"),
				testData.testDataProvider().getProperty("closedStatus"), amount,
				testData.testDataProvider().getProperty("postingStatus"));
		apiValidation.verifyReciptDetails(null, amount, testData.testDataProvider().getProperty("cesmembershipType0"));
		apiValidation.verifyPointOfContact(testData.testDataProvider().getProperty("pocRole"), dataList.get(5),
				dataList.get(0) + " " + dataList.get(1));
		ces_ContactPage.verifyAccountAssociatedtoSecondaryPOC();
	}

	@Test(priority = 6, description = "(FC-375) Verify account associated to User on contact page after JOIN Process", enabled = true)
	public void validateaccountAssociatedtoUser() throws Throwable {
//Here we create the user
		String prefix = "Dr.";
		String suffix = "Sr.";
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
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
		Logging.logger.info("Total Amount is : " + paymntSuccesFullPageCes.amountPaid());//*[contains(@class,'windowViewMode-normal')]//a[contains(normalize-space(),'Show All')]
		Object amount = paymntSuccesFullPageCes.amountPaid();
		String reciptData = paymntSuccesFullPageCes.ClickonViewReceipt();
//Navigate to Fonteva side
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
//fontevaPage.changeTermDates(dataList.get(0) + " " + dataList.get(1));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaPage.checkUserInProviderApplication(dataList.get(0) + " " + dataList.get(1));
		rapidOrderEntery.selectAccount();
		apiValidation.verifyProviderApplicationAccountDetails(
				testData.testDataProvider().getProperty("membershipStatus"),
				testData.testDataProvider().getProperty("cesmembershipType0"),
				testData.testDataProvider().getProperty("termEndDate"), false);
		apiValidation.verifySalesOrder(testData.testDataProvider().getProperty("orderPaidStatus"),
				testData.testDataProvider().getProperty("closedStatus"), amount,
				testData.testDataProvider().getProperty("postingStatus"));
		apiValidation.verifyReciptDetails(null, amount, testData.testDataProvider().getProperty("cesmembershipType0"));
		apiValidation.verifyPointOfContact(testData.testDataProvider().getProperty("pocRole"), dataList.get(5),
				dataList.get(0) + " " + dataList.get(1));
		ces_ContactPage.verifyAccountAssociatedtoPrimaryPOC();
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown() {
		BrowserSetup.closeBrowser(driver);
	}
}
