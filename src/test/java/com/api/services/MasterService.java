package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class MasterService {
	
	private static final Logger LOGGER = LogManager.getLogger(MasterService.class);

	
	public static final String MASTER_ENDPOINT= "/master";
	
	
	@Step("Making MAster API Request")
	public Response master(Role role) {
		LOGGER.info("Making master request to the '{}' API for the role '{}'" ,MASTER_ENDPOINT,role);
	
		return 	given().spec(requestSpecWithAuthToken(role))
				.when().post(MASTER_ENDPOINT);
		
	}
	
	@Step("Making MAster API Request Without Auth")
	public Response master() {
		LOGGER.info("Making master request to the '{}' API with No auth token" ,MASTER_ENDPOINT);
	
		return 	given().spec(requestSpec())
				.when().post(MASTER_ENDPOINT);
		
	}

}
