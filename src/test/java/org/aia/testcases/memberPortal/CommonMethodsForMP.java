package org.aia.testcases.memberPortal;

import java.util.ArrayList;

import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.fonteva.memberPortal.AccountAcessForContact;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.fonteva.membership.SalesOrder;
import org.aia.pages.membership.CheckYourEmailPage;
import org.aia.pages.membership.FinalPageThankYou;
import org.aia.pages.membership.OrderSummaryPage;
import org.aia.pages.membership.PaymentInformation;
import org.aia.pages.membership.PrimaryInformationPage;
import org.aia.pages.membership.SignInPage;
import org.aia.pages.membership.SignUpPage;
import org.aia.pages.membership.SignUpSuccess;
import org.aia.pages.membership.TellusAboutYourselfPage;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class CommonMethodsForMP {
	WebDriver driver;
	ConfigDataProvider testData;
	ContactCreateUser fontevaJoin;
	JoinAPIValidation offlinApiValidation;
	public ExtentReports extent;
	public ExtentTest extentTest;
	SignUpPage signUpPage;
	SignInPage signInpage;
	CheckYourEmailPage closeButtnPage;
	MailinatorAPI mailinator;
	SignUpSuccess successPage;
	PrimaryInformationPage primaryInfoPage;
	OrderSummaryPage orderSummaryPage;
	PaymentInformation paymentInfoPage;
	FinalPageThankYou finalPage;
	TellusAboutYourselfPage tellAbtPage;
	SalesOrder salesOrder;
	AccountAcessForContact accAcessForContact;
	
	public CommonMethodsForMP(WebDriver IDriver) {
		driver=IDriver;
		testData = new ConfigDataProvider();
		signUpPage = PageFactory.initElements(driver, SignUpPage.class);
		offlinApiValidation = new JoinAPIValidation(driver);
		signInpage = PageFactory.initElements(driver, SignInPage.class);
		closeButtnPage = PageFactory.initElements(driver, CheckYourEmailPage.class);
		successPage = PageFactory.initElements(driver, SignUpSuccess.class);
		primaryInfoPage = PageFactory.initElements(driver, PrimaryInformationPage.class);
		orderSummaryPage = PageFactory.initElements(driver, OrderSummaryPage.class);
		paymentInfoPage = PageFactory.initElements(driver, PaymentInformation.class);
		finalPage = PageFactory.initElements(driver, FinalPageThankYou.class);
		tellAbtPage = PageFactory.initElements(driver, TellusAboutYourselfPage.class);
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		salesOrder = PageFactory.initElements(driver, SalesOrder.class);
		accAcessForContact = PageFactory.initElements(driver, AccountAcessForContact.class);
	}
	
	
	public ArrayList<String> navigateToMyAccount(MailinatorAPI mailinator) throws Throwable {
		ArrayList<String> dataList = fontevaJoin.userData();
		//fontevaJoin.pointOffset();
		fontevaJoin.createUserInFonteva();
		fontevaJoin.joinCreatedUser(testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"));
		fontevaJoin.enterLicenseDetail();
		fontevaJoin.createSalesOrder(testData.testDataProvider().getProperty("paymentMethod"));
		fontevaJoin.applyPayment(dataList.get(5));
		ArrayList<Object> data =fontevaJoin.getPaymentReceiptData();
		//Validation of Thank you massage in email inbox after register.
		mailinator.thankYouEmailforOfflineJoin(dataList.get(2));
		//Validate Membership & Term is got created
		offlinApiValidation.verifyMemebershipCreation(dataList.get(2),
			DataProviderFactory.getConfig().getValue("termEndDate"),
			DataProviderFactory.getConfig().getValue("type_aia_national"), testData.testDataProvider().getProperty("membershipType"),
  		    testData.testDataProvider().getProperty("selection"));
		JoinAPIValidation.accountID = null;
		//Validate sales order is created or not
	//	offlinApiValidation.verifySalesOrderForPriceRule(testData.testDataProvider().getProperty("membershipType"));
		accAcessForContact.clickContactsModule();
		accAcessForContact.globalSearchInContact(dataList.get(5));
		accAcessForContact.clickContactsInGlobalSearch(dataList.get(5));
//		accAcessForContact.showAllInRealtedQuickLinks();
		accAcessForContact.clickDropDownInActionContainer();
		accAcessForContact.verifyFieldsMemberPortal("AIA Customer");
		accAcessForContact.clickDropDownInActionContainer();
		accAcessForContact.optionsInactionContainer();
		return dataList;
	}

}
