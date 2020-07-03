package Tests.ZipCodeApi_GetRequestTests;

import Base.RestUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author navpreetsingh on 11/05/19
 * @project RestApiAutomation
 */
public class US_90210 extends RestUtil {


    private static Response response;
    String endPoint = getValueFromPropertyFile("ZIPPO_US_90210_ENDPOINT");
    String baseURI = getValueFromPropertyFile("ZIPPO_API_BASEURL");
    String expectedCountryName = getValueFromPropertyFile("ZIPPO_US_90210_COUNTRY");
    int expectedKeysCount = Integer.parseInt(getValueFromPropertyFile("ZIPPO_US_90210_KEYS_COUNT_IN_RESPONSE"));
    String expectedPlaceName = getValueFromPropertyFile("ZIPPO_US_90210_PLACENAME_RESPONSE");


    @BeforeTest
    public void setup() {
        setBaseURI(baseURI);
    }

    @Test(priority = 9)
    public void getApiResponse() {
        response = getRequestTemplate(response, endPoint);
    }

    @Test(priority = 10)
    public void validateApiStatusCode() {
        checkStatusCode(response);
    }

    @Test(priority = 11)
    public void validateApiResponseTime() {
        checkResponseTime(response, TEN_SECONDS);
    }

    @Test(priority = 12)
    public void validateCountry() {
        printValueOfKeyFromResponse(response, "country");
        checkValueFromResponse(response, "country", expectedCountryName);
    }

    @Test(priority = 13)
    public void validateContentTypeOfResponse() {
        response.then().assertThat().contentType(jsonContentType);
    }

    @Test(priority = 14)
    public void validateJsonSchema() {
        System.out.println("Performing json schema validation");
        response.then().body(matchesJsonSchemaInClasspath(getValueFromPropertyFile("ZIPPO_US_90210_JSON_SCHEMA_PATH")));
    }

    @Test(priority = 15)
    public void printHeadersAndCookiesData() {
        printResponseHeaders(response);
        fetchResponseHeaderValue(response, "Date");
        printResponseCookies(response);
    }

    // compute number of keys in response
    @Test(priority = 16)
    public void validateCountOfKeysInResponse() {
        int count = getKeysCountInResponse(response);
        Assert.assertEquals(count, expectedKeysCount);
    }

    @Test(priority = 17)
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
