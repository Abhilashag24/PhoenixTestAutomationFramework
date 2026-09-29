package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.Search;
import com.api.services.JobService;
import com.api.utils.SpecUtil;

public class SearchAPITest {
	
	private JobService jobService;
	
	private static final String JOB_NUMBER="JOB_440119";
	
	private Search searchPayload;
	
	@BeforeMethod(description = "Instantiating the job Service and creating the search payload")
	public void setUp() {
		
		jobService = new JobService();
		searchPayload =  new Search(JOB_NUMBER);
	}
	
	@Test(description = "Verify if Search API if working as expected", groups = {"e2e","Regression","smoke"})
	public void seachAPItest() {
		
		jobService.search(Role.FD,searchPayload)
		.then()
		.spec(SpecUtil.responseSpec_OK()).body("message", Matchers.equalTo("Success"));
	}

}
