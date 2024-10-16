package org.aia.pages.api.abi;

import static io.restassured.RestAssured.given;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Map.Entry;

import org.aia.pages.api.events.FontevaConnection;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.Utility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ABIAPIValidation {
	WebDriver driver;
	static String ABI_URI = DataProviderFactory.getConfig().getValue("abi_uri_uriUpgradeStg");
	
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();

	public ABIAPIValidation(WebDriver Idriver) {
		this.driver = Idriver;
	}

	Utility util = new Utility(driver, 10);
	
	public void uriSetUp() {
				RestAssured.baseURI = ABI_URI;
	}
	
	public static Response getSalesOrderInfo() {
		Response response = given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON)
		.when().get(ABI_URI+"/services/data/v57.0/sobjects/OrderApi__Sales_Order__c")
				.then().log().all()
				.statusCode(200)
				.extract()
				.response();
		return response;

	}
	
	public static Response getSalesOrderLinesInfo(String url) {
		Response response = given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken)
				.header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON)
		.when().get(ABI_URI+url+"/OrderApi__Sales_Order_Lines__r")
				.then().log().all()
				.statusCode(200)
				.extract()
				.response();
		return response;

	}
		
	public static HashMap<String, Object> getSalesOrderDetails(String salesOrderId) throws JSONException {
		HashMap<String,Object> SalesOrderDetails = new HashMap<String, Object>();
		JSONObject sojo = new JSONObject(getSalesOrderInfo().getBody().asString());
		JSONArray ja=sojo.getJSONArray("recentItems");
		for(int i=0;i<=ja.length()-1;i++) {
				 if(ja.getJSONObject(i).getString("Name").equalsIgnoreCase(salesOrderId)) 
				 	{
					JSONObject attributes=ja.getJSONObject(i).getJSONObject("attributes");
					String url=attributes.getString("url");
					Response soli=getSalesOrderLinesInfo(url);
					JSONObject solinesjo = new JSONObject(soli.getBody().asString());
					JSONArray solja=solinesjo.getJSONArray("records");
				 	
					for(int j=0;j<=solja.length()-1;j++) {
						String key=solja.getJSONObject(j).getString("OrderApi__Line_Description__c");
						float value=solja.getJSONObject(j).getFloat("OrderApi__Total__c");
						SalesOrderDetails.put(key, value);
						}
				 	} 
				 }
		return SalesOrderDetails;
	}
	
	public static void main (String args[]) {
	//000416204
		//getSalesOrderLinesInfo("/services/data/v57.0/sobjects/OrderApi__Sales_Order__c/a1JDP000003K9C42AK");
		//getSalesOrderInfo();
		HashMap<String,Object> SalesOrderDetails = new HashMap<String, Object>();
		SalesOrderDetails=getSalesOrderDetails("000416204");
		for(Map.Entry<String,Object> e:SalesOrderDetails.entrySet()) {
			System.out.println(e.getKey()+" : "+e.getValue());
		}
	}
}