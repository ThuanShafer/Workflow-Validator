package com.branch.validator.branchvalidatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class BranchValidatorServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(BranchValidatorServiceApplication.class, args);
	}
}
