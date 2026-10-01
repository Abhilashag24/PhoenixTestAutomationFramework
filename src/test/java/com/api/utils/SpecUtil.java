package com.api.utils;

import static com.api.constants.Role.FD;
import static com.api.utils.AuthTokenProvider.getToken;
import static com.api.utils.ConfigManager.getProperty;

import org.hamcrest.Matchers;

import com.api.constants.Role;
import com.api.filters.SensitiveDataFilter;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecUtil {

	@Step("Setting up the BASE_URI, Content Type as Application/JSON and attaching the SensitiveData filter for a role")
	public static RequestSpecification requestSpecWithAuthToken(Role role) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addHeader("Authorization", getToken(FD))
				.addFilter(new SensitiveDataFilter()).addFilter(new AllureRestAssured()).build();

		return request;
	}

	// POST -- PUT -- PATCH (Body)
	@Step("Setting up the BASE_URI, Content Type as Application/JSON and attaching the SensitiveData filter with payload")
	public static RequestSpecification requestSpec(Object payload) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).setBody(payload)
				.addFilter(new SensitiveDataFilter()).addFilter(new AllureRestAssured()).build();

		return request;
	}

	@Step("Setting up the BASE_URI, Content Type as Application/JSON and attaching the SensitiveData filter for a role and attaching payload")
	public static RequestSpecification requestSpecWithAuthToken(Role role, Object payload) {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addHeader("Authorization", getToken(FD))
				.setBody(payload).addFilter(new SensitiveDataFilter()).addFilter(new AllureRestAssured()).build();
		return request;
	}

	// GET -- DEL
	@Step("Setting up the BASE_URI, Content Type as Application/JSON and attaching the SensitiveData filter")
	public static RequestSpecification requestSpec() {
		RequestSpecification request = new RequestSpecBuilder().setBaseUri(getProperty("BASE_URI"))
				.setContentType(ContentType.JSON).setAccept(ContentType.JSON).addFilter(new SensitiveDataFilter())
				.addFilter(new AllureRestAssured()).build();

		return request;
	}

	@Step("Expecting the response to have Content Type as Application/JSON, Status 200 and Response Time Less Than 1000 ms")
	public static ResponseSpecification responseSpec_OK() {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(200)
				.expectResponseTime(Matchers.lessThan(1000L)).expectContentType(ContentType.JSON).build();

		return responseSpecification;
	}

	@Step("Expecting the response to have Content Type as Application/JSON, Response Time Less Than 1000 ms and Status Code")
	public static ResponseSpecification responseSpec_JSON(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).expectContentType(ContentType.JSON).build();

		return responseSpecification;
	}

	@Step("Expecting the response to have Content Type as TEXT, Response Time Less Than 1000 ms and Status Code")
	public static ResponseSpecification responseSpec_TEXT(int statusCode) {
		ResponseSpecification responseSpecification = new ResponseSpecBuilder().expectStatusCode(statusCode)
				.expectResponseTime(Matchers.lessThan(1000L)).build();

		return responseSpecification;
	}
}
