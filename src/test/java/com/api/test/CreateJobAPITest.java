package com.api.test;

import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import static com.api.constants.Role.*;

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
		CustomerProduct customerProduct = new CustomerProduct("2026-04-30T20:00:00.000Z", "18058861592677",
				"18058861592677", "18058861592677", "2026-04-30T20:00:00.000Z", "1", "1");

		Problems problems = new Problems(1, "Battery Issue");
		Problems[] problemsArray = new Problems[1];
		problemsArray[0] = problems;

		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemsArray);

		given().spec(SpecUtil.requestSpecWithAuthToken(FD, createJobPayload)).when().post("/job/create").then()
				.spec(SpecUtil.responseSpec_OK());

	}
	
	@Test
	public void invalidTokenCreateJobAPITest() {
		
	}
	
	
}
