package org.aia.pages.fonteva.chapterPortal;

import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class Finance {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(Finance.class);

	public Finance(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	@FindBy(xpath = "//button[text()='Finance']") WebElement financeTab;
	
	@FindBy(xpath = "//button[text()='Dues Installment Plan Report']") WebElement duesInstallamentLinkInFinanace;
	
	@FindBy(xpath = "//button[text()='Dues Waiver Report']") WebElement duesWaiverReportLinkInFinanace; 
	
	@FindBy(xpath = "//button[text()='Create report for a date range']") WebElement createReportButtonInfinance;
	
	@FindBy(xpath = "//div[@class='dt-outer-container']")WebElement achDistributiontableInFinance;
	
	@FindBy(xpath = "//div[@class='slds-page-header__col-title']//span[text()='Installments Report']") WebElement validateInstallament;
	
	@FindBy(xpath = "//h2[contains(@id,'modal-heading')]") WebElement headerMemberShipYear;
	
	@FindBy(xpath = "//button[contains(@id,'combobox-button')]") WebElement memberShipYearDropDown;
	
	@FindAll(value = {@FindBy(xpath = "//lightning-base-combobox-item[@data-value]")}) List<WebElement> selectMemberYears;

	@FindBy(xpath = "//button[text()='Go to Report']")WebElement gotoReportButton;
	
	@FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement duesInstallmentRecords;
	
    @FindBy(xpath = "//div[@title='Total Records']/parent::li") WebElement duesWaiverRecords;
    
    @FindBy(xpath = "//iframe[@title='Report Viewer']") WebElement duesInstallAndWaiverFrame;
    
    @FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement duesInstallmetNoDataMSg;
	
	@FindBy(xpath  = "//div[@class='report-table-widget-noData']") WebElement duesWaiverNoDataMSg;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>duesInstallmentRecordsData;
	
	@FindAll(value = {@FindBy(xpath = "//table[@class='data-grid-table data-grid-full-table']//tr[contains(@class,'data-grid-table-row')]")}) List<WebElement>duesWaiverRecordsData;
	
	public void clickFinanceTab() {
		Utility.waitForWebElement(driver, financeTab, 0);
		Assert.assertTrue(financeTab.isDisplayed());
		financeTab.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void validateFinanceTabSections() {
		Assert.assertTrue(duesInstallamentLinkInFinanace.isDisplayed());
		Assert.assertTrue(duesWaiverReportLinkInFinanace.isDisplayed());
		Assert.assertTrue(createReportButtonInfinance.isDisplayed());
		Assert.assertTrue(achDistributiontableInFinance.isDisplayed());
		System.out.println(achDistributiontableInFinance.getText());
	}
	
	public void clickDuesInstallmentLink() {
		util.waitUntilElement(driver, duesInstallamentLinkInFinanace);
		duesInstallamentLinkInFinanace.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void getDuesInstallemntRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		util.switchToFrameUsingWebElement(driver, duesInstallAndWaiverFrame);
		util.waitUntilElement(driver, validateInstallament);
		Assert.assertTrue(validateInstallament.isDisplayed());
		Utility.waitForWebElement(driver, duesInstallmentRecords, 0);
		String recordsCount = duesInstallmentRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = duesInstallmetNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(duesInstallmentRecordsData.size());
			for (int i = 0; i < duesInstallmentRecordsData.size(); i++) {
			Assert.assertTrue(duesInstallmentRecordsData.get(i).isDisplayed(), "Record is dsiplayed");
			String recordsData = duesInstallmentRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
	public void clickduesWaiverReportLink() {
		util.waitUntilElement(driver, duesWaiverReportLinkInFinanace);
		duesWaiverReportLinkInFinanace.click();
		util.waitForJavascript(driver, 10000, 2000);
	}
	
	public void selectMemberShipYear(String year) throws Throwable {
		Assert.assertTrue(headerMemberShipYear.isDisplayed());
		memberShipYearDropDown.click();
		Thread.sleep(2000);
	    System.out.println(selectMemberYears.size());
	    for (WebElement selectYear : selectMemberYears) {
	    	System.out.println(selectYear.getText());
	    	if(selectYear.getText().equals(year)) {
	    		selectYear.click();
	    		Thread.sleep(3000);
	    		gotoReportButton.click();
	    		break;
	    	}
		}
	}
	
	public void getDuesWaiverRecordsCount() throws Throwable {
		Thread.sleep(10000);
		util.switchToTabs(driver, 1);
		Thread.sleep(5000);
		util.switchToFrameUsingWebElement(driver, duesInstallAndWaiverFrame);
		Utility.waitForWebElement(driver, duesWaiverRecords, 0);
		String recordsCount = duesWaiverRecords.getText();
		String recordsTotal = recordsCount.replace("Total Records\n", "");
		System.out.println(recordsCount);
		if(recordsTotal.equalsIgnoreCase("0")) {
			String noDataMSg = duesWaiverNoDataMSg.getText();
			log.info(noDataMSg.replace("\n", ""));
		}else {
			Thread.sleep(8000);
			System.out.println(duesWaiverRecordsData.size());
			for (int i = 0; i < duesWaiverRecordsData.size(); i++) {
			Assert.assertTrue(duesWaiverRecordsData.get(i).isDisplayed(), "Record is displayed");
			String recordsData = duesWaiverRecordsData.get(i).getText();
			System.out.println(recordsData.replace("\n", ""));
			log.info(recordsData.replace("\n", ""));
			System.out.println("records count"+i);
			}
		}
	}
	
	

}
