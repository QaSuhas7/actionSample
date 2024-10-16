package org.aia.pages.api.membership;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class UpgradeMembershipAPIValidation {
	static WebDriver driver;

	public UpgradeMembershipAPIValidation(WebDriver Idriver) {
		this.driver = Idriver;
	}

	Utility util = new Utility(driver, 10);

	String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uriUpgradeStg");
	String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	String SOBJECT_URI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
	String RECCIPT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	JsonPath jsonPathEval = null;
	JsonPath jsonPath = null;
	int retryCount = 0;
	int totalMembershipCount = 0;
	private static String accountID = null;

	static FontevaConnection bt = new FontevaConnection();
	// private static final String bearerToken =
	// DataProviderFactory.getConfig().getValue("access_token");//bt.getbearerToken();;
	private static final String bearerToken = bt.getbearerToken();

	public void verifyMemebershipCreation(String memberAccount, String enddate, String type,
			String MembershipTypeAssigned, String CareerType) throws InterruptedException {
		Response response = null;
		// GET Account ID
		while ((totalMembershipCount == 0) && retryCount < 10) {
			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI);
			response.then().extract().response();
			Thread.sleep(7000);
			jsonPathEval = response.jsonPath();
			System.out.println("id Resp" + jsonPathEval.prettyPrint());
			Thread.sleep(10000);
			accountID = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("Account ID:" + accountID);
			// Use Account ID to fetch account details.
			if ((totalMembershipCount == 0) && accountID != null) {
				String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";

				response = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
						.param("fields", "OrderApi__Term_Start_Date__c," + "OrderApi__Term_End_Date__c,"
								+ "OrderApi__Term_Dues_Total__c," + "Membership_Type__c," + "OrderApi__Status__c,"
								+ "OrderApi__Activated_Date__c," + "OrderApi__Paid_Through_Date__c,"
								+ "OrderApi__Days_To_Lapse__c," + "OrderApi__Item__c, " + "OrderApi__Contact__c")
						.when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract().response();

				jsonPathEval = response.jsonPath();
				System.out.println(jsonPathEval.prettyPrint());
				totalMembershipCount = jsonPathEval.getInt("totalSize");
				Thread.sleep(10000);
			} else {
				totalMembershipCount = 0;
			}
			// Verify if totalMembershipCount is 1 , then account creation was success.
			if (totalMembershipCount > 0
					&& util.containsExpectedValue(response, "records[0].OrderApi__Status__c", "Active")) {
				System.out.println("Number of Memberships : " + totalMembershipCount);
				String termStartDate = jsonPathEval.getString("records[0].OrderApi__Term_Start_Date__c");
				String termEndDate = jsonPathEval.getString("records[0].OrderApi__Term_End_Date__c");
				String activatedDate = jsonPathEval.getString("records[0].OrderApi__Activated_Date__c");
				String paidThroughDate = jsonPathEval.getString("records[0].OrderApi__Paid_Through_Date__c");
				Object lapseDays = jsonPathEval.getDouble("records[0].OrderApi__Days_To_Lapse__c");

				Double termDues = jsonPathEval.getDouble("records[0].OrderApi__Term_Dues_Total__c");
				String membershipType = jsonPathEval.getString("records[0].Membership_Type__c");
				String membershipStatus = jsonPathEval.getString("records[0].OrderApi__Status__c");
				String membershipItemID = jsonPathEval.getString("records[0].OrderApi__Item__c");
				String contactID = jsonPathEval.getString("records[0].OrderApi__Contact__c");

				System.out.println("=====================================");
				System.out.println("Membership type :" + membershipType);
				System.out.println("Membership start date :" + termStartDate);
				System.out.println("Membership end date :" + termEndDate);
				System.out.println("Membership term dues :" + termDues);
				System.out.println("=====================================");

				assertEquals(membershipStatus, "Active");
				assertEquals(membershipType, type);
				SimpleDateFormat myFormat = new SimpleDateFormat("yyyy-MM-dd");
				String inputString1 = activatedDate;
				String inputString2 = paidThroughDate;
				Object days = null;
				try {
					Date date1 = myFormat.parse(inputString1);
					Date date2 = myFormat.parse(inputString2);
					long diff = date2.getTime() - date1.getTime();
					days = Double.valueOf(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
					System.out.println("Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				assertEquals(lapseDays, days);

				// AIA_Member_Type_Assignment
				String Member_Type_Assignment_URI = SOBJECT_URI + "/OrderApi__Item__c/" + membershipItemID;
				Response response2 = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
						.param("fields", "AIA_Member_Type_Assignment__c").when().get(Member_Type_Assignment_URI).then()
						.statusCode(200).extract().response();

				jsonPathEval = response2.jsonPath();
				Thread.sleep(10000);
				String AIA_Member_Type = jsonPathEval.getString("AIA_Member_Type_Assignment__c");
				System.out.println("AIA_Member_Type_Assignment : " + AIA_Member_Type);
				assertEquals(AIA_Member_Type, MembershipTypeAssigned);

				// AIA_Career_Type__c
				String contact_URI = SOBJECT_URI + "/Contact/" + contactID;
				Response response_contact = given().header("Authorization", "Bearer " + bearerToken)
						.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON)
						.param("fields", "AIA_Career_Type__c").when().get(contact_URI).then().statusCode(200).extract()
						.response();

				jsonPathEval = response_contact.jsonPath();
				Thread.sleep(10000);
				String AIA_Career_Type = jsonPathEval.getString("AIA_Career_Type__c");
				System.out.println("AIA_Career_Type : " + AIA_Career_Type);
				assertEquals(AIA_Career_Type, CareerType);
			}

			else {
				totalMembershipCount = 0;
				System.out.println("No active memberships found!!!");
			}
			retryCount = retryCount + 1;
		}
	}
	
	public void verifyMemebershipCreation(String memberAccount, String membershipCancelledreson, String currentActiveMem, String termEndDate)
			throws InterruptedException {
		// GET Account ID
			Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();

			jsonPathEval = response.jsonPath();
			accountID = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("Account ID:" + accountID);
			// Use Account ID to fetch account details.
			String SUBSCRIPTIONS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Subscriptions__r";
			response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).when().get(SUBSCRIPTIONS_URI).then().statusCode(200).extract()
					.response();
			Thread.sleep(30000);
			jsonPath = response.jsonPath();
			Thread.sleep(10000);
			retryCount = retryCount + 1;
			verifyActiveMembership(currentActiveMem,termEndDate);
			verifyCanclledMembership(membershipCancelledreson);
			
		}

	public void verifyCanclledMembership(String membershipCancelledreson) {
		String cancelledMemId = jsonPath.getString("records[0].Id");
		String membershipUri = SOBJECT_URI + "/OrderApi__Subscription__c/" + cancelledMemId;
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(membershipUri)
				.then().statusCode(200).extract().response();
		jsonPathEval = response.jsonPath();
		boolean isMembershipCancelled = jsonPathEval.getBoolean("OrderApi__Is_Cancelled__c");
		String membershipCancelledDate = jsonPathEval.getString("OrderApi__Cancelled_Date__c");
		String membershipCancelledReason = jsonPathEval.getString("OrderApi__Cancelled_Reason__c");
		String membershipTypeId = jsonPathEval.getString("OrderApi__Item__c");
		// Get MembershipType
		String membershipTyepeUri = SOBJECT_URI + "/OrderApi__Item__c/" + membershipTypeId;
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(membershipTyepeUri).then().statusCode(200).extract()
				.response();
		jsonPathEval = response.jsonPath();
		String membershipName = jsonPathEval.getString("Name");
		assertEquals("Associate - National", membershipName);
		assertTrue(isMembershipCancelled);
//		assertEquals(membershipCancelledDate, java.time.LocalDate.now().toString());
		assertEquals(membershipCancelledReason, membershipCancelledreson);
	}

	public void verifyActiveMembership(String currentActiveMem, String termEndDate) {
		String activeMemId = jsonPath.getString("records[1].Id");
		String membershipUri = SOBJECT_URI + "/OrderApi__Subscription__c/" + activeMemId;
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(membershipUri)
				.then().statusCode(200).extract().response();
		jsonPathEval = response.jsonPath();
		String activeTermstartDate=jsonPathEval.getString("OrderApi__Term_Start_Date__c");
		String activeTermEndDate=jsonPathEval.getString("OrderApi__Term_End_Date__c");
		String membershipTypeId = jsonPathEval.getString("OrderApi__Item__c");
		// Get MembershipType
		String membershipTyepeUri = SOBJECT_URI + "/OrderApi__Item__c/" + membershipTypeId;
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(membershipTyepeUri).then().statusCode(200).extract()
				.response();
		jsonPathEval = response.jsonPath();
		String membershipName = jsonPathEval.getString("Name");
		assertEquals(membershipName,currentActiveMem);
//		assertEquals(activeTermstartDate, java.time.LocalDate.now().toString());
		assertEquals(activeTermEndDate, "2025-12-31");
		
	}
	
}
