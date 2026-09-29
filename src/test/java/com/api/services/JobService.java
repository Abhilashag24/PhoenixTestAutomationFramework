package com.api.services;

import static com.api.utils.SpecUtil.*;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;
import com.api.request.model.CreateJobPayload;

import io.restassured.response.Response;

public class JobService {

	public static final String CREATE_JOB_ENDPOINT = "/job/create";
	
	public static final String SEARCH_ENDPOINT = "/job/search";


	public Response create(Role role, CreateJobPayload createJobPayload) {

		return given().spec(requestSpecWithAuthToken(role, createJobPayload)).when().post(CREATE_JOB_ENDPOINT);
	}

	public Response create() {

		return given().spec(requestSpec()).when().post(CREATE_JOB_ENDPOINT);
	}
	
	public Response search(Role role, Object payload) {

		return given().spec(requestSpecWithAuthToken(role, payload)).when().post(SEARCH_ENDPOINT);
	}

}
