package org.etutoria.usersservice.entities;

import lombok.Data;
import java.util.List;

@Data
public class Listing {
    private String listingId;
    private String userId;
    private String title;
    private String description;
    private double price;
    private String location;
    private String status;
    private Category category;
    private List<Image> images;
}