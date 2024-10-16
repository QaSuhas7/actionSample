package org.aia.pages.api.ces;

import static io.restassured.RestAssured.given;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.awaitility.core.Condition;
import org.checkerframework.common.value.qual.StaticallyExecutable;
import org.glassfish.jersey.message.internal.StringHeaderProvider;
import org.openqa.selenium.WebDriver;


import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import org.awaitility.Awaitility;
import org.awaitility.Awaitility.*;
import java.util.concurrent.TimeUnit.*;
import org.hamcrest.Matchers.*;
import org.junit.Assert.*;

public class FontevaCESTermDateChangeAPI {
	WebDriver driver;

	public FontevaCESTermDateChangeAPI(WebDriver driver) {
		this.driver = driver;
	}

	Utility util = new Utility(driver, 10);

	static String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig()
			.getValue("parameterizedSearch_uriUpgradeStg");
	static String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	static String SOBJECT_URI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
	static String sObjectCompositeURI = DataProviderFactory.getConfig().getValue("sObjectURI_uriUpgradeStg");
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();
	static JsonPath jsonPathEval = null;
	private static String accountID = null;
	private static String providerId = null;
	private static String membershipId = null;
	static int retryCount = 0;
	private boolean providerStat = false;

	public void changeTermDateAPI(String memberAccount, String termDate, String provideMembershipType) throws InterruptedException {
		// From this api we get the provider id
		// membershipIndex = null;
		providerStat = false;
		while (retryCount < 10 && providerStat == false) {
			Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount)
					.param("sobject", "Provider_Application__c").when().get(PARAMETERIZED_SEARCH_URI).then()
					.statusCode(200).extract().response();

			jsonPathEval = response.jsonPath();
			System.out.println(jsonPathEval);
			providerId = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("ProviderId  ID:" + providerId);

			// From this api call we get account id using provider id
            if(providerId != null) {
			String providerUri = SOBJECT_URI + "/Provider_Application__c/" + providerId;
			System.out.println("ProviderUrl:" + providerUri);
			response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).when().get(providerUri).then().statusCode(200).extract()
					.response();
			jsonPathEval = response.jsonPath();
			accountID = jsonPathEval.getString("Account__c");
			System.out.println("Account ID:" + accountID);

			// From this API we try to get membership ID
			String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";
			Thread.sleep(10000);

//		Awaitility.await().atMost(60, TimeUnit.SECONDS).pollInterval(1, TimeUnit.SECONDS).until(() ->
//		{
			response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).when().get(SUBSCRIPTIONS_URI);
			response.then().statusCode(200).extract().response();
			Thread.sleep(10000);
			jsonPathEval = response.jsonPath();
			int respSize = jsonPathEval.getInt("totalSize");
			System.out.println("My preety resp:" + jsonPathEval.prettyPrint());
			for (int i = 0; i <= respSize; i++) {
				if (jsonPathEval.getString("records[" + i + "].Membership_Type__c")
						.contains(provideMembershipType)) {
					System.out.println(jsonPathEval.getString("records[" + i + "].Membership_Type__c"));
					membershipId = jsonPathEval.getString("records[" + i + "].Id");
					System.out.println("Membership ID:" + membershipId);
					break;
				}
			}

			if (membershipId != null) {

				// From this call we are getting the termID
				String selectTermURI = SOBJECT_URI + "/OrderApi__Subscription__c/" + membershipId
						+ "/OrderApi__Renewals__r";
				response = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when()
						.get(selectTermURI);
				// util.waitForResponse(response,200);
				Thread.sleep(10000);
				response.then().statusCode(200).extract().response();
				jsonPathEval = response.jsonPath();
				String termId = jsonPathEval.getString("records[0].Id");
				System.out.println("termId ID:" + termId);
				// Here we change the termend date using termID
				response = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when()
						.body("{\r\n" + "    \"allOrNone\": false,\r\n" + "    \"records\": [\r\n" + "        {\r\n"
								+ "            \"attributes\": {\r\n"
								+ "                \"type\": \"OrderApi__Renewal__c\"\r\n" + "            },\r\n"
								+ "             \"id\": \"" + termId + "\",\r\n"
								+ "            \"OrderApi__Term_End_Date__c\": \"" + termDate + "\"\r\n"
								+ "        }\r\n" + "    ]\r\n" + "}")
						.patch(sObjectCompositeURI).then().statusCode(200).extract().response();
				providerStat = true;
			}

			if (membershipId != null) {
				break;
			}
			providerStat = true;
            }else {
				retryCount = retryCount + 1;
				System.out.println("My retry count:" + retryCount);
			}
			
		}
	}

	/**
	 * @param expectedAccountStatus
	 * @param expectedMembershipType Here we getting accountStatus and
	 *                               membershipType using accountID
	 * @throws InterruptedException
	 */
	public void getCESAccountDetails(String expectedAccountStatus, String expectedMembershipType)
			throws InterruptedException {
		String AccountDetailsURI = ACCOUNT_URI + "/" + accountID;
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when()
				.get(AccountDetailsURI).then().statusCode(200).extract().response();
		jsonPathEval = response.jsonPath();
		String cesAccountStatus = jsonPathEval.getString("AIA_CES_Provider_Status__c");
		String cesMembershipType = jsonPathEval.getString("Membership_Type__c");
		// Boolean providerRenewEligible =
		// jsonPathEval.getBoolean("CES_Provider_Renew_Eligible__c");
		assertEquals(cesAccountStatus, expectedAccountStatus);
		assertEquals(cesMembershipType, expectedMembershipType);
		Thread.sleep(3000);
	}

	/**
	 * Here we validate actual membership status.
	 */
	public void validateCESMembershipCreated(String membershipStatus, String membershipData) {
		String selectTermURI = SOBJECT_URI + "/OrderApi__Subscription__c/" + membershipId + "/OrderApi__Renewals__r";
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(selectTermURI)
				.then().statusCode(200).extract().response();
		jsonPathEval = response.jsonPath();
		String actualmembershipStatus = jsonPathEval.getString("records[1]." + membershipData + "");
		assertEquals(membershipStatus, actualmembershipStatus);

	}

	public void validateisproviderRenewEligible(Boolean expectedisproviderRenewEligible) {
		String AccountDetailsURI = ACCOUNT_URI + "/" + accountID;
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when()
				.get(AccountDetailsURI).then().statusCode(200).extract().response();
		jsonPathEval = response.jsonPath();
		Boolean providerRenewEligible = jsonPathEval.getBoolean("CES_Provider_Renew_Eligible__c");
		assertEquals(providerRenewEligible, expectedisproviderRenewEligible);

	}
	
	/**
	 * This method is created for getting account ID of the ces account.
	 * 
	 * @param memberAccount
	 */
	public void getAccountId(String memberAccount) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).param("q", memberAccount)
				.param("sobject", "Provider_Application__c").when().get(PARAMETERIZED_SEARCH_URI);
		util.waitForResponse(response, 200);
		response.then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		providerId = jsonPathEval.getString("searchRecords[0].Id");
		System.out.println("ProviderId  ID:" + providerId);

		// From this api call we get account id using provider id
		String providerUri = SOBJECT_URI + "/Provider_Application__c/" + providerId;
		System.out.println("ProviderUrl:" + providerUri);
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(providerUri);
		util.waitForResponse(response, 200);
		response.then().statusCode(200).extract().response();
		jsonPathEval = response.jsonPath();
		accountID = jsonPathEval.getString("Account__c");
		System.out.println("Account ID:" + accountID);
	}

}