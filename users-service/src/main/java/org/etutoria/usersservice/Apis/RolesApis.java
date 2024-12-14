package org.etutoria.usersservice.Apis;
import org.etutoria.usersservice.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/roles")
public class RolesApis {
    private final RoleService roleService;
    public RolesApis(RoleService roleService) {
        this.roleService = roleService;
    }
    @PutMapping("/assign/users/{userId}")
    public ResponseEntity<?> assignRole(@PathVariable String userId, @RequestParam String roleName) {
        roleService.assignRole(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @DeleteMapping("/delete/users/{userId}")
    public ResponseEntity<?> deleteRoleFromUser(@PathVariable String userId, @RequestParam String roleName) {

        roleService.deleteRoleFromUser(userId, roleName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
