package com.example.masterthesisbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class MasterThesisBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MasterThesisBeApplication.class, args);
	}

}
