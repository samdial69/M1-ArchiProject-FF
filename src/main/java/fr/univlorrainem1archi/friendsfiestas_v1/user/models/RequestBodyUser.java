package fr.univlorrainem1archi.friendsfiestas_v1.user.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestBodyUser {

    private String pseudo;
    private String email;
    private String password;
    //private Image profilePicture;
    //private String type;

}