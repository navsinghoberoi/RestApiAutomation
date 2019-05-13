package Utility;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.response.Response;
import org.testng.annotations.AfterClass;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;


/**
 * @author navpreetsingh on 13/05/19
 * @project RestApiAutomation
 */


public class WireMockSetup extends RestUtil {


    WireMockServer wireMockServer;
    final static int portNumber = 8099;
    final String localHost = "http://localhost:";


//    @BeforeClass
//    public void setup() {
//        wireMockServer = new WireMockServer(portNumber);
//        wireMockServer.start();
//        setupStub();
//    }

    @AfterClass
    public void teardown() {
        wireMockServer.stop();
    }

    public void setupStub(String endpoint, String responseBody) {
        wireMockServer.stubFor(post(urlEqualTo(endpoint))     // mocking POST method
                .willReturn(aResponse()
                        .withHeader("Content-type", jsonContentType)
                        .withStatus(200)
                        .withBody(responseBody)));
    }


    // Method to be used in different classes
    public Response fetchMockApiResponse(String endpoint, String responseBody) {
        wireMockServer = new WireMockServer(portNumber);
        wireMockServer.start();
        setupStub(endpoint, responseBody);
        Response response = given().
                log().all().
                contentType("application/json").
                when().
                post(localHost + portNumber + endpoint);
        return response;
    }

    // Method to deserialize api response
    public Address fetchMockApiDeserializedResponse(String endpoint, String responseBody) {
        wireMockServer = new WireMockServer(portNumber);
        wireMockServer.start();
        setupStub(endpoint, responseBody);
        Address addressObject = given().
                log().all().
                contentType("application/json").
                when().
                post(localHost + portNumber + endpoint)
                .as(Address.class);      // actual deserialization occurs here
        return addressObject;
    }


}
