package org.etutoria.listingservice.services.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import org.etutoria.listingservice.entities.Category;
import org.etutoria.listingservice.entities.Listing;
import org.etutoria.listingservice.entities.ListingImage;
import org.etutoria.listingservice.entities.ListingStatus;
import org.etutoria.listingservice.repositories.CategoryRepository;
import org.etutoria.listingservice.repositories.ListingImageRepository;
import org.etutoria.listingservice.repositories.ListingRepository;
import org.etutoria.listingservice.restemplate.InternalUser;
import org.etutoria.listingservice.restemplate.UserService;
import org.etutoria.listingservice.scheduler.NotificationService;
import org.etutoria.listingservice.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ListingServiceImpl implements ListingService {
    private final ListingRepository listingRepository;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final NotificationService notificationService;

    private final ListingImageRepository listingImageRepository;

    public ListingServiceImpl(ListingRepository listingRepository, CategoryRepository categoryRepository, UserService userService, ListingImageRepository listingImageRepository, NotificationService notificationService) {
        this.listingRepository = listingRepository;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.listingImageRepository = listingImageRepository;
        this.notificationService = notificationService;
    }


//
//    @Override
//    public Listing createListing(Listing listing, HttpServletRequest request) {
//        if (listing.getListingId() == null || listing.getListingId().isEmpty()) {
//            listing.setListingId(UUID.randomUUID().toString());
//        }
//        Category category = categoryRepository.findById(listing.getCategory().getId())
//                .orElseThrow(() -> new IllegalArgumentException("Category not found"));
//        listing.setCategory(category);
//
//
//
//        String token = (String) request.getAttribute("token");
//        if (token == null) {
//            throw new IllegalArgumentException("Token cannot be null");
//        }
//
//
//
//        // Décoder le token pour obtenir le sub (userId)
//        DecodedJWT decodedJWT = JWT.decode(token);
//        String userId = decodedJWT.getSubject();
//        if (userId == null) {
//            throw new IllegalArgumentException("User ID cannot be null");
//        }
//
//        InternalUser internalUser = userService.getInternalUser(userId, token);
//        listing.setInternalUser(internalUser);
//        listing.setUserId(internalUser.getId());
//
//        return listingRepository.save(listing);
//    }
@Override
public Listing createListing(Listing listing, HttpServletRequest request) {
    if (listing.getListingId() == null || listing.getListingId().isEmpty()) {
        listing.setListingId(UUID.randomUUID().toString());
    }

    // Vérifier et attacher la catégorie
    Category category = categoryRepository.findById(listing.getCategory().getId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found"));
    listing.setCategory(category);

    // Récupération du token et décodage pour définir le `userId`
    String token = (String) request.getAttribute("token");
    if (token == null) {
        throw new IllegalArgumentException("Token cannot be null");
    }
    DecodedJWT decodedJWT = JWT.decode(token);
    String userId = decodedJWT.getSubject();
    if (userId == null) {
        throw new IllegalArgumentException("User ID cannot be null");
    }

    InternalUser internalUser = userService.getInternalUser(userId, token);
    listing.setInternalUser(internalUser);
    listing.setUserId(internalUser.getId());

    // Initialiser les champs liés au paiement
    listing.setStatus(ListingStatus.WAITING_FOR_PAYMENT); // L'utilisateur a jusqu'à 3 jours pour payer
    listing.setPaymentPending(true); // Le paiement est en attente
    listing.setPaymentDeadline(LocalDateTime.now().plusDays(3)); // Fixer une date limite de 3 jours

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
    public List<Listing> getAllListings(HttpServletRequest request) {
        List<Listing> listings = listingRepository.findAll();
        String token = (String) request.getAttribute("token");
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null");
        }

        for (Listing listing : listings) {
            List<ListingImage> images = listingImageRepository.findByListing_ListingId(listing.getListingId());
            listing.setImages(images);

            InternalUser internalUser = userService.getInternalUser(listing.getUserId(), token);
            listing.setInternalUser(internalUser);
        }
        return listings;
    }



    @Override
    public List<Listing> advancedSearch(String keyword, BigDecimal minPrice, BigDecimal maxPrice, String location, String categoryId, String sortField, boolean isAscending) {
        return listingRepository.advancedSearch(keyword, minPrice, maxPrice, location, categoryId, sortField, isAscending);
    }

    @Override
    public Page<Listing> searchByKeyword(String keyword, Pageable pageable) {
        return listingRepository.findByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable);
    }

    @Override
    public void uploadPaymentProof(String listingId, String proofImageUrl, String userId) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing non trouvé."));
        if (!listing.getUserId().equals(userId)) {
            throw new RuntimeException("Vous n'êtes pas autorisé à envoyer une preuve pour cette annonce.");
        }
        if (!listing.isPaymentPending()) {
            throw new RuntimeException("Cette annonce ne nécessite pas de paiement.");
        }
        listing.setPaymentProofImage(proofImageUrl);
        listing.setStatus(ListingStatus.AWAITING_VALIDATION);
        listingRepository.save(listing);
        notificationService.notifyAdmin("Une nouvelle preuve de paiement a été soumise pour l'annonce " + listing.getTitle());
    }

    @Override
    public void validatePayment(String listingId, boolean isValid) {
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new IllegalArgumentException("Listing not found"));

        if (isValid) {
            // Valider le paiement
            listing.setPaymentPending(false);
            listing.setStatus(ListingStatus.PAID); // Paiement validé
            listingRepository.save(listing);
            // Notifier l'utilisateur
            notificationService.sendNotification(
                    listing.getUserId(),
                    "Votre paiement pour l'annonce \"" + listing.getTitle() + "\" a été validé. Merci!"
            );
        } else {
            // Rejeter la preuve de paiement
            listing.setStatus(ListingStatus.WAITING_FOR_PAYMENT); // Retourner au statut précédent
            listing.setPaymentProofImage(null); // Supprimer la preuve rejetée
            listingRepository.save(listing);

            // Notifier l'utilisateur
            notificationService.sendNotification(
                    listing.getUserId(),
                    "Votre preuve de paiement soumise pour l'annonce \"" + listing.getTitle() + "\" a été rejetée. Veuillez soumettre une nouvelle preuve."
            );
        }
    }

    /**
     * Désactiver une annonce si le paiement n'est pas effectué
     */
    @Override
    public void handleUnpaidListings() {
        // Liste des annonces non payées dont le délai est expiré
        List<Listing> unpaidListings = listingRepository.findAll().stream()
                .filter(listing -> listing.isPaymentPending() &&
                        LocalDateTime.now().isAfter(listing.getPaymentDeadline()))
                .collect(Collectors.toList());

        for (Listing listing : unpaidListings) {
            // Mise à jour de l'état de l'annonce
            listing.setStatus(ListingStatus.INACTIVE);
            listingRepository.save(listing);

            // Notification utilisateur pour paiement non effectué
            notificationService.sendReminder(
                    listing.getUserId(),
                    "Votre annonce \"" + listing.getTitle() + "\" a été désactivée faute de paiement dans les délais impartis."
            );
        }
    }

}