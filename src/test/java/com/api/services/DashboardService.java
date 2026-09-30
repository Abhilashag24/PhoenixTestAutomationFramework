package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;

import io.restassured.response.Response;

public class DashboardService {
	private static final Logger LOGGER = LogManager.getLogger(DashboardService.class);

	public static final String COUNT_ENDPOINT = "/dashboard/count";
	
	public static final String DETAIL_COUNT = "/dashboard/details";

	public Response count(Role role) {
		LOGGER.info("Making request to the '{}' API for the role '{}'" ,COUNT_ENDPOINT,role);
		return given().spec(requestSpecWithAuthToken(role)).when().get(COUNT_ENDPOINT);

	}
	public Response countForNoAuth() {
		LOGGER.info("Making request to the '{}' API with no auth" ,COUNT_ENDPOINT);
		return given().spec(requestSpec()).when().get(COUNT_ENDPOINT);

	}
	
	public Response details(Role role, Object payload) {
		LOGGER.info("Making request to the '{}' API for the role '{}' and the paylolad is {}" ,DETAIL_COUNT,role, payload);
		return given().spec(requestSpecWithAuthToken(role)).body(payload).when().post(DETAIL_COUNT);

	}
}
