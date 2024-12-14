package org.etutoria.listingservice.restemplate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InternalUser {
    private String id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Integer numberOfViews;
    private String phoneNumber;
    private String profilePicture;
    private Double rating;

}