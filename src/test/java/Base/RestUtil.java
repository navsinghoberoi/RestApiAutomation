package Base;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dzieciou.testing.curl.CurlLoggingRestAssuredConfigFactory;
import com.github.dzieciou.testing.curl.Options;
import com.github.dzieciou.testing.curl.Platform;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.Cookie;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.apache.log4j.Logger;

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

    static Logger logger = Logger.getLogger(RestUtil.class);

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
        logger.info("API response time = " + response.getTime() + " ms");
        return response.then().time(lessThan(timeInMillis));
    }

    public static void checkStatusCode(Response response) {
        logger.info("Status code of api response = " + response.getStatusCode());
        logger.info("Status Line of api response = " + response.getStatusLine());
        assertEquals("Status Code Check Failed", 200, response.getStatusCode());
    }

    // for checking specific status code other than 200
    public static void checkStatusCode(Response response, int expectedStatusCode) {
        logger.info("Status code of api response = " + response.getStatusCode());
        logger.info("Status Line of api response = " + response.getStatusLine());
        assertEquals("Status Code Check Failed", expectedStatusCode, response.getStatusCode());
    }


    public static void printResponseBody(Response response) {
        logger.info("**************************************************");
        logger.info("API response body = " + response.getBody().asString());
        logger.info("**************************************************");
    }


    public static void printLineAfterTestMethod() {
        System.out.println();
        logger.info("-------------END OF TEST----------------");
        System.out.println();
    }


    public static void printValueOfKeyFromResponse(Response response, String key) {
        Object value = response.path(key);
        logger.info("Value of " + key + " key fetched from response body = " + value);
    }

    public static void checkValueFromResponse(Response response, String key, Object expectedValue) {
        response.then().assertThat().body(key, equalTo(expectedValue));
    }

    public static int getKeysCountInResponse(Response response) {
        Map<String, ?> numberOfKeys = response.jsonPath().get("");
        logger.info("Total number of keys in response body = " + numberOfKeys.size());
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
            logger.info("Name of all the keys from the map ->");
            Set<String> keys = map.keySet();
            for (String key : keys) {
                listOfKeys.add(key);
            }
        } catch (IOException e) {
            logger.info("Exception is being handled here");
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
        logger.info("Value of the header " + header + " is = " + headerValue);
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
        logger.info(cookie + " has expiry date ? = " + data.hasExpiryDate());
        logger.info(cookie + " expiry date = " + data.getExpiryDate());
        logger.info(cookie + " value = " + data.getValue());
    }

    public Response getRequestTemplate(String endPoint) {
        Options options = Options.builder().targetPlatform(Platform.UNIX).printMultiliner().useLongForm().build();
        RestAssuredConfig curlConfig = CurlLoggingRestAssuredConfigFactory.createConfig(options);
        Response response = given()
                .log().all()
                .config(curlConfig)
                .when()
                .get(endPoint);
        printResponseBody(response);
        return response;
    }

}
