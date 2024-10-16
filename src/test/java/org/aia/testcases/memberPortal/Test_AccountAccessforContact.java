package org.aia.testcases.memberPortal;

import java.io.IOException;
import java.util.ArrayList;

import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;
import org.aia.pages.fonteva.memberPortal.AccountAcessForContact;
import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.memberPortal.MyInformation;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.aia.utility.VideoRecorder;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class Test_AccountAccessforContact extends BaseClass {
	boolean recording;
	ContactCreateUser fontevaJoin;
	MailinatorAPI mailinator;
	JoinAPIValidation offlinApiValidation;
	public ExtentReports extent;
	public ExtentTest extentTest;
	MyInformation myInformation;
	CommonMethodsForMP comMethodsForMP;

	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				DataProviderFactory.getConfig().getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		util = new Utility(driver, 30);
		testData = new ConfigDataProvider();
		fontevaJoin = PageFactory.initElements(driver, ContactCreateUser.class);
		mailinator = new MailinatorAPI(driver);
		offlinApiValidation = PageFactory.initElements(driver, JoinAPIValidation.class);
		comMethodsForMP = new CommonMethodsForMP(driver);
		myInformation = PageFactory.initElements(driver, MyInformation.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	@Test(description = "FM-318: My Account access for a contact", enabled = true, priority = 1)
	public void myAccountAccessForContact() throws Throwable {
		comMethodsForMP.navigateToMyAccount(mailinator);
		myInformation.verifyMyinformationTabSections();
	}

	@AfterMethod(alwaysRun = true)
	public void teardown(ITestResult result) throws IOException {
		if (recording) {
			VideoRecorder.stopRecording();
		}
		if (result.getStatus() == ITestResult.FAILURE) {
			System.out.println("LOG : FAIL Test failed to executed");
			Utility.takeScreenShotAfterFail(driver, result);
		}
		BrowserSetup.closeBrowser(driver);
	}

}
