package org.aia.pages.api.ces;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.*;

import java.util.ArrayList;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class usBankFontevaValidationAPI {
	WebDriver driver;

	public usBankFontevaValidationAPI(WebDriver driver) {
		this.driver = driver;
	}

	Utility util = new Utility(driver, 10);
	static String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig()
			.getValue("parameterizedSearch_uriUpgradeStg");
	static String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uri");
	static String sObjectURI = DataProviderFactory.getConfig().getValue("sobject_uri");
	static String sObjectCompositeURI = DataProviderFactory.getConfig().getValue("sObjectURI");
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();
	static JsonPath jsonPathEval = null;
	private static String accountId = null;
	static int retryCount = 0;

	/**
	 * @param memberAccount
	 */
	public void getAccountID(String memberAccount) {
		while ((accountId == null) && retryCount < 10) {
			Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
					.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
					.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();
			jsonPathEval = response.jsonPath();
			System.out.println(jsonPathEval.prettyPrint());
			accountId = jsonPathEval.getString("searchRecords[0].Id");
			System.out.println("Account Id:" + accountId);
			retryCount = retryCount + 1;
		}

	}

	public void validateEpayment(String memberAccount, String transactionTypes, String currency,
			String paymentMsg, String amount, String paymentStatus) {
		getAccountID(memberAccount);
		String SALESORDER_URI = "https://aia--upgradestg.sandbox.my.salesforce.com/services/data/v56.0/sobjects/Account"
				+ "/" + accountId + "/OrderApi__Sales_Orders__r";
		System.out.println(SALESORDER_URI);
		Response response = given().header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when().get(SALESORDER_URI)
				.then().statusCode(200).extract().response();
		jsonPathEval = response.jsonPath();
		System.out.println("Salesorder" + jsonPathEval.prettyPrint());
		int totalSalesOrderSize = jsonPathEval.getInt("totalSize");
		ArrayList<String> salesordersId = new ArrayList<String>();
		if (totalSalesOrderSize > 1) {
			for (int i = 0; i < totalSalesOrderSize; i++) {
				salesordersId.add(i, jsonPathEval.getString("records[" + i + "].Id"));
				String SalesOrderStatus = jsonPathEval.getString("records[" + i + "].OrderApi__Status__c");
				if (SalesOrderStatus != "Cancelled" && salesordersId.size() > 1) {
					String epaymentUri = "https://aia--upgradestg.sandbox.my.salesforce.com/services/data/v56.0/sobjects/OrderApi__Sales_Order__c"
							+ "/" + salesordersId.get(i) + "/OrderApi__Payment_Activities__r";
					System.out.println(epaymentUri);
					response = given().header("Authorization", "Bearer " + bearerToken)
							.header("Content-Type", ContentType.JSON).header("Accept", ContentType.JSON).when()
							.get(epaymentUri).then().statusCode(200).extract().response();
					jsonPathEval = response.jsonPath();
					System.out.println("After epayment" + jsonPathEval.prettyPrint());
//					int totalSize = jsonPathEval.get("totalSize");
					String transactionTyep = jsonPathEval.getString("records[0].OrderApi__Transaction_Type__c");
					String currencyCode = jsonPathEval.getString("records[0].OrderApi__Currency_Code__c");
					String message = jsonPathEval.getString("records[0].OrderApi__Message__c");
					String status = jsonPathEval.getString("records[0].OrderApi__Status__c");
					String totalAmount = jsonPathEval.getString("records[0].OrderApi__Amount__c");
					System.out.println("=====================================================");
					System.out.println("Transaction Type" + transactionTyep);
					System.out.println("Currecny Code" + currencyCode);
					System.out.println("Message" + message);
					System.out.println("Payment Status" + status);
					System.out.println("Total Amount" + totalAmount);
					System.out.println("=====================================================");
					assertEquals(transactionTyep, transactionTypes);
					assertEquals(currencyCode, currency);
					assertEquals(message, paymentMsg);
					assertEquals(status, paymentStatus);
					assertEquals(totalAmount, amount);
					if (jsonPathEval != null) {
						break;
					}
					
				}
			}

		}

	}

}
