package org.aia.testcases.chapterPortal;

import java.io.IOException;

import org.aia.pages.BaseClass;
import org.aia.pages.api.membership.FontevaConnectionSOAP;
import org.aia.pages.fonteva.chapterPortal.ActiveMemberRoster;
import org.aia.pages.fonteva.chapterPortal.ArchitectsLicenced;
import org.aia.pages.fonteva.chapterPortal.AssociatePathToLicence;
import org.aia.pages.fonteva.chapterPortal.ChapterInfo;
import org.aia.pages.fonteva.chapterPortal.Deceased;
import org.aia.pages.fonteva.chapterPortal.EmailChangeLog;
import org.aia.pages.fonteva.chapterPortal.FirmDetails;
import org.aia.pages.fonteva.chapterPortal.FullMemberRoster;
import org.aia.pages.fonteva.chapterPortal.GlobalSearch;
import org.aia.pages.fonteva.chapterPortal.InterestAreas;
import org.aia.pages.fonteva.chapterPortal.JoinReJoin;
import org.aia.pages.fonteva.chapterPortal.LapsedMembers;
import org.aia.pages.fonteva.chapterPortal.MemberShipInChapterPortal;
import org.aia.pages.fonteva.chapterPortal.MembersInCESAudit;
import org.aia.pages.fonteva.chapterPortal.NavigateToChapterPortal;
import org.aia.pages.fonteva.chapterPortal.NewGrad;
import org.aia.pages.fonteva.chapterPortal.TerminatedMembers;
import org.aia.pages.fonteva.chapterPortal.TransfersIn;
import org.aia.pages.fonteva.chapterPortal.TransfersOut;
import org.aia.pages.fonteva.chapterPortal.UpgradeToArchitect;
import org.aia.pages.fonteva.chapterPortal.UpgradeToEmeritus;
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

