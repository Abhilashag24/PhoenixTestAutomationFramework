package com.api.utils;

import static com.api.constants.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.filters.SensitiveDataFilter;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	public static RequestSpecification requestSpecWithAuthToken(Role role) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addHeader("Authorization", getToken(FD))
				.addFilter(new SensitiveDataFilter()).build();

		return request;
	}

	// POST -- PUT -- PATCH (Body)
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilter()).build();

		return request;
	}

	public static RequestSpecification requestSpecWithAuthToken(Role role, Object payload) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addHeader("Authorization", getToken(FD))
				.setBody(payload)
				.addFilter(new SensitiveDataFilter()).build();
		return request;
	}

	// GET -- DEL
	public static RequestSpecification requestSpec() {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter()).build();

		return request;
	}

	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
				.expectResponseTime(Matchers.lessThan(1000L)).expectContentType(ContentType.JSON)
				.build();

		return responseSpecification;
	}

	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).expectContentType(ContentType.JSON)
				.build();

		return responseSpecification;
	}

	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).build();

		return responseSpecification;
	}
}
