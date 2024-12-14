package org.etutoria.listingservice.Dtos;

import lombok.Data;
import org.etutoria.listingservice.entities.ListingStatus;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


public class ListingDTO {
    private String listingId;
    private String userId;
    private String title;
    private String description;
    private BigDecimal price;
    private String location;
    private ListingStatus status;
    private Date createdAt;
    private Date updatedAt;
    private Integer categoryId;
    private List<ListingImageDTO> images;
    private String imagePath;

    public String getListingId() {
        return listingId;
    }

    public void setListingId(String listingId) {
        this.listingId = listingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public ListingStatus getStatus() {
        return status;
    }

    public void setStatus(ListingStatus status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public List<ListingImageDTO> getImages() {
        return images;
    }

    public void setImages(List<ListingImageDTO> images) {
        this.images = images;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}