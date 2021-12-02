package fr.univlorrainem1archi.friendsfiestas_v1.user.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String pseudo;
    private String password;

    @ManyToMany
    private List<Role> roles;

    public String toString(){
        return this.getPseudo();
    }
}
