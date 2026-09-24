package com.database.model;

import java.sql.SQLException;

import com.database.dao.CustomerDAO;
import com.database.dao.CustomerProductDAO;

public class DemoRunner {

	public static void main(String[] args) throws SQLException {

		CustomerProductDBModel customerAddressDBModel = CustomerProductDAO.getCustomerProductInfo(433609);
		System.out.println(customerAddressDBModel);
		
		CustomerDBModel customerDBModel = CustomerDAO.getCustomerInfo(433609);
		System.out.println(customerDBModel);

	
	}

}
