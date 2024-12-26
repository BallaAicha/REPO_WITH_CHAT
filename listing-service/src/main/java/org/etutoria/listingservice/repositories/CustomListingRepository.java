package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.Listing;

import java.math.BigDecimal;
import java.util.List;

public interface CustomListingRepository {
    List<Listing> advancedSearch(String keyword, BigDecimal minPrice, BigDecimal maxPrice, String location, String categoryId, String sortField, boolean isAscending);
}
