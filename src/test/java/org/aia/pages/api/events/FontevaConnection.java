package org.aia.pages.api.events;

import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class FontevaConnection {

	public String getbearerToken() {

		String response = given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.param("username", "sgopisetty@innominds.com.aia.prod.upgradestg")
				.param("password", "Fonteva@443EPdieieVelj8JFKaO2srpJZj")
				.param("client_id",
						"3MVG9ZM6Cuht.9St5FCATeNnzGJZdZhv6GlIJ1Dp336uMPMHOYwBy47aCa0TAKNY9Ya5.zulbUhmNVetwlo8N")
				.param("client_secret", "8CC272DD9BA7FDF1E32471FC6E275E1A510133952F25EE47E9AADBDEDFDA1FF6")
				.param("grant_type", "password").header("Accept", "application/json")
				.header("Content-type", "application/x-www-form-urlencoded").when()
				.post("https://aia--upgradestg.sandbox.my.salesforce.com/services/oauth2/token").then().extract()
				.path("access_token");
		return response;
	}
}
