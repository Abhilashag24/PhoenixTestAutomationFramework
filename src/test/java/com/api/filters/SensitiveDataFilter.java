package com.api.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		LOGGER.info("---------------------- Hello from Filter ------------------");
		readactPayload(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);
		redactResponseBody(response);
		LOGGER.info("---------------------- Got the response in Filter ------------------");

		return response;
	}

	// Create a method which is going to redact/hide the password from the request
	// payload

	public void readactPayload(FilterableRequestSpecification requestSpec) {
		String requestPayload = requestSpec.getBody().toString();
		requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\":\"[REDACTED]\"");
		LOGGER.info("Request Payload : {}", requestPayload);
	}

	public void redactResponseBody(Response response) {
		String responseBody = response.asPrettyString();

		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\":\"[REDACTED]\"");
		LOGGER.info("Response Body : {}", responseBody);
	}

}
