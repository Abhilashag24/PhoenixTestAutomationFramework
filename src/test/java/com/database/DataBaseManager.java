package com.database;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.api.utils.VaultDBConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DataBaseManager {

	private static final Logger LOGGER = LogManager.getLogger(DataBaseManager.class);

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
	private static volatile HikariDataSource dataSource = null;

	private static boolean isVaultUp = true;
	private static final String DB_URL = loadSecret("DB_URL");
	private static final String DB_USERNAME = loadSecret("DB_USERNAME");
	private static final String DB_PASSWORD = loadSecret("DB_PASSWORD");

	public static String loadSecret(String key) {
		String value;
		if (isVaultUp) {
			value = VaultDBConfig.getSecret(key);
			if (value == null) { 
				LOGGER.error("Vault is down !! or some issue with Vault...");
				isVaultUp = false;

			} else {
				LOGGER.info("Reading value for the key {} from Vault....",key);
				return value;
			}
		}
		LOGGER.info("Reading value from .env");
		value = EnvUtil.getValue(key);
		return value;

	}

	private DataBaseManager() {

	}

	private static void initializePool() {

		if (dataSource == null) { // First Check which all parallel thread will enter
			LOGGER.info("DataBase Conenction is not available....Creating HikariDataSource");

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
					LOGGER.info("HikariDataSource Conenction is established !!!");

				}
			}
		}

	}

	public static Connection getConnection() throws SQLException {
		Connection conn = null;
		LOGGER.info("Initializing the DataBase Conenction using HikariCP");

		if (dataSource == null) {
			initializePool();
		} else if (dataSource.isClosed() || dataSource == null) {
			LOGGER.error("HIKARI DATA SOURCE IS CLOSED");
			throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
		}
		conn = dataSource.getConnection();

		return conn;
	}
}
