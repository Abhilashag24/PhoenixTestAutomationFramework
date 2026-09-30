package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import static com.api.constants.Role.*;
import com.api.services.UserService;

@Listeners(com.listeners.APITestListener.class)
public class UserDetailsAPITest {
	
	private UserService userService;
	
	@BeforeMethod(description = "Setting up UserDetails Service")
	public void setUp() {
		userService = new UserService();
	}
	
	@Test(description = "Verifying if userDetails API response is shown correctly", groups = { "api", "regression", "smoke" })

	public void userDetailsAPITest() {
		
		
		userService.userdetails(FD).then()
		.spec(responseSpec_OK())
		.and()
		.body(matchesJsonSchemaInClasspath("response-schema/UserDetailsSchema.json"));
				
	}

}
