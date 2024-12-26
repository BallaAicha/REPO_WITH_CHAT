package org.etutoria.usersservice.repositories;

import org.etutoria.usersservice.entities.Addresse;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AddresseRepository extends MongoRepository<Addresse, String> {

}
