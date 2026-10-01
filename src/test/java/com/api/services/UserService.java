package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserService {
	
	private static final String USER_DETAILS_ENDPOINT= "/userdetails";
	private static final Logger LOGGER = LogManager.getLogger(UserService.class);

	@Step("Making User Details API Request")
	public Response userdetails(Role role) {
		LOGGER.info("Making userdetails request to the '{}' API for the role '{}'" ,USER_DETAILS_ENDPOINT,role);

		Response response = given()
		.spec(requestSpecWithAuthToken(role))
		.when()
		.get(USER_DETAILS_ENDPOINT);
		
		return response;
	}

}
