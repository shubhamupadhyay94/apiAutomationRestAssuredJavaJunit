package apitest;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import payload.Payload;
import utilities.Utilities;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static io.restassured.RestAssured.*;

public class JIRAAPITest {

    String getSessionId= null;
    String response=null;
    String issueId=null;
    String commentId=null;

    SessionFilter sessionFilter =null;

    List<String> addComment=new ArrayList<>();
    //create a new issue
    //create a comment for same
    //update the comment

    @BeforeTest
    public void setJiraBaseURI(){
        RestAssured.baseURI="http://localhost:8080";
    }

    @BeforeClass
    public void setJiraBasicConfig(){
        //create a session filter object and store session id and details into it

        sessionFilter = new SessionFilter();
         response =given().log().all().header("content-type","application/json").body("{ \"username\": \"shubhamupadhyaysdet\", \"password\": \"Technical@21\" }")
               .filter(sessionFilter).when().log().all().post("/rest/auth/1/session")
                .then().log().all().assertThat().statusCode(200).extract().asPrettyString();

        JsonPath jsonPath = Utilities.rawStringToJson(response);
        getSessionId=jsonPath.getString("session.value");

        baseURI="";
        given().header("content-type","application/json").body("json").filter(sessionFilter).when().post("/auth/1/session")
                .then().assertThat().statusCode(200).extract().asString();

    }


    @Test(priority = 0)
    public void createJiraBug(){
        response =given().log().all().header("content-type","application/json").
                body(Payload.createIssue(RandomStringUtils.randomAlphabetic(8),RandomStringUtils.randomAlphabetic(8)))
                .filter(sessionFilter).when().post("/rest/api/2/issue")
                .then().log().all().assertThat().statusCode(201).extract().asPrettyString();

        JsonPath jsonPath = Utilities.rawStringToJson(response);
        issueId=jsonPath.getString("id");

    }

    @Test(priority = 1)
    public void createJiraComment()
    {
       response = given().log().all().pathParam("issueId",issueId).header("content-type","application/json")
              .body(Payload.createComment(RandomStringUtils.randomAlphabetic(20)))
                .filter(sessionFilter).when().log().all().post("/rest/api/2/issue/{issueId}/comment")
                .then().assertThat().statusCode(201).extract().asPrettyString();
        JsonPath jsonPath = Utilities.rawStringToJson(response);
        commentId=jsonPath.getString("id");
        addComment.add(jsonPath.getString("body"));

    }

    //update comment and verify the comment
    @Test(priority = 2)
    public void updateJiraComment()
    {
        String randomString = RandomStringUtils.randomAlphabetic(10);
        String comment = "update comment for issue "+ randomString;
         response =given().log().all().header("content-type","application/json")
            .pathParam("issueId",issueId).pathParam("commentId",commentId)
            .body(Payload.updateComment(randomString))
            .filter(sessionFilter)
            .when().log().all().put("/rest/api/2/issue/{issueId}/comment/{commentId}")
            .then().log().all().assertThat().statusCode(200).extract().asPrettyString();

        JsonPath jsonPath = Utilities.rawStringToJson(response);
        String updateComment=jsonPath.getString("body");
        addComment.add(updateComment);
        Assert.assertEquals(updateComment,comment);

    }

    //add attachment for jira bug as comment , i don't have permission to update attachment getting 403, forbidden client error

    @Test(priority = 3)
    public void addAttachmentForBug(){
        given().log().all().header("X-Atlassian-Token","no-check").filter(sessionFilter).pathParam("issueId",issueId)
                .header("content-type","multipart/form-data")
                .multiPart("file",new File("C:\\Must Read Book.txt"))
                .when().post(" /rest/api/2/issue/{issueId}/attachments")
                .then().log().all().assertThat().statusCode(403);

    }

    //create multiple comment and verify all added comment

    @Test(priority = 4,invocationCount = 4)
    public void addMultipleComment(){
         createJiraComment();

    }

    @Test(priority = 5)
    public void fetchAllComment(){

        String response = given().pathParam("issueId",issueId).header("content-type","application/json")
                .filter(sessionFilter)
                .when().get("/rest/api/2/issue/{issueId}/comment")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath jsonPath = Utilities.rawStringToJson(response);
        int count=jsonPath.getInt("comments.size()");
        for(int i=0;i<count;i++){
            System.out.println(jsonPath.getString("comments["+i+"].body"));
            Assert.assertTrue(addComment.contains(jsonPath.getString("comments["+i+"].body")));
        }

    }

}
