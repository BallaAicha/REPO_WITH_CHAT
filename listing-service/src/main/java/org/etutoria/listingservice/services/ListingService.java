package org.etutoria.listingservice.services;

import jakarta.servlet.http.HttpServletRequest;
import org.etutoria.listingservice.entities.Listing;

import java.util.List;

public interface ListingService {
    Listing createListing(Listing listing, HttpServletRequest request);
    Listing getListingById(String listingId);
    Listing updateListing(String listingId, Listing listingDTO);
    void deleteListing(String listingId);
    //List<Listing> getAllListings();
    List<Listing> getAllListings(HttpServletRequest request);
}