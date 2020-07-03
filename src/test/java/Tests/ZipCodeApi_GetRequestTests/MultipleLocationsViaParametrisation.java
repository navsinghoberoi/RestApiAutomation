package Tests.ZipCodeApi_GetRequestTests;

import Base.RestUtil;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author navpreetsingh on 11/05/19
 * @project RestApiAutomation
 */
public class MultipleLocationsViaParametrisation extends RestUtil {

    String baseURI = getValueFromPropertyFile("ZIPPO_API_BASEURL");

    @DataProvider(name = "data-provider")
    public static Object[][] zipCodesAndPlaces() {
        return new Object[][]{
                {"in", "110058", "Janakpuri"},
                {"us", "12345", "Schenectady"},
                {"ca", "B2R", "Waverley"}
        };
    }

    @BeforeTest
    public void setup() {
        setBaseURI(baseURI);
    }

    @Description("Validating place name fetched from api response")
    @Severity(SeverityLevel.CRITICAL)
    @Test(dataProvider = "data-provider",priority = 18)
    public void requestZipCodesFromCollection_checkPlaceNameInResponseBody_expectSpecifiedPlaceName(String countryCode, String zipCode, String expectedPlaceName) {

        given()
                .log().all()
                .pathParam("countryCode", countryCode).pathParam("zipCode", zipCode).
                when()
                .get("http://zippopotam.us/{countryCode}/{zipCode}").
                then()
                .log().all()
                .assertThat()
                .body("places[0].'place name'", equalTo(expectedPlaceName));
    }


}

