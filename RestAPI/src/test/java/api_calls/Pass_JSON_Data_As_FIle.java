package api_calls;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Payload;
import files.ReusebleMathods;

public class Pass_JSON_Data_As_FIle {

	public static void main(String[] args) throws IOException {
		
		//Add place
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
		.body(new String(Files.readAllBytes(Paths.get("F:\\Selenium_2026\\workspace_Cucumber\\RestAPI\\src\\test\\java\\files\\addPlace.json")))).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP"))
		.header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		
		System.out.println(response);
		
		/*JsonPath js=new JsonPath(response);
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
	*/
	}

}
