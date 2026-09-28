package com.api.utils;

import com.api.constants.Role;

public class AuthTokenDemoRunner {

	public static void main(String[] args) {
String token = 	AuthTokenProvider.getToken(Role.FD);

System.out.println(token);
	}

}
