package com.database;

import com.api.utils.ConfigManager;

import io.github.cdimascio.dotenv.Dotenv;

public class envRunner {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		String URL = dotenv.get("DB_URL");
System.out.println(URL);
	}

}
