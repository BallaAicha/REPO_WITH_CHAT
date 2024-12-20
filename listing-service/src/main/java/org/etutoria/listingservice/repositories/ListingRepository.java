package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<Listing, String> {
}