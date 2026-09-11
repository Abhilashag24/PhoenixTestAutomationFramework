package com.api.test;

import static org.hamcrest.Matchers.*;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;

import static com.api.utils.AuthTokenProvider.*;
import static com.api.utils.ConfigManager.*;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import static io.restassured.RestAssured.*;

public class UserDetailsAPITest {
	
	@Test
	public void userDetailsAPITest() {
		
		 
		
		Header Authheader= new Header("Authorization",getToken(FD));
		given()
				.baseUri(getProperty("BASE_URI"))
				.and()
		.header(Authheader)
		.and()
		.accept(ContentType.JSON)
		.log().uri()
		.log().method()
		.log().body()
		.log().headers()
		.when()
		.get("/userdetails")
		.then()
		.statusCode(200)
		.log().all()
		.time(lessThan(2000L))
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsSchema.json"));
				
	}

}
