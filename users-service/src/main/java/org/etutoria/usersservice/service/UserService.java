package org.etutoria.usersservice.service;
import org.etutoria.usersservice.entities.InternalUser;
import org.etutoria.usersservice.model.Login;
import org.etutoria.usersservice.model.NewUser;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.GroupRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;

import java.util.List;

public interface UserService {
    void createUser(NewUser newUser);
    void sendVerificationEmail(String userId);
    String login(Login login);
    void deleteUser(String userId);
    void forgotPassword(String username);
    UserResource getUser(String userId);
    List<RoleRepresentation> getUserRoles(String userId);
    List<GroupRepresentation> getUserGroups(String userId);
    List<InternalUser> getAllInternalUsers();
    InternalUser getInternalUser(String userId);
    void deleteInternalUser(String userId);
    void disconnectUser(InternalUser internalUser);
    List<InternalUser> findConnectedUsers();
    String refreshToken(String refreshToken);
}
