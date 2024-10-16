package org.aia.utility;

import java.awt.AWTException;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.ss.formula.functions.Replace;
import org.apache.poi.util.SystemOutLogger;
import org.awaitility.Awaitility;
import org.bouncycastle.its.ITSPublicEncryptionKey.symmAlgorithm;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.validator.PublicClassValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.opencsv.CSVWriter;

import freemarker.core.ReturnInstruction.Return;

import org.testng.ITestResult;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import java.nio.file.*;

public class Utility {

	WebDriverWait wait;
	Robot robot;
	Actions action;

	public Utility(WebDriver driver, int time) {

	}

	public void acceptAlert() {
		wait.until(ExpectedConditions.alertIsPresent());
		// wait.until(Exp)
		// wait.until(ExpectedConditions.alertIsPresent()).accept();
	}

	/**
	 * @param firstFrame
	 * 
	 */
	public void switchToFrame(WebElement firstFrame) {
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(firstFrame));
	}

	/**
	 * @param frameElement" This function we can use for load and switch into the
	 *                      target frame with locator.
	 */
	public void switchToFrame(WebDriver driver, WebElement frameElement) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameElement));
	}

	public void dismissAlert() {
		wait.until(ExpectedConditions.alertIsPresent()).dismiss();
	}

	public String getAlertText() {
		return wait.until(ExpectedConditions.alertIsPresent()).getText();
	}

	public static String captureScreenshotld(WebDriver driver) {

		TakesScreenshot ts = (TakesScreenshot) driver;

		String path = System.getProperty("user.dir") + "\\Screenshots\\" + "AIA" + getCurrentDateTime() + ".png";

		try {
			FileHandler.copy(ts.getScreenshotAs(OutputType.FILE), new File(path));
		} catch (WebDriverException e) {
			System.out.println("Unable to capture screenshots " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Unable to capture screenshots " + e.getMessage());

		}

		return path;
	}

	public static String captureScreenshotFromBase64(WebDriver driver) {
		String newBase = null;
		TakesScreenshot ts = (TakesScreenshot) driver;
		try {

			String mybase = ts.getScreenshotAs(OutputType.BASE64);

			newBase = "data:image/png;base64," + mybase;

			System.out.println(mybase);

		} catch (WebDriverException e) {
			System.out.println("Unable to capture screenshots " + e.getMessage());
		}
		return newBase;
	}

	public static String getCurrentDateTime() {

		DateFormat myCustomDateFormat = new SimpleDateFormat("MM_dd_yyyy_HH_mm_ss");

		Date date = new Date();

		return myCustomDateFormat.format(date);
	}

	public boolean waitForWebElementDisappear(WebDriver driver, WebElement ele) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		return wait.until(ExpectedConditions.invisibilityOf(ele));
	}

	public boolean waitForWebElementDisappear(String xpath) {
		return wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
	}

	public WebElement waitForPresence(WebDriver driver, String xpath) {

		WebElement element = null;
		int x = 0;

		List<WebElement> elements = driver.findElements(By.xpath(xpath));

		if (elements.size() > 0) {
			x = elements.get(0).getLocation().getX();
		}

		while (x > 0) {
			element = driver.findElements(By.xpath(xpath)).get(0);
			break;
		}

		return element;

	}

	public static WebElement waitForWebElement(WebDriver driver, String xpath, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
		WebElement ele = driver.findElement(By.xpath(xpath));
		highLightElement(driver, ele);
		return ele;
	}

	public static WebElement waitForWebElement(WebDriver driver, WebElement element, int time) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(ExpectedConditions.visibilityOf(element));
		WebElement ele = wait.until(ExpectedConditions.elementToBeClickable(element));

		highLightElement(driver, ele);

		return ele;
	}

	public void waitUntilElement(WebDriver driver, WebElement element) {
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
		wait.until(ExpectedConditions.visibilityOf(element));

	}

	public static void highLightElement(WebDriver driver, WebElement element) {

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			System.out.println(e.getMessage());
		}

		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white');", element);

	}

	public void enterText(WebDriver driver, WebElement ele, String txt) {
		waitUntilElement(driver, ele);
		ele.clear();
		ele.sendKeys(txt);
	}

	public void selectDropDownByText(WebElement element, String text) {
		Select sel = new Select(element);
		sel.selectByVisibleText(text);
	}

	public String getSubString(String text, String textpattern) {
		// Custom input
		String string1 = "Payment Method Receipt Number: 0000218551 Posted Date";

		// Paranthesis indicate it is a group and signifies
		// it can have substring enclosed in single quote
		Pattern p;
		if (textpattern.isBlank() || textpattern.isEmpty()) {
			p = Pattern.compile(".+:(.+) Posted Date");
		} else {
			p = Pattern.compile(textpattern);
		}
		// This method returns a pattern object

		// Calling matcher() method of pattern object
		// and passing input character sequence
		Matcher m1 = p.matcher(string1);

		// Printing complete entered string 1
		System.out.println("String to be extracted : " + string1);

		// Condition check using matches() method which
		// looks out for content if any in single quote
		if (m1.matches()) {

			// Print the required sub-string
			System.out.println("Extracted part         : " + m1.group(1));
			return m1.group(1);
		} else {
			System.out.println("Extracted part not matched,");
			return null;
		}
	}

	public static String captureScreenshotbase64(WebDriver driver) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		String base64code = screenshot.getScreenshotAs(OutputType.BASE64);
		return base64code;
	}

	public static String captureScreenshot(WebDriver driver) {
		TakesScreenshot screenshot = (TakesScreenshot) driver;
		File sourceFile = screenshot.getScreenshotAs(OutputType.FILE);
		File sspath = new File(
				System.getProperty("user.dir") + "/ScreenShots/" + "AIA_" + getCurrentDateTime() + ".png");
		try {
			FileHandler.copy(sourceFile, sspath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sspath.getAbsolutePath();
	}

	public Select selectDrp(WebElement element) {
		Select option = new Select(element);
		return option;
	}

	public WebElement getCustomizedWebElement(WebDriver driver, String element, String replacement) {
		WebElement finalElement = driver.findElement(By.xpath(String.format(element, replacement)));
		waitUntilElement(driver, finalElement);
		return finalElement;
	}

	/**
	 * @param driver
	 * @param url
	 * @param tab
	 * @return
	 */
	public WebDriver switchToTab(WebDriver driver, int tab) {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tab));
		return driver;
	}

	public static Map<String, Object> parseJSONObjectToMap(JSONObject jsonObject) throws JSONException {
		Map<String, Object> mapData = new HashMap<String, Object>();
		Iterator<String> keysItr = jsonObject.keys();
		while (keysItr.hasNext()) {
			String key = keysItr.next();
			Object value = jsonObject.get(key);

			if (value instanceof JSONArray) {
				value = parseJSONArrayToList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = parseJSONObjectToMap((JSONObject) value);
			}
			mapData.put(key, value);
		}
		return mapData;
	}

	public static List<Object> parseJSONArrayToList(JSONArray array) throws JSONException {
		List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = parseJSONArrayToList((JSONArray) value);
			} else if (value instanceof JSONObject) {
				value = parseJSONObjectToMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	/**
	 * Here we get todays date using Localdate class from java.
	 * 
	 * @return
	 */
	public LocalDate todaysDate() {
		LocalDate localDate = java.time.LocalDate.now();
		return localDate;
	}

	/**
	 * Here we switching to new tab using below params
	 * 
	 * @param driver
	 * @param link
	 */
	public void createNewWindow(WebDriver driver, String link) {
		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(link);

	}

	public WebDriver switchToTabs(WebDriver driver, int tab) {
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(tab));
		return driver;
	}

	public void scrollingElementUsingJS(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", element);
	}

	public void clickUsingJS(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].click();", element);
	}

	public void switchToFrameUsingWebElement(WebDriver driver, WebElement element) {
		driver.switchTo().frame(element);

	}

	public void navigateToURl(WebDriver driver, String url) {
		driver.navigate().to(url);
	}

	public void navigateToBack(WebDriver driver) {
		driver.navigate().back();
	}

	public void waitForJavascript(WebDriver driver, int maxWaitMillis, int pollDelimiter) {
		double startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() < startTime + maxWaitMillis) {
			String prevState = driver.getPageSource();
			try {
				Thread.sleep(pollDelimiter);
				System.out.println("Waiting for Javascript Page loads!!!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // <-- would need to wrap in a try catch
			if (prevState.equals(driver.getPageSource())) {
				return;
			}
		}
	}

	public void waitForPageLoad(WebDriver driver) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
		wait.until(new Function<WebDriver, Boolean>() {
			public Boolean apply(WebDriver driver) {
				System.out.println("Current Window State       : "
						+ String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")));
				return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
						.equals("complete");
			}
		});
	}

	public boolean waitForURL(WebDriver driver, String pattern) {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(180));
		return wait.until(ExpectedConditions.urlContains(pattern));
	}

	public void domLoading(WebDriver driver, int maxWaitMillis, int pollDelimiter) {
		double startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() < startTime + maxWaitMillis) {
			String prevState = driver.getPageSource();
			try {
				Thread.sleep(pollDelimiter);
				System.out.println("Waiting for Javascript Page loads!!!");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} // <-- would need to wrap in a try catch
			if (prevState.equals(driver.getPageSource())) {
				return;
			}
		}
	}

	public List<String> getAllElementsText(WebDriver driver, String xpath) {
		List<WebElement> elements = driver.findElements(By.xpath(xpath));
		List<String> allElementText = new ArrayList<>();
		for (int i = 0; i < elements.size(); i++) {
			allElementText.add(elements.get(i).getText());
		}
		return allElementText;
	}

	public void fileUploadThroughKeyBoardActions(WebDriver driver, WebElement element, String filepath) {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		element.click();
		robot.setAutoDelay(2000);
		StringSelection stringSeclection = new StringSelection(filepath);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSeclection, null);
		robot.setAutoDelay(1000);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);

		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyRelease(KeyEvent.VK_V);

		robot.setAutoDelay(2000);

		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}

	public String randomStringGenerator(int n) {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + "0123456789" + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(n);
		for (int i = 0; i < n; i++) {
			int index = (int) (AlphaNumericString.length() * Math.random());
			sb.append(AlphaNumericString.charAt(index));
		}
		return sb.toString();
	}

	/**
	 * Writing csv
	 * 
	 * @param userEmail
	 * @param userName
	 * 
	 * @throws IOException
	 */
	public void writeCsv(String userName, String userEmail) throws IOException {
		File file = new File(System.getProperty("user.dir") + "/" + "User.csv");
		FileWriter outputfile = new FileWriter(file);
		CSVWriter writer = new CSVWriter(outputfile);
		// adding header to csv
		String[] header = { "Name", "Email" };
		writer.writeNext(header);
		List<String[]> userData = new ArrayList<>();
		String[] userCreated = { userName, userEmail };
		userData.add(userCreated);
		for (String[] user : userData) {
			writer.writeNext(user);
		}

		writer.close();
	}

	/**
	 * Here we are using awaitility for waiting the response from api
	 */

	public void mosueOverUsingAction(WebDriver driver, WebElement element) {
		action = new Actions(driver);
		action.moveToElement(element).perform();
	}

	/**
	 * Here we are using awaitility for waiting the response from api
	 */

	public void waitForResponse(final Response response, final int statusCode) {
		// Awaitility.await().atMost(10,TimeUnit.SECONDS).until(()->{return
		// response.getStatusCode()==statusCode;});
		Awaitility.await().atMost(10, TimeUnit.SECONDS).until(new Callable<Boolean>() {
			@Override
			public Boolean call() throws Exception {
				return response.getStatusCode() == statusCode;
			}
		});

	}

	public static void takeScreenShotAfterFail(WebDriver driver, ITestResult result) {
		TakesScreenshot ts = (TakesScreenshot) driver;
		File screenshot = ts.getScreenshotAs(OutputType.FILE);
		try {
			// Define the destination path for the screenshot
			String screenshotPath = "./ScreenShots/" + result.getName() + ".png";
			Files.copy(screenshot.toPath(), new File(screenshotPath).toPath(), StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Screenshot saved at: " + screenshotPath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getfileNameFromFolder(File[] a, String expectedFileName) {
		String fileName = null;
		for (int i = 0; i < a.length; i++) {
			if (a[i].getName().startsWith(expectedFileName)) {
				fileName = a[i].getName();
				break;
			}
		}
		return fileName;
	}

	public void scrollingBottomOfPage(WebDriver driver) {
		JavascriptExecutor js = ((JavascriptExecutor) driver);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	public void mouseOverToElement(WebDriver driver, WebElement element) {
		Actions actions = new Actions(driver);
		actions.moveToElement(element).click().build().perform();
	}

	public Response makeAPICallWithRetry(String endpoint, int maxAttempts, long delayMillis, String expectKey,
			Object expectedValue) {
		for (int attempt = 1; attempt <= maxAttempts; attempt++) {
			Response response = RestAssured.get(endpoint);

			if (response.getStatusCode() == 200 && containsExpectedValue(response, expectKey, expectedValue)) {
				System.out.println("Attempt #" + attempt + ": API call successful!");
				return response; // Return the response if successful
			} else {
				System.out.println("Attempt #" + attempt + ": API call failed. Retrying...");
			}

			try {
				Thread.sleep(delayMillis);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		return null; // Return null if all retry attempts fail
	}

	public boolean containsExpectedValue(Response response, String expectKey, Object expectedValue) {
		JsonPath jsonPath = response.jsonPath();
		Object actualValue = jsonPath.get(expectKey);
		return actualValue != null && actualValue.equals(expectedValue);

	}

	public static void retryableSeleniumOperation(int retryCount, long waitTimeInMilliseconds, Runnable seleniumAction)
			throws InterruptedException {
		// Flag to check if the operation was successful
		boolean flag = false;

		// Loop for the number of retries
		while (retryCount > 0 && !flag) {
			try {
				// Perform the Selenium operation here
				seleniumAction.run();
				System.out.println("retrying for element" + retryCount);
				// Operation completed successfully, set the flag to true
				flag = true;
			} catch (NoSuchElementException e) {
				// NoSuchElementException caught, decrement the retry count
				retryCount--;

				// Wait for the specified time
				Thread.sleep(waitTimeInMilliseconds);

				// If retry count is zero, the operation is deemed unsuccessful
				if (retryCount == 0) {
					System.out.println("Operation Failed...");
				}
			}
		}
	}

	public static void retryElement(int retryCount, WebDriver driver, WebElement element, int time, WebElement element2)
			throws InterruptedException {
		boolean flag = false;
		while (retryCount > 0 && !flag) {
			try {
				if (retryCount < 3) {
					JavascriptExecutor js = (JavascriptExecutor) driver;
					js.executeScript("arguments[0].click();", element2);
				}
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(70));
				wait.until(ExpectedConditions.visibilityOf(element));
				System.out.println("element is visible in dom");
				wait.until(ExpectedConditions.elementToBeClickable(element));
				System.out.println("Element is clickable in dom");
				highLightElement(driver, element);
				flag = true;
			} catch (Exception e) {
				retryCount--;
				System.out.println("retry count for webElement" + retryCount);
				// Wait for the specified time
				Thread.sleep(time);
			}
			if (retryCount == 0) {
				System.out.println("Operation Failed...");
			}
		}
	}

	/**
	 * Here we are deleting file from specific folder
	 * 
	 */
	public void deleteFile() {
		File file = new File(System.getProperty("user.dir") + File.separator + "DownloadFiles");
		File[] contents = file.listFiles();
		System.out.println("List of files and directories in the specified directory:");
		for (File file1 : contents) {
			if (file1.getName().contains("join")) {
				file1.delete();
				System.out.println("=========File is deleted ===========");
			} else {
				System.out.println("=========File is not available==========");
			}

		}

	}

	public void deleteAllFile() {
		String directoryPath = System.getProperty("user.dir") + File.separator + "DownloadFiles";
        Path directory = Paths.get(directoryPath);

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
            for (Path file : stream) {
                Files.delete(file); // Delete each file
                System.out.println("Deleted: " + file.getFileName());
                System.out.println("=========File is deleted ===========");
            }
        } catch (IOException e) {
            System.err.println("Error occurred while deleting files: " + e.getMessage());
            System.out.println("=========File is not available==========");
        }
	}

	public File getFile() {
		File file = new File(System.getProperty("user.dir") + File.separator + "DownloadFiles");
		File[] contents = file.listFiles();
		System.out.println("List of files and directories in the specified directory:");
		for (File file1 : contents) {
			if (file1.getName().contains("join")) {
				System.out.println("Avaibale file name is:" + file1.getName());
				System.out.println("=========File is available ===========");
//				break;
			} else {
				System.out.println("=========File is not available==========");
			}
			return file1;
		}
		return null;

	}


	/**
	 * @param fileName
	 * @return
	 */
	public File getAnyFile(String fileName) {
		File file = new File(System.getProperty("user.dir") + File.separator + "DownloadFiles");
		File[] contents = file.listFiles();
		System.out.println("List of files and directories in the specified directory:");
		for (File file1 : contents) {
			if (file1.getName().contains(fileName)) {
				System.out.println("Avaibale file name is:" + file1.getName());
				System.out.println("=========File is available ===========");
//				break;
			} else {
				System.out.println("=========File is not available==========");
			}
			return file1;
		}
		return null;

	}

	
	/**
	 * @param pdfFile
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public Map<String, String> extractKeyValuePairsFromAnyPDF(File pdfFile, String filename) throws IOException, InterruptedException {
		try (PDDocument document = PDDocument.load(getAnyFile(filename))) {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(document);
			Thread.sleep(5000);
			return extractKeyValuePairs(text);
		}

	}

	/**
	 * @param value
	 * @param filename
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String getAnyPDFReceiptValue(String value,String filename) throws IOException, InterruptedException {
		Map<String, String> map = extractKeyValuePairsFromAnyPDF(getAnyFile(filename), filename);
		System.out.println(map);
		String dues = map.get(value);
		return dues;
	}

	
	/**
	 * @param text
	 * @return This function we are using for reading data from PDF and storing into
	 *         map.using regular expression.
	 */
	public Map<String, String> extractKeyValuePairs(String text) {
		Map<String, String> keyValuePairs = new HashMap<>();

		// Define a more flexible pattern for key-value pairs
		Pattern pattern = Pattern.compile("([\\w\\s]+):\\s*(.+)");
		Matcher matcher = pattern.matcher(text);

		// Extract key-value pairs
		while (matcher.find()) {
			String key = matcher.group(1).trim().replaceAll("\\r\\n|\\r|\\n", " ").replaceAll("[0-9]", "").trim();
			String value = matcher.group(2).trim();
			keyValuePairs.put(key, value);
		}

		return keyValuePairs;
	}

	/**
	 * @param pdfFile
	 * @return
	 * @throws IOException          This function we are using for reading data from
	 *                              PDF and storing into map.
	 * @throws InterruptedException
	 */
	public Map<String, String> extractKeyValuePairsFromPDF(File pdfFile) throws IOException, InterruptedException {
		try (PDDocument document = PDDocument.load(getFile())) {
			PDFTextStripper textStripper = new PDFTextStripper();
			String text = textStripper.getText(document);
			Thread.sleep(5000);
			return extractKeyValuePairs(text);
		}

	}

	public String getPDFReceiptValue(String value) throws IOException, InterruptedException {
		Map<String, String> map = extractKeyValuePairsFromPDF(getFile());
		String dues = map.get(value);
		return dues;
	}

	public String replaceStringvalues(String str) {
		String replacedString = str.replaceAll("[\\s|\\u00A0]+", "").replace("$", "").replaceFirst("\\.0$", ".")
				.replaceFirst("0$", "");
		return replacedString;
	}

}
