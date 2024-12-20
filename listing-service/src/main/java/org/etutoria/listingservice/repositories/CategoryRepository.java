package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
}