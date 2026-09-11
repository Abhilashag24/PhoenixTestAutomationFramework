package com.api.test;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import org.testng.annotations.Test;

import com.api.pojo.UserCredentials;

import io.restassured.http.ContentType;

public class FDLoginAPITest {
	
	@Test
	
	public void loginAPITest() {
			
		UserCredentials userCredentials =  new UserCredentials("iamfd", "password");
		
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.contentType(ContentType.JSON)
			.and()
			.accept(ContentType.JSON)
			.and()
			.body(userCredentials)
			.log().uri()
			.log().headers()
			.log().method()
			.when()
			.post("/login")
			.then()
			.log().all()
			.statusCode(200)
			.and()
			.body("message", equalTo("Success"))
			.time(lessThan(2000L))
			.and()
			.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
