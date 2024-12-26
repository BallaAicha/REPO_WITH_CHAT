package org.etutoria.listingservice.controller;

import lombok.RequiredArgsConstructor;
import org.etutoria.listingservice.entities.Favorite;

import org.etutoria.listingservice.services.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    // Endpoint pour ajouter une annonce aux favoris
    @PostMapping("/add")
    public ResponseEntity<String> addToFavorites(@RequestParam String listingId) {
        favoriteService.addListingToFavorites(listingId);
        return ResponseEntity.ok("Listing added to favorites successfully.");
    }

    // Endpoint pour supprimer une annonce des favoris
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeFromFavorites(@RequestParam String listingId) {
        favoriteService.removeListingFromFavorites(listingId);
        return ResponseEntity.ok("Listing removed from favorites successfully.");
    }

    // Endpoint pour récupérer les favoris de l'utilisateur connecté
    @GetMapping
    public ResponseEntity<List<Favorite>> getUserFavorites() {
        List<Favorite> favorites = favoriteService.getUserFavorites();
        return ResponseEntity.ok(favorites);
    }

    // Endpoint pour vérifier si une annonce est dans les favoris
    @GetMapping("/isFavorite")
    public ResponseEntity<Boolean> isFavorite(@RequestParam String listingId) {
        boolean isFavorite = favoriteService.isFavorite(listingId);
        return ResponseEntity.ok(isFavorite);
    }
}