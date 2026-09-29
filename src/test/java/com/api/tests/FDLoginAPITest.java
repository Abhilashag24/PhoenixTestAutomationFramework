package com.api.tests;

import static com.api.utils.SpecUtil.responseSpec_OK;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.api.services.AuthService;
import com.dataproviders.api.bean.UserBean;

public class FDLoginAPITest {

	private UserBean userCredentials;
	private AuthService authService;

	@BeforeMethod(description = "Create the request payload for Login API")
	public void setUp() {
		userCredentials = new UserBean("iamfd", "password");
		authService = new AuthService();

	}

	@Test(description = "Verifying if login api is working for FD user", groups = { "api", "regression", "smoke" })

	public void loginAPITest() {

		authService.login(userCredentials).then().spec(responseSpec_OK()).body("message", equalTo("Success")).and()
				.body(matchesJsonSchemaInClasspath("response-schema/LoginResponseSchema.json"));
	}

}
