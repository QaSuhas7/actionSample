package org.aia.testcases.chapterPortal;

import java.io.IOException;

import org.aia.pages.BaseClass;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.chapterPortal.CallForDues;
import org.aia.pages.fonteva.chapterPortal.GlobalSearch;
import org.aia.pages.fonteva.chapterPortal.MemberShipInChapterPortal;
import org.aia.pages.fonteva.chapterPortal.NavigateToChapterPortal;
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

public class GlobalSearchInCP extends BaseClass {

	boolean recording;
	NavigateToChapterPortal naToChapterPortal;
	MemberShipInChapterPortal memChapterPortal;
	CommonMehodsInCP commonMehodsInCP;
	GlobalSearch globalSearch;
	
@BeforeMethod(alwaysRun = true)
public void setUp() throws Exception {
	testData = new ConfigDataProvider();
	sessionID = new FontevaConnectionSOAP();
	driver = BrowserSetup.startApplication(driver, DataProviderFactory.getConfig().getValue("browser"),
			testData.getValue("fontevaUpgradeStgSessionIdUrl") + sessionID.getSessionID());
	recording = Boolean.parseBoolean(testData.testDataProvider().getProperty("videoRecording"));
	naToChapterPortal = PageFactory.initElements(driver, NavigateToChapterPortal.class);
	memChapterPortal= PageFactory.initElements(driver, MemberShipInChapterPortal.class);
	globalSearch = PageFactory.initElements(driver, GlobalSearch.class);
	commonMehodsInCP = new CommonMehodsInCP(driver);
	Logging.configure();
}

@Test(description = "FM-492: Global Search functionality on Chapter Portal", enabled = true, priority = 1)
public void test_VerificationGlobalSearch(ITestContext context) throws InterruptedException, Throwable {
	if (recording) {
		VideoRecorder.startRecording("test_VerificationGlobalSearchs");
	}
	commonMehodsInCP.navigationChapterPortal("Mardriss Nelson");
	naToChapterPortal.getComunityGroup(7);
	globalSearch.globalSearch("Mardriss Nelson");
	globalSearch.getContactListInGlobalSearch();
}

@Test(description = "FM-493 Global Search results refining", enabled = true, priority = 2)
public void test_VerificationGlobalSearchRefine(ITestContext context) throws InterruptedException, Throwable {
	if (recording) {
		VideoRecorder.startRecording("test_VerificationGlobalSearchRefine");
	}
	commonMehodsInCP.navigationChapterPortal("Mardriss Nelson");
	naToChapterPortal.getComunityGroup(7);
	globalSearch.globalSearch("Mardriss Nelson");
	globalSearch.validateSearchRefineItems();
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
