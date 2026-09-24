package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.response.model.TRJobHeadDBModel;
import com.api.utils.FakerDataGenerator;
import com.api.utils.TimestampAssertionUtil;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDAO;
import com.database.dao.CustomerProductDAO;
import com.database.dao.JobHeadDAO;
import com.database.dao.MapJobProblemDAO;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;
import com.database.model.MapJobProblemDBModel;

import io.restassured.response.Response;

public class CreateJobAPITestWithFaker {

	private CreateJobPayload createJobPayload;

	@BeforeMethod(description = "Creating a Create Job API Request Payload")
	public void setUp() {

		createJobPayload = FakerDataGenerator.generateFakeCreateJobData();

	}

	@Test(description = "Verifying if Create Job API is able to create In-Warranty Jobs", groups = { "api",
			"regression", "smoke" })
	public void createJobAPITest() {

		Response response = given().spec(requestSpecWithAuthToken(FD, createJobPayload)).when().post("/job/create").then()
				.spec(responseSpec_OK())
				.body(matchesJsonSchemaInClasspath("response-schema/CreateJobAPIResponseSchema.json"))
				.body("message", equalTo("Job created successfully. ")).and()
				.body("data.mst_service_location_id", equalTo(1)).and().body("data.job_number", startsWith("JOB_"))
				.extract().response();


int customerId = response.then().extract().jsonPath().getInt("data.tr_customer_id");
		
		Customer expecteCustomer = createJobPayload.customer();
		CustomerDBModel customerDBModel = CustomerDAO.getCustomerInfo(customerId);
		Assert.assertEquals(customerDBModel.getFirst_name(), expecteCustomer.first_name());
		Assert.assertEquals(customerDBModel.getLast_name(), expecteCustomer.last_name());
		Assert.assertEquals(customerDBModel.getMobile_number(), expecteCustomer.mobile_number());
		Assert.assertEquals(customerDBModel.getMobile_number_alt(), expecteCustomer.mobile_number_alt());
		Assert.assertEquals(customerDBModel.getEmail_id(), expecteCustomer.email_id());
		Assert.assertEquals(customerDBModel.getEmail_id_alt(), expecteCustomer.email_id_alt());

		CustomerAddress customerAddress = createJobPayload.customer_address();

		CustomerAddressDBModel customerAddressDBModel = CustomerAddressDAO
				.getCustomerAddressInfo(customerDBModel.getTr_customer_address_id());

		Assert.assertEquals(customerAddressDBModel.getFlat_number(), customerAddress.flat_number());
		Assert.assertEquals(customerAddressDBModel.getApartment_name(), customerAddress.apartment_name());
		Assert.assertEquals(customerAddressDBModel.getStreet_name(), customerAddress.street_name());
		Assert.assertEquals(customerAddressDBModel.getLandmark(), customerAddress.landmark());
		Assert.assertEquals(customerAddressDBModel.getArea(), customerAddress.area());
		Assert.assertEquals(customerAddressDBModel.getPincode(), customerAddress.pincode());
		Assert.assertEquals(customerAddressDBModel.getCountry(), customerAddress.country());
		Assert.assertEquals(customerAddressDBModel.getState(), customerAddress.state());

		System.out.println();

		int productId = response.then().extract().jsonPath().getInt("data.tr_customer_product_id");
		
		CustomerProduct customerProduct = createJobPayload.customer_product(); 
		CustomerProductDBModel cProductDBModel = CustomerProductDAO
				.getCustomerProductInfo(productId);
		Assert.assertEquals(cProductDBModel.getMst_model_id(), customerProduct.mst_model_id());
		TimestampAssertionUtil.assertTimestampEqualsExact(cProductDBModel.getDop(), customerProduct.dop());
		Assert.assertEquals(cProductDBModel.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(cProductDBModel.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(cProductDBModel.getImei1(), customerProduct.imei1());
		Assert.assertEquals(cProductDBModel.getImei2(), customerProduct.imei2());
		
		

		int tr_job_head_id = response.then().extract().jsonPath().getInt("data.id");
		System.out.println("=============="+tr_job_head_id);
		MapJobProblemDBModel mDbModel = MapJobProblemDAO.getProblemDetails(tr_job_head_id);
		Assert.assertEquals(mDbModel.getMst_problem_id(), createJobPayload.problems().getLast().id());
		Assert.assertEquals(mDbModel.getRemark(), createJobPayload.problems().getLast().remark());
		
		TRJobHeadDBModel tHeadDBModel = JobHeadDAO.getJobHeadDetails(customerId);
		Assert.assertEquals(tHeadDBModel.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(tHeadDBModel.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(tHeadDBModel.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(tHeadDBModel.getMst_platform_id(), createJobPayload.mst_platform_id()); 

		
	}

	@Test(description = "Verifying if Create Job API is giving correct status for Invalid Token", groups = { "api",
			"negative", "regression", "smoke" })
	public void invalidTokenCreateJobAPITest() {

	}

}
