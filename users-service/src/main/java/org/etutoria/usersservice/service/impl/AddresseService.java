package org.etutoria.usersservice.service.impl;

import org.etutoria.usersservice.entities.Addresse;
import org.etutoria.usersservice.entities.InternalUser;
import org.etutoria.usersservice.repositories.AddresseRepository;
import org.etutoria.usersservice.repositories.InternalUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddresseService {
    private final AddresseRepository addresseRepository;
    private final InternalUserRepository internalUserRepository;

    public AddresseService(AddresseRepository addresseRepository, InternalUserRepository internalUserRepository) {
        this.addresseRepository = addresseRepository;
        this.internalUserRepository = internalUserRepository;
    }

    public Addresse saveAddresse(Addresse addresse, String userId) {
        if (addresse.getId() == null || addresse.getId().isEmpty()) {
            addresse.setId(UUID.randomUUID().toString());
        }
        Optional<InternalUser> userOptional = internalUserRepository.findById(userId);
        if (userOptional.isPresent()) {
            InternalUser user = userOptional.get();
            user.setAddress(addresse);
            internalUserRepository.save(user);
            return addresseRepository.save(addresse);
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    public Optional<Addresse> getAddresseById(String id) {
        return addresseRepository.findById(id);
    }

    public void deleteAddresse(String id) {
        addresseRepository.deleteById(id);
    }
}
