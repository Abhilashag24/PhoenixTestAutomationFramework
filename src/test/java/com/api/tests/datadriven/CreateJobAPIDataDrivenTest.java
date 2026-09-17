package com.api.tests.datadriven;

import static com.api.constants.Role.FD;
import static com.api.utils.DateTimeUtility.getTimeWithDaysAgo;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Model;
import com.api.constants.OEM;
import com.api.constants.Platform;
import com.api.constants.Problem;
import com.api.constants.Product;
import com.api.constants.ServiceLocation;
import com.api.constants.Warranty_Status;
import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import static com.api.utils.SpecUtil.*;

public class CreateJobAPIDataDrivenTest {

	@Test(description = "Verifying if Create Job API is able to create In-Warranty Jobs", groups = { "api",
			"regression",
			"smoke","datadriven" }, dataProviderClass = com.dataproviders.DataProviderUtils.class, dataProvider = "CreateJobAPIDataProvider")
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
