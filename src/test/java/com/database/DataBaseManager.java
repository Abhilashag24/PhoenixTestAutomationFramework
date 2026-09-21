package com.database;

import java.sql.Connection;
import java.sql.DriverManager;

import com.api.utils.ConfigManager;

public class DataBaseManager {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USERNAME = ConfigManager.getProperty("DB_USERNAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private static volatile Connection conn;

	public static void createConnection() {
		try {
			if (conn == null) { // First Check which all parallel thread will enter
				synchronized (DataBaseManager.class) {
					if (conn == null) { // Only and only for the first connection request.
						conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public static void main(String[] args) {
		createConnection();
		createConnection();
		createConnection();
	}
}
