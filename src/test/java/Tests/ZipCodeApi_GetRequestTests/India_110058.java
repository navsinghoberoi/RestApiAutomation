package Tests.ZipCodeApi_GetRequestTests;

import Base.RestUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.hasSize;

/**
 * @author navpreetsingh on 10/05/19
 * @project RestApiAutomation
 */

public class India_110058 extends RestUtil {

    static Logger logger = Logger.getLogger(India_110058.class);

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

    @Description("Hitting the api endpoint to fetch response")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 0)
    public void getApiResponse() {
        response = getRequestTemplate(endPoint);
    }

    @Description("Validating api status code is 200")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 1)
    public void validateApiStatusCode() {
        checkStatusCode(response);
    }

    @Description("Validating api response time is within accepted limits")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 2)
    public void validateApiResponseTime() {
        checkResponseTime(response, TEN_SECONDS);
    }

    @Description("Validating country fetched from api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 3)
    public void validateCountry() {
        printValueOfKeyFromResponse(response, "country");
        checkValueFromResponse(response, "country", expectedCountryName);
    }

    @Description("Validating content type of api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 4)
    public void validateContentTypeOfResponse() {
        response.then().assertThat().contentType(jsonContentType);
    }

    @Description("Validating json schema is as per expectations")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 5)
    public void validateJsonSchema() {
        logger.info("Performing json schema validation");
        response.then().body(matchesJsonSchemaInClasspath(getValueFromPropertyFile("ZIPPO_INDIA_58_JSON_SCHEMA_PATH")));
        logger.info("json schema validation completed");
    }

    @Test(priority = 6)
    public void printHeadersAndCookiesData() {
        printResponseHeaders(response);
        fetchResponseHeaderValue(response, "Date");
        printResponseCookies(response);
    }

    @Description("Validating count of keys fetched from api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 7)
    public void validateCountOfKeysInResponse() {
        int count = getKeysCountInResponse(response);
        Assert.assertEquals(count, expectedKeysCount);
    }

    @Description("Validating place name fetched from api response")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 8)
    public void validateNumberOfPlaceName() {
        response.
                then()
                .assertThat()
                .body("places.'place name'", hasSize(expectedPlaceNameCount));
    }


    @AfterTest
    public void afterTests() {
        logger.info("All tests have been run");
        resetBaseURI();
    }


}
