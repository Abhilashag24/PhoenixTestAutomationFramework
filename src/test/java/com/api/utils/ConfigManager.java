package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigManager {

	// Write a program to read properties file from
	// src/test/resources/config/config.properties

	private static String path = "config/config.properties";
	private static Properties properties = new Properties();
	private static String env;

	static {
		// If no evn is mentioned , then assign qa hence the second parameter
		env = System.getProperty("env", "qa");
		switch (env.toLowerCase().trim()) {
		case "dev" -> path = "config/config.dev.properties";

		case "qa" -> path = "config/config.qa.properties";

		case "uat" -> path = "config/config.uat.properties";

		default -> path = "config/config.qa.properties";
		
		}
		// Operation of loading a property file in the memory
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (path == null) {
			throw new RuntimeException("Not able to find properties file at given path " + path);
		}
		try {
			properties.load(input);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// To restrict any class to create an object of ConfigManager
	private ConfigManager() {

	}

	public static String getProperty(String key) {

		return properties.getProperty(key);

	}

}
