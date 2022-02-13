package apitest;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;
import payload.Payload;
import utilities.Utilities;
import static org.testng.Assert.*;

public class CourseAPITest {


    @Test
    public void printNoOfCourses(){

        JsonPath jsonPath = Utilities.rawStringToJson(Payload.getCourse());
        int courseSize =jsonPath.getList("courses").size();
        Assert.assertEquals(courseSize,3, "No of courses is not matching");
      int purchaseAmount=  jsonPath.getInt("dashboard.purchaseAmount");
        assertEquals(jsonPath.getInt("dashboard.purchaseAmount"), 910, "Purchase amount is not matching");
        System.out.println(jsonPath.getString("courses[1].title"));

        int sum=0;
        for(int i =0;i<courseSize;i++){
            System.out.println(jsonPath.getString("courses["+i+"].title"));
            System.out.println(jsonPath.getString("courses["+i+"].price"));

            //get total amount by multiplying each copies with price
            sum = sum+(jsonPath.getInt("courses["+i+"].price")*jsonPath.getInt("courses["+i+"].copies"));

        }
        assertEquals(sum,purchaseAmount);

        //get sold copies of RPA only
        for(int i =0;i<courseSize;i++){

            if(jsonPath.getString("courses["+i+"].title").equals("RPA")){
                System.out.println("RPA copies sold - "+jsonPath.getString("courses["+i+"].copies"));
                break;
            }

        }
    }

}
