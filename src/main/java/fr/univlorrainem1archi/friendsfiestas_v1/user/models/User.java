package fr.univlorrainem1archi.friendsfiestas_v1.user.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;
    private String pseudo;
    private String password;

    @OneToMany(mappedBy = "host",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Salon> salons;
}
