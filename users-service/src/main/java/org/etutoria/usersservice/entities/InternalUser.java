package org.etutoria.usersservice.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InternalUser {
    @Id
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer numberOfViews = 0;
    private String phoneNumber;
    private String profilePicture;
    private Double rating = 0.0;
    private Status status;
    @DBRef
    private List<ImageInternalUser> images;
    private String imagePath;
}