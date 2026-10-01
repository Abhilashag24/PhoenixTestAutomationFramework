package com.api.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cdimascio.dotenv.Dotenv;
import io.qameta.allure.Step;

public class EnvUtil {
	private static final Logger LOGGER = LogManager.getLogger(EnvUtil.class);
	
	private static Dotenv dotenv;
	
	static {
		LOGGER.info("Loading the .env file ....");

		dotenv  = Dotenv.load();
	}
	private EnvUtil() {}
	
	@Step("Retrieving the value from the .env")
	public static String getValue(String varName) {
		LOGGER.info("Reading the value of varname : {} from  the .env file ....",varName);

		return dotenv.get(varName);
	}

}
