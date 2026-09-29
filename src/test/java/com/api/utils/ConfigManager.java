package com.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class ConfigManager {

	private static final Logger LOGGER = LogManager.getLogger(ConfigManager.class);

	private static String path = "config/config.properties";
	private static Properties properties = new Properties();
	private static String env;

	static {
		LOGGER.info("Reading env value passed from the terminal");
		if (System.getProperty("env") == null) {
			LOGGER.warn("Env variable is not set... Using 'qa' as env");
		}

		env = System.getProperty("env", "qa");

		LOGGER.info("Running the test in the {} env ", env);

		switch (env.toLowerCase().trim()) {
		case "dev" -> path = "config/config.dev.properties";

		case "qa" -> path = "config/config.qa.properties";

		case "uat" -> path = "config/config.uat.properties";

		default -> path = "config/config.qa.properties";

		}
		
		LOGGER.info("Using the properties file from the path {} ", path);

		// Operation of loading a property file in the memory
		InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);

		if (path == null) {
			LOGGER.error("Not able to find properties file at given path ", path);

			throw new RuntimeException("Not able to find properties file at given path " + path);
		}
		try {
			properties.load(input);
		} catch (IOException e) {
			LOGGER.error("Something went wrong ... Please check the file {}", path,e);
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
