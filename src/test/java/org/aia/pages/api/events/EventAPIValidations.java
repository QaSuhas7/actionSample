package org.aia.pages.api.events;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.text.DecimalFormat;
import java.util.Date;

import org.aia.pages.events.EventRegistration;
import org.aia.pages.fonteva.events.NewCloneEvents;
import org.aia.utility.ConfigDataProvider;
import org.aia.utility.DataProviderFactory;
import org.aia.utility.DateUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class EventAPIValidations {
	WebDriver driver;
	ConfigDataProvider testData;

	static Logger log = Logger.getLogger(NewCloneEvents.class);
	static String EVENT_URI = DataProviderFactory.getConfig().getValue("event_uriUpgradeStg");
	static FontevaConnection bt = new FontevaConnection();
	private static final String bearerToken = bt.getbearerToken();

	String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uriUpgradeStg");
	String ACCOUNT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	String SOBJECT_URI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
	String RECCIPT_URI = DataProviderFactory.getConfig().getValue("account_uriUpgradeStg");
	JsonPath jsonPathEval = null;
	private static String accountID = null;
	EventRegistration eventRegistration;

	static String eventName = "TestEvent_" + DateUtils.getFormatedDate(new Date(), "MMddyyyyHHmmss");
	static String startDate = DateUtils.getFormatedDate(new Date(), "yyyy-MM-dd");
	static String time = DateUtils.getFormatedDate(new Date(), "hh:mm a");
	static String endDate = DateUtils.getFormatedDate(DateUtils.getDateAfter(new Date(), 5), "yyyy-MM-dd");
	static String eventKey = DateUtils.getFormatedDate(new Date(), "MMddyyyyHHmmssa");
	static String body = "{ \"Name\" : " + "\"" + eventName + "\"," + " \"EventApi__Start_Date__c\" : " + "\""
			+ startDate + "\"," + " \"EventApi__Start_Time__c\" : " + "\"" + time + "\","
			+ " \"EventApi__End_Time__c\": " + "\"" + time + "\"," + " \"EventApi__End_Date__c\":  " + "\"" + endDate
			+ "\"," + " \"EventApi__Event_Category__c\" : \"a1X1U000001UeeCUAS\"," + " \"EventApi__Event_Key__c\" : "
			+ "\"" + eventKey + "\"" + "  }";

	public EventAPIValidations(WebDriver Idriver) {
		this.driver = Idriver;
		eventRegistration = PageFactory.initElements(driver, EventRegistration.class);
		testData = new ConfigDataProvider();
	}

	public void uriSetUp() {
		RestAssured.baseURI = DataProviderFactory.getConfig().getValue("event_uri_qa");
	}

	public void cloneEvent(ITestContext context) throws InterruptedException {

		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).and().body(body).when().post(EVENT_URI).then().log().all()
				.statusCode(201).extract().response();

		JsonPath jsonPath = response.jsonPath();
		String eventID = jsonPath.getString("id");
		context.setAttribute("eventId", eventID);
		Thread.sleep(1000);

	}

	/**
	 * @param context
	 * @throws Throwable
	 */
	public void verifyEvent(ITestContext context) throws Throwable {
		String Id = context.getAttribute("eventId").toString();
		System.out.println("EVENTID: " + Id);
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(EVENT_URI + "/" + Id).then().log().all().statusCode(200)
				.extract().response();
		JSONObject obj = new JSONObject(response.asString());
		Assert.assertEquals(obj.get("Name").toString(), context.getAttribute("eventName").toString());

		Assert.assertEquals(Double.parseDouble(obj.get("EventApi__Attendees__c").toString()),
				Double.parseDouble("0.0"));
		System.out.println("VERIFIED: Event Name - " + obj.get("Name").toString());
		log.info("VERIFIED: Event Name - " + obj.get("Name").toString());
	}

	/**
	 * @param context
	 * @throws Throwable
	 */
	public void verifySalesOrderRegistration(ITestContext context) throws Throwable {
		String Id = context.getAttribute("eventId").toString();
		String attendeeQuan = context.getAttribute("attendees").toString();
		String soldTickets = context.getAttribute("soldtickets").toString();
		String remainEventCapacity = context.getAttribute("remainEvents").toString();
		String reaminingTickets = context.getAttribute("remainTickets").toString();
		System.out.println("EVENTID: " + Id);
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(EVENT_URI + "/" + Id).then().log().all().statusCode(200)
				.extract().response();
		JSONObject obj = new JSONObject(response.asString());
		Assert.assertEquals(obj.get("Name").toString(), context.getAttribute("eventName").toString());
		System.out.println("VERIFIED: Event Name - " + obj.get("Name").toString());
		log.info("VERIFIED: Event Name - " + obj.get("Name").toString());

		// attendees
		Assert.assertEquals(Double.parseDouble(obj.get("EventApi__Attendees__c").toString()),
				Double.parseDouble(attendeeQuan));
		// ticket sold
		Assert.assertEquals(Double.parseDouble(obj.get("EventApi__Quantity_Sold__c").toString()),
				Double.parseDouble(soldTickets));

		// event capacity remaining
		Assert.assertEquals(Double.parseDouble(obj.get("EventApi__Event_Capacity_Remaining__c").toString()),
				Double.parseDouble(remainEventCapacity));

		Assert.assertEquals(Double.parseDouble(obj.get("EventApi__Quantity_Remaining__c").toString()),
				Double.parseDouble(reaminingTickets));

	}

	/**
	 * @param context
	 * @throws Throwable
	 */
	public void verifyAttendees(ITestContext context) throws Throwable {
		String Id = context.getAttribute("eventId").toString();
		String attndessQun = context.getAttribute("attendees").toString();
		System.out.println("EVENTID: " + Id);
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(EVENT_URI + "/" + Id).then().log().all().statusCode(200)
				.extract().response();
		JSONObject obj = new JSONObject(response.asString());
		Assert.assertEquals(obj.get("Name").toString(), context.getAttribute("eventName").toString());
		// attendees
		Assert.assertEquals(Double.parseDouble(obj.get("EventApi__Attendees__c").toString()),
				Double.parseDouble(attndessQun));
	}

	/**
	 * @param context
	 * @param memberAccount
	 * @param receiptNumberExpected
	 * @param ammount
	 * @throws InterruptedException Here we validate the event receipt using get api
	 *                              call endpoint is "OrderApi__Receipts__r"
	 */
	public void verifyReciptDetails(String memberAccount, Object receiptNumberExpected, Object amount)
			throws InterruptedException {
		log.info("===============Started Validate ReciptDetails for events=================");
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
				.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();

		System.out.println(response.asString());
		jsonPathEval = response.jsonPath();
		accountID = jsonPathEval.getString("searchRecords[0].Id");
		log.info(accountID);
		String RECIPTS_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Receipts__r";
		log.info(RECIPTS_URI);
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).param("fields", "Name," + "OrderApi__Total__c").when()
				.get(RECIPTS_URI).then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		log.info(jsonPathEval);
		int totalReciptCount = jsonPathEval.getInt("totalSize");
		log.info(totalReciptCount);
		if (totalReciptCount > 0) {
			System.out.println("Number of Recipt : " + totalReciptCount);
			String receiptNumber = jsonPathEval.getString("records[0].Name");
			Object totalFeePaid = jsonPathEval.getDouble("records[0].OrderApi__Total__c");
			System.out.println("=====================================");
			System.out.println("Receipt number :" + receiptNumber);
			System.out.println("Total fee paid :" + totalFeePaid);
			System.out.println("=====================================");
			assertEquals(receiptNumber, receiptNumberExpected.toString().split("#")[1]);
			log.info("Actual Receipt number" + receiptNumber + "is equals to" + receiptNumberExpected);
			double doubleValue = Double.parseDouble(totalFeePaid.toString());

			// Format the double to a string with two decimal places
			assertEquals("$" + new DecimalFormat("0.00").format(doubleValue), amount);
			log.info("Actual Receipt number" + totalFeePaid + "is equals to" + amount);
		} else {
			System.out.println("No Recipt found!!!");
		}
		log.info("================ReciptDetails are Validated===============");
	}

	/**
	 * @param memberAccount
	 * @param orderPaidStatus
	 * @param closed
	 * @param posted
	 * @throws InterruptedException
	 */
	public void verifySalesOrder(String memberAccount, String orderPaidStatus, String closed, Object amount,
			String posted) throws InterruptedException {
		log.info("==================started Validate SalesOrder for events================");
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).param("q", memberAccount).param("sobject", "Account").when()
				.get(PARAMETERIZED_SEARCH_URI).then().statusCode(200).extract().response();

		System.out.println(response.asString());
		log.info(response.asString());
		jsonPathEval = response.jsonPath();
		accountID = jsonPathEval.getString("searchRecords[0].Id");
		log.info(accountID);

		// Use Account ID to fetch account details.
		String SALESORDER_URI = ACCOUNT_URI + "/" + accountID + "/OrderApi__Sales_Orders__r";
		System.out.println("Account Id is:" + accountID);
		response = given().header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON)
				.param("fields",
						"OrderApi__Sales_Order_Status__c," + "OrderApi__Status__c," + "OrderApi__Posting_Status__c,"
								+ "OrderApi__Amount_Paid__c," + "OrderApi__Date__c, "
								+ "AIA_National_Subscription_Plan__c")
				.when().get(SALESORDER_URI).then().statusCode(200).extract().response();

		jsonPathEval = response.jsonPath();
		int totalSalesOrderCount = jsonPathEval.getInt("totalSize");
		if (totalSalesOrderCount > 0) {
			System.out.println("Number of Sales order : " + totalSalesOrderCount);
			String closedStatus = jsonPathEval.getString("records[0].OrderApi__Status__c");
			String salesOrderStatus = jsonPathEval.getString("records[0].OrderApi__Sales_Order_Status__c");
			String postingStatus = jsonPathEval.getString("records[0].OrderApi__Posting_Status__c");
			Object amountPaid = jsonPathEval.getDouble("records[0].OrderApi__Amount_Paid__c");
			String salesOrderPaidDate = jsonPathEval.getString("records[0].OrderApi__Date__c");
			String subscriptionPlan = jsonPathEval.getString("records[0].AIA_National_Subscription_Plan__c");

			System.out.println("=====================================");
			System.out.println("Status :" + closedStatus);
			System.out.println("Status of Sales orders :" + salesOrderStatus);
			System.out.println("Sales orders Posting Status :" + postingStatus);
			System.out.println("Sales orders amount paid :" + amountPaid);
			System.out.println("Sales orders date :" + salesOrderPaidDate);
			System.out.println("Sales orders Subscription_Plan :" + subscriptionPlan);
			System.out.println("=====================================");

			assertEquals(salesOrderStatus, orderPaidStatus);
			assertEquals(closedStatus, closed);
			double doubleValue = Double.parseDouble(amountPaid.toString());
			assertEquals("$" + new DecimalFormat("0.00").format(doubleValue), amount);
			log.info("Actual Receipt number" + amountPaid + "is equals to" + amount);
			assertEquals(postingStatus, posted);
			assertEquals(salesOrderPaidDate, java.time.LocalDate.now().toString());
			if (postingStatus.equalsIgnoreCase("unpaid")) {
				assertEquals(subscriptionPlan, "Dues Installment Plan - 6 Installments");
			}
		} else {
			System.out.println("No Sales order found!!!");
		}
		log.info("==============Sales Order is Validated===================");
	}
	
	/**
	 * @param context
	 * @throws Throwable
	 */
	public void verifyEventTicketCapacity(ITestContext context) throws Throwable {
		String Id = context.getAttribute("eventId").toString();
		System.out.println("EVENTID: " + Id);
		 String uiEditEventCapacity =context.getAttribute("uiEventCapacityAfterModify").toString();
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(EVENT_URI + "/" + Id).then().log().all().statusCode(200)
				.extract().response();
		JSONObject obj = new JSONObject(response.asString());
		Assert.assertEquals(obj.get("Name").toString(), context.getAttribute("eventName").toString());

		Assert.assertEquals(Double.parseDouble(obj.get("EventApi__Capacity__c").toString()),
				Double.parseDouble(uiEditEventCapacity));
		System.out.println("VERIFIED: Event Name - " + obj.get("Name").toString());
		log.info("VERIFIED: Event Name - " + obj.get("Name").toString());
	}
	
	public void verifyEventModifyTicketSalesStartDate(ITestContext context) throws Throwable {
		String Id = context.getAttribute("eventId").toString();
		System.out.println("EVENTID: " + Id);
		 String uiTicketSalesStartDate =context.getAttribute("ticketSalesStartDate").toString();
		Response response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", "Bearer " + bearerToken).header("Content-Type", ContentType.JSON)
				.header("Accept", ContentType.JSON).when().get(EVENT_URI + "/" + Id).then().log().all().statusCode(200)
				.extract().response();
		JSONObject obj = new JSONObject(response.asString());
		Assert.assertEquals(obj.get("Name").toString(), context.getAttribute("eventName").toString());

		Assert.assertEquals(obj.get("EventApi__Ticket_Sales_Start_Date__c").toString(),uiTicketSalesStartDate);
		System.out.println("VERIFIED: Event Name - " + obj.get("Name").toString());
		log.info("VERIFIED: Event Name - " + obj.get("Name").toString());
	}
	
	
}
