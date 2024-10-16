package org.aia.pages.api.membership;

import static io.restassured.RestAssured.given;


import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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

/**
 * @author sghodake
 *
 */
public class FontevaMemTermDateChangeAPI {
    WebDriver driver;
    Utility util;
    static String PARAMETERIZED_SEARCH_URI = DataProviderFactory.getConfig().getValue("parameterizedSearch_uriUpgradeStg");
    static String sObjectURI = DataProviderFactory.getConfig().getValue("sobject_uriUpgradeStg");
    static String sObjectCompositeURI = DataProviderFactory.getConfig().getValue("sObjectURI_uriUpgradeStg");
    static FontevaConnection bt = new FontevaConnection();
    private static final String bearerToken = bt.getbearerToken();
    int attempt = 0;

    public FontevaMemTermDateChangeAPI(WebDriver driver) {
        this.driver = driver;
        util = new Utility(driver, 10);
    }

    /**
     * Generic function to change term end date
     * @param memberAccount The member account to search for
     * @param termDate The new term end date to set
     */
    public void changeTermDateAPI(String memberAccount, String termDate) throws Exception {
        attempt = 0; // Reset attempt counter for retries
        String contactId = null, membershipId = null, termId = null;

        // Retry logic for up to 3 attempts
        while (attempt < 3) {
            attempt++;
            try {
                // Step 1: Get Contact ID
                String searchUri = PARAMETERIZED_SEARCH_URI + "?q=" + memberAccount + "&sobject=Contact";
                Response response = executeWithRetry(searchUri, 3);
                if (response == null || response.jsonPath().getList("searchRecords").isEmpty()) {
                    throw new Exception("No contact ID found for account: " + memberAccount);
                }
                contactId = response.jsonPath().getString("searchRecords[0].Id");
                System.out.println("Contact ID: " + contactId);

                // Step 2: Get Membership ID
                String membershipSubUri = sObjectURI + "/Contact/" + contactId + "/OrderApi__Subscriptions__r";
                response = executeWithRetry(membershipSubUri, 3);
                if (response == null || response.jsonPath().getList("records").isEmpty()) {
                    throw new Exception("No membership ID found for contact: " + contactId);
                }
                membershipId = response.jsonPath().getString("records[0].Id");
                System.out.println("Membership ID: " + membershipId);

                // Step 3: Get Term ID
                String selectTermUri = sObjectURI + "/OrderApi__Subscription__c/" + membershipId + "/OrderApi__Renewals__r";
                response = executeWithRetry(selectTermUri, 3);
                if (response == null || response.jsonPath().getList("records").isEmpty()) {
                    throw new Exception("No term ID found for membership: " + membershipId);
                }
                termId = response.jsonPath().getString("records[0].Id");
                System.out.println("Term ID: " + termId);

                // Step 4: Change Term End Date
                String requestBody = "{\r\n" + "    \"allOrNone\": false,\r\n" + "    \"records\": [\r\n" + "        {\r\n"
                        + "            \"attributes\": {\r\n" + "                \"type\": \"OrderApi__Renewal__c\"\r\n"
                        + "            },\r\n" + "             \"id\": \"" + termId + "\",\r\n"
                        + "            \"OrderApi__Term_End_Date__c\": \"" + termDate + "\"\r\n" + "        }\r\n"
                        + "    ]\r\n" + "}";
                response = executeWithRetryForPatch(sObjectCompositeURI, requestBody, 3);
                if (response.statusCode() == 200) {
                    System.out.println("Term date changed successfully: " + response.prettyPrint());
                    break; // Success, exit retry loop
                } else {
                    System.out.println("Error in changing term date: " + response.prettyPrint());
                }

            } catch (Exception e) {
                System.out.println("Request failed in attempt " + attempt + ": " + e.getMessage());
                if (attempt >= 3) {
                    throw e; // Max attempts reached, throw the exception
                }
                Thread.sleep(2000); // Wait before retrying
            }
        }
    }

    // Method to execute GET request with retries
    private Response executeWithRetry(String uri, int maxRetries) throws InterruptedException {
        int attempt = 0;
        Response response = null;
        while (attempt < maxRetries) {
            attempt++;
            try {
                response = given()
                    .contentType(ContentType.JSON)
                    .accept(ContentType.JSON)
                    .header("Authorization", "Bearer " + bearerToken)
                    .header("Content-Type", ContentType.JSON)
                    .header("Accept", ContentType.JSON)
                    .when()
                    .get(uri)
                    .then()
                    .extract()
                    .response();

                if (response.statusCode() == 200) {
                    return response; // Successful response
                } else {
                    System.out.println("GET request failed with status code: " + response.statusCode());
                }
            } catch (Exception e) {
                System.out.println("GET request failed on attempt " + attempt + ": " + e.getMessage());
            }
            Thread.sleep(2000); // Wait before retrying
        }
        return response;
    }

    // Method to execute PATCH request with retries
    private Response executeWithRetryForPatch(String uri, String body, int maxRetries) throws InterruptedException {
        int attempt = 0;
        Response response = null;
        while (attempt < maxRetries) {
            attempt++;
            try {
                response = given()
                    .header("Authorization", "Bearer " + bearerToken)
                    .header("Content-Type", ContentType.JSON)
                    .header("Accept", ContentType.JSON)
                    .body(body)
                    .when()
                    .patch(uri)
                    .then()
                    .extract()
                    .response();

                if (response.statusCode() == 200) {
                    return response; // Successful response
                } else {
                    System.out.println("PATCH request failed with status code: " + response.statusCode());
                }
            } catch (Exception e) {
                System.out.println("PATCH request failed on attempt " + attempt + ": " + e.getMessage());
            }
            Thread.sleep(2000); // Wait before retrying
        }
        return response;
    }
}
