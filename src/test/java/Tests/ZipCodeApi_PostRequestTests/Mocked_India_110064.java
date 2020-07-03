package Tests.ZipCodeApi_PostRequestTests;


import Base.RestUtil;
import Utility.WireMockSetup;
import com.github.tomakehurst.wiremock.WireMockServer;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
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

    static Logger logger = Logger.getLogger(Mocked_India_110064.class);

    private static Response response;
    String expectedCountryName = getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_COUNTRY");
    int expectedKeysCount = Integer.parseInt(getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_KEYS_COUNT_IN_RESPONSE"));
    String expectedPlaceName = getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_PLACENAME_RESPONSE");

    WireMockSetup wireMockSetup = new WireMockSetup();
    WireMockServer wireMockServer;

    @Description("Hitting the api endpoint to fetch mocked api response")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 19)
    public void getMockedApiResponse() {
        response = wireMockSetup.fetchMockApiResponse(getValueFromPropertyFile("postEndPoint1"),
                getValueFromPropertyFile("POST_API_RESPONSE_BODY1_ENDPOINT1"));
        logger.info("Mocked api response = " + response.getBody().asString());
    }

    @Description("Validating api status code is 200")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 20)
    public void validateApiStatusCode() {
        checkStatusCode(response);
    }

    @Description("Validating api response time is within accepted limits")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 21)
    public void validateApiResponseTime() {
        checkResponseTime(response, TWO_SECONDS);
    }

    @Description("Validating country fetched from api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 22)
    public void validateCountry() {
        printValueOfKeyFromResponse(response, "country");
        checkValueFromResponse(response, "country", expectedCountryName);
    }

    @Description("Validating content type of api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 23)
    public void validateContentTypeOfResponse() {
        response.then().assertThat().contentType(jsonContentType);
    }

    @Description("Validating json schema is as per expectations")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 24)
    public void validateJsonSchema() {
        logger.info("Performing json schema validation");
        response.then().body(matchesJsonSchemaInClasspath(getValueFromPropertyFile("ZIPPO_MOCK_INDIA_110064_JSON_SCHEMA_PATH")));
    }

    @Test(priority = 25)
    public void printHeadersAndCookiesData() {
        printResponseHeaders(response);
        fetchResponseHeaderValue(response, "Date");
        printResponseCookies(response);
    }

    @Description("Validating count of keys fetched from api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 26)
    public void validateCountOfKeysInResponse() {
        int count = getKeysCountInResponse(response);
        Assert.assertEquals(count, expectedKeysCount);
    }

    @Description("Validating place name fetched from api response")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 27)
    public void validatePlaceNameInResponse() {
        response.
                then()
                .assertThat()
                .body("places[0].'place name'", equalTo(expectedPlaceName));
    }


    @AfterTest
    public void afterTests() {
        logger.info("All tests have been run");
        resetBaseURI();
    }

}
