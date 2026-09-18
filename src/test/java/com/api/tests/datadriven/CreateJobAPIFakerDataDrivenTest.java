package com.api.tests.datadriven;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;

public class CreateJobAPIFakerDataDrivenTest {

	@Test(description = "Verifying if Create Job API is able to create In-Warranty Jobs", groups = { "api",
			"regression","smoke","datadriven" }, 
			dataProviderClass = com.dataproviders.DataProviderUtils.class,
			dataProvider = "CreateJobAPIFakerDataProvider")
	public void createJobAPITest(CreateJobPayload createJobPayload) {

		given().spec(requestSpecWithAuthToken(FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).and()
				.body("data.mst_service_location_id", equalTo(1)).and().body("data.job_number", startsWith("JOB_"));

	}

	@Test(description = "Verifying if Create Job API is giving correct status for Invalid Token", groups = { "api",
			"negative", "regression", "smoke" })
	public void invalidTokenCreateJobAPITest() {

	}

}
