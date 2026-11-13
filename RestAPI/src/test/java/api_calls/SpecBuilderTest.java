package api_calls;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.List;

import files.Payload;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class SpecBuilderTest {
	
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
		
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key","qaclick123")
		.setContentType(ContentType.JSON).build();
		
		ResponseSpecification res=new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		RequestSpecification reqGiven=given().log().all().spec(req);
		
		
		Response response=reqGiven
		.body(p).when().post("maps/api/place/add/json")
		.then().assertThat().spec(res).extract().response();
		
		System.out.println(response.asString());
		
	}
}
