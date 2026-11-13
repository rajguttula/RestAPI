package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DaynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle) {
		
		RestAssured.baseURI="http://216.10.245.166";
		String response=given().log().all().header("Content-Type","application/json")
		.body(Payload.addBook(isbn,aisle))
		.when().post("/Library/Addbook.php")
		.then().assertThat().log().all().statusCode(200)
		.extract().response().asString();
		JsonPath js=ReusebleMathods.rawToJon(response);
		String id=js.get("ID");
		System.out.println(id);
	}
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData(){
		
		 /*Object [][]object= {
				{"xyz","002"},
				{"abc","003"}
		};
		 
		 return object;*/
		return new Object[][] {{"xyz","002"},{"abc","003"}};
		
		}
}
