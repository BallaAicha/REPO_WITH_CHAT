package org.etutoria.listingservice.repositories;

import org.etutoria.listingservice.entities.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FavoriteRepository extends MongoRepository<Favorite, String> {
    // Récupérer les favoris par utilisateur
    List<Favorite> findByUserId(String userId);

    // Vérifier si une annonce est déjà dans les favoris d'un utilisateur
    boolean existsByUserIdAndListingId(String userId, String listingId);

    // Supprimer un favori (par utilisateur et annonce)
    void deleteByUserIdAndListingId(String userId, String listingId);
}