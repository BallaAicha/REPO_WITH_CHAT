package org.etutoria.listingservice.services;

import jakarta.servlet.http.HttpServletRequest;
import org.etutoria.listingservice.entities.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;

public interface ListingService {
    Listing createListing(Listing listing, HttpServletRequest request);
    Listing getListingById(String listingId);
    Listing updateListing(String listingId, Listing listingDTO);
    void deleteListing(String listingId);
    //List<Listing> getAllListings();
    List<Listing> getAllListings(HttpServletRequest request);
    Page<Listing> searchByKeyword(String keyword, Pageable pageable);

    // Recherche avancée avec filtres
    List<Listing> advancedSearch(
            String keyword,
            BigDecimal minPrice,
            BigDecimal maxPrice,
            String location,
            String categoryId,
            String sortField,
            boolean isAscending
    );
    // Nouvelle méthode : Uploader une preuve de paiement
    void uploadPaymentProof(String listingId, String proofImageUrl, String userId);

    // Nouvelle méthode : Validation ou rejet par administrateur
    void validatePayment(String listingId, boolean isValid);

    // Nouvelle méthode : Gérer les annonces non payées (désactiver après le délai)
    void handleUnpaidListings();

}