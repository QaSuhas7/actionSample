package org.aia.testcases.chapterPortal;

import java.io.IOException;

import org.aia.pages.BaseClass;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.chapterPortal.ChapterInfo;
import org.aia.pages.fonteva.chapterPortal.GlobalSearch;
import org.aia.pages.fonteva.chapterPortal.MemberShipInChapterPortal;
import org.aia.pages.fonteva.chapterPortal.NavigateToChapterPortal;
import org.aia.pages.fonteva.chapterPortal.Retention;
import org.aia.utility.BrowserSetup;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.aia.utility.VideoRecorder;
import org.openqa.selenium.support.PageFactory;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Test_Retention extends BaseClass {
	boolean recording;
	NavigateToChapterPortal naToChapterPortal;
	MemberShipInChapterPortal memChapterPortal;
	Retention retention;
	GlobalSearch globalSearch;
	CommonMehodsInCP commonMehodsInCP;
	
	
	
	@BeforeMethod(alwaysRun = true)
	public void setUp() throws Exception {
		testData = new ConfigDataProvider();
		sessionID = new FontevaConnectionSOAP();
		driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
				testData.getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
		recording = Boolean.parseBoolean(testData.testDataProvider().getProperty("videoRecording"));
		naToChapterPortal = PageFactory.initElements(driver, NavigateToChapterPortal.class);
		memChapterPortal= PageFactory.initElements(driver, MemberShipInChapterPortal.class);
		retention = PageFactory.initElements(driver, Retention.class);
		globalSearch = PageFactory.initElements(driver, GlobalSearch.class);
		commonMehodsInCP = new CommonMehodsInCP(driver);
		Logging.configure();
	}
	
	@Test(description = "FM-430: Retention Tab page verification", enabled = false, priority = 1)
	public void test_VerificationRetention(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_VerificationRetention");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.clickRetentionTab();
		retention.validateRetentionPieCharts();
		retention.getRenewedMembersRecordsData();
		//UI changed new table data is displayed instead of non renewed members table data
		retention.clickInvocingCycleLink();
		//After clicking on the Invoicing link it showing universal login.It needs credential for universal login.
		util.switchToTabs(driver, 1);
		Thread.sleep(5000);
		util.switchToTabs(driver, 0);
		retention.clickMembershipApplicationFormLink();
		//After clicking on the memberShipApllicatinForm link it showing universal login.It needs credential for universal login.
		util.switchToTabs(driver, 2);
		Thread.sleep(5000);
		util.switchToTabs(driver, 0);
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
