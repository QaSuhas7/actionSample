package org.aia.pages.api;



import static org.testng.Assert.assertTrue;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DateUtils;
import org.aia.utility.Logging;
import org.aia.utility.Utility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MailinatorAPI {

    WebDriver driver;

	Utility util = new Utility(driver, 30);
	ConfigDataProvider data = new ConfigDataProvider();

	public MailinatorAPI(WebDriver IDriver) {
		this.driver = IDriver;
	}

	String domain = "architects-team.m8r.co";
	String msgId = "";
	String bearerToken = "13779f35d3cc4108a0cf41ef417d183f";
	String MAILINATOR_API = "https://api.mailinator.com/v2/domains/architects-team.m8r.co/inboxes/";
	String MAILINATOR_INBOS_ENDPOINT = "https://mailinator.com/api/v2/domains/architects-team.m8r.co/inboxes/";

	@FindBy(xpath = "//span[text()='SUCCESS']")
	WebElement successMessage;

	public void verifyEmailForAccountSetup(String emailPrefix) throws InterruptedException {
		String inbox = emailPrefix;

		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		System.out.println("Message Id is+ " + messageId);

		Thread.sleep(15000);
		String message_uri = MAILINATOR_API + inbox + "/messages/" + messageId + "/links";
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(message_uri).then().extract().response();

		Logging.logger.info(response.asString());
		jsonPathEval = response.jsonPath();
		Thread.sleep(20000);
		String link = jsonPathEval.getString("links[0]");
		System.out.println("Message Id is " + link);

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(link);
		Thread.sleep(5000);
		util.waitUntilElement(driver, successMessage);
		driver.switchTo().window(tabs.get(0));

	}

	public String GetLinks(String emailprefix, String subject, String pattern) {
		String URI = "https://mailinator.com/api/v2/domains/" + domain + "/inboxes/";
		String token = "13779f35d3cc4108a0cf41ef417d183f";
		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", token).when()
				.get(URI).then().extract().response();
		String responseBody = response.getBody().asString();
		// System.out.println("Response Body is "+ responseBody);
		// System.out.println("Status code is "+
		// response.then().assertThat().statusCode(200));

		JsonPath js = new JsonPath(responseBody);
		List<HashMap<String, Object>> msgs = js.getList("msgs");
		System.out.println("MSGS is " + msgs);

		for (HashMap<String, Object> singleObject : msgs) {
			if (singleObject.get("to").equals(emailprefix) && singleObject.get("subject").equals(subject)) {
				System.out.println("****To******: " + singleObject.get("id"));
				msgId = singleObject.get("id").toString();
				System.out.println("MessageId: " + msgId);
			}
		}
		String finalMailURI = "https://mailinator.com/api/v2/domains/" + domain + "/inboxes/" + emailprefix
				+ "/messages/" + msgId + "/links";
		System.out.println("finalMailURI: " + finalMailURI);
		Response resp = RestAssured.given().headers("Authorization", token).when().get(finalMailURI).then().extract()
				.response();
		String respBody = resp.getBody().asString();
		System.out.println("Response Body is " + respBody);

		System.out.println("Status is" + response.then().assertThat().statusCode(200));

		JsonPath json = new JsonPath(respBody);
		System.out.println("JSON: " + json.prettyPrint());
		List<String> links = json.getList("links");

		String lnk = "";

		for (String l : links) {
			if (l.contains(pattern))// confirm-signup?
				lnk = l;
			System.out.println("Verification link is " + lnk);
			break;
		}

		return lnk;
	}

	public void welcomeAIAEmailLink(ArrayList<String> dataList, ArrayList<Object> receiptData)
			throws InterruptedException {
		String inbox = dataList.get(3);

		JsonPath jsonPathEval = null;

		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		System.out.println("Message Id is " + messageId);

		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(7000);

		System.out.println(response.getBody().asPrettyString());
		String value = response.path("parts[1].body").toString();
		System.out.println("body is " + value);

		Assert.assertTrue(value.contains("Thanks for joining AIA!"));
		Assert.assertTrue(value.contains(dataList.get(0)));
		Assert.assertTrue(value.contains(dataList.get(1)));
		Assert.assertTrue(value.contains((CharSequence) receiptData.get(1)));

		String links_uri = "https://mailinator.com/api/v2/domains/architects-team.m8r.co/inboxes/" + inbox
				+ "/messages/" + messageId + "/links";
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(links_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);

		String link = jsonPathEval.getString("links[4]");
		String finallink;
		if (link.contains("apex")) {
			finallink = link;
		} else {
			String link1 = jsonPathEval.getString("links[3]");
			finallink = link1;
		}

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(finallink);
		// util.waitUntilElement(driver, successMessage);
		driver.switchTo().window(tabs.get(0));
	}

	public void thanksForRenewingEmailLink(ArrayList<String> dataList, ArrayList<Object> receiptData)
			throws InterruptedException {
		String inbox = dataList.get(3);

		JsonPath jsonPathEval = null;

		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		String messageSubject = jsonPathEval.getString("msgs[0].subject");
		System.out.println("Message Id is " + messageId);
		System.out.println("Member got renewed " + messageSubject);
		Assert.assertTrue(messageSubject.contains("Thanks for renewing your AIA membership"));

		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);

		String value = response.path("parts[1].body").toString();
		System.out.println("body is " + value);

		Assert.assertTrue(value.contains("your AIA membership. By renewing"));
		Assert.assertTrue(value.contains(dataList.get(0)));
		Assert.assertTrue(value.contains(dataList.get(1)));
		Assert.assertTrue(value.contains((CharSequence) receiptData.get(1)));

		String links_uri = "https://mailinator.com/api/v2/domains/architects-team.m8r.co/inboxes/" + inbox
				+ "/messages/" + messageId + "/links";
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(links_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);

		String link = jsonPathEval.getString("links[4]");
		String finallink;
		if (link.contains("apex")) {
			finallink = link;
		} else {
			String link1 = jsonPathEval.getString("links[3]");
			finallink = link1;
		}

		((JavascriptExecutor) driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.get(finallink);
		// Bug
		// util.waitUntilElement(driver, successMessage);
		driver.switchTo().window(tabs.get(0));
	}

	public void thankYouEmailforOfflineJoin(String emailPrefix) throws InterruptedException {
		String inbox = emailPrefix;
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response = RestAssured.given().headers("Content-Type",

				ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken).when().get(mailinator_uri)
				.then().extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		System.out.println("link response:" + response.getBody().asPrettyString());
		Thread.sleep(7000);
		String msgBody = response.path("parts[0].body").toString();
		System.out.println("body is " + msgBody);
		Assert.assertTrue(msgBody.contains(data.testDataProvider().getProperty("thankyouJoinMail")));
	}

	public void thankYouEmailforOfflineJoinAutoRenew(String emailPrefix) throws InterruptedException {
		Thread.sleep(100000);
		Response response;
		String inbox=emailPrefix;
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);
		response =  RestAssured.given().headers("Content-Type",

				ContentType.JSON, "Accept",
				ContentType.JSON,"Authorization",
				bearerToken).
				when().
				get(mailinator_uri).
				then().
				extract().response();
		System.out.println(response.getBody().asPrettyString());
		Thread.sleep(10000);
		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId ;
		 response =  RestAssured.given().
				 headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		System.out.println("link response:"+response.getBody().asPrettyString());
		Thread.sleep(7000);
		String msgBody1 = response.path("parts[0].body").toString();
		System.out.println("body is " + msgBody1);
		Assert.assertTrue(msgBody1.contains(data.testDataProvider().getProperty("thanksforAutoRenewalMail")));
		Assert.assertTrue(msgBody1.contains(data.testDataProvider().getProperty("daysToRenew")));
	}
	
	public void thankYouEmailforOfflineRenew(String emailPrefix) throws InterruptedException {
		String inbox = emailPrefix;
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(7000);
		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());
		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(message_uri).then().extract().response();

		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		String msgBody = response.path("parts[0].body").toString();
		System.out.println("body is " + msgBody);
		Assert.assertTrue(msgBody.contains(data.testDataProvider().getProperty("thankyouJoinMail")));
	}

	public void registrationConfirmationEmailforEvents(ArrayList<String> dataList, String eventName) throws Throwable {
		try {
			String inbox = dataList.get(3);
			JsonPath jsonPathEval = null;
			String mailinator_uri = MAILINATOR_API + inbox;
			Thread.sleep(7000);
			Response response = RestAssured.given()
					.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
					.when().get(mailinator_uri).then().extract().response();
			System.out.println(response.getBody().asPrettyString());
			jsonPathEval = response.jsonPath();
			String messageId = jsonPathEval.getString("msgs[1].id");
			System.out.println("Message Id is " + messageId);
			String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
			response = RestAssured.given()
					.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
					.when().get(message_uri).then().extract().response();
			jsonPathEval = response.jsonPath();
			Thread.sleep(5000);
			System.out.println(response.getBody().asPrettyString());
			String value = jsonPathEval.getString("parts[1].body");
			System.out.println("body is " + value);
			Assert.assertTrue(value.contains(eventName));
			System.out.println("event name is contains in email");
			Assert.assertTrue(value.contains(dataList.get(0)));
		} catch (Throwable e) {
			System.out.println(e.getMessage());
		}
	}

	public void sessionConfirmationEmailforEvents(ArrayList<String> dataList, String eventName) throws Throwable {
		String inbox = dataList.get(3);
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(7000);
		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());
		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		System.out.println("Message Id is " + messageId);
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(message_uri).then().extract().response();
		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		System.out.println(response.getBody().asPrettyString());
		String value = jsonPathEval.getString("parts[1].body");
		System.out.println("body is " + value);
		Assert.assertTrue(value.contains(eventName));
		Assert.assertTrue(value.contains(dataList.get(0)));
	}

	public void registrationConfirmationEmailforEmailAttendees(ArrayList<String> emailList, String eventName)
			throws Throwable {
		for (int i = 0; i < emailList.size(); i++) {
			String inbox = emailList.get(i);
			JsonPath jsonPathEval = null;
			String mailinator_uri = MAILINATOR_API + inbox;
			Thread.sleep(7000);
			Response response = RestAssured.given()
					.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
					.when().get(mailinator_uri).then().extract().response();
			System.out.println(response.getBody().asPrettyString());
			jsonPathEval = response.jsonPath();
			String messageId = jsonPathEval.getString("msgs[0].id");
			System.out.println("Message Id is " + messageId);
			String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
			Thread.sleep(5000);
			response = RestAssured.given()
					.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
					.when().get(message_uri).then().extract().response();
			jsonPathEval = response.jsonPath();
			System.out.println(response.getBody().asPrettyString());
			String value = jsonPathEval.getString("parts[1].body");
			System.out.println("body is " + value);
			Assert.assertTrue(value.contains(eventName));
		}
	}

	public void validateFutureDatesessionConfirmationEmailforEvents(ArrayList<String> dataList, String eventName,
			String futureDate) throws Throwable {
		String inbox = dataList.get(3);
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(7000);
		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());
		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		System.out.println("Message Id is " + messageId);
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId;
		response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(message_uri).then().extract().response();
		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		System.out.println(response.getBody().asPrettyString());
		String value = jsonPathEval.getString("parts[1].body");
		System.out.println("body is " + value);
		String startDate = DateUtils.formatDate(futureDate);
		Assert.assertTrue(value.contains(eventName));
		Assert.assertTrue(value.contains(startDate));
		Assert.assertTrue(value.contains(dataList.get(0)));
	}
	
	/**
	 * Validating email after transferring membership request
	 * @param msg 
	 * @throws InterruptedException 
	 */
	public void validateEmailAfterTranserMemRequest(String emailPrefix, String msg) throws InterruptedException {
		String inbox = emailPrefix;
		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(7000);
		 Response response =  RestAssured.given().headers("Content-Type",
				ContentType.JSON, "Accept",
				ContentType.JSON,"Authorization",
				bearerToken).
				when().
				get(mailinator_uri).
				then().
				extract().response();
		System.out.println(response.getBody().asPrettyString());
		jsonPathEval = response.jsonPath();
		String messageId = jsonPathEval.getString("msgs[0].id");
		System.out.println("Message Id is "+messageId);
		String message_uri = MAILINATOR_INBOS_ENDPOINT + inbox + "/messages/" + messageId ;
		 response =  RestAssured.given().
				 headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON,"Authorization",bearerToken).when().get(message_uri).then().extract().response();
		jsonPathEval = response.jsonPath();
		Thread.sleep(5000);
		System.out.println(response.getBody().asPrettyString());
		String thankYouMsg = jsonPathEval.getString("parts[0].body");
		assertTrue(thankYouMsg.contains(msg));
		
	}
	
	/**
	 * @author sghodake
	 * @param emailPrefix
	 * @param emailSub 
	 * @throws InterruptedException 
	 */
	public void validatePaymentInfoInMail(String emailPrefix, String emailSub) throws InterruptedException {
		String inbox = emailPrefix;

		JsonPath jsonPathEval = null;
		String mailinator_uri = MAILINATOR_API + inbox;
		Thread.sleep(10000);

		Response response = RestAssured.given()
				.headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON, "Authorization", bearerToken)
				.when().get(mailinator_uri).then().extract().response();
		System.out.println(response.getBody().asPrettyString());

		jsonPathEval = response.jsonPath();
		String messageSub = jsonPathEval.getString("msgs[0].subject");
		System.out.println("Message subject is+ " + messageSub);
		assertTrue(messageSub.contains(emailSub));
	}
	
}
