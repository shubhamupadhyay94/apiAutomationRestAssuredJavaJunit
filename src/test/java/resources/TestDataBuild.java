package resources;

import pojo.GoogleAPI;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

public class TestDataBuild {

    public GoogleAPI googleAPI(){
       GoogleAPI googleAPI = new GoogleAPI();

        Location location = new Location();
        location.setLat(-40.383494);
        location.setLng(50.427362);

        googleAPI.setLocation(location);

        googleAPI.setAccuracy(50);
        googleAPI.setName("Reliance Mart");
        googleAPI.setPhoneNo("(+91) 983 893 3937");
        googleAPI.setAddress("Shop no -45, Shanti plaza, near Mira Road Station, Mira Road Thane");

        List<String> arrayList = new ArrayList<>();
        arrayList.add("Shanti Plaza");
        arrayList.add("Shanti Nagar");
        googleAPI.setType(arrayList);

        googleAPI.setWebsite("https://sso.teachable.com/secure/9521/identity/login");
        googleAPI.setLanguage("French-IN");
        return googleAPI;

    }

    public GoogleAPI dynamicGoogleAPIPayLoad(String name, String language , String address){
     googleAPI();
     googleAPI().setLanguage(language);
     googleAPI().setAddress(address);
     googleAPI().setName(name);
     return googleAPI();
    }
}
