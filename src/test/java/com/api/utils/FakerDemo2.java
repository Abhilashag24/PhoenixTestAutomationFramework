package com.api.utils;

import java.util.Locale;

import com.github.javafaker.Faker;

public class FakerDemo2 {
	public static void main(String[] args) {

		Faker faker = new Faker(new Locale("en-IND"));
		System.out.println(faker.name().fullName());
		System.out.println(faker.address().country());
		System.out.println(faker.address().state());
		System.out.println(faker.address().cityName());
		System.out.println(faker.relationships().sibling());
		System.out.println(faker.numerify("870#######"));
		System.out.println(faker.numerify("870#######"));
		System.out.println(faker.numerify("870#######"));
	}

}
