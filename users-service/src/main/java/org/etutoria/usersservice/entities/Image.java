package org.etutoria.usersservice.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class Image {
    private Long idImage;
    private String name;
    private String type;
    private byte[] image;

}
