package org.etutoria.listingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class ListingImage {
    @Id
    private String idImage;
    private String name;
    private String type;
    private byte[] image;
    @DBRef
    @JsonIgnore
    private Listing listing;
}