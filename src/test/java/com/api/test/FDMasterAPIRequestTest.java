package com.api.test;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import com.api.constants.Role;
import static com.api.utils.AuthTokenProvider.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static com.api.utils.ConfigManager.*;

import static io.restassured.RestAssured.*;

public class FDMasterAPIRequestTest {

	@Test
	public void masterAPITest() {
		given().baseUri(getProperty("BASE_URI")).and().contentType("").and().headers("Authorization", getToken(Role.FD))
				.when().post("/master")
// Whenever you are making post request, default content Type is application/url-formencoded
				.then().statusCode(200).log().ifValidationFails().body("message", equalTo("Success"))
				.time(lessThan(1000L))
				.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIresponseSchema.json"))
				.body("data", notNullValue()).body("data", hasKey("mst_oem")).body("data", hasKey("mst_model"))
				.body("$", hasKey("message")).body("$", hasKey("data")).body("data.mst_oem.size()", equalTo(2))
				.body("data.mst_model.size()", greaterThan(0)).body("data.mst_oem.id", everyItem(notNullValue()))
				.body("data.mst_oem.name", everyItem(notNullValue()));

	}

	@Test
	public void invalidTokenMasterAPITest() {
		given().baseUri(getProperty("BASE_URI")).and().contentType("").and().headers("Authorization", "").when()
				.post("/master") 
				.then().statusCode(401).log().all();
	}

}
