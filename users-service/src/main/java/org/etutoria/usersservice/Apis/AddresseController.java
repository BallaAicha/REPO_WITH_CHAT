package org.etutoria.usersservice.Apis;

import org.etutoria.usersservice.entities.Addresse;
import org.etutoria.usersservice.service.impl.AddresseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;



import java.util.Optional;

@RestController
@RequestMapping("/api/addresses")
public class AddresseController {

    private  final AddresseService addresseService;

    public AddresseController(AddresseService addresseService) {
        this.addresseService = addresseService;
    }

    @PostMapping
    public ResponseEntity<Addresse> createAddresse(@RequestBody Addresse addresse, @AuthenticationPrincipal Jwt principal) {
        String userId = principal.getClaim("sub");
        Addresse savedAddresse = addresseService.saveAddresse(addresse, userId);
        return ResponseEntity.ok(savedAddresse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Addresse> getAddresseById(@PathVariable String id) {
        Optional<Addresse> addresse = addresseService.getAddresseById(id);
        return addresse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddresse(@PathVariable String id) {
        addresseService.deleteAddresse(id);
        return ResponseEntity.noContent().build();
    }
}
