package com.api.tests;

import static com.api.constants.Role.FD;
import static com.api.utils.SpecUtil.responseSpec_OK;
import static com.api.utils.SpecUtil.responseSpec_TEXT;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.notNullValue;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.MasterService;

public class FDMasterAPIRequestTest {
	
	private MasterService masterService;
	
	@BeforeMethod(description = "Setting Up Master Service instance")
	public void setUp() {
		
		masterService = new MasterService();
	}

	@Test(description = "Verifying if Master API is giving correct response", groups = { "api", "regression", "smoke" })
	public void masterAPITest() {
	
// Whenever you are making post request, default content Type is application/url-formencoded
				masterService.master(FD).then().spec(responseSpec_OK()).body("message", equalTo("Success"))
				.body(matchesJsonSchemaInClasspath("response-schema/MasterAPIResponseSchema.json"))
				.body("data", notNullValue()).body("data", hasKey("mst_oem")).body("data", hasKey("mst_model"))
				.body("$", hasKey("message")).body("$", hasKey("data")).body("data.mst_oem.size()", equalTo(2))
				.body("data.mst_model.size()", greaterThan(0)).body("data.mst_oem.id", everyItem(notNullValue()))
				.body("data.mst_oem.name", everyItem(notNullValue()));

	}

	@Test (description = "Verifying if Master API is giving correct status for Invalid Token", groups = { "api","negative", "regression", "smoke" })

	public void invalidTokenMasterAPITest() { 
				masterService.master().then().spec(responseSpec_TEXT(401));
	}

}
