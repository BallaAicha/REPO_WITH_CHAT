package org.etutoria.listingservice.Dtos;





import java.util.List;

public class CategoryDTO {
    private Integer id;
    private String name;
    private String description;
    private List<ListingDTO> listings;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ListingDTO> getListings() {
        return listings;
    }

    public void setListings(List<ListingDTO> listings) {
        this.listings = listings;
    }
}
