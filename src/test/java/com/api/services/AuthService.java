package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.dataproviders.api.bean.UserBean;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthService {
	
	// It is going to hold the APIs that belong to Auth
	private static final Logger LOGGER = LogManager.getLogger(AuthService.class);
	
	private static final String LOGIN_ENDPOINT = "/login";
	
	@Step("Perform login request with the valid user credentials")
	public Response login(Object userCredentials) {
		LOGGER.info("Making login request for the payload {}" ,((UserBean)userCredentials).getUsername());
		
		return given().spec(requestSpec(userCredentials)).when().post(LOGIN_ENDPOINT);
		
	}

}
