# RestApiAutomation
This project demonstrates api automation via Rest-Assured library + Allure Reporting. Wiremock is used to mocking api response. 
Also, Lombok is used to remove need of writing the boilerplate code.

# Getting Started :
Below mentioned instructions will get you a copy of the project up and running on your local machine for testing purposes.

# Prerequisites :
IntelliJ Idea                                                                                                                                                                                  
Java 8                                                                                                                                                                  
TestNG                      
Allure                                                     

Please follow the below mentioned steps to get a working repo :                                                       
Clone the repository via ssh/http using URL --> **https://github.com/navsinghoberoi/RestApiAutomation**

# Running the tests :                                                                                             
To run the testcases, execute following command :                                
**mvn clean test -DsuiteXmlFile=TestNG.xml | tee src/test/java/TestLogs/logging.log | grep -A10 "DEBUG curl"**


# Generating allure report :
Run the following commands :

**allure generate allure-results**                                                      
**NOTE** : Copy the history folder from your previous allure-report folder inside your current allure-results folder in order to see History trend in the report.                                                           
**allure serve allure-results**

# Built With :
Java -- Programming Language            
Maven -- Build automation tool                    
Git -- Distributed version-control system                               
TestNG -- Testing framework                                      
WireMock -- Library for mocking APIs                                           
Lombok -- Library that generates getter, setters method at compile time without having the need to write all boilerplate code
Allure -- Reporting                                             
Curl-Logger -- Library used to log curl requests

# Author :
Navpreet Singh

# Add test cases :
Remember below mentioned points before creating new api tests --> 
a) Add new GET method tests -->                                                                                                       
To add tests, go to RestApiAutomation/src/test/java/Tests/ZipCodeApi_GetRequestTests folder and create a new file                                                
b) Add new POST method tests -->                                                                                                        
To add tests, go to RestApiAutomation/src/test/java/Tests/ZipCodeApi_PostRequestTests folder and create a new file                          
