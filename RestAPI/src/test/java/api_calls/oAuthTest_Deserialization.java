package api_calls;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.Mobile;
import pojo.WebAutomation;


public class oAuthTest_Deserialization {



public static void main(String[] args) throws InterruptedException {

String response = given() 
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
	            .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
	            .formParams("grant_type", "client_credentials")
	            .formParams("scope", "trust")
	            .when().log().all()
	            .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

System.out.println(response);

JsonPath jsonPath = new JsonPath(response);

    String accessToken = jsonPath.getString("access_token");

    System.out.println(accessToken);

GetCourse gc=given()
	.queryParams("access_token", accessToken)
	.when()
    .get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

System.out.println(gc.getLinkedIn());
System.out.println(gc.getInstructor());
List<Api> apiCourses=gc.getCourse().getApi();
System.out.println(apiCourses.get(1).getCourseTitle());

for(int i=0;i<apiCourses.size();i++) {
	if(apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
		System.out.println(apiCourses.get(i).getPrice());
	}
	
}


String []arr_Courses= {"Selenium Webdriver Java","Cypress","Protractor"};
boolean blnFound=true;
List<WebAutomation>webAutomation =gc.getCourse().getWebAutomation();
for(int i=0;i<webAutomation.size();i++) {
	 String str_Title=webAutomation.get(i).getCourseTitle();
	if(!str_Title.equalsIgnoreCase(arr_Courses[i])) {
		blnFound=false;
	}
}
if(blnFound) {
	System.out.println("All Course Names Are Matched");
}
else {
	System.out.println("All Course Names Are Not Matched");
}
	

List<Mobile> mobile=gc.getCourse().getMobile();
for(int i=0;i<mobile.size();i++) {
	System.out.println(mobile.get(i).getCourseTitle()+":"+mobile.get(i).getPrice());
	
}

}




}

