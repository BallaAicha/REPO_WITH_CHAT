package org.etutoria.listingservice.services;

import org.etutoria.listingservice.entities.ListingImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ListingImageService {
    ListingImage uplaodImage(MultipartFile file) throws IOException;
    ListingImage getImageDetails(String id) throws IOException;
    ResponseEntity<byte[]> getImage(String id) throws IOException;
    void deleteImage(String id) ;
    //ListingImage uplaodImageListing(MultipartFile file,String listingId) throws IOException;
    List<ListingImage> getImagesParListing(String listingId);
    List<ListingImage> uploadImageListing(List<MultipartFile> files, String listingId) throws IOException;
}