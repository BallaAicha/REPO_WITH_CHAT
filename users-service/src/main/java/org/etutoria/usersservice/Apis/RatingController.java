package org.etutoria.usersservice.Apis;

import lombok.RequiredArgsConstructor;
import org.etutoria.usersservice.entities.Rating;
import org.etutoria.usersservice.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    // Endpoint pour ajouter une note et un avis
    @PostMapping("/{userId}")
    public ResponseEntity<String> rateUser(
            @PathVariable String userId, // L'utilisateur recevant la note
            @RequestParam int rating,
            @RequestParam String comment) {
        ratingService.rateUser(userId, rating, comment);
        return ResponseEntity.ok("Rating and review added successfully.");
    }

    // Endpoint pour récupérer toutes les avis et notes concernant un utilisateur
    @GetMapping("/{userId}")
    public ResponseEntity<List<Rating>> getUserRatings(
            @PathVariable String userId) {
        List<Rating> ratings = ratingService.getUserRatings(userId);
        return ResponseEntity.ok(ratings);
    }
}