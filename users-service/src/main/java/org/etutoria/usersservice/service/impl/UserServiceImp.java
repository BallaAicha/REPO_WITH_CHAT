package org.etutoria.usersservice.service.impl;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.etutoria.usersservice.Config.EnvLoader;
import org.etutoria.usersservice.constantes.KeycloakConstantes;
import org.etutoria.usersservice.entities.ImageInternalUser;
import org.etutoria.usersservice.entities.InternalUser;
import org.etutoria.usersservice.entities.Status;
import org.etutoria.usersservice.model.Login;
import org.etutoria.usersservice.model.NewUser;
import org.etutoria.usersservice.repositories.ImageInternalUserRepository;
import org.etutoria.usersservice.repositories.InternalUserRepository;
import org.etutoria.usersservice.service.UserService;
import org.jetbrains.annotations.NotNull;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.util.List;
import java.util.Objects;
@Service
@Slf4j
public class UserServiceImp implements UserService {
    private final Keycloak keycloak;
    private final InternalUserRepository internalUserRepository;
    private final ImageInternalUserRepository imageInternalUserRepository;
    public UserServiceImp(Keycloak keycloak, InternalUserRepository internalUserRepository, ImageInternalUserRepository imageInternalUserRepository) {
        this.keycloak = keycloak;
        this.internalUserRepository = internalUserRepository;
        this.imageInternalUserRepository = imageInternalUserRepository;
    }
    @Override
    public void createUser(NewUser newUser) {
        if (internalUserRepository.findByEmail(newUser.email()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }
        UserRepresentation userRepresentation = new UserRepresentation();
        userRepresentation.setEnabled(true);
        userRepresentation.setFirstName(newUser.firstName());
        userRepresentation.setLastName(newUser.lastName());
        userRepresentation.setUsername(newUser.email());
        userRepresentation.setEmail(newUser.email());
        userRepresentation.setEmailVerified(false);
        userRepresentation.singleAttribute("numberOfViews", newUser.numberOfViews().toString());
        userRepresentation.singleAttribute("phoneNumber", newUser.phoneNumber());
        userRepresentation.singleAttribute("profilePicture", newUser.profilePicture());
        userRepresentation.singleAttribute("rating", newUser.rating().toString());
        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setValue(newUser.password());
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        userRepresentation.setCredentials(List.of(credentialRepresentation));
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.create(userRepresentation);
        System.out.println("Status code: " + response.getStatus());
        if (!Objects.equals(201, response.getStatus())) {
            throw new RuntimeException("Status code: " + response.getStatus());
        }
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(newUser.email(), true);
        if (userRepresentations.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        UserRepresentation userRepresentation1 = userRepresentations.get(0);
        System.out.println("User id: " + userRepresentation1.getId());
        sendVerificationEmail(userRepresentation1.getId());
        InternalUser internalUser = InternalUser.builder()
                .id(userRepresentation1.getId())
                .email(newUser.email())
                .password(newUser.password())
                .firstName(newUser.firstName())
                .lastName(newUser.lastName())
                .numberOfViews(0)
                .phoneNumber(newUser.phoneNumber())
                .profilePicture(null)
                .rating(0.0)
                .status(Status.ONLINE)
                .build();

        internalUserRepository.save(internalUser);
    }
    @Override
    public void disconnectUser(InternalUser internalUser) {
        var storedUser = internalUserRepository.findById(internalUser.getId())
                .orElse(null);
        if (storedUser != null) {
            storedUser.setStatus(Status.OFFLINE);
            internalUserRepository.save(storedUser);
        }


    }

    @Override
    public List<InternalUser> findConnectedUsers() {
        return internalUserRepository.findAllByStatus(Status.ONLINE);
    }





    @Override
    public List<InternalUser> getAllInternalUsers() {
        List<InternalUser> internalUsers = internalUserRepository.findAll();
        for (InternalUser user : internalUsers) {
            List<ImageInternalUser> images = imageInternalUserRepository.findByInternalUserId(user.getId());
            user.setImages(images);
        }
        return internalUsers;
    }


    @Override
    public InternalUser getInternalUser(String userId) {
        InternalUser internalUser = internalUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        List<ImageInternalUser> images = imageInternalUserRepository.findByInternalUserId(userId);
        internalUser.setImages(images);
        return internalUser;
    }

    @Override
    public void deleteInternalUser(String userId) {
        List<ImageInternalUser> images = imageInternalUserRepository.findByInternalUserId(userId);
        for (ImageInternalUser image : images) {
            imageInternalUserRepository.deleteById(image.getIdImage());
        }
        internalUserRepository.deleteById(userId);

    }


    @Override
    public void sendVerificationEmail(String userId) {
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();

    }
    @Override
    public String login(Login login) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", EnvLoader.getEnv("KEYCLOAK_GRANT_TYPE"));
        map.add("client_id", EnvLoader.getEnv("KEYCLOAK_CLIENT_ID"));
        map.add("username", login.username());
        map.add("password", login.password());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        ResponseEntity<String> response = restTemplate.exchange(
                KeycloakConstantes.SERVER_URL + "/realms/" + KeycloakConstantes.REALM + "/protocol/openid-connect/token",
                HttpMethod.POST,
                request,
                String.class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Failed to login: " + response.getStatusCode());
        }
    }

    @Override
    public String refreshToken(String refreshToken) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "refresh_token");
        map.add("client_id", "connect");
        map.add("refresh_token", refreshToken);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        log.info("Sending refresh token request with parameters: grant_type=refresh_token, client_id=connect, refresh_token={}", refreshToken);

        try {
            ResponseEntity<String> response = restTemplate.exchange(
                    KeycloakConstantes.SERVER_URL + "/realms/" + KeycloakConstantes.REALM + "/protocol/openid-connect/token",
                    HttpMethod.POST,
                    request,
                    String.class
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            } else {
                log.error("Failed to refresh token: {}", response.getStatusCode());
                throw new RuntimeException("Failed to refresh token: " + response.getStatusCode());
            }
        } catch (HttpClientErrorException e) {
            log.error("Failed to refresh token: {}", e.getStatusCode());
            throw new RuntimeException("Failed to refresh token: " + e.getStatusCode());
        }
    }


    @Override
    public void deleteUser(String userId) {
        UsersResource usersResource = getUsersResource();
        Response response = usersResource.delete(userId);
        System.out.println("Status code: " + response.getStatus());
        if (!Objects.equals(204, response.getStatus())) {
            throw new RuntimeException("Status code: " + response.getStatus());
        }
        System.out.println("User has been deleted");

        internalUserRepository.deleteById(userId);
    }
    @Override
    public void forgotPassword(String username) {
        UsersResource usersResource = getUsersResource();
        List<UserRepresentation> userRepresentations = usersResource.searchByUsername(username, true);
        if(userRepresentations.isEmpty()){
            throw new RuntimeException("User not found");
        }
        UserRepresentation userRepresentation = userRepresentations.get(0);
        usersResource.get(userRepresentation.getId()).executeActionsEmail(List.of("UPDATE_PASSWORD"));

    }
    private UsersResource getUsersResource() {
        return keycloak.realm(KeycloakConstantes.REALM).users();
    }
    @Override
    public UserResource getUser(String userId) {
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }
    @Override
    public List<RoleRepresentation> getUserRoles(String userId) {
        return getUser(userId).roles().realmLevel().listAll();
    }
    @Override
    public List<GroupRepresentation> getUserGroups(String userId) {
        return getUser(userId).groups();
    }
}