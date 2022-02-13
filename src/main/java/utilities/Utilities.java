package utilities;

import io.restassured.path.json.JsonPath;

public class Utilities {

    public static JsonPath rawStringToJson(String s){
        JsonPath jsonPath = new JsonPath(s);
        return jsonPath;
    }


}
