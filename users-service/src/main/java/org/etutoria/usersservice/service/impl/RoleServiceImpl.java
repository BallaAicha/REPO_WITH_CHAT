package org.etutoria.usersservice.service.impl;
import lombok.extern.slf4j.Slf4j;
import org.etutoria.usersservice.constantes.KeycloakConstantes;
import org.etutoria.usersservice.service.RoleService;
import org.etutoria.usersservice.service.UserService;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.springframework.stereotype.Service;
import java.util.Collections;
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    private final UserService userService;
    private final Keycloak keycloak;
    public RoleServiceImpl(UserService userService, Keycloak keycloak) {
        this.userService = userService;
        this.keycloak = keycloak;
    }
    @Override
    public void assignRole(String userId, String roleName) {
        try {
            System.out.println("Assigning role " + roleName + " to user " + userId);
            UserResource userResource = userService.getUser(userId);
            RoleRepresentation roleToAdd = keycloak.realm(KeycloakConstantes.REALM)
                    .roles().get(roleName).toRepresentation();
            userResource.roles().realmLevel().add(Collections.singletonList(roleToAdd));
           System.out.println("Role " + roleName + " assigned to user " + userId);
        } catch (Exception e) {
            System.out.println("Error assigning role " + roleName + " to user " + userId);
            throw e;
        }
    }
    @Override
    public void deleteRoleFromUser(String userId, String roleName) {
        // Get the UserResource for the specified userId
        UserResource userResource = userService.getUser(userId);
        // Obtain the RoleRepresentation for the roleName
        RoleRepresentation roleToRemove = keycloak.realm(KeycloakConstantes.REALM)
                .roles().get(roleName).toRepresentation();
        // Remove the role from the user
        userResource.roles().realmLevel().remove(Collections.singletonList(roleToRemove));

    }


}
