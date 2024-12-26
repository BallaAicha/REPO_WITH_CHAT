package org.etutoria.listingservice.services;

import lombok.RequiredArgsConstructor;
import org.etutoria.listingservice.entities.Favorite;
import org.etutoria.listingservice.repositories.FavoriteRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    // Obtenir userId depuis le token JWT
    public String getCurrentUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authenticated user found");
        }

        var principal = authentication.getPrincipal();
        if (principal instanceof org.springframework.security.oauth2.jwt.Jwt jwt) {
            return jwt.getClaim("sub"); // Récupérez le "sub" comme userId
        }

        throw new RuntimeException("Unable to extract userId from authentication token");
    }

    // Ajouter une annonce aux favoris
    public void addListingToFavorites(String listingId) {
        String userId = getCurrentUserId(); // Récupérer l'utilisateur connecté

        if (favoriteRepository.existsByUserIdAndListingId(userId, listingId)) {
            throw new RuntimeException("The listing is already in favorites.");
        }

        Favorite favorite = Favorite.builder()
                .userId(userId)
                .listingId(listingId)
                .addedAt(LocalDateTime.now())
                .build();

        favoriteRepository.save(favorite);
    }

    // Supprimer une annonce des favoris
    public void removeListingFromFavorites(String listingId) {
        String userId = getCurrentUserId(); // Récupérer l'utilisateur connecté

        if (!favoriteRepository.existsByUserIdAndListingId(userId, listingId)) {
            throw new RuntimeException("The listing is not in the user's favorites.");
        }

        favoriteRepository.deleteByUserIdAndListingId(userId, listingId);
    }

    // Récupérer toutes les annonces favorites d'un utilisateur
    public List<Favorite> getUserFavorites() {
        String userId = getCurrentUserId(); // Récupérer l'utilisateur connecté
        return favoriteRepository.findByUserId(userId);
    }

    // Vérifier si une annonce est dans les favoris de l'utilisateur
    public boolean isFavorite(String listingId) {
        String userId = getCurrentUserId(); // Récupérer l'utilisateur connecté
        return favoriteRepository.existsByUserIdAndListingId(userId, listingId);
    }
}