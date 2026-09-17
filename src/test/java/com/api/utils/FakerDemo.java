package com.api.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.api.request.model.CreateJobPayload;
import com.api.request.model.Customer;
import com.api.request.model.CustomerAddress;
import com.api.request.model.CustomerProduct;
import com.api.request.model.Problems;
import com.github.javafaker.Faker;

public class FakerDemo {
	public static void main(String[] args) {

		// Create Customer using Faker
		Faker faker = new Faker();
		String first_name = faker.name().firstName();
		String last_name = faker.name().firstName();
		String mobile_number = faker.numerify("870#######");
		String mobile_number_alt = faker.numerify("870#######");
		String email_id = faker.internet().emailAddress();
		String email_id_alt = faker.internet().emailAddress();

		Customer customer = new Customer(first_name, last_name, mobile_number, mobile_number_alt, email_id,
				email_id_alt);
		System.out.println(customer);

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
		System.out.println(customerAddress);

		String dop = DateTimeUtility.getTimeWithDaysAgo(10);
		String serial_number = faker.numerify("###############");
		String popurl = faker.internet().url();
		CustomerProduct customerProduct = new CustomerProduct(dop, serial_number, serial_number, serial_number, popurl,
				0, 0);
		System.out.println(customerProduct);

		String remark = faker.lorem().sentence(10);

// generate random number between 1-27 

		Random random = new Random();
		int id = random.nextInt(27) + 1;

		Problems problems = new Problems(id, remark);
		System.out.println(problems);

		List<Problems> problemList = new ArrayList<Problems>();
		problemList.add(problems);

		CreateJobPayload createJobPayload = new CreateJobPayload(0, 2, 1, 1, customer, customerAddress, customerProduct,
				problemList);
		System.out.println(createJobPayload);

	}

}
