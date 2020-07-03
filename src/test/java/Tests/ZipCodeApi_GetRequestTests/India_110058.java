package Tests.ZipCodeApi_GetRequestTests;

import Base.RestUtil;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author navpreetsingh on 10/05/19
 * @project RestApiAutomation
 */

public class India_110058 extends RestUtil {

    private static Response response;
    String endPoint = getValueFromPropertyFile("ZIPPO_INDIA_58_ENDPOINT");
    String baseURI = getValueFromPropertyFile("ZIPPO_API_BASEURL");
    String expectedCountryName = getValueFromPropertyFile("ZIPPO_INDIA_58_COUNTRY");
    int expectedKeysCount = Integer.parseInt(getValueFromPropertyFile("ZIPPO_INDIA_58_KEYS_COUNT_IN_RESPONSE"));
    int expectedPlaceNameCount = Integer.parseInt(getValueFromPropertyFile("ZIPPO_INDIA_58_PLACENAME_COUNT_IN_RESPONSE"));


    @BeforeTest
    public void setup() {
        setBaseURI(baseURI);
    }

    @Test(priority = 0)
    public void getApiResponse() {
      response =  getRequestTemplate(response,endPoint);
    }

    @Test(priority = 1)
    public void validateApiStatusCode() {
        checkStatusCode(response);
    }

    @Test(priority = 2)
    public void validateApiResponseTime() {
        checkResponseTime(response, TEN_SECONDS);
    }

    @Test(priority = 3)
    public void validateCountry() {
        printValueOfKeyFromResponse(response, "country");
        checkValueFromResponse(response, "country", expectedCountryName);
    }

    @Test(priority = 4)
    public void validateContentTypeOfResponse() {
        response.then().assertThat().contentType(jsonContentType);
    }

    @Test(priority = 5)
    public void validateJsonSchema() {
        System.out.println("Performing json schema validation");
        response.then().body(matchesJsonSchemaInClasspath(getValueFromPropertyFile("ZIPPO_INDIA_58_JSON_SCHEMA_PATH")));
    }

    @Test(priority = 6)
    public void printHeadersAndCookiesData() {
        printResponseHeaders(response);
        fetchResponseHeaderValue(response, "Date");
        printResponseCookies(response);
    }

    // compute number of keys in response
    @Test(priority = 7)
    public void validateCountOfKeysInResponse() {
        int count = getKeysCountInResponse(response);
        Assert.assertEquals(count, expectedKeysCount);
    }


    @Test(priority = 8)
    public void validateNumberOfPlaceName() {
        response.
                then()
                .assertThat()
                .body("places.'place name'", hasSize(expectedPlaceNameCount));
    }


    @AfterTest
    public void afterTests() {
        System.out.println("All tests have been run");
        resetBaseURI();
    }


}
