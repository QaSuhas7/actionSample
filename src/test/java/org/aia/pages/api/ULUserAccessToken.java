package org.aia.pages.api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.time.Instant;

public class ULUserAccessToken {

    private static String accessToken;
    private static Instant tokenExpirationTime;

    private static final String BASE_URI = "https://auth2-stg.aia.org";
    private static final String CLIENT_ID = System.getenv("CLIENT_ID");
    private static final String CLIENT_SECRET = System.getenv("CLIENT_SECRET");

    private static final long TOKEN_EXPIRATION_BUFFER = 300; // Buffer time in seconds

   public ULUserAccessToken() {
        // Private constructor to prevent instantiation
    }

    public static String getAccessToken() {
        if (isTokenExpired()) {
            generateNewToken();
        }
        return accessToken;
    }

    private static boolean isTokenExpired() {
        return accessToken == null || Instant.now().isAfter(tokenExpirationTime.minusSeconds(TOKEN_EXPIRATION_BUFFER));
    }

    private static void generateNewToken() {
        if (CLIENT_ID == null || CLIENT_SECRET == null) {
            System.out.println("Please set correct client id & client secret in cmd");
            System.out.println("Ex: setx CLIENT_ID \"your_client_id\"\r\n" + "setx CLIENT_SECRET \"your_client_secret\"\r\n" + "");
            return;
        }

        RestAssured.baseURI = BASE_URI;

        Response response = given()
                .header("Content-type", "application/x-www-form-urlencoded")
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", CLIENT_ID)
                .formParam("client_secret", CLIENT_SECRET)
                .formParam("scope", "https://api.aia.org/sign-up")
                .when()
                .post("/oauth2/token");

        System.out.println("Token Generation Response Status Code: " + response.getStatusCode());
        System.out.println("Token Generation Response Body: " + response.getBody().asString());

        if (response.getStatusCode() == 200) {
            accessToken = response.path("access_token");
            int expiresIn = response.path("expires_in");
            tokenExpirationTime = Instant.now().plusSeconds(expiresIn);
        } else {
            System.err.println("Error: " + response.getStatusCode());
        }
    }
    
}
