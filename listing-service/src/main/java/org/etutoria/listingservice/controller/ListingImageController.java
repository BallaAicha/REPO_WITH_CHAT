package org.etutoria.listingservice.controller;

import org.etutoria.listingservice.entities.ListingImage;
import org.etutoria.listingservice.services.ListingImageService;
import org.etutoria.listingservice.services.ListingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/listing-images")
public class ListingImageController {
    private final  ListingImageService listingImageService;
    public ListingImageController(ListingImageService listingImageService) {
        this.listingImageService = listingImageService;

    }

    @PostMapping(value = "/uplaodImageListing/{listingId}" )
    public ListingImage uploadMultiImages(@RequestParam("image") MultipartFile file,
                                   @PathVariable("listingId") String listingId)
            throws IOException {
        return listingImageService.uplaodImageListing(file, listingId);
    }

    @RequestMapping(value = "/getImagesListing/{listingId}" , method = RequestMethod.GET)
    public List<ListingImage> getImagesParListing(@PathVariable("listingId") String listingId)
            throws IOException {
        return listingImageService.getImagesParListing(listingId);
    }

    @RequestMapping(value = "/get/info/{id}" , method = RequestMethod.GET)
    public ListingImage getImageDetails(@PathVariable("id") String id) throws IOException {
        return listingImageService.getImageDetails(id);
    }


    @RequestMapping(value = "/load/{id}" , method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) throws IOException
    {
        return listingImageService.getImage(id);
    }


    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("id") String id){
        listingImageService.deleteImage(id);
    }



    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public ListingImage UpdateImage(@RequestParam("image")MultipartFile file) throws IOException {
        return listingImageService.uplaodImage(file);
    }


}