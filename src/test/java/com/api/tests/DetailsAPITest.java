package com.api.tests;

import org.hamcrest.Matchers;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.constants.Role;
import com.api.request.model.Detail;
import com.api.services.DashboardService;
import com.api.utils.SpecUtil;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Job Management")
@Feature("Job Details")
@Listeners(com.listeners.APITestListener.class)

public class DetailsAPITest {

	private DashboardService dashboardService;
	
	private Detail detailPayload;
	
	@BeforeMethod(description = "Instantiating dashboard Service and Creating detail payload")
	public void setUp() {
		
		dashboardService = new DashboardService();
		detailPayload =  new Detail("created_today");
	}
	
	@Story("Job Details is shown correctly for FD")
	@Description("Verifying if Details Job API is able to create In-Warranty Jobs")
	@Severity(SeverityLevel.CRITICAL)
	@Test (description = "Verifying if Details Job API is able to create In-Warranty Jobs", groups = { "api", "regression", "smoke" })
	public void detailsAPITest() {
		
		dashboardService.details(Role.FD, detailPayload).then()
		.spec(SpecUtil.responseSpec_OK())
		.body("message", Matchers.equalTo("Success"));
	}
	
}
