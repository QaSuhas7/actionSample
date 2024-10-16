package org.aia.pages.api;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

/**
 * @author sghodake
 *
 */
public class SignUpAPI {

    /**
     * @param userList
     * Please provide the data using list for creating the users.
     */
    public void stageUserCreationAPI(ArrayList<String> userList) {
        String fName = userList.get(0);
        String lName = userList.get(1);
        String mobilePhone = userList.get(2);
        String email = userList.get(5);
        String password = userList.get(6);
        String bearerToken = ULUserAccessToken.getAccessToken();
        RestAssured.baseURI = "https://api-stage.aia.org";
        String userDataBody = "{\r\n"
                + "    \"firstName\": \"" + fName + "\",\r\n"
                + "    \"lastName\": \"" + lName + "\",\r\n"
                + "    \"email\": \"" + email + "\",\r\n"
                + "    \"mobilePhoneCountry\": \"United States of America (+1)\",\r\n"
                + "    \"mobilePhone\": \"" + mobilePhone + "\",\r\n"
                + "    \"password\": \"" + password + "\",\r\n"
                + "    \"lastLoginApp\": \"https://stg.aia.org\"\r\n"
                + "}";
        System.out.println(userDataBody);

        Response response =
                given().contentType(ContentType.JSON).accept(ContentType.JSON)
                        .header("Authorization", "Bearer " + bearerToken)
                        .header("Content-Type", ContentType.JSON)
                        .header("Accept", ContentType.JSON)
                        .body(userDataBody)
                        .when().post("/auth/sign-up")
                        .then().extract().response();

        // Log the response for debugging
        System.out.println("Response Status Code: " + response.getStatusCode());
        System.out.println("Response Headers: " + response.getHeaders().toString());
        System.out.println("Response Body: " + response.getBody().asString());

        if (response.statusCode() == 201) {
            System.out.println("User is created...........");
        } else {
            System.out.println("User is not created, please check the status code...........");
            System.err.println("Error: " + response.getStatusCode());
        }
    }

}
