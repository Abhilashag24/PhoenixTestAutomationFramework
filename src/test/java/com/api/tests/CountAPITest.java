package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.api.services.DashboardService;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;


@Epic("Job Management")
@Feature("Job Count")
@Listeners(com.listeners.APITestListener.class)
public class CountAPITest {

	private DashboardService dashboardService;

	@BeforeMethod(description = "Setting up dashboard Service instance")
	public void setUp() {
		dashboardService = new DashboardService();
	}

	@Story("Job Count Data is shown correctly")
	@Description("Verifying if Count API is working for FD user")
	@Severity(SeverityLevel.CRITICAL)
	@Test(description = "Verifying if Count API is working for FD user", groups = { "api", "regression", "smoke" })
	public void verifyCountAPIResponse() {
		dashboardService.count(FD).then().spec(responseSpec_OK()).body("message", equalTo("Success")).and()
				.body("data", notNullValue()).and().body("data.size()", equalTo(3))
				.body("data.count", everyItem(greaterThanOrEqualTo(0)))
				.body("data.label", everyItem(not(blankOrNullString())))
				.body(matchesJsonSchemaInClasspath("response-schema/CountAPIResponseSchema-FD.json")).body("data.key",
						containsInAnyOrder("pending_for_delivery", "created_today", "pending_fst_assignment"));
	}

	@Test(description = "Verifying if Count API is giving correct status for Invalid Token", groups = { "api",
			"regression", "smoke", "negative" })
	public void countAPITest_MissingAuthToken() {
		dashboardService.countForNoAuth().then().spec(responseSpec_TEXT(401));
	}
}
