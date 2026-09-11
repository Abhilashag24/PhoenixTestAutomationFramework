package com.api.test;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.utils.AuthTokenProvider;
import static com.api.utils.ConfigManager.*;

import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class CountAPITest {

	
	
	@Test
	public void verifyCountAPIResponse() {
		given()
			.baseUri(getProperty("BASE_URI"))
			.and()
			.header("Authorization",AuthTokenProvider.getToken(Role.FD))
			.when()
			.get("/dashboard/count")
			.then()
			.log().all()
			.statusCode(200)
			.body("message", equalTo("Success"))
			.and()
			.time(lessThan(1000L))
			.body("data", notNullValue())
			.and()
			.body("data.size()",equalTo(3))
			.body("data.count", everyItem(greaterThanOrEqualTo(0)))
			.body("data.label",everyItem(not(blankOrNullString())))
			.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json"))
			.body("data.key",containsInAnyOrder("pending_for_delivery","created_today","pending_fst_assignment"));
	}
	
	
	@Test
	public void countAPITest_MissingAuthToken() {
		given()
		.baseUri(getProperty("BASE_URI"))
		.when()
		.get("/dashboard/count")
		.then()
		.log().all()
		.statusCode(401);
	}
}
