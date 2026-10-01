package com.database.model;

 
import lombok.AllArgsConstructor;
import lombok.Data;
 
@Data
@AllArgsConstructor
public class MapJobProblemDBModel {
	
	private int id;
	private int tr_job_head_id;
	private int mst_problem_id;
	private String remark;

}
