package com.api.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigManager_OLD {

	// Write a program to read properties file from
	// src/test/resources/config/config.properties

	private static Properties properties = new Properties();

	static {
		// Operation of loading a property file in the memory
		File configFile = new File(System.getProperty("user.dir") + File.separator + "src" + File.separator + "test"
				+ File.separator + "resources" + File.separator + "config" + File.separator + "config.properties");
		FileReader fileReader;
		try {
			fileReader = new FileReader(configFile);
			properties.load(fileReader);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// To restrict any class to create an object of ConfigManager
	private ConfigManager_OLD() {

	}

	public static String getProperty(String key) {

		return properties.getProperty(key);

	}

}
