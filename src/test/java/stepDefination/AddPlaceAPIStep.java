package stepDefination;

import constants.APIResourceConstant;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import payload.Payload;
import pojo.GoogleAPI;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import utilities.Utilities;
import java.util.Locale;

import static io.restassured.RestAssured.given;


public class AddPlaceAPIStep extends Utils {

    GoogleAPI  googleAPI =null;
    static Response response=null;

    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;
    public static String placeId;

    TestDataBuild testDataBuild = new TestDataBuild();

    @Given("I create a add place payload")
    public void i_create_a_add_place_payload() {
             //responseSpecification= new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        requestSpecification= given().log().all().spec(requestSpecification()).body(Payload.addPlace() ); //testDataBuild.googleAPI()
    }


    @Given("I create a add place payload with {string} and {string} and {string}")
    public void i_create_a_add_place_payload_with_and_and(String name, String language, String address) {
        requestSpecification= given().log().all().spec(requestSpecification()).body(Payload.dynamicAddPlacePayload(name, language, address));
        //testDataBuild.dynamicGoogleAPIPayLoad(name,language,address)
    }

    @When("user call {string} with post http request")
    public void user_call_with_post_http_request(String resource) {
         response = requestSpecification.when().log().all().post(APIResources.valueOf(resource).getResource()).  //APIResourceConstant.ADD_PLACE_API use enum to pass resource
                then().log().all().spec(responseSpecification()).extract().response();

    }

    @When("user call {string} with {string} http request")
    public Response user_call_with_http_request(String resource, String requestType) {

        if(requestType.toUpperCase(Locale.ROOT).equals("POST")) {
            response = requestSpecification.when().log().all().post(APIResources.valueOf(resource).getResource()).  //APIResourceConstant.ADD_PLACE_API use enum to pass resource
                    then().log().all().spec(responseSpecification()).extract().response();
        } else if (requestType.toUpperCase(Locale.ROOT).equals("GET")){
            response = requestSpecification.when().log().all().get(APIResources.valueOf(resource).getResource()).  //APIResourceConstant.ADD_PLACE_API use enum to pass resource
                    then().log().all().spec(responseSpecification()).extract().response();
        }
        return response;

    }

    @Then("the addPlaceApi call got success with status code {int}")
    public void the_add_place_api_call_got_success_with_status_code(Integer statusCode) {
    Assert.assertEquals(new Integer(response.getStatusCode()),statusCode);
    }


    String name =null;
    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String value) {
        //takes string as input and expose json value in key and value format
        JsonPath jsonPath =  Utilities.rawStringToJson(response.asPrettyString());
        Assert.assertEquals(jsonPath.getString(key).toUpperCase(Locale.ROOT),value.toUpperCase(Locale.ROOT));
        placeId =jsonPath.getString("place_id");
       name=jsonPath.getString("name");
    }

    @Then("verify getplaceId is created {string}")
    public void verify_getplace_id_is_created(String placeName) {
      requestSpecification=  given().spec(requestSpecification()).queryParam("place_id",placeId);
      response=  user_call_with_http_request( "getPlaceAPI",  "GET");

      JsonPath jsonPath =  Utilities.rawStringToJson(response.asPrettyString());
       name=jsonPath.getString("name");
        Assert.assertEquals(name,placeName);
    }


    @Then("verify {string} is created successfully with {string}")
    public void verify_is_created_successfully_with(String resource, String placeName) {
        requestSpecification=  given().spec(requestSpecification()).queryParam("place_id",placeId);
        response=  user_call_with_http_request( resource,  "GET");

        JsonPath jsonPath =  Utilities.rawStringToJson(response.asPrettyString());
        name=jsonPath.getString("name");
        Assert.assertEquals(name,placeName);
    }

    @Given("create delete payload")
    public void create_delete_payload() {
        requestSpecification= given().log().all().spec(requestSpecification()).body(Payload.deletePlaceId(placeId));
//        response = requestSpecification.when().log().all().post(APIResources.valueOf("deletePlaceAPI").getResource()).  //APIResourceConstant.ADD_PLACE_API use enum to pass resource
//                then().log().all().spec(responseSpecification()).extract().response();
    }
}
