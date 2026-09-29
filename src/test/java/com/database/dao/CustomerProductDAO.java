package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DataBaseManager;
import com.database.model.CustomerProductDBModel;

public class CustomerProductDAO {

	private static final Logger LOGGER = LogManager.getLogger(CustomerProductDAO.class);

	private static final String CUSTOMER_PRODUCT_QUERY = """

						SELECT id,
			tr_customer_id,
			mst_model_id,
			dop,
			popurl,
			imei2,
			imei1,
			serial_number

			from tr_customer_product where id=?


						""";

	private CustomerProductDAO() {
	}

	public static CustomerProductDBModel getCustomerProductInfo(int customerProductID) {
		Connection conn = null;
		PreparedStatement preparedStatement;
		ResultSet resultSet;
		CustomerProductDBModel customerProductModel = null;

		try {
			LOGGER.info("Getting the connection from the Database Manager");

			conn = DataBaseManager.getConnection();
			preparedStatement = conn.prepareStatement(CUSTOMER_PRODUCT_QUERY);
			preparedStatement.setInt(1, customerProductID);
			LOGGER.info("Executing the SQL Query {}", CUSTOMER_PRODUCT_QUERY);

			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				customerProductModel = new CustomerProductDBModel(resultSet.getInt("id"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("mst_model_id"),
						resultSet.getString("dop"), resultSet.getString("serial_number"), resultSet.getString("imei1"),
						resultSet.getString("imei2"), resultSet.getString("popurl"));
			}

		} catch (SQLException e) {
			LOGGER.error("Cannot convert the resultSet into Bean", e);

			e.printStackTrace();
		}
		return customerProductModel;
	}

}
