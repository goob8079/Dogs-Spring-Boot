package com.justice.dogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DogApplication {

	    private static final Logger logger = LoggerFactory.getLogger(DogApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DogApplication.class, args);
		logger.info("Application started successfully");
	}
}