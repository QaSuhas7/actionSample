package org.aia.pages.api.memberPortal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.aia.pages.api.ces.FontevaConnection;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.DateUtils;
import org.aia.utility.Utility;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.*;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class MemberPortalAPI {
	WebDriver driver;

	public MemberPortalAPI(WebDriver Idriver) {
		this.driver = Idriver;
	}

	Utility util = new Utility(driver, 10);


		String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uriUpgradeStg");
		String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
		String SOBJECT_URI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
		String RECCIPT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
		int totalMembershipCount = 0;
		JsonPath jsonPathEval = null;
		int retryCount = 0;
		private static String accountID = null;
		static FontevaConnection bt = new FontevaConnection();
		// private static final String bearerToken =
		// DataProviderFactory.getConfig().getValue("access_token");//bt.getbearerToken();;
		private static final String bearerToken = bt.getbearerToken();
		public void verifyContactInformation(String memberAccount, String enddate, Object dues, String type,
				String MembershipTypeAssigned, String CareerType, ArrayList<String> contactInfoList) throws InterruptedException {
			Response response = null;
			Response response_contact = null;
		    boolean	 responseStatus = false;
		    totalMembershipCount = 0;
			
		// GET Account ID
		while ((totalMembershipCount == 0) && retryCount < 10 && responseStatus == false) {
			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI);
			util.waitForResponse(response, 200);
			response.then().statusCode(200).extract().response();
			Thread.sleep(7000);
			jsonPathEval = response.jsonPath();
			Thread.sleep(10000);
			accountID = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("Account ID:" + accountID);
			if ((totalMembershipCount == 0) && accountID != null) {
				// Use Account ID to fetch account details.
				String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";
				Thread.sleep(10000);
				response = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
						.param("fields", "OrderApi__Term_Start_Date__c," + "OrderApi__Term_End_Date__c,"
								+ "OrderApi__Term_Dues_Total__c," + "Membership_Type__c," + "OrderApi__Status__c,"
								+ "OrderApi__Activated_Date__c," + "OrderApi__Paid_Through_Date__c,"
								+ "OrderApi__Days_To_Lapse__c," + "OrderApi__Item__c, " + "OrderApi__Contact__c")
						.when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract().response();

				jsonPathEval = response.jsonPath();

				totalMembershipCount = jsonPathEval.getInt("totalSize");
				Thread.sleep(10000);
			}if (totalMembershipCount > 0 && responseStatus == false) {
				System.out.println("jgdh");
				System.out.println("Number of Memberships : " + totalMembershipCount);
				String contactID = jsonPathEval.getString("records[0].OrderApi__Contact__c");
				System.out.println("=====================================");
				// AIA_Career_Type__c
				String contact_URI = SOBJECT_URI + "/Contact/" + contactID;
				
				response_contact = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(contact_URI)
						.then().statusCode(200).extract().response();
				responseStatus = util.containsExpectedValue(response_contact, "Email", contactInfoList.get(3));
				if(responseStatus == true) {
				jsonPathEval = response_contact.jsonPath();
				Thread.sleep(10000);
				String firstName = jsonPathEval.getString("FirstName");
				System.out.println(firstName);
				Assert.assertEquals(firstName, contactInfoList.get(0));
				String lastName = jsonPathEval.getString("LastName");
				System.out.println(lastName);
				Assert.assertEquals(lastName, contactInfoList.get(1));
				String email = jsonPathEval.getString("Email");
				System.out.println(email);
				Assert.assertEquals(email, contactInfoList.get(3));
				System.out.println("Validations are passed");
				}else {
					totalMembershipCount = 0;
				}
			}
			else {
				System.out.println("No active memberships found!!!");
			}

			retryCount = retryCount + 1;
		}

		// Verify if totalMembershipCount is 1 , then account creation was success.
			}

	public void verifyMemberShipInformation(String memberAccount, String enddate, Object dues, String type,
			String MembershipTypeAssigned, String CareerType, ArrayList<String> memberShipInfo)
			throws InterruptedException {
		Response response = null;
		boolean responseStatus = false;
		// GET Account ID
		while ((totalMembershipCount == 0) && retryCount < 10 && responseStatus == false) {
			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI);
			util.waitForResponse(response, 200);
			response.then().statusCode(200).extract().response();
			Thread.sleep(7000);
			jsonPathEval = response.jsonPath();
			Thread.sleep(10000);
			accountID = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("Account ID:" + accountID);
			if ((totalMembershipCount == 0) && accountID != null) {
				// Use Account ID to fetch account details.
				String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";

				response = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
						.param("fields", "OrderApi__Term_Start_Date__c," + "OrderApi__Term_End_Date__c,"
								+ "OrderApi__Term_Dues_Total__c," + "Membership_Type__c," + "OrderApi__Status__c,"
								+ "OrderApi__Activated_Date__c," + "OrderApi__Paid_Through_Date__c,"
								+ "OrderApi__Days_To_Lapse__c," + "OrderApi__Item__c, " + "OrderApi__Contact__c")
						.when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract().response();

				jsonPathEval = response.jsonPath();
				Thread.sleep(10000);
				totalMembershipCount = jsonPathEval.getInt("totalSize");
				Thread.sleep(10000);
			}
			if (totalMembershipCount > 0 && responseStatus == false) {
				System.out.println("Number of Memberships : " + totalMembershipCount);
				String contactID = jsonPathEval.getString("records[0].OrderApi__Contact__c");
				System.out.println("=====================================");
				// AIA_Career_Type__c
				String contact_URI = SOBJECT_URI + "/Contact/" + contactID;
				Response response_contact = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(contact_URI)
						.then().statusCode(200).extract().response();
				responseStatus = util.containsExpectedValue(response_contact, "AIA_Official_State_Assignment__c", memberShipInfo.get(1));
				if(responseStatus == true) {
				jsonPathEval = response_contact.jsonPath();
				Thread.sleep(10000);
				String aiaNumber = jsonPathEval.getString("AIA_Number__c");
				System.out.println(aiaNumber);
				Assert.assertEquals(aiaNumber, memberShipInfo.get(0));
				String memberShipType = jsonPathEval.getString("AIA_Membership_Type__c");
				System.out.println(memberShipType);
				Assert.assertEquals(memberShipType, memberShipInfo.get(1));
				String stateAssignment = jsonPathEval.getString("AIA_Official_State_Assignment__c");
				System.out.println(stateAssignment);
				Assert.assertEquals(stateAssignment, memberShipInfo.get(2));
				String sectionAssignment = jsonPathEval.getString("AIA_Official_Section_Assignment__c");
				System.out.println(sectionAssignment);
				Assert.assertEquals(sectionAssignment, memberShipInfo.get(3));
				String status = jsonPathEval.getString("AIA_FF_Status__c");
				System.out.println(status);
				Assert.assertEquals(status, memberShipInfo.get(4));
				}else {
					totalMembershipCount = 0;
				}
			}

			else {
				System.out.println("No active memberships found!!!");
			}

			retryCount = retryCount + 1;
		}

		// Verify if totalMembershipCount is 1 , then account creation was success.
		
	}

	public void verifyDemographicsInformation(String memberAccount, String enddate, Object dues, String type,
			String MembershipTypeAssigned, String CareerType, ArrayList<String> demoGraphicsData)
			throws InterruptedException {
		Response response = null;
		// GET Account ID
		while ((totalMembershipCount == 0) && retryCount < 10) {
			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI);
			util.waitForResponse(response, 200);
			response.then().statusCode(200).extract().response();
			Thread.sleep(7000);
			jsonPathEval = response.jsonPath();
			Thread.sleep(10000);
			accountID = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("Account ID:" + accountID);
			if ((totalMembershipCount == 0) && accountID != null) {
				// Use Account ID to fetch account details.
				String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";

				response = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
						.param("fields", "OrderApi__Term_Start_Date__c," + "OrderApi__Term_End_Date__c,"
								+ "OrderApi__Term_Dues_Total__c," + "Membership_Type__c," + "OrderApi__Status__c,"
								+ "OrderApi__Activated_Date__c," + "OrderApi__Paid_Through_Date__c,"
								+ "OrderApi__Days_To_Lapse__c," + "OrderApi__Item__c, " + "OrderApi__Contact__c")
						.when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract().response();

				jsonPathEval = response.jsonPath();
				Thread.sleep(10000);
				totalMembershipCount = jsonPathEval.getInt("totalSize");
			}

			retryCount = retryCount + 1;
		}

		// Verify if totalMembershipCount is 1 , then account creation was success.
		if (totalMembershipCount > 0) {
			System.out.println("Number of Memberships : " + totalMembershipCount);
			String contactID = jsonPathEval.getString("records[0].OrderApi__Contact__c");
			System.out.println("=====================================");
			// AIA_Career_Type__c
			String contact_URI = SOBJECT_URI + "/Contact/" + contactID;
			Response response_contact = given().header("Authorization", "Bearer " + bearerToken)
					.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(contact_URI)
					.then().statusCode(200).extract().response();
			jsonPathEval = response_contact.jsonPath();
			Thread.sleep(10000);
			String birthDate = jsonPathEval.getString("Birthdate");
			System.out.println(birthDate);
			Assert.assertEquals(birthDate, demoGraphicsData.get(0));
			String genderIdentity = jsonPathEval.getString("AIA_Gender__c");
			System.out.println(genderIdentity);
			Assert.assertEquals(genderIdentity, demoGraphicsData.get(1));
			String gender = jsonPathEval.getString("AIA_Other_Gender__c");
			System.out.println(gender);
			Assert.assertEquals(gender, demoGraphicsData.get(2));
			String preferedPronouns = jsonPathEval.getString("AIA_Preferred_Pronouns__c");
			System.out.println(preferedPronouns);
			Assert.assertEquals(preferedPronouns, demoGraphicsData.get(3));
			String primaryRaceEthnicity = jsonPathEval.getString("AIA_Primary_race_ethnicity__c");
			System.out.println(primaryRaceEthnicity);
			Assert.assertEquals(primaryRaceEthnicity, demoGraphicsData.get(4));
			String secondaryRaceEthnicity = jsonPathEval.getString("AIA_Secondary_race_ethnicity__c");
			System.out.println(secondaryRaceEthnicity);
			Assert.assertEquals(secondaryRaceEthnicity, demoGraphicsData.get(5));
			String diverseAbility = jsonPathEval.getString("AIA_Diverse_Ability__c");
			System.out.println(diverseAbility);
			Assert.assertEquals(diverseAbility, demoGraphicsData.get(6));
			String abilityNotes = jsonPathEval.getString("AIA_Diverse_Ability_Other__c");
			System.out.println(abilityNotes);
			Assert.assertEquals(abilityNotes, demoGraphicsData.get(7));
		}

		else {
			System.out.println("No active memberships found!!!");
		}
	}

}
