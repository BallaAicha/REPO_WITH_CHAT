package org.etutoria.usersservice.repositories;

import org.etutoria.usersservice.entities.ImageInternalUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ImageInternalUserRepository extends MongoRepository<ImageInternalUser, String> {
    List<ImageInternalUser> findByInternalUserId(String userId);
}