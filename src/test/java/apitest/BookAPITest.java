package apitest;

import io.cucumber.java.en.Given;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import payload.Payload;
import utilities.Utilities;

import java.util.Locale;

import static io.restassured.RestAssured.*;

public class BookAPITest {


    String bookId;

    @BeforeClass

    public void baseUrl(){

        RestAssured.baseURI="http://216.10.245.166";
    }

    @Test(priority = 0)
    public void addBook(){
        String bookName= RandomStringUtils.randomAlphabetic(8).toUpperCase(Locale.ROOT);
        String authorName=RandomStringUtils.randomAlphabetic(8).toUpperCase(Locale.ROOT);
        String uniqueBookId= RandomStringUtils.randomAlphanumeric(3).toUpperCase(Locale.ROOT);

        String response =given().log().all().header("Content-Type","application/json").body(Payload.addBook(bookName,authorName,uniqueBookId))
                .when().log().all().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200).extract().asPrettyString();

        JsonPath jsonPath = Utilities.rawStringToJson(response);

        bookId=jsonPath.getString("ID");
        Assert.assertTrue(jsonPath.getString("ID").contains(uniqueBookId));
        Assert.assertEquals(jsonPath.getString("Msg"),"successfully added");

        System.out.println("Book id --------------"+bookId);
    }


    @Test(priority = 1)
    public void getBook(){
        String response = given().log().all().header("content-type","application/json")
                .when().log().all().get("/Library/GetBook.php?ID="+bookId+"")
                .then().assertThat().statusCode(200).extract().asString();

        JsonPath jsonPath = Utilities.rawStringToJson(response);

        System.out.println(jsonPath.getString("book_name"));
        System.out.println(jsonPath.getString("isbn"));
        System.out.println( jsonPath.getString("author"));
    }



}
