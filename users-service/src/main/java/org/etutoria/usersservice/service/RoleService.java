package org.etutoria.usersservice.service;

public interface RoleService {
    void assignRole(String userId ,String roleName);
    void deleteRoleFromUser(String userId ,String roleName);
}
