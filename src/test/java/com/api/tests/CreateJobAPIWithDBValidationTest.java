package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.DateTimeUtility.getTimeWithDaysAgo;
import static com.api.utils.SpecUtil.requestSpecWithAuthToken;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;
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
import com.api.response.model.TRJobHeadDBModel;
import com.api.utils.TimestampAssertionUtil;
import com.database.dao.CustomerAddressDAO;
import com.database.dao.CustomerDAO;
import com.database.dao.CustomerProductDAO;
import com.database.dao.JobHeadDAO;
import com.database.model.CustomerAddressDBModel;
import com.database.model.CustomerDBModel;
import com.database.model.CustomerProductDBModel;

import io.restassured.response.Response;

public class CreateJobAPIWithDBValidationTest {

	private CreateJobPayload createJobPayload;
	private Customer customer;
	private CustomerAddress customerAddress;
	private CustomerProduct customerProduct;

	@BeforeMethod(description = "Creating a Create Job API Request Payload")
	public void setUp() {
		customer = new Customer("Test_FN", "Test_LN", "9856321452", "", "test@test.com", "");
		customerAddress = new CustomerAddress("101", "Test Apartment", "Test Street", "Inorbit mall", "Test Area",
				"451245", "India", "Maharashtra");
		customerProduct = new CustomerProduct(getTimeWithDaysAgo(10), "77778861592777", "77778861592777",
				"77778861592777", "2026-04-30T20:00:00.000Z", Product.NEXUS_2.getCode(), Model.NEXUS_2_BLUE.getCode());

		Problems problems = new Problems(Problem.SMARTPHONE_IS_RUNNING_SLOW.getCode(), "Battery Issue");
		List<Problems> problemsList = new ArrayList<Problems>();
		problemsList.add(problems);

		createJobPayload = new CreateJobPayload(ServiceLocation.SERVICE_LOCATION_A.getCode(),
				Platform.FRONTDESK.getCode(), Warranty_Status.IN_WAARANTY.getCode(), OEM.GOOGLE.getCode(), customer,
				customerAddress, customerProduct, problemsList);

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


		CustomerDBModel customerDBModel = CustomerDAO.getCustomerInfo(customerId);
		Assert.assertEquals(customerDBModel.getFirst_name(), customer.first_name());
		Assert.assertEquals(customerDBModel.getLast_name(), customer.last_name());
		Assert.assertEquals(customerDBModel.getMobile_number(), customer.mobile_number());
		Assert.assertEquals(customerDBModel.getMobile_number_alt(), customer.mobile_number_alt());
		Assert.assertEquals(customerDBModel.getEmail_id(), customer.email_id());
		Assert.assertEquals(customerDBModel.getEmail_id_alt(), customer.email_id_alt());

		System.out.println();

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

		CustomerProductDBModel cProductDBModel = CustomerProductDAO
				.getCustomerProductInfo(productId);
		Assert.assertEquals(cProductDBModel.getMst_model_id(), customerProduct.mst_model_id());
		TimestampAssertionUtil.assertTimestampEqualsExact(cProductDBModel.getDop(), customerProduct.dop());
		Assert.assertEquals(cProductDBModel.getPopurl(), customerProduct.popurl());
		Assert.assertEquals(cProductDBModel.getSerial_number(), customerProduct.serial_number());
		Assert.assertEquals(cProductDBModel.getImei1(), customerProduct.imei1());
		Assert.assertEquals(cProductDBModel.getImei2(), customerProduct.imei2());
		
		
		TRJobHeadDBModel tHeadDBModel = JobHeadDAO.getJobHeadDetails(customerId);
		Assert.assertEquals(tHeadDBModel.getMst_oem_id(), createJobPayload.mst_oem_id());
		Assert.assertEquals(tHeadDBModel.getMst_service_location_id(), createJobPayload.mst_service_location_id());
		Assert.assertEquals(tHeadDBModel.getMst_warrenty_status_id(), createJobPayload.mst_warrenty_status_id());
		Assert.assertEquals(tHeadDBModel.getMst_platform_id(), createJobPayload.mst_platform_id()); 

	}



}
