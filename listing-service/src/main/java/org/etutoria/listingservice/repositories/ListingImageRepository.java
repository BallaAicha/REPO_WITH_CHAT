package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.ListingImage;
import org.springframework.data.jpa.repository.JpaRepository;



public interface ListingImageRepository extends JpaRepository<ListingImage, Long> {
}