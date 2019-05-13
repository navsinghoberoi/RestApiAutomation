package ZipCodeApi_PostRequestTests;

import Utility.Address;
import Utility.RestUtil;
import Utility.WireMockSetup;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;


/**
 * @author navpreetsingh on 13/05/19
 * @project RestApiAutomation
 */

public class Mocked_India_110018_Deserialize_Response extends RestUtil {

    public Address addressObject;

    WireMockSetup wireMockSetup = new WireMockSetup();


    // Here response is being DESERIALIZED into object of the mentioned classname
    @Test(priority = 0)
    public void getMockedApiResponse() {
        addressObject = wireMockSetup.fetchMockApiDeserializedResponse(getValueFromPropertyFile("postEndPoint2"),
                getValueFromPropertyFile("POST_API_RESPONSE_BODY2_ENDPOINT1"));
        System.out.println("Mocked api response after deserializing into POJO = " + addressObject.toString());
    }


    @Test(priority = 1)
    public void validateCityNameBeforeUpdating() {
        String actualCityName = addressObject.getCityName();
        System.out.println("City name = " + actualCityName);
        Assert.assertEquals(actualCityName, "New Delhi");
    }

    @Test(priority = 2)
    public void validateCityNameAfterUpdatingViaSetter() {
        addressObject.setCityName("New York");
        String actualCityName = addressObject.getCityName();
        System.out.println("New city name after updating via setter = " + actualCityName);
        Assert.assertEquals(actualCityName, "New York");
    }

    @AfterTest
    public void afterTests() {
        System.out.println("All tests have been run");
        resetBaseURI();
    }


}
