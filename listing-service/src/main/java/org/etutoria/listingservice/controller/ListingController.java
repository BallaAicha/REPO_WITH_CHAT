package org.etutoria.listingservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.etutoria.listingservice.entities.Listing;
import org.etutoria.listingservice.restemplate.InternalUser;
import org.etutoria.listingservice.restemplate.UserService;
import org.etutoria.listingservice.services.ListingService;
import org.etutoria.listingservice.services.impl.ImageUploadService;
import org.etutoria.listingservice.utils.TokenUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;
    private final UserService userService;
    private final ImageUploadService imageUploadService;


    public ListingController(ListingService listingService, UserService userService,ImageUploadService imageUploadService) {
        this.listingService = listingService;
        this.userService = userService;
        this.imageUploadService = imageUploadService;
    }

    @PostMapping
    public ResponseEntity<Listing> createListing(@RequestBody Listing listing, HttpServletRequest request) {
        Listing createdListing = listingService.createListing(listing, request);
        return ResponseEntity.ok(createdListing);
    }



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


@GetMapping
public ResponseEntity<List<Listing>> getAllListings(HttpServletRequest request) {
    List<Listing> listings = listingService.getAllListings(request);
    return ResponseEntity.ok(listings);
}

    @GetMapping("/search")
    public Page<Listing> searchListings(
            @RequestParam(required = false) String keyword,
            Pageable pageable
    ) {
        return listingService.searchByKeyword(keyword, pageable);
    }

    @GetMapping("/search/advanced")
    public List<Listing> advancedSearchListings(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false, defaultValue = "false") boolean isAscending,
            @RequestParam(required = false, defaultValue = "price") String sortField
    ) {
        return listingService.advancedSearch(keyword, minPrice, maxPrice, location, categoryId, sortField, isAscending);
    }

    /**
     * Endpoint pour uploader une preuve de paiement
     *
     * @param listingId L'identifiant de l'annonce
     * @param file      L'image de la preuve de paiement
     * @param request   La requête HTTP avec l'utilisateur
     * @return Une réponse indiquant le statut de l'opération
     */
    @PostMapping("/{listingId}/payment-proof")
    public ResponseEntity<String> uploadPaymentProof(@PathVariable String listingId,
                                                     @RequestParam("file") MultipartFile file,
                                                     HttpServletRequest request) {
        try {
            // Charger l'image
            String proofImagePath = imageUploadService.uploadImage(file);

            // Récupérer l'identifiant de l'utilisateur (par exemple depuis le token JWT)
            String token = (String) request.getAttribute("token");
            String userId = TokenUtils.extractUserIdFromToken(token);

            // Enregistrer la preuve pour l'annonce
            listingService.uploadPaymentProof(listingId, proofImagePath, userId);

            return ResponseEntity.ok("Preuve de paiement téléchargée avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur: " + e.getMessage());
        }
    }

    /**
     * Endpoint pour valider ou rejeter un paiement
     *
     * @param listingId L'id de l'annonce
     * @param isValid   Booléen indiquant si le paiement est validé ou rejeté
     * @return Une réponse indiquant le statut de l'opération
     */
    @PutMapping("/{listingId}/validate-payment")
    public ResponseEntity<String> validatePayment(@PathVariable String listingId, @RequestParam boolean isValid) {
        try {
            listingService.validatePayment(listingId, isValid);
            if (isValid) {
                return ResponseEntity.ok("Paiement validé avec succès.");
            } else {
                return ResponseEntity.ok("Preuve de paiement rejetée avec succès.");
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Erreur: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue : " + e.getMessage());
        }
    }

    /**
     * Endpoint pour gérer automatiquement les annonces non payées
     *
     * @return Une réponse indiquant l'issue de l'opération
     */
    @PostMapping("/handle-unpaid")
    public ResponseEntity<String> handleUnpaidListings() {
        try {
            listingService.handleUnpaidListings();
            return ResponseEntity.ok("Les annonces non payées ont été traitées avec succès.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Une erreur est survenue : " + e.getMessage());
        }
    }
}