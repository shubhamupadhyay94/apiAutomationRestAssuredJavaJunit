package apitest;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payload.Payload;
import utilities.Utilities;

import static org.testng.Assert.*;
import static io.restassured.RestAssured.*;

public class PlaceAPITest {

        //validate add api

        //given all input details
        //when submit the api resource ,http method
        //then validate the response
    String placeId;

    @Test(priority = 0)
    public void addPlace(){
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response=  given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(Payload.addPlace())
                .when().log().all().post("/maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        System.out.println("Response is "+response);

        //takes string as input and expose json value in key and value format
        JsonPath jsonPath =  Utilities.rawStringToJson(response);
        placeId = jsonPath.getString("place_id");

        System.out.println("placeId is "+placeId);
        Assert.assertTrue(placeId!=null && placeId.length()>0);
    }

    @Test(priority = 1)
    public void getPlace(){

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        //get place api+
        String getResponse= given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)
                .when().log().all().get("/maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().asPrettyString();

        System.out.println("Get place api " + getResponse);

        JsonPath jsonPath1 = Utilities.rawStringToJson(getResponse);
        String address= jsonPath1.getString("address");
        String name= jsonPath1.getString("name");
        assertEquals(name,"CCD", "Added place name is not same");
        assertEquals(address,"56, Bhayander", "Added place name is not same");
    }

    @Test(priority = 2)
    public void deletePlace(){

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        //delete place

        String response = given().log().all().header("content-type","application/json")
                .queryParam("key","qaclick123")
                .body("{\n" +
                        "    \"place_id\":\""+placeId+"\"\n" +
                        "}\n")
                .when().log().all().delete("/maps/api/place/delete/json")
                .then().log().all().assertThat().statusCode(200).extract().asString();

        JsonPath jsonPath = Utilities.rawStringToJson(response);
        assertEquals("OK",jsonPath.get("status"),"place is deleted successfully");

    }

}
