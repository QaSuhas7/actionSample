package org.aia.pages.fonteva.chapterPortal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import com.google.inject.Key;

import lombok.val;

public class CommunityGroups {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(CommunityGroups.class);
	public static List<String>commuintyGroupsarrayList = new ArrayList<String>();

	public CommunityGroups(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}
	
	
	@FindBy(xpath  = "//a[@href='javascript:void(0);']/span[@class='slds-truncate']")  WebElement communityGroupElement;
	
	@FindAll(value = {@FindBy(xpath = "//table[@aria-label=\"All Communities - Chapter Portal\"]/tbody/tr")}) List<WebElement> communityGroupList;
	
    @FindBy(xpath = "//input[@name='PagesApi__Community_Group__c-search-input']") WebElement searchCommunityGroup;
    
    @FindBy(xpath =  "//span[@aria-label='All Communities - Chapter Portal']") WebElement itemsCountElement;
    
    @FindBy(css =  "lightning-formatted-rich-text[class='slds-rich-text-editor__output']") WebElement noItemsElement;
    
    
    public void communityGroupsSortedAssendingAndDescendingOrder() throws Throwable {
    	List<String> beforeAssendingAndDesendingOrderList=new ArrayList<String>();
    	List<String> afterAssendingList=new ArrayList<String>();
    	List<String> afterDesendingList=new ArrayList<String>();
    	Thread.sleep(7000);
    	for (int i = 0; i < communityGroupList.size(); i++) {
    	 String communityGroup=	communityGroupList.get(i).getText();
    	 beforeAssendingAndDesendingOrderList.add(communityGroup);
		}
    	System.out.println(beforeAssendingAndDesendingOrderList);
        // Sorted Descending
    	Collections.reverse(beforeAssendingAndDesendingOrderList);
    	System.out.println(beforeAssendingAndDesendingOrderList);
    	Thread.sleep(8000);
       // WebElement communityGroupElement =	driver.findElement(By.xpath("//a[@href='javascript:void(0);']/span[@class='slds-truncate']"));
    	util.waitUntilElement(driver, communityGroupElement);
    	util.clickUsingJS(driver, communityGroupElement);
    	Thread.sleep(5000);
    	for (int i = 0; i < communityGroupList.size(); i++) {
       	 String communityGroup=	communityGroupList.get(i).getText();
       	afterDesendingList.add(communityGroup);
   		}
    	System.out.println(afterDesendingList);
    	Assert.assertEquals(beforeAssendingAndDesendingOrderList, afterDesendingList);
    	
        //Sorted Ascending 	
    	Collections.sort(beforeAssendingAndDesendingOrderList);
    	System.out.println(beforeAssendingAndDesendingOrderList);
    	//WebElement communityGroupAssending =	driver.findElement(By.xpath("//a[@href='javascript:void(0);']/span[@class='slds-truncate']"));
    	util.waitUntilElement(driver, communityGroupElement);
    	util.clickUsingJS(driver, communityGroupElement);
    	Thread.sleep(5000);
    	for (int i = 0; i < communityGroupList.size(); i++) {
       	 String communityGroup=	communityGroupList.get(i).getText();
         afterAssendingList.add(communityGroup);
         System.out.println(afterAssendingList);
   		}
    	Assert.assertEquals(beforeAssendingAndDesendingOrderList, afterAssendingList);
    }
    
    public void getAllCommunityGroups() throws Throwable {
    	for (int i = 0; i < communityGroupList.size(); i++) {
    		Thread.sleep(1000);
    		commuintyGroupsarrayList.add(communityGroupList.get(i).getText());	
		}
    }
    
    public void searchCommunityGroupInMychapters() {
    	System.out.println("jj");
    	util.waitUntilElement(driver, searchCommunityGroup);
    	searchCommunityGroup.sendKeys("AIA California");
    	searchCommunityGroup.sendKeys(Keys.ENTER);
    }
    
    public void searchCommunityGroupWithNegativeData() {
    	util.waitUntilElement(driver, searchCommunityGroup);
    	searchCommunityGroup.sendKeys("AIA Ohio");
    	searchCommunityGroup.sendKeys(Keys.ENTER);
    	System.out.println(itemsCountElement.getText());
    	System.out.println(noItemsElement.getText());
    }
    
    

}
