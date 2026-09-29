package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;
import static com.api.utils.SpecUtil.*;

import io.restassured.response.Response;

public class DashboardService {

	public static final String COUNT_ENDPOINT = "/dashboard/count";

	public Response count(Role role) {

		return given().spec(requestSpecWithAuthToken(role)).when().get(COUNT_ENDPOINT);

	}
	public Response countForNoAuth() {

		return given().spec(requestSpec()).when().get(COUNT_ENDPOINT);

	}
}
