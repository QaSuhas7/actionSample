package org.aia.pages.api.membership;

import static io.restassured.RestAssured.given;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.path.xml.XmlPath;

public class FontevaConnectionSOAP {

	public static String getSessionID()
    {
		String requestBody = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:partner.soap.sforce.com\"> \r\n"
				+ "   <soapenv:Body>\r\n"
				+ "      <urn:login>    \r\n"
				+ "        <urn:username>sgopisetty@innominds.com.aia.prod.upgradestg</urn:username>\r\n"
				+ "         <urn:password>Fonteva@446yWHSrM4h0ciAqSZRGfa8Xlo3</urn:password>\r\n"
				+ "      </urn:login>    \r\n"
				+ "   </soapenv:Body>    \r\n"
				+ "</soapenv:Envelope>";
       
       Response paresponse = 
		    	 given()
		    	 .contentType("application/soap+xml; charset=UTF-8;")
		    	 .contentType(ContentType.HTML)
		         .accept(ContentType.JSON)
		    	 .header("Content-type", "text/xml")
		         .header("SOAPAction", "Define")
		         .body(requestBody)
				 .when().post("https://aia--upgradestg.sandbox.my.salesforce.com/services/Soap/u/35.00").
				 then().extract().response();
//       System.out.println(paresponse.prettyPrint());
       
       //next we get the xmlPath of the response
		XmlPath xmlPath = paresponse.xmlPath();
		//and get the value of a node in the xml
		String nodeValue= xmlPath.get("Envelope.Body.loginResponse.result.sessionId");
//		System.out.println(nodeValue);
		return nodeValue;
    }
	public static void main(String[] args) {
		try {
			System.out.println(getSessionID());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
