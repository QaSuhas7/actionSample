package org.aia.pages.membership;

import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class CheckYourEmailPage {

	WebDriver driver;
	Utility util = new Utility(driver, 30);
	
	public CheckYourEmailPage(WebDriver Idriver)
	{
		this.driver=Idriver;
	}

	
	@FindBy(xpath="//span[text()= 'Close']") WebElement closebtn;
	
	@FindBy(css = "[class='header'] span") WebElement header;
	
	@FindBy(css = "[class='modal-row'] span") WebElement message;
	
	public void clickCloseAfterVerification() throws InterruptedException
	{
		closebtn.click();
		Thread.sleep(1000);
	}

}
