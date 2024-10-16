package org.aia.pages.fonteva.events;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;

import org.aia.utility.Utility;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

public class VenuesEvent {
	WebDriver driver;
	Utility util = new Utility(driver, 30);
	JavascriptExecutor executor;
	Actions act;
	static Logger log = Logger.getLogger(VenuesEvent.class);
	public String salesOrder = "";
	public String aiaNumber = "";

	public VenuesEvent(WebDriver IDriver) {
		this.driver = IDriver;
		executor = (JavascriptExecutor) driver;
		act = new Actions(driver);
	}

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//div[@data-menu='EventApi:EventBuilderVenues']")
	WebElement venueModule;

	public static String venueEditIcon(String venueName) {
		String xpath = "(//td[contains(text(),'" + venueName + "')]//following-sibling::td[3]//a)[1]";
		return xpath;
	}

	@FindBy(xpath = "//button[contains(text(),'Add Venue')]")
	WebElement addVenueButton;

	public static String venueDeleteIcon(String venueName) {
		String xpath = "(//td[contains(text(),'" + venueName + "')]//following-sibling::td[3]//a)[2]";
		return xpath;
	}

	@FindBy(xpath = "(//button[contains(text(),'Delete')])[2]")
	WebElement confirmationDeleteButton;

	@FindBy(xpath = "//div[@class='EventApiEventBuilderVenues']//div//div[@class='slds-text-heading--small']")
	WebElement createdVenueCountText;

	@FindBy(xpath = "//h2[contains(text(),'Create Venue')]")
	WebElement createVenueTextInPopUp;

	@FindBy(xpath = "//label[contains(text(),'Venue Name')]//parent::span//following-sibling::div//input")
	WebElement venueNameInputField;

	@FindBy(xpath = "//label//span[contains(text(),'Primary Venue')]//preceding-sibling::span")
	WebElement primaryVenueCheckBox;

	@FindBy(xpath = "//span[@data-name='description' and @data-label='Description']/following::div[@class='fr-element fr-view cke_editable']/p")
	WebElement descriptionTextField;

	@FindBy(xpath = "(//label[contains(text(),'Address')]//parent::span//following-sibling::div//input)[1]")
	WebElement addressInputField;

//	@FindBy(xpath  = "//span[contains(text(),'Hyderabad, Bhagya Nagar Colony, Kukatpally, Hyderabad, Telangana, India')]")
//	WebElement selectAddressBasedUponInputField;

	@FindBy(xpath = "//div[@class='windowViewMode-normal oneContent active lafPageHost']//label[@data-name='manualAddress']//input")
	WebElement enterAddressManually;

	@FindBy(xpath = "//label[contains(text(),'Street')]/parent::span//following-sibling::div//textarea")
	WebElement street;

	@FindBy(xpath = "//label[contains(text(),'City')]/parent::span//following-sibling::div//input")
	WebElement city;

	@FindBy(xpath = "//label[contains(text(),'Country')]/parent::span//following-sibling::div//select[@name='Country']")
	WebElement country;

	@FindBy(xpath = "//label[contains(text(),'Zip Code')]//parent::span//following-sibling::div//input")
	WebElement pincode;

	@FindBy(xpath = "//label//span[contains(text(),'Display Map')]//preceding-sibling::span")
	WebElement displayMapCheckBox;

	@FindBy(xpath = "(//label[contains(text(),'Venue Image URL')]//parent::span//following-sibling::div//input)[1]")
	WebElement venueImageURLInput;

	@FindBy(xpath = "//label[contains(text(),'Browse')]") // button[@data-label='Save']
	WebElement browseButton;

	@FindBy(xpath = "//input[@placeholder='Browse for files or paste in a URL']/following::input[@name='file' and @aria-label='venueImageUrl']") // button[@data-label='Save']
	WebElement uploadFile;

	@FindBy(xpath = "//div[@data-name='venueImageUrl']//button[@data-label='Save' and @data-name='saveCroppedImage']")
	WebElement cropimageSaveButton;

	@FindBy(xpath = "//button[contains(text(),'Save & Continue')]//preceding-sibling::button[contains(text(),'Cancel')]")
	WebElement cancelButton;

	@FindBy(css = "div[class='windowViewMode-normal oneContent active lafPageHost'] button[data-name='venueModalSaveButton']")
	WebElement saveContinueButton;

	@FindBy(xpath = "//h2[contains(text(),'Create Venue')]//parent::div//span[@part='boundary']")
	WebElement closeButonOfCreateVenuePopUp;

	public static String venueName(String venueName) {
		String venueName1 = "//td[contains(text(),'" + venueName + "')]";
		return venueName1;
	}

