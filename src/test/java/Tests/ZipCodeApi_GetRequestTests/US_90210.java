package Tests.ZipCodeApi_GetRequestTests;

import Base.RestUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.apache.log4j.Logger;
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

    static Logger logger = Logger.getLogger(US_90210.class);

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


    @Description("Hitting the api endpoint to fetch response")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 9)
    public void getApiResponse() {
        response = getRequestTemplate(endPoint);
    }

    @Description("Validating api status code is 200")
    @Severity(SeverityLevel.BLOCKER)
    @Test(priority = 10)
    public void validateApiStatusCode() {
        checkStatusCode(response);
    }

    @Description("Validating api response time is within accepted limits")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 11)
    public void validateApiResponseTime() {
        checkResponseTime(response, TEN_SECONDS);
    }

    @Description("Validating country fetched from api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 12)
    public void validateCountry() {
        printValueOfKeyFromResponse(response, "country");
        checkValueFromResponse(response, "country", expectedCountryName);
    }

    @Description("Validating content type of api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 13)
    public void validateContentTypeOfResponse() {
        response.then().assertThat().contentType(jsonContentType);
    }

    @Description("Validating json schema is as per expectations")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 14)
    public void validateJsonSchema() {
        logger.info("Performing json schema validation");
        response.then().body(matchesJsonSchemaInClasspath(getValueFromPropertyFile("ZIPPO_US_90210_JSON_SCHEMA_PATH")));
        logger.info("json schema validation completed");
    }

    @Test(priority = 15)
    public void printHeadersAndCookiesData() {
        printResponseHeaders(response);
        fetchResponseHeaderValue(response, "Date");
        printResponseCookies(response);
    }

    @Description("Validating count of keys fetched from api response")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 16)
    public void validateCountOfKeysInResponse() {
        int count = getKeysCountInResponse(response);
        Assert.assertEquals(count, expectedKeysCount);
    }

    @Description("Validating place name fetched from api response")
    @Severity(SeverityLevel.MINOR)
    @Test(priority = 17)
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
