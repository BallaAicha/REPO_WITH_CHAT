package org.etutoria.usersservice.service;

import lombok.RequiredArgsConstructor;
import org.etutoria.usersservice.entities.Rating;
import org.etutoria.usersservice.entities.InternalUser;
import org.etutoria.usersservice.repositories.InternalUserRepository;
import org.etutoria.usersservice.repositories.RatingRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final InternalUserRepository internalUserRepository;

    // Ajouter une nouvelle note et un commentaire
    public void rateUser(String userId, int rating, String comment) {
        // Vérifier que la note est valide
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        // Récupérer l'ID de l'utilisateur connecté
        String reviewerId = getCurrentUserId();

        // Vérifier que l'utilisateur recevant la note existe
        var user = internalUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        // Créer et sauvegarder une note
        Rating ratingEntity = Rating.builder()
                .reviewerId(reviewerId) // Utilisateur qui donne la note
                .userId(userId)         // Utilisateur qui reçoit la note
                .rating(rating)         // Note
                .comment(comment)       // Avis
                .createdAt(LocalDateTime.now()) // Date de l'avis
                .build();

        ratingRepository.save(ratingEntity);


        // Mettre à jour la note moyenne de l'utilisateur
        updateUserRating(userId);
    }

    // Récupérer toutes les notes et avis d'un utilisateur
    public List<Rating> getUserRatings(String userId) {
        return ratingRepository.findByUserId(userId);
    }

    // Méthode privée pour recalculer et mettre à jour la note moyenne d'un utilisateur
    private void updateUserRating(String userId) {
        List<Rating> ratings = ratingRepository.findByUserId(userId);

        if (ratings.isEmpty()) {
            // Si aucun avis, réinitialiser les valeurs
            var user = internalUserRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found."));
            user.setRating(0.0);
            user.setNumberOfRatings(0);
            internalUserRepository.save(user);
            return;
        }

        // Calculer la moyenne des avis
        double averageRating = ratings.stream()
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0.0);

        // Récupérer l'utilisateur et mettre à jour ses détails
        var user = internalUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found."));

        user.setRating(averageRating); // Mettre à jour la moyenne
        user.setNumberOfRatings(ratings.size());
        internalUserRepository.save(user);
    }

    // Récupère l'ID de l'utilisateur connecté depuis le token JWT
    public String getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        var principal = authentication.getPrincipal();
        // Extraire le "sub" comme userId
        if (principal instanceof Jwt jwt) {
            return jwt.getClaim("sub");
        }

        throw new RuntimeException("Unable to extract userId from authentication token");
    }
}