package org.etutoria.usersservice.entities;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Addresse {
    @Id
    private String id;
    private String street;
    private String houseNumber;
    private String postalCode;
    private String city;
    private String country;
}
