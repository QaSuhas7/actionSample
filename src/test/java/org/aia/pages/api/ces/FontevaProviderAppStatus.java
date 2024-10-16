package org.aia.pages.api.ces;

import static io.restassured.RestAssured.given;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FontevaProviderAppStatus {
	WebDriver driver;

	public FontevaProviderAppStatus(WebDriver driver) {
		super();
		this.driver = driver;
	}

	Utility util = new Utility(driver, 10);

	String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uriUpgradeStg");
	String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	String SOBJECT_URI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
	String RECCIPT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	static String sObjectCompositeURI = DataProviderFactory.getConfig().getValue("sObjectURI_uriUpgradeStg");
	int totalMembershipCount = 0;
	public boolean providerStat = false;
	JsonPath jsonPathEval = null;
	int retryCount = 0;
	public static String accountID = null;
	private static String providerID = null;
	private static String receiptid = null;
	int attempt = 0;

	static FontevaConnection bt = new FontevaConnection();
	// private static final String bearerToken =
	// DataProviderFactory.getConfig().getValue("access_token");//bt.getbearerToken();;
	private static final String bearerToken = bt.getbearerToken();

	/**
	 * Here we are fetching account ID using api get call
	 * 
	 * @param memberAccount
	 * @return
	 */
	public String providerId(String memberAccount) {
		providerStat = false;
		// From this api we get the provider id
		while (retryCount < 10 && providerID == null) {
			Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount)
					.param("sobject", "Provider_Application__c").when().get(PARAMETERIZED_SEARCH_URI).then()
					.statusCode(200).extract().response();

			jsonPathEval = response.jsonPath();
			providerID = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("ProviderId  ID:" + providerID);
			retryCount = retryCount + 1;
			System.out.println("My retry count:" + retryCount);
			if (providerID != null) {
				break;
			}

		}
		return providerID;

	}

	public void changeProviderApplicationStatus(String memberAccount, String status) throws InterruptedException {
		while (retryCount < 15) {
			String providerId = providerId(memberAccount);
			Response response = null;
			attempt++;
			String requestBody = "{\r\n" + "    \"allOrNone\": false,\r\n" + "    \"records\": [\r\n" + "        {\r\n"
					+ "            \"attributes\": {\r\n" + "                \"type\": \"Provider_Application__c\"\r\n"
					+ "            },\r\n" + "            \"id\": \"" + providerId + "\",\r\n"
					+ "            \"Application_Stage__c\": \""+status+"\"\r\n" + "        }\r\n" + "    ]\r\n" + "}";

			try {
				response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
						.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
						.header("Accept", ContentType.JSON).body(requestBody).when().patch(sObjectCompositeURI).then().extract()
						.response();
				System.out.println(response.jsonPath().prettyPrint());
				if (response.statusCode() == 200) {
					break;
				}
			} catch (Exception e) {
				System.out.println("Request failed. Attempt: " + attempt);
				if (attempt >= 2) {
					throw e;
				}
			}
			Thread.sleep(2000); // Wait before retrying
		}

	}

}
