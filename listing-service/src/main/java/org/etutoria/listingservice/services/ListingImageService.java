package org.etutoria.listingservice.services;

import org.etutoria.listingservice.entities.ListingImage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ListingImageService {
    ListingImage uplaodImage(MultipartFile file) throws IOException;
    ListingImage getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id) ;
    ListingImage uplaodImageListing(MultipartFile file,String listingId) throws IOException;
    List<ListingImage> getImagesParListing(String listingId);
}