package org.etutoria.usersservice.repositories;

import org.etutoria.usersservice.entities.InternalUser;
import org.etutoria.usersservice.entities.Status;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface InternalUserRepository extends MongoRepository<InternalUser, String> {
    Optional<InternalUser> findByEmail(String email);

    List<InternalUser> findAllByStatus(Status status);
}