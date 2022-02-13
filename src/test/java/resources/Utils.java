package resources;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.io.*;
import java.util.Properties;

public class Utils {

    public static RequestSpecification requestSpecification = null;
    public RequestSpecification requestSpecification() {

        try {
            if(requestSpecification ==null) {
                PrintStream printStream = new PrintStream(new FileOutputStream(".\\src\\test\\logfile\\log.txt"));
              RestAssured.baseURI = readConfig().getProperty("url");
                requestSpecification = new RequestSpecBuilder().setBaseUri(RestAssured.baseURI).addQueryParam("key", "qaclick123")
                        .addFilter(RequestLoggingFilter.logRequestTo(printStream)).
                                addFilter(ResponseLoggingFilter.logResponseTo(printStream)).

                                setContentType(ContentType.JSON).build();
                return requestSpecification;
            }
           // return requestSpecification;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestSpecification;
    }

    public ResponseSpecification responseSpecification(){

        ResponseSpecification responseSpecification= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
        return responseSpecification;
    }

    public static Properties readConfig(){

        Properties properties = new Properties();
        try {
            FileInputStream fis = new FileInputStream(".\\src\\test\\java\\resources\\config.properties");
            properties.load(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

}
