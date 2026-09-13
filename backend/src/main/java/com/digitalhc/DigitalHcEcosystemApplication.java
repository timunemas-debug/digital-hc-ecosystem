package com.digitalhc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DigitalHcEcosystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalHcEcosystemApplication.class, args);
	}

}