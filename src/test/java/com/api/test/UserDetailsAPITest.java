package com.api.test;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import com.api.utils.SpecUtil;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() {
		
		given()
		.spec(SpecUtil.requestSpecWithAuthToken(FD))
		.when()
		.get("/userdetails")
		.then()
		.spec(SpecUtil.responseSpec_OK())
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsSchema.json"));
				
	}

}
