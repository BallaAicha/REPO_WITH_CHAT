package org.etutoria.usersservice.repositories;



import org.etutoria.usersservice.entities.InternalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InternalUserRepository extends JpaRepository<InternalUser, String> {
    Optional<InternalUser> findByEmail(String email);
}