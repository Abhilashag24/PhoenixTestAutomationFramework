package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.DateTimeUtility.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
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
import com.api.services.JobService;

public class CreateJobAPITest {
	
	private CreateJobPayload createJobPayload;
	private JobService jobService;
	
	@BeforeMethod(description = "Creating a Create Job API Request Payload and instantiating Job Service")
	public void setUp() {
		
		jobService = new JobService();
		
		
		Customer customer = new Customer("Test_FN", "Test_LN", "9856321452", "", "test@test.com", "");
		CustomerAddress customerAddress = new CustomerAddress("101", "Test Apartment", "Test Street", "Inorbit mall",
				"Test Area", "451245", "India", "Maharashtra");
		CustomerProduct customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "73758861591777",
				"73758861591777", "73758861591777", "2026-04-30T20:00:00.000Z", Product.NEXUS_2.getCode(),
				Model.NEXUS_2_BLUE.getCode());

		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);

		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONTDESK.getCode(), Warranty_Status.IN_WAARANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemsList);

	}

	@Test (description = "Verifying if Create Job API is able to create In-Warranty Jobs", groups = { "api", "regression", "smoke" })
	public void createJobAPITest() {

		
		jobService.create(FD, createJobPayload).then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).and()
				.body("data.mst_service_location_id", equalTo(1)).and().body("data.job_number", startsWith("JOB_"));

	}

	@Test (description = "Verifying if Create Job API is giving correct status for Invalid Token", groups = { "api","negative", "regression", "smoke" })
	public void invalidTokenCreateJobAPITest() {
		jobService.create().then().spec(responseSpec_TEXT(401));
	}

}
