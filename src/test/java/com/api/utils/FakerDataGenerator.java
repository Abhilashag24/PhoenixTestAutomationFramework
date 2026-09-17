package com.api.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDataGenerator {

	private static Faker faker = new Faker();
	private final static Random RANDOM = new Random();

	private final static int MST_SERVICE_LOCATION_ID = 0;
	private final static int MST_PLATFORM_ID = 2;
	private final static int MST_WARRANTY_STATUS_ID = 1;
	private final static int MST_OEM_ID = 1;
	private final static int PRODUCT_ID = 1;
	private final static int MST_MODEL_ID = 1;
	private final static int[] VALIDPROBLEMIDS= {1,2,3,4,5,6,7,8,9,10,11,12,15,16,17,19,20,22,24,26,27,28,29};
	private FakerDataGenerator() {

	}

	public static CreateJobPayload generateFakeCreateJobData() {

		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddress();
		CustomerProduct customerProduct = generateFakeCustomerProduct();
		List<Problems> problemList = generateFakeProblems();

		CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);

		return createJobPayload;
	}

	public static Iterator<CreateJobPayload> generateFakeCreateJobData(int count) {

		List<CreateJobPayload> listCreateJobPayload = new ArrayList<CreateJobPayload>(count);
		for(int i=1;i<=count;i++) {
		Customer customer = generateFakeCustomerData();
		CustomerAddress customerAddress = generateFakeCustomerAddress();
		CustomerProduct customerProduct = generateFakeCustomerProduct();
		List<Problems> problemList = generateFakeProblems();

		CreateJobPayload createJobPayload = new CreateJobPayload(MST_SERVICE_LOCATION_ID, MST_PLATFORM_ID,
				MST_WARRANTY_STATUS_ID, MST_OEM_ID, customer, customerAddress, customerProduct, problemList);
	listCreateJobPayload.add(createJobPayload);
		}
		return listCreateJobPayload.iterator();
	}

	private static List<Problems> generateFakeProblems() {
		String remark = faker.lorem().sentence(3);

		// generate random number between 1-27

		int id = RANDOM.nextInt(VALIDPROBLEMIDS.length);

		Problems problems = new Problems(VALIDPROBLEMIDS[id], remark);

		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

		return problemList;
	}

	private static CustomerProduct generateFakeCustomerProduct() {
		String dop = DateTimeUtility.getTimeWithDaysAgo(10);
		String serial_number = faker.numerify("###############");
		String popurl = faker.internet().url();
		CustomerProduct customerProduct = new CustomerProduct(dop, serial_number, serial_number, serial_number, popurl,
				PRODUCT_ID, MST_MODEL_ID);
		return customerProduct;
	}

	private static CustomerAddress generateFakeCustomerAddress() {
		String flat_number = faker.numerify("###");
		String apartment_name = faker.address().streetName();
		String street_name = faker.address().streetName();
		String landmark = faker.address().streetName();
		String area = faker.address().cityName();
		String pincode = faker.numerify("######");
		String country = faker.address().country();
		String state = faker.address().state();	

		CustomerAddress customerAddress = new CustomerAddress(flat_number, apartment_name, street_name, landmark, area,
				pincode, country, state);
		return customerAddress;
	}

	private static Customer generateFakeCustomerData() {

		String first_name = faker.name().firstName();
		String last_name = faker.name().firstName();
		String mobile_number = faker.numerify("870#######");
		String mobile_number_alt = faker.numerify("870#######");
		String email_id = faker.internet().emailAddress();
		String email_id_alt = faker.internet().emailAddress();

		Customer customer = new Customer(first_name, last_name, mobile_number, mobile_number_alt, email_id,
				email_id_alt);
		return customer;
	}

}
