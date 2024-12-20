package org.etutoria.listingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.etutoria.listingservice.restemplate.InternalUser;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;
import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Listing extends BaseEntity {
    @Id
    private String listingId;
    private String userId;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private ListingStatus status;
    @DBRef(lazy = true)
    private Category category;
    @DBRef(lazy = true)
    private List<ListingImage> images;
    private String imagePath;
    @Transient
    private InternalUser internalUser;
}