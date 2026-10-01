package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DataBaseManager;
import com.database.model.CustomerDBModel;

import io.qameta.allure.Step;

public class CustomerDAO {

	private static final Logger LOGGER = LogManager.getLogger(CustomerDAO.class);

	// Executing the query for tr_customer table which will give the customer
	// details
	private static final String CUSTOMER_DETAIL_QUERY = "Select * from tr_customer where  id =?";

	private CustomerDAO() {
	}

	@Step("Retrieving the Customer Data from Database for the specific customerId")
	public static CustomerDBModel getCustomerInfo(int customerId) {
		Connection conn;
		PreparedStatement preparedStatement;
		ResultSet resultSet;

		CustomerDBModel customerDBModel = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");

			conn = DataBaseManager.getConnection();

			preparedStatement = conn.prepareStatement(CUSTOMER_DETAIL_QUERY);
			preparedStatement.setInt(1, customerId);
			LOGGER.info("Executing the SQL Query {}", CUSTOMER_DETAIL_QUERY);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				customerDBModel = new CustomerDBModel(resultSet.getInt("id"), resultSet.getString("first_name"),
						resultSet.getString("last_name"), resultSet.getString("mobile_number"),
						resultSet.getString("mobile_number_alt"), resultSet.getString("email_id"),
						resultSet.getString("email_id_alt"), resultSet.getInt("tr_customer_address_id"));

			}
		} catch (SQLException e) {
			LOGGER.error("Cannot convert the resultSet into Bean", e);

			e.printStackTrace();
		}
		return customerDBModel;
	}

}