	String getTotalVenuNames = "//table[@class='slds-table slds-table--bordered slds-theme--shade slds-no-row-hover']//tbody//tr//td[2]";

	@FindBy(xpath = "(//button[text()='Add Venue']//parent::div//div)[1]")
	WebElement totalVenueRecordsCountText;

	public void navigateIntoVenueModule() {
		util.waitUntilElement(driver, venueModule);
		venueModule.click();
		util.waitUntilElement(driver, createdVenueCountText);
		Assert.assertTrue(createdVenueCountText.isDisplayed(), "create Venue count Text is not available");
	}

	public void clickAddVenue() {
		util.waitUntilElement(driver, addVenueButton);
		addVenueButton.click();
	}

	public void navigateAddIntoVenuePopUpTabAndVerifyIt() {
		clickAddVenue();
		util.waitUntilElement(driver, createdVenueCountText);
		util.scrollingElementUsingJS(driver, createdVenueCountText);
		Assert.assertTrue(createdVenueCountText.isDisplayed(), "create Venue Text is not available");
	}

	public void verifyCreateVenueinputFields() {

		util.scrollingElementUsingJS(driver, createdVenueCountText);
		Assert.assertTrue(createdVenueCountText.isDisplayed(), "create Venue Text is not available");

		util.scrollingElementUsingJS(driver, venueNameInputField);
		Assert.assertTrue(venueNameInputField.isDisplayed(), "venue Name InputField field is not available");

		util.waitUntilElement(driver, descriptionTextField);
		util.scrollingElementUsingJS(driver, descriptionTextField);
		Assert.assertTrue(descriptionTextField.isDisplayed(), "description Text Field field is not available");

//		util.waitUntilElement(driver, addressInputField);
//		util.scrollingElementUsingJS(driver, addressInputField);
//		Assert.assertTrue(addressInputField.isDisplayed(), "addressInputField field is not available");

		util.waitUntilElement(driver, enterAddressManually);
		util.scrollingElementUsingJS(driver, enterAddressManually);
		Assert.assertTrue(enterAddressManually.isDisplayed(), " Enter Address Manually field is not available");

		util.waitUntilElement(driver, displayMapCheckBox);
		util.scrollingElementUsingJS(driver, displayMapCheckBox);
		Assert.assertTrue(displayMapCheckBox.isDisplayed(), "displayMapCheckBox field is not available");

		util.waitUntilElement(driver, venueImageURLInput);
		util.scrollingElementUsingJS(driver, venueImageURLInput);
		Assert.assertTrue(venueImageURLInput.isDisplayed(), "venueImageURLInput field is not available");

		util.waitUntilElement(driver, closeButonOfCreateVenuePopUp);
		util.scrollingElementUsingJS(driver, closeButonOfCreateVenuePopUp);
		Assert.assertTrue(closeButonOfCreateVenuePopUp.isDisplayed(),
				" close Buton Of Create Venue PopUp is not available");

		util.waitUntilElement(driver, saveContinueButton);
		util.scrollingElementUsingJS(driver, saveContinueButton);
		Assert.assertTrue(saveContinueButton.isDisplayed(), " save & ContinueButton field is not available");

		util.waitUntilElement(driver, cancelButton);
		util.scrollingElementUsingJS(driver, cancelButton);
		Assert.assertTrue(cancelButton.isDisplayed(), " field is not available");
	}

	public void ClickOnCrossButtonAndVerifycreateNewVenuepopUpTabIsClosed() {
		util.waitUntilElement(driver, closeButonOfCreateVenuePopUp);
		closeButonOfCreateVenuePopUp.click();
		Assert.assertFalse(createVenueTextInPopUp.isDisplayed(), "venue cross button is not working");
	}

	public void ClickOnCloseButtonAndVerifycreateNewVenuepopUpTabIsClosed() {
		util.waitUntilElement(driver, cancelButton);
		util.scrollingElementUsingJS(driver, cancelButton);
		cancelButton.click();
		Assert.assertFalse(createVenueTextInPopUp.isDisplayed(), "venue close button is not working");
	}

