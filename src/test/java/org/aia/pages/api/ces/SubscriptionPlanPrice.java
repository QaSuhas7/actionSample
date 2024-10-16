package org.aia.pages.api.ces;

import static io.restassured.RestAssured.given;

import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.openqa.selenium.WebDriver;

import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class SubscriptionPlanPrice {
	WebDriver driver;

	public SubscriptionPlanPrice(WebDriver driver) {
		this.driver = driver;
	}

	Utility util = new Utility(driver, 10);
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();
	JsonPath jsonPathEval = null;
	private static double membershipAmmount = 0;
	
	/**
	 * From here we are getting exact amount ces membership price using "OrderApi__Item__c" end point;
	 * @param cesMembershipId 
	 * @return 
	 */
	public double getCesMembershipPrice(String cesMembershipId) {
		String subscriptionPlanItamURL= DataProviderFactory.getConfig().getValue("orderItemAPIUrlUpgradeStg")+"/"+cesMembershipId;
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(subscriptionPlanItamURL).then().statusCode(200)
				.extract().response();

		jsonPathEval = response.jsonPath();
		membershipAmmount = jsonPathEval.getDouble("OrderApi__Price__c");
		System.out.println("membershipAmmount:" + membershipAmmount);
		return membershipAmmount;
	}
}
