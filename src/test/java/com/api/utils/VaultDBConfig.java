package com.api.utils;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bettercloud.vault.Vault;
import com.bettercloud.vault.VaultConfig;
import com.bettercloud.vault.VaultException;
import com.bettercloud.vault.response.LogicalResponse;

public class VaultDBConfig {
	private static final Logger LOGGER = LogManager.getLogger(VaultDBConfig.class);


	private static VaultConfig vaultConfig;
	private static Vault vault;
	private static final String VAULT_SERVER = System.getenv("VAULT_SERVER");
	private static final String VAULT_TOKEN = System.getenv("VAULT_TOKEN");


	static {
		LOGGER.info("Accessing the secrets from Vault ....");
		try {
			vaultConfig = new VaultConfig().address(VAULT_SERVER).token(VAULT_TOKEN).build();

			vault = new Vault(vaultConfig);

		} catch (VaultException e) {
		LOGGER.error("Something went wrong with the vault...",e);
			e.printStackTrace();
		}
	}

	private VaultDBConfig() {
	}

	public static String getSecret(String key) {
		LogicalResponse response = null;
		try {
			response = vault.logical().read("secret/phoenix/qa/database");
		} catch (VaultException e) {
			LOGGER.error("Something went wrong reading the vault response...",e);

			e.printStackTrace();
			return null;
		}

		Map<String, String> dataMap = response.getData();

		String secretValue =  dataMap.get(key);
		LOGGER.info("Secret found in the vault");

		return secretValue;

	}

}
