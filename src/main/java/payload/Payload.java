package payload;

public class Payload {

    public static String addPlace(){
        return  "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \"CCD\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \"56, Bhayander\",\n" +
                "  \"types\": [\n" +
                "    \"Navghar Road\",\n" +
                "    \"Thane\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \"French-IN\"\n" +
                "}\n";
    }


    public static String dynamicAddPlacePayload(String name, String language, String address){
        return  "{\n" +
                "  \"location\": {\n" +
                "    \"lat\": -38.383494,\n" +
                "    \"lng\": 33.427362\n" +
                "  },\n" +
                "  \"accuracy\": 50,\n" +
                "  \"name\": \""+name+"\",\n" +
                "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                "  \"address\": \""+address+"\",\n" +
                "  \"types\": [\n" +
                "    \"Navghar Road\",\n" +
                "    \"Thane\"\n" +
                "  ],\n" +
                "  \"website\": \"http://google.com\",\n" +
                "  \"language\": \""+language+"\"\n" +
                "}\n";
    }

    public static  String getCourse(){
        return "{\n" +
                "\"dashboard\": {\n" +
                "\"purchaseAmount\": 910,\n" +
                "\"website\": \"rahulshettyacademy.com\"\n" +
                "},\n" +
                "\"courses\": [\n" +
                "{\n" +
                "\"title\": \"Selenium Python\",\n" +
                "\"price\": 50,\n" +
                "\"copies\": 6\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"Cypress\",\n" +
                "\"price\": 40,\n" +
                "\"copies\": 4\n" +
                "},\n" +
                "{\n" +
                "\"title\": \"RPA\",\n" +
                "\"price\": 45,\n" +
                "\"copies\": 10\n" +
                "}\n" +
                "]\n" +
                "}\n";
    }


    public static String addBook(String name, String authorName, String uniqueBookId){
        return "{\n" +
                "\n" +
                "\"name\":\""+name+"\",\n" +
                "\"isbn\":\""+uniqueBookId+"\",\n" +
                "\"aisle\":\"MO1\",\n" +
                "\"author\":\""+authorName+"\"\n" +
                "}\n";
    }

    public static  String createIssue(String summary, String description){
        return "   \n" +
                "   {\n" +
                "        \"fields\":\n" +
                "        {\n" +
                "        \"project\":\n" +
                "         {\n" +
                "            \"key\": \"RES\"\n" +
                "        },\n" +
                "        \"summary\": \"Login is getting failed due to "+summary+"\",\n" +
                "     \"description\":\"This is your team backlog. Create and estimate new issues and prioritize that "+description+"\",\n" +
                "\t \"issuetype\":\n" +
                "\t {\n" +
                "\t \"name\":\"Bug\"\n" +
                "\t }\n" +
                "     }\n" +
                "}";
    }


    public static  String createComment(String comment){
        return "{\n" +
                "    \"body\": \"After entering right creds also login is getting failed "+comment+"\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}";
    }

    public static  String updateComment(String updateComment){
        return "{\n" +
                "    \"body\": \"update comment for issue "+updateComment+"\",\n" +
                "    \"visibility\": {\n" +
                "        \"type\": \"role\",\n" +
                "        \"value\": \"Administrators\"\n" +
                "    }\n" +
                "}";
    }


    public static String deletePlaceId(String placeId){
        return "{\n" +
                "    \"place_id\":\""+placeId+"\"\n" +
                "}\n";
    }

}
