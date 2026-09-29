package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.request.model.UserCredentials;

import io.restassured.response.Response;

public class AuthService {
	
	// It is going to hold the APIs that belong to Auth
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
	
	private static final String LOGIN_ENDPOINT = "/login";
	
	public Response login(Object userCredentials) {
		LOGGER.info("Making log in request for the payload {}" ,((UserCredentials)userCredentials).username());
		return given().spec(requestSpec(userCredentials)).when().post(LOGIN_ENDPOINT);
		
	}

}
