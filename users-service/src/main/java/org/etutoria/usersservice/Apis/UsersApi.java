package org.etutoria.usersservice.Apis;
import org.etutoria.usersservice.model.Login;
import org.etutoria.usersservice.model.NewUser;
import org.etutoria.usersservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/users")
public class UsersApi {
    private final UserService userService;
    public UsersApi(UserService userService) {
        this.userService = userService;
    }
    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody NewUser newUser) {
        userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PutMapping("/{id}/send-verify-email")
    public ResponseEntity<?> sendVerifyEmailToUser(
            @PathVariable String id){
        userService.sendVerificationEmail(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Login login) {
        String token = userService.login(login);
        return ResponseEntity.ok(token);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(
            @PathVariable String id){
        userService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PutMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(
            @RequestParam String username){
        userService.forgotPassword(username);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    @GetMapping("/{id}/roles")
    public ResponseEntity<?> getUserRoles(@PathVariable String id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUserRoles(id));
    }
}