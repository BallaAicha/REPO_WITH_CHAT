package org.etutoria.usersservice.service;

import org.etutoria.usersservice.entities.ImageInternalUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProfileImageService {
    ImageInternalUser uplaodImage(MultipartFile file) throws IOException;
    ImageInternalUser getImageDetails(Long id) throws IOException;
    ResponseEntity<byte[]> getImage(Long id) throws IOException;
    void deleteImage(Long id) ;
    ImageInternalUser uplaodImageProfileByUserId(MultipartFile file,String id) throws IOException;
    List<ImageInternalUser> getImagesParUser(String userId);


}
