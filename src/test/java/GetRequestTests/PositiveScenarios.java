package GetRequestTests;

import Utility.RestUtil;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

/**
 * @author navpreetsingh on 10/05/19
 * @project RestApiAutomation
 */
public class PositiveScenarios extends RestUtil {

    private static Response response;
    String apiBody = getValueFromPropertyFile("BoardingApiBody_PositiveScenario1");
    String endPoint = getValueFromPropertyFile("BoardingApi_EndPoint");



    @BeforeTest
    public void setup() {
        setBaseURI(getValueFromPropertyFile("QAUMSDOMAIN"));
    }

    @Test(priority = 0)
    public void postBoardingApi() {
        response = postRequestTemplate(jsonContentType, apiBody, endPoint);
    }

    @Test(priority = 1)
    public void TC_01_validateApiStatusCode() {
        checkStatusCode(response);
    }

    @Test(priority = 2)
    public void TC_02_validateApiResponseTime() {
        checkResponseTime(response, 3000);
    }

    @Test(priority = 3)
    public void TC_03_validateResponseIsSuccess() {
        printValueOfKeyFromResponse(response, "success");
        checkValueFromResponse(response, "success", true);
    }

    @Test(priority = 4)
    public void TC_04_validateValueFromResponse() {
        printValueOfKeyFromResponse(response, "cached");
        checkValueFromResponse(response, "cached", false);
    }

    @Test(priority = 5)
    public void validateJsonSchema() {
        System.out.println("Performing json schema validation");
        response.then().body(matchesJsonSchemaInClasspath("Boarding_Api_Json_Schema.json"));
    }

    @Test(priority = 6)
    public void printHeadersAndCookiesData() {
        printResponseHeaders(response);
        fetchResponseHeaderValue(response, "Content-Length");
        printResponseCookies(response);
    }

    // compute number of keys in response
    @Test(priority = 7)
    public void validateCountOfKeysInResponse() {
        int count = getKeysCountInResponse(response);
        Assert.assertEquals(count, 3);
        // It will fetch all keys from the api response and add them to List
        List<Object> listOfKeys = fetchJsonKeysInArrayList(getValueFromPropertyFile("BoardingResponse"));
        System.out.println(listOfKeys);
    }


    @Test(priority = 8)
    public void validateResponseValueViaJsonPath(){
        JsonPath jsonPath = response.jsonPath();
        boolean expectedResult = jsonPath.get("success");
        Assert.assertEquals(true,expectedResult);
    }


    @AfterMethod
    public void insertLineToSeparateTests() {
        printLineAfterTestMethod();
    }

    @AfterTest
    public void afterTests() {
        System.out.println("All tests have been run");
        resetBaseURI();
    }






}
