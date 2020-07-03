package Tests.ZipCodeApi_PostRequestTests;

import Base.RestUtil;

import PojoClasses.Address;
import Utility.WireMockSetup;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;


/**
 * @author navpreetsingh on 13/05/19
 * @project RestApiAutomation
 */

public class Mocked_India_110018_Deserialize_Response extends RestUtil {

    static Logger logger = Logger.getLogger(Mocked_India_110018_Deserialize_Response.class);
    public Address addressObject;

    WireMockSetup wireMockSetup = new WireMockSetup();
    WireMockServer wireMockServer;

    // Here response is being DESERIALIZED into object of the mentioned classname
    @Test(priority = 28)
    public void getMockedApiResponse() {
        addressObject = wireMockSetup.fetchMockApiDeserializedResponse(getValueFromPropertyFile("postEndPoint2"),
                getValueFromPropertyFile("POST_API_RESPONSE_BODY2_ENDPOINT1"));
       logger.info("Mocked api response after deserializing into POJO = " + addressObject.toString());
    }


    @Test(priority = 29)
    public void validateCityNameBeforeUpdating() {
        String actualCityName = addressObject.getCityName();
        logger.info("City name = " + actualCityName);
        Assert.assertEquals(actualCityName, "New Delhi");
    }

    @Test(priority = 30)
    public void validateCityNameAfterUpdatingViaSetter() {
        addressObject.setCityName("New York");
        String actualCityName = addressObject.getCityName();
        logger.info("New city name after updating via setter = " + actualCityName);
        Assert.assertEquals(actualCityName, "New York");
    }

    @AfterTest
    public void afterTests() {
        logger.info("All tests have been run");
        resetBaseURI();
    }


}
