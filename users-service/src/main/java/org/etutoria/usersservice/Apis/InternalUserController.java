package org.etutoria.usersservice.Apis;

import org.etutoria.usersservice.entities.ImageInternalUser;
import org.etutoria.usersservice.entities.InternalUser;
import org.etutoria.usersservice.service.ProfileImageService;
import org.etutoria.usersservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/internal-users")
public class InternalUserController {
    private final UserService userService;
    private final ProfileImageService profileImageService;
    public InternalUserController(UserService userService, ProfileImageService profileImageService) {
        this.userService = userService;
        this.profileImageService = profileImageService;
    }
    @GetMapping
    public ResponseEntity<List<InternalUser>> getAllInternalUsers() {
        List<InternalUser> internalUsers = userService.getAllInternalUsers();
        return ResponseEntity.ok(internalUsers);
    }
    @PostMapping(value = "/uplaodImageprofile/{userId}" )
    public ImageInternalUser uploadMultiImages(@RequestParam("image") MultipartFile file,
                                               @PathVariable("userId") String userId)
            throws IOException {
        return profileImageService.uplaodImageProfileByUserId(file, userId);
    }
    @RequestMapping(value = "/getImagesProfile/{userId}" , method = RequestMethod.GET)
    public List<ImageInternalUser> getImagesParUser(@PathVariable("userId") String userId)
            throws IOException {
        return profileImageService.getImagesParUser(userId);
    }
    @RequestMapping(value = "/get/info/{id}" , method = RequestMethod.GET)
    public ImageInternalUser getImageDetails(@PathVariable("id") String id) throws IOException {
        return profileImageService.getImageDetails(id);
    }
    @RequestMapping(value = "/load/{id}" , method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) throws IOException
    {
        return profileImageService.getImage(id);
    }
    @RequestMapping(value = "/delete/{id}" , method = RequestMethod.DELETE)
    public void deleteImage(@PathVariable("id") String id){
        profileImageService.deleteImage(id);
    }
    @RequestMapping(value="/update",method = RequestMethod.PUT)
    public ImageInternalUser UpdateImage(@RequestParam("image")MultipartFile file) throws IOException {
        return profileImageService.uplaodImage(file);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<InternalUser> getInternalUser(@PathVariable String userId) {
        InternalUser internalUser = userService.getInternalUser(userId);
        return ResponseEntity.ok(internalUser);
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteInternalUser(@PathVariable String userId) {
        userService.deleteInternalUser(userId);
        return ResponseEntity.status(204).build();
    }
}