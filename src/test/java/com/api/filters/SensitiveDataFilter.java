package com.api.filters;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.http.Header;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

public class SensitiveDataFilter implements Filter {

	private static final Logger LOGGER = LogManager.getLogger(SensitiveDataFilter.class);

	@Override
	public Response filter(FilterableRequestSpecification requestSpec, FilterableResponseSpecification responseSpec,
			FilterContext ctx) {
		LOGGER.info("*********** REQUEST DETAILS ***************");
		LOGGER.info("BASE_URI : {}", requestSpec.getURI());
		LOGGER.info("HTTP METHOD : {}", requestSpec.getMethod());

		readactPayload(requestSpec);
		redactHeader(requestSpec);
		Response response = ctx.next(requestSpec, responseSpec);

		LOGGER.info("*********** RESPONSE DETAILS ***************");
		LOGGER.info("STATUS LINE : {}", response.statusLine());
		LOGGER.info("RESPONSE TIME : {}", response.getTime());
		LOGGER.info("RESPONSE HEADERS : \n{}", response.getHeaders());

		redactResponseBody(response);

		return response;
	}

	public void redactHeader(FilterableRequestSpecification requestSpec) {
		List<Header> headerList = requestSpec.getHeaders().asList();
		for (Header header : headerList) {

			if (header.getName().equalsIgnoreCase("Authorization")) {
				LOGGER.info("HEADER  {}  :  {}", header.getName(), "\"[REDACTED]\"");
			} else {
				LOGGER.info("HEADER  {}  :  {}", header.getName(), header.getValue());

			}
		}

	}

	public void readactPayload(FilterableRequestSpecification requestSpec) {
		if (requestSpec.getBody() != null) {
			String requestPayload = requestSpec.getBody().toString();
			requestPayload = requestPayload.replaceAll("\"password\"\s*:\s*\"[^\"]+\"", "\"password\":\"[REDACTED]\"");
			LOGGER.info("Request Payload : \n{}", requestPayload);
		}
	}

	public void redactResponseBody(Response response) {
		String responseBody = response.asPrettyString();

		responseBody = responseBody.replaceAll("\"token\"\s*:\s*\"[^\"]+\"", "\"token\":\"[REDACTED]\"");
		LOGGER.info("Response Body : \n{}", responseBody);
	}

}
