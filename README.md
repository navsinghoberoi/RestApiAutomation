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

Please clone the repo using below mentioned link :                                                       
**https://github.com/navsinghoberoi/RestApiAutomation**

# Running the tests :                                                                                             
To run the testcases, execute following command :                                
**mvn clean test -DsuiteXmlFile=TestNG.xml | tee src/test/java/TestLogs/logging.log | grep -A10 "DEBUG curl"**


# Generating allure report :
Run the following commands :

1. **allure generate allure-results**                                                      
**NOTE** : Copy the history folder from your previous allure-report folder inside your current allure-results folder in order to see History trend in the report.                                                           

2. **allure serve allure-results**

# Built With :
Rest Assured                                
Java                                  
Maven                                       
Git                                       
TestNG                                        
WireMock                                                
Lombok                                            
Allure                                            
Log4j                                                     
Curl-Logger                             

# Author :
Navpreet Singh

# Add test cases :
Remember below mentioned points before creating new api tests --> 
a) Add new GET method tests -->                                                                                                       
To add tests, go to RestApiAutomation/src/test/java/Tests/ZipCodeApi_GetRequestTests folder and create a new file                                                
b) Add new POST method tests -->                                                                                                        
To add tests, go to RestApiAutomation/src/test/java/Tests/ZipCodeApi_PostRequestTests folder and create a new file                      

# Sample generated report :

![allure_report_page1](https://user-images.githubusercontent.com/21955275/86487677-5baed400-bd7c-11ea-9f64-38a06dff807b.png)

![allure_report_page2](https://user-images.githubusercontent.com/21955275/86487687-62d5e200-bd7c-11ea-930e-2ccbf76f8cc3.png)

