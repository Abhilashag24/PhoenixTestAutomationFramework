package com.database;

import java.sql.Connection;
import java.sql.SQLException;

public class DemoRunner {

	public static void main(String[] args) throws SQLException {
		Connection con = DataBaseManager.getConnection();
		System.out.println(con);
		

	}

}
