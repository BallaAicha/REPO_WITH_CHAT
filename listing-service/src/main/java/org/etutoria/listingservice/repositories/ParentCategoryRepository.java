package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.ParentCategory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParentCategoryRepository extends MongoRepository<ParentCategory, String> {
}