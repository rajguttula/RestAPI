package eCommerce;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import eCommerce_POJO.LoginRequest;
import eCommerce_POJO.LoginResponse;
import eCommerce_POJO.OrderDetails;
import eCommerce_POJO.Orders;

public class ECommerceAPITest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").setContentType(ContentType.JSON).build();
		
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setUserEmail("raju253025@gmail.com");
		loginRequest.setUserPassword("Playwright@1234");
		
		RequestSpecification reqLogin= given().log().all().spec(req).body(loginRequest);
		LoginResponse loginResponse=reqLogin.when().log().all().post("/api/ecom/auth/login").then().extract().response().as(LoginResponse.class);
		String token=loginResponse.getToken();
		String userId=loginResponse.getUserId();
		System.out.println(token);
		System.out.println(userId);
		
		//Add Product
		RequestSpecification addProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).build();
		RequestSpecification addproduct=given().log().all().spec(addProductBaseReq).param("productName","Laptop")
		.param("productAddedBy",userId)
		.param("productCategory","fashion").param("productSubCategory","shirts")
		.param("productPrice","25000").param("productDescription", "HP Original")
		.param("productFor","All").multiPart("productImage",new File("F:\\Selenium_2026\\Capture.jpg"));
		
		String addProductResponse=addproduct.when().post("/api/ecom/product/add-product").then().log().all().extract().response().asString();
		JsonPath js=new JsonPath(addProductResponse);
		String product_Id=js.get("productId");
		System.out.println(product_Id);
		
		//Create Order
		RequestSpecification createOrderBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("authorization",token).setContentType(ContentType.JSON).build();
		OrderDetails orderDetails=new OrderDetails();
		orderDetails.setCuntry("India");
		orderDetails.setProductOrderedId(product_Id);
		List<OrderDetails> orderDetailsList=new ArrayList<>();
		orderDetailsList.add(orderDetails);
		Orders orders=new Orders();
		orders.setOrders(orderDetailsList);
		RequestSpecification createOrderReq=given().log().all().spec(createOrderBaseReq).body(orders);
	    String responseOrder=createOrderReq.when().post("api/ecom/order/create-order").then().log().all().extract().response().asString();
	    System.out.println(responseOrder);
	    
	    //Delete Product
	    RequestSpecification deleteProductBaseReq=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addHeader("authorization",token).setContentType(ContentType.JSON).build();
	    
	    RequestSpecification deleteProductReq=given().log().all().spec(deleteProductBaseReq).pathParam("productId",product_Id);
	    
	    String deleteProductRes= deleteProductReq.when().log().all().delete("/api/ecom/product/delete-product/{productId}")
	    		.then().log().all().extract().response().asString();
	    JsonPath js1=new JsonPath(deleteProductRes);
	    String msg=js1.get("messsage");
	    System.out.println(msg);
	    
	}

}
