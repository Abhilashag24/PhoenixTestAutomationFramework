package com.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariCPDemo {

	private static final String DB_URL = ConfigManager.getProperty("DB_URL");
	private static final String DB_USERNAME = ConfigManager.getProperty("DB_USERNAME");
	private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
	private static  Connection conn;

	public static void main(String[] args) {

		HikariConfig hikariConfig = new HikariConfig();
		hikariConfig.setJdbcUrl(DB_URL);
		hikariConfig.setUsername(DB_USERNAME);
		hikariConfig.setPassword(DB_PASSWORD);
		hikariConfig.setMaximumPoolSize(10);
		hikariConfig.setMinimumIdle(2);
		hikariConfig.setConnectionTimeout(10000);
		hikariConfig.setIdleTimeout(10000);
		hikariConfig.setMaxLifetime(1800000);
		hikariConfig.setPoolName("Phoenix Test Automation Framework");
		
		HikariDataSource dataSource = new HikariDataSource(hikariConfig);
		try {
			conn = dataSource.getConnection();
			Statement statement = conn.createStatement();
			 ResultSet resultSet = statement.executeQuery("select first_name , last_name , mobile_number  from tr_customer");
			
			
			  while (resultSet.next()) {
			  System.out.println(resultSet.getString("first_name"));
			  System.out.println(resultSet.getString("last_name"));
			  System.out.println(resultSet.getString("mobile_number"));
			  
			  }
			 
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
