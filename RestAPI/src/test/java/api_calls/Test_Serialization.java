package api_calls;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import files.Payload;
import io.restassured.RestAssured;
import pojo.AddPlace;
import pojo.Location;

public class Test_Serialization {
	
	public static void main(String []args) {
		
		AddPlace p=new AddPlace();
		p.setAccuracy(50);
		p.setName("Rahul Shetty Academy");
		p.setPhone_number("(+91) 983 893 3937");
		p.setAddress("29,side layout,cohen 09");
		p.setWebsite("http://rahulshettyacademy.com");
		p.setLanguage("French-IN");
		
		List<String>myList=new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		p.setTypes(myList);
		
		Location l=new Location();
		l.setLat(-38.383494);
		l.setLng(33.427362);
		p.setLocation(l);
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(p).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(response);
		
	}
}
