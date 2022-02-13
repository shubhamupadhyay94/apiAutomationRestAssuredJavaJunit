package hook;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import stepDefination.AddPlaceAPIStep;

public class Hooks {

    Response response;

    @Before("@deletePlace")
    public void beforeDeletePlaceScenario(){
        if(AddPlaceAPIStep.placeId==null) {
            AddPlaceAPIStep addPlaceAPIStep = new AddPlaceAPIStep();
            addPlaceAPIStep.i_create_a_add_place_payload();
            response = addPlaceAPIStep.user_call_with_http_request("addPlaceApi", "POST");
            JsonPath jsonPath = new JsonPath(response.asString());
            AddPlaceAPIStep.placeId = jsonPath.getString("place_id");
            System.out.println("-----------------------Before hook-----------------------" + AddPlaceAPIStep.placeId);
        }
    }

    @Before()
    public void before(){
        System.out.println("==================Before hook====================================================================");
    }

    @After
    public void after(){
        System.out.println("==================After hook====================================================================");
    }



}
