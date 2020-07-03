package Base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;
import static org.testng.AssertJUnit.assertEquals;

/**
 * @author navpreetsingh on 10/05/19
 * @project RestApiAutomation
 */
public class RestUtil {

    // Different Content Types
    public final String jsonContentType = "application/json";
    public final String xmlContentType = "application/xml";

    // Different response time variables
    public final int TWO_SECONDS = 2000;
    public final int SIX_SECONDS = 6000;
    public final int TEN_SECONDS = 10000;


    public static void setBaseURI(String URI) {
        baseURI = URI;
    }

    public static void resetBaseURI() {
        RestAssured.baseURI = null;
    }

    public static ValidatableResponse checkResponseTime(Response response, long timeInMillis) {
        System.out.println("API response time = " + response.getTime() + " ms");
        return response.then().time(lessThan(timeInMillis));
    }

    public static void checkStatusCode(Response response) {
        System.out.println("Status code of api response = " + response.getStatusCode());
        System.out.println("Status Line of api response = " + response.getStatusLine());
        assertEquals("Status Code Check Failed", 200, response.getStatusCode());
    }

    // for checking specific status code other than 200
    public static void checkStatusCode(Response response, int expectedStatusCode) {
        System.out.println("Status code of api response = " + response.getStatusCode());
        System.out.println("Status Line of api response = " + response.getStatusLine());
        assertEquals("Status Code Check Failed", expectedStatusCode, response.getStatusCode());
    }


    public static void printResponseBody(Response response) {
        System.out.println("**************************************************");
        System.out.println("API response body = " + response.getBody().asString());
        System.out.println("**************************************************");
    }


    public static void printLineAfterTestMethod() {
        System.out.println();
        System.out.println("-------------END OF TEST----------------");
        System.out.println();
    }


    public static void printValueOfKeyFromResponse(Response response, String key) {
        Object value = response.path(key);
        System.out.println("Value of " + key + " key fetched from response body = " + value);
    }

    public static void checkValueFromResponse(Response response, String key, Object expectedValue) {
        response.then().assertThat().body(key, equalTo(expectedValue));
    }

    public static int getKeysCountInResponse(Response response) {
        Map<String, ?> numberOfKeys = response.jsonPath().get("");
        System.out.println("Total number of keys in response body = " + numberOfKeys.size());
        return numberOfKeys.size();
    }


    // Fetch all keys from the api response and add them to List
    public static List fetchJsonKeysInArrayList(String json) {
        List<Object> listOfKeys = new ArrayList<Object>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new HashMap<String, Object>();
            // convert JSON string to Map
            map = mapper.readValue(json, new TypeReference<Map<String, String>>() {
            });
            System.out.println("Name of all the keys from the map ->");
            Set<String> keys = map.keySet();
            for (String key : keys) {
                listOfKeys.add(key);
            }
        } catch (IOException e) {
            System.out.println("Exception is being handled here");
            e.printStackTrace();
        }
        return listOfKeys;
    }


    public static Properties loadPropertyFile() throws Exception {
        FileInputStream fileInput = new FileInputStream(new File("/Users/nasingh/IdeaProjects/RestApiAutomation/src/test/java/Config/Data.properties"));
        Properties prop = new Properties();
        prop.load(fileInput);
        return prop;
    }

    public static String getValueFromPropertyFile(String key) {
        String fileName = null;
        try {
            fileName = loadPropertyFile().getProperty(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fileName;
    }


    public static void printResponseHeaders(Response response) {
        Headers headers = response.getHeaders();

        for (Header h : headers) {
            System.out.println(h.getName() + " : " + h.getValue());
        }
    }


    public static String fetchResponseHeaderValue(Response response, String header) {
        String headerValue = response.getHeader(header);
        System.out.println("Value of the header " + header + " is = " + headerValue);
        return headerValue;
    }


    public static void printResponseCookies(Response response) {
        Map<String, String> cookies = response.getCookies();

        for (Map.Entry<String, String> entry : cookies.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }

    }


    public static void fetchCookieDetails(Response response, String cookie) {
        Cookie data = response.getDetailedCookie(cookie);
        System.out.println(cookie + " has expiry date ? = " + data.hasExpiryDate());
        System.out.println(cookie + " expiry date = " + data.getExpiryDate());
        System.out.println(cookie + " value = " + data.getValue());
    }

    public Response getRequestTemplate(Response response, String endPoint) {
        response = given()
                .log().all()
                .when()
                .get(endPoint);
        printResponseBody(response);
        return response;

    }

}
