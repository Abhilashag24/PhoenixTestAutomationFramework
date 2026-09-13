package com.api.test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;
import com.api.utils.SpecUtil;

public class FDLoginAPITest {
	
	@Test
	
	public void loginAPITest() {
			
		UserCredentials userCredentials =  new UserCredentials("iamfd", "password");	
		
		given()
			.spec(SpecUtil.requestSpec(userCredentials)) 
			.when()
			.post("/login")
			.then().spec(SpecUtil.responseSpec_OK())
			.body("message", equalTo("Success")) 
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
