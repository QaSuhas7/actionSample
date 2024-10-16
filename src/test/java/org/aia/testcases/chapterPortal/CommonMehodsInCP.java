package org.aia.testcases.chapterPortal;

import java.io.FileNotFoundException;

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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class CommonMehodsInCP  {
	WebDriver driver;
	NavigateToChapterPortal naToChapterPortal;
	GlobalSearch globalSearch;
	
	
	public CommonMehodsInCP(WebDriver Idriver) throws FileNotFoundException {
		driver= Idriver;
		naToChapterPortal = PageFactory.initElements(driver, NavigateToChapterPortal.class);
		globalSearch = PageFactory.initElements(driver, GlobalSearch.class);
		Logging.configure();
	}

	public  void navigationChapterPortal(String contactName) throws Throwable {
		naToChapterPortal.clickContactsModule();
		globalSearch.globalSearchInContact(contactName);
		globalSearch.clickContactsInGlobalSearch(contactName);
		naToChapterPortal.showAllInRealtedQuickLinks();
		naToChapterPortal.getPortalAccessCount();
		naToChapterPortal.clickDropDownInActionContainer();
		naToChapterPortal.optionsInactionContainer();
		naToChapterPortal.clickMyChapterTab();
	}

}
