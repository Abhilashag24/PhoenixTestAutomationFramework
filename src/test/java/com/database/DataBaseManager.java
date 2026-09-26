package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import groovyjarjarantlr4.v4.codegen.model.dbg;
import io.github.cdimascio.dotenv.Dotenv;

public class DataBaseManager {

	static Dotenv dotenv = Dotenv.load();
	private static final String DB_URL = dotenv.get("DB_URL");
	private static final String DB_USERNAME = dotenv.get("DB_USERNAME");
	private static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
	private static final int MAX_POOL_SIZE = Integer.parseInt(ConfigManager.getProperty("MAX_POOL_SIZE"));
	private static final int MIN_IDLE_COUNT = Integer.parseInt(ConfigManager.getProperty("MIN_IDLE_COUNT"));
	private static final int CONNECTION_TIMEOUT_IN_SEC = Integer
			.parseInt(ConfigManager.getProperty("CONNECTION_TIMEOUT_IN_SEC")) * 1000;
	private static final int IDEL_TIMEOUT_IN_SEC = Integer.parseInt(ConfigManager.getProperty("IDEL_TIMEOUT_IN_SEC"))
			* 1000;
	private static final int MAX_LIFETIME_IN_MIN = Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME_IN_MIN"))
			* 60 * 1000;
	private static final String HIKARI_CP_POOL_NAME = ConfigManager.getProperty("HIKARI_CP_POOL_NAME");
	private static HikariConfig hikariConfig;
	private static volatile HikariDataSource dataSource =null;
//	private static  Connection conn;

	private DataBaseManager() {

	}

	private static void initializePool() {
		try {
			if (dataSource == null) { // First Check which all parallel thread will enter
				synchronized (DataBaseManager.class) {
					if (dataSource == null) { // Only and only for the first connection request.
						hikariConfig = new HikariConfig();
						hikariConfig.setJdbcUrl(DB_URL);
						hikariConfig.setUsername(DB_USERNAME);
						hikariConfig.setPassword(DB_PASSWORD);
						hikariConfig.setMaximumPoolSize(MAX_POOL_SIZE);
						hikariConfig.setMinimumIdle(MIN_IDLE_COUNT);
						hikariConfig.setConnectionTimeout(CONNECTION_TIMEOUT_IN_SEC);
						hikariConfig.setIdleTimeout(IDEL_TIMEOUT_IN_SEC);
						hikariConfig.setMaxLifetime(MAX_LIFETIME_IN_MIN);
						hikariConfig.setPoolName(HIKARI_CP_POOL_NAME);

						dataSource = new HikariDataSource(hikariConfig);

						
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	
	public static Connection getConnection() throws SQLException {
		Connection conn = null;
	 
			if (dataSource ==null) {
				initializePool();
			}else if(dataSource.isClosed() || dataSource == null) {
				throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
			}
			conn= dataSource.getConnection();
		 
		return conn;
	}
}
