package com.numble.carot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //jpa Auditing Enable(EntityListener)
public class CarotApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarotApplication.class, args);
	}

}
