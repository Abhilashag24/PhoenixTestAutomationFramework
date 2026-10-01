package com.api.utils;

import static com.api.utils.ConfigManager.getProperty;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.request.model.UserCredentials;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;

public final class AuthTokenProvider {

	private static final Logger LOGGER = LogManager.getLogger(AuthTokenProvider.class);

	private static Map<Role,String> tokencache = new ConcurrentHashMap<Role,String>();
	
	private AuthTokenProvider() {

	}
	@Step("Getting the Auth token for the role")
	public static String getToken(Role role) {
		LOGGER.info("Checking if the token for {} is present in the cache",role);
		if(tokencache.containsKey(role)) {
			LOGGER.info("Token found for {}",role);
			return tokencache.get(role);
		}
		LOGGER.info("Token not found ,making the login request for the role {}",role);

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

		LOGGER.info("Token cached for future requests");

		tokencache.put(role, token);
		
		return token;
	}

 

}
