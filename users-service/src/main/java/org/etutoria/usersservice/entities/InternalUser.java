package org.etutoria.usersservice.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
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

    @OneToMany(mappedBy = "internalUser")
    private List<ImageInternalUser> images;
    private String imagePath;
}