package com.api.services;

import static com.api.utils.SpecUtil.requestSpec;
import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static io.restassured.RestAssured.given;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {
	
	private static final Logger LOGGER = LogManager.getLogger(JobService.class);


	public static final String CREATE_JOB_ENDPOINT = "/job/create";
	
	public static final String SEARCH_ENDPOINT = "/job/search";


	public Response create(Role role, CreateJobPayload createJobPayload) {
		LOGGER.info("Making create request to the '{}' API for the role '{}' and the paylolad is {}" ,CREATE_JOB_ENDPOINT,role, createJobPayload);

		return given().spec(requestSpecWithAuthToken(role, createJobPayload)).when().post(CREATE_JOB_ENDPOINT);
	}

	public Response create() {
		LOGGER.info("Making request to the '{}' API with No Auth" ,CREATE_JOB_ENDPOINT);

		return given().spec(requestSpec()).when().post(CREATE_JOB_ENDPOINT);
	}
	
	public Response search(Role role, Object payload) {
		LOGGER.info("Making search request to the '{}' API for the role '{}' and the paylolad is {}" ,SEARCH_ENDPOINT,role,payload);

		return given().spec(requestSpecWithAuthToken(role, payload)).when().post(SEARCH_ENDPOINT);
	}

}
