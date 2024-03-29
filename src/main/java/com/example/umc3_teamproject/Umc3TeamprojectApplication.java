package com.example.umc3_teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Umc3TeamprojectApplication {

	static {
		System.setProperty("com.amazonaws.sdk.disableEc2Metadata", "true");
	}
	public static void main(String[] args) {

		SpringApplication.run(GettingStartedSpringAwsS3Application.class, args);
	}

}
