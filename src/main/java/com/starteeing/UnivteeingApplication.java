package com.starteeing;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class UnivteeingApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnivteeingApplication.class, args);
	}

}
