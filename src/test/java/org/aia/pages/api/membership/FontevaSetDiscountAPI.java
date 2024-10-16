package org.aia.pages.api.membership;

import static io.restassured.RestAssured.given;

import java.util.Iterator;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class FontevaSetDiscountAPI {
	WebDriver driver;

	public FontevaSetDiscountAPI(WebDriver driver) {
		this.driver = driver;
	}

	Utility util = new Utility(driver, 10);
	static String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uriUpgradeStg");
	static String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	static String sObjectURI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
	static String sObjectCompositeURI = DataProviderFactory.getConfig().getValue("sObjectURI_uriUpgradeStg");
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();
	static JsonPath jsonPathEval = null;
	private static String accountID = null;
	private static String SaleOrderId = null;
	private static String salesordeLineId=null;
	private static JsonPath jsonPathData=null;

	/**
	 * @param memberAccount
	 */
	public void setDiscountAPI(String memberAccount,double discountedAmmount) {
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
				.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		System.out.println(jsonPathEval.prettyPrint());
		accountID = jsonPathEval.getString("searchRecords[0].Id");
		System.out.println("Account ID:" + accountID);

		// In this request we are trying to fetch a sales orders.

		String SALESORDER_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Sales_Orders__r";

		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(SALESORDER_URI).then().statusCode(200).extract()
				.response();
		jsonPathEval = response.jsonPath();
		System.out.println("Salesorder" + jsonPathEval.prettyPrint());
		int totalSalesOrderSize=jsonPathEval.getInt("totalSize");

		for (int i = 0; i <=totalSalesOrderSize-1; i++) {
			SaleOrderId = jsonPathEval.getString("records[" + i + "].Id");
			System.out.println("SaleOrderId"+SaleOrderId);
			String salseOrderLineUrl = sObjectURI + "/" + "OrderApi__Sales_Order__c/" + SaleOrderId
					+ "/OrderApi__Sales_Order_Lines__r";

			response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
					.header("Accept", ContentType.JSON).when().get(salseOrderLineUrl).then().statusCode(200).extract()
					.response();
			jsonPathData = response.jsonPath();
			System.out.println("SalesorderId" + jsonPathData.prettyPrint());
			int totalsize = jsonPathData.getInt("totalSize");

			for (int j = 0; j <=totalsize-1; j++) {
			    salesordeLineId = jsonPathData.getString("records[" + j + "].Id");
				response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
						.header("Accept", ContentType.JSON).when()
						.body("{\r\n"
								+ "    \"allOrNone\": false,\r\n"
								+ "    \"records\": [\r\n"
								+ "        {\r\n"
								+ "            \"attributes\": {\r\n"
								+ "                \"type\": \"OrderApi__Sales_Order_Line__c\"\r\n"
								+ "            },\r\n"
								+ "             \"id\": \""+salesordeLineId+"\",\r\n"
								+ "            \"OrderApi__Sale_Price__c\": \""+discountedAmmount+"\",\r\n"
								+ "            \"OrderApi__Price_Override__c\":\"true\"\r\n"
								+ "\r\n"
								+ "            \r\n"
								+ "            \r\n"
								+ "        }\r\n"
								+ "    ]\r\n"
								+ "}")
						.patch(sObjectCompositeURI).then().statusCode(200).extract().response();
				String finalOP = response.jsonPath().prettyPrint();
				System.out.println("Final Output"+finalOP);
			}

		}

	}

}
