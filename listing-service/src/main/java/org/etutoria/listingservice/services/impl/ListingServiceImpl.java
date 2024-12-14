package org.etutoria.listingservice.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.etutoria.listingservice.entities.Category;
import org.etutoria.listingservice.entities.Listing;
import org.etutoria.listingservice.entities.ListingImage;
import org.etutoria.listingservice.repositories.CategoryRepository;
import org.etutoria.listingservice.repositories.ListingRepository;
import org.etutoria.listingservice.restemplate.InternalUser;
import org.etutoria.listingservice.restemplate.UserService;
import org.etutoria.listingservice.services.ListingService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {
    private final ListingRepository listingRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;

    public ListingServiceImpl(ListingRepository listingRepository, CategoryRepository categoryRepository, UserService userService) {
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
    }

//    @Override
//    public Listing createListing(Listing listing) {
//        if (listing.getListingId() == null || listing.getListingId().isEmpty()) {
//            listing.setListingId(UUID.randomUUID().toString());
//        }
//        Category category = categoryRepository.findById(listing.getCategory().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
//        listing.setCategory(category);
//        return listingRepository.save(listing);
//    }

    @Override
    public Listing createListing(Listing listing, HttpServletRequest request) {
        if (listing.getListingId() == null || listing.getListingId().isEmpty()) {
            listing.setListingId(UUID.randomUUID().toString());
        }
        Category category = categoryRepository.findById(listing.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        listing.setCategory(category);

        String token = (String) request.getAttribute("token");
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        // Décoder le token pour obtenir le sub (userId)
        DecodedJWT decodedJWT = JWT.decode(token);
        String userId = decodedJWT.getSubject();
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }

        InternalUser internalUser = userService.getInternalUser(userId, token);
        listing.setInternalUser(internalUser);
        listing.setUserId(internalUser.getId());

        return listingRepository.save(listing);
    }

    @Override
    public Listing getListingById(String listingId) {
        return listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
    }

    @Override
    public Listing updateListing(String listingId, Listing Listing) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        Category category = categoryRepository.findById(Listing.getCategory().getId())
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
        listing.setCategory(category);
        listing.setTitle(Listing.getTitle());
        listing.setDescription(Listing.getDescription());
        listing.setPrice(Listing.getPrice());
        listing.setImages(Listing.getImages());
        return listingRepository.save(listing);
    }

    @Override
    public void deleteListing(String listingId) {
        listingRepository.deleteById(listingId);

    }

    @Override
    public List<Listing> getAllListings() {
        return listingRepository.findAll();
    }

}