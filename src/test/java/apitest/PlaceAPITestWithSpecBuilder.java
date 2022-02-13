package apitest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import payload.Payload;
import utilities.Utilities;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.testng.Assert.assertEquals;

public class PlaceAPITestWithSpecBuilder {

        //validate add api

        //given all input details
        //when submit the api resource ,http method
        //then validate the response
    String placeId;

    RequestSpecification req;
    ResponseSpecification responseSpecification;

    @BeforeTest
    public void basicSetup(){

        //Benefit of using spec builder is all common req and response code will bee written once and can be used across test.
        // In-case in future there is any change in req and res resource you have to uodate it at only once place.

        RestAssured.baseURI = "https://rahulshettyacademy.com";
         req= new RequestSpecBuilder().setBaseUri(RestAssured.baseURI).addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON).build();
         responseSpecification= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
    }

    @Test(priority = 0)
    public void addPlace(){

        RequestSpecification requestSpecification= given().log().all().spec(req).body(Payload.addPlace());
        Response res = requestSpecification.when().log().all().post("/maps/api/place/add/json").
                then().log().all().spec(responseSpecification).extract().response();

        //takes string as input and expose json value in key and value format
        JsonPath jsonPath =  Utilities.rawStringToJson(res.asString());
        placeId = jsonPath.getString("place_id");
        Assert.assertTrue(placeId!=null && placeId.length()>0);
    }


    @Test(priority = 1)
    public void getPlace(){
        RequestSpecification requestSpecification= given().log().all().spec(req).queryParam("place_id",placeId);
        Response res = requestSpecification.when().log().all().get("/maps/api/place/get/json").
                then().log().all().spec(responseSpecification).extract().response();

        JsonPath jsonPath1 = Utilities.rawStringToJson(res.asString());

        String address= jsonPath1.getString("address");
        String name= jsonPath1.getString("name");
        assertEquals(name,"CCD", "Added place name is not same");
        assertEquals(address,"56, Bhayander", "Added place name is not same");
    }

    @Test(priority = 2)
    public void deletePlace(){
        RequestSpecification requestSpecification= given().log().all().spec(req).body("{\n" +
                "    \"place_id\":\""+placeId+"\"\n" +
                "}\n");
        Response response = requestSpecification.when().log().all().delete("/maps/api/place/delete/json").
                then().log().all().spec(responseSpecification).extract().response();

        JsonPath jsonPath = Utilities.rawStringToJson(response.asString());
        assertEquals("OK",jsonPath.get("status"),"place is deleted successfully");

    }

}
