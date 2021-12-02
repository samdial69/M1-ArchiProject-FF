package fr.univlorrainem1archi.friendsfiestas_v1.user.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import fr.univlorrainem1archi.friendsfiestas_v1.salon.models.Salon;
import fr.univlorrainem1archi.friendsfiestas_v1.task.models.Task;
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


    public String toString(){
        return this.getPseudo();
    }
}