public class Test_MemberShip extends BaseClass {
	boolean recording;
	NavigateToChapterPortal naToChapterPortal;
	MemberShipInChapterPortal memChapterPortal;
	FullMemberRoster fullMemberRoster;
	ActiveMemberRoster activeMemberRoster;
	LapsedMembers lapsedMembers;
	TerminatedMembers terminatedMembers;
	UpgradeToArchitect upgradeToArchitect;
	UpgradeToEmeritus upgradeToEmeritus; 
	ChapterInfo chapterInfo;
	AssociatePathToLicence associatePathToLicence;
	ArchitectsLicenced architectsLicenced;
	NewGrad newGrad;
	TransfersIn transfersIn;
	TransfersOut transfersOut;
	FirmDetails firmDetails;
	EmailChangeLog emailChangeLog;
	InterestAreas interestAreas;
	Deceased deceased;
	MembersInCESAudit membersInCESAudit;
	JoinReJoin joinReJoin;
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
		fullMemberRoster=PageFactory.initElements(driver, FullMemberRoster.class);
		activeMemberRoster = PageFactory.initElements(driver, ActiveMemberRoster.class);
		lapsedMembers=PageFactory.initElements(driver, LapsedMembers.class);
		upgradeToArchitect = PageFactory.initElements(driver, UpgradeToArchitect.class);
		upgradeToEmeritus= PageFactory.initElements(driver, UpgradeToEmeritus.class);
		terminatedMembers = PageFactory.initElements(driver, TerminatedMembers.class);
		chapterInfo = PageFactory.initElements(driver, ChapterInfo.class);
		associatePathToLicence =PageFactory.initElements(driver, AssociatePathToLicence.class);
		architectsLicenced = PageFactory.initElements(driver, ArchitectsLicenced.class);
		newGrad=PageFactory.initElements(driver, NewGrad.class);
		transfersIn=PageFactory.initElements(driver, TransfersIn.class);
		transfersOut=PageFactory.initElements(driver, TransfersOut.class);
		firmDetails=PageFactory.initElements(driver, FirmDetails.class);
		emailChangeLog=PageFactory.initElements(driver, EmailChangeLog.class);
		interestAreas=PageFactory.initElements(driver, InterestAreas.class);
		deceased=PageFactory.initElements(driver, Deceased.class);
		membersInCESAudit=PageFactory.initElements(driver, MembersInCESAudit.class);
        joinReJoin=PageFactory.initElements(driver, JoinReJoin.class);
        globalSearch = PageFactory.initElements(driver, GlobalSearch.class);
        commonMehodsInCP = new CommonMehodsInCP(driver);
		Logging.configure();
	}
	
	@Test(description = "FM-425: Membership Tab page and its section's verification", enabled = false, priority = 1)
	public void test_ValidateMembersShipTabSections(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateMembersShipTabSections");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		memChapterPortal.validateMemberShipPiecharts();
	}
	
	@Test(description = "FM-426: Full Member Roster report page ", enabled = false, priority = 2)
	public void test_ValidateFullMemberRoster(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateFullMemberRoster");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		fullMemberRoster.clickFullMemberRosterTab();
		fullMemberRoster.getFullMemberRosterRecordsCount();
		fullMemberRoster.getRecordsData();
	}
	
	@Test(description = "FM-427: Active Member Roaster report page ", enabled = true, priority = 3)
	public void test_ValidateActiveMemberRoster(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateActiveMemberRoster");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		activeMemberRoster.clickActiveMemberRosterTab();
		activeMemberRoster.getActiveMemberRosterRecordsCount();
		activeMemberRoster.getActiveMembersRecordsData();
	}
	
	@Test(description = "FM-428: Export functionality  Active Member Roaster report page ", enabled = false, priority = 4)
	public void test_ExportFuncActiveMemberRoster(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ExportfuncActiveMemberRoster");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		activeMemberRoster.clickActiveMemberRosterTab();
		String recordCount = activeMemberRoster.getActiveMemberRosterRecordsCount();
		activeMemberRoster.getActiveMembersRecordsData();
		activeMemberRoster.clickExportButton();
		activeMemberRoster.selectExportView("Formatted");
		activeMemberRoster.clickExportButtonInexportPopup("Export");
		activeMemberRoster.validateDownloafFileDataAndApplicationdata(recordCount);
	}
	
	
	
	@Test(description = "FM-437: Terminated Members report page", enabled = false, priority = 5)
	public void test_ValidateTerminatedMemberRoster(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateTerminatedMemberRoster");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		terminatedMembers.clickTerminatedMemberRosterTab();
		terminatedMembers.getTerminatedMemberRosterRecordsCount();
		terminatedMembers.getTerminatedMembersRecordsData();
	}
	
	@Test(description = "FM-438: Lapsed Members report page", enabled = false, priority = 6)
	public void test_ValidateLapsedMemberRoster(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateLapsedMemberRoster");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		lapsedMembers.clickLapsedMemberRosterTab();
		lapsedMembers.getLapsedMemberRosterRecordsCount();
		lapsedMembers.getLapsedMembersRecordsData();
	}
	
	@Test(description = "FM-439: Upgrade to Architect report page", enabled = false, priority = 7)
	public void test_ValidateUpgradeToArchitect(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateUpgradeToArchitect");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		upgradeToArchitect.clickUpgradeToArchitectTab();
		upgradeToArchitect.selectDateReportPopupInUpGradeArchitect("2/10/2023");
		upgradeToArchitect.getUpgradeToArchitectTabRecordsCount();
	}
	
	@Test(description = "FM-440: Upgrade to Emeritus report page", enabled = false, priority = 8)
	public void test_ValidateUpgradeToEmeritus(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateUpgradeToEmeritus");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		upgradeToEmeritus.clickUpgradeToEmeritusTab();
		upgradeToEmeritus.selectDateReportPopupInUpGradeEmeritus("2/10/2023");
		upgradeToEmeritus.getUpgradeToEmeritusTabRecordsCount();
	}
	
	@Test(description = "FM-441: \"Associate/Path to Licensure\" report page", enabled = false, priority = 9)
	public void test_ValidateAssociatePathToLicensure(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateAssociatePathToLicensure");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
	    associatePathToLicence.clickAssociatesPathtoLicensureTab();
	    associatePathToLicence.getAssociatesPathtoLicensureRecordsCount();
	    associatePathToLicence.getAssociatesPathtoLicensureRecordsData();
	}
	
	@Test(description = "FM-442: \"Active Architect Members Licensed <10Yrs\" report page", enabled = false, priority = 10)
	public void test_ValidateActiveArchitectMembersLicensed10Yrs(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateActiveArchitectMembersLicensed10Yrs");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		architectsLicenced.clickArchitectsLicensedTenyearsTab();
		architectsLicenced.getArchitectsLicensedTenyearsRecordsCount();
		architectsLicenced.getArchitectsLicensedTenyearsRecordsData();
	}
	
	@Test(description = "FM-446: Firm Details report page", enabled = false, priority = 11)
	public void test_ValidateFirmDetails(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateFirmDetails");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		firmDetails.clickFirmDetailsTab();
		firmDetails.getFirmDetailsTabRecordsCount();
	}
	
	@Test(description = "FM-447: Email Change Log report page", enabled = false, priority = 12)
	public void test_ValidateEmailChangeLog(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateEmailChangeLog");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		emailChangeLog.clickEmailChangeLogTab();
		emailChangeLog.selectDateReportPopupInEmailChange("2/10/2023");
		emailChangeLog.getEmailChangeLogTabRecordsCount();
	}
	
	@Test(description = "FM-449: Interest Areas report page", enabled = false, priority = 13)
	public void test_ValidateInterestAreas(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateInterestAreas");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		interestAreas.clickInterestAreasTab();
		interestAreas.getInterestAreasTabRecordsCount(); 
	}
	
	@Test(description = "FM-450: Deceased report page", enabled = false, priority = 14)
	public void test_ValidateDeceased(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateDeceased");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		deceased.clickDeceasedTab();
		deceased.selectDateReportPopupInDeceased("2/10/2023");
		deceased.getDeceasedTabRecordsCount();
	}
	
	@Test(description = "FM-451: Members in CES Audit report page", enabled = false, priority = 15)
	public void test_ValidateMembersInCESAudit(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateMembersInCESAudit");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		membersInCESAudit.clickMembersInCESAuditTab();
		membersInCESAudit.getMembersInCESAuditTabRecordsCount();
	}
	
	@Test(description = "FM-451: Join/ReJoin report page", enabled = false, priority = 16)
	public void test_ValidateJoinReJoin(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateMembersInCESAudit");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		joinReJoin.clickJoinReJoinTabTab();
		joinReJoin.selectDateReportPopupInJoinRejoin("2/10/2023");
		joinReJoin.getJoinReJoinTabRecordsCount();
	}
	
	@Test(description = "FM-458: Transfers Out report page", enabled = false, priority = 17)
	public void test_ValidateTransfersOutReport(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateTransfersOutReport");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		transfersOut.clickTransfersOutTab();
		transfersOut.selectDateReportPopupInTransferOut("2/10/2023");
		transfersOut.getTransfersOutTabRecordsCount();
	}
	
	@Test(description = "FM-459: Transfers In report page", enabled = false, priority = 18)
	public void test_ValidateTransfersInReport(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateTransfersInReport");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		transfersIn.clickTransfersInTab();
		transfersIn.selectDateReportPopupInTransferIn("2/10/2023");
		transfersIn.getTransfersInTabRecordsCount();
	}
	
	@Test(description = "FM-461: New Grad report page report page", enabled = false, priority = 19)
	public void test_ValidateNewGradReport(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("test_ValidateNewGradReport");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		newGrad.clicknewGradTab();
		newGrad.selectDateReportPopupInNewGrad("2/10/2023");
		newGrad.getnewGradTabRecordsCount();;
	}
	
	@Test(description = "FM-576 Seach functionality for report page's", enabled = false, priority = 20)
	public void ValidateSearchFuntionInActiveMemberRoaster(ITestContext context) throws InterruptedException, Throwable {
		if (recording) {
			VideoRecorder.startRecording("ValidateSearchFuntionInActiveMemberRoaster");
		}
		commonMehodsInCP.navigationChapterPortal("Allison Garwood Freedland");
		naToChapterPortal.getComunityGroup(1);
		memChapterPortal.validateMemberShipTabSections();
		activeMemberRoster.clickActiveMemberRosterTab();
		activeMemberRoster.SearchButtonInActiveMemberRoaster();
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
