package Tests.ZipCodeApi_PostRequestTests;


import Base.RestUtil;
import Utility.WireMockSetup;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author navpreetsingh on 13/05/19
 * @project RestApiAutomation
 */


public class Mocked_India_110064 extends RestUtil {

    private static Response response;
    String expectedCountryName = getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_COUNTRY");
    int expectedKeysCount = Integer.parseInt(getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_KEYS_COUNT_IN_RESPONSE"));
    String expectedPlaceName = getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_PLACENAME_RESPONSE");

    WireMockSetup wireMockSetup = new WireMockSetup();
    WireMockServer wireMockServer;

    @Test(priority = 19)
    public void getMockedApiResponse() {
        response = wireMockSetup.fetchMockApiResponse(getValueFromPropertyFile("postEndPoint1"),
                getValueFromPropertyFile("POST_API_RESPONSE_BODY1_ENDPOINT1"));
        System.out.println("Mocked api response = " + response.getBody().asString());
    }

    @Test(priority = 20)
    public void validateApiStatusCode() {
        checkStatusCode(response);
    }

    @Test(priority = 21)
    public void validateApiResponseTime() {
        checkResponseTime(response, TWO_SECONDS);
    }

    @Test(priority = 22)
    public void validateCountry() {
        printValueOfKeyFromResponse(response, "country");
        checkValueFromResponse(response, "country", expectedCountryName);
    }

    @Test(priority = 23)
    public void validateContentTypeOfResponse() {
        response.then().assertThat().contentType(jsonContentType);
    }

    @Test(priority = 24)
    public void validateJsonSchema() {
        System.out.println("Performing json schema validation");
        response.then().body(matchesJsonSchemaInClasspath(getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_JSON_SCHEMA_PATH")));
    }

    @Test(priority = 25)
    public void printHeadersAndCookiesData() {
        printResponseHeaders(response);
        fetchResponseHeaderValue(response, "Date");
        printResponseCookies(response);
    }

    @Test(priority = 26)
    public void validateCountOfKeysInResponse() {
        int count = getKeysCountInResponse(response);
        Assert.assertEquals(count, expectedKeysCount);
    }


    @Test(priority = 27)
    public void validatePlaceNameInResponse() {
        response.
                then()
                .assertThat()
                .body("places[0].'place name'", equalTo(expectedPlaceName));
    }


    @AfterTest
    public void afterTests() {
        System.out.println("All tests have been run");
        resetBaseURI();
    }

}
