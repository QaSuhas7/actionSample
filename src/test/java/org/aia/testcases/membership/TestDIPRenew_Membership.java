package org.aia.testcases.membership;

import org.testng.annotations.BeforeMethod;

import org.testng.annotations.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.FontevaMemTermDateChangeAPI;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.api.membership.RenewAPIValidation;
import org.aia.pages.api.SignUpAPI;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.membership.*;
import org.aia.utility.BrowserSetup;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterMethod;

public class TestDIPRenew_Membership extends BaseClass {

	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	FinalPageThankYou finalPage;
	JoinAPIValidation apiValidation;
	RenewAPIValidation apiValidationRenew;
	TellusAboutYourselfPage tellAbtPage;
	DevSandBoxFonteva fontevaPage;
	RenewPage renew;
	SignUpAPI signUpAPI;
	ContactCreateUser fontevaJoin;

	FontevaMemTermDateChangeAPI termDateChangeAPI;
	public String inbox;

	@BeforeMethod(alwaysRun=true)
	public void setUp() throws Exception {
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
		inbox = DataProviderFactory.getConfig().getValue("inbox");
		util = new Utility(driver, 30);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		mailinator = PageFactory.initElements(driver, MailinatorAPI.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		apiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		apiValidationRenew = PageFactory.initElements(driver, RenewAPIValidation.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		fontevaPage = PageFactory.initElements(driver, DevSandBoxFonteva.class);
		renew = PageFactory.initElements(driver, RenewPage.class);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		signUpAPI =PageFactory.initElements(driver, SignUpAPI.class);
		termDateChangeAPI=PageFactory.initElements(driver, FontevaMemTermDateChangeAPI.class);

	}

	@Test(priority=1, description="Validate DIP Renew", enabled=true, groups= {"smoke"})
	public void ValidateDipRenew() throws Exception
	{
		LocalDate localDate = java.time.LocalDate.now();
		if (localDate.getMonthValue() >= 10 || localDate.getMonthValue() <= 04) {
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		/*tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");	
		finalPage.verifyThankYouMessage();
		ArrayList<Object> receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is "+receiptData.get(0));
		System.out.println("Customer AIA Number is "+receiptData.get(1));
		System.out.println("Total Amount is "+receiptData.get(2));
		System.out.println("AIA National is "+receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);*/
		
		// Navigate to Fonteva app and make record renew eligible.
		FontevaConnectionSOAP sessionID = new FontevaConnectionSOAP(); 
		final String sID = sessionID.getSessionID();
		driver.get("https://aia--upgradestg.sandbox.my.salesforce.com/secur/frontdoor.jsp?sid=" + sID);
		//driver.get(DataProviderFactory.getConfig().getValue("fonteva_endpoint"));
		fontevaJoin.selectContact(dataList.get(0) + " " + dataList.get(1));
		fontevaPage.changeTermDates(dataList.get(0)+" "+dataList.get(1));
		// Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
		renew.renewMembership(dataList.get(5));
		//signInpage.login(dataList.get(5), dataList.get(6));
		orderSummaryPage.confirmTerms("activeUSLicense");
		String totalMembership = orderSummaryPage.payInInstallmentsClick("activeUSLicense");
		paymentInfoPage.clickOnCreditCard();
		paymentInfoPage.paymentDetails("activeUSLicense");
		/*finalPage.verifyThankYouMessage();
		ArrayList<Object> data = finalPage.getFinalReceiptData();
		finalPage.ValidateTotalAmount(totalMembership);
		ArrayList<Object> receiptData1 = finalPage.getFinalReceiptData();
		//mailinator.thanksForRenewingEmailLink(dataList, receiptData);
		
		// Validate Membership renew - Fonteva API validations
		apiValidationRenew.verifyMemebershipRenewal(dataList.get(3), 
				  DataProviderFactory.getConfig().getValue("termEndDate"), 
				  data.get(2), 
				  DataProviderFactory.getConfig().getValue("type_aia_national"), 
				  "Architect", "None Selected"); 
		  // Validate sales order
		apiValidationRenew.verifySalesOrder(DataProviderFactory.getConfig().getValue("dip_salesOrderStatus"), 
				  DataProviderFactory.getConfig().getValue("orderStatus"),
				  receiptData1.get(2), 
				  DataProviderFactory.getConfig().getValue("postingStatus")); 
		  //Validate Receipt Details 
		apiValidationRenew.verifyReciptDetails(data.get(0), data.get(2));
		}
		else {
			System.out.println("We are not in DIP period");*/
		}
	}
	
	@Test(priority = 11, description = "Dues Adjustment for Sanfrancisco chapter renewal (Architect, Installment)", enabled = false)
	public void ValidateRenewWithDuesAdjustmentForSanfranciscoChapterInstallment() throws Throwable {
		ArrayList<Object> receiptData;
		ArrayList<String> dataList = signUpPage.signUpData();
		signUpAPI.stageUserCreationAPI(dataList);
//		signUpPage.signUpUser();
		mailinator.verifyEmailForAccountSetup(dataList.get(3));
		signInpage.login(dataList.get(5), dataList.get(6));
		primaryInfoPage.enterPrimaryInfo_pac("activeUSLicense", "None Selected");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
		String aiaNational = paymentInfoPage.paymentDetails("activeUSLicense");
		tellAbtPage.enterTellUsAboutYourSelfdetails("activeUSLicense", "None Selected");
		finalPage.verifyThankYouMessage();
		receiptData = finalPage.getFinalReceiptData();
		receiptData.add(3, aiaNational);
		System.out.println("Receipt Number is " + receiptData.get(0));
		System.out.println("Customer AIA Number is " + receiptData.get(1));
		System.out.println("Total Amount is " + receiptData.get(2));
		System.out.println("AIA National is " + receiptData.get(3));
		mailinator.welcomeAIAEmailLink(dataList, receiptData);
		termDateChangeAPI.changeTermDateAPI(dataList.get(3),"2024-12-31");
//Navigate back to membership portal
		driver.get(DataProviderFactory.getConfig().getValue("upgradeStagingurl_membership"));
//Renew user
		renew.renewMembership(dataList.get(5));
		orderSummaryPage.enterSupplementalDuesDetails("architecturalFirmOwner", "1", "1", "1");
		orderSummaryPage.confirmTerms("activeUSLicense");
		orderSummaryPage.clickonPayNow();
//		Map<String, Double> paymentBeforeEdit = orderSummaryPage.paymentInfo("six");
//		System.out.println(paymentBeforeEdit);
//		FontevaSetDiscountAPI.setDiscountAPI(dataList.get(
//				3), 30.00);
//		driver.navigate().refresh();
//		Map<String, Double>  paymentAfterEdit = orderSummaryPage.paymentInfo("six");
//		System.out.println(paymentAfterEdit);
//		Assert.assertNotEquals(paymentBeforeEdit.get("AIA National"), paymentAfterEdit.get("AIA National"));
//		Assert.assertNotEquals(paymentBeforeEdit.get("AIA California"), paymentAfterEdit.get("AIA California"));
//		Assert.assertNotEquals(paymentBeforeEdit.get("Supplemental Dues - San Francisco"), paymentAfterEdit.get("Supplemental Dues - San Francisco"));
//		Assert.assertNotEquals(paymentBeforeEdit.get("Special Assessment for the Center for Architecture + Design"), paymentAfterEdit.get("Special Assessment for the Center for Architecture + Design"));
//		Assert.assertNotEquals(paymentBeforeEdit.get("AIA San Francisco"), paymentAfterEdit.get("AIA San Francisco"));
//		Assert.assertNotEquals(paymentBeforeEdit.get("Installment plan administration fee"), paymentAfterEdit.get("Installment plan administration fee"));

	}
	
	@AfterMethod(alwaysRun=true)
	public void teardown() throws IOException {
		BrowserSetup.closeBrowser(driver);
	}
}