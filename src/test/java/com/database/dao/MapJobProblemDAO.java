package com.database.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.database.DataBaseManager;
import com.database.model.MapJobProblemDBModel;

public class MapJobProblemDAO {

	private static final Logger LOGGER = LogManager.getLogger(MapJobProblemDAO.class);

	private static final String PROBLEM_QUERY = "select * from map_job_problem where tr_job_head_id=?";

	private MapJobProblemDAO() {
	}

	public static MapJobProblemDBModel getProblemDetails(int tr_job_head_id) {

		Connection connection;
		ResultSet resultSet;
		MapJobProblemDBModel mapDbModel = null;
		try {
			LOGGER.info("Getting the connection from the Database Manager");

			connection = DataBaseManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(PROBLEM_QUERY);
			preparedStatement.setInt(1, tr_job_head_id);
			LOGGER.info("Executing the SQL Query {}", PROBLEM_QUERY);

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {

				mapDbModel = new MapJobProblemDBModel(resultSet.getInt("id"), resultSet.getInt("tr_job_head_id"),
						resultSet.getInt("mst_problem_id"), resultSet.getString("remark"));
				System.out.println("------------------------" + mapDbModel);
			}
		} catch (Exception e) {
			LOGGER.error("Cannot convert the resultSet into Bean", e);

			e.printStackTrace();
		}
		return mapDbModel;
	}

}
