package com.api.test;

import static com.api.constants.Role.FD;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.api.pojo.CreateJobPayload;
import com.api.pojo.Customer;
import com.api.pojo.CustomerAddress;
import com.api.pojo.CustomerProduct;
import com.api.pojo.Problems;
import com.api.utils.SpecUtil;

public class CreateJobAPITest {

	@Test
	public void createJobAPITest() {

		Customer customer = new Customer("Test_FN", "Test_LN", "9856321452", "", "test@test.com", "");
		CustomerAddress customerAddress = new CustomerAddress("101", "Test Apartment", "Test Street", "Inorbit mall",
				"Test Area", "451245", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct("2026-04-30T20:00:00.000Z", "73058861592677",
				"73058861592677", "73058861592677", "2026-04-30T20:00:00.000Z", "1", "1");

		Problems problems = new Problems(1, "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();		
		problemsList.add(problems);

		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemsList);

		given().spec(SpecUtil.requestSpecWithAuthToken(FD, createJobPayload)).when().post("/job/create").then()
				.spec(SpecUtil.responseSpec_OK()).body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).and().body("data.mst_service_location_id", equalTo(1))
				.and().body("data.job_number", startsWith("JOB_"));
		

	}

	@Test
	public void invalidTokenCreateJobAPITest() {

	}

}
