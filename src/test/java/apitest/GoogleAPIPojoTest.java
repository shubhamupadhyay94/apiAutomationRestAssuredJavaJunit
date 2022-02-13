package apitest;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Payload;
import pojo.GoogleAPI;
import pojo.GoogleAPIResponse;
import pojo.Location;
import utilities.Utilities;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class GoogleAPIPojoTest {

    GoogleAPI googleAPI=null;
    GoogleAPIResponse googleAPIResponse=null;
    @BeforeClass
    public void getAddPlaceObject(){
        googleAPI = new GoogleAPI();

        Location location = new Location();
        location.setLat(-40.383494);
        location.setLng(50.427362);

        googleAPI.setLocation(location);

        googleAPI.setAccuracy(50);
        googleAPI.setName("Reliance Mart");
        googleAPI.setPhoneNo("(+91) 983 893 3937");
        googleAPI.setAddress(" Shop no -45, Shanti plaza, near Mira Road Station, Mira Road Thane");

        List<String> arrayList = new ArrayList<>();
        arrayList.add("Shanti Plaza");
        arrayList.add("Shanti Nagar");
        arrayList.add("Hutcase Mira Road East");
        googleAPI.setType(arrayList);

        googleAPI.setWebsite("https://sso.teachable.com/secure/9521/identity/login");
        googleAPI.setLanguage("Marathi-IN");

        RestAssured.baseURI = "https://rahulshettyacademy.com";

    }

    @Test(priority = 0)
    public void addNewPlaceByPojo(){
        Response response=  given().log().all().queryParam("key","qaclick123")
              //  header("Content-Type","application/json")
          //      expect().defaultParser(Parser.JSON)
                .body(googleAPI)
                .when().log().all().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response();

        System.out.println("Response is ---------------->"+response);

        //takes string as input and expose json value in key and value format
//        JsonPath jsonPath =  Utilities.rawStringToJson(response.toString());
//      String  placeId = jsonPath.getString("place_id");

        String res= response.asPrettyString();
        System.out.println("placeId is "+res);
     //   Assert.assertTrue(placeId!=null && placeId.length()>0);
    }
}
