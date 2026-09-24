package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.database.DataBaseManager;
import com.database.model.MapJobProblemDBModel;

public class MapJobProblemDAO {

	private static final String PROBLEM_QUERY = "select * from map_job_problem where tr_job_head_id=?";

	private MapJobProblemDAO() {
	}

	public static MapJobProblemDBModel getProblemDetails(int tr_job_head_id) {

		Connection connection;
		ResultSet resultSet;
		MapJobProblemDBModel mapDbModel = null;
		try {
			connection = DataBaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(PROBLEM_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				mapDbModel = new MapJobProblemDBModel(resultSet.getInt("id"), resultSet.getInt("tr_job_head_id"),
						resultSet.getInt("mst_problem_id"), resultSet.getString("remark"));
System.out.println("------------------------"+mapDbModel);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return mapDbModel;
	}

}
