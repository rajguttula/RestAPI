package api_calls;

import org.testng.Assert;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[]args) {
		JsonPath js=new JsonPath(Payload.coursePrice());
		System.out.println("Print no of courses returned by API : "+js.getInt("courses.size()"));
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		System.out.println("Print purchase amount : "+purchaseAmount);
		System.out.println("Print title of the first course : "+js.getString("courses[0].title"));
		System.out.println("Print title of the first course : "+js.getString("courses[0].title"));
		
		System.out.println("Print All course title and their respective prices");
		int noOfCourses=js.getInt("courses.size()");
		for(int i=0;i<noOfCourses;i++) {
			System.out.println(js.get("courses["+i+"].title")+":"+js.get("courses["+i+"].price")+" ");
		}
		System.out.println("Print no of copies sold by RPA course");
		for(int i=0;i<noOfCourses;i++) {
			String courseTtile=js.get("courses["+i+"].title");
			if(courseTtile.equals("RPA")) {
			System.out.println("RPA Copies Sold:"+js.get("courses["+i+"].copies")+" ");
			break;
			}
		}
		//Verify if sum of all course prices match with purchase amount
		int total=0;
		for(int i=0;i<noOfCourses;i++) {
			int temp=(int)js.get("courses["+i+"].price")*(int)js.get("courses["+i+"].copies");
			total+=temp;
		}
		System.out.println(total);
		if(total==purchaseAmount) {
			Assert.assertEquals(purchaseAmount, total);
		}
		else
		{
			Assert.assertFalse(true);
		}
	}
	
}
