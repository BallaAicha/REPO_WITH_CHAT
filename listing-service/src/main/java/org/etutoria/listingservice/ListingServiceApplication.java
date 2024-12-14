package org.etutoria.listingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
public class ListingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListingServiceApplication.class, args);
	}
}