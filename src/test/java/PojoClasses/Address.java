package PojoClasses;

import lombok.Data;

/**
 * @author navpreetsingh on 13/05/19
 * @project RestApiAutomation
 */

// Lombok annotation to generate getters,setters,toString methods on code compilation
@Data
public class Address {
    private String postcode;
    private String country;
    private String cityName;
    private String places;
}
