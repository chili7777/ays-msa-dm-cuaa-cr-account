package com.pichincha.dm.cuaa.account;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Boots the account microservice application context, enabling component scanning, auto-configuration, and runtime initialization required to expose HTTP endpoints and support service execution.
 */
@SpringBootApplication
public class AysMsaDmCuaaCrAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(AysMsaDmCuaaCrAccountApplication.class, args);
	}

}