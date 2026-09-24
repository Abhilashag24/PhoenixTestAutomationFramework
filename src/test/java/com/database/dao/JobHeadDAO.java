package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.api.response.model.TRJobHeadDBModel;
import com.database.DataBaseManager;

public class JobHeadDAO {

	private static final String JOB_HEAD_QUERY = "select * from tr_job_head where tr_customer_id=?";

	private JobHeadDAO() {
		// TODO Auto-generated constructor stub
	}

	public static TRJobHeadDBModel getJobHeadDetails(int customerId) {

		Connection conn;
		ResultSet resultSet;
		PreparedStatement pStatement;
		TRJobHeadDBModel tJobHeadDBModel = null;
		try {
			conn = DataBaseManager.getConnection();
			pStatement = conn.prepareStatement(JOB_HEAD_QUERY);
			pStatement.setInt(1, customerId);
			resultSet = pStatement.executeQuery();

			while (resultSet.next()) {
				tJobHeadDBModel = new TRJobHeadDBModel(resultSet.getInt("id"), resultSet.getString("job_number"),
						resultSet.getInt("tr_customer_id"), resultSet.getInt("tr_customer_product_id"),
						resultSet.getInt("mst_service_location_id"), resultSet.getInt("mst_platform_id"),
						resultSet.getInt("mst_warrenty_status_id"), resultSet.getInt("mst_oem_id"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return tJobHeadDBModel;
	}
}
