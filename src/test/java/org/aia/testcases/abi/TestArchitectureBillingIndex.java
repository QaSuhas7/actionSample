package org.aia.testcases.abi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.aia.pages.BaseClass;
import org.aia.pages.abi.ABISignUpPage;
import org.aia.pages.abi.PaymentSuccessFullPageABI;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.abi.ABIAPIValidation;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.abi.FontevaLoginPage;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.CheckYourEmailPage;
import org.aia.pages.membership.FinalPageThankYou;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpPage;
//import org.aia.pages.membership.SignUpPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.pages.membership.TellusAboutYourselfPage;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class TestArchitectureBillingIndex extends BaseClass {

	SignUpPage signUpPage;
	ABISignUpPage abisignUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	FinalPageThankYou finalPage;
	// JoinAPIValidation apiValidation;
	TellusAboutYourselfPage tellAbtPage;
	ContactCreateUser fontevaJoin;
	SalesOrder salesOrder;
	PaymentSuccessFullPageABI paymentSuccessfullPage;
	ABIAPIValidation apiValidation;
	FontevaLoginPage fontevaLoginPage;

	public ExtentReports extent;
	public ExtentTest extentTest;
	public String inbox;
	public static HashMap<String, String> users = new HashMap<String, String>();
	public static ArrayList<String> pdfDetails = new ArrayList<String>();
	public String[] addresses = { "1735 york avenue, New york" ,
			"115 E 3rd Ave, Anchorage, AK 99501, United States" ,
			"9, Netkallappa Circle, Basavanagudi, Bengaluru, Karnataka, India" ,
			"WAVEROCK SEZ, Road Number 2, Financial District" };
			;
	protected FontevaConnectionSOAP sessionID;
	//public VideoRecorder recorder;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		System.out.println("@BeforeMethod");
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("stagingurl_abi"));
		inbox = DataProviderFactory.getConfig().getValue("inbox");
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		abisignUpPage = PageFactory.initElements(driver, ABISignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		paymentSuccessfullPage = PageFactory.initElements(driver, PaymentSuccessFullPageABI.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		fontevaLoginPage = PageFactory.initElements(driver, FontevaLoginPage.class);
		// apiValidation = PageFactory.initElements(driver,
		// ArchitectureBillingIndexAPIValidation.class);
		Logging.configure();
	}

	@Test(groups = { "Smoke" }, description = " To subscribe for ABI", enabled = true, priority=0)
	public void SubscribeToABI_US_Taxable_Address(ITestContext context) throws Exception {
			System.out.println("Subscription");

		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage.goToSignUpLink();
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		abisignUpPage.continueToCheckOut();
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "1735 York Avenue, New York, NY, USA");
		// report.logTestInfo((String)context.getAttribute("contactName"));
		// report.logTestInfo((String)addressType+"-"+(String)address);
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);

		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());
	}
	
	@Test(groups = { "Smoke" }, description = " To subscribe for ABI", enabled = true, priority=1)
	public void SubscribeToABI_US_Non_Taxable_Address(ITestContext context) throws Exception {
			System.out.println("Subscription");

		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage.goToSignUpLink();
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		abisignUpPage.continueToCheckOut();
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "115 E 3rd Ave, Anchorage, AK 99501, United States");
		// report.logTestInfo((String)context.getAttribute("contactName"));
		// report.logTestInfo((String)addressType+"-"+(String)address);
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);

		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());
	}
	
	@Test(groups = { "Smoke" }, description = " To subscribe for ABI", enabled = true, priority=2)
	public void SubscribeToABI_International_Address(ITestContext context) throws Exception {
			System.out.println("Subscription");

		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage.goToSignUpLink();
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		abisignUpPage.continueToCheckOut();
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "9, Netkallappa Circle, Basavanagudi, Bengaluru, Karnataka, India");
		// report.logTestInfo((String)context.getAttribute("contactName"));
		// report.logTestInfo((String)addressType+"-"+(String)address);
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);

		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());
	}
	
	@Test(groups = { "Smoke" }, description = " To subscribe for ABI", enabled = true, priority=3)
	public void SubscribeToABI_Non_US_Address(ITestContext context) throws Exception {
			System.out.println("Subscription");

		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage.goToSignUpLink();
		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		closeButtnPage.clickCloseAfterVerification();
		signInpage.login(dataList.get(5), dataList.get(6));
		abisignUpPage.continueToCheckOut();
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "WAVEROCK SEZ, Road Number 2, Financial District");
		// report.logTestInfo((String)context.getAttribute("contactName"));
		// report.logTestInfo((String)addressType+"-"+(String)address);
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		driver.get(DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);

		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());
	}



	@Test(groups = {"Smoke" }, description = " To renew ABI subscription as Guest", enabled = true, priority=4)
	public void SubscribeToABIAsGuest_US_Taxable_Address(ITestContext context) throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage = PageFactory.initElements(driver, ABISignUpPage.class);
		abisignUpPage.continueToCheckOutAsGuest();
		abisignUpPage.continueAsGuest(dataList.get(0), dataList.get(1), dataList.get(5));
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "1735 york avenue, New york");
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());
	}

	@Test(groups = {"Smoke" }, description = " To renew ABI subscription as Guest", enabled = true, priority=5)
	public void SubscribeToABIAsGuest_US_Non_Taxable_Address(ITestContext context) throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage = PageFactory.initElements(driver, ABISignUpPage.class);
		abisignUpPage.continueToCheckOutAsGuest();
		abisignUpPage.continueAsGuest(dataList.get(0), dataList.get(1), dataList.get(5));
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "115 E 3rd Ave, Anchorage, AK 99501, United States");
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());

	}
	
	@Test(groups = {"Smoke" }, description = " To renew ABI subscription as Guest", enabled = true, priority=6)
	public void SubscribeToABIAsGuest_International_Address(ITestContext context) throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage = PageFactory.initElements(driver, ABISignUpPage.class);
		abisignUpPage.continueToCheckOutAsGuest();
		abisignUpPage.continueAsGuest(dataList.get(0), dataList.get(1), dataList.get(5));
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "9, Netkallappa Circle, Basavanagudi, Bengaluru, Karnataka, India");
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());

	}
	
	@Test(groups = {"Smoke" }, description = " To renew ABI subscription as Guest", enabled = true, priority=7)
	public void SubscribeToABIAsGuest_Non_US_Address(ITestContext context) throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage = PageFactory.initElements(driver, ABISignUpPage.class);
		abisignUpPage.continueToCheckOutAsGuest();
		abisignUpPage.continueAsGuest(dataList.get(0), dataList.get(1), dataList.get(5));
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), "WAVEROCK SEZ, Road Number 2, Financial District");
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		users.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
		System.out.println("UserCount: "+users.keySet().size());

	}
	
	@Test(groups = { "Smoke" }, description = " To renew ABI subscription", enabled = true, priority=8)
	public void RenewABISubscription_US_Taxable_Address(ITestContext context) throws Exception {
		for (Map.Entry<String, String> e : users.entrySet()) {
			util.navigateToURl(driver,
					DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
			fontevaLoginPage.viewSubscriptionOrder(e.getKey());
			fontevaLoginPage.renewABI(e.getKey());
			abisignUpPage.processPayment(e.getValue());
			pdfDetails = abisignUpPage.proceedToCheckOut();
		}
	}

	@Test(groups = {
			"Smoke" }, description = " To renew ABI subscription as Guest", enabled = true, dataProvider = "Address")
	public void SubscribeToABIAsGuest(ITestContext context, String addressType, String address) throws Exception {
		ArrayList<String> dataList = signUpPage.signUpData();
		abisignUpPage = PageFactory.initElements(driver, ABISignUpPage.class);
		abisignUpPage.continueToCheckOutAsGuest();
		abisignUpPage.continueAsGuest(dataList.get(0), dataList.get(1), dataList.get(5));
		pdfDetails = abisignUpPage.subscribeToABI(dataList.get(0) + " " + dataList.get(1), String.valueOf(address));
		context.setAttribute("salesOrderId", pdfDetails.get(0).substring(13));
		context.setAttribute("contactName", dataList.get(0) + " " + dataList.get(1));
		HashMap<String, String> registeredUsers = null;
		registeredUsers.put(dataList.get(0) + " " + dataList.get(1), dataList.get(5));
		util.navigateToURl(driver,
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		fontevaLoginPage.viewSalesOrder((String) context.getAttribute("contactName"),
				(String) context.getAttribute("salesOrderId"));
		abisignUpPage.validateSubscriptionDetails(pdfDetails);
	}

	@DataProvider(name = "Address")
	public Object[][] getAddress(ITestContext context) {

		return new Object[][] { 
		{ "US Taxable Address", "1735 york avenue" }
			, {"US Non- Taxable Address", "115 E 3rd Ave, Anchorage, AK 99501, United States"}
			, {"Non US Address", "Waverock"}
			, {"International Address", "9, Netkallappa Circle, Basavanagudi"}

		};
	}
	
 

	@AfterMethod(alwaysRun = true)
	public void teardown() throws IOException {
		BrowserSetup.closeBrowser(driver);
	}

}
