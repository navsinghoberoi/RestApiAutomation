package Utility;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;


/**
 * @author navpreetsingh on 13/05/19
 * @project RestApiAutomation
 */


public class WireMockSetup extends RestUtil {


    WireMockServer wireMockServer;
    final static int portNumber = 8099;
    final String postEndPoint1 = "/postApi/endpoint";


    @BeforeClass
    public void setup() {
        wireMockServer = new WireMockServer(portNumber);
        wireMockServer.start();
        setupStub();
    }

    @AfterClass
    public void teardown() {
        wireMockServer.stop();
    }

    public void setupStub() {
        wireMockServer.stubFor(get(urlEqualTo(postEndPoint1))
                .willReturn(aResponse()
                        .withHeader("Content-type", jsonContentType)
                        .withStatus(200)
                        .withBody(getValueFromPropertyFile("POST_API_ENDPOINT1"))));
    }


    @Test(priority = 0)
    public void testStatusCodePositive() {
        given().
                when().
                get("http://localhost:" + portNumber + postEndPoint1).
                then().
                assertThat().statusCode(200);
    }

    @Test(priority = 1)
    public void testStatusCodeNegative() {
        given().
                when().
                get("http://localhost:" + portNumber + "/incorrect/endpoint").
                then().
                assertThat().statusCode(404);
    }


    @Test(priority = 2)
    public void testResponseContents() {
        Response response = given().
                when().
                get("http://localhost:" + portNumber + postEndPoint1);
        String apiResponse = response.asString();
        System.out.println(apiResponse);
        Assert.assertEquals(getValueFromPropertyFile("POST_API_ENDPOINT1"), apiResponse);
    }


    @Test(priority = 3)
    public void testResponseContentType() {
        Response response = given().
                when().
                get("http://localhost:" + portNumber + postEndPoint1);
        String actualContentType = response.getContentType();
        System.out.println(actualContentType);
        Assert.assertEquals(actualContentType,jsonContentType);
    }




    // Method to be used in different classes
    public Response fetchMockApiResponse() {

        wireMockServer = new WireMockServer(portNumber);
        wireMockServer.start();
        setupStub();


        Response response = given().
                when().
                get("http://localhost:" + portNumber + postEndPoint1);
        return response;
    }


}
