package org.etutoria.listingservice.services.impl;

import org.etutoria.listingservice.entities.Listing;
import org.etutoria.listingservice.entities.ListingImage;
import org.etutoria.listingservice.repositories.ListingImageRepository;
import org.etutoria.listingservice.repositories.ListingRepository;
import org.etutoria.listingservice.services.ListingImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ListingImageServiceImpl implements ListingImageService {
    private final ListingImageRepository listingImageRepository;
    private final ListingRepository listingRepository;

    public ListingImageServiceImpl(ListingImageRepository listingImageRepository, ListingRepository listingRepository) {
        this.listingImageRepository = listingImageRepository;
        this.listingRepository = listingRepository;
    }


    @Override
    public ListingImage uplaodImage(MultipartFile file) throws IOException {
        return listingImageRepository.save(ListingImage.builder().name(file.getOriginalFilename()).type(file.getContentType())
                .image(file.getBytes()).build());
    }

    @Override
    public ListingImage getImageDetails(Long id) throws IOException {
        final Optional<ListingImage> dbImage = listingImageRepository.findById(id);
        return ListingImage.builder().idImage(dbImage.get().getIdImage()).name(dbImage.get().getName())
                .type(dbImage.get().getType()).image(dbImage.get().getImage()).build();
    }

    @Override
    public ResponseEntity<byte[]> getImage(Long id) throws IOException {
        final Optional<ListingImage> dbImage = listingImageRepository.findById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
    }

    @Override
    public void deleteImage(Long id) {
        listingImageRepository.deleteById(id);


    }

    @Override
    public ListingImage uplaodImageListing(MultipartFile file, String listingId) throws IOException {
        Listing listing = listingRepository.findById(listingId).
                orElseThrow(() -> new IllegalArgumentException("Listing not found"));
        return listingImageRepository.save(ListingImage.builder().name(file.getOriginalFilename()).type(file.getContentType())
                .image(file.getBytes()).listing(listing).build());

    }

    @Override
    public List<ListingImage> getImagesParListing(String listingId) {
        return listingImageRepository.findAll().stream().filter(image -> image.getListing().getListingId().equals(listingId)).collect(Collectors.toList());
    }
}