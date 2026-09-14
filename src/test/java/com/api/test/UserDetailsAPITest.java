package com.api.test;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

public class UserDetailsAPITest {
	
	@Test(description = "Verifying if userDetails API response is shown correctly", groups = { "api", "regression", "smoke" })

	public void userDetailsAPITest() {
		
		given()
		.spec(requestSpecWithAuthToken(FD))
		.when()
		.get("/userdetails")
		.then()
		.spec(responseSpec_OK())
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsSchema.json"));
				
	}

}