	public void createNewVenue(String description, Map<String, String> address, String imageURL)
			throws AWTException, InterruptedException {
		EventConfig.venueNameInputField = util.randomStringGenerator(5);
		System.out.println("Created event Name: " + EventConfig.venueNameInputField);
		EventConfig.getVenueRecordsCount = totalVenueRecordsCountText.getText();
		Map<String, String> venueAddress = EventConfig.address;

		util.scrollingElementUsingJS(driver, venueNameInputField);
		venueNameInputField.sendKeys(EventConfig.venueNameInputField);

		Thread.sleep(3000);
		util.scrollingElementUsingJS(driver, descriptionTextField);
		descriptionTextField.sendKeys(description);

		util.waitUntilElement(driver, enterAddressManually);
		Utility.highLightElement(driver, enterAddressManually);
		if (enterAddressManually.isSelected()) {
			System.out.println("is published checkobox is enabled");
		} else {
			util.clickUsingJS(driver, enterAddressManually);
			log.info("IsPublished CheckBox is clicked");
		}

		util.scrollingElementUsingJS(driver, street);
		street.sendKeys(venueAddress.get("street"));

		util.scrollingElementUsingJS(driver, city);
		city.sendKeys(venueAddress.get("city"));

		Utility.waitForWebElement(driver, country, 10);
		util.scrollingElementUsingJS(driver, country);
		util.selectDropDownByText(country, venueAddress.get("country"));

		util.scrollingElementUsingJS(driver, pincode);
		pincode.sendKeys(venueAddress.get("pincode"));

		util.scrollingElementUsingJS(driver, displayMapCheckBox);
		displayMapCheckBox.click();

		util.scrollingElementUsingJS(driver, venueImageURLInput);
		uploadFile.sendKeys(imageURL);
		Thread.sleep(5000);
		if (cropimageSaveButton.isDisplayed()) {
			cropimageSaveButton.click();
		}
		Thread.sleep(7000);
		util.waitUntilElement(driver, saveContinueButton);
		saveContinueButton.click();

	}

	public void newlyCreatedVenueIsDisplayedInVenuesListingScreen() throws InterruptedException {
		System.out.println("createdvenueName :" + EventConfig.venueNameInputField);
		Thread.sleep(2000);
		boolean value = false;
		List<WebElement> elms = driver.findElements(By.xpath(getTotalVenuNames));
		for (int i = 0; i < elms.size(); i++) {
			System.out.println("elms.get(i).getText() :" + elms.get(i).getText());
			if (elms.get(i).getText().equalsIgnoreCase(EventConfig.venueNameInputField)) {
				value = true;
				break;
			}
		}
		Thread.sleep(5000);
		Assert.assertTrue(value,
				"Assert failure:- " + EventConfig.venueNameInputField + " is not dispayed in the venue listing screen");
	}

	public void VerifyTotalCreatedVenueCounts() {
		EventConfig.getVenueRecordsCount2 = totalVenueRecordsCountText.getText();
		boolean value = false;

		if (Integer.parseInt(EventConfig.getVenueRecordsCount2.replaceAll("[a-zA-z() ]", "")) > Integer
				.parseInt(EventConfig.getVenueRecordsCount.replaceAll("[a-zA-z() ]", ""))) {
			value = true;
		}
	}

	public void editCreatedVenueNameAndVerifyIt() throws InterruptedException {
        Thread.sleep(5000);
		System.out.println("edit event Name: " + EventConfig.venueNameInputField);

		util.scrollingElementUsingJS(driver,
				driver.findElement(By.xpath(VenuesEvent.venueEditIcon(EventConfig.venueNameInputField))));
		driver.findElement(By.xpath(VenuesEvent.venueEditIcon(EventConfig.venueNameInputField))).click();

		EventConfig.venueNameInputField = util.randomStringGenerator(5);
		util.scrollingElementUsingJS(driver, venueNameInputField);
		util.waitUntilElement(driver, venueNameInputField);
		venueNameInputField.clear();
		venueNameInputField.sendKeys(EventConfig.venueNameInputField);

		util.waitUntilElement(driver, saveContinueButton);
		util.scrollingElementUsingJS(driver, saveContinueButton);
		saveContinueButton.click();

		newlyCreatedVenueIsDisplayedInVenuesListingScreen();
	}

	public void deleteVenueNameAndVerifyIt() throws InterruptedException {

		util.scrollingElementUsingJS(driver,
				driver.findElement(By.xpath(VenuesEvent.venueDeleteIcon(EventConfig.venueNameInputField))));
		driver.findElement(By.xpath(VenuesEvent.venueDeleteIcon(EventConfig.venueNameInputField))).click();
		util.waitUntilElement(driver, confirmationDeleteButton);
		confirmationDeleteButton.click();
		Thread.sleep(2000);
		boolean value = false;
		List<WebElement> elms = driver.findElements(By.xpath(getTotalVenuNames));
		for (int i = 0; i < elms.size(); i++) {
			System.out.println("elms.get(i).getText() :" + elms.get(i).getText());
			if (elms.get(i).getText().equalsIgnoreCase(EventConfig.venueNameInputField)) {
				value = true;
				break;
			}
		}
		Assert.assertFalse(value, "Assert failure:- " + EventConfig.venueNameInputField
				+ " is not deleted yet, it is dispayed in the venue listing screen");
	}

}
