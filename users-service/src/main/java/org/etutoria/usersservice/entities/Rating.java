package org.etutoria.usersservice.entities;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Builder
@Document(collection = "ratings")
public class Rating {
    @Id
    private String id;
    private String reviewerId; // L'ID de l'utilisateur qui publie la note/avis
    private String userId; // L'ID de l'utilisateur recevant la note
    private int rating; // Note donnée (ex : de 1 à 5)
    private String comment; // Avis sous forme de texte
    private LocalDateTime createdAt; // Timestamp de création
}