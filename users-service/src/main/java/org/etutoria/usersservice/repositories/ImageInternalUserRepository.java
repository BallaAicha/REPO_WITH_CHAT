package org.etutoria.usersservice.repositories;


import org.etutoria.usersservice.entities.ImageInternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageInternalUserRepository extends JpaRepository<ImageInternalUser, Long> {
}