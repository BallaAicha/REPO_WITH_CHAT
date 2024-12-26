package org.etutoria.listingservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {
    @Id
    private String id;
    private String userId;
    private String listingId;
    private LocalDateTime addedAt;
}