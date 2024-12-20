package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.ListingImage;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ListingImageRepository extends MongoRepository<ListingImage, String> {

    List<ListingImage> findByListing_ListingId(String listingId);
}