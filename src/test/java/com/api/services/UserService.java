package com.api.services;

import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static io.restassured.RestAssured.given;

import com.api.constants.Role;

import io.restassured.response.Response;

public class UserService {
	
	private static final String USER_DETAILS_ENDPOINT= "/userdetails";
	
	public Response userdetails(Role role) {
		
		Response response = given()
		.spec(requestSpecWithAuthToken(role))
		.when()
		.get(USER_DETAILS_ENDPOINT);
		
		return response;
	}

}
