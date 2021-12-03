package fr.univlorrainem1archi.friendsfiestas_v1.user.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.awt.*;

@Data
@Builder
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String pseudo;
    private String email;
    private String password;
    //private Image profilePicture;
    //private TypeUser typeUser;

}