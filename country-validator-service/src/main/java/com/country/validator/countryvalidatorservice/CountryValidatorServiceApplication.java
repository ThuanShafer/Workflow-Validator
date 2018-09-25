package com.country.validator.countryvalidatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CountryValidatorServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(CountryValidatorServiceApplication.class, args);
	}
}
