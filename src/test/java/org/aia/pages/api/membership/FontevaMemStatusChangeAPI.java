package org.aia.pages.api.membership;

import static io.restassured.RestAssured.given;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

/**
 * @author sghodake
 *
 */
public class FontevaMemStatusChangeAPI {
	WebDriver driver;

	public FontevaMemStatusChangeAPI(WebDriver driver) {
		this.driver = driver;
	}

	Utility util = new Utility(driver, 10);

	static String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig()
			.getValue("parameterizedSearch_uriUpgradeStg");
	static String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	static String sObjectURI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
	static String sObjectCompositeURI = DataProviderFactory.getConfig().getValue("sObjectURI_uriUpgradeStg");
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();
	JsonPath jsonPathEval = null;  // No need for static
    public String contactId = null;  // Remove static
    public String membershipId = null;  // Remove static
    public String termId = null;
	int retryCount = 0;
	int attempt = 0;

	/**
	 * Here we are changing status & membership expire date
	 * 
	 * @param memberAccount
	 * @param expireDate
	 * @throws Exception
	 */
	public void changeMembershipStatusExpDate(String memberAccount, String expireDate) throws Exception {
		Response response = null;
		while (contactId == null && retryCount < 3) {
			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Contact").when()
					.get(PARAMETERIZED_SEARCH_URI);
			 jsonPathEval = response.jsonPath();
			 contactId = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("Account ID:" + contactId);
			if (contactId == null) {
				System.out.println("Contact ID not found, retrying... Attempt: " + (retryCount + 1));

			} else {
				System.out.println("Contact ID found: " + contactId);

			}

			String membershipSubUri = sObjectURI + "/Contact/" + contactId + "/OrderApi__Subscriptions__r";
			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).when().get(membershipSubUri).then().extract().response();
			jsonPathEval = response.jsonPath();
			membershipId = jsonPathEval.getString("records[0].Id");
			System.out.println("My mem id:" + membershipId);
			if (membershipId == null) {
				System.out.println("Membership ID not found, retrying... Attempt: " + (retryCount + 1));
			} else {
				System.out.println("Membership ID found: " + membershipId);
			}
			
			String termUri = sObjectURI +"/OrderApi__Subscription__c/"+membershipId+"/OrderApi__Renewals__r";
			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).when().get(termUri).then().extract().response();
			jsonPathEval = response.jsonPath();
			termId = jsonPathEval.getString("records[0].Id");
			System.out.println("My mem term id:" + termId);
			if (termId == null) {
				System.out.println("Membership term ID not found, retrying... Attempt: " + (retryCount + 1));
			} else {
				System.out.println("Membership term ID found: " + termId);
			}

			String compositUri = sObjectCompositeURI;

			System.out.println("Attemts:" + attempt);
			String reqBody = "{\r\n"
					+ "    \"allOrNone\": false,\r\n"
					+ "    \"records\": [\r\n"
					+ "        {\r\n"
					+ "            \"attributes\": {\r\n"
					+ "                \"type\": \"Contact\"\r\n"
					+ "            },\r\n"
					+ "            \"id\": \""+contactId+"\",\r\n"
					+ "            \"AIA_Status__c\": \"Terminated\"\r\n"
					+ "        },\r\n"
					+ "        {\r\n"
					+ "            \"attributes\": {\r\n"
					+ "                \"type\": \"OrderApi__Subscription__c\"\r\n"
					+ "            },\r\n"
					+ "            \"id\": \""+membershipId+"\",\r\n"
					+ "            \"OrderApi__Status__c\": \"Cancelled\",\r\n"
					+ "            \"OrderApi__Is_Cancelled__c\": \"true\",\r\n"
					+ "            \"OrderApi__Activated_Date__c\": \"2021-10-14\",\r\n"
					+ "            \"OrderApi__Paid_Through_Date__c\": \"2021-12-31\",\r\n"
					+ "            \"OrderApi__Grace_Period_End_Date__c\": \"2022-04-04\",\r\n"
					+ "            \"OrderApi__Expired_Date__c\": \""+expireDate+"\"\r\n"
					+ "        },\r\n"
					+ "        {\r\n"
					+ "            \"attributes\": {\r\n"
					+ "                \"type\": \"OrderApi__Renewal__c\"\r\n"
					+ "            },\r\n"
					+ "            \"id\": \""+termId+"\",\r\n"
					+ "            \"OrderApi__Term_End_Date__c\": \"2021-12-31\",\r\n"
					+ "            \"OrderApi__Term_Start_Date__c\": \"2021-01-01\",\r\n"
					+ "            \"OrderApi__Grace_Period_End_Date__c\": \"2022-04-04\",\r\n"
					+ "            \"OrderApi__Renewed_Date__c\": \"2021-10-14\"\r\n"
					+ "        }\r\n"
					+ "    ]\r\n"
					+ "}";

			response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).body(reqBody).when().patch(compositUri).then().extract()
					.response();
			System.out.println(response.jsonPath().prettyPrint());

			System.out.println("Request failed. Attempt: " + attempt);
			if (response.getStatusCode() != 200) {
				System.out.println("Succesfully Changed the status");
			} else {
				System.out.println("Retrying........"+retryCount);
			}
			
			retryCount = retryCount + 1;
		}

	}
}
