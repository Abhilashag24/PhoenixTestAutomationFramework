package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.api.constants.Role;
import com.api.request.model.UserCredentials;

import io.restassured.http.ContentType;

public final class AuthTokenProvider {

	private static Map<Role,String> tokencache = new ConcurrentHashMap<Role,String>();
	
	private AuthTokenProvider() {

	}

	public static String getToken(Role role) {
		if(tokencache.containsKey(role)) {
			return tokencache.get(role);
		}

		UserCredentials userCredentials = null;

		switch (role) {
		case FD -> userCredentials = new UserCredentials("iamfd", "password");
		case SUP -> userCredentials = new UserCredentials("iamsup", "password");
		case ENG -> userCredentials = new UserCredentials("iameng", "password");
		case QC -> userCredentials = new UserCredentials("iamqc", "password");

		}
		String token = given().baseUri(getProperty("BASE_URI")).and().contentType(ContentType.JSON).and()
				.accept(ContentType.JSON).and().body(userCredentials).log().uri().log().headers().log().method().when()
				.post("/login").then().log().ifValidationFails().statusCode(200).and().body("message", equalTo("Success"))
				.time(lessThan(2000L)).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json")).extract().response()
				.jsonPath().getString("data.token");

		tokencache.put(role, token);
		
		return token;
	}

 

}
