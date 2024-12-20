package org.etutoria.usersservice.service.impl;

import org.etutoria.usersservice.entities.ImageInternalUser;
import org.etutoria.usersservice.entities.InternalUser;
import org.etutoria.usersservice.repositories.ImageInternalUserRepository;
import org.etutoria.usersservice.repositories.InternalUserRepository;
import org.etutoria.usersservice.service.ProfileImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProfileImageServiceImpl implements ProfileImageService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileImageServiceImpl.class);
    private final ImageInternalUserRepository imageInternalUserRepository;
    private final InternalUserRepository internalUserRepository;

    public ProfileImageServiceImpl(ImageInternalUserRepository imageInternalUserRepository, InternalUserRepository internalUserRepository) {
        this.imageInternalUserRepository = imageInternalUserRepository;
        this.internalUserRepository = internalUserRepository;
    }

    @Override
    public ImageInternalUser uplaodImage(MultipartFile file) throws IOException {
        return imageInternalUserRepository.save(ImageInternalUser.builder().name(file.getOriginalFilename()).type(file.getContentType())
                .image(file.getBytes()).build());
    }

    @Override
    public ImageInternalUser getImageDetails(String id) throws IOException {
        final Optional<ImageInternalUser> dbImage = imageInternalUserRepository.findById(id);
        return ImageInternalUser.builder().idImage(dbImage.get().getIdImage()).name(dbImage.get().getName())
                .type(dbImage.get().getType()).image(dbImage.get().getImage()).build();
    }

    @Override
    public ResponseEntity<byte[]> getImage(String id) throws IOException {
        final Optional<ImageInternalUser> dbImage = imageInternalUserRepository.findById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType()))
                .body(dbImage.get().getImage());
    }

    @Override
    public void deleteImage(String id) {
        imageInternalUserRepository.deleteById(id);
    }

    @Override
    public ImageInternalUser uplaodImageProfileByUserId(MultipartFile file, String id) throws IOException {
        logger.info("Uploading image for user ID: {}", id);
        InternalUser internalUser = internalUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return imageInternalUserRepository.save(ImageInternalUser.builder().name(file.getOriginalFilename()).type(file.getContentType())
                .image(file.getBytes()).internalUser(internalUser).build());
    }

    @Override
    public List<ImageInternalUser> getImagesParUser(String userId) {
        return imageInternalUserRepository.findAll().stream().filter(image -> image.getInternalUser().getId().equals(userId)).collect(Collectors.toList());
    }


}