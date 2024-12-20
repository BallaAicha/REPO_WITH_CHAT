package org.etutoria.listingservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.etutoria.listingservice.entities.Listing;
import org.etutoria.listingservice.restemplate.InternalUser;
import org.etutoria.listingservice.restemplate.UserService;
import org.etutoria.listingservice.services.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;
    private final UserService userService;


    public ListingController(ListingService listingService, UserService userService) {
        this.listingService = listingService;
        this.userService = userService;
    }

//    @PostMapping
//    public ResponseEntity<Listing> createListing(@RequestBody Listing listing) {
//        Listing createdListing = listingService.createListing(listing);
//        return ResponseEntity.ok(createdListing);
//    }

    @PostMapping
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing, HttpServletRequest request) {
        Listing createdListing = listingService.createListing(listing, request);
        return ResponseEntity.ok(createdListing);
    }

//    @PostMapping
//    public Listing createListing(@RequestBody Listing listing, HttpServletRequest request) {
//        String token = (String) request.getAttribute("token");
//        if (token == null) {
//            throw new IllegalArgumentException("Token cannot be null");
//        }
//        System.out.println("Token: " + token);
//
//        String userId = listing.getUserId();
//        if (userId == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
//        System.out.println("User ID: " + userId);
//
//        InternalUser internalUser = userService.getInternalUser(userId, token);
//        listing.setInternalUser(internalUser);
//        listing.setUserId(internalUser.getId());
//
//        return listingService.createListing(listing);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Listing> getListingById(@PathVariable String id) {
        Listing listing = listingService.getListingById(id);
        return ResponseEntity.ok(listing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Listing> updateListing(@PathVariable String id, @RequestBody Listing listing) {
        Listing updatedListing = listingService.updateListing(id, listing);
        return ResponseEntity.ok(updatedListing);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable String id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }

//    @GetMapping
//    public ResponseEntity<List<Listing>> getAllListings() {
//        List<Listing> listings = listingService.getAllListings();
//        return ResponseEntity.ok(listings);
//    }
@GetMapping
public ResponseEntity<List<Listing>> getAllListings(HttpServletRequest request) {
    List<Listing> listings = listingService.getAllListings(request);
    return ResponseEntity.ok(listings);
}
}