package org.aia.testcases.memberPortal;

import java.io.IOException;
import java.util.ArrayList;
import org.aia.pages.BaseClass;
import org.aia.pages.api.MailinatorAPI;

import org.aia.pages.api.memberPortal.MemberPortalAPI;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.api.membership.JoinAPIValidation;

import org.aia.pages.fonteva.membership.ContactCreateUser;
import org.aia.pages.memberPortal.MemberPortal;
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

public class TestMemberPortal extends BaseClass {
	boolean recording;
	ContactCreateUser fontevaJoin;
	MailinatorAPI mailinator;
	JoinAPIValidation offlinApiValidation;
	public ExtentReports extent;
	public ExtentTest extentTest;
	MyInformation myInformation;
	CommonMethodsForMP comMethodsForMP;
	MemberPortalAPI memberPortalAPI;
	MemberPortal memberPortal;

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
		memberPortalAPI = new MemberPortalAPI(driver);
		memberPortal = PageFactory.initElements(driver, MemberPortal.class);
		myInformation = PageFactory.initElements(driver, MyInformation.class);
		// Configure Log4j to perform error logging
		Logging.configure();
	}

	/**
	 * @throws Throwable
	 */

	@Test(priority = 1, description = " FM- 319 Contact Information page verification", enabled = true)
	public void contactpageVerification() throws Throwable {

		ArrayList<String> dataList = comMethodsForMP.navigateToMyAccount(mailinator);
		ArrayList<String> contactInfo = memberPortal.validateContactInformationpage();
		memberPortalAPI.verifyContactInformation(dataList.get(2),
				DataProviderFactory.getConfig().getValue("termEndDate"), dataList.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"), contactInfo);
	}

	@Test(priority = 2, description = " FM- 324 Membership page verification", enabled = true)
	public void verifyMembershipPage() throws Throwable {

		ArrayList<String> dataList = comMethodsForMP.navigateToMyAccount(mailinator);
		ArrayList<String> memberShipInfo = memberPortal.validateMembershipFields();
		memberPortalAPI.verifyMemberShipInformation(dataList.get(2),
				DataProviderFactory.getConfig().getValue("termEndDate"), dataList.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"), memberShipInfo);
	}

	@Test(priority = 3, description = " FM- 320 Communication Preferences page verification(My Account)", enabled = true)
	public void communicationPreferencesVerification() throws Throwable {
		comMethodsForMP.navigateToMyAccount(mailinator);
		memberPortal.verifycommunicationPreferences();
	}

	@Test(priority = 4, description = " FM- 325 Interest Areas page verification", enabled = true)
	public void interestAreaPageVerification() throws Throwable {
		comMethodsForMP.navigateToMyAccount(mailinator);
		memberPortal.validateInterestPageFields();
	}

	@Test(priority = 5, description = " FM- 326 Demographics page verification(My Account)", enabled = true)
	public void demographicsPageVerification() throws Throwable {
		ArrayList<String> dataList = comMethodsForMP.navigateToMyAccount(mailinator);
		ArrayList<String> demographicsData = memberPortal.verifyDemographicsFields();
		memberPortalAPI.verifyDemographicsInformation(dataList.get(2),
				DataProviderFactory.getConfig().getValue("termEndDate"), dataList.get(2),
				DataProviderFactory.getConfig().getValue("type_aia_national"),
				testData.testDataProvider().getProperty("membershipType"),
				testData.testDataProvider().getProperty("selection"), demographicsData);
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
