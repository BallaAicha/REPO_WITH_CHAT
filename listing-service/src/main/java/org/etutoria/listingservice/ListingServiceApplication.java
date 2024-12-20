package org.etutoria.listingservice;

import org.etutoria.listingservice.entities.ParentCategory;
import org.etutoria.listingservice.repositories.ParentCategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
public class ListingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ParentCategoryRepository parentCategoryRepository) {
		return args -> {
			ParentCategory electronics = new ParentCategory();
			electronics.setName("Electronics");
			parentCategoryRepository.save(electronics);

			ParentCategory homeAppliances = new ParentCategory();
			homeAppliances.setName("Home Appliances");
			parentCategoryRepository.save(homeAppliances);

			ParentCategory clothing = new ParentCategory();
			clothing.setName("Clothing");
			parentCategoryRepository.save(clothing);

			ParentCategory furniture = new ParentCategory();
			furniture.setName("Furniture");
			parentCategoryRepository.save(furniture);

			ParentCategory books = new ParentCategory();
			books.setName("Books");
			parentCategoryRepository.save(books);

			ParentCategory sports = new ParentCategory();
			sports.setName("Sports");
			parentCategoryRepository.save(sports);

			ParentCategory toys = new ParentCategory();
			toys.setName("Toys");
			parentCategoryRepository.save(toys);

			ParentCategory beauty = new ParentCategory();
			beauty.setName("Beauty");
			parentCategoryRepository.save(beauty);

			ParentCategory health = new ParentCategory();
			health.setName("Health");
			parentCategoryRepository.save(health);
		};
	}
}