package com.api.test;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.Test;

import static com.api.utils.SpecUtil.*;

public class FDMasterAPIRequestTest {

	@Test(description = "Verifying if Master API is giving correct response", groups = { "api", "regression", "smoke" })
	public void masterAPITest() {
		given().spec(requestSpecWithAuthToken(FD))
				.when().post("/master")
// Whenever you are making post request, default content Type is application/url-formencoded
				.then().spec(responseSpec_OK()).body("message", equalTo("Success"))
				.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"))
				.body("data", notNullValue()).body("data", hasKey("mst_oem")).body("data", hasKey("mst_model"))
				.body("$", hasKey("message")).body("$", hasKey("data")).body("data.mst_oem.size()", equalTo(2))
				.body("data.mst_model.size()", greaterThan(0)).body("data.mst_oem.id", everyItem(notNullValue()))
				.body("data.mst_oem.name", everyItem(notNullValue()));

	}

	@Test (description = "Verifying if Master API is giving correct status for Invalid Token", groups = { "api","negative", "regression", "smoke" })

	public void invalidTokenMasterAPITest() {
		given().spec(requestSpec()).when()
				.post("/master") 
				.then().spec(responseSpec_TEXT(401));
	}

}
