package org.etutoria.listingservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class Category {
    @Id
    private String id;
    private String name;
    private String description;
    @JsonIgnore
    @DBRef
    private List<Listing> listings;
    @DBRef
    private ParentCategory parentCategory;
}