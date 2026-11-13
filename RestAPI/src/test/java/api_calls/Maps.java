package api_calls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;
import files.ReusebleMathods;

public class Maps {

	public static void main(String[] args) {
		
		//Add place
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(Payload.addPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js=new JsonPath(response);
		String placeId=js.getString("place_id");
		System.out.println(placeId);
		
		//Update place
		String new_Address="70 Summer Road ,USA";
		String updateRespone=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\": \""+new_Address+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg",equalTo("Address successfully updated")).extract().response().asString();
		System.out.println("Update Response : "+updateRespone);
		
		//Get
		String getPlaceResponse=given().log().all().queryParam("key","qaclick123").queryParam("place_id",placeId)
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().statusCode(200).extract().response().asString();
		JsonPath js1=ReusebleMathods.rawToJon(getPlaceResponse);
		String actualAddress=js1.getString("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, new_Address);
	
	}

}
